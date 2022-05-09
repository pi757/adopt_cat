package cn.pjs.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;

/**
 * @author pjs
 * @create 2020-10-09   13:48
 */
@Data
@Alias("provinceMap")
public class Province {
    private int provinceId;//省份id
    private String provinceName;//省名
    private int parentProvinceId;//父级省份

}
