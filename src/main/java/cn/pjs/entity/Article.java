package cn.pjs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.sql.Date;
import java.time.LocalDateTime;

/**
 * @author pjs
 * @create 2020-10-09   13:45
 */
@Data
@Alias("articleMap")
public class Article {

    public String articleId;//文章id
    public long userId;//作者id
    public User user;
    public String articleTitle;//文章标题
    public String articleContent;//文章内容
    public int articleCommentCount;//文章下面评论总数
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime articleDate;//文章发布时间
    public int check;//是否审核

}
