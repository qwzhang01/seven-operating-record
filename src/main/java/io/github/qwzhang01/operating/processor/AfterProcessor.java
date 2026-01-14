package io.github.qwzhang01.operating.processor;

import io.github.qwzhang01.operating.anno.Op;
import io.github.qwzhang01.operating.kit.SpringKit;
import io.github.qwzhang01.operating.strategy.OpNeedQueryStrategy;
import io.github.qwzhang01.operating.strategy.OpParamStrategy;
import io.github.qwzhang01.operating.strategy.OpReturnStrategy;
import io.github.qwzhang01.operating.strategy.OpStrategy;

/**
 * After Processor for Operation Recording.
 * <p>
 * This processor is invoked after the target method execution completes.
 * It retrieves the configured strategy and delegates the operation recording
 * to the strategy's afterAction and afterReturn methods.
 *
 * <p>The processor handles two scenarios:
 * <ul>
 *   <li>If oldData is null (no comparison needed), only newData is
 *   recorded</li>
 *   <li>If oldData exists (comparison enabled), both old and new data are
 *   recorded</li>
 * </ul>
 *
 * <p>Additionally, if the method returns a value, it will be passed to the
 * strategy's afterReturn methods for further processing.
 *
 * @author avinzhang
 */
public class AfterProcessor {

    /**
     * Processes the operation recording after method execution.
     *
     * <p>This method:
     * <ol>
     *   <li>Retrieves the strategy class from the annotation</li>
     *   <li>Gets the strategy bean instance from Spring context</li>
     *   <li>Invokes the appropriate afterAction method based on whether
     *   oldData exists</li>
     *   <li>Invokes afterReturn methods to process the return value</li>
     * </ol>
     *
     * @param clazz        the fully qualified name of the class containing
     *                     the method
     * @param method       the name of the method being executed
     * @param op           the operation annotation containing configuration
     * @param beforeDbData the data captured before method execution (null if
     *                     comparison is disabled)
     * @param methodArgs   the data extracted from method arguments
     * @param methodReturn the return value from the method execution
     */
    public void process(String clazz, String method,
                        Op op,
                        Object beforeDbData,
                        Object methodArgs,
                        Object methodReturn) {
        if (op == null) {
            return;
        }
        if (op.strategy() == null) {
            return;
        }

        Class<? extends OpStrategy> strategyClazz = op.strategy();

        OpStrategy strategy = SpringKit.getBeanSafely(strategyClazz);
        if (strategy == null) {
            return;
        }

        if (beforeDbData == null) {
            if (methodArgs != null
                    && (OpNeedQueryStrategy.class.isAssignableFrom(strategyClazz)
                    || OpParamStrategy.class.isAssignableFrom(strategyClazz))) {
                strategy.afterAction(methodArgs);
                strategy.afterAction(clazz, method, methodArgs);
            }

            if (methodReturn != null && OpReturnStrategy.class.isAssignableFrom(strategyClazz)) {
                strategy.afterReturn(methodReturn);
                strategy.afterReturn(clazz, method, methodReturn);
            }
        } else {

            if (methodArgs != null
                    && (OpNeedQueryStrategy.class.isAssignableFrom(strategyClazz)
                    || OpParamStrategy.class.isAssignableFrom(strategyClazz))) {
                strategy.afterAction(beforeDbData, methodArgs);
                strategy.afterAction(clazz, method, beforeDbData, methodArgs);
            }

            if (methodReturn != null && OpReturnStrategy.class.isAssignableFrom(strategyClazz)) {
                strategy.afterReturn(methodReturn);
                strategy.afterReturn(clazz, method, methodReturn);
            }
        }

    }
}
