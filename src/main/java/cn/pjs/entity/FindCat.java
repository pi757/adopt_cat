package cn.pjs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import java.sql.Date;
import java.time.LocalDate;

/**
 * @author pjs
 * @create 2020-10-09   13:47
 */
@Data
@Alias("findCatMap")
public class FindCat extends Article {
    private String catSex;//猫的性别
    private int catAge;//猫的年龄
    private String lostLocation;//丢失地址
    private String contact;//联系方式
    private String catDescription;//猫的描述
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate catLostDate;//丢失时间
    private String catImage;//猫的照片
    private int province;//省份
    private Province provinceName;//省份类


    @Override
    public String toString() {
        return "FindCat{" +
                "catSex='" + catSex + '\'' +
                ", catAge=" + catAge +
                ", lostLocation='" + lostLocation + '\'' +
                ", contact='" + contact + '\'' +
                ", catDescription='" + catDescription + '\'' +
                ", catLostDate=" + catLostDate +
                ", province=" + province +
                ", articleId=" + articleId +
                ", userId=" + userId +
                ", articleTitle='" + articleTitle + '\'' +
                ", articleContent='" + articleContent + '\'' +
                ", articleCommentCount=" + articleCommentCount +
                ", articleDate=" + articleDate +
                ", check=" + check +
                '}';
    }
}
