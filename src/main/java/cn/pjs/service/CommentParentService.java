package cn.pjs.service;

import cn.pjs.dao.ICommentParent;
import cn.pjs.dao.ICommentSon;
import cn.pjs.entity.CommentParent;
import cn.pjs.entity.CommentSon;
import cn.pjs.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author pjs
 * @create 2020-11-06   19:38
 */
@Service
public class CommentParentService {
    private ICommentParent commentParentDao;

    @Autowired
    public void setCommentParentDao(ICommentParent commentParentDao) {
        this.commentParentDao = commentParentDao;
    }

    private ICommentSon commentSonDao;

    @Autowired
    public void setCommentSonDao(ICommentSon commentSonDao) {
        this.commentSonDao = commentSonDao;
    }

    public boolean addSon(CommentSon commentSon) {
        return commentSonDao.add(commentSon) > 0;
    }

    public boolean addParent(CommentParent commentParent) {
        return commentParentDao.add(commentParent) > 0;
    }

    public ArrayList<CommentParent> findByArticle(String articleId) {
        return commentParentDao.findById(articleId);
    }

    public String findParentMaxId() {
        return commentParentDao.findMaxId();
    }

    public String findSonMaxId() {
        return commentSonDao.findMaxId();
    }

    public boolean like(HashMap<String, String> map, boolean isIncrease) {
        String commentId = map.get("commentId");
        String userId = map.get("userId");
        if (isIncrease) {
            int increase = commentParentDao.increase(commentId);
            return increase > 0;
        } else {
            return commentParentDao.decrease(commentId) > 0;
        }
    }
}
