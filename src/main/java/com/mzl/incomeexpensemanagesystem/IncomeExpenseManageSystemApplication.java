package com.mzl.incomeexpensemanagesystem;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableAdminServer  //开启监控可视化注解
public class IncomeExpenseManageSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(IncomeExpenseManageSystemApplication.class, args);
    }

}
