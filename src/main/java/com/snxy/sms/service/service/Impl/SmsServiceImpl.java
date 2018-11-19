package com.snxy.sms.service.service.Impl;

import com.baidubce.services.sms.SmsClient;
import com.baidubce.services.sms.model.*;
import com.snxy.common.exception.BizException;
import com.snxy.common.util.StringUtil;
import com.snxy.sms.service.async.SmsAsync;
import com.snxy.sms.service.domain.SmsInfoLog;
import com.snxy.sms.service.service.SmsService;
import com.snxy.sms.service.service.config.RequestConfig;
import com.snxy.sms.service.service.config.SmsConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
//短信格式可能多种，下载app提醒（分安卓和ios，下载路径会有不同），注册验证码,找回密码验证码
public class SmsServiceImpl implements SmsService {
    @Resource
    private SmsClient client;
    @Resource
    SmsAsync smsAsync;
    @Resource
    RequestConfig.RequestInfo requestInfo;

    //发送短信
    @Override
    public boolean smsMessage(SendMessageV2Request request) {
        // 发送请求
        SendMessageV2Response response = client.sendMessage(request);
        log.info("RequestId:[{}]",response.getRequestId());
        SmsInfoLog smsInfoLog = SmsInfoLog.builder().build();

        // 解析请求响应 response.isSuccess()为true 表示成功
        if (response != null && response.isSuccess()) {
            smsInfoLog.setStatus(1);
            this.smsAsync.selectMessage(response.getRequestId(),smsInfoLog);
            return true;
        } else {
            smsInfoLog.setStatus(0);
            this.smsAsync.selectMessage(response.getRequestId(),smsInfoLog);
            throw new BizException("短信发送失败");
        }
    }

//    //查询短信流水信息
//    @Override
//    public void selectMessage(String messageId,SmsInfoLog smsInfoLog) {
//        QueryMessageDetailRequest qurRequest = new QueryMessageDetailRequest();// 构建request对象
//        qurRequest.setMessageId(messageId);// 设置待查询的messageId
//
//        QueryMessageDetailResponse querResponse = client.queryMessageDetail(qurRequest);// 请求Server
//        log.info("querResponse:[{}]",querResponse);
//
//        if(querResponse.getMessageId() == null || querResponse.getContent() == null ||
//                querResponse.getReceiver().size()<=0 || querResponse.getSendTime()==null){
//            throw new BizException("没有查询到短信流水");
//        }
//        smsInfoLog.setMessageId(querResponse.getMessageId());
//        smsInfoLog.setContent(querResponse.getContent());
//                            //没有群发功能，所以返回的list长度只能是1
//        smsInfoLog.setReceiverMobile(querResponse.getReceiver().get(0));
//        smsInfoLog.setSendtime(querResponse.getSendTime());
//        smsInfoLog.setGmtCreate(new Date());
//        smsInfoLog.setIsDelete(new Byte("0"));
//
//        this.smsInfoLogService.insertSelective(smsInfoLog);
//
//    }

    @Override
    public boolean sendMessage(String mobile, String message, Long messageType) {
        if(!StringUtil.checkMobile(mobile)){
            throw new BizException("手机号格式不正确");
        }
        //实例化请求对象
        SendMessageV2Request request = new SendMessageV2Request();
        request.withInvokeId(requestInfo.getInvokeId())
                .withPhoneNumber(mobile);

        //添加短信模板中的变量
        Map<String, String> vars = new HashMap<String, String>();
        if (messageType.equals(1L)){            //1为注册验证码
            request.withTemplateCode(requestInfo.getTemplateCode1());
            //添加短信模板中的变量
            vars.put("code", message);
            vars.put("time", "20");
            request.withContentVar(vars);
        }else if (messageType.equals(2L)){      //2为密码找回验证码

        }else if(messageType.equals(3L)){                             //3为推广短信

        }
        //System.out.println("主线程名称："+Thread.currentThread().getName());
        return this.smsMessage(request);
    }

    @Override
    public void sendMessageList(List<String> mobiles, String message) {
        List<SmsInfoLog> smsInfoLogs = new ArrayList<>();

        //添加短信模板中的变量
        Map<String, String> vars = new HashMap<String, String>();
        vars.put("code", message);
        vars.put("time", "20");
        mobiles.stream().forEach(mobile ->{
            //实例化请求对象
            SendMessageV2Request request = new SendMessageV2Request();
            request.withInvokeId(requestInfo.getInvokeId())
                    .withPhoneNumber(mobile)
                    .withTemplateCode(requestInfo.getTemplateCode3())
                    .withContentVar(vars);
            SmsInfoLog smsInfoLog = this.smsMessageList(request);
            smsInfoLogs.add(smsInfoLog);
        });
        this.smsAsync.selectMessages(smsInfoLogs);

    }

    @Override
    //发送短信
    public SmsInfoLog smsMessageList(SendMessageV2Request request) {
        // 发送请求
        SendMessageV2Response response = client.sendMessage(request);
        log.info("RequestId:[{}]",response.getRequestId());
        SmsInfoLog smsInfoLog = SmsInfoLog.builder()
                            .messageId(response.getRequestId())
                            .build();

        // 解析请求响应 response.isSuccess()为true 表示成功
        if (response != null && response.isSuccess()) {
            smsInfoLog.setStatus(1);
            return smsInfoLog;
        } else {
            smsInfoLog.setStatus(0);
            return smsInfoLog;
        }
    }
}
