package com.sai.vo;

/**
 * 登录成功后返回data数据类
 * @author Sai
 * */
public class LoginRestData implements java.io.Serializable
{
	private static final long serialVersionUID = 3671316630369651642L;
	/** sessionid，用来验证是否在线 */
	private String sessionId;
	/** 用户id，上传gis地址信息时使用 */
	private String id;
	/** 手机号 */
	private String phone;
	/** 密码(已加密) */
	private String password;
	/** 车主 */
	private String name;
	/** 车牌 */
	private String carNum;
	/** 邮箱 */
	private String email;
	/** 车辆信息 */
	private String carInfo;
	/** 创建时间 */
	private String createTime;
	/** 车辆照片 */
	private String carPic;
	/** 驾驶证照片 */
	private String driverLicensePic;
	/** 行驶证照片 */
	private String drivingLicensePic;
	/** 上传时间 */
	private String updateTime;
	/** 。。。 */
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
