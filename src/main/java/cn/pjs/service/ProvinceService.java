package cn.pjs.service;

import cn.pjs.dao.IProvince;
import cn.pjs.entity.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author pjs
 * @create 2020-10-19   15:44
 */
@Service
public class ProvinceService {
    private IProvince provinceDao;

    @Autowired
    public void setProvinceDao(IProvince provinceDao) {
        this.provinceDao = provinceDao;
    }

    public ArrayList<Province> findAll() {
        return provinceDao.findAll();
    }

    public int findByName(String name) {
        return provinceDao.findByName(name);
    }

}
