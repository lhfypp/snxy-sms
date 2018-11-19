package com.snxy.sms.service.web;

import com.snxy.common.response.ResultData;
import com.snxy.sms.service.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/sms")
@Slf4j
public class SmsController {
    @Resource
    private SmsService smsService;

    @RequestMapping("/sendMessage")
    private ResultData sendMessage(String mobile, String message, Long messageType){
        log.debug("mobile:[{}]",mobile);
        log.debug("message:[{}]",message);
        log.debug("messageType:[{}]",messageType);
        smsService.sendMessage(mobile, message, messageType);
        return ResultData.success(null);
    }

    @RequestMapping("/sendMessageList")
    private ResultData sendMessageList(List<String> mobiles, String message){
        log.debug("mobiles:[{}]",mobiles);
        log.debug("message:[{}]",message);
        return ResultData.success(null);
    }
}
