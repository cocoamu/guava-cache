package com.example.guavacache.controller;

import com.example.guavacache.domain.Product;
import com.example.guavacache.cache.LocalCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class TestController {

    @Autowired
    private LocalCache localCache;

    @RequestMapping("/get")
    public String get(String key) throws ExecutionException {
        //第一次查找没有会进入build的CacheLoader重写方法从数据库获取
        Product product = localCache.get(key);
        return product.toString();
    }

    @RequestMapping("/remove")
    public String remove(String key){
        localCache.invalidate(key);
        return "ok";
    }

}
