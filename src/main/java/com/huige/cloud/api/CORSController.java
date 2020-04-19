package com.huige.cloud.api;

import com.huige.cloud.utils.ResultMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/cors")
public class CORSController {

    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/cors")
    public ResultMap cors(HttpServletResponse response) {
        /*// 允许跨域
        // 指定允许其他域名访问
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 响应类型
        response.setHeader("Access-Control-Allow-Methods", "*");
        // 响应头设置
        response.setHeader("Access-Control-Allow-Headers",
                "x-requested-with,content-type");*/
        return new ResultMap().success().data("1");
    }
}
