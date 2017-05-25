package oauthclientmanager.clientmanager;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.io.UnsupportedEncodingException;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.jayway.jsonpath.Configuration;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@DirtiesContext
public abstract class BaseIntegrationTest {

	@Autowired public MockMvc mvc;
	
	public MockHttpServletRequestBuilder _post(String urlTemplate){
		return post(urlTemplate)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
	}
	
	public MockHttpServletRequestBuilder _get(String urlTemplate){
		return get(urlTemplate)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
	}
	
	public MockHttpServletRequestBuilder _put(String urlTemplate){
		return put(urlTemplate)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
	}
	
	
	public String contentBodyAsString(MvcResult result) throws UnsupportedEncodingException{
		return result.getResponse().getContentAsString();
	}
	
	public Object contentBodyAsJsonDoc(MvcResult result) throws UnsupportedEncodingException {
		Object jdoc = Configuration.defaultConfiguration().jsonProvider().parse(result.getResponse().getContentAsString());
		return jdoc;
	}
	
}
