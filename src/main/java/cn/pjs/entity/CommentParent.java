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
import java.util.ArrayList;

/**
 * @author pjs
 * @create 2020-10-09   13:45
 */
@Data
@Alias("commentParentMap")
public class CommentParent {
    private String commentId;//评论id
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime commentDate;//评论时间
    private String articleId;//文章id
    private String userId;//用户id
    private User user;
    private String commentContent;//评论内容
    private int likeNum;//点赞数
    private ArrayList<CommentSon> reply;//一对多 子评论
}
