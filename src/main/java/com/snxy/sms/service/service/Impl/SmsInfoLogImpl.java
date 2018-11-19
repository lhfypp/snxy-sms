package com.snxy.sms.service.service.Impl;

import com.snxy.common.exception.BizException;
import com.snxy.sms.service.dao.mapper.SmsInfoLogMapper;
import com.snxy.sms.service.domain.SmsInfoLog;
import com.snxy.sms.service.service.SmsInfoLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class SmsInfoLogImpl implements SmsInfoLogService {
    @Resource
    private SmsInfoLogMapper smsInfoLogMapper;

    @Override
    public void insertSelective(SmsInfoLog record) {
        int i = this.smsInfoLogMapper.insertSelective(record);
        if(i == 0){
            throw new BizException("添加记录失败");
        }
    }

    @Override
    public void insertSelectiveList(List<SmsInfoLog> smsInfoLogList) {
        int i = this.smsInfoLogMapper.insertSelectiveList(smsInfoLogList);
        if(i == 0){
            throw new BizException("添加记录失败");
        }
        if(i<smsInfoLogList.size() && i>0){
            throw new BizException("添加记录不完整");
        }
    }
}
