package com.snxy.sms.service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsInfoLog {
    private Long id;
    //短信流水号，唯一,发送短信时返回
    private String messageId;
    //短信内容
    private String content;
    //短信发送时间，符合BCE API规范，形如：2014-06-12T10:08:22Z
    private Date sendtime;
    //短信收信人
    private String receiverMobile;
    //短信发送状态
    private Integer status;

    private Date gmtCreate;

    private Date gmtModified;

    private Byte isDelete;

}