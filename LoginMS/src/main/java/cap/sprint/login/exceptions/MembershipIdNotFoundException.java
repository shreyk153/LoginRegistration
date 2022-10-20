package cap.sprint.login.exceptions;

public class MembershipIdNotFoundException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public MembershipIdNotFoundException(String msg)
	{
		super(msg);
	}

}
