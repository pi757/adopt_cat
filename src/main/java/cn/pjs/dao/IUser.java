package cn.pjs.dao;

import cn.pjs.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pjs
 * @create 2020-10-09   14:37
 */
@Repository
@Mapper
public interface IUser {
    //查找所有
    @Select("select * from user")
    @Results(id = "userMap", value = {
            @Result(column = "user_id", property = "userId", id = true),
            @Result(column = "user_name", property = "userName"),
            @Result(column = "user_pwd", property = "userPwd"),
            @Result(column = "user_date", property = "userDate"),
            @Result(column = "user_birthday", property = "userBirthday"),
            @Result(column = "user_age", property = "userAge"),
            @Result(column = "user_image", property = "userImage"),
            @Result(column = "user_mode", property = "mode"),
            @Result(column = "user_role", property = "role"),
    })
    ArrayList<User> findAllUser();

    //根据id查找
    @ResultMap("userMap")
    @Select("select * from user where user_id = #{id}")
    User findById(long id);

    //根据id删除
    @Delete("delete from user where user_id = #{userId}")
    int deleteUserById(long user);

    //根据id更新信息
    @Update("update user set  user_birthday = #{userBirthday} ,user_name = #{userName} where user_id = #{userId}")
    int updateUserById(User user);

    //根据id改密码
    @Update("update user set  user_pwd = #{newPwd} where user_id = #{userId} and user_pwd = #{oldPwd}")
    int updateUserPwdById(String userId, String oldPwd, String newPwd);

    //插入用户
    @Insert("insert into user(user_id,user_name,user_pwd,user_birthday,user_image) values(#{userId},#{userName},#{userPwd},#{userBirthday},#{userImage})")
    int insertUser(User user);

    //插入管理员
    @Insert("insert into user(user_id,user_name,user_pwd,user_birthday,user_image,user_role) values(#{userId},#{userName},#{userPwd},#{userBirthday},#{userImage},#{role})")
    int insertAdmin(User user);

    //查找最大的id
    @Select("select MAX(user_id) from user")
    int findMaxId();

    //登录
    @ResultMap("userMap")
    @Select("select * from user where user_id = #{userId} and user_pwd = #{userPwd}")
    User login(User user);

    //根据id更新状态
    @Update("update user set  user_mode = #{mode} where user_id = #{userId}")
    int updateUserMode(long userId, int mode);

}
