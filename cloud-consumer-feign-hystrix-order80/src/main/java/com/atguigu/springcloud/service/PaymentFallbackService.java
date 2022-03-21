package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class PaymentFallbackService implements PaymentHystrixService{
    public String paymentInfo_OK(Integer id) {
        return "-----PaymentFallbackService  fall  back-paymentInfo_OK";
    }

    public String paymentInfo_TimeOut(Integer id) {
        return "-----PaymentFallbackService  fall  back-paymentInfo_TimeOUt";
    }
}
