package com.atuguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component //表示将此类标记为Spring容器中的一个Bean，不加的话会扫描不到
public class MyLB implements LoadBalancer{
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public final int getIncrement(){
        int current;
        int next;
        do{
            current = this.atomicInteger.get(); //得到当前的值
            next = current >= 2147483647 ? 0 : current + 1;
        }while (!this.atomicInteger.compareAndSet(current,next));//自旋
        System.out.println("***next:"+next);
        return next;
    }

    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstances) {
        int index = atomicInteger.getAndIncrement() % serviceInstances.size();
        return serviceInstances.get(index);
    }
}
