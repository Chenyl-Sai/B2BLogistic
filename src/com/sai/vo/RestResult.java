package com.sai.vo;

/**
 * POST�����rest�ķ���������
 * @author Sai
 * */
public class RestResult<T> implements java.io.Serializable
{
	private static final long serialVersionUID = 321815687447253311L;
	/** ����״̬ */
	private String status;
	/** ����˵�� */
	private String message;
	/** �ɹ��󷵻ص����� */
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
