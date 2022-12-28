package com.example.guavacache.cache;

import com.example.guavacache.domain.Product;
import com.google.common.cache.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Component
public class LocalCache {
    private static LoadingCache<String, Product> cache;

    @PostConstruct
    public void init(){
        cache = CacheBuilder.newBuilder()
                .initialCapacity(10) //cache初始大小
                .concurrencyLevel(5) //设置并发数为5，即同一时间最多只能有5个线程往 cache 执行写入操作
                .maximumSize(10000)// cache最大缓存key个数
                .expireAfterAccess(5,TimeUnit.SECONDS) //设置5秒未访问则失效
//                .expireAfterWrite(5, TimeUnit.SECONDS) // 设置 cache 中的数据在写入之后的存活时间为10秒
                .removalListener(new CacheRemoveListener()) // 失效监听器,主动移除的情况马上就会执行，被动失效的下一次get不到会触发
                .build(new CacheLoader<String, Product>() {
                    /**
                     * 当未命中缓存数据时，调用load方法获取
                     */
                    @Override
                    public Product load(String key) throws Exception {
                        System.out.println("缓存未命中，从数据库中获取数据");
                        return new Product(key, "农产品",11);
                    }
                });
    }

    public class CacheRemoveListener implements RemovalListener<String, Product> {
        @Override
        public void onRemoval(RemovalNotification<String, Product> removalNotification) {
            System.out.printf("产品：%s被清除,移除原因:%s \n", removalNotification.getKey(), removalNotification.getCause());
        }
    }

    public void put(String key,Product value){
        cache.put(key,value);
    }

    public Product get(String key) throws ExecutionException {
        return cache.get(key);
    }

    public void invalidate(String key){
        cache.invalidate(key);
    }

    public void invalidateAll(){
        cache.invalidateAll();
    }

}