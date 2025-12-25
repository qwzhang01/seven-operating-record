package io.github.qwzhang01.operating.strategy;

/**
 * Operation Recording Strategy Interface.
 * 
 * This interface defines the contract for custom operation recording strategies.
 * Implementations can provide custom logic for capturing data before method execution
 * and recording operations after method execution.
 * 
 * <p>The generic type {@code T} represents the type of data returned by {@link #beforeAction()}.
 * 
 * <p>To create a custom strategy:
 * <ol>
 *   <li>Implement this interface with your specific business logic</li>
 *   <li>Register the implementation as a Spring bean</li>
 *   <li>Reference it in the {@link io.github.qwzhang01.operating.anno.Op} annotation</li>
 * </ol>
 * 
 * @param <T> the type of data captured before method execution
 */
public interface OpStrategy<T> {
    
    /**
     * Invoked before the target method execution.
     * 
     * <p>This method is called when {@code comparable} is set to true in the
     * {@link io.github.qwzhang01.operating.anno.Op} annotation. It should capture
     * and return the current state of the data that will be modified by the operation.
     * 
     * <p>Typical use cases:
     * <ul>
     *   <li>Query the database for the current record before update</li>
     *   <li>Cache the current state for comparison</li>
     *   <li>Prepare data needed for change tracking</li>
     * </ul>
     * 
     * @return the captured data before method execution, or null if not applicable
     */
    default T beforeAction() {
        return null;
    }

    /**
     * Invoked after the target method execution when no comparison is needed.
     * 
     * <p>This method is called when {@code comparable} is set to false or
     * when {@link #beforeAction()} returns null. It receives only the new data
     * extracted from method arguments.
     * 
     * <p>Typical use cases:
     * <ul>
     *   <li>Record creation operations (INSERT)</li>
     *   <li>Log operations without change tracking</li>
     *   <li>Simple audit logging</li>
     * </ul>
     * 
     * @param target the target entity type from the annotation
     * @param action the action type from the annotation
     * @param newData the data extracted from method arguments
     */
    default void afterAction(Class<? extends Enum> target,
                             Class<? extends Enum> action, Object newData) {
    }

    /**
     * Invoked after the target method execution when comparison is enabled.
     * 
     * <p>This method is called when {@code comparable} is set to true and
     * {@link #beforeAction()} returns a non-null value. It receives both the
     * old data (captured before execution) and new data (from method arguments).
     * 
     * <p>Typical use cases:
     * <ul>
     *   <li>Record update operations with change tracking</li>
     *   <li>Compare old and new values to log specific changes</li>
     *   <li>Detailed audit logging with before/after states</li>
     * </ul>
     * 
     * @param target the target entity type from the annotation
     * @param action the action type from the annotation
     * @param oldData the data captured before method execution
     * @param newData the data extracted from method arguments
     */
    default void afterAction(Class<? extends Enum> target,
                             Class<? extends Enum> action, Object oldData,
                             Object newData) {
    }
}