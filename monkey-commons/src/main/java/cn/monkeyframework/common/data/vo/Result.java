package cn.monkeyframework.common.data.vo;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class Result<T> implements Serializable {
    private Result() {
    }

    private int code;
    private String msg;
    private T data;
    private Throwable error;

    public boolean isOK() {
        return this.code == ResultCode.OK;
    }

    public boolean hasError() {
        return this.error != null;
    }

    private static <T> Builder<T> newBuilder() {
        return new Builder<>();
    }

    public static <T> Result<T> of(int code, String msg, T data, Throwable error) {
        Builder<T> builder = newBuilder();
        return builder.code(code).msg(msg).data(data).error(error).build();
    }

    public static <T> Result<T> ok(T data) {
        return of(ResultCode.OK, null, data, null);
    }

    public static <T> Result<T> ok() {
        return ok(null);
    }

    public static <T> Result<T> fail(int code, String msg) {
        return of(code, msg, null, null);
    }

    public static <T> Result<T> fail(String msg) {
        return fail(ResultCode.FAIL, msg);
    }

    public static <T> Result<T> error(Throwable error) {
        return of(ResultCode.ERROR, null, null, error);
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", error=" + error +
                '}';
    }

    static class Builder<T> {
        private final Result<T> result;

        Builder() {
            this.result = new Result<>();
        }

        Builder<T> code(int code) {
            this.result.code = code;
            return this;
        }

        Builder<T> msg(String msg) {
            this.result.msg = msg;
            return this;
        }

        Builder<T> data(T data) {
            this.result.data = data;
            return this;
        }

        Builder<T> error(Throwable error) {
            this.result.error = error;
            return this;
        }

        Result<T> build() {
            return this.result;
        }
    }
}
