package project.mini.board.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Aes256Util {
	private static final String CIPHER_TYPE = "AES/CBC/PKCS5Padding";
	private static final String ALGORITHM = "AES";

	public static String encrypt(String key, String text) throws Exception {
		Cipher cipher = Cipher.getInstance(CIPHER_TYPE);
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);
		IvParameterSpec ivParamSpec = new IvParameterSpec(key.substring(0, 16).getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

		byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8.name()));
		return Base64.getEncoder().encodeToString(encrypted);
	}

	public String decrypt(String key, String cipherText) throws Exception {
		Cipher cipher = Cipher.getInstance(CIPHER_TYPE);
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);
		IvParameterSpec ivParamSpec = new IvParameterSpec(key.substring(0, 16).getBytes());
		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

		byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
		byte[] decrypted = cipher.doFinal(decodedBytes);
		return new String(decrypted, StandardCharsets.UTF_8.name());
	}

}
