package com.file2chart.api.v1;

import com.file2chart.service.TableService;
import com.file2chart.service.files.FileValidator;
import com.file2chart.service.utils.SecureRedirectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class TableController {

    private final TableService tableService;
    private final SecureRedirectService secureRedirectService;

    @PostMapping("/table/html")
    public String upload(@RequestParam("file") MultipartFile file) {
        FileValidator.validateFileFormat(file);
        return secureRedirectService.generateSecureRedirect(tableService::generateTable, file, "/draw/table");
    }
}
