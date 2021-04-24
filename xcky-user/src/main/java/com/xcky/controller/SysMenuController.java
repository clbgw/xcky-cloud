package com.xcky.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xcky.enums.ResponseEnum;
import com.xcky.model.req.AdminSysMenuTreeReq;
import com.xcky.model.resp.R;
import com.xcky.model.vo.SysMenuVo;
import com.xcky.service.SysMenuService;
import com.xcky.util.EntityUtil;
import com.xcky.util.ResponseUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 系统菜单控制器
 *
 * @author lbchen
 */
@RestController
@Slf4j
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 批量更新系统菜单树
     *
     * @param list 系统菜单值对象列表
     * @return 基本响应对象
     */
    @PostMapping("/sys/menu/updateMenuList")
    public R updateMenu(@RequestBody List<SysMenuVo> list) {
        // 批量更新树层级的菜单列表
    	Integer updateResult = sysMenuService.updateSysMenuBatch(list);
        if (null == updateResult || updateResult < 1) {
        	log.error("批量更新菜单树失败");
            return ResponseUtil.fail(ResponseEnum.UPDATE_ERROR);
        }
        return ResponseUtil.ok(updateResult);
    }

    /**
     * 获取菜单
     *
     * @param adminSysMenuTreeReq 获取菜单树请求参数
     * @return 基本响应对象
     */
    @PostMapping("/sys/menu/postMenuTree")
    public R postMenuTree(@RequestBody AdminSysMenuTreeReq adminSysMenuTreeReq) {
        Map<String, Object> map = EntityUtil.entityToMap(adminSysMenuTreeReq);
        List<SysMenuVo> list = sysMenuService.getSysMenuTreeByMap(map);
        List<SysMenuVo> tempList = sysMenuService.getSysMenuVoTreeByList(list);
        return ResponseUtil.ok(tempList);
    }
}
