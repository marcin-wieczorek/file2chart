package com.file2chart.api.v1;

import com.file2chart.model.dto.output.TableOutput;
import com.file2chart.service.CompressionService;
import com.file2chart.service.TableService;
import com.file2chart.service.utils.CryptoService;
import com.file2chart.service.utils.JsonConverter;
import com.file2chart.service.FileFormatValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    private final CompressionService compressionService;

    @PostMapping("/table/html")
    public String upload(@RequestParam("file") MultipartFile file) throws Exception {
        FileFormatValidator.validate(file);
        TableOutput tableOutput = tableService.generateTable(file);
        var json = jsonConverter.toJSON(tableOutput, false);
        var compressedJson = compressionService.compress(json);
        var encryptedMessage = cryptoService.encrypt(compressedJson);
        var encryptedMessageParam = URLEncoder.encode(encryptedMessage, StandardCharsets.UTF_8.toString());
        return "redirect:/draw/table?data=" + encryptedMessageParam;
    }

    @RequestMapping("/draw/table")
    public String draw(@RequestParam String data, Model model) throws Exception {
        var decryptedMessage = cryptoService.decrypt(data);
        var decompressedJson = compressionService.decompress(decryptedMessage);
        TableOutput tableOutput = jsonConverter.toObject(decompressedJson, TableOutput.class);
        model.addAttribute("tableOutput", tableOutput);
        return "table/index";
    }
}
