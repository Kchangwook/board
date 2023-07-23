package project.mini.board.util;

import lombok.extern.slf4j.Slf4j;
import project.mini.board.exception.CipherException;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@Slf4j
public class Aes256Util {
	private static final String CIPHER_TYPE = "AES/CBC/PKCS5Padding";
	private static final String ALGORITHM = "AES";

	public static String encrypt(String key, String text) {
		try {
			Cipher cipher = Cipher.getInstance(CIPHER_TYPE);
			SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);
			IvParameterSpec ivParamSpec = new IvParameterSpec(key.substring(0, 16).getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

			byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8.name()));
			return Base64.getEncoder().encodeToString(encrypted);
		} catch (Exception exception) {
			log.error("cipher exception: {}", exception);
			throw new CipherException("encrypt fail");
		}
	}

	public static String decrypt(String key, String cipherText) {
		try {
			Cipher cipher = Cipher.getInstance(CIPHER_TYPE);
			SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);
			IvParameterSpec ivParamSpec = new IvParameterSpec(key.substring(0, 16).getBytes());
			cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

			byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
			byte[] decrypted = cipher.doFinal(decodedBytes);
			return new String(decrypted, StandardCharsets.UTF_8.name());
		} catch (Exception exception) {
			log.error("cipher exception: {}", exception);
			throw new CipherException("decrypt fail");
		}
	}

}
