package com.snxy.sms.service.async;

import com.baidubce.services.sms.SmsClient;
import com.baidubce.services.sms.model.QueryMessageDetailRequest;
import com.baidubce.services.sms.model.QueryMessageDetailResponse;
import com.snxy.common.exception.BizException;
import com.snxy.sms.service.domain.SmsInfoLog;
import com.snxy.sms.service.service.SmsInfoLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: zhangfeng233
 * @Date: 2018/11/15 18:05
 */
@Component
@Slf4j
public class SmsAsync {
    @Resource
    private SmsClient client;
    @Resource
    private SmsInfoLogService smsInfoLogService;
    //查询短信流水信息
    @Async
    public void selectMessage(String messageId, SmsInfoLog smsInfoLog) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //System.out.println("异步线程名称："+Thread.currentThread().getName());
        // 实例化请求对象
        QueryMessageDetailRequest qurRequest = new QueryMessageDetailRequest();
        qurRequest.setMessageId(messageId);// 设置待查询的messageId

        QueryMessageDetailResponse querResponse = client.queryMessageDetail(qurRequest);// 请求Server
        log.info("querResponse:[{}]",querResponse);

        if(querResponse.getMessageId() == null || querResponse.getContent() == null ||
                querResponse.getReceiver().size()<=0 || querResponse.getSendTime()== null){
            smsInfoLog.setMessageId(messageId);
            this.smsInfoLogService.insertSelective(smsInfoLog);
            throw new BizException("没有查询到短信流水");
        }
        smsInfoLog.setMessageId(querResponse.getMessageId());
        smsInfoLog.setContent(querResponse.getContent());
        //没有群发功能，所以返回的list长度只能是1
        smsInfoLog.setReceiverMobile(querResponse.getReceiver().get(0));
        smsInfoLog.setSendtime(querResponse.getSendTime());
        smsInfoLog.setGmtCreate(new Date());
        smsInfoLog.setIsDelete(new Byte("0"));

        this.smsInfoLogService.insertSelective(smsInfoLog);

    }

    @Async
    //群发查询短信流水
    public void selectMessages(List<SmsInfoLog> smsInfoLogs) {
        System.out.println(new Date());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(new Date());
        //System.out.println("异步线程名称："+Thread.currentThread().getName());
        List<SmsInfoLog> smsInfoLogList = new ArrayList<>();
        smsInfoLogs.forEach(smsInfoLog -> {
            // 实例化请求对象
            QueryMessageDetailRequest qurRequest = new QueryMessageDetailRequest();
            qurRequest.setMessageId(smsInfoLog.getMessageId());// 设置待查询的messageId
            QueryMessageDetailResponse querResponse = client.queryMessageDetail(qurRequest);

            if(querResponse.getMessageId() == null || querResponse.getContent() == null ||
                    querResponse.getReceiver().size()<=0 || querResponse.getSendTime()== null){
                smsInfoLog.setMessageId(smsInfoLog.getMessageId());
                this.smsInfoLogService.insertSelective(smsInfoLog);
            }
            SmsInfoLog smsInfoLog1 = SmsInfoLog.builder()
                    .messageId(querResponse.getMessageId())
                    .content(querResponse.getContent())
                    .receiverMobile(querResponse.getReceiver().get(0))
                    .sendtime(querResponse.getSendTime())
                    .status(smsInfoLog.getStatus())
                    .gmtCreate(new Date())
                    .isDelete(new Byte("0"))
                    .build();
            smsInfoLogList.add(smsInfoLog1);
        });
        this.smsInfoLogService.insertSelectiveList(smsInfoLogList);

    }
}
