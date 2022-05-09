package cn.pjs.control;

import cn.pjs.entity.User;
import cn.pjs.service.ArticleService;
import cn.pjs.service.FindCatService;
import cn.pjs.service.FindPeopleService;
import cn.pjs.service.UserService;
import cn.pjs.util.ChangeImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author pjs
 * @create 2020-10-16   13:54
 */
@RestController
public class UserController {
    private UserService userService;
    private ChangeImage changeImage;
    private ArticleService articleService;
    private FindPeopleService findPeopleService;
    private FindCatService findCatService;
    PasswordEncoder password;

    @Autowired
    public void setPassword(PasswordEncoder password) {
        this.password = password;
    }

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Autowired
    public void setFindPeopleService(FindPeopleService findPeopleService) {
        this.findPeopleService = findPeopleService;
    }

    @Autowired
    public void setFindCatService(cn.pjs.service.FindCatService findCatService) {
        this.findCatService = findCatService;
    }

    @Autowired
    public void setChangeImage(ChangeImage changeImage) {
        this.changeImage = changeImage;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    //注册用户
    @PostMapping("/l1/addUser")
    public HashMap<String, Object> add(User user, @RequestParam("file") MultipartFile file) {
        int id;
        boolean result;
        user.setUserPwd(password.encode(user.getPassword()));
        if (userService.findAllUser().isEmpty()) {
            id = 2020000001;
            user.setUserId(id);
            user.setRole("admin");
            String imageSrc = changeImage.upload(String.valueOf(user.getUserId()), "avatar", file);
            user.setUserImage(imageSrc);
            result = userService.insertAdmin(user);
        } else {
            id = userService.findMaxId() + 1;
            user.setUserId(id);
            String imageSrc = changeImage.upload(String.valueOf(user.getUserId()), "avatar", file);
            user.setUserImage(imageSrc);
            result = userService.insert(user);
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", id);
        map.put("flag", result);
        return map;
    }

    //用户登录
//    @PostMapping("/l1/login")
//    public HashMap<String, Object> login(@RequestBody User user){
//        User login = userService.login(user);
//        HashMap<String, Object> map = new HashMap<>();
//        if (login != null){
//            if (login.getMode() == 0) {
//                String avatar = changeImage.download(String.valueOf(login.getUserId()), "avatar", login.getUserImage(),false);
//                login.setUserImage(avatar);
//                map.put("user",login);
//                map.put("flag",0);//正常
//
//            }else {
//                map.put("flag", 1);//黑名单
//            }
//        } else {
//            map.put("flag",2);//没有此用户
//        }
//        return map;
//    }
    //修改用户信息，包含头像
    @PostMapping("/l2/updateUser")
    public User updateUserImage(User user, @RequestParam("file") MultipartFile file) {
        System.out.println("修改头像");
        System.out.println(user);
        String serviceImage = changeImage.upload(String.valueOf(user.getUserId()), "avatar", file);
        user.setUserImage(serviceImage);
        if (userService.updateUserById(user)) {
            String clientImage = changeImage.download(String.valueOf(user.getUserId()), "avatar", serviceImage, true);
            user.setUserImage(clientImage);
            System.out.println(user);
            return user;
        } else {
            return null;
        }
    }

    //修改用户信息，不包含头像
    @PutMapping("/l2/updateUser")
    public User updateUser(@RequestBody User user) {
        System.out.println("不修改头像");
        System.out.println(user);
        boolean b = userService.updateUserById(user);
        System.out.println(b);
        if (b) {
            return user;
        } else {
            return null;
        }
    }

    //修改密码
    @PutMapping("/l2/updatePwd")
    public boolean updatePwd(@RequestBody HashMap<String, HashMap<String, String>> map) {
        HashMap<String, String> user = map.get("pwd");
        String userId = user.get("userId");
        String oldPwd = user.get("oldPwd");
        String newPwd = user.get("newPwd");
        return userService.updateUserPwdById(userId, oldPwd, newPwd);
    }

    //查找所有user
    @GetMapping("/l3/findAllUser")
    public ArrayList<User> findAllUser() {

        ArrayList<User> allUser = userService.findAllUser();
        for (User user : allUser) {
            long userId = user.getUserId();
            user.setArticleAll(findPeopleService.countBuId(userId) +
                    findCatService.countBuId(userId) +
                    articleService.countBuId(userId));
            user.setArticleNoPass(findPeopleService.countNoPassById(userId) +
                    findCatService.countNoPassById(userId) +
                    articleService.countNoPassById(userId));
        }
        return allUser;
    }

    //黑名单操作
    @PutMapping("/l3/updateUserMode")
    public boolean updateUserMode(@RequestBody HashMap<String, String> map) {
        long userId = Long.parseLong(map.get("userId"));
        int mode = Integer.parseInt(map.get("mode"));
        return userService.updateUserMode(userId, mode);
    }

    //删除用户
    @DeleteMapping("/l3/deleteUser")
    public boolean deleteUser(long userId) {
        if (changeImage.deleteUser(String.valueOf(userId))) {
            return userService.deleteUser(userId);
        }
        return false;
    }
}

