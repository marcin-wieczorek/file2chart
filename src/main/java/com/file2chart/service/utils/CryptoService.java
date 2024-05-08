package com.file2chart.service.utils;

import com.file2chart.exceptions.custom.DataEncryptionException;
import com.file2chart.model.enums.Algorithm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

@Service
@AllArgsConstructor
public class CryptoService {

    private final KeyPair keyPair;
    private final KeyGenerator keyGenerator;

    public String encrypt(String data) {
        // Generating AES key
        SecretKey secretKey = keyGenerator.generateKey();

        // Encrypting data with AES
        byte[] encryptedData = encryptAES(data.getBytes(), secretKey);

        // Encrypting AES key with RSA
        byte[] encryptedKey = encryptRSA(secretKey.getEncoded());

        // Combining encrypted AES data and RSA key
        byte[] result = combineEncryptedDataWithEncryptedKey(encryptedData, encryptedKey);

        // Encoding encrypted data to Base64
        return Base64Utils.encrypt(result);
    }

    public String decrypt(String data) {
        // Decoding encrypted data from Base64
        byte[] encryptedBytes = Base64Utils.decryptToBytes(data);

        // Separating encrypted data into AES key and message
        byte[] encryptedMessage = getEncryptedDataFromEncryptedBytes(encryptedBytes);
        byte[] encryptedKey = getEncryptedKeyFromEncryptedBytes(encryptedBytes);

        // Decrypting AES key with RSA
        byte[] decryptedKey = decryptRSA(encryptedKey);
        SecretKeySpec secretKeySpec = new SecretKeySpec(decryptedKey, Algorithm.AES.name());

        // Decrypting AES data
        byte[] decryptedData = decryptAES(encryptedMessage, secretKeySpec);

        return new String(decryptedData);
    }

    private byte[] encryptRSA(byte[] data) {
        try {
            Cipher aesCipher = Cipher.getInstance(Algorithm.RSA.name());
            aesCipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
            return aesCipher.doFinal(data);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new DataEncryptionException("Error occurred during RSA encryption", e);
        }
    }

    private byte[] decryptRSA(byte[] data) {
        try {
            Cipher aesCipher = Cipher.getInstance(Algorithm.RSA.name());
            aesCipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
            return aesCipher.doFinal(data);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new DataEncryptionException("Error occurred during RSA decryption", e);
        }
    }

    private byte[] encryptAES(byte[] data, SecretKey key) {
        try {
            Cipher aesCipher = Cipher.getInstance(Algorithm.AES.name());
            aesCipher.init(Cipher.ENCRYPT_MODE, key);
            return aesCipher.doFinal(data);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new DataEncryptionException("Error occurred during AES encryption", e);
        }
    }

    private byte[] decryptAES(byte[] encryptedData, SecretKey key) {
        try {
            Cipher aesCipher = Cipher.getInstance(Algorithm.AES.name());
            aesCipher.init(Cipher.DECRYPT_MODE, key);
            return aesCipher.doFinal(encryptedData);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new DataEncryptionException("Error occurred during AES decryption", e);
        }
    }

    private byte[] combineEncryptedDataWithEncryptedKey(byte[] encryptedData, byte[] encryptedKey) {
        byte[] result = new byte[encryptedKey.length + encryptedData.length];
        System.arraycopy(encryptedKey, 0, result, 0, encryptedKey.length);
        System.arraycopy(encryptedData, 0, result, encryptedKey.length, encryptedData.length);
        return result;
    }

    private byte[] getEncryptedDataFromEncryptedBytes(byte[] encryptedBytes) {
        byte[] encryptedMessage = new byte[encryptedBytes.length - 256];
        System.arraycopy(encryptedBytes, 256, encryptedMessage, 0, encryptedBytes.length - 256);
        return encryptedMessage;
    }

    private byte[] getEncryptedKeyFromEncryptedBytes(byte[] encryptedBytes) {
        byte[] encryptedKey = new byte[256]; // RSA key length (you can adjust as needed)
        System.arraycopy(encryptedBytes, 0, encryptedKey, 0, 256);
        return encryptedKey;
    }
}
