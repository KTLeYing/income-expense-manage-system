package com.mzl.incomeexpensemanagesystem.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName :   SwaggerConfig
 * @Description: swagger配置类
 * @Author: v_ktlema
 * @CreateDate: 2021/12/22 15:50
 * @Version: 1.0
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
//只在dev环境生效
//@Profile("dev")
public class SwaggerConfig {

    @Bean
    public Docket commonDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("智能收支管理平台-普通业务区")
                .apiInfo(apiInfo()) //调用下面apiInfo()方法
                .select()
                //对所有该包下的Api进行监控，如果想要监控所有的话可以改成any()，配置swagger扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.mzl.incomeexpensemanagesystem.controller.common"))  //这个是必须的：指定你的controller包
                .paths(PathSelectors.any()) //过滤规则：swagger所有的访问都可以，否则被访问被限制了也会出现上述原因：这个我没有出现参考的别人：没测试过。
                .build()
                //给swagger文档设置全局登录
                .securityContexts(securityContexts())
                .securitySchemes(securitySchemes())
                //接口全局前缀
                //.pathMapping("");
                //或
                .pathMapping("/");
    }

    @Bean
    public Docket manageDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("智能收支管理平台-管理员区")
                .apiInfo(apiInfo()) //调用下面apiInfo()方法
                .select()
                //对所有该包下的Api进行监控，如果想要监控所有的话可以改成any()，配置swagger扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.mzl.incomeexpensemanagesystem.controller.manage"))  //这个是必须的：指定你的controller包
                .paths(PathSelectors.any()) //过滤规则：swagger所有的访问都可以，否则被访问被限制了也会出现上述原因：这个我没有出现参考的别人：没测试过。
                .build()
                //给swagger文档设置全局登录
                .securityContexts(securityContexts())
                .securitySchemes(securitySchemes())
                //接口全局前缀
                //.pathMapping("");
                //或
                .pathMapping("/");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("智能收支管理平台Swagger")
                .description("智能收支管理平台")
                .termsOfServiceUrl("http://localhost:8888/incomeExpense")
                .contact(new Contact("v_ktlema(马振乐);", "", "2198902814@qq.com"))
                .version("Beta")
                .license("MIT")
                .licenseUrl("")
                .build();
    }

    /**
     * @params: []
     * @return: java.util.List<springfox.documentation.service.ApiKey>
     * @author v_geekrchen
     * @description: 给swagger文档设置全局登录
     * @date: 2021/1/19 16:45
     */
    private List<ApiKey> securitySchemes(){
        List<ApiKey> result = new ArrayList<>();
        ApiKey apiKey = new ApiKey("Authorization", "Authorization", "Header");
        result.add(apiKey);
        return result;
    }


    private List<SecurityContext> securityContexts(){
        //设置需要登录认证的路径
        List<SecurityContext> result =  new ArrayList<>();
        result.add(getContextByPath("/hello/.*"));
        return result;
    }


    private SecurityContext getContextByPath(String pathRegex) {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(pathRegex))
                .build();
    }

    private List<SecurityReference> defaultAuth(){
        List<SecurityReference> result = new ArrayList<>();
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        result.add(new SecurityReference("Authorization", authorizationScopes));
        return result;
    }

}
