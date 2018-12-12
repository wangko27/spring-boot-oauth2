package com.simon.controller;

import com.simon.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/*import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;*/

/**
 * 全局错误处理
 *
 * @author simon
 * @create 2018-05-30 17:26
 **/

@RestController
@RequestMapping(value = "error")
public class TokenErrorController extends BasicErrorController {
    private static Logger logger = LoggerFactory.getLogger(TokenErrorController.class);
    private static final String PATH = "/error";

    public TokenErrorController(){
        super(new DefaultErrorAttributes(), new ErrorProperties());
    }

    @RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request,
                isIncludeStackTrace(request, MediaType.ALL));
        /*HttpStatus status = getStatus(request);
        if (!Strings.isNullOrEmpty((String)body.get("exception")) && body.get("exception").equals(InvalidTokenException.class.getName())){
            body.put("status", HttpStatus.FORBIDDEN.value());
            status = HttpStatus.FORBIDDEN;
        }*/
        Map<String, Object> resultMap = new LinkedHashMap<>();
        HttpStatus status = getStatus(request);
        resultMap.put(AppConfig.CODE, status.value());
        resultMap.put(AppConfig.MESSAGE, body.get("message"));
        resultMap.put(AppConfig.DATA, null);
        return new ResponseEntity<>(resultMap, status);
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}