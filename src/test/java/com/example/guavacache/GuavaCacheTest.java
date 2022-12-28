package com.example.guavacache;

import com.example.guavacache.cache.LocalCache;
import com.example.guavacache.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutionException;

@SpringBootTest
public class GuavaCacheTest {

    @Autowired
    private LocalCache localCache;

    @Test
    public void test() throws ExecutionException {
        //第一次查找没有会进入build的CacheLoader重写方法从数据库获取
        Product product = localCache.get("11");
        System.out.println(product.toString());
        //因为上面已经缓存，这次不会再执行重写方法
        System.out.println(product.toString());
        //手动移除会触发onRemoval方法
        localCache.invalidate("11");
    }
}
