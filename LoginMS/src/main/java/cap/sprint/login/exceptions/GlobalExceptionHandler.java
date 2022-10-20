package cap.sprint.login.exceptions;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cap.sprint.login.util.ErrorInfo;

@RestControllerAdvice
public class GlobalExceptionHandler 
{
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({MethodArgumentNotValidException.class})
	public ErrorInfo handleException(MethodArgumentNotValidException ie,HttpServletRequest req)
	{
		Map<String, String> err = new LinkedHashMap<>();
		ie.getBindingResult().getAllErrors().forEach(e->
		{
			String ef = ((FieldError)e).getField();
			String ec = e.getDefaultMessage();
			err.put(ef, ec);
		});
		
		return new ErrorInfo(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST,err.toString(),
				req.getRequestURI(),req.getMethod());
	}
	
	//To handle the MethodArgumentNotValidException.
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({MembershipIdNotFoundException.class})
	public ErrorInfo handleException(MembershipIdNotFoundException me,HttpServletRequest req)
	{
		return new ErrorInfo(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST,me.getMessage(),
				req.getRequestURI(),req.getMethod());
	}
	//To handle the MembershipIdNotFoundException
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({NoPendingRequestsFoundException.class})
	public ErrorInfo handleException(NoPendingRequestsFoundException pre,HttpServletRequest req)
	{
		return new ErrorInfo(HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND,pre.getMessage(),
				req.getRequestURI(),req.getMethod());
	}
	//To handle the NoPendingRequestsFoundException
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({UsernameNotFoundException.class})
	public ErrorInfo handleException(UsernameNotFoundException ue,HttpServletRequest req)
	{
		return new ErrorInfo(HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND,ue.getMessage(),
				req.getRequestURI(),req.getMethod());
	}
	//To handle the UsernameNotFoundException
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({InvalidRoleUpdateException.class})
	public ErrorInfo handleException(InvalidRoleUpdateException ire,HttpServletRequest req)
	{
		return new ErrorInfo(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST,ire.getMessage(),
				req.getRequestURI(),req.getMethod());
	}
	//To handle the InvalidRoleUpdateException
}
