// 代码生成时间: 2025-09-17 06:28:44
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * PasswordEncryptDecryptTool is a utility class for encrypting and decrypting passwords using AES algorithm.
 */
public class PasswordEncryptDecryptTool {

    private static final String ALGORITHM = "AES";
    private static final int KEY_SIZE = 128;

    /**
     * Encrypts the given password using AES algorithm.
     *
     * @param password the password to encrypt
     * @return encrypted password in Base64 encoded string
     * @throws Exception if encryption fails
     */
    public static String encryptPassword(String password) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        SecureRandom secureRandom = new SecureRandom();
        keyGenerator.init(KEY_SIZE, secureRandom);
        SecretKey secretKey = keyGenerator.generateKey();

        byte[] keyBytes = secretKey.getEncoded();
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, ALGORITHM);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        byte[] encryptedBytes = cipher.doFinal(password.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * Decrypts the given encrypted password using AES algorithm.
     *
     * @param encryptedPassword the encrypted password to decrypt
     * @return decrypted password
     * @throws Exception if decryption fails
     */
    public static String decryptPassword(String encryptedPassword) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(encryptedPassword);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, ALGORITHM);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
        return new String(decryptedBytes);
    }

    public static void main(String[] args) {
        try {
            String password = "myPassword123";
            String encryptedPassword = encryptPassword(password);
            System.out.println("Encrypted Password: " + encryptedPassword);
            String decryptedPassword = decryptPassword(encryptedPassword);
            System.out.println("Decrypted Password: " + decryptedPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
