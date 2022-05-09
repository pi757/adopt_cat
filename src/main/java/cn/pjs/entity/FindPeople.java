package cn.pjs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.ibatis.type.Alias;

/**
 * @author pjs
 * @create 2020-10-09   13:46
 */
@Data
@Alias("findPeopleMap")
public class FindPeople extends Article {
    private String catSex;//猫的性别
    private int catAge;//猫的年龄
    private int province;//省份的id
    private Province provinceName;//省份类
    private String contact;//联系方式
    private String claim;//领养要求
    private String catImage;//猫咪照片

    @Override
    public String toString() {
        return "FindPeople{" +
                "catSex='" + catSex + '\'' +
                ", catAge=" + catAge +
                ", province=" + province +
                ", contact='" + contact + '\'' +
                ", claim='" + claim + '\'' +
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
