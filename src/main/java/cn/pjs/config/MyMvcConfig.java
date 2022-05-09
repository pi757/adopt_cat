package cn.pjs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author pjs
 * @create 2020-10-09   15:33
 */
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    @Value("${images.baseImagePath}")
    private String SERVER_LOCATION;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("level1/index");
        registry.addViewController("/l1/toLogin").setViewName("level1/login");
        registry.addViewController("/l1/toRegisterUser").setViewName("level1/registerUser");
        registry.addViewController("/l2/toFindPeople").setViewName("level2/findPeople");
        registry.addViewController("/l2/toFindCat").setViewName("level2/findCat");
        registry.addViewController("/l2/toArticle").setViewName("level2/article");
        registry.addViewController("/l1/toLookCat").setViewName("level1/lookCat");
        registry.addViewController("/l1/toLookArticle").setViewName("level1/lookArticle");
        registry.addViewController("/l1/toShow").setViewName("level1/show");
        registry.addViewController("/l2/toMyArticle").setViewName("level2/myArticle");
        registry.addViewController("/l2/toUpdatePwd").setViewName("level2/updatePwd");
        registry.addViewController("/l2/toInfo").setViewName("level2/info");
        registry.addViewController("/l3/toCheck").setViewName("level3/check");
        registry.addViewController("/l3/toAllUser").setViewName("level3/allUser");

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/l1/static/images/**").addResourceLocations("file:" + SERVER_LOCATION);
        registry.addResourceHandler("/l2/static/images/**").addResourceLocations("file:" + SERVER_LOCATION);
        registry.addResourceHandler("/l3/static/images/**").addResourceLocations("file:" + SERVER_LOCATION);
        registry.addResourceHandler("/static/images/**").addResourceLocations("file:" + SERVER_LOCATION);
        registry.addResourceHandler("/static/favicon.ico").addResourceLocations("classpath:/favicon.ico");
    }

}
