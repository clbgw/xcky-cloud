package com.xcky.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 菜单实体类
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class SysMenu<T> extends TreeEntity<T> {

    /**
     * 排序序号(越大越前面)
     */
    private Integer sortNum;
    /**
     * 唯一逻辑名称
     */
    private String name;
    /**
     * 显示名称
     */
    private String title;
    /**
     * icon
     */
    private String icon;
    /**
     * 跳转路径
     */
    private String jump;
    /**
     * 是否切断(1-是,0-否)
     */
    private Boolean spread;

    /**
     * 所属应用
     */
    private String appid;
}
