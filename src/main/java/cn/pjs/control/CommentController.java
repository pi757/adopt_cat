package cn.pjs.control;

import cn.pjs.entity.CommentParent;
import cn.pjs.entity.CommentSon;
import cn.pjs.service.CommentParentService;
import cn.pjs.util.ChangeImage;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author pjs
 * @create 2020-11-06   19:46
 */
@RestController
public class CommentController {
    private CommentParentService commentParentService;
    private ChangeImage changeImage;

    @Autowired
    public void setChangeImage(ChangeImage changeImage) {
        this.changeImage = changeImage;
    }

    @Autowired
    public void setCommentParentService(CommentParentService commentParentService) {
        this.commentParentService = commentParentService;
    }

    //添加父评论
    @PostMapping("/l2/addCommentParent")
    public CommentParent addParent(@RequestBody CommentParent commentParent) {
        String id = commentParentService.findParentMaxId();
        if (id == null || id.length() == 0) {
            id = "CP1";
        } else {
            int oldId = Integer.parseInt(id.substring(2));
            id = "CP" + (oldId + 1);
        }
        commentParent.setCommentId(id);
        if (commentParentService.addParent(commentParent)) {
            return commentParent;
        } else {
            return null;
        }
    }

    //添加子评论
    @PostMapping("/l2/addCommentSon")
    public CommentSon addSon(@RequestBody CommentSon commentSon) {
        String id = commentParentService.findSonMaxId();
        if (id == null || id.length() == 0) {
            id = "CS1";
        } else {
            int oldId = Integer.parseInt(id.substring(2));
            id = "CS" + (oldId + 1);
        }
        commentSon.setCommentId(id);
        if (commentParentService.addSon(commentSon)) {
            return commentSon;
        } else {
            return null;
        }
    }

    //根据id获取评论
    @GetMapping("/l1/comment")
    public PageInfo findByArticle(String articleId, int currentPage, int pageSize) {
        Page<CommentParent> page = new Page<>();
        PageInfo<CommentParent> source = PageHelper.startPage(currentPage,
                pageSize,
                page.getOrderBy())
                .doSelectPageInfo(() -> {
                    // 查询
                    commentParentService.findByArticle(articleId);
                });
        //需要转换的对象
        PageInfo<CommentParent> target = new PageInfo<>();
        //复制分页属性
        BeanUtils.copyProperties(source, target);
        //转换属性进行二次操作
        List<CommentParent> allComment = source.getList().stream().collect(Collectors.toList());
        //把服务器图片地址下周到本地
        HashSet<String> set = new HashSet<>();
        for (CommentParent commentParent : allComment) {
            String id = String.valueOf(commentParent.getUser().getUserId());
            if (!set.contains(id)) {
                String avatar = changeImage.download(id, "avatar", commentParent.getUser().getUserImage(), false);
                commentParent.getUser().setUserImage(avatar);
                set.add(id);
            }
        }
        //添加回去
        target.setList(allComment);
        return target;
    }

    //点赞
    @PutMapping("/l2/increase")
    public boolean increase(@RequestBody HashMap<String, String> map) {

        return commentParentService.like(map, true);
    }

    //取消点赞
    @PutMapping("/l2/decrease")
    public boolean decrease(@RequestBody HashMap<String, String> map) {
        return commentParentService.like(map, false);
    }

}
