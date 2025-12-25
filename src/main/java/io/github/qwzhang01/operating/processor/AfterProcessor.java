package io.github.qwzhang01.operating.processor;

import io.github.qwzhang01.operating.anno.Op;
import io.github.qwzhang01.operating.kit.SpringKit;
import io.github.qwzhang01.operating.strategy.OpStrategy;

public class AfterProcessor {
    public void process(Op op, Object oldData, Object newData) {
        Class<? extends OpStrategy> strategyClazz = op.strategy();

        OpStrategy strategy = SpringKit.getBeanSafely(strategyClazz);
        if (strategy == null) {
            return;
        }

        if (oldData == null) {
            strategy.afterAction(op.target(), op.action(), newData);
            return;
        }
        strategy.afterAction(op.target(), op.action(), oldData, newData);
    }
}
