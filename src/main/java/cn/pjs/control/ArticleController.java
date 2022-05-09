package cn.pjs.control;

import cn.pjs.entity.Article;
import cn.pjs.service.ArticleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author pjs
 * @create 2020-10-24   19:01
 */
@RestController
public class ArticleController {
    private ArticleService articleService;

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    //分页获取 游客查看
    @GetMapping("/l1/articleAll")
    public PageInfo<Article> findByArticle(int currentPage, int pageSize) {
        Page<Article> page = new Page<>();
        PageInfo<Article> source = PageHelper.startPage(currentPage,
                pageSize,
                page.getOrderBy())
                .doSelectPageInfo(() -> {
                    // 查询
                    articleService.findAll();
                });
        //需要转换的对象
        PageInfo<Article> target = new PageInfo<>();
        //复制分页属性
        BeanUtils.copyProperties(source, target);
        return target;
    }

    //用户发布
    @PostMapping("/l2/article")
    public boolean add(@RequestBody Article article) {
        String maxId = articleService.findMaxId();
        String id;
        if (maxId == null || maxId.length() == 0) {
            id = "A1";
        } else {
            int oldId = Integer.parseInt(maxId.substring(1));
            id = "A" + (oldId + 1);
        }
        article.articleId = id;
        return articleService.add(article);

    }

    //用户查看自己的文章
    @GetMapping("/l2/findArticleByUserId")
    public ArrayList<Article> findArticleByUserId(String userId) {
        return articleService.findArticleByUserId(userId);
    }

    //用户删除自己的文章
    @DeleteMapping("/l2/deleteArticle")
    public boolean deleteArticle(String articleId) {
        return articleService.deleteById(articleId);
    }

    //搜索
    @GetMapping("/l1/articleByTitle")
    public PageInfo<Article> findArticleByTitle(String param, int currentPage, int pageSize) {
        Page<Article> page = new Page<>();
        PageInfo<Article> source = PageHelper.startPage(currentPage,
                pageSize,
                page.getOrderBy())
                .doSelectPageInfo(() -> {
                    // 查询
                    articleService.findByTitle(param);
                });
        //需要转换的对象
        PageInfo<Article> target = new PageInfo<>();
        //复制分页属性
        BeanUtils.copyProperties(source, target);
        return target;
    }

    //不分页获取 管理员要审核的
    @GetMapping("/l3/findArticleAllCheck")
    public ArrayList<Article> findAllCheck() {
        return articleService.findAllCheck();
    }

    //更改审核状态
    @PutMapping("/l3/isCheckFindArticle")
    public boolean pass(@RequestBody HashMap<String, String> map) {
        String articleId = map.get("articleId");
        int isCheck = Integer.parseInt(map.get("isCheck"));
        return articleService.isCheck(articleId, isCheck);
    }
}
