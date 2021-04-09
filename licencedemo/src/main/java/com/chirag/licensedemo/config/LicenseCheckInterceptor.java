package com.chirag.licensedemo.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class LicenseCheckInterceptor extends HandlerInterceptorAdapter{
	private static Logger logger = LogManager.getLogger(LicenseCheckInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LicenseVerify licenseVerify = new LicenseVerify();

        //Check whether the certificate is valid
        boolean verifyResult = licenseVerify.verify();

        if(verifyResult){
            return true;
        }else{
            response.setCharacterEncoding("utf-8");
            //Map<String,String> result = new HashMap<>(1);
            //result.put("result","Your certificate is invalid. Please check whether the server is authorized or reapply for the certificate!");

            response.getWriter().write("Your certificate is invalid. Please check whether the server is authorized or reapply for the certificate!");

            return false;
        }
    }
}
