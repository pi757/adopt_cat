package cn.pjs.dao;

import cn.pjs.entity.Province;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

/**
 * @author pjs
 * @create 2020-10-19   15:13
 */
@Mapper
@Repository
public interface IProvince {
    @Select("select * from province")
    @Results(id = "provinceMap", value = {
            @Result(column = "province_id", property = "provinceId", id = true),
            @Result(column = "province_name", property = "provinceName"),
            @Result(column = "parent_province_id", property = "parentProvinceId"),
    })
    ArrayList<Province> findAll();

    @ResultMap("provinceMap")
    @Select("select * from province where province_id = #{id}")
    Province findById(int id);

    @Select("select province_id from province where province_name = #{name}")
    int findByName(String name);
}
