package oauthclientmanager.clientmanager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import javax.json.Json;
import javax.transaction.Transactional;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.jayway.jsonpath.JsonPath;

@Transactional
public class ApiIntegrationTest extends BaseIntegrationTest {

	@Test
	public void createSingleClientDetails() throws Exception {
		MvcResult result = mvc.perform(_post("/clientDetails")
				.content(Json.createObjectBuilder()
					.add("userName", "jnovak")
		            .build().toString()))
				
				.andDo(MockMvcResultHandlers.print())
				
				//.andExpect(jsonPath("$.userId", 			Matchers.is(2)))
				.andExpect(jsonPath("$.userName", 			Matchers.is("jnovak")))
				//.andExpect(jsonPath("$.creTimest", 			Matchers.isEmptyOrNullString()))
				.andExpect(jsonPath("$.clientDetails", 		Matchers.hasSize(1)))
				//.andExpect(jsonPath("$.clientDetails[0].clientId", 	Matchers.is())
				
				.andReturn()
			;	
	}
	
	@Test
	public void createMultipleClientDetails() throws Exception {
		// Step 1: Create first details
		// Validate it has a single element array
		MvcResult firstResult = mvc.perform(_post("/clientDetails")
				.content(Json.createObjectBuilder()
					.add("userName", "jnovak")
		            .build().toString()))
				
				.andDo(MockMvcResultHandlers.print())
				
				//.andExpect(jsonPath("$.userId", 			Matchers.is(2)))
				.andExpect(jsonPath("$.userName", 			Matchers.is("jnovak")))
				//.andExpect(jsonPath("$.creTimest", 			Matchers.isEmptyOrNullString()))
				.andExpect(jsonPath("$.clientDetails", 		Matchers.hasSize(1)))
				//.andExpect(jsonPath("$.clientDetails[0].clientId", 	Matchers.is())
				
				.andReturn()
			;	
		
		// Step 2: Create second details
		// Validate the array is now 2
		MvcResult secondResult = mvc.perform(_post("/clientDetails")
				// POST
				// {
				//    "userId" : 2
				// }
				//
				.content("{\"userName\":\"jnovak\"}"))
				
				.andDo(MockMvcResultHandlers.print())
				
				//.andExpect(jsonPath("$.userId", 			Matchers.is(2)))
				.andExpect(jsonPath("$.userName", 			Matchers.is("jnovak")))
				//.andExpect(jsonPath("$.creTimest", 			Matchers.isEmptyOrNullString()))
				.andExpect(jsonPath("$.clientDetails", 		Matchers.hasSize(2)))
				//.andExpect(jsonPath("$.clientDetails[0].clientId", 	Matchers.is())
				
				.andReturn()
			;
		
		// Extract Info
		String firstClientId = JsonPath.read(contentBodyAsString(secondResult), "$.clientDetails[0].clientId");
		String secondClientId = JsonPath.read(contentBodyAsString(secondResult), "$.clientDetails[1].clientId");
		
		assertThat(firstClientId).isNotEqualTo(secondClientId);
	}	
	
	@Test
	public void testChangeClientSecret() throws Exception {
		MvcResult firstResult = mvc.perform(_post("/clientDetails")
				.content(Json.createObjectBuilder()
					.add("userName", "jnovak")
		            .build().toString()))
				
				.andDo(MockMvcResultHandlers.print())
				
				//.andExpect(jsonPath("$.userId", 			Matchers.is(2)))
				.andExpect(jsonPath("$.userName", 			Matchers.is("jnovak")))
				//.andExpect(jsonPath("$.creTimest", 			Matchers.isEmptyOrNullString()))
				.andExpect(jsonPath("$.clientDetails", 		Matchers.hasSize(1)))
				//.andExpect(jsonPath("$.clientDetails[0].clientId", 	Matchers.is())
				
				.andReturn()
			;	
		
		
		// Extract Info
		String origClientSecret = JsonPath.read(contentBodyAsString(firstResult), "$.clientDetails[0].clientSecret");
		String firstClientId = JsonPath.read(contentBodyAsString(firstResult), "$.clientDetails[0].clientId");
		
		MvcResult secondResult = mvc.perform(_put("/clientDetails/"+firstClientId))
							
				.andDo(MockMvcResultHandlers.print())
				
				.andExpect(jsonPath("$.clientSecret", 		Matchers.not(origClientSecret)))
				
				.andReturn()
			;	
			
		
	}
	
	@Test
	public void requestClientDetails() throws Exception {
		MvcResult result = mvc.perform(_post("/clientDetails")
				.content(Json.createObjectBuilder()
					.add("userName", "jnovak")
		            .build().toString()))
				
				.andDo(MockMvcResultHandlers.print())
				
				.andExpect(jsonPath("$.userName", 			Matchers.is("jnovak")))
				.andExpect(jsonPath("$.clientDetails", 		Matchers.hasSize(1)))
				
				.andReturn()
			
			;	
		
		MvcResult secondResult = mvc.perform(_post("/clientDetails")
				.content("{\"userName\":\"jnovak\"}"))
				
				.andDo(MockMvcResultHandlers.print())
				
				.andExpect(jsonPath("$.userName", 			Matchers.is("jnovak")))
				.andExpect(jsonPath("$.clientDetails", 		Matchers.hasSize(2)))
				
				.andReturn()
			;
		
		String firstResponseClientId = JsonPath.read(contentBodyAsString(result), "$.clientDetails[0].clientId");
		String secondResponseFirstClientId = JsonPath.read(contentBodyAsString(secondResult), "$.clientDetails[0].clientId");
		String secondResponseSecondClientId = JsonPath.read(contentBodyAsString(secondResult), "$.clientDetails[1].clientId");
		
		assertThat(secondResponseFirstClientId).isNotEqualTo(secondResponseSecondClientId);
		
		MvcResult getResult = mvc.perform(_get("/clientDetails?userName=jnovak"))
				
				.andDo(MockMvcResultHandlers.print())
				.andExpect(jsonPath("$", 			Matchers.hasSize(2)))
				//.andExpect(jsonPath("$.clientDetails", 		Matchers.hasSize(1)))
				.andExpect(jsonPath("$[*].clientId",  Matchers.containsInAnyOrder(secondResponseFirstClientId , secondResponseSecondClientId)))
				
				.andReturn()
			;	
		
		
	}
	
}
