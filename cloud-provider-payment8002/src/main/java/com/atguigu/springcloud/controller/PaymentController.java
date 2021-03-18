package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController //controller注解
@Slf4j
//Controller调Service，Service调dao
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

    //一般浏览器不支持发post请求，会出错
    @PostMapping(value = "/payment/create")
    //不要忘记RequestBody否则服务消费者插不进来数据
    public CommonResult create(@RequestBody Payment payment){ //跟在url后面的参数会映射成payment对象
        int result = paymentService.create(payment);
        log.info("*****插入结果："+result);

        if(result>0) return new CommonResult(200,"插入数据库成功,serverPort:"+serverPort,result);
        else return new CommonResult(444,"插入数据库失败",null);
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("*****插入结果："+payment);

        if(payment!=null) return new CommonResult(200,"查询成功,serverPort:"+serverPort,payment);
        else return new CommonResult(444,"没有对应记录，查询ID："+id,null);
    }

    @GetMapping(value = "/payment/discovery")
    public Object discovery(){
        //获得所有微服务名称
        List<String> services = discoveryClient.getServices();
        for(String element : services){
            log.info("***element:"+element);
        }
        /*
         * 获得对应微服务名称的实例
         * ServiceId 微服务名称
         * Host 微服务Ip地址
         * Port 端口
         * */
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for(ServiceInstance instance : instances){
            log.info(instance.getServiceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());
        }
        return this.discoveryClient;

    }

    @GetMapping(value = "/payment/lb")
    public String getPayment(){
        return serverPort;
    }

    //Feign超时控制模拟长流程调用
    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeout(){
        //暂停几秒钟线程
        try{
            TimeUnit.SECONDS.sleep(3);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return serverPort;
    }
}

