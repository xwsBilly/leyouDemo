package com.leyou.search.client;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import feign.Client;
import feign.Contract;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.hystrix.HystrixFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FeignClientConfig
 *
 * @author xieweisong
 * @date 2019/8/12 11:02
 */
//@Configuration
public class FeignClientConfig {
    /**
     * FeignClientFactoryBean 该工厂类中 设置builder属性时就是通过该对象，源码中可看到
     */
    @Autowired
    private FeignContext feignContext;

    /**
     * 通过注入Eureka实例对象，就不用手动指定url，只需要指定服务名即可
     */
    @Qualifier("eurekaClient")
    @Autowired
    private EurekaClient eurekaClient;


    private <T> T createFeign(Class<T> clazz, String serverId){
        InstanceInfo nextServerFromEureka = eurekaClient.getNextServerFromEureka(serverId,false);
        return Feign.builder()
                .encoder(feignContext.getInstance(serverId, Encoder.class))
                .decoder(feignContext.getInstance(serverId, Decoder.class))
                .contract(feignContext.getInstance(serverId, Contract.class))
                .target(clazz, nextServerFromEureka.getHomePageUrl());
    }

    @Bean
    public BrandFeignClient getBrandFeignClient(){
        return createFeign(BrandFeignClient.class,"item-service");
    }

    @Bean
    public CategoryFeignClient getCategoryFeignClient(){
        return createFeign(CategoryFeignClient.class,"item-service");
    }

    @Bean
    public GoodsFeignClient getGoodsFeignClient(){
        return createFeign(GoodsFeignClient.class,"item-service");
    }
}
