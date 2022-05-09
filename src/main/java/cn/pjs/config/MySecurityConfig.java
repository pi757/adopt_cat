package cn.pjs.config;


import cn.pjs.entity.User;
import cn.pjs.util.ChangeImage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pjs
 * @create 2020-11-29   15:42
 */
@Configuration
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
    UserDetailsService userService;

    ChangeImage changeImage;

    @Autowired
    public void setChangeImage(ChangeImage changeImage) {
        this.changeImage = changeImage;
    }

    @Autowired
    public void setUserService(UserDetailsService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());

    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .exceptionHandling().accessDeniedPage("");//设置403页面
        http
                .formLogin()
                .loginPage("/l1/toLogin")//设置登录页面
                .loginProcessingUrl("/l1/login")//访问登录页面url
//                .failureUrl("/l1/toLogin")//设置失败了访问路径
//                .defaultSuccessUrl("/")//登录成功跳转
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        User login = (User) authentication.getPrincipal();
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        httpServletResponse.setStatus(200);
                        Map<String, Object> map = new HashMap<>(16);
                        map.put("status", 200);
                        if (login.getMode() == 0) {
                            String avatar = changeImage.download(String.valueOf(login.getUserId()), "avatar", login.getUserImage(), false);
                            login.setUserImage(avatar);
                            map.put("user", login);
                            map.put("flag", 0);//正常

                        } else {
                            map.put("flag", 1);//黑名单
                        }
                        ObjectMapper om = new ObjectMapper();
                        out.write(om.writeValueAsString(map));
                        out.flush();
                        out.close();
                        System.out.println("登录成功");
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        Map<String, Object> map = new HashMap<>(16);
                        map.put("flag", 2);//没有这个人
                        ObjectMapper om = new ObjectMapper();
                        out.write(om.writeValueAsString(map));
                        out.flush();
                        out.close();
                        System.out.println("登录失败");
                    }
                })
                .and().logout()//退出注销
                .logoutUrl("/logout")//退出的请求
                .logoutSuccessUrl("/")//退出成功后返回哪里
                .permitAll()
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        System.out.println("注销成功");
                        httpServletResponse.sendRedirect("/");
                    }
                })
                .and().authorizeRequests()//设置权限
                .antMatchers("/error/**", "/", "/l1/**", "/static/**", "/static/favicon.ico").permitAll()//所有人都可以访问的资源
                .antMatchers("/l3/**").hasRole("admin")//admin可以访问的资源
                .antMatchers("/l2/**").hasAnyRole("user", "admin")//l2哪些可以访问
                .anyRequest().authenticated()//拦截所有请求
                .and().csrf().disable();//关闭csrf防护
    }
}



