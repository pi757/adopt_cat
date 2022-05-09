package cn.pjs.control;

import cn.pjs.dao.IProvince;
import cn.pjs.entity.FindCat;
import cn.pjs.entity.FindPeople;
import cn.pjs.service.FindCatService;
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
 * @create 2020-10-24   16:01
 */
@RestController
public class FindCatController {
    private FindCatService findCatService;
    private ChangeImage changeImage;
    private IProvince provinceDao;

    @Autowired
    public void setProvinceDao(IProvince provinceDao) {
        this.provinceDao = provinceDao;
    }

    @Autowired
    public void setChangeImage(ChangeImage changeImage) {
        this.changeImage = changeImage;
    }

    @Autowired
    public void setFindCatService(FindCatService findCatService) {
        this.findCatService = findCatService;
    }

    //分页获取 游客
    @GetMapping("/l1/findCatAll")
    public PageInfo<FindCat> findByArticle(int currentPage, int pageSize) {
        Page<FindCat> page = new Page<>();
        PageInfo<FindCat> source = PageHelper.startPage(currentPage,
                pageSize,
                page.getOrderBy())
                .doSelectPageInfo(() -> {
                    // 查询
                    findCatService.findAll();
                });
        //需要转换的对象
        PageInfo<FindCat> target = new PageInfo<>();
        return myPage(page, source, target);
    }

    //用户发布
    @PostMapping("/l2/findCat")
    public void add(FindCat findCat, @RequestParam("file") MultipartFile file) {
        String maxId = findCatService.findMaxId();
        String id;
        if (maxId == null || maxId.length() == 0) {
            id = "C1";
        } else {
            int oldId = Integer.parseInt(maxId.substring(1));
            id = "C" + (oldId + 1);
        }
        findCat.articleId = id;
        String imageSrc = changeImage.upload(String.valueOf(findCat.userId), findCat.articleId, file);
        findCat.setCatImage(imageSrc);
        findCatService.add(findCat);
    }

    //用户查看自己的文章
    @GetMapping("/l2/findCatByUserId")
    public ArrayList<FindCat> findCatsByUserId(String userId) {
        return findCatService.findCatByUserId(userId);
    }

    //用户删除
    @DeleteMapping("/l2/deleteFindCat")
    public boolean deleteFindCat(long userId, String articleId) {
        if (changeImage.deleteArticle(String.valueOf(userId), articleId)) {
            return findCatService.deleteById(articleId);
        }
        return false;
    }

    //根据标题分页 搜索
    @GetMapping("/l1/findCatAllByTitle")
    public PageInfo<FindCat> findCatAllByTitle(String param, int currentPage, int pageSize) {
        Page<FindCat> page = new Page<>();
        PageInfo<FindCat> source = PageHelper.startPage(currentPage,
                pageSize,
                page.getOrderBy())
                .doSelectPageInfo(() -> {
                    // 查询
                    ArrayList<FindCat> catAllByTitle = findCatService.findCatAllByTitle(param);
                    System.out.println(catAllByTitle);
                });
        //需要转换的对象
        PageInfo<FindCat> target = new PageInfo<>();
        return myPage(page, source, target);
    }

    //根据省份分页 搜索
    @GetMapping("/l1/findCatAllByProvince")
    public PageInfo<FindCat> findCatAllByProvince(String param, int currentPage, int pageSize) {
        int provinceId = provinceDao.findByName(param);
        Page<FindCat> page = new Page<>();
        PageInfo<FindCat> source = PageHelper.startPage(currentPage,
                pageSize,
                page.getOrderBy())
                .doSelectPageInfo(() -> {
                    // 查询
                    findCatService.findCatAllByProvince(provinceId);

                });
        //需要转换的对象
        PageInfo<FindCat> target = new PageInfo<>();
        return myPage(page, source, target);
    }

    //根据地址分页
    @GetMapping("/l1/findCatAllByLocation")
    public PageInfo<FindCat> findCatAllByLocation(String param, int currentPage, int pageSize) {
        Page<FindCat> page = new Page<>();
        PageInfo<FindCat> source = PageHelper.startPage(currentPage,
                pageSize,
                page.getOrderBy())
                .doSelectPageInfo(() -> {
                    // 查询
                    findCatService.findCatAllByLocation(param);
                });
        //需要转换的对象
        PageInfo<FindCat> target = new PageInfo<>();
        return myPage(page, source, target);
    }

    //不分页获取 所有要审核的
    @GetMapping("/l3/findCatAllCheck")
    public ArrayList<FindCat> findAllCheck() {
        return findCatService.findAllCheck();
    }

    @PutMapping("/l3/isCheckFindCat")
    public boolean pass(@RequestBody HashMap<String, String> map) {
        String articleId = map.get("articleId");
        int isCheck = Integer.parseInt(map.get("isCheck"));
        return findCatService.isCheck(articleId, isCheck);
    }

    //照片分页
    public PageInfo<FindCat> myPage(Page<FindCat> page, PageInfo<FindCat> source, PageInfo<FindCat> target) {
        //复制分页属性
        BeanUtils.copyProperties(source, target);
        //转换属性进行二次操作
        List<FindCat> allFindCat = source.getList().stream().collect(Collectors.toList());
        //把服务器图片地址下周到本地
        for (FindCat findCat : allFindCat) {
            String id = String.valueOf(findCat.getUser().getUserId());
            String articleId = findCat.getArticleId();
            String avatar = changeImage.download(id, articleId, findCat.getCatImage(), false);
            findCat.setCatImage(avatar);
        }
        //添加回去
        target.setList(allFindCat);
        return target;
    }

}
