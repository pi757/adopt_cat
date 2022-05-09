package cn.pjs.service;

import cn.pjs.dao.ICommentParent;
import cn.pjs.dao.IFindPeople;
import cn.pjs.entity.FindPeople;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Array;
import java.sql.Blob;
import java.util.ArrayList;

/**
 * @author pjs
 * @create 2020-10-23   10:50
 */
@Service
public class FindPeopleService {
    private IFindPeople findPeopleDao;
    private ICommentParent commentParentDao;

    @Autowired
    public void setCommentParentDao(ICommentParent commentParentDao) {
        this.commentParentDao = commentParentDao;
    }

    @Autowired
    public void setFindPeopleDao(IFindPeople findPeopleDao) {
        this.findPeopleDao = findPeopleDao;
    }

    public boolean add(FindPeople findPeople) {
        return findPeopleDao.addFindPeople(findPeople) > 0;
    }

    public ArrayList<FindPeople> findAll() {
        return findPeopleDao.findAll();
    }

    public ArrayList<FindPeople> findAllCheck() {
        return findPeopleDao.findAllCheck();
    }

    public String findMaxId() {
        return findPeopleDao.findMaxId();
    }

    public ArrayList<FindPeople> findPeopleByUserId(String userId) {
        return findPeopleDao.findByUserId(userId);
    }

    @Transactional
    public boolean deleteById(String articleId) {
        return findPeopleDao.deleteById(articleId) > 0 && commentParentDao.deleteById(articleId) > 0;
    }

    public ArrayList<FindPeople> findByProvince(int param) {
        return findPeopleDao.findByProvince(param);
    }

    public ArrayList<FindPeople> findByTitle(String param) {
        return findPeopleDao.findByTitle(param);
    }

    public boolean isCheck(String articleId, int isCheck) {
        return findPeopleDao.pass(articleId, isCheck) > 0;
    }

    public int countBuId(long userId) {
        return findPeopleDao.countById(userId);
    }

    public int countNoPassById(long userId) {
        return findPeopleDao.countNoPassById(userId);
    }
}
