package indi.monkey.commons.web.vo;

import indi.monkey.commons.web.constant.ResultCode;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response<T> {
	private String id;
	private T data;
	private Throwable e;
	private Integer costTime;
	private String resultCode;

	@SuppressWarnings("unchecked")
	public static <T> Response<T> of(String id, Integer costTime, String resultCode, Throwable e, T data) {
		return (Response<T>) Response.builder().id(id).costTime(costTime).e(e).data(data).resultCode(resultCode)
				.build();
	}

	public static <T> Response<T> error(String resultCode, Throwable e) {
		return of(null, null, resultCode == null ? ResultCode.ERROR : resultCode, e, null);
	}

	public static <T> Response<T> error(Throwable e) {
		return error(null, e);
	}

	public static <T> Response<T> success(T data) {
		return of(null, null, ResultCode.SUCCESS, null, data);
	}
}
