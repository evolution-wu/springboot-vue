package com.spring.springboot.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;


import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString
@TableName("book")
public class Book {
    //设置自增ID
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private BigDecimal price;
    private String author;
   @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")   //日期格式化
    private Date createTime;
    private String cover;

    public Book() {
    }


    public Book(Integer id, String name, BigDecimal price, String author, Date createTime, String cover) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.author = author;
        this.createTime = createTime;
        this.cover = cover;
    }
}
