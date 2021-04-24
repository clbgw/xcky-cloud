package com.xcky.util;


import com.xcky.model.entity.TreeEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 树类的工具类
 *
 * @param <T extends TreeEntity>
 * @author lbchen
 */
public class TreeUtil<T extends TreeEntity<T>> {

    /**
     * 根据扁平化单层的菜单列表转化成深度的菜单树（列表转树）
     *
     * @param list
     * @return
     */
    public List<T> getTreeByList(List<T> list) {
        if (null == list || list.size() < 1) {
            return new ArrayList<>();
        }
        Map<Integer, T> entityMap = new HashMap<>();
        for (T t : list) {
            if (null == t) {
                continue;
            }
            entityMap.put(t.getId(), t);
        }
        List<T> resultList = new ArrayList<>();
        for (T t : list) {
            if (null == t) {
                continue;
            }
            Integer pid = t.getPid();
            if (entityMap.containsKey(pid)) {
                List<T> tlist = entityMap.get(pid).getList();
                if (null == tlist) {
                    entityMap.get(pid).setList(new ArrayList<>());
                }
                entityMap.get(pid).getList().add(t);
            } else {
                resultList.add(t);
            }
        }
        return resultList;
    }

}
