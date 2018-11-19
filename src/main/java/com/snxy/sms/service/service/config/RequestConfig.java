package com.snxy.sms.service.service.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: zhangfeng233
 * @Date: 2018/11/19 9:57
 */
@Configuration
public class RequestConfig {
    @Bean
    @ConfigurationProperties(prefix = "baidu.request")
    public RequestConfig.RequestInfo getRequestInfo(){
        return new RequestInfo();
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestInfo{
        private String invokeId;
        private String templateCode1;
        private String templateCode2;
        private String templateCode3;
    }
}
