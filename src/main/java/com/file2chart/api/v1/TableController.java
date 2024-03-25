package com.file2chart.api.v1;

import com.file2chart.model.dto.output.TableOutput;
import com.file2chart.service.DataCompressionService;
import com.file2chart.service.FileFormatValidator;
import com.file2chart.service.TableService;
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
public class TableController {

    private final TableService tableService;
    private final JsonConverter jsonConverter;
    private final CryptoService cryptoService;
    private final DataCompressionService dataCompressionService;

    @PostMapping("/table/html")
    public String upload(@RequestParam("file") MultipartFile file) throws Exception {
        FileFormatValidator.validate(file);
        TableOutput tableOutput = tableService.generateTable(file);
        var json = jsonConverter.toJSON(tableOutput, false);
        var compressedJson = dataCompressionService.compress(json);
        var encryptedMessage = cryptoService.encrypt(compressedJson);
        var encryptedMessageParam = URLEncoder.encode(encryptedMessage, StandardCharsets.UTF_8.toString());
        return "redirect:/draw/table?data=" + encryptedMessageParam;
    }
}
