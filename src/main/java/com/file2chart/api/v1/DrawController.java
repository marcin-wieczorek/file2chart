package com.file2chart.api.v1;

import com.file2chart.service.DrawingService;
import com.file2chart.service.GoogleMapsService;
import com.file2chart.service.ImageGeneratorService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.function.Function;

@Controller
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class DrawController {

    private final DrawingService drawingService;
    private final GoogleMapsService googleMapsService;
    private final ImageGeneratorService imageGeneratorService;

    @RequestMapping("/draw/table/html")
    public String drawTableHtml(@RequestParam String data, Model model) {
        return processDrawingHtml(drawingService::drawTable, data, model, "table/index");
    }

    @RequestMapping("/draw/table/image")
    public ResponseEntity<InputStreamResource> drawTableImage(@RequestParam String data) {
        return processDrawingImage(drawingService::drawTable, data, "table/index");
    }

    @RequestMapping("/draw/map")
    public String drawMap(@RequestParam String data, Model model) {
        model.addAttribute("googleMapsScript", googleMapsService.getScript());
        return processDrawingHtml(drawingService::drawMap, data, model, "map/index");
    }

    @RequestMapping("/draw/chart/bar")
    public String drawBarChart(@RequestParam String data, Model model) {
        return processDrawingHtml(drawingService::drawChart, data, model, "chart/bar/index");
    }

    @RequestMapping("/draw/chart/doughnut")
    public String drawDoughnutChart(@RequestParam String data, Model model) {
        return processDrawingHtml(drawingService::drawChart, data, model, "chart/doughnut/index");
    }

    @RequestMapping("/draw/chart/line")
    public String drawLineChart(@RequestParam String data, Model model) {
        return processDrawingHtml(drawingService::drawChart, data, model, "chart/line/index");
    }

    @RequestMapping("/draw/chart/pie")
    public String drawPieChart(@RequestParam String data, Model model) {
        return processDrawingHtml(drawingService::drawChart, data, model, "chart/pie/index");
    }

    @RequestMapping("/draw/chart/polar-area")
    public String drawPolarAreaChart(@RequestParam String data, Model model) {
        return processDrawingHtml(drawingService::drawChart, data, model, "chart/polar-area/index");
    }

    @RequestMapping("/draw/chart/radar")
    public String drawRadarChart(@RequestParam String data, Model model) {
        return processDrawingHtml(drawingService::drawChart, data, model, "chart/radar/index");
    }

    private <T, R> String processDrawingHtml(Function<T, R> function, T input, Model model, String view) {
        R result = function.apply(input);
        model.addAttribute("data", result);
        return view;
    }

    private <T, R> ResponseEntity<InputStreamResource> processDrawingImage(Function<T, R> function, T input, String view) {
        R result = function.apply(input);

        InputStreamResource inputStreamResource = imageGeneratorService.generateImage(result, view);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "asd.png");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok()
                             .headers(headers)
                             .body(inputStreamResource);
    }
}
