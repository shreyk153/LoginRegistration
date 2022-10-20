package cap.sprint.login.dao;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="members")
public class Membership 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int membershipId;
	@Column(unique=true)
	String username;
	String password;
	String roles;
	boolean active;
	
	public Membership(){}

	public Membership(String username, String password) 
	{
		super();
		this.username = username;
		this.password = password;
	}

	public int getMembershipId() {
		return membershipId;
	}

	public void setMembershipId(int membershipId) {
		this.membershipId = membershipId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public int hashCode() {
		return Objects.hash(membershipId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Membership other = (Membership) obj;
		return membershipId == other.membershipId;
	}

	@Override
	public String toString() {
		return "Membership [Id=" + membershipId + ", Roles=" + roles + ", isActive = " + active + "]";
	}
	
	

}
