package com.mzl.incomeexpensemanagesystem.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;

/**
 * @ClassName :   MysqlGenerator
 * @Description: 自定义的MysqlGenerator自动生成类
 * @Author: v_ktlema
 * @CreateDate: 2021/12/22 17:31
 * @Version: 1.0
 */
public class MysqlGenerator extends SuperGenerator {

    /**
     * <p>
     * MySQL generator
     * </p>
     */
    public void generator(String author, SeniorityEnum seniority, String jdbcDriverName, String jdbcUsername,
                          String jdbcPassword, String jdbcUrl, String tablePrefix, String... tableName) {

        // 代码生成器
        AutoGenerator mpg = getAutoGenerator(author, seniority, jdbcDriverName, jdbcUsername, jdbcPassword, jdbcUrl, tablePrefix, tableName);
        mpg.execute();
        if (tableName == null) {
            System.err.println(" Generator Success !");
        } else {
            for (int i = 0; i < tableName.length; i++) {
                System.err.println(" TableName【 " + tableName[i] + " 】" + "Generator Success !");
            }
        }
    }

}
