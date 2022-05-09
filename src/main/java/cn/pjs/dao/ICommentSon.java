package cn.pjs.dao;

import cn.pjs.entity.CommentSon;
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
public interface ICommentSon {
    @Select("select * from comment_son where parent_id = #{id}")
    @Results(id = "commentParentMap", value = {
            @Result(column = "comment_id", property = "commentId", id = true),
            @Result(column = "from_id", property = "fromUser", one = @One(select = "cn.pjs.dao.IUser.findById", fetchType = FetchType.EAGER)),
            @Result(column = "to_id", property = "toUser", one = @One(select = "cn.pjs.dao.IUser.findById", fetchType = FetchType.EAGER)),
            @Result(column = "comment_content", property = "commentContent"),
            @Result(column = "comment_date", property = "commentDate"),
    })
    ArrayList<CommentSon> findById(String articleId);

    @Insert("insert into comment_son(comment_id,parent_id,from_id,to_id,comment_content) " +
            "values(#{commentId},#{parentId},#{fromId},#{toId},#{commentContent})")
    int add(CommentSon commentSon);

    @Select("SELECT MAX(comment_id) from comment_son  where LENGTH(comment_id) = (SELECT MAX(LENGTH(comment_id))  from comment_son)")
    String findMaxId();
}
