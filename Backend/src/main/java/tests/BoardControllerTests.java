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

        // Set up Cards
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
                        + "    {\"word\":\"Cyclone\"}"
                        + "]")
                .contentType("application/json")	// set this to json type
                .put("/admin/cards/addbulk")
                .then()
                .statusCode(200)
                .assertThat()
                .body("message", equalTo("success"));


    }

    @Order(1)
    @Test
    public void generateBoardTest() {
        with()
                .get("/games/1/generateboard")

                .then()

                .statusCode(200)
                .assertThat()
                .body("message", equalTo("success"));
    }

    @Order(2)
    @Test
    public void receiveClueFromSpymasterTest() {
        with()
                .body("{\"user\": {\"username\": \"test\"}, \"role\": \"SPYMASTER\", \"team\": \"RED\"}")
                .contentType("application/json")
                .put("/games/1/clue/goodClue/4")

                .then()

                .statusCode(200)
                .assertThat()
                .body("message", equalTo("success"));
    }

    @Order(3)
    @Test
    public void receiveClueFromOperativeTest() {
        with()
                .body("{\"user\": {\"username\": \"test\"}, \"role\": \"OPERATIVE\", \"team\": \"RED\"}")
                .contentType("application/json")
                .put("/games/1/clue/goodClue/3")

                .then()

                .statusCode(200)
                .assertThat()
                .body("message", equalTo("incorrect clue giver"));
    }

    @Order(4)
    @Test
    public void receiveClueNegativeGuessesTest() {
        with()
                .body("{\"user\": {\"username\": \"test\"}, \"role\": \"SPYMASTER\", \"team\": \"RED\"}")
                .contentType("application/json")
                .put("/games/1/clue/goodClue/-3")

                .then()

                .statusCode(200)
                .assertThat()
                .body("message", equalTo("error negative guesses"));
    }

    @Order(5)
    @Test
    public void receiveClueEmptyClueTest() {
        with()
                .body("{\"user\": {\"username\": \"test\"}, \"role\": \"SPYMASTER\", \"team\": \"RED\"}")
                .contentType("application/json")
                .put("/games/1/clue/ /3")

                .then()

                .statusCode(200)
                .assertThat()
                .body("message", equalTo("error: clue is empty"));
    }

    @Order(6)
    @Test
    public void receiveGuessOperativeTest() {
        with()
                .body("{\"user\": {\"username\": \"test\"}, \"role\": \"OPERATIVE\", \"team\": \"RED\"}")
                .contentType("application/json")
                .put("/games/1/guess/0")

                .then()

                .statusCode(200)
                .assertThat()
                .body("message", equalTo("success"));
    }

    @Order(6)
    @Test
    public void receiveGuessSpymasterTest() {
        with()
                .body("{\"user\": {\"username\": \"test\"}, \"role\": \"SPYMASTER\", \"team\": \"RED\"}")
                .contentType("application/json")
                .put("/games/1/guess/0")

                .then()

                .statusCode(200)
                .assertThat()
                .body("message", equalTo("incorrect guesser"));
    }

    @Order(7)
    @Test
    public void endGuessingTest() {
        with()
                .put("/games/1/endguessing")

                .then()

                .statusCode(200)
                .assertThat()
                .body("message", equalTo("success"));
    }

    @Order(8)
    @Test
    public void guessesAvailableTest() {
        with()
                .get("/games/1/guessesavailable")

                .then()

                .statusCode(200)
                .assertThat()
                .body("numGuesses", equalTo("0"));
    }

    @Order(9)
    @Test
    public void turnColorTest() {
        with()
                .get("/games/1/turncolor")

                .then()

                .statusCode(200)
                .assertThat()
                .body("turnColor", equalTo("BLUE"));
    }

    @Order(10)
    @Test
    public void currentClueTest() {
        with()
                .get("/games/1/clue")

                .then()

                .statusCode(200)
                .assertThat()
                .body("clue", equalTo("goodClue"));
    }

    @Order(11)
    @Test
    public void clueListTest() {
        with()
                .get("/games/1/cluelist")

                .then()

                .statusCode(200)
                .assertThat()
                .body("1", equalTo("goodClue"));
    }

    @Order(12)
    @Test
    public void isRevealedTest() {
        with()
                .get("/games/1/isrevealed")

                .then()

                .statusCode(200)
                .assertThat()
                .body("message", equalTo(null)); // AKA no invalid state message
    }

    @Order(13)
    @Test
    public void getColorsTest() {
        with()
                .get("/games/1/colors")

                .then()

                .statusCode(200)
                .assertThat()
                .body("message", equalTo(null)); // AKA no invalid state message
    }

    @Order(14)
    @Test
    public void generateStatesTest() {
        with()
                .get("/games/1/generatestates")

                .then()

                .statusCode(200)
                .assertThat()
                .body("message", equalTo("success"));
    }

    @Order(15)
    @Test
    public void generateWordsTest() {
        with()
                .get("/games/1/generatewords")

                .then()

                .statusCode(200)
                .assertThat()
                .body("message", equalTo("success"));
    }

    @AfterAll
    static void cleanUp() {
        // clear users
        with().delete("users/clearusers/75362");
    }


}
