package io.github.qwzhang01.operating.processor;

import io.github.qwzhang01.operating.anno.Op;
import io.github.qwzhang01.operating.kit.SpringKit;
import io.github.qwzhang01.operating.strategy.OpStrategy;

/**
 * After Processor for Operation Recording.
 * 
 * This processor is invoked after the target method execution completes.
 * It retrieves the configured strategy and delegates the operation recording
 * to the strategy's afterAction method.
 * 
 * <p>The processor handles two scenarios:
 * <ul>
 *   <li>If oldData is null (no comparison needed), only newData is recorded</li>
 *   <li>If oldData exists (comparison enabled), both old and new data are recorded</li>
 * </ul>
 */
public class AfterProcessor {
    
    /**
     * Processes the operation recording after method execution.
     * 
     * <p>This method:
     * <ol>
     *   <li>Retrieves the strategy class from the annotation</li>
     *   <li>Gets the strategy bean instance from Spring context</li>
     *   <li>Invokes the appropriate afterAction method based on whether oldData exists</li>
     * </ol>
     * 
     * @param op the operation annotation containing configuration
     * @param oldData the data captured before method execution (null if comparison is disabled)
     * @param newData the data extracted from method arguments
     */
    public void process(Op op, Object oldData, Object newData) {
        Class<? extends OpStrategy> strategyClazz = op.strategy();

        OpStrategy strategy = SpringKit.getBeanSafely(strategyClazz);
        if (strategy == null) {
            return;
        }

        // If no oldData (comparison disabled), record only new data
        if (oldData == null) {
            strategy.afterAction(op.target(), op.action(), newData);
            return;
        }
        
        // If oldData exists (comparison enabled), record both old and new data
        strategy.afterAction(op.target(), op.action(), oldData, newData);
    }
}
