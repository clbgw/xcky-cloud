package com.xcky.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xcky.model.entity.SysMenu;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 菜单值对象
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SysMenuVo extends SysMenu<SysMenuVo> {

}
