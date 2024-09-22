package librarymanagment.dto;

public class User {
	private String userId;
	private String userName;
	private String email;
	private String address;
	private String gender;
	private String password;

	public User(String userName, String email, String address, String gender,String password) {
		this.userName = userName;
		this.email = email;
		this.address = address;
		this.gender = gender;
		this.setPassword(password);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User{" + "userId='" + userId + '\'' + ", userName='" + userName + '\'' + ", email='" + email + '\''
				+ ", address='" + address + '\'' + ", gender='" + gender + '\'' + '}';
	}
}
