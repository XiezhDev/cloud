package com.huige.cloud.auth;

import com.huige.cloud.utils.ResultMap;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author xiezh
 * @Description
 * @Date 2018/4/2 9:35
 */
public class UnAuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        System.out.println("AuthEntryPoint");
        /*Row result = new Row();
        result.put(Constant.CODE, 2);
        result.put(Constant.MSG, "登录失败");*/
        ResultMap resultMap = new ResultMap();
        if (authException instanceof UsernameNotFoundException) {
            //result.put(Constant.MSG, authException.getMessage());
            resultMap.fail().info(authException.getMessage());
        } else if (authException instanceof BadCredentialsException) {
            //result.put(Constant.MSG, "账号密码错误");
            resultMap.fail().info("账号或密码错误");
        } else {
            //result.put(Constant.MSG, "没有访问该页面的权限");
            resultMap.fail().info("没有访问该页面的权限");
        }

        // TODO 暂时不理解isAjaxRequest，授权失败先直接跳转到登录页
        response.sendRedirect("/user/");
        /*if (isAjaxRequest(request)) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JsonUtil.rowToStr(result));
        } else {
            response.sendRedirect("/admin/");
        }*/
    }

    /**
     * @Author xiezh
     * @Description 判断是否为AJAX请求
     * @Date 2018/4/2 9:25
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String requestType = request.getHeader("X-Requested-With");
        if (requestType != null && requestType.equals("XMLHttpRequest")) {
            return true;
        } else {
            return false;
        }
    }
}


