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

/**
 * @author isaaclo
 * Tests for the BoardController class
 */
@TestMethodOrder(OrderAnnotation.class)
class BoardControllerTests {
    @BeforeAll
    static void setup() {
        RestAssured.registerParser("text/plain", Parser.JSON);

        // Set Up Player
        with()
                .body("{\"username\":\"test\",\"password\":\"world\"}")
                .contentType("application/json")	// set this to json type
                .post("/users/register");

        // Set up Game
        with()
                .body("{\"gameLobbyName\":\"lobbyForTests\"}")
                .contentType("application/json")	// set this to json type
                .post("/games/add");
    }

    @Order(1)
    @Test
    void receiveClueFromSpymasterTest() {
        with()
                .body("{\"user\": {\"username\": \"test\"}, \"role\": \"SPYMASTER\", \"team\": \"RED\"}")
                .contentType("application/json")
                .put("/games/1/clue/goodClue/3")

                .then()

                .statusCode(200)
                .assertThat()
                .body("message", equalTo("success"));
    }

    @Order(2)
    @Test
    void receiveClueFromOperativeTest() {
        with()
                .body("{\"user\": {\"username\": \"test\"}, \"role\": \"OPERATIVE\", \"team\": \"RED\"}")
                .contentType("application/json")
                .put("/games/1/clue/goodClue/3")

                .then()

                .statusCode(200)
                .assertThat()
                .body("message", equalTo("incorrect clue giver"));
    }

    @AfterAll
    static void cleanUp() {
        //with()	// clear games
        //.delete("/games/removeall/98765");

        with() 	// clear users
                .delete("users/clearusers/75362");
    }
}
