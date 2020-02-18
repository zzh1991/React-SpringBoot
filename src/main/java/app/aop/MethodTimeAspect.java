package app.aop;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhihao zhang
 * @since 2019-03-26
 */

@Aspect
@Component
@Slf4j
public class MethodTimeAspect {
    @Pointcut("@annotation(app.aop.MethodTime)")
    public void doAspect() {
        // do some operations based on point cut
    }

    @Around("doAspect()")
    public void recordMethodTime(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        StopWatch watch = new StopWatch();
        watch.start();
        joinPoint.proceed();
        watch.stop();
        log.info("{} {} cost {} ms",
                methodSignature.getMethod().getName(),
                getParameterArray(joinPoint),
                watch.getTime());
    }

    private String getParameterArray(JoinPoint joinPoint) {
        Object[] parameters = joinPoint.getArgs();
        if (parameters.length == 0) {
            return "";
        }
        List<String> parametersList = Lists.newArrayList();
        for (Object parameter : parameters) {
            parametersList.add(parameter.toString());
        }
        return parametersList.toString();
    }
}
