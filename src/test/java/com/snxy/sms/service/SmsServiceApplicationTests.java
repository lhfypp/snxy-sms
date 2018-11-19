package com.snxy.sms.service;

import com.snxy.sms.service.dao.mapper.SmsInfoLogMapper;
import com.snxy.sms.service.domain.SmsInfoLog;
import com.snxy.sms.service.service.SmsService;
import com.snxy.sms.service.service.config.RequestConfig;
import com.snxy.sms.service.service.config.SmsConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmsServiceApplicationTests {
    @Resource
    SmsInfoLogMapper smsInfoLogMapper;
    @Resource
    SmsService service;
    @Test
    public void contextLoads() {
        List<SmsInfoLog> smsInfoLogList = new ArrayList<>();
        SmsInfoLog smsInfoLog = SmsInfoLog.builder()
                .content("aaa")
                .messageId("1111111")
                .receiverMobile("123456")
                .build();
        SmsInfoLog smsInfoLog1 = SmsInfoLog.builder()
                .content("bbb")
                .messageId("222222")
                .receiverMobile("123456")
                .build();
        SmsInfoLog smsInfoLog2 = SmsInfoLog.builder()
                .content("ccc")
                .messageId("333333")
                .receiverMobile("123456")
                .build();
        SmsInfoLog smsInfoLog3 = SmsInfoLog.builder()
                .content("ddd")
                .messageId("444444")
                .receiverMobile("123456")
                .build();
        smsInfoLogList.add(smsInfoLog);
        smsInfoLogList.add(smsInfoLog1);
        smsInfoLogList.add(smsInfoLog2);
        smsInfoLogList.add(smsInfoLog3);

        this.smsInfoLogMapper.insertSelectiveList(smsInfoLogList);
    }

    @Test
    public void sendMessageList(){

        List<String> list = new ArrayList<>();
        list.add("13261420174");
        list.add("15001080037");
        list.add("18584830939");
        list.add("13611163216");
        list.add("15776544152");
        String message = "9527";
        service.sendMessageList(list,message);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sendMessageTest(){
        List<String> list = new ArrayList<>();
        list.add("13261420174");
//        list.add("15001080037");
//        list.add("18584830939");
//        list.add("13611163216");
//        list.add("15776544152");
        String message = "9527";
        service.sendMessage(list.get(0),message,1L);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Resource
    RequestConfig.RequestInfo requestInfo;
    @Test
    public void Tesa(){
        System.out.println(requestInfo.getInvokeId());
    }
}
