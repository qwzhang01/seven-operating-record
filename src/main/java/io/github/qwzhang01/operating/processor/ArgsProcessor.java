package io.github.qwzhang01.operating.processor;

import io.github.qwzhang01.operating.anno.Op;

public class ArgsProcessor {
    public static Object process(Op op, Object[] args) {

        if (args != null) {
            for (Object arg : args) {
                if (arg != null && arg.getClass().equals(op.args())) {
                    return arg;
                }
            }
        }

        return null;
    }
}
