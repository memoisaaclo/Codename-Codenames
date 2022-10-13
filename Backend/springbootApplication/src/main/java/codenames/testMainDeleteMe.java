package onetoone;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class testMainDeleteMe {

	public static void main(String[] args) {
		String pwd = "test";
		byte[] password = null;
		
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-512");
			password = md.digest(pwd.getBytes(StandardCharsets.UTF_8));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String s = new String(password, StandardCharsets.UTF_8);
		System.out.println((s));
	}

}
