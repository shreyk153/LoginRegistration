package cap.sprint.login.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cap.sprint.login.dao.IMembershipDao;
import cap.sprint.login.dao.Membership;
import cap.sprint.login.exceptions.InvalidRoleUpdateException;
import cap.sprint.login.exceptions.MembershipIdNotFoundException;
import cap.sprint.login.util.Constants;

@Service
@Transactional
public class LoginServiceImpl implements ILoginService, UserDetailsService
{
	@Autowired
	IMembershipDao membershipDao;			//MembershipDao object created.
	
	public LoginServiceImpl(IMembershipDao membershipDao)
	{
		this.membershipDao=membershipDao;
	}

	@Override
	public List<Membership> listAllRequests() 
	{
		return membershipDao.findPendingRequests();			//Method to find all pending requests.
	}

	@Override
	public String deleteMembershipById(int requestId) 
	{
		if(membershipDao.findById(requestId).isEmpty()) 
			throw new MembershipIdNotFoundException(Constants.MEMNE);
		membershipDao.deleteById(requestId);
		return Constants.DEL;									//Method to delete request from database.
	}

	@Override
	public String approveRequest(int requestId, boolean flag) 
	{
		if(membershipDao.findById(requestId).isEmpty()) 
			throw new MembershipIdNotFoundException(Constants.MEMNE);
		else if(flag)
		{
			Membership member = membershipDao.getById(requestId);		//Member retrieved.
			member.setActive(true);												//Member approved.
			membershipDao.save(member);
			return Constants.APP;
		}
		else
		{
			return Constants.DNY;												//Member Denied.
		}														//Method to approve request in database.
	}

	@Override
	public String updateRoles(int membershipId, String roles) 
	{
		if(membershipDao.findById(membershipId).isEmpty()) 
			throw new MembershipIdNotFoundException(Constants.MEMNE);
		boolean flag = false;
		for(String s:Constants.VALIDROLES)
		{
			if(s.equals(roles)) flag=true;
		}
		
		if(!flag) throw new InvalidRoleUpdateException("Invalid roles entered.");
		
		Membership member = membershipDao.getById(membershipId);
		member.setRoles(roles);
		membershipDao.save(member);
		return Constants.APP;								//Method to change roles.
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{		
		return new Principle(membershipDao.findByUsername(username));			//Method to find Principle for authentication.
	}

}
