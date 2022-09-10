package com.techvg.vks.security.jwt;

import java.util.Set;

import javax.validation.constraints.*;

public class SignUpForm {

	@NotBlank
	@Size(min = 1, max = 50)
	private String firstName;

	@NotBlank
	@Size(min = 1, max = 50)
	private String lastName;
    
	@NotBlank
	@Size(min = 1, max = 50)
	private String username;

	@NotBlank
	@Size(max = 60)
	@Email
	private String email;

	@Size(min = 3, max = 50)
	private String primaryaddress;

	

	@NotBlank
	@Size(min = 3, max = 50)
	private String phone;

	
	@NotBlank
	@Size(min = 6, max = 40)
	private String password;

	private Set<String> role;
	
	private Set<String> department;
	
	
	public String getPrimaryaddress() {
		return primaryaddress;
	}

	public void setPrimaryaddress(String primaryaddress) {
		this.primaryaddress = primaryaddress;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Set<String> getRole() {
		return this.role;
	}

	public void setRole(Set<String> role) {
		this.role = role;
	}

	public Set<String> getDepartment() {
		return department;
	}

	public void setDepartment(Set<String> department) {
		this.department = department;
	}
	
	
}