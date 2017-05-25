package oauthclientmanager.clientmanager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import oauthclientmanager.clientmanager.clientdetails.OauthClientDetailsRepo;
import oauthclientmanager.clientmanager.user.User;
import oauthclientmanager.clientmanager.user.UserRepo;
import oauthclientmanager.clientmanager.user.UserService;

public class UserServiceTest {
	
	UserService sutUserService;
	UserRepo mockUserRepo;
	OauthClientDetailsRepo mockDetailsRepo;
	
	
	@Before
	public void init(){
		mockUserRepo = mock(UserRepo.class);
		mockDetailsRepo = mock(OauthClientDetailsRepo.class);
		sutUserService = new UserService(mockDetailsRepo, mockUserRepo);
	}
	
	@Test
	public void testCreateUser(){
		when(mockUserRepo.findOne("DOES_NOT_EXIST")).thenReturn(null);
		User user = sutUserService.getOrCreateUser("DOES_NOT_EXIST");
		assertThat(user.getUserName()).isEqualTo("DOES_NOT_EXIST"); 
		assertThat(user.getCreTimest()).isNull();
		
	}
	
	@Test
	public void testGetExistingUser(){
		User user = new User("jamesnovak");
		Date date = new Date();
		user.setCreTimest(date);
		
		when(mockUserRepo.findOne("jamesnovak")).thenReturn(user);
		User user2 = sutUserService.getOrCreateUser("jamesnovak");
		
		assertTrue(user == user2);
		assertThat(user.getUserName()).isEqualTo(user2.getUserName()); 
		assertThat(user.getCreTimest()).isEqualTo(date);
		
	}
}
