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
class BenTestPlayerController {
	
	/**
	 * force rest-assured to use JSON parsing for plaintext responses
	 * ...because rest-assured is annoying
	 */
	@BeforeAll
	static void setup() {
		RestAssured.registerParser("text/plain", Parser.JSON);
		with()
		.body("{\"username\":\"tester\",\"password\":\"world\"}")
		.contentType("application/json")	// set this to json type
		.post("/users/register");
		
		with()
		.body("{\"username\":\"tester2\",\"password\":\"world\"}")
		.contentType("application/json")	// set this to json type
		.post("/users/register");
		
		with()
		.body("[\r\n"
				+ "    {\"word\":\"Apple\"},\r\n"
				+ "    {\"word\":\"Banana\"},\r\n"
				+ "    {\"word\":\"Pear\"},\r\n"
				+ "    {\"word\":\"Orange\"},\r\n"
				+ "    {\"word\":\"Peach\"},\r\n"
				+ "    {\"word\":\"Alex\"},\r\n"
				+ "    {\"word\":\"Isaac\"},\r\n"
				+ "    {\"word\":\"Dylan\"},\r\n"
				+ "    {\"word\":\"Jimmy\"},\r\n"
				+ "    {\"word\":\"Ben\"},\r\n"
				+ "    {\"word\":\"Mitra\"},\r\n"
				+ "    {\"word\":\"Gold\"},\r\n"
				+ "    {\"word\":\"Silver\"},\r\n"
				+ "    {\"word\":\"Platinum\"},\r\n"
				+ "    {\"word\":\"Diamond\"},\r\n"
				+ "    {\"word\":\"Bronze\"},\r\n"
				+ "    {\"word\":\"customer\"},\r\n"
				+ "    {\"word\":\"stranger\"},\r\n"
				+ "    {\"word\":\"painting\"},\r\n"
				+ "    {\"word\":\"sculpture\"},\r\n"
				+ "    {\"word\":\"Iowa\"},\r\n"
				+ "    {\"word\":\"Utah\"},\r\n"
				+ "    {\"word\":\"Kansas\"},\r\n"
				+ "    {\"word\":\"Arkansas\"},\r\n"
				+ "    {\"word\":\"Nebraska\"},\r\n"
				+ "    {\"word\":\"Missouri\"},\r\n"
				+ "    {\"word\":\"Cyclone\"},\r\n"
				+ "    {\"word\":\"Tornado\"},\r\n"
				+ "    {\"word\":\"Hurricane\"},\r\n"
				+ "    {\"word\":\"Tsunami\"}\r\n"
				+ "]")
		.contentType("application/json")	// set this to json type
		.put("/admin/cards/addbulk");
		
		with()
		.body("{\"gameLobbyName\":\"lobby2\"}")
		.contentType("application/json")	// set this to json type
		.post("/games/add");
		
		with()
		.post("/games/2/addplayer/tester");
		
		with()
		.post("/games/2/addplayer/tester2");
	}
 
	/**
	 * test registering a new user
	 * checks to see if the user is already registered
	 */
//	@Order(1)
//	@Test
//	void testGetAllPlayers() {
//		with()
//		.get("/players")
//		
//		.then()
//		
//		.statusCode(200);
//	}
	
	@Order(3)
	@Test
	void testSetTeamSuccessBLUE() {
		with()
		.post("/players/{username}/setteam/{team}", "tester", "BLUE")
		
		.then()
		
		.statusCode(200)
		.assertThat()
		.body("message", equalTo("success"));
	}
	
	@Order(4)
	@Test
	void testSetTeamSuccessRED() {
		with()
		.post("/players/{username}/setteam/{team}", "tester", "RED")
		
		.then()
		
		.statusCode(200)
		.assertThat()
		.body("message", equalTo("success"));
		
		with()
		.post("/players/{username}/setteam/{team}", "tester2", "RED")
		
		.then()
		
		.statusCode(200)
		.assertThat()
		.body("message", equalTo("success"));
	}
	
	@Order(5)
	@Test
	void testSetTeamFailureInvalidTeam() {
		with()
		.post("/players/{username}/setteam/{team}", "tester", "GREEN")
		
		.then()
		
		.statusCode(200)
		.assertThat()
		.body("message", equalTo("invalid team color"));
	}
	
	@Order(6)
	@Test
	void testSetTeamFailureCouldNotFindPlayer() {
		with()
		.post("/players/{username}/setteam/{team}", "testasdfasder", "BLUE")
		
		.then()
		
		.statusCode(200)
		.assertThat()
		.body("message", equalTo("could not find player"));
	}
	
	@Order(7)
	@Test
	void testSetRoleOperative() {
		with()
		.post("/players/{username}/setrole/{role}", "tester", "OPERATIVE")
		
		.then()
		
		.statusCode(200)
		.assertThat()
		.body("message", equalTo("success"));
	}
	
	@Order(8)
	@Test
	void testSetRoleSpymaster() {
		with()
		.post("/players/{username}/setrole/{role}", "tester", "SPYMASTER")
		
		.then()
		
		.statusCode(200)
		.assertThat()
		.body("message", equalTo("success"));
	}
	
	@Order(9)
	@Test
	void testSetRoleInvalidPlayer() {
		with()
		.post("/players/{username}/setrole/{role}", "teaasdfster", "OPERATIVE")
		
		.then()
		
		.statusCode(200)
		.assertThat()
		.body("message", equalTo("could not find player"));
	}
	
	@Order(10)
	@Test
	void testSetRoleInvalidRole() {
		with()
		.post("/players/{username}/setrole/{role}", "tester", "OPERasdfATIVE")
		
		.then()
		
		.statusCode(200)
		.assertThat()
		.body("message", equalTo("invalid role name"));
	}
	
	@Order(11)
	@Test
	void testGetSpecasdfificPlayer() {
		with()
		.body("{\"username\":\"test\",\"password\":\"world\"}")
		.contentType("application/json")	// set this to json type
		.post("/users/register")
		
		.then()
		
		.statusCode(200)
		.assertThat()
		.body("message", equalTo("success"));
	}
	
	@Order(12)
	@Test
	void testSetRoleSpymasterFailureDuplicateSpymaster() {
		with()
		.post("/players/{username}/setrole/{role}", "tester2", "SPYMASTER")
		
		.then()
		
		.statusCode(200)
		.assertThat()
		.body("message", equalTo("invalid role, team spymaster already selected"));
	}
	
	@Order(13)
	@Test
	void testGetTeam() {
		with()
		.get("/players/{username}/getteam", "tester")
		
		.then()
		
		.statusCode(200);
	}
	
	@Order(14)
	@Test
	void testGetRole() {
		with()
		.get("/players/{username}/getrole", "tester")
		
		.then()
		
		.statusCode(200);
	}
	
	
	@AfterAll
	static void cleanUp() {
		with()	// delete users
		.delete("/users/clearplayers/75362");
	}
}
