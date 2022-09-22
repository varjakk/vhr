package com.varjak.vhrlearn.mapper;

import com.varjak.vhrlearn.model.HrRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

public interface HrRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HrRole record);

    int insertSelective(HrRole record);

    HrRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HrRole record);

    int updateByPrimaryKey(HrRole record);

    void deleteByHrId(Integer hrid);

    int addHrRole(@Param("hrid") Integer hrid, @Param("rids") Integer[] rids);
}