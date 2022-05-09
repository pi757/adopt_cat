package cn.pjs.service;

import cn.pjs.dao.ICommentParent;
import cn.pjs.dao.IFindCat;
import cn.pjs.dao.IProvince;
import cn.pjs.entity.FindCat;
import cn.pjs.entity.FindPeople;
import org.apache.ibatis.type.Alias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * @author pjs
 * @create 2020-10-24   16:02
 */
@Service
public class FindCatService {
    private IFindCat findCatDao;
    private ICommentParent commentParentDao;

    @Autowired
    public void setCommentParentDao(ICommentParent commentParentDao) {
        this.commentParentDao = commentParentDao;
    }

    @Autowired
    public void setFindCatDao(IFindCat findCatDao) {
        this.findCatDao = findCatDao;
    }

    public ArrayList<FindCat> findAll() {
        return findCatDao.findAll();
    }

    public boolean add(FindCat findCat) {
        return findCatDao.add(findCat) > 0;
    }

    public String findMaxId() {
        return findCatDao.findMaxId();
    }

    public ArrayList<FindCat> findCatByUserId(String userId) {
        return findCatDao.findByUserId(userId);
    }

    @Transactional
    public boolean deleteById(String articleId) {
        return findCatDao.deleteById(articleId) > 0 && commentParentDao.deleteById(articleId) > 0;
    }

    public ArrayList<FindCat> findCatAllByTitle(String param) {
        return findCatDao.findCatAllByTitle(param);
    }

    public ArrayList<FindCat> findCatAllByProvince(int param) {
        return findCatDao.findCatAllByProvince(param);
    }

    public ArrayList<FindCat> findCatAllByLocation(String param) {
        return findCatDao.findCatAllByLocation(param);
    }

    public ArrayList<FindCat> findAllCheck() {
        return findCatDao.findAllCheck();
    }

    public boolean isCheck(String articleId, int isCheck) {
        return findCatDao.pass(articleId, isCheck) > 0;
    }

    public int countBuId(long userId) {
        return findCatDao.countById(userId);
    }

    public int countNoPassById(long userId) {
        return findCatDao.countNoPassById(userId);
    }
}