package com.sai.vo;

/**
 * POST请求后rest的返回数据类
 * @author Sai
 * */
public class RestResult<T> implements java.io.Serializable
{
	private static final long serialVersionUID = 321815687447253311L;
	/** 返回状态 */
	private String status;
	/** 返回说明 */
	private String message;
	/** 成功后返回的数据 */
	private T data;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
}
