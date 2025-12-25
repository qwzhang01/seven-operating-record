package io.github.qwzhang01.operating.strategy;

public class DefaultOpStrategy implements OpStrategy<Void> {
    @Override
    public Void beforeAction() {
        return OpStrategy.super.beforeAction();
    }

    @Override
    public void afterAction(Class<? extends Enum> target, Class<?
            extends Enum> action, Object newData) {
        OpStrategy.super.afterAction(target, action, newData);
    }

    @Override
    public void afterAction(Class<? extends Enum> target, Class<?
            extends Enum> action, Object oldData, Object newData) {
        OpStrategy.super.afterAction(target, action, oldData, newData);

    }
}
