package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

public interface PaymentService {

    //方法一般和mybatis一致
    public int create(Payment payment);

    public Payment getPaymentById(@Param("id") Long id);
}
