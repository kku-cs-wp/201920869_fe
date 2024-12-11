package kr.ac.kku.cs.wp.wsd.tools.secure;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * CryptoUtil
 * 
 * @author kimjunwoo
 * @since 2024. 12. 11.
 * @version 1.0
 */
public class CryptoUtil {

	private static final Logger logger = LogManager.getLogger(CryptoUtil.class);
	
	/**
	 * 
	 * @param message
	 * @param salt
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String hash(String message, byte[] salt) throws NoSuchAlgorithmException {
		StringBuffer hasedMessageAndSalt = new StringBuffer();
	
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		
		md.update(salt);
		
		byte[] hashedMessage = md.digest(message.getBytes());
		
		String base64Salt = Base64.getEncoder().encodeToString(salt);
		String base64HashedMessage = Base64.getEncoder().encodeToString(hashedMessage);
		
		hasedMessageAndSalt.append(base64HashedMessage).append(base64Salt);
		
		logger.debug("salt:" + base64Salt);
		logger.debug("hashedMessage:" + base64HashedMessage);
		logger.debug("hasedMessageAndSalt:" + hasedMessageAndSalt.toString());
			
		return hasedMessageAndSalt.toString();
	}
	
	/**
	 * 
	 * @return
	 */
	public static byte[] genSalt() {
		//generate salt
		SecureRandom sr = new SecureRandom();
		byte[] salt = new byte[16];
		sr.nextBytes(salt);

		return salt;
	}
	
	/**
	 * 
	 * @param base64HassedMessageAndSalt
	 * @return
	 */
	public static byte[] extractSalt(String base64HassedMessageAndSalt) {
		byte[] salt;
		
		String base64Salt = base64HassedMessageAndSalt.substring(44);
		
		logger.debug("base64Salt:" + base64Salt );
		
		salt = Base64.getDecoder().decode(base64Salt);
		
		return salt;
	}
	
	/**
	 * 
	 * @param base64hasedMessageAndSalt
	 * @param message
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static boolean isMatch(String base64hasedMessageAndSalt, String message) throws NoSuchAlgorithmException{
		boolean isMatch = false;
		
		if (base64hasedMessageAndSalt == null || message == null) {
			logger.debug("is null");
			throw new NullPointerException();
		}
		
		byte[] salt = extractSalt(base64hasedMessageAndSalt);
		
		String base64HasedMAS = hash(message, salt);
		
		if (base64hasedMessageAndSalt.equals(base64HasedMAS)) {
			isMatch = true;
		}
		
		return isMatch;
	}
}