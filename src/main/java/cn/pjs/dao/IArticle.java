package cn.pjs.dao;

import cn.pjs.entity.Article;
import cn.pjs.entity.FindCat;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * @author pjs
 * @create 2020-10-24   17:51
 */
@Repository
@Mapper
public interface IArticle {
    @Select("select * from article where is_check = 1")
    @Results(id = "articleMap", value = {
            @Result(column = "article_id", property = "articleId", id = true),
            @Result(column = "user_id", property = "user", one = @One(select = "cn.pjs.dao.IUser.findById", fetchType = FetchType.EAGER)),
            @Result(column = "article_title", property = "articleTitle"),
            @Result(column = "article_content", property = "articleContent"),
            @Result(column = "article_id", property = "articleCommentCount", one = @One(select = "cn.pjs.dao.ICommentParent.commentCountById", fetchType = FetchType.EAGER)),
            @Result(column = "is_check", property = "check"),
            @Result(column = "article_date", property = "articleDate"),
    })
    ArrayList<Article> findAll();

    //添加
    @Insert("insert into article(article_id,user_id,article_title,article_content,article_comment_count,is_check) " +
            "values(#{articleId},#{userId},#{articleTitle},#{articleContent},#{articleCommentCount},#{check})")
    int addArticle(Article article);

    @Select("SELECT MAX(article_id) from article  where LENGTH(article_id) = (SELECT MAX(LENGTH(article_id))  from article) ")
    String findMaxId();

    @ResultMap("articleMap")
    @Select("select * from article where user_id = #{userId}")
    ArrayList<Article> findByUserId(String userId);

    @Delete("delete from article where article_id = #{articleId}")
    int deleteById(String articleId);

    @ResultMap("articleMap")
    @Select("select * from article where article_title like concat('%',#{param},'%') and is_check = 1")
    ArrayList<Article> findArticleAllByTitle(String param);

    //更新状态
    @Update("update article set is_check = #{isCheck} where article_id = #{articleId}")
    int pass(String articleId, int isCheck);

    //查找所有未处理的
    @ResultMap("articleMap")
    @Select("select * from article where is_check = 0")
    ArrayList<Article> findAllCheck();

    @Select("select count(*) from article where user_id = #{userId}")
    int countById(long userId);

    @Select("select count(*) from article where user_id = #{userId} and is_check = 2")
    int countNoPassById(long userId);
}
