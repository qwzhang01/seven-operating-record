package io.github.qwzhang01.operating.aspecrt;

import io.github.qwzhang01.operating.anno.Op;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class OpAspect {
    @Pointcut("@annotation(io.github.qwzhang01.operating.anno.Op)")
    public void opPointcut() {
    }

    @Around("opPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        var method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        var op = method.getAnnotation(Op.class);

        Object proceed = joinPoint.proceed();

        return proceed;
    }
}
