package oauthclientmanager.clientmanager.user;

import org.springframework.stereotype.Service;

import oauthclientmanager.clientmanager.clientdetails.OauthClientDetails;
import oauthclientmanager.clientmanager.clientdetails.OauthClientDetailsRepo;


@Service
public class UserService {
		
	private OauthClientDetailsRepo detailsRepo;
	private UserRepo userRepo;
	
	public UserService(OauthClientDetailsRepo detailsRepo, UserRepo userRepo) {
		this.detailsRepo = detailsRepo;
		this.userRepo = userRepo;
	}

	public User getOrCreateUser(String userName) {
		User user;
		userName = userName.trim();
		user = userRepo.findOne(userName);
		if (user == null) user = new User(userName);
		return user;
	}
	
	public OauthClientDetails getDetails(String clientId){
		OauthClientDetails clientDetails;
		clientDetails = detailsRepo.findOne(clientId);
		
		if(clientDetails == null) throw new RuntimeException("Oauth Client details does not exist");
		
		return clientDetails;
	}

	public OauthClientDetails save(OauthClientDetails details) {
		return detailsRepo.save(details);
		
	}

	public User save(User user) {
		return userRepo.save(user);
	}



}
