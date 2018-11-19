package com.snxy.sms.service.service.config;

import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.sms.SmsClient;
import com.baidubce.services.sms.SmsClientConfiguration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmsConfig {
    @Bean
    @ConfigurationProperties(prefix = "baidu.sms")
    public BaiduSmsInfo getBaiduSmsInfo(){
        return new BaiduSmsInfo();
    }

    @Bean
    // 实例化发送客户端
    public SmsClient getSmsClient(BaiduSmsInfo baiduSmsInfo){
        // ak、sk等config
        SmsClientConfiguration config = new SmsClientConfiguration();
        // 配置代理为本地8080端口,应该没用，底层服务不对外，默认服务端口就可以
        //config.setProxyHost("127.0.0.1");
        //config.setProxyPort(8080);
        //设置用户名和密码，暂时不清楚作用
        //config.setProxyUsername("username");
        //config.setProxyPassword("password");
        // 设置HTTP最大连接数为10
        //config.setMaxConnections(10);
        // 设置TCP连接超时为5000毫秒,底层写成了静态常量
        //config.setConnectionTimeout(5000);
        // 设置最大的重试次数为3，底层写成了静态常量
        //config.setMaxErrorRetry(3);
        // 设置Socket传输数据超时的时间为2000毫秒，底层写成了静态常量
        //config.setSocketTimeout(2000);

        config.setCredentials(new DefaultBceCredentials(baiduSmsInfo.getAccessKeyId(), baiduSmsInfo.getSecretAccessKy()));
        config.setEndpoint(baiduSmsInfo.getEndPoint());
        SmsClient client = new SmsClient(config);
        return client;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BaiduSmsInfo{
        private String endPoint;
        private String accessKeyId;
        private String secretAccessKy;
    }
}
