package com.sai.vo;

/**
 * ��¼�ɹ��󷵻�data������
 * @author Sai
 * */
public class LoginRestData implements java.io.Serializable
{
	private static final long serialVersionUID = 3671316630369651642L;
	/** sessionid��������֤�Ƿ����� */
	private String sessionId;
	/** �û�id���ϴ�gis��ַ��Ϣʱʹ�� */
	private String id;
	/** �ֻ��� */
	private String phone;
	/** ����(�Ѽ���) */
	private String password;
	/** ���� */
	private String name;
	/** ���� */
	private String carNum;
	/** ���� */
	private String email;
	/** ������Ϣ */
	private String carInfo;
	/** ����ʱ�� */
	private String createTime;
	/** ������Ƭ */
	private String carPic;
	/** ��ʻ֤��Ƭ */
	private String driverLicensePic;
	/** ��ʻ֤��Ƭ */
	private String drivingLicensePic;
	/** �ϴ�ʱ�� */
	private String updateTime;
	/** ������ */
	private String balance;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCarNum() {
		return carNum;
	}

	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCarInfo() {
		return carInfo;
	}

	public void setCarInfo(String carInfo) {
		this.carInfo = carInfo;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCarPic() {
		return carPic;
	}

	public void setCarPic(String carPic) {
		this.carPic = carPic;
	}

	public String getDriverLicensePic() {
		return driverLicensePic;
	}

	public void setDriverLicensePic(String driverLicensePic) {
		this.driverLicensePic = driverLicensePic;
	}

	public String getDrivingLicensePic() {
		return drivingLicensePic;
	}

	public void setDrivingLicensePic(String drivingLicensePic) {
		this.drivingLicensePic = drivingLicensePic;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}
	
	
}
