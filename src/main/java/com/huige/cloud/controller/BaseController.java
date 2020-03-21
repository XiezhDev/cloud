package com.huige.cloud.controller;

import com.iw86.base.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 *@Author xiezh
 *@Description 
 *@Date 2018/4/2 9:35
 */
public class BaseController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public BaseController() {
    }

    protected void setHeader(HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
    }

    protected void noCache(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0L);
    }

    protected String getReferer(HttpServletRequest request) {
        return request.getHeader("REFERER");
    }

    protected String sendRedirect(HttpServletResponse response, String url) {
        try {
            response.sendRedirect(url);
        } catch (Exception var4) {
            this.logger.error("页面重定向出现异常：", var4);
        }

        return null;
    }

    protected String renderText(HttpServletRequest request, HttpServletResponse response, String text) {
        this.noCache(request, response);
        this.setHeader(response);

        try {
            response.getWriter().write(text);
        } catch (Exception var5) {
            this.logger.error("异常", var5);
        }

        return null;
    }

    protected String renderXml(HttpServletRequest request, HttpServletResponse response, String text) {
        this.noCache(request, response);
        response.setContentType("text/xml;charset=UTF-8");

        try {
            response.getWriter().write(text);
        } catch (Exception var5) {
            this.logger.error("异常", var5);
        }

        return null;
    }

    protected String renderJson(HttpServletRequest request, HttpServletResponse response, String json) {
        this.noCache(request, response);
        response.setContentType("application/json;charset=UTF-8");

        try {
            response.getWriter().write(json);
        } catch (Exception var5) {
            this.logger.error("异常", var5);
        }

        return null;
    }

    protected String renderStream(HttpServletRequest request, HttpServletResponse response, byte[] bytes) {
        this.noCache(request, response);
        response.setContentType("application/octet-stream;charset=UTF-8");

        try {
            OutputStream out = response.getOutputStream();
            out.write(bytes);
            out.flush();
            out.close();
        } catch (Exception var5) {
            this.logger.error("异常", var5);
        }

        return null;
    }

    protected String renderScript(HttpServletRequest request, HttpServletResponse response, String jscode) {
        String text = StringUtil.str(new Object[]{"<script>", jscode, "</script>"});
        return this.renderText(request, response, text);
    }
}
