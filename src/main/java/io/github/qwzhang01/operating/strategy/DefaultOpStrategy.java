package io.github.qwzhang01.operating.strategy;

/**
 * Default Operation Recording Strategy.
 * 
 * This is a no-op implementation of {@link OpStrategy} that provides default
 * behavior for operation recording. It doesn't perform any actual recording
 * but serves as a placeholder and base implementation.
 * 
 * <p>Users should extend or replace this class with their own implementation
 * to provide actual operation recording functionality, such as:
 * <ul>
 *   <li>Logging to a database</li>
 *   <li>Writing to audit logs</li>
 *   <li>Sending to a message queue</li>
 *   <li>Triggering notifications</li>
 * </ul>
 * 
 * <p>This class is automatically registered as a Spring bean by
 * {@link io.github.qwzhang01.operating.config.OperationRecordConfig}
 * and can be overridden by defining a custom bean in your application context.
 */
public class DefaultOpStrategy implements OpStrategy<Void> {
    
    /**
     * Default implementation that returns null.
     * Override this method to capture data before method execution.
     * 
     * @return null by default
     */
    @Override
    public Void beforeAction() {
        return OpStrategy.super.beforeAction();
    }

    /**
     * Default implementation that does nothing.
     * Override this method to record operations without comparison.
     * 
     * @param target the target entity type from the annotation
     * @param action the action type from the annotation
     * @param newData the data extracted from method arguments
     */
    @Override
    public void afterAction(Class<? extends Enum> target, Class<?
            extends Enum> action, Object newData) {
        OpStrategy.super.afterAction(target, action, newData);
    }

    /**
     * Default implementation that does nothing.
     * Override this method to record operations with comparison.
     * 
     * @param target the target entity type from the annotation
     * @param action the action type from the annotation
     * @param oldData the data captured before method execution
     * @param newData the data extracted from method arguments
     */
    @Override
    public void afterAction(Class<? extends Enum> target, Class<?
            extends Enum> action, Object oldData, Object newData) {
        OpStrategy.super.afterAction(target, action, oldData, newData);

    }
}
