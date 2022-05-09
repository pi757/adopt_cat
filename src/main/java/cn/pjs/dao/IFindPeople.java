package cn.pjs.dao;

import cn.pjs.entity.FindPeople;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * @author pjs
 * @create 2020-10-14   20:48
 */
@Repository
@Mapper
public interface IFindPeople {
    //查找所有
    @Select("select * from find_people where is_check = 1")
    @Results(id = "findPeopleMap", value = {
            @Result(column = "article_id", property = "articleId", id = true),
            @Result(column = "user_id", property = "user", one = @One(select = "cn.pjs.dao.IUser.findById", fetchType = FetchType.EAGER)),
            @Result(column = "article_title", property = "articleTitle"),
            @Result(column = "cat_description", property = "articleContent"),
            @Result(column = "article_id", property = "articleCommentCount", one = @One(select = "cn.pjs.dao.ICommentParent.commentCountById", fetchType = FetchType.EAGER)),
            @Result(column = "is_check", property = "check"),
            @Result(column = "cat_sex", property = "catSex"),
            @Result(column = "cat_age", property = "catAge"),
            @Result(column = "province", property = "provinceName", one = @One(select = "cn.pjs.dao.IProvince.findById", fetchType = FetchType.EAGER)),
            @Result(column = "contact", property = "contact"),
            @Result(column = "claim", property = "claim"),
            @Result(column = "cat_image", property = "catImage"),
            @Result(column = "article_date", property = "articleDate"),
    })
    ArrayList<FindPeople> findAll();

    @ResultMap("findPeopleMap")
    @Select("select * from find_people where is_check = 0")
    ArrayList<FindPeople> findAllCheck();

    //添加
    @Insert("insert into find_people(article_id,user_id,article_title,cat_description,article_comment_count," +
            "is_check,cat_sex,cat_age,province,contact,claim,cat_image) " +
            "values(#{articleId},#{userId},#{articleTitle},#{articleContent},#{articleCommentCount},#{check}," +
            "#{catSex},#{catAge},#{province},#{contact},#{claim},#{catImage})")
    int addFindPeople(FindPeople findPeople);

    //查找最大的id
    @Select("SELECT MAX(article_id) from find_people  where LENGTH(article_id) = (SELECT MAX(LENGTH(article_id))  from find_people)")
    String findMaxId();

    //更具用户id查找文章
    @ResultMap("findPeopleMap")
    @Select("select * from find_people where user_id = #{userId}")
    ArrayList<FindPeople> findByUserId(String userId);

    //根据标题
    @ResultMap("findPeopleMap")
    @Select("select * from find_people where article_title like concat('%',#{param},'%') and is_check = 1")
    ArrayList<FindPeople> findByTitle(String param);

    //根据省份
    @ResultMap("findPeopleMap")
    @Select("select * from find_people where province like concat('%',#{param},'%') and is_check = 1")
    ArrayList<FindPeople> findByProvince(int param);

    //根据文章id删除文章
    @Delete("delete from find_people where article_id = #{articleId}")
    int deleteById(String articleId);

    @Update("update find_people set is_check = #{isCheck} where article_id = #{articleId}")
    int pass(String articleId, int isCheck);

    @Select("select count(*) from find_people where user_id = #{userId}")
    int countById(long userId);

    @Select("select count(*) from find_people where user_id = #{userId} and is_check = 2")
    int countNoPassById(long userId);
}
