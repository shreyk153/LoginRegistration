package cap.sprint.login.services;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import cap.sprint.login.dao.Membership;

public class Principle implements UserDetails
{
	private static final long serialVersionUID = 1L;
	
	private Membership member;							//Priciple class for authenticating membership.
	public Principle(Membership member)
	{
		this.member=member;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() 			//Method for obtaining roles.
	{
		return Arrays.stream(member.getRoles().split(",")).map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}

	@Override
	public String getPassword() 
	{
		return member.getPassword();										//Method for obtaining password.
	}

	@Override
	public String getUsername() 
	{
		return member.getUsername();										//Method for obtaining username.
	}

	@Override
	public boolean isAccountNonExpired() 
	{
		return true;
	}

	@Override
	public boolean isAccountNonLocked() 
	{
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() 
	{
		return true;
	}

	@Override
	public boolean isEnabled() 
	{
		return member.isActive();									//Checks Activity/Approval status.
	}

}
