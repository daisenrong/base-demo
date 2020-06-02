package com.lazydsr.base.demo.basedemo.config.aspect;

import com.lazydsr.base.demo.basedemo.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;

/**
 * FileName : ControllerLogAspect
 * <p>
 * Description : Controller层日志切面
 *
 * @author : daisenrong
 * @version : 1.0-SNAPSHOT.0
 * @date : 2018/11/16 23:00
 */
@Aspect
@Slf4j
@Component
@Order(100)
public class ControllerLogAspect {
    @Pointcut("execution(public * com.lazydsr.*..*.controller..*.*(..))")
    public void controllerPoint() {}

    @Around("controllerPoint()")
    public Object doControllerAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = null;
        try {
            HttpServletRequest request =
                ((ServletRequestAttributes)Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                    .getRequest();
            Object[] params = joinPoint.getArgs();
            log.info("Controller层 className={}, methodName={}, params={},url={},ip={}",
                joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
                Arrays.toString(params), request.getRequestURL(), IpUtil.getIpAddr(request));
            result = joinPoint.proceed();
        } finally {
            log.info("Controller层 耗时={}(ms), className={}, methodName={}, result={}",
                System.currentTimeMillis() - startTime, joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), result);
        }
        return result;
    }
}
