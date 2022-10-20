package cap.sprint.login.util;

public class Constants 
{
	public static final String MEMNE = "Membership id does not exist.";
	public static final String PRNF = "No Pending requests.";
	public static final String DDNY = "Not an active member or wrong Membership Id entered.";
	public static final String ACC = "Accepted.";
	public static final String ACK = "Your request is acknowledged.\n"
								   + "Changes may be made only if data is consistent.\n"
								   + "Make sure you have entered correct id numbers.\n"
								   + "Please get your data to check.";
	
	public static final String DNY = "Request Denied.";
	public static final String APP = "Request Approved.";
	public static final String DEL = "Deleted.";
	public static final String NF = "None Found.";
	public static final String SAVED = "Saved Successfully.";
	public static final String[] VALIDROLES = {"ROLE_USER","ROLE_ADMIN","ROLE_USER,ROLE_ADMIN"};
	
	private Constants() {}
}
