package com.snxy.sms.service;

import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.sms.SmsClient;
import com.baidubce.services.sms.SmsClientConfiguration;
import com.baidubce.services.sms.model.QueryMessageDetailRequest;
import com.baidubce.services.sms.model.QueryMessageDetailResponse;
import com.baidubce.services.sms.model.SendMessageV2Request;
import com.baidubce.services.sms.model.SendMessageV2Response;
import com.snxy.common.exception.BizException;
import com.snxy.sms.service.domain.SmsInfoLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Slf4j
public class Test {
    public static void main(String[] args) {

        // 相关参数定义
        String endPoint = "http://sms.bj.baidubce.com"; // SMS服务域名，可根据环境选择具体域名
        String accessKeyId = "dcc7c1e05ede4c96b903e76b4b656ae6";  // 发送账号安全认证的Access Key ID
        String secretAccessKy = "25f8ceec33aa49b586557176863dd5e9"; // 发送账号安全认证的Secret Access Key

        // ak、sk等config
        SmsClientConfiguration config = new SmsClientConfiguration();
        config.setCredentials(new DefaultBceCredentials(accessKeyId, secretAccessKy));
        config.setEndpoint(endPoint);

        // 实例化发送客户端
        SmsClient smsClient = new SmsClient(config);

//        // 定义请求参数
//        String invokeId = "1WuHtl54-ltYn-DriX"; // 发送使用签名的调用ID
//        String phoneNumber = "13261420174"; // 要发送的手机号码(只能填写一个手机号)
//        String templateCode = "smsTpl:e7476122a1c24e37b3b0de19d04ae900"; // 本次发送使用的模板Code
//        Map<String, String> vars = new HashMap<String, String>(); // 若模板内容为：您的验证码是${code},在${time}分钟内输入有效
//        vars.put("code", "986082");
//
//        //实例化请求对象
//        SendMessageV2Request request = new SendMessageV2Request();
//        request.withInvokeId(invokeId)
//                .withPhoneNumber(phoneNumber)
//                .withTemplateCode(templateCode)
//                .withContentVar(vars);
//
//        // 发送请求
//        SendMessageV2Response response = smsClient.sendMessage(request);
//
//        //短信流水号
//        String requestId = response.getRequestId();

        //查询短信流水信息
        QueryMessageDetailRequest qurRequest = new QueryMessageDetailRequest();// 构建request对象
        qurRequest.setMessageId("616d38fd-3c2b-4374-93f6-dec56023ed3");// 设置待查询的messageId

        QueryMessageDetailResponse querResponse = smsClient.queryMessageDetail(qurRequest);// 请求Server

        /*SmsInfoLog smsInfoLog = SmsInfoLog.builder()
                .messageId(querResponse.getMessageId())
                .content(querResponse.getContent())
                //没有群发功能，所以返回的list长度只能是1
                .receiverMobile(querResponse.getReceiver().get(0))
                .sendtime(querResponse.getSendTime())
                .isDelete(new Byte("0"))
                .build();*/

        System.out.println(querResponse);
        if(querResponse.getMessageId() == null || querResponse.getContent() == null ||
                querResponse.getReceiver().size()<=0 || querResponse.getSendTime()==null){
            System.out.println("1=============="+new Date());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("2=============="+new Date());
            throw new BizException("没有查询到短信流水");
        }else {


        }
        log.info("querResponse:[{}]",querResponse);
    }
}
