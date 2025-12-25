package io.github.qwzhang01.operating.processor;

import io.github.qwzhang01.operating.anno.Op;
import io.github.qwzhang01.operating.kit.SpringKit;
import io.github.qwzhang01.operating.strategy.OpStrategy;

public class BeforeProcessor {
    public Object process(Op op) {

        Class<? extends OpStrategy> strategyClazz = op.strategy();

        OpStrategy strategy = SpringKit.getBeanSafely(strategyClazz);
        if (strategy == null) {
            return null;
        }

        return strategy.beforeAction();
    }
}
