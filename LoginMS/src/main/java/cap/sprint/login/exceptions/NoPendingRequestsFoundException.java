package cap.sprint.login.exceptions;

public class NoPendingRequestsFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public NoPendingRequestsFoundException(String msg)
	{
		super(msg);
	}

}
