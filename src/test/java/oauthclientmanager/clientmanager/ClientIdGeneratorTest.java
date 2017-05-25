package oauthclientmanager.clientmanager;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import oauthclientmanager.clientmanager.util.ClientIdGenerator;

@SpringBootTest
public class ClientIdGeneratorTest {

	private ClientIdGenerator sutClientIdGenerator;
	
	public boolean isAlphaNumeric(String s){
	    String pattern = "^[a-zA-Z0-9]*$";
	    return s.matches(pattern);
	}
	
	@Before
	public void init(){
		sutClientIdGenerator = new ClientIdGenerator();
	}
	
	@Test
	public void testId_Length(){
		assertThat(sutClientIdGenerator.generateId().length()).isEqualTo(20);
	}
	
	@Test
	public void testId_IsAlphaNumeric(){
		assertTrue(isAlphaNumeric(sutClientIdGenerator.generateId()));
	}
	
	@Test
	public void testId_IsUnique(){
		Set<String> list = new HashSet<>();
		IntStream.range(0, 10000).forEach(i -> list.add(sutClientIdGenerator.generateId()));
		assertThat(list.size()).isEqualTo(10000);
	}
	
	@Test
	public void testSecret_Length(){
		assertThat(sutClientIdGenerator.generateSecret().length()).isEqualTo(40);
	}
	
	@Test
	public void testSecret_IsAlphaNumeric(){
		assertTrue(isAlphaNumeric(sutClientIdGenerator.generateId()));
	}
	
	@Test
	public void testSecret_IsUnique(){
		Set<String> list = new HashSet<>();
		IntStream.range(0, 10000).forEach(i -> list.add(sutClientIdGenerator.generateId()));
		assertThat(list.size()).isEqualTo(10000);
	}
	
}
