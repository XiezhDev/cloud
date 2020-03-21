package com.huige.cloud.controller;


import com.huige.cloud.Constant;
import com.huige.cloud.auth.AdminDetailsService;
import com.huige.cloud.model.User;
import com.huige.cloud.service.UserService;
import com.huige.cloud.utils.ResultMap;
import com.huige.cloud.utils.TokenUtil;
import com.iw86.base.RequestUtil;
import com.iw86.base.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 *@Author xiezh
 *@Description 用户控制器
 *@Date 2018/3/30 19:43
 */

@Controller
@EnableAutoConfiguration
@RequestMapping("/user")
public class UserController extends BaseController{
    @Resource
    private UserService userService;

    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private AdminDetailsService adminDetailsService;
    @Autowired
    private TokenUtil tokenUtil;

    private String cookieToken = "token";
    /**
     *@Author xiezh
     *@Description 跳转到登录页
     *@Date 2018/3/30 22:24
     */
    @RequestMapping("/")
    public String index() {
        return "/login/login";
    }


    /**
     * @Author xiezh
     * @Description 用户登录
     * @Date 2018/3/30 19:52
     * @Params 
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public ResultMap login(@RequestBody User user, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        ResultMap resultMap = new ResultMap();
        if (request.getMethod().equalsIgnoreCase("post")) {
            resultMap.fail().info("参数错误");

            String isSave = RequestUtil.get(request, "isSave", "false");
            if (!StringUtil.isEmpty(user.getUsername()) && !StringUtil.isEmpty(user.getPassword())) {
                Authentication authentication = null;
                // 用户密码必须加密保存，否则报异常
                try {
                    authentication = authenticationProvider.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                user.getUsername(),
                                user.getPassword()
                        )
                    );
                    SecurityContext securityContext = SecurityContextHolder.getContext();
                    securityContext.setAuthentication(authentication);
                } catch (Exception e) {
                    e.printStackTrace();
                    resultMap.fail().info("用户不存在或密码错误");
                    return resultMap;
                }
                if (authentication != null && authentication.isAuthenticated()) {
                    int authTime = Constant.cookieTime;
                    if (isSave.equalsIgnoreCase("true")) {
                        authTime = Constant.saveCookieTime;
                    }
                    final UserDetails userDetails = adminDetailsService.loadUserByUsername(user.getUsername());
                    final String token = tokenUtil.generateToken(userDetails, authTime);
                    Cookie cookie = new Cookie(cookieToken, token);
                    cookie.setMaxAge(authTime);
                    // 设置cookie路径为根路径，避免跳转后cookie失效问题
                    cookie.setPath("/");
                    response.addCookie(cookie);

                    // 保存用户回话
                    User currentUser = userService.selectByUserName(user.getUsername());
                    session.setAttribute(Constant.USERSESSION, currentUser);
                    //session.setMaxInactiveInterval(-1);
                    resultMap.success().info("登录成功");
                } else {
                    resultMap.fail().info("用户名或密码错误");
                }
            } else {
                resultMap.fail().info("用户名和密码必须填写");
            }
            return resultMap;
        } else {
            try {
                response.sendRedirect("/user/");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public ResultMap logout(HttpServletRequest request, HttpServletResponse response, Device device) {
        ResultMap resultMap = new ResultMap();
        if (request.getMethod().equalsIgnoreCase("post")) {

            Cookie cookie = new Cookie(cookieToken, "");
            cookie.setMaxAge(-1);
            cookie.setPath("/");
            response.addCookie(cookie);
            resultMap.success().info("注销成功");

            // TODO 不知道为何，重定向页面不跳转
            //response.sendRedirect( "http://localhost:8080/admin/");
        }
        return resultMap;
    }

    @ResponseBody
    @RequestMapping("register")
    public ResultMap register(@RequestBody User user) {
        ResultMap resultMap = new ResultMap();
        try {
            if(StringUtil.isEmpty(user.getUsername()) || StringUtil.isEmpty(user.getPassword())){
                resultMap.fail().info("用户名或密码不能为空");
            } else {
                int saveResult = userService.addUser(user);
                if(saveResult == Constant.Fail) {
                    resultMap.fail().info("参数错误");
                } else if(saveResult == Constant.DUPLICATE) {
                    resultMap.fail().info("用户已存在");
                } else if(saveResult > 0){
                    resultMap.success().info("注册成功");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.fail().info(e.getMessage());
        }

        return resultMap;
    }

    @RequestMapping("/login/success")
    public String loginSuccess() {
        return "/login/login_success";
    }
}
