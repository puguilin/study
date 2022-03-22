package com.guilin.studycode.config;

import com.guilin.studycode.entrity.SysLog;
import com.guilin.studycode.service.SysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

/**
 * @description: AOP的切面和切点
 * @author: puguilin
 * @date: 2022/3/22
 * @version: 1.0
 */

@Aspect
@Component
public class LogAsPect {

    private final static Logger log = org.slf4j.LoggerFactory.getLogger(LogAsPect.class);

    @Resource
    private SysLogService sysLogService;

    /**
     *
     * ==========================*********AOP的几个重要注解************==============================
     *
     *  1 @Aspect:这个注解表示将当前类视为一个切面类
     * 2 @Component：表示将当前类交由Spring管理。
     * 3 @Pointcut:切点表达式,定义我们的匹配规则，
     *  下边我们使用@Pointcut("@annotation(com.web.springbootaoplog.config.Log)")表示匹配带有我们自定义注解的方法。
     * 4 @Around:环绕通知，可以在目标方法执行前后执行一些操作，以及目标方法抛出异常时执行的操作。
     *
     * 其中result = point.proceed();这句话表示执行目标方法
     * ==========================*********AOP的几个重要注解************==============================
     */


    //表示匹配带有自定义注解的方法
    @Pointcut("@annotation(com.guilin.studycode.config.Log)")
    public void pointcut() {}

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) {
        Object result =null;
        long beginTime = System.currentTimeMillis();

        try {
            log.info("我在目标方法之前执行！");
            result = point.proceed();    // 这句话表示执行目标方法,
            long endTime = System.currentTimeMillis();
            insertLog(point,endTime-beginTime);
        } catch (Throwable e) {
            // TODO Auto-generated catch block
        }
        return result;
    }
    @Pointcut("execution(public * com.guilin.studycode.controller..*.*(..))")
    public void pointcutController() {
    }

    @Before("pointcutController()")
    public void around2(JoinPoint point) {
        //获取目标方法
        String methodNam = point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName();

        //获取方法参数
        String params = Arrays.toString(point.getArgs());

        log.info("get in {} ,参数是 params :{}",methodNam,params);
    }

    private void insertLog(ProceedingJoinPoint point ,long time) {
        MethodSignature signature = (MethodSignature)point.getSignature();
        Method method = signature.getMethod();
        SysLog sys_log = new SysLog();

        Log userAction = method.getAnnotation(Log.class);
        if (userAction != null) {
            // 注解上的描述
            sys_log.setUserAction(userAction.value());
        }

        // 请求的类名
        String className = point.getTarget().getClass().getName();
        // 请求的方法名
        String methodName = signature.getName();
        // 请求的方法参数值
        String args = Arrays.toString(point.getArgs());

        //从session中获取当前登陆人id
//      Long useride = (Long)SecurityUtils.getSubject().getSession().getAttribute("userid");

        String id = UUID.randomUUID().toString();
        Integer userid = 1;//应该从session中获取当前登录人的id，这里简单模拟下

        sys_log.setId(id);
        sys_log.setUserId(userid);

        sys_log.setCreateTime(new java.sql.Timestamp(new Date().getTime()));

        log.info("当前登陆人：{},类名:{},方法名:{},参数：{},执行时间：{}",userid, className, methodName, args, time);

        sysLogService.insertLog(sys_log);
    }
}
