package com.example.guavacache.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
    // 产品编码
    private String code;
    // 产品名称
    private String name;
    // 产品价格
    private Integer price;
}