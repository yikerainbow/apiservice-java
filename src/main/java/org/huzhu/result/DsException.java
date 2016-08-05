package org.huzhu.result;

/**
 * Created by wjh on 15/10/19.
 */
public class DsException extends RuntimeException{
    private ExcepFactor excepFactor;
    public DsException (ExcepFactor excepFactor) {
        super(excepFactor.getData().toString());
        this.excepFactor = excepFactor;
    }

    public DsException(ExcepFactor excepFactor,String message) {
        super(message);
        this.excepFactor = excepFactor;
    }

    public DsException(ExcepFactor factor, String message, Throwable t) {
        super(message, t);
        this.excepFactor = factor;
    }

    public ExcepFactor getExcepFactor() {
        return this.excepFactor;
    }
}
