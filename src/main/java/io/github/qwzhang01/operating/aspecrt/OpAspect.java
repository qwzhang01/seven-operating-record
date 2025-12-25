package io.github.qwzhang01.operating.aspecrt;

import io.github.qwzhang01.operating.anno.Op;
import io.github.qwzhang01.operating.processor.AfterProcessor;
import io.github.qwzhang01.operating.processor.ArgsProcessor;
import io.github.qwzhang01.operating.processor.BeforeProcessor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class OpAspect {
    private final BeforeProcessor beforeProcessor;
    private final AfterProcessor afterProcessor;

    public OpAspect(BeforeProcessor beforeProcessor,
                    AfterProcessor afterProcessor) {
        this.beforeProcessor = beforeProcessor;
        this.afterProcessor = afterProcessor;
    }

    @Pointcut("@annotation(io.github.qwzhang01.operating.anno.Op)")
    public void opPointcut() {
    }

    @Around("opPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        var method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        var op = method.getAnnotation(Op.class);

        Object oldData = null;
        boolean comparable = op.comparable();
        if (comparable) {
            oldData = beforeProcessor.process(op);
        }
        Object[] args = joinPoint.getArgs();

        Object newData = ArgsProcessor.process(op, args);

        Object proceed = joinPoint.proceed();

        afterProcessor.process(op, oldData, newData);

        return proceed;
    }
}
