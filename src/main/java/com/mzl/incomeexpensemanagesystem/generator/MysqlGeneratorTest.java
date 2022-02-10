package com.mzl.incomeexpensemanagesystem.generator;

/**
 * @ClassName :   MysqlGeneratorTest
 * @Description: 运行生成类MysqlGeneratorTest
 * @Author: v_ktlema
 * @CreateDate: 2021/12/22 17:33
 * @Version: 1.0
 */
public class MysqlGeneratorTest {

    public static final String jdbcDriverName = "com.mysql.cj.jdbc.Driver";

    public static final String jdbcUsername = "root";

    public static final String jdbcPassword = "105293";

    public static final String jdbcUrl = "jdbc:mysql://localhost:3306/income_expense_manage?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false";

    public static void main(String[] args) {
        MysqlGenerator mysqlGenerator = new MysqlGenerator();
        mysqlGenerator.generator("v_ktlema", SeniorityEnum.MOGUL, jdbcDriverName, jdbcUsername,
                jdbcPassword, jdbcUrl, "", "feedback");
    }

}
