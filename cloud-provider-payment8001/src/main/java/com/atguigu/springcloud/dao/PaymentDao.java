package com.atguigu.springcloud.dao;

import com.atguigu.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
//@Repository有时候插入有问题
public interface PaymentDao {

    public int create(Payment payment); //插入成功返回1，失败返回0

    public Payment getPaymentById(@Param("id") Long id);
}
