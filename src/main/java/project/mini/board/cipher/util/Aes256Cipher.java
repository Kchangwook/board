package project.mini.board.cipher.util;

import lombok.extern.slf4j.Slf4j;
import project.mini.board.cipher.enumeration.AesKey;
import project.mini.board.cipher.exception.CipherException;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@Slf4j
public class Aes256Cipher {
    private static final String CIPHER_TYPE = "AES/CBC/PKCS5Padding";
    private static final String ALGORITHM = "AES";

    public static String encrypt(AesKey key, String text) {
        try {
            String keyString = key.getKeyString();
            Cipher cipher = Cipher.getInstance(CIPHER_TYPE);
            SecretKeySpec keySpec = new SecretKeySpec(keyString.getBytes(), ALGORITHM);
            IvParameterSpec ivParamSpec = new IvParameterSpec(keyString.substring(0, 16).getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

            byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8.name()));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception exception) {
            log.error("cipher exception: {}", exception);
            throw new CipherException("encrypt fail");
        }
    }

    public static String decrypt(AesKey key, String cipherText) {
        try {
            String keyString = key.getKeyString();
            Cipher cipher = Cipher.getInstance(CIPHER_TYPE);
            SecretKeySpec keySpec = new SecretKeySpec(keyString.getBytes(), ALGORITHM);
            IvParameterSpec ivParamSpec = new IvParameterSpec(keyString.substring(0, 16).getBytes());
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
