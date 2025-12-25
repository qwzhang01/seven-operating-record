package io.github.qwzhang01.operating.strategy;

/**
 * Default Implementation of Operation Recording Strategy.
 * <p>
 * This is a no-op implementation of {@link OpStrategy} that serves as a
 * placeholder when no custom strategy is specified. All methods simply delegate
 * to their parent interface's default implementations, which do nothing.
 *
 * <p>This strategy uses {@code Void} for all type parameters, indicating that
 * it doesn't process any specific data types. Users should create their own
 * implementations with appropriate type parameters for their use cases.
 *
 * <p>To create a custom strategy:
 * <pre>
 * public class MyStrategy implements OpStrategy&lt;MyDto, MyEntity, Boolean&gt; {
 *     &#64;Override
 *     public MyEntity beforeAction(MyDto args) {
 *         // Custom implementation
 *     }
 *
 *     &#64;Override
 *     public void afterAction(MyEntity oldData, MyDto newData) {
 *         // Custom implementation
 *     }
 * }
 * </pre>
 *
 * @author avinzhang
 * @see OpStrategy
 */
public class DefaultOpStrategy implements OpStrategy<Void, Void, Void> {
    @Override
    public Void beforeAction(Void args) {
        return OpStrategy.super.beforeAction(args);
    }

    @Override
    public Void beforeAction(String clazz, String method, Void args) {
        return OpStrategy.super.beforeAction(clazz, method, args);
    }

    @Override
    public void afterAction(Void newData) {
        OpStrategy.super.afterAction(newData);
    }

    @Override
    public void afterAction(Void oldData, Void newData) {
        OpStrategy.super.afterAction(oldData, newData);
    }

    @Override
    public void afterAction(String clazz, String method, Void newData) {
        OpStrategy.super.afterAction(clazz, method, newData);
    }

    @Override
    public void afterAction(String clazz, String method, Void oldData,
                            Void newData) {
        OpStrategy.super.afterAction(clazz, method, oldData, newData);
    }

    @Override
    public void afterReturn(Void returnData) {
        OpStrategy.super.afterReturn(returnData);
    }

    @Override
    public void afterReturn(String clazz, String method, Void returnData) {
        OpStrategy.super.afterReturn(clazz, method, returnData);
    }
}