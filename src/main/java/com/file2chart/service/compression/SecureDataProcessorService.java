package com.file2chart.service.compression;

import com.file2chart.service.utils.CryptoService;
import com.file2chart.service.utils.JsonConverter;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@AllArgsConstructor
public class SecureDataProcessorService {

    private final JsonConverter jsonConverter;
    private final CryptoService cryptoService;
    private final DataCompressionService dataCompressionService;

    @SneakyThrows
    public <T> String serialize(T input) {
        var json = jsonConverter.toJSON(input, false);
        var compressedJson = dataCompressionService.compress(json);
        var encryptedMessage = cryptoService.encrypt(compressedJson);

        return URLEncoder.encode(encryptedMessage, StandardCharsets.UTF_8.toString());
    }

    @SneakyThrows
    public <T> T deserialize(String input, Class<T> clazz) {
        var decryptedMessage = cryptoService.decrypt(input);
        var decompressedJson = dataCompressionService.decompress(decryptedMessage);
        T object = jsonConverter.toObject(decompressedJson, clazz);

        return object;
    }
}
