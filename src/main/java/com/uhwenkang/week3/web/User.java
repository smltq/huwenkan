package com.uhwenkang.week3.web;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;


@ToString
@Data
public class User {
   @TableId(value = "id",type = IdType.AUTO)
   private Integer id;
   private String userName;
   private String userPassWord;

}
