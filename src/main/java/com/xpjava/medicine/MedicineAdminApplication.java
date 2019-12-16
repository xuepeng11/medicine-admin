package com.xpjava.medicine;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.xpjava.medicine.mapper")
public class MedicineAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicineAdminApplication.class, args);
    }

    //修改了
    //qweqwe


}
