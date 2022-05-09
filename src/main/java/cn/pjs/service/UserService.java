package cn.pjs.service;

import cn.pjs.dao.IUser;
import cn.pjs.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author pjs
 * @create 2020-10-16   13:45
 */
@Service
public class UserService implements UserDetailsService {
    private IUser userDao;

    @Autowired
    public void setUserDao(IUser userDao) {
        this.userDao = userDao;
    }

    //注册
    public boolean insert(User user) {
        int i = userDao.insertUser(user);
        return i > 0;
    }

    //注册管理员
    public boolean insertAdmin(User user) {
        int i = userDao.insertAdmin(user);
        return i > 0;
    }

    //查找所有
    public ArrayList<User> findAllUser() {
        return userDao.findAllUser();
    }

    //找最大的id
    public int findMaxId() {
        return userDao.findMaxId();
    }

    //登录
    public User login(User user) {
        return userDao.login(user);
    }

    //修改信息
    public boolean updateUserById(User user) {
        return userDao.updateUserById(user) > 0;
    }

    //修改密码
    public boolean updateUserPwdById(String userId, String oldPwd, String newPwd) {
        return userDao.updateUserPwdById(userId, oldPwd, newPwd) > 0;
    }

    //修改状态
    public boolean updateUserMode(long userId, int mode) {
        return userDao.updateUserMode(userId, mode) > 0;
    }

    //删除用户
    public boolean deleteUser(long userId) {
        return userDao.deleteUserById(userId) > 0;
    }

    //根据id查找用户 具体是权限管理
    public User findUserById(long userId) {
        return userDao.findById(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDao.findById(Long.parseLong(s));
        if (user == null) {
            throw new UsernameNotFoundException("该用户不存在！");
        }
        user.setRole(user.getRole());
        return user;
    }

}
