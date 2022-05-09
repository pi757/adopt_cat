package cn.pjs.dao;

import cn.pjs.entity.CommentParent;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * @author pjs
 * @create 2020-11-06   18:56
 */
@Repository
@Mapper
public interface ICommentParent {
    @Select("select * from comment_parent where article_id = #{id}")
    @Results(id = "commentParentMap", value = {
            @Result(column = "comment_id", property = "commentId", id = true),
            @Result(column = "article_id", property = "articleId"),
            @Result(column = "user_id", property = "user", one = @One(select = "cn.pjs.dao.IUser.findById", fetchType = FetchType.EAGER)),
            @Result(column = "comment_content", property = "commentContent"),
            @Result(column = "like_num", property = "likeNum"),
            @Result(column = "comment_date", property = "commentDate"),
            @Result(column = "comment_id", property = "reply", many = @Many(select = "cn.pjs.dao.ICommentSon.findById", fetchType = FetchType.EAGER)),
    })
    ArrayList<CommentParent> findById(String articleId);

    @Insert("insert into comment_parent(comment_id,article_id,user_id,comment_content,like_num) " +
            "values(#{commentId},#{articleId},#{userId},#{commentContent},#{likeNum})")
    int add(CommentParent commentParent);

    @Select("SELECT MAX(comment_id) from comment_parent  where LENGTH(comment_id) = (SELECT MAX(LENGTH(comment_id))  from comment_parent)")
    String findMaxId();

    @Delete("delete from comment_parent where article_id=#{articleId}")
    int deleteById(String articleId);

    @Select("select count(*) from comment_parent where article_id = #{articleId}")
    int commentCountById(String articleId);

    @Update("update comment_parent set like_num = like_num +1 where comment_id = #{commentId}")
    int increase(String commentId);

    @Update("update comment_parent set like_num = like_num -1 where comment_id = #{commentId}")
    int decrease(String commentId);
}
