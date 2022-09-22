package com.varjak.vhrlearn.mapper;

import com.varjak.vhrlearn.model.MenuRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

public interface MenuRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MenuRole record);

    int insertSelective(MenuRole record);

    MenuRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MenuRole record);

    int updateByPrimaryKey(MenuRole record);

    Integer insertMenuRoleById(@Param("rid") Integer rid, @Param("mids") Integer[] mids);

    void deleteByRid(Integer rid);
}