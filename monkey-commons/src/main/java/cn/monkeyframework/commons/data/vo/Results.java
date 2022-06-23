package cn.monkeyframework.commons.data.vo;

public abstract class Results {
    public static <T> Result<T> ok(T data) {
        Result.Builder<T> builder = Result.newBuilder();
        return builder.code(ResultCode.OK)
                .data(data)
                .build();
    }

    public static <T> Result<T> ok() {
        return ok(null);
    }

    public static <T> Result<T> fail(int code, String msg) {
        Result.Builder<T> builder = Result.newBuilder();
        return builder.code(code)
                .msg(msg)
                .build();
    }

    public static <T> Result<T> fail(String msg) {
        return fail(ResultCode.FAIL, msg);
    }

    public static <T> Result<T> error(Throwable error) {
        Result.Builder<T> builder = Result.newBuilder();
        return builder.code(ResultCode.ERROR)
                .error(error)
                .build();
    }

    public static boolean isOK(Result<?> result) {
        return ResultCode.OK == result.getCode();
    }
}
