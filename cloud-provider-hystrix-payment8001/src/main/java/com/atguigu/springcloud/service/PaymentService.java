package com.atguigu.springcloud.service;


import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;


@Service
public class PaymentService {
    public String paymentInfo_OK(Integer id){
        return "线程池： "+Thread.currentThread().getName()+"  paymentInfo_OK,id:  "+id+"\t"+"O(∩_∩)O哈哈~";
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })//实际时间超过设置的value的时间，执行fallbackMethod定义的方法
      //注意主启动类加注解@EnableCircuitBreaker
    public String paymentInfo_TimeOut(Integer id){
//      try catch 快捷键  Ctrl+Alt+T
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int timeNumber = 3;
//        int age = 10/0;
        return "线程池： "+Thread.currentThread().getName()+"id:  "+id+"\t"+"O(∩_∩)O哈哈~,耗时"+timeNumber+"秒";
    }
    public String paymentInfo_TimeOutHandler(Integer id){
        return "线程池： "+Thread.currentThread().getName()+"  系统繁忙或运行报错，请稍后再试，id"+id+"\t"+"o(╥﹏╥)o";
    }


    //服务熔断
    @HystrixCommand(fallbackMethod = "paymentCricuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),// 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),// 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),// 时间窗口期/时间范文
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")// 失败率达到多少后跳闸
    })
    public String paymentCricuitBreaker(@PathVariable("id") Integer id){
        if (id<0){
            throw new RuntimeException("*****id 不能为复数");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName()+"\t"+"调用成功，流水号： "+serialNumber;
    }
    public String paymentCricuitBreaker_fallback(@PathVariable("id") Integer id){
        return "id不能为负数，请稍后再试，o(╥﹏╥)o  id:"+id;
    }

}
