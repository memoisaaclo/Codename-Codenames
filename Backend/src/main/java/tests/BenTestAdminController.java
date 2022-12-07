package tests;

import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;

/**
 * @author Benjamin Kelly
 */
@TestMethodOrder(OrderAnnotation.class)	// enforce a specific order 
public class BenTestAdminController {

	/**
	 * force rest-assured to use JSON parsing for plaintext responses
	 * ...because rest-assured is annoying
	 */
	@BeforeAll
	static void setup() {
		RestAssured.registerParser("text/plain", Parser.JSON);
	}
 
	@Order(1)
	@Test
	void testGetAllWords() {
		with()
		.get("/admin/cards/all")
		
		.then()
		
		.statusCode(200);
	}
	
	@Order(2)
	@Test
	void testAddWord() {
		with()
		.body("{\"word\":\"test\"}")
		.contentType("application/json")
		.put("/admin/cards/add")
		
		.then()
		
		.statusCode(200)
		.assertThat()
		.body("message", equalTo("success"));
	}
	
	@Order(3)
	@Test
	void testAddWordFail() {
		with()
		.body("{\"word\":\"test\"}")
		.contentType("application/json")
		.put("/admin/cards/add")
		
		.then()
		
		.statusCode(200)
		.assertThat()
		.body("message", equalTo("failure"));
	}
	
	@Order(4)
	@Test
	void testAddWordPathVar() {
		with()
		.put("/admin/cards/add/{card}", "test2")
		
		.then()
		
		.statusCode(200)
		.assertThat()
		.body("message", equalTo("success"));
	}
	
	@Order(5)
	@Test
	void testAddWordPathVarFail() {
		with()
		.put("/admin/cards/add/{card}", "test2")
		
		.then()
		
		.statusCode(200)
		.assertThat()
		.body("message", equalTo("failure"));
	}
	
	@Order(6)
	@Test
	void testRemoveCard() {
		with()
		.body("{\"word\":\"test\"}")
		.contentType("application/json")
		.delete("/admin/cards/remove")
		
		.then()
		
		.statusCode(200)
		.assertThat()
		.body("message", equalTo("success"));
	}
	
	@Order(7)
	@Test
	void testRemoveCardFail() {
		with()
		.body("{\"word\":\"test\"}")
		.contentType("application/json")
		.delete("/admin/cards/remove")
		
		.then()
		
		.statusCode(200)
		.assertThat()
		.body("message", equalTo("failure"));
	}
	
	@Order(8)
	@Test
	void testRemoveCardPath() {
		with()
		.delete("/admin/cards/remove/{card}", "test2")
		
		.then()
		
		.statusCode(200)
		.assertThat()
		.body("message", equalTo("success"));
	}
	
	@Order(9)
	@Test
	void testRemoveCardPathFail() {
		with()
		.delete("/admin/cards/remove/{card}", "test2")
		
		.then()
		
		.statusCode(200)
		.assertThat()
		.body("message", equalTo("failure"));
	}
	
	@Order(10)
	@Test
	void testDeleteAllCards() {
		with()
		.delete("/admin/cards/removeall/98765")
		
		.then()
		
		.statusCode(200)
		.assertThat()
		.body("message", equalTo("success"));
	}
	
	@Order(10)
	@Test
	void testGetPaths() {
		with()
		.get("/admin/paths")
		
		.then()
		
		.statusCode(200);
	}
	
}
