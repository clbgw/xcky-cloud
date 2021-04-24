package com.xcky.model.entity;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 树实体类基本属性
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class TreeEntity<T> {
    /**
     * 主键ID
     */
    private Integer id;
    /**
     * 父主键ID
     */
    private Integer pid;
    /**
     * 子菜单
     */
    private List<T> list;
}
