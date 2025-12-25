package io.github.qwzhang01.operating.strategy;

public interface OpStrategy<T> {
    /**
     * 执行前
     * 缓存旧数据
     */
    default T beforeAction() {
        return null;
    }

    /**
     * 执行结束
     * 获取新数据
     * 记录操作记录
     */
    default void afterAction(Class<? extends Enum> target,
                             Class<? extends Enum> action, Object newData) {
    }

    default void afterAction(Class<? extends Enum> target,
                             Class<? extends Enum> action, Object oldData,
                             Object newData) {
    }
}