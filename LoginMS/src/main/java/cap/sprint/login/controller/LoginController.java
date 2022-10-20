package cap.sprint.login.controller;


import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import cap.sprint.login.dto.AdminDto;
import cap.sprint.login.dto.CustomerDto;
import cap.sprint.login.exceptions.NoPendingRequestsFoundException;
import cap.sprint.login.services.ILoginService;
import cap.sprint.login.util.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@ApiModel("Login.")
@RestController
public class LoginController 
{	
	@Autowired
	ILoginService loginService;								//Login Service object created.
	
	RestTemplate restTemplate = new RestTemplate();			//RestTemplate object created.
	
	private Log log = LogFactory.getLog(LoginController.class);   //Log object.

	@GetMapping("/admin/requests")							//endpoint for accessing.
	@ApiOperation(tags="Admin Operations",value="To get all pending requests.")	//Api description for swagger-ui.
	public ResponseEntity<String> getAllRequests()
	{
		log.info("Get all pending requests method called.");
		StringBuilder stringbuilder = new StringBuilder("");
		loginService.listAllRequests().stream().forEach(t->stringbuilder.append("\n"+t.toString()));
		if(stringbuilder.toString().equals("")) throw new NoPendingRequestsFoundException(Constants.PRNF);
		return new ResponseEntity<>(stringbuilder.toString(), HttpStatus.FOUND);
		
		//Method to return all pending requests in database.
	}
	
	@DeleteMapping("/admin/deleterequest/{requestId}")				//endpoint for accessing.
	@ApiOperation(tags="Admin Operations",value="To delete a request from database.")		//Api description for swagger-ui.
	public ResponseEntity<String> processDelete(@ApiParam("Enter MembershipId.") @PathVariable("requestId") int requestId) 
	{
		log.info("Delete a request method called.");
		if(requestId==1) return new ResponseEntity<> (Constants.DNY, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<> (loginService.deleteMembershipById(requestId), HttpStatus.ACCEPTED);
		//Method to delete request by id from database.
	}

	@GetMapping("/admin/approve/id={id}/flag={flag}")	//endpoint for accessing.
	@ApiOperation(tags="Admin Operations",value="To approve/deny a request in database.")	//Api description for swagger-ui.
	public ResponseEntity<String> tacklerequest(@ApiParam("Enter MembershipId.") @PathVariable("id") int requestId, 
												@ApiParam("Enter Approval.") @PathVariable("flag") boolean flag) 
	{
		log.info("Approve a request method called.");
		return new ResponseEntity<> (loginService.approveRequest(requestId, flag), HttpStatus.ACCEPTED);
		//Method to approve a particular request by id in database.
	}
	
	@PutMapping("/admin/role/id={id}/role={roles}")	//endpoint for accessing.
	@ApiOperation(tags="Admin Operations",value="To update roles in database.")	//Api description for swagger-ui.
	public ResponseEntity<String> updateRole(@ApiParam("Enter MembershipId.") @PathVariable("id") int id, 
											 @ApiParam("Enter Roles.") @PathVariable("roles") String roles)
	{
		log.info("Update roles method called.");
		return new ResponseEntity<> (loginService.updateRoles(id, roles), HttpStatus.ACCEPTED);
		//Method to update roles.
	}
	
	
	@PostMapping("/admin/data")								//endpoint for accessing.
	@ApiOperation(tags="Admin CRUD",value="To add admin data.")						//Api description for swagger-ui.
	public ResponseEntity<String> getData(@ApiParam("Enter Admin details.")
										  @Valid @RequestBody AdminDto ad)
	{
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);     			//Specifies that JSON type object is required. 
		HttpEntity<AdminDto> httpEntity = new HttpEntity<>(ad, headers);	//Creation of HttpEntity of JSON type AdminDto.
		String res = restTemplate.postForObject("http://adminms:8093/adminsave", httpEntity, String.class);
		//Posting to producer microservice uri.
		
		if(res!=null&&res.length()<5) 
			return new ResponseEntity<>(Constants.ACC + "\nId = " + res, HttpStatus.ACCEPTED);
		return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST); //Response sent back.
	}
	
	@PostMapping("/user/data")								//endpoint for accessing.
	@ApiOperation(tags="User CRUD",value="To add customer data.")					//Api description for swagger-ui.
	public ResponseEntity<String> getData(@ApiParam("Enter Customer details.")
										  @Valid @RequestBody CustomerDto cd)
	{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);     			//Specifies that JSON type object is required. 
		HttpEntity<CustomerDto> httpEntity = new HttpEntity<>(cd, headers);	//Creation of HttpEntity of JSON type AdminDto.
		String res = restTemplate.postForObject("http://adminms:8093/usersave", httpEntity, String.class);	//Posting to producer microservice uri.
		
		if(res!=null&&res.equals(Constants.SAVED)) 
			return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
		return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST); //Response sent back.
	}
	
	@PutMapping("/admin/update/id={id}")							//endpoint for accessing.
	@ApiOperation(tags="Admin CRUD",value="To update admin data.")								//Api description for swagger-ui.
	public ResponseEntity<String> updateData(@ApiParam("Enter Admin details.") @Valid @RequestBody AdminDto ad,
											 @ApiParam("Admin Id.") @PathVariable("id") int id)
	{
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);     			//Specifies that JSON type object is required. 
		HttpEntity<AdminDto> httpEntity = new HttpEntity<>(ad, headers);	//Creation of HttpEntity of JSON type AdminDto.
		restTemplate.put("http://adminms:8093/adminupdate/id="+id, httpEntity);	//Sent to producer microservice uri.
		return new ResponseEntity<>(Constants.ACK, HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/user/update")							//endpoint for accessing.
	@ApiOperation(tags="User CRUD",value="To update customer data.")				//Api description for swagger-ui.
	public ResponseEntity<String> updateData(@ApiParam("Enter Customer details.") 
											 @Valid @RequestBody CustomerDto cd)
	{
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);     			//Specifies that JSON type object is required. 
		HttpEntity<CustomerDto> httpEntity = new HttpEntity<>(cd, headers);	//Creation of HttpEntity of JSON type AdminDto.
		restTemplate.put("http://adminms:8093/userupdate", httpEntity);	//Sent to producer microservice uri.
		return new ResponseEntity<>(Constants.ACK, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/admin/get/id={id}")				//endpoint for accessing.
	@ApiOperation(tags="Admin CRUD",value="To get admin data.")				//Api description for swagger-ui.
	public ResponseEntity<String> getData(@ApiParam("Admin Id.") @PathVariable("id") int id)
	{
		
		AdminDto admin = restTemplate.getForObject("http://adminms:8093/adminget/id="+id, AdminDto.class);
		//Getting from producer microservice uri.
		String res="";
		if(admin!=null) 
			{
				res = admin.toString();
				return new ResponseEntity<>(res, HttpStatus.FOUND);
			}
		return new ResponseEntity<>(Constants.NF, HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/admin/getalladmins")				//endpoint for accessing.
	@ApiOperation(tags="Admin CRUD",value="To get all admins data.")		//Api description for swagger-ui.
	public ResponseEntity<String> getAdminData()
	{
		
		String admins = restTemplate.getForObject("http://adminms:8093/admingetall", String.class);
		//Getting from producer microservice uri.
		if(admins==null) 
			{
				admins = Constants.NF;
				return new ResponseEntity<>(admins, HttpStatus.NOT_FOUND);
			}
		
		return new ResponseEntity<>(admins, HttpStatus.FOUND);
	}
	
	@GetMapping("/admin/getallusers")				//endpoint for accessing.
	@ApiOperation(tags="Admin CRUD",value="To get all users data.")		//Api description for swagger-ui.
	public ResponseEntity<String> getUserData()
	{
		
		String admins = restTemplate.getForObject("http://adminms:8093/usergetall", String.class);
		//Getting from producer microservice uri.
		if(admins==null) 
			{
				admins = Constants.NF;
				return new ResponseEntity<>(admins, HttpStatus.NOT_FOUND);
			}
		
		return new ResponseEntity<>(admins, HttpStatus.FOUND);
	}
	
	@GetMapping("/user/get/id={aadhar}")				//endpoint for accessing.
	@ApiOperation(tags="User CRUD",value="To get customer data.")			//Api description for swagger-ui.
	public ResponseEntity<String> getData(@ApiParam("Aadhar Id.") @PathVariable("aadhar") long aadhar)
	{
		
		CustomerDto user = restTemplate.getForObject
				("http://adminms:8093/userget/id="+aadhar, CustomerDto.class);
		//Getting from producer microservice uri.
		String res="";
		if(user!=null) 
			{
				res = user.toString();
				return new ResponseEntity<>(res, HttpStatus.FOUND);
			}
		return new ResponseEntity<>(Constants.NF, HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/admin/deletedata/id={adminid}")			//endpoint for accessing.
	@ApiOperation(tags="Admin CRUD",value="To delete admin data.")				//Api description for swagger-ui.
	public ResponseEntity<String> deleteAdmin(@ApiParam("Admin Id.") @PathVariable("adminid") int id)
	{
		restTemplate.delete("http://adminms:8093/admindelete/id="+id);
		//Deleting from producer microservice uri.
		return new ResponseEntity<>(Constants.ACK, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/user/deletedata/id={aadhar}")			//endpoint for accessing.
	@ApiOperation(tags="User CRUD",value="To delete customer data.")			//Api description for swagger-ui.
	public ResponseEntity<String> deleteUser(@ApiParam("Aadhar Id.") @PathVariable("aadhar") long id)
	{
		restTemplate.delete("http://adminms:8093/userdelete/id="+id);
		//Deleting from producer microservice uri.
		return new ResponseEntity<>(Constants.ACK, HttpStatus.ACCEPTED);
	}

	@GetMapping("/access_denied")
	public ModelAndView accessDenied() {
		ModelAndView model= new ModelAndView();
		model.setViewName("errors/access_denied");
		return model;
	}
	
	@GetMapping("/user/")
	public String home()
	{
		return "Welcome to homepage";
	}
	
}