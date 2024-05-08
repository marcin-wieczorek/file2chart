package com.file2chart.service.compression;

import com.file2chart.exceptions.custom.SerializationException;
import com.file2chart.service.utils.CryptoService;
import com.file2chart.service.utils.JsonConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class SecureDataProcessorService {

    private final JsonConverter jsonConverter;
    private final CryptoService cryptoService;
    private final DataCompressionService dataCompressionService;

    public <T> String serialize(T input) {
        var json = jsonConverter.toJSON(input, false);
        var compressedJson = dataCompressionService.compress(json);
        var encryptedMessage = cryptoService.encrypt(compressedJson);

        //return URLEncoder.encode(encryptedMessage, StandardCharsets.UTF_8.toString());
        return encryptedMessage;
    }

    public <T> T deserialize(String input, Class<T> clazz) {
        try {
            var decryptedMessage = cryptoService.decrypt(input);
            var decompressedJson = dataCompressionService.decompress(decryptedMessage);
            T object = jsonConverter.toObject(decompressedJson, clazz);

            return object;
        } catch (Exception e) {
            throw new SerializationException("Error occurred during hash deserialization: " + e.getMessage(), e);
        }
    }
}
