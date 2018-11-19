package com.snxy.sms.service.dao.mapper;

import com.snxy.sms.service.domain.SmsInfoLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SmsInfoLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SmsInfoLog record);

    int insertSelective(SmsInfoLog record);

    SmsInfoLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SmsInfoLog record);

    int updateByPrimaryKey(SmsInfoLog record);

    int insertSelectiveList(@Param("smsInfoLogList") List<SmsInfoLog> smsInfoLogList);
}