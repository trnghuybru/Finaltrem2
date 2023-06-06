package Client.model;

public class Account {
	private String username;
	private String password;
	private String phone;
	private String email;
	
	
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Account(String username, String password, String phone, String email) {
		this.username = username;
		this.password = password;
		this.phone = phone;
		this.email = email;
	}
	
	
	@Override
	public String toString() {
		return "Account [username=" + username + ", password=" + password + ", phone=" + phone + ", email=" + email
				+ "]";
	}
	
}
