package cn.pjs.control;

import cn.pjs.util.ChangeImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author pjs
 * @create 2020-11-04   18:42
 */
@RestController
public class AllController {
    private ChangeImage changeImage;

    @Autowired
    public void setChangeImage(ChangeImage changeImage) {
        this.changeImage = changeImage;
    }

    //    点击详情下载照片
    @GetMapping("/l1/imageUrl")
    public String download(@RequestParam("userId") String userId, @RequestParam("articleId") String articleId,
                           @RequestParam("catImage") String catImage) {
        return changeImage.download(userId, articleId, catImage, false);
    }
}
