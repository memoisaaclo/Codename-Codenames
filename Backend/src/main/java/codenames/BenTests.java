package codenames;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;

class BenTests {
 
	
	//@Test
	void testRegisterUser() {
		post("/users/register").then().statusCode(200).assertThat().body("loginCount", equalTo(3));
	}
	
	/**
	 * tries to log in the test user
	 */
	@Test
	void testLoginUser() {
		with()
		.body("{\"username\":\"test\",\"password\":\"world\"}")
		.post("/users/login")
		
		.then()
		.statusCode(200)
		.assertThat()
		.body("message", equalTo("success"));
	}
	
	/**
	 * tests we get a response from getAllUsers
	 */
	@Test
	void testGetUsers() {	
		get("/users/getallusers").then().statusCode(200).assertThat();
	}
	
	

}
