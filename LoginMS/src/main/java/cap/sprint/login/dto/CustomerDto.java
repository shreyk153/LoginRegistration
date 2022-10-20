package cap.sprint.login.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class CustomerDto 
{
	@Min(value=100000000000l)
	@Max(value=999999999999l)
	private long aadhar;
	
	@NotBlank(message = "Name is mandatory")
	private String name;
	
	@Email(message="Please enter valid email.")
	private String email;
	
	@Min(value=6000000000l, message = "Invalid Phone number.")
	@Max(value=10000000000l, message = "Invalid Phone number.")
	private long phone;
	
	@Min(value=0)
	private int mid;
	
	public CustomerDto() {}

	public CustomerDto(@Min(1000000000) long aadhar, @NotBlank(message = "Name is mandatory") String name,
			@Email(message = "Please enter valid email.") String email,
			@NotBlank(message = "Phone is mandatory") long phone,
			@Min(value=0) int mid) {
		super();
		this.aadhar = aadhar;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.mid = mid;
	}

	public long getAadhar() {
		return aadhar;
	}

	public void setAadhar(long aadhar) {
		this.aadhar = aadhar;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}


	@Override
	public String toString() {
		return "Customer [Aadhar=" + aadhar + ", Name=" + name + ", Email=" + email + ", Phone=" + phone + 
			   ", MembershipId=" + mid + "]";
	}	
	
	
}
