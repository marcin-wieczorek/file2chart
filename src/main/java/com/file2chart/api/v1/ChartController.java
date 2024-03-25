package com.file2chart.api.v1;

import com.file2chart.service.ChartService;
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
public class ChartController {

    private final ChartService chartService;
    private final SecureRedirectService secureRedirectService;

    @PostMapping("/chart/bar/html")
    public String redirectToBarChart(@RequestParam("file") MultipartFile file) {
        FileValidator.validateFileFormat(file);
        return secureRedirectService.generateSecureRedirect(chartService::generateChart, file, "/draw/chart/bar");
    }

    @PostMapping("/chart/line/html")
    public String redirectToLineChart(@RequestParam("file") MultipartFile file) {
        FileValidator.validateFileFormat(file);
        return secureRedirectService.generateSecureRedirect(chartService::generateChart, file, "/draw/chart/line");
    }
}
