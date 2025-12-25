package io.github.qwzhang01.operating.processor;

import io.github.qwzhang01.operating.anno.Op;
import io.github.qwzhang01.operating.kit.SpringKit;
import io.github.qwzhang01.operating.strategy.OpStrategy;

/**
 * Before Processor for Operation Recording.
 * 
 * This processor is invoked before the target method execution begins.
 * It retrieves the configured strategy and delegates to the strategy's
 * beforeAction method to capture the current state of data.
 * 
 * <p>The captured data (oldData) is typically used for comparison with
 * the data after method execution to track changes when {@code comparable}
 * is enabled in the {@link Op} annotation.
 */
public class BeforeProcessor {
    
    /**
     * Processes the data capture before method execution.
     * 
     * <p>This method:
     * <ol>
     *   <li>Retrieves the strategy class from the annotation</li>
     *   <li>Gets the strategy bean instance from Spring context</li>
     *   <li>Invokes the strategy's beforeAction method to capture current data</li>
     * </ol>
     * 
     * @param op the operation annotation containing configuration
     * @return the data captured before method execution, or null if strategy is unavailable
     */
    public Object process(Op op) {

        Class<? extends OpStrategy> strategyClazz = op.strategy();

        OpStrategy strategy = SpringKit.getBeanSafely(strategyClazz);
        if (strategy == null) {
            return null;
        }

        return strategy.beforeAction();
    }
}
