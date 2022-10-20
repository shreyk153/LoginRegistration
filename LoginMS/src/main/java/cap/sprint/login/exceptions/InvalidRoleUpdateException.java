package cap.sprint.login.exceptions;

public class InvalidRoleUpdateException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	public InvalidRoleUpdateException(String msg)
	{
		super(msg);
	}

}
