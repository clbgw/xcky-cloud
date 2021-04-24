package com.xcky.service.impl;

import com.xcky.enums.ResponseEnum;
import com.xcky.exception.BizException;
import com.xcky.mapper.JlBasicInfoMapper;
import com.xcky.mapper.JlEducationMapper;
import com.xcky.mapper.JlProjectMapper;
import com.xcky.mapper.JlSkillMapper;
import com.xcky.mapper.JlWorkMapper;
import com.xcky.mapper.UserJlRelaMapper;
import com.xcky.model.entity.UserJlRela;
import com.xcky.model.req.JlBasicInfoUpdateReq;
import com.xcky.model.req.JlEduInfoUpdateReq;
import com.xcky.model.req.JlProjectInfoUpdateReq;
import com.xcky.model.req.JlSkillInfoUpdateReq;
import com.xcky.model.req.JlWorkInfoUpdateReq;
import com.xcky.model.vo.JlBasicInfoVo;
import com.xcky.model.vo.JlEducationVo;
import com.xcky.model.vo.JlInfoVo;
import com.xcky.model.vo.JlProjectVo;
import com.xcky.model.vo.JlSkillVo;
import com.xcky.model.vo.JlWorkVo;
import com.xcky.model.vo.UserJlRelaVo;
import com.xcky.service.JlInfoService;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 简历信息服务接口实现类
 *
 * @author lbchen
 */
@Service
public class JlInfoServiceImpl implements JlInfoService {
    @Autowired
    private JlBasicInfoMapper jlBasicInfoMapper;
    @Autowired
    private JlEducationMapper jlEducationMapper;
    @Autowired
    private JlProjectMapper jlProjectMapper;
    @Autowired
    private JlSkillMapper jlSkillMapper;
    @Autowired
    private JlWorkMapper jlWorkMapper;
    @Autowired
    private UserJlRelaMapper userJlRelaMapper;

    @Override
    public JlInfoVo getJlInfoByJlId(Long jlId) {
        // 获取简历基本信息
        JlBasicInfoVo jlBasicInfoVo = getJlBasicInfoByJlId(jlId);
        // 获取简历学历信息列表
        List<JlEducationVo> jlEducationVos = getJlEducationInfoByJlId(jlId);
        // 获取简历项目信息列表
        List<JlProjectVo> jlProjectVos = getJlProjectInfoByJlId(jlId);
        // 获取简历技能信息列表
        List<JlSkillVo> jlSkillVos = getJlSkillInfoByJlId(jlId);
        // 获取简历工作信息列表
        List<JlWorkVo> jlWorkVos = getJlWorkInfoByJlId(jlId);

        JlInfoVo jlInfoVo = new JlInfoVo();
        jlInfoVo.setJlBasicInfoVo(jlBasicInfoVo);
        jlInfoVo.setJlEducationVos(jlEducationVos);
        jlInfoVo.setJlProjectVos(jlProjectVos);
        jlInfoVo.setJlSkillVos(jlSkillVos);
        jlInfoVo.setJlWorkVos(jlWorkVos);
        return jlInfoVo;
    }


    @Override
    public Integer updateJlBasicInfo(JlBasicInfoUpdateReq jlBasicInfoUpdateReq) {
        Long jlId = jlBasicInfoUpdateReq.getJlId();
        if (null == jlId) {
            // 新增
            return jlBasicInfoMapper.insertBasicInfo(jlBasicInfoUpdateReq);
        }
        // 更新
        return jlBasicInfoMapper.updateBasicInfo(jlBasicInfoUpdateReq);
    }

    @Override
    public Integer updateJlSkillInfo(JlSkillInfoUpdateReq jlSkillInfoUpdateReq) {
        Long id = jlSkillInfoUpdateReq.getId();
        Long jlId = jlSkillInfoUpdateReq.getJlId();
        boolean isInsert = true;
        if (null != id) {
            List<JlSkillVo> list = jlSkillMapper.selectJlSkillVoByJlId(jlId);
            Long eduId;
            for (JlSkillVo jlSkillVo : list) {
                eduId = jlSkillVo.getId();
                if (id.equals(eduId)) {
                    isInsert = false;
                    break;
                }
            }
        }
        if (isInsert) {
            return jlSkillMapper.insertSkillInfo(jlSkillInfoUpdateReq);
        } else {
            return jlSkillMapper.updateSkillInfo(jlSkillInfoUpdateReq);
        }
    }

    @Override
    public Integer updateJlEduInfo(JlEduInfoUpdateReq jlEduInfoUpdateReq) {
        Long id = jlEduInfoUpdateReq.getId();
        Long jlId = jlEduInfoUpdateReq.getJlId();
        boolean isInsert = true;
        if (null != id) {
            List<JlEducationVo> list = jlEducationMapper.selectJlEducationVoByJlId(jlId);
            Long eduId;
            for (JlEducationVo jlEducationVo : list) {
                eduId = jlEducationVo.getId();
                if (id.equals(eduId)) {
                    isInsert = false;
                    break;
                }
            }
        }
        if (isInsert) {
            return jlEducationMapper.insertEduInfo(jlEduInfoUpdateReq);
        } else {
            return jlEducationMapper.updateEduInfo(jlEduInfoUpdateReq);
        }
    }

    @Override
    public Integer updateJlWorkInfo(JlWorkInfoUpdateReq jlWorkInfoUpdateReq) {
        Long id = jlWorkInfoUpdateReq.getId();
        Long jlId = jlWorkInfoUpdateReq.getJlId();
        boolean isInsert = true;
        if (null != id) {
            List<JlWorkVo> list = jlWorkMapper.selectJlWorkVoByJlId(jlId);
            Long eduId;
            for (JlWorkVo jlWorkVo : list) {
                eduId = jlWorkVo.getId();
                if (id.equals(eduId)) {
                    isInsert = false;
                    break;
                }
            }
        }
        if (isInsert) {
            return jlWorkMapper.insertWorkInfo(jlWorkInfoUpdateReq);
        } else {
            return jlWorkMapper.updateWorkInfo(jlWorkInfoUpdateReq);
        }
    }

    @Override
    public Integer updateJlProjectInfo(JlProjectInfoUpdateReq jlProjectInfoUpdateReq) {
        Long id = jlProjectInfoUpdateReq.getId();
        Long jlId = jlProjectInfoUpdateReq.getJlId();
        boolean isInsert = true;
        if (null != id) {
            List<JlProjectVo> list = jlProjectMapper.selectJlProjectVoByJlId(jlId);
            Long eduId;
            for (JlProjectVo jlProjectVo : list) {
                eduId = jlProjectVo.getId();
                if (id.equals(eduId)) {
                    isInsert = false;
                    break;
                }
            }
        }
        if (isInsert) {
            return jlProjectMapper.insertProjectInfo(jlProjectInfoUpdateReq);
        } else {
            return jlProjectMapper.updateProjectInfo(jlProjectInfoUpdateReq);
        }
    }


    @Override
    public JlBasicInfoVo getJlBasicInfoByJlId(Long jlId) {
        JlBasicInfoVo jlBasicInfoVo = jlBasicInfoMapper.selectJlBasicInfoVoByKey(jlId);
        if (null == jlBasicInfoVo) {
            throw new BizException(ResponseEnum.NO_DATA, null);
        }
        return jlBasicInfoVo;
    }

    @Override
    public List<JlSkillVo> getJlSkillInfoByJlId(Long jlId) {
        return jlSkillMapper.selectJlSkillVoByJlId(jlId);
    }

    @Override
    public List<JlEducationVo> getJlEducationInfoByJlId(Long jlId) {
        return jlEducationMapper.selectJlEducationVoByJlId(jlId);
    }

    @Override
    public List<JlWorkVo> getJlWorkInfoByJlId(Long jlId) {
        return jlWorkMapper.selectJlWorkVoByJlId(jlId);
    }

    @Override
    public List<JlProjectVo> getJlProjectInfoByJlId(Long jlId) {
        return jlProjectMapper.selectJlProjectVoByJlId(jlId);
    }

    @Override
    public Integer updateUserJlRela(Long userId, Long jlId, String openid, String status) {
        UserJlRela userJlRela = new UserJlRela();
        userJlRela.setUserId(userId);
        userJlRela.setJlId(jlId);
        userJlRela.setOpenid(openid);
        userJlRela.setStatus(status);

        Date nowDate = new Date();
        userJlRela.setUpdateTime(nowDate);

        Map<String, Object> map = new HashMap<>(4);
        map.put("openid", openid);
        map.put("jlId", jlId);
        boolean isInsert = false;
        List<UserJlRelaVo> list = userJlRelaMapper.selectUserJlRelaVoByMap(map);
        if (null == list || list.size() < 1) {
            isInsert = true;
        }
        if (isInsert) {
            userJlRela.setCreateTime(nowDate);
            return userJlRelaMapper.insertUserJlRela(userJlRela);
        } else {
            userJlRela.setId(list.get(0).getId());
            return userJlRelaMapper.updateUserJlRela(userJlRela);
        }
    }

    @Override
    public List<UserJlRelaVo> getUserJlRelaListByMap(Map<String, Object> map) {
        return userJlRelaMapper.selectUserJlRelaVoByMap(map);
    }
}
