package com.file2chart.config.security;

import com.file2chart.model.enums.Algorithm;
import com.file2chart.service.utils.Base64Utils;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StreamUtils;

import javax.crypto.KeyGenerator;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private static final String PRIVATE_KEY_PATH = "keys/private_key.pem";
    private static final String PRIVATE_KEY_FILE_PREFIX = "-----BEGIN PRIVATE KEY-----";
    private static final String PRIVATE_KEY_FILE_SUFFIX = "-----END PRIVATE KEY-----";

    private static final String PUBLIC_KEY_PATH = "keys/public_key.pem";
    private static final String PUBLIC_KEY_FILE_PREFIX = "-----BEGIN PUBLIC KEY-----";
    private static final String PUBLIC_KEY_FILE_SUFFIX = "-----END PUBLIC KEY-----";

    @Bean
    public KeyPair keyPair() {
        try (InputStream privateKeyInputStream = new ClassPathResource(PRIVATE_KEY_PATH).getInputStream();
             InputStream publicKeyInputStream = new ClassPathResource(PUBLIC_KEY_PATH).getInputStream()) {

            String privateKeyAsString = StreamUtils.copyToString(privateKeyInputStream, Charset.defaultCharset());
            String privateKeyPEM = privateKeyAsString.replaceAll(PRIVATE_KEY_FILE_PREFIX + "|" + PRIVATE_KEY_FILE_SUFFIX + "|" + System.lineSeparator(),
                                                                 "");

            String publicKeyAsString = StreamUtils.copyToString(publicKeyInputStream, Charset.defaultCharset());
            String publicKeyPEM = publicKeyAsString.replaceAll(PUBLIC_KEY_FILE_PREFIX + "|" + PUBLIC_KEY_FILE_SUFFIX + "|" + System.lineSeparator(),
                                                                "");

            byte[] privateKeyPEMDecoded = Base64Utils.decryptToBytes(privateKeyPEM);
            byte[] publicKeyPEMDecoded = Base64Utils.decryptToBytes(publicKeyPEM);

            KeyFactory keyFactory = KeyFactory.getInstance(Algorithm.RSA.name());
            PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyPEMDecoded));
            PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyPEMDecoded));

            return new KeyPair(publicKey, privateKey);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean
    public KeyGenerator keyGenerator() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(Algorithm.AES.name());
        keyGenerator.init(256);
        return keyGenerator;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
