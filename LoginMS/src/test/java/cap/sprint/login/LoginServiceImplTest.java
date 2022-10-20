package cap.sprint.login;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import cap.sprint.login.dao.IMembershipDao;
import cap.sprint.login.dao.Membership;
import cap.sprint.login.exceptions.MembershipIdNotFoundException;
import cap.sprint.login.services.LoginServiceImpl;
import cap.sprint.login.util.Constants;

@ExtendWith(MockitoExtension.class)
class LoginServiceImplTest {

	@Mock
	private IMembershipDao membershipDao;
	
	@Autowired
	private LoginServiceImpl loginService;
	
	Membership membership1;
	Membership membership2;
	
	@BeforeEach
	@SuppressWarnings("deprecation")
	void setUp() {
		 MockitoAnnotations.initMocks(this);
		this.loginService=new LoginServiceImpl(this.membershipDao);
		membership1=new Membership();
		membership2=new Membership();
		membership1.setActive(false);
		membership1.setPassword("Password@1");
		membership1.setUsername("User1");
		membership1.setRoles("ROLE_USER");
		membership1.setMembershipId(101);
		membershipDao.save(membership1);
		membership2.setActive(false);
		membership2.setPassword("Password@2");
		membership2.setUsername("User2");
		membership2.setRoles("ROLE_USER");
		membership2.setMembershipId(102);
		membershipDao.save(membership2);
	}
	
	@Test
	void testListAllRequests() {
		  loginService.listAllRequests();
		  verify(membershipDao).findPendingRequests();
	}
	
	
	@Test void testDeleteMembershipById(){
		 when(membershipDao.findById(101)).thenReturn(Optional.empty());
		 assertThrows(MembershipIdNotFoundException.class, ()->loginService.deleteMembershipById(101));
	}
	
	@Test
	void testupdateRoles() {
		when(membershipDao.findById(101)).thenReturn(Optional.empty());
		assertThrows(MembershipIdNotFoundException.class, ()->loginService.updateRoles(101, "ROLE_USER"));
		verify(membershipDao,times(1)).findById(101);
	}
	 
	
	@Test
	void testApproveRequest() {
		 when(membershipDao.findById(101)).thenReturn(Optional.empty());
		 assertThrows(MembershipIdNotFoundException.class, ()->loginService.approveRequest(101, true));
		 when(membershipDao.findById(102)).thenReturn(Optional.of(membership2));
		 assertEquals(Constants.DNY, loginService.approveRequest(102, false));
	}
	
	@Test
	void testLoadUserByUsername() {
		loginService.loadUserByUsername("User1");
		verify(membershipDao).findByUsername("User1");
	}

}
