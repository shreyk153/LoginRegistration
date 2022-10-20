package cap.sprint.login.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface IMembershipDao extends JpaRepository<Membership, Integer>
{
	@Query("from Membership m where m.active=false")
	public List<Membership> findPendingRequests();					//To get all pending requests.
	
	public Membership findByUsername(String username);				//To get membership from username.
}