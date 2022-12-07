package tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

@TestMethodOrder(OrderAnnotation.class)	// enforce a specific order 
class BenTestUserController {
	
	/**
	 * force rest-assured to use JSON parsing for plaintext responses
	 * ...because rest-assured is annoying
	 */
	@BeforeAll
	static void setup() {
		RestAssured.registerParser("text/plain", Parser.JSON);
	}
 
	/**
	 * test registering a new user
	 * checks to see if the user is already registered
	 */
	@Order(1)
	@Test
	void testRegisterUser() {
		with()
		.body("{\"username\":\"testasdf\",\"password\":\"world\"}")
		.contentType("application/json")	// set this to json type
		.post("/users/register")
		
		.then()
		
		.statusCode(200)
		.assertThat()
		.body("message", equalTo("success"));
	}
	
	/**
	 * tries to log in the test user
	 * tests for the message "success"
	 */
	@Order(2)
	@Test
	void testLoginUser() {
		with()
		.body("{\"username\":\"testasdf\",\"password\":\"world\"}")
		.contentType("application/json")
		.post("/users/login")
		
		.then()
		
		.statusCode(200)
		.assertThat()
		.body("message", equalTo("success"));
		
	}
	
	@Order(3)
	@Test
	void testIfUserIsNotAdmin() {
		with()
		.get("/admin/get/{username}", "testasdf")
		
		.then()
		
		.statusCode(200)
		.assertThat()
		.body("message", equalTo("failure"));
	}
	
	@Order(4)
	@Test
	void testSetAdmin() {
		with()
		.post("/admin/set/{username}", "testasdf")
		
		.then()
		
		.statusCode(200)
		.assertThat()
		.body("message", equalTo("success"));
		
	}
	
	@Order(5)
	@Test
	void testIfUserIsAdmin() {
		with()
		.get("/admin/get/{username}", "testasdf")
		
		.then()
		
		.statusCode(200)
		.assertThat()
		.body("message", equalTo("success"));
	}
	
	@Order(6)
	@Test
	void testIncorrectLogin() {
		with()
		.body("{\"username\":\"testasdf\",\"password\":\"asdf\"}")
		.contentType("application/json")
		.post("/users/login")
		
		.then()
		
		.statusCode(200)
		.assertThat()
		.body("message", equalTo("Incorrect Credentials"));
		
	}
	
	/**
	 * tests we get a response from getAllUsers
	 * only tests that a 200 response code is given
	 */
	@Order(7)
	@Test
	void testGetUsers() {		
		get("/users/getallusers")

		.then()
		
		.statusCode(200);
	}
	
	/**
	 * test double registering
	 */
	@Order(8)
	@Test
	void testDoubleRegister() {
		with()	// test re-registering when already existing
		.body("{\"username\":\"testasdf\",\"password\":\"world\"}")
		.contentType("application/json")	// set this to json type
		.post("/users/register")
		
		.then()
		
		.statusCode(200)
		.assertThat()
		.body("message", equalTo("Username already in use"));
	}
	
	/**
	 * test deleting all users
	 */
	@Order(9)
	@Test
	void testDeleteUsers() {
		with()	// delete users
		.delete("/users/clearusers/75362");
		
		with()	// test re-registering
		.body("{\"username\":\"testasdf\",\"password\":\"world\"}")
		.contentType("application/json")	// set this to json type
		.post("/users/register")
		
		.then()
		
		.statusCode(200)
		.assertThat()
		.body("message", equalTo("success"));
	}
	
	/**
	 * test deleting all users
	 */
	@Order(10)
	@Test
	void testDeleteSpecificUser() {
		with()	// delete user
		.delete("/users/removeuser/{username}", "testasdf");
		
		with()	// test re-registering
		.body("{\"username\":\"testasdf\",\"password\":\"world\"}")
		.contentType("application/json")	// set this to json type
		.post("/users/register")
		
		.then()
		
		.statusCode(200)
		.assertThat()
		.body("message", equalTo("success"));
	}
	
	/**
	 * test deleting all users
	 */
	@Order(11)
	@Test
	void testGetUserInfo() {
		with()	// delete user
		.get("/users/{username}", "testasdf")
		.then()
		.statusCode(200)
		.assertThat()
		.body("username", equalTo("testasdf"))
		.and()
		.body("loginCount", equalTo(1));
	}
	
	@Order(12)
	@Test
	void testDeleteNonExistantUser() {
		with()	// delete user
		.delete("/users/removeuser/{username}", "doesntExist")
		.then()
		.statusCode(200)
		.assertThat()
		.body("message", equalTo("failure"));
	}
	
	@AfterAll
	static void cleanUp() {
		with()	// delete users
		.delete("/users/clearusers/75362");
	}
}
