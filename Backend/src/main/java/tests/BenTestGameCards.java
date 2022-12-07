package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import codenames.Color;
import codenames.Game;
import codenames.GameCard;

/**
 * @author Benjamin Kelly
 */
@TestMethodOrder(OrderAnnotation.class)	// enforce a specific order 
public class BenTestGameCards {

	static GameCard c;
	
	@BeforeAll
	static void setup(){
		c = new GameCard(1, "asdf", Color.RED, new Game());
	}
	
	@Order(0)
	@Test
	void testCreateCard() {
		c = new GameCard(1, Color.BLACK);
		return;
	}
	
	@Order(2)
	@Test
	void testCreateCard2() {
		c = new GameCard(1, "asdf", Color.RED, new Game());
	}
	
	@Order(1)
	@Test
	void testCreateCard3() {
		c = new GameCard(Color.RED);
	}
	
	@Order(3)
	@Test
	void testSetId() {
		c.setId(100);
		assertEquals(c.getId().intValue(), 100);
	}
	
	@Order(4)
	@Test
	void testSetPosition() {
		c.setGamePosition(120);
	}
	
	@Order(5)
	@Test
	void testGetPosition() {
		assertEquals(c.getGamePosition().intValue(), 120);
	}
	
	@Order(6)
	@Test
	void testGetWord() {
		assertEquals(c.getWord(), "asdf");
	}
	
	@Order(7)
	@Test
	void testGetColor() {
		assertEquals(c.getColor(), Color.RED);
	}
	
	@Order(8)
	@Test
	void testSetGame() {
		c.setGame(null);
	}
	
	@Order(9)
	@Test
	void testGetGame() {
		assertEquals(c.getGame(), null);
	}
	
	@Order(10)
	@Test
	void testEquals() {
		GameCard a = c;
		assertEquals(a.equals(c), true);
	}
	
	@Order(11)
	@Test
	void testEquals1() {
		c = new GameCard(1, "asdf", Color.RED, new Game());
		GameCard a = new GameCard(1, "asdf", Color.RED, new Game());
		assertEquals(a.equals(c), true);
	}
	
	@Order(12)
	@Test
	void testEquals2() {
		c = new GameCard(1, "asdf", Color.RED, new Game());
		Object a = new GameCard(1, "asdf", Color.RED, new Game());
		assertEquals(a.equals(c), true);
	}
	
	@Order(13)
	@Test
	void testHashCode() {
		assertNotEquals(c.hashCode(), null);
	}
	
}
