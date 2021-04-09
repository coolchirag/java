package com.chirag.licensedemo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chirag.licensedemo.config.AbstractServerInfos;
import com.chirag.licensedemo.config.LicenseCheckInterceptor;
import com.chirag.licensedemo.config.LicenseCreator;
import com.chirag.licensedemo.config.LinuxServerInfos;
import com.chirag.licensedemo.config.WindowsServerInfos;
import com.chirag.licensedemo.config.dto.LicenseCheckModel;
import com.chirag.licensedemo.config.dto.LicenseCreatorParam;
import com.chirag.licensedemo.util.StringUtils;

@RestController
@RequestMapping("/license")
public class LicenseCreatorController {

	/**
     * Certificate generation path
     */
    @Value("${license.licensePath}")
    private String licensePath = "";
    
    @Autowired
    private LicenseCheckInterceptor interceptor;
    
    @RequestMapping(value = "/getServerInfos",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public LicenseCheckModel getServerInfos(@RequestParam(value = "osName",required = false) String osName) {
        //Operating system type
        if(StringUtils.isBlank(osName)){
            osName = System.getProperty("os.name");
        }
        osName = osName.toLowerCase();

        AbstractServerInfos abstractServerInfos = null;

        //Choose different data acquisition methods according to different operating system types
        if (osName.startsWith("windows")) {
            abstractServerInfos = new WindowsServerInfos();
        } else if (osName.startsWith("linux")) {
            abstractServerInfos = new LinuxServerInfos();
        }else{//Other server types
            abstractServerInfos = new LinuxServerInfos();
        }

        return abstractServerInfos.getServerInfos();
    }
    
    @RequestMapping(value = "/generateLicense",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Map<String,Object> generateLicense(@RequestBody(required = true) LicenseCreatorParam param) {
        Map<String,Object> resultMap = new HashMap<>(2);

        if(StringUtils.isBlank(param.getLicensePath())){
            param.setLicensePath(licensePath);
        }

        LicenseCreator licenseCreator = new LicenseCreator(param);
        boolean result = licenseCreator.generateLicense();

        if(result){
            resultMap.put("result","ok");
            resultMap.put("msg",param);
        }else{
            resultMap.put("result","error");
            resultMap.put("msg","Certificate file generation failed!");
        }
        return resultMap;
    }
    
    @RequestMapping("/test")
    public String test(HttpServletRequest request, HttpServletResponse response) {
    	boolean data = false;
		try {
			data = interceptor.preHandle(request, response, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return data+"";
    }

}
