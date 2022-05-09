package cn.pjs.control;

import cn.pjs.entity.FindPeople;
import cn.pjs.service.FindPeopleService;
import cn.pjs.service.ProvinceService;
import cn.pjs.util.ChangeImage;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pjs
 * @create 2020-10-19   16:07
 */
@RestController
public class FindPeopleController {
    private FindPeopleService findPeopleService;
    private ChangeImage changeImage;
    private ProvinceService provinceService;

    @Autowired
    public void setProvinceService(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @Autowired
    public void setChangeImage(ChangeImage changeImage) {
        this.changeImage = changeImage;
    }

    @Autowired
    public void setFindPeopleService(FindPeopleService findPeopleService) {
        this.findPeopleService = findPeopleService;
    }

    //发布 需要登录
    @PostMapping("/l2/findPeople")
    public void add(FindPeople findPeople, @RequestParam("file") MultipartFile file) {
        String maxId = findPeopleService.findMaxId();
        String id;
        if (maxId == null || maxId.length() == 0) {
            id = "P1";
        } else {
            int oldId = Integer.parseInt(maxId.substring(1));
            id = "P" + (oldId + 1);
        }
        findPeople.articleId = id;
        String imageSrc = changeImage.upload(String.valueOf(findPeople.userId), findPeople.articleId, file);
        findPeople.setCatImage(imageSrc);
        findPeopleService.add(findPeople);
    }

    //分页获取
    @GetMapping("/l1/findPeopleAll")
    public PageInfo<FindPeople> findAll(int currentPage, int pageSize) {
        Page<FindPeople> page = new Page<>();
        PageInfo<FindPeople> source = PageHelper.startPage(currentPage,
                pageSize,
                page.getOrderBy())
                .doSelectPageInfo(() -> {
                    // 查询
                    findPeopleService.findAll();
                });
        //需要转换的对象
        PageInfo<FindPeople> target = new PageInfo<>();
        return myPage(page, source, target);
    }

    //不分页获取 管理员审核的
    @GetMapping("/l3/findPeopleAllCheck")
    public ArrayList<FindPeople> findAllCheck() {
        return findPeopleService.findAllCheck();
    }

    //管理员审核通过
    @PutMapping("/l3/isCheckFindPeople")
    public boolean pass(@RequestBody HashMap<String, String> map) {
        String articleId = map.get("articleId");
        int isCheck = Integer.parseInt(map.get("isCheck"));
        return findPeopleService.isCheck(articleId, isCheck);
    }

    //用户查看自己文章
    @GetMapping("/l2/findPeopleByUserId")
    public ArrayList<FindPeople> findPeopleByUserId(String userId) {
        return findPeopleService.findPeopleByUserId(userId);
    }

    //用户删除自己文章
    @DeleteMapping("/l2/deleteFindPeople")
    public boolean deleteFindPeople(long userId, String articleId) {
        if (changeImage.deleteArticle(String.valueOf(userId), articleId)) {
            return findPeopleService.deleteById(articleId);

        }
        return false;
    }

    //搜索
    @GetMapping("/l1/findPeopleAllByTitle")
    public PageInfo<FindPeople> findPeopleByTitle(String param, int currentPage, int pageSize) {
        Page<FindPeople> page = new Page<>();
        PageInfo<FindPeople> source = PageHelper.startPage(currentPage,
                pageSize,
                page.getOrderBy())
                .doSelectPageInfo(() -> {
                    // 查询
                    findPeopleService.findByTitle(param);
                });
        //需要转换的对象
        PageInfo<FindPeople> target = new PageInfo<>();
        return myPage(page, source, target);
    }

    //搜索
    @GetMapping("/l1/findPeopleAllByProvince")
    public PageInfo<FindPeople> findPeopleByProvince(String param, int currentPage, int pageSize) {
        int provinceId = provinceService.findByName(param);
        Page<FindPeople> page = new Page<>();
        PageInfo<FindPeople> source = PageHelper.startPage(currentPage,
                pageSize,
                page.getOrderBy())
                .doSelectPageInfo(() -> {
                    // 查询
                    findPeopleService.findByProvince(provinceId);
                });
        //需要转换的对象
        PageInfo<FindPeople> target = new PageInfo<>();
        return myPage(page, source, target);
    }

    public PageInfo<FindPeople> myPage(Page<FindPeople> page, PageInfo<FindPeople> source, PageInfo<FindPeople> target) {

        //复制分页属性
        BeanUtils.copyProperties(source, target);
        //转换属性进行二次操作
        List<FindPeople> allFindPeople = source.getList().stream().collect(Collectors.toList());
        //把服务器图片地址下周到本地
        for (FindPeople findPeople : allFindPeople) {
            String id = String.valueOf(findPeople.getUser().getUserId());
            String articleId = findPeople.getArticleId();
            String avatar = changeImage.download(id, articleId, findPeople.getCatImage(), false);
            findPeople.setCatImage(avatar);
        }
        //添加回去
        target.setList(allFindPeople);
        return target;
    }
}
