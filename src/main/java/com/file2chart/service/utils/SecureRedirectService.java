package com.file2chart.service.utils;

import com.file2chart.service.compression.DataCompressionService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

@Slf4j
@Service
@AllArgsConstructor
public class SecureRedirectService {

    private final JsonConverter jsonConverter;
    private final CryptoService cryptoService;
    private final DataCompressionService dataCompressionService;

    @SneakyThrows
    public <T, R> String generateSecureRedirect(Function<T, R> function, T input, String redirect) {
        R result = function.apply(input);
        var json = jsonConverter.toJSON(result, false);
        var compressedJson = dataCompressionService.compress(json);
        var encryptedMessage = cryptoService.encrypt(compressedJson);
        var encryptedMessageParam = URLEncoder.encode(encryptedMessage, StandardCharsets.UTF_8.toString());
        return "redirect:" + redirect + "?data=" + encryptedMessageParam;
    }
}
