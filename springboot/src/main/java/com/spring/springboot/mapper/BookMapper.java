package com.spring.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.springboot.pojo.Book;

//nmd,myabatisplus是真的牛，完全不用自己先mapper接口了，继承下接口就OK了。。。。。。。
public interface BookMapper extends BaseMapper<Book> {
}
