package cn.pjs.service;

import cn.pjs.dao.IArticle;
import cn.pjs.dao.ICommentParent;
import cn.pjs.entity.Article;
import cn.pjs.entity.FindCat;
import cn.pjs.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author pjs
 * @create 2020-10-24   18:51
 */
@Service
public class ArticleService {
    private IArticle articleDao;
    private ICommentParent commentParentDao;

    @Autowired
    public void setCommentParentDao(ICommentParent commentParentDao) {
        this.commentParentDao = commentParentDao;
    }

    @Autowired
    public void setArticleDao(IArticle articleDao) {
        this.articleDao = articleDao;
    }

    public ArrayList<Article> findAll() {

        return articleDao.findAll();
    }

    public boolean add(Article article) {
        return articleDao.addArticle(article) > 0;
    }

    public String findMaxId() {
        return articleDao.findMaxId();
    }

    public ArrayList<Article> findArticleByUserId(String userId) {
//        List<Object> temp = redisUtil.lGet("articleByUserId"+userId, 0, -1);
//        if (temp == null || temp.isEmpty()){
//            List<Object> byUserId = new ArrayList<>(articleDao.findByUserId(userId));
//            redisUtil.lSetList("articleByUserId"+userId,byUserId);
//        }
        return articleDao.findByUserId(userId);
    }

    @Transactional
    public boolean deleteById(String articleId) {
        return articleDao.deleteById(articleId) > 0 && commentParentDao.deleteById(articleId) > 0;
    }

    public ArrayList<Article> findByTitle(String param) {
        return articleDao.findArticleAllByTitle(param);
    }

    public ArrayList<Article> findAllCheck() {
        return articleDao.findAllCheck();
    }

    public boolean isCheck(String articleId, int isCheck) {
        return articleDao.pass(articleId, isCheck) > 0;
    }

    public int countBuId(long userId) {
        return articleDao.countById(userId);
    }

    public int countNoPassById(long userId) {
        return articleDao.countNoPassById(userId);
    }
}
