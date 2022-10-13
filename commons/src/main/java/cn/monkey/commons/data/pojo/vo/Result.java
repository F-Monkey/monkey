package cn.monkey.commons.data.pojo.vo;

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
