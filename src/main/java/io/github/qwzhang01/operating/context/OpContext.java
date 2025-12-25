package io.github.qwzhang01.operating.context;

public class OpContext {

    private static final ThreadLocal<OpContext> CUR = new ThreadLocal<>();

}
