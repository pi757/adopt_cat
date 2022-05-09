package cn.pjs.dao;

import cn.pjs.entity.Article;
import cn.pjs.entity.FindCat;
import cn.pjs.entity.FindPeople;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * @author pjs
 * @create 2020-10-24   16:02
 */
@Repository
@Mapper
public interface IFindCat {
    //查找所有
    @Select("select * from find_cat where is_check = 1")
    @Results(id = "findCatMap", value = {
            @Result(column = "article_id", property = "articleId", id = true),
            @Result(column = "user_id", property = "user", one = @One(select = "cn.pjs.dao.IUser.findById", fetchType = FetchType.EAGER)),
            @Result(column = "article_title", property = "articleTitle"),
            @Result(column = "cat_description", property = "articleContent"),
            @Result(column = "article_id", property = "articleCommentCount", one = @One(select = "cn.pjs.dao.ICommentParent.commentCountById", fetchType = FetchType.EAGER)),
            @Result(column = "is_check", property = "check"),
            @Result(column = "cat_sex", property = "catSex"),
            @Result(column = "cat_age", property = "catAge"),
            @Result(column = "lost_location", property = "lostLocation"),
            @Result(column = "province", property = "provinceName", one = @One(select = "cn.pjs.dao.IProvince.findById", fetchType = FetchType.EAGER)),
            @Result(column = "contact", property = "contact"),
            @Result(column = "cat_lost_Date", property = "catLostDate"),
            @Result(column = "cat_image", property = "catImage"),
            @Result(column = "article_date", property = "articleDate"),
    })
    ArrayList<FindCat> findAll();

    //添加
    @Insert("insert into find_cat(article_id,user_id,article_title,cat_description,article_comment_count," +
            "is_check,cat_sex,cat_age,province,contact,lost_location,cat_image,cat_lost_date) " +
            "values(#{articleId},#{userId},#{articleTitle},#{articleContent},#{articleCommentCount},#{check}," +
            "#{catSex},#{catAge},#{province},#{contact},#{lostLocation},#{catImage},#{catLostDate})")
    int add(FindCat findCat);

    @Select("SELECT MAX(article_id) from find_cat  where LENGTH(article_id) = (SELECT MAX(LENGTH(article_id))  from find_cat)")
    String findMaxId();

    @ResultMap("findCatMap")
    @Select("select * from find_cat where user_id = #{userId}")
    ArrayList<FindCat> findByUserId(String userId);

    @Delete("delete from find_cat where article_id = #{articleId}")
    int deleteById(String articleId);

    @ResultMap("findCatMap")
    @Select("select * from find_cat where article_title like concat('%',#{param},'%') and is_check = 1")
    ArrayList<FindCat> findCatAllByTitle(String param);

    @ResultMap("findCatMap")
    @Select("select * from find_cat where province = #{param} and is_check = 1")
    ArrayList<FindCat> findCatAllByProvince(int param);

    @ResultMap("findCatMap")
    @Select("select * from find_cat where lost_location like concat('%',#{param},'%') and is_check = 1")
    ArrayList<FindCat> findCatAllByLocation(String param);

    //更新状态
    @Update("update find_cat set is_check = #{isCheck} where article_id = #{articleId}")
    int pass(String articleId, int isCheck);

    //查找所有未处理的
    @ResultMap("findCatMap")
    @Select("select * from find_cat where is_check = 0")
    ArrayList<FindCat> findAllCheck();

    @Select("select count(*) from find_cat where user_id = #{userId}")
    int countById(long userId);

    @Select("select count(*) from find_cat where user_id = #{userId} and is_check=2")
    int countNoPassById(long userId);
}
