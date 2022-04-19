package com.rg.eduservice.entity.subject;

import lombok.Data;

import java.util.List;

/**
 * @author lxy
 * @version 1.0
 * @Description 树形实体
 * @date 2022/2/21 17:00
 */
@Data
public class OneSubject {
    private String id;
    private String title;
    //一级分类中包含多个二级分类
    private List <TwoSubject> children;
}
