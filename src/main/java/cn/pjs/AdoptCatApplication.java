package cn.pjs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(value = "cn.pjs.dao")
@SpringBootApplication
public class AdoptCatApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdoptCatApplication.class, args);
    }

}
