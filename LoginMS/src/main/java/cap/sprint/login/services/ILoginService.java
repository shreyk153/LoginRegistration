package cap.sprint.login.services;

import java.util.List;

import cap.sprint.login.dao.Membership;

public interface ILoginService 
{
	public List<Membership> listAllRequests();
	public String approveRequest(int membershipId, boolean flag);
	public String deleteMembershipById(int id);
	public String updateRoles(int membershipId, String roles);
}
