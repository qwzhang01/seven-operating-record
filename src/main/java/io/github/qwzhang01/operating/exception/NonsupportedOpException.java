package io.github.qwzhang01.operating.exception;

/**
 * 不支持的操作异常
 *
 * @author avinzhang
 */
public class NonsupportedOpException extends RuntimeException {
    public NonsupportedOpException() {
        super("不支持的操作");
    }

    public NonsupportedOpException(String message) {
        super(message);
    }
}
