package com.file2chart.api.v1;

import com.file2chart.model.dto.output.ChartOutput;
import com.file2chart.service.ChartService;
import com.file2chart.service.DataCompressionService;
import com.file2chart.service.FileFormatValidator;
import com.file2chart.service.utils.CryptoService;
import com.file2chart.service.utils.JsonConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ChartController {

    private final ChartService chartService;
    private final JsonConverter jsonConverter;
    private final CryptoService cryptoService;
    private final DataCompressionService dataCompressionService;

    @PostMapping("/chart/bar/html")
    public String upload(@RequestParam("file") MultipartFile file) throws Exception {
        FileFormatValidator.validate(file);
        ChartOutput chartOutput = chartService.generateChart(file);
        var json = jsonConverter.toJSON(chartOutput, false);
        var compressedJson = dataCompressionService.compress(json);
        var encryptedMessage = cryptoService.encrypt(compressedJson);
        var encryptedMessageParam = URLEncoder.encode(encryptedMessage, StandardCharsets.UTF_8.toString());
        return "redirect:/draw/chart/bar?data=" + encryptedMessageParam;
    }

}
