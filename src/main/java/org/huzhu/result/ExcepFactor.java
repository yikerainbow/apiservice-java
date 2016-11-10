package org.huzhu.result;

/**
 * Created by guobao on 15/10/19.
 */
public class ExcepFactor extends Result {

    protected ExcepFactor(int code, String errorMsg) {
        super(code, errorMsg);
    }

    public static final ExcepFactor E_INVALID_SOURCE = new ExcepFactor(-2, "invalid source");
    public static final ExcepFactor E_PARAMETER_ERROR = new ExcepFactor(-3, "parameter error");
    public static final ExcepFactor E_REMOTE_SERVER_ERROR = new ExcepFactor(-4, "remote server error");
    public static final ExcepFactor E_USER_DOES_NOT_EXISTS = new ExcepFactor(-5, "User does not exists");
}
