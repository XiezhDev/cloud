package com.huige.cloud.filter;

import com.huige.cloud.Constant;
import com.huige.cloud.auth.AdminDetailsService;
import com.huige.cloud.utils.TokenUtil;
import com.iw86.base.RequestUtil;
import com.iw86.base.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 *@Author xiezh
 *@Description 拦截路径，并判断权限
 *@Date 2018/4/2 9:26
 */
public class AuthTokenFilter extends OncePerRequestFilter {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private AdminDetailsService adminDetailsService;

    @Autowired
    private TokenUtil tokenUtil;

    private String cookieToken = "token";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("url--" + RequestUtil.getUrl(request));
        String authToken = RequestUtil.getCookieValue(request, this.cookieToken);
        if (StringUtil.isEmpty(authToken)) {
            chain.doFilter(request, response);
            return;
        }
        String username;
        try {
            username = tokenUtil.getUsernameFromToken(authToken);
        } catch (IllegalArgumentException e) {
            username = null;
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.adminDetailsService.loadUserByUsername(username);
            if (tokenUtil.validateToken(authToken, userDetails)) {
                Date expireDate = tokenUtil.getExpirationDateFromToken(authToken);
                if (expireDate.getTime() - new Date().getTime() <= 2 * 60 * 60 * 1000) {
                    final String token = tokenUtil.refreshToken(authToken, Constant.cookieTime);
                    Cookie cookie = new Cookie(cookieToken, token);
                    cookie.setMaxAge(Constant.cookieTime);
                    response.addCookie(cookie);
                }

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            try{
                chain.doFilter(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}