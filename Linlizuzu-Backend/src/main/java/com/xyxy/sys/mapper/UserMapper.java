package com.xyxy.sys.mapper;

import com.xyxy.sys.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xyxy.sys.entity.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 吴沈涛
 * @since 2023-05-15
 */
public interface UserMapper extends BaseMapper<User> {
    public List<String> getRoleNameByUserId(Integer userId);

    boolean updateByUsername(@Param("userVo") UserVo userVo);

    String getUserByName(String username);

    void changeBalance(@Param("changenum") Double changenum, @Param("username") String username);
    void changeBalanceAdd(@Param("changenum") Double changenum, @Param("username") String username);

    User getUserinfoByName(String username);

    void updateReputation(String username);
}
