package com.linmo.oj.common.aop;

import com.linmo.oj.common.utils.NetUtils;
import com.linmo.oj.model.syslog.SysLog;
import com.linmo.oj.model.user.vo.UserVo;
import com.linmo.oj.service.SysLogService;
import com.linmo.oj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * 日志 AOP
 **/
@Aspect
@Component
@Slf4j
public class LogInterceptor {

    @Resource
    private UserService userService;

    @Resource
    private SysLogService sysLogService;

    /**
     * 执行拦截
     */
    @Around("@annotation(logRecord)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, LogRecord logRecord) throws Throwable {
        // 获取请求路径
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
        // 获取操作内容
        String operation = logRecord.value();
        // 当前登录用户
        UserVo loginUser = userService.getLoginUser();
        String operator = loginUser.getUserAccount();
        // 获取请求路径
        String url = httpServletRequest.getRequestURI();
        // 获取IP地址
        String ip = NetUtils.getIpAddress(httpServletRequest);
        // 执行原方法
        Object result = joinPoint.proceed();
        SysLog sysLog = new SysLog();
        sysLog.setOperation(operation);
        sysLog.setOperator(operator);
        sysLog.setUrl(url);
        sysLog.setIp(ip);
        // 数据库记录日志
        sysLogService.save(sysLog);
        log.info("操作人：{}，操作：{}，请求路径：{}，IP地址：{}，执行结果：{}", operator, operation, url, ip, result);
        return result;
    }
}