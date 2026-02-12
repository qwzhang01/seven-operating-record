package io.github.qwzhang01.operating.processor;

import io.github.qwzhang01.operating.anno.Op;
import io.github.qwzhang01.operating.kit.SpringKit;
import io.github.qwzhang01.operating.strategy.OpReturnStrategy;

/**
 * Before Processor for Operation Recording.
 * <p>
 * This processor is invoked before the target method execution begins.
 * It retrieves the configured strategy and delegates to the strategy's
 * beforeAction method to capture the current state of data.
 *
 * <p>The captured data (oldData) is typically used for comparison with
 * the data after method execution to track changes when {@code comparable}
 * or {@code removed} is enabled in the {@link Op} annotation.
 *
 * @author avinzhang
 */
public class BeforeProcessor {

    /**
     * Processes the data capture before method execution.
     *
     * <p>This method:
     * <ol>
     *   <li>Retrieves the strategy class from the annotation</li>
     *   <li>Gets the strategy bean instance from Spring context</li>
     *   <li>Invokes the strategy's beforeAction method to capture current
     *   data</li>
     *   <li>Falls back to the no-context version if the context-aware version
     *   returns null</li>
     * </ol>
     *
     * @param clazz  the fully qualified name of the class containing the method
     * @param method the name of the method being executed
     * @param op     the operation annotation containing configuration
     * @param args   the arguments extracted from the method parameters
     * @return the data captured before method execution, or null if strategy
     * is unavailable
     */
    public Object process(String clazz, String method, Op op, Object args) {
        if (op == null) {
            return null;
        }
        if (op.strategy() == null) {
            return null;
        }

        var strategy = SpringKit.getBeanSafely(op.strategy());
        if (strategy == null) {
            return null;
        }

        if (OpReturnStrategy.class.isAssignableFrom(op.strategy())) {
            return null;
        }

        var dbData = strategy.beforeAction(clazz, method, args);
        if (dbData != null) {
            return dbData;
        }
        return strategy.beforeAction(args);
    }
}
