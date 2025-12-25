package io.github.qwzhang01.operating.strategy;

public interface OpStrategy {
    /**
     * 执行前
     * 缓存旧数据
     */
    default void beforeAction() {
    }

    /**
     * 执行结束
     * 获取新数据
     * 记录操作记录
     */
    default void afterAction() {
    }
}