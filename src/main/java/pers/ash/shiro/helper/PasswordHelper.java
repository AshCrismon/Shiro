package pers.ash.shiro.helper;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

import pers.ash.shiro.model.systemmanage.User;

public class PasswordHelper {
	
	private static RandomNumberGenerator rng = new SecureRandomNumberGenerator();
	private static String ENCRYPT_ALGORITHM = "md5";
	private static int HASH_TIMES = 2;
	
	public static void encrypt(User user){
		String password = user.getPassword();
		String salt = rng.nextBytes().toHex();
		String hashedPassword = encrypt(password, salt);
		user.setSalt(salt);
		user.setPassword(hashedPassword);
	}
	
	public static String encrypt(String password, String salt){
		return new SimpleHash(ENCRYPT_ALGORITHM, password, salt, HASH_TIMES).toHex();
	}
	
	public static String getEncryptAlgorithm() {
		return ENCRYPT_ALGORITHM;
	}
	
	public static void setEncryptAlgorithm(String algorithm){
		ENCRYPT_ALGORITHM = algorithm;
	}

	public static int getHashTimes() {
		return HASH_TIMES;
	}

	public static void setHashTimes(int hashTimes) {
		HASH_TIMES = hashTimes;
	}
}
