package com.mzl.incomeexpensemanagesystem.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.RateLimiter;
import com.mzl.incomeexpensemanagesystem.annotation.LoginRateLimit;
import com.mzl.incomeexpensemanagesystem.enums.RetCodeEnum;
import com.mzl.incomeexpensemanagesystem.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName :   LoginRateAspect
 * @Description: 登录限流的横向切面
 * @Author: v_ktlema
 * @CreateDate: 2021/12/24 10:33
 * @Version: 1.0
 */
@Aspect
@Component
@Slf4j
public class LoginRateAspect {

    //用ConcurrentHashMap来解决限流并发冲突问题，用来存放不同接口的RateLimiter(key为接口方法名称，value为RateLimiter)
    private ConcurrentHashMap<String, RateLimiter> map = new ConcurrentHashMap<>();

    //谷歌的guava限流工具类
    private RateLimiter rateLimiter;

    /**
     * 这里我们使用注解的形式
     * 当然，我们也可以通过切点表达式直接指定需要拦截的package,需要拦截的class 以及 method
     * 切点表达式:   execution(...)
     */
    @Pointcut("@annotation(com.mzl.incomeexpensemanagesystem.annotation.LoginRateLimit)")
    public void limitPointCut() {

    }

    /**
     * 方法前执行
     * @param joinPoint
     */
    @Before("limitPointCut()")
    public void doBefore(JoinPoint joinPoint){
//        System.out.println("doBefore");
    }

    /**
     * 方法后执行
     * @param joinPoint
     */
    @After("limitPointCut()")
    public void doAfter(JoinPoint joinPoint){
//        System.out.println("doAfter");
    }

    /**
     * 返回后执行
     * @param joinPoint
     */
    @AfterReturning("limitPointCut()")
    public void doAfterReturning(JoinPoint joinPoint){
//        System.out.println("doAfterReturning");
    }

    /**
     * 抛出异常执行
     * @param joinPoint
     */
    @AfterThrowing("limitPointCut()")
    public void deAfterThrowing(JoinPoint joinPoint){
//        System.out.println("deAfterThrowing");
    }

    /**
     * 环绕通知 @Around， 当然也可以使用 @Before (前置通知) + 方法执行 + @After (后置通知)
     * @param
     * @return
     * @throws Throwable
     */
    @Around("limitPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object obj = null;
        //获取拦截的方法名
        Signature sig = joinPoint.getSignature();
        //获取拦截的方法名
        MethodSignature msig = (MethodSignature) sig;
        //返回被织入增加处理目标对象
        Object target = joinPoint.getTarget();
        //为了获取注解信息
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        //获取注解信息
        LoginRateLimit annotation = currentMethod.getAnnotation(LoginRateLimit.class);
        double limitNum = annotation.limitNum(); //获取注解每秒加入桶中的token
        String functionName = msig.getName(); // 注解所在方法名区分不同的限流策略

        //获取rateLimiter(底层是漏桶算法)，(key为接口方法名称，value为RateLimiter)
        if(map.containsKey(functionName)){
            rateLimiter = map.get(functionName);
        }else {
            map.put(functionName, RateLimiter.create(limitNum));
            rateLimiter = map.get(functionName);
        }

        if (rateLimiter.tryAcquire()) {
            //执行方法，允许放行
            obj = joinPoint.proceed();
        } else {
            //拒绝了请求（服务降级）
            log.info("限流工具拒绝了请求");
            throw new CustomException(RetCodeEnum.REFUSE_REQUEST);
        }

        return obj;
    }

}
