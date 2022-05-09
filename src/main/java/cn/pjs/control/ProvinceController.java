package cn.pjs.control;

import cn.pjs.entity.Province;
import cn.pjs.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author pjs
 * @create 2020-10-19   15:44
 */
@Controller
public class ProvinceController {
    private ProvinceService provinceService;

    @Autowired
    public void setProvinceService(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @ResponseBody
    @GetMapping("/l1/provinces")
    public ArrayList<Province> findAll() {
        return provinceService.findAll();
    }
}
