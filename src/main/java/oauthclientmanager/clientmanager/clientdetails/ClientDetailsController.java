package oauthclientmanager.clientmanager.clientdetails;


import java.util.*;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import oauthclientmanager.clientmanager.user.User;
import oauthclientmanager.clientmanager.user.UserService;
import oauthclientmanager.clientmanager.util.ClientIdGenerator;


@RestController
public class ClientDetailsController {
	
	private static final Logger log = LoggerFactory.getLogger(ClientDetailsController.class);

	@Autowired
	private ClientIdGenerator generator;
	
	@Autowired
	private UserService userService;
	
	// creates new instance of client details for this particular user
	@RequestMapping(method=RequestMethod.POST, value="/clientDetails")
	public User createCredentials(@RequestBody User user){
		log.info(user.toString());
		//check to see does user exist, if user exists add client details to users collection
		//if user doesnt exist, create new user

		user = userService.getOrCreateUser(user.getUserName());
	
		//generate clientId and clientSecret randomized strings
		String clientId = generator.generateId();
		String clientSecret = generator.generateSecret();
		
		//set the newly generated strings to OauthClientDetails instance
		OauthClientDetails details = new OauthClientDetails();
		details.setClientId(clientId);
		details.setClientSecret(clientSecret);
		
		//add OauthClientDetails instance to the user's clientDetails set 
		user.addClientDetails(details);
		
		details.setUser(user);
		//need to cascade on attribute details inside user object
		return userService.save(user);
	}
	
	// creates new client secret for specific clientId on user
	@RequestMapping(method=RequestMethod.PUT, value="/clientDetails/{clientId}")
	public OauthClientDetails changeClientSecret(@PathVariable String clientId){
		log.info("resetting client secret for clientId="+clientId);
		
		//grab user client detail by clientId
		OauthClientDetails details = userService.getDetails(clientId);
		
		details.setClientSecret(generator.generateSecret());
		
		return userService.save(details);
		
	}
	
	// gets all client details on user
	@RequestMapping(method=RequestMethod.GET, value="/clientDetails")
	public Set<OauthClientDetails> request(@RequestParam String userName){
		User user = userService.getOrCreateUser(userName);
		log.info(user.toString());
		log.info(user.getClientDetails().toString());
		return user.getClientDetails();
	}
	

}
