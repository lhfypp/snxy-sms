package com.snxy.sms.service.service;

import com.snxy.sms.service.domain.SmsInfoLog;

import java.util.List;

public interface SmsInfoLogService {
    void insertSelective(SmsInfoLog record);

    void insertSelectiveList(List<SmsInfoLog> smsInfoLogList);
}
