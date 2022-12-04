package codenames;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;

class BenTests {
 
	@Test
	void test() {

		String a = "foo";
        String b = "FOO";
        assertThat(a, equalToIgnoringCase(b));
		
	}

}
