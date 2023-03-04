package com.spring.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.springboot.common.Result;
import com.spring.springboot.mapper.BookMapper;
import com.spring.springboot.pojo.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@CrossOrigin //实现跨域
@RestController
//返回json数据的
@RequestMapping("/book")
@Slf4j
//映射
public class BookController {
    @Resource  //没用那个啥auto wired而用得resource，可能是因为没有service层？
    BookMapper BookMapper;
    //按照一般情况下得话是引入service接口得，主要是这里得业务不复杂，就是简单得增删查改，这都是dao/mapper接口就可以了

    //POST是新增
    @PostMapping
    public Result<?> save(@RequestBody Book book){
        BookMapper.insert(book);
        return Result.success();
    }

    //PUT是更新
    @PutMapping
    public Result<?> update(@RequestBody Book book){
        BookMapper.updateById(book);
        return Result.success();
    }


    //DELETE是删除
    @DeleteMapping("/{id}")
    public Result<?> detelete(@PathVariable("id") Long id){
        BookMapper.deleteById(id);
        return Result.success();
    }



    @GetMapping//分页得功能，抄就完事了
    public Result<?> findPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search){
        //模糊查询
        LambdaQueryWrapper<Book> wrapper = Wrappers.<Book>lambdaQuery();
        if (StrUtil.isNotBlank(search)){
            //用hutool的第三方库函数还判断search是否为空，来判断是不是要进行模糊查询
             wrapper.like(Book::getName,search);
        }
        Page<Book> BookPage = BookMapper.selectPage(new Page<>(pageNum, pageSize),wrapper);
        return Result.success(BookPage);
    }




}
