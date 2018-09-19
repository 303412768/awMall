package com.wen.mall;

import com.wen.mall.file.entity.FileStorageProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author jackson
 */
@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
@MapperScan("com.wen.mall.**.mapper")
public class MallApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallApplication.class, args);
    }
}
