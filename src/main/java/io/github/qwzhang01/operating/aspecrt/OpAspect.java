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

/**
 * Operation Recording Aspect.
 * 
 * This aspect intercepts methods annotated with {@link Op} and orchestrates
 * the operation recording workflow. It captures data before method execution,
 * processes method arguments, and records the operation after execution.
 * 
 * <p>Workflow:
 * <ol>
 *   <li>If comparable is enabled, capture old data via {@link BeforeProcessor}</li>
 *   <li>Extract new data from method arguments via {@link ArgsProcessor}</li>
 *   <li>Execute the original method</li>
 *   <li>Record the operation via {@link AfterProcessor}</li>
 * </ol>
 */
@Aspect
public class OpAspect {
    private final BeforeProcessor beforeProcessor;
    private final AfterProcessor afterProcessor;

    /**
     * Constructs an OpAspect with the required processors.
     * 
     * @param beforeProcessor processor for capturing data before method execution
     * @param afterProcessor processor for recording operations after method execution
     */
    public OpAspect(BeforeProcessor beforeProcessor,
                    AfterProcessor afterProcessor) {
        this.beforeProcessor = beforeProcessor;
        this.afterProcessor = afterProcessor;
    }

    /**
     * Pointcut definition for methods annotated with {@link Op}.
     * This pointcut identifies all methods that require operation recording.
     */
    @Pointcut("@annotation(io.github.qwzhang01.operating.anno.Op)")
    public void opPointcut() {
    }

    /**
     * Around advice that intercepts method execution for operation recording.
     * 
     * <p>This method performs the following steps:
     * <ol>
     *   <li>Retrieves the {@link Op} annotation from the target method</li>
     *   <li>If comparable is true, captures old data before execution</li>
     *   <li>Extracts new data from method arguments</li>
     *   <li>Executes the original method</li>
     *   <li>Invokes the after processor to record the operation</li>
     * </ol>
     * 
     * @param joinPoint the proceeding join point representing the intercepted method
     * @return the result of the original method execution
     * @throws Throwable if the original method throws an exception
     */
    @Around("opPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        var method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        var op = method.getAnnotation(Op.class);

        // Capture old data if comparison is enabled
        Object oldData = null;
        boolean comparable = op.comparable();
        if (comparable) {
            oldData = beforeProcessor.process(op);
        }
        
        // Extract new data from method arguments
        Object[] args = joinPoint.getArgs();
        Object newData = ArgsProcessor.process(op, args);

        // Execute the original method
        Object proceed = joinPoint.proceed();

        // Record the operation
        afterProcessor.process(op, oldData, newData);

        return proceed;
    }
}
