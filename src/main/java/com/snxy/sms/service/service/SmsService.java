package com.snxy.sms.service.service;

import com.baidubce.services.sms.model.SendMessageV2Request;
import com.snxy.sms.service.domain.SmsInfoLog;

import java.util.List;

public interface SmsService {
    boolean smsMessage(SendMessageV2Request request);

//    void selectMessage(String messageId,SmsInfoLog smsInfoLog);

    boolean sendMessage(String mobile, String message,Long messageType);

    void sendMessageList(List<String> mobiles,String message);

    SmsInfoLog smsMessageList(SendMessageV2Request request);
}
