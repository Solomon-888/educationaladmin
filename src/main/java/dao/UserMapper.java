package dao;

import entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    @Select("select *from s_user where u_id = #{id}")
    User getUserById(@Param("id") String id);
}
