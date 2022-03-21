package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import com.netflix.discovery.DiscoveryClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j//打印日志
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverport;

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("****插入结果："+result);
        if(result > 0){
            return new CommonResult(200,"插入数据库成功"+serverport,result);
        }else {
            return new CommonResult(444,"插入数据库失败",null);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentByid(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentByid(id);
        log.info("****插入结果："+payment);
        if(payment != null){
            return new CommonResult(200,"查询成功"+serverport,payment);
        }else {
            return new CommonResult(444,"没有对应记录"+id,null);
        }
    }
    @GetMapping(value = "/payment/lb")
    public String getPayment(){
        return serverport;
    }
}
