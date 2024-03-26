package com.file2chart.api.v1;

import com.file2chart.service.DrawingService;
import com.file2chart.service.GoogleMapsService;
import lombok.AllArgsConstructor;
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

    @RequestMapping("/draw/table")
    public String drawTable(@RequestParam String data, Model model) {
        return processDrawing(drawingService::drawTable, data, model, "table/index");
    }

    @RequestMapping("/draw/map")
    public String drawMap(@RequestParam String data, Model model) {
        model.addAttribute("googleMapsScript", googleMapsService.getScript());
        return processDrawing(drawingService::drawMap, data, model, "map/index");
    }

    @RequestMapping("/draw/chart/bar")
    public String drawBarChart(@RequestParam String data, Model model) {
        return processDrawing(drawingService::drawChart, data, model, "chart/bar/index");
    }

    @RequestMapping("/draw/chart/doughnut")
    public String drawDoughnutChart(@RequestParam String data, Model model) {
        return processDrawing(drawingService::drawChart, data, model, "chart/doughnut/index");
    }

    @RequestMapping("/draw/chart/line")
    public String drawLineChart(@RequestParam String data, Model model) {
        return processDrawing(drawingService::drawChart, data, model, "chart/line/index");
    }

    @RequestMapping("/draw/chart/pie")
    public String drawPieChart(@RequestParam String data, Model model) {
        return processDrawing(drawingService::drawChart, data, model, "chart/pie/index");
    }

    @RequestMapping("/draw/chart/polar-area")
    public String drawPolarAreaChart(@RequestParam String data, Model model) {
        return processDrawing(drawingService::drawChart, data, model, "chart/polar-area/index");
    }

    @RequestMapping("/draw/chart/radar")
    public String drawRadarChart(@RequestParam String data, Model model) {
        return processDrawing(drawingService::drawChart, data, model, "chart/radar/index");
    }

    private static <T, R> String processDrawing(Function<T, R> function, T input, Model model, String view) {
        R result = function.apply(input);
        model.addAttribute("data", result);
        return view;
    }
}
