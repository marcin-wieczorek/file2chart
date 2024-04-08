package com.file2chart.controller;

import com.file2chart.service.VisualizationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class DrawController {

    private final VisualizationService visualizationService;

    @RequestMapping("/draw/chart/bar")
    public String drawBarChart(@RequestParam String data, Model model) {
        model.addAttribute("data", visualizationService.drawChart(data));
        return "chart/bar/index";
    }

    @RequestMapping("/draw/chart/doughnut")
    public String drawDoughnutChart(@RequestParam String data, Model model) {
        model.addAttribute("data", visualizationService.drawChart(data));
        return "chart/doughnut/index";
    }

    @RequestMapping("/draw/chart/line")
    public String drawLineChart(@RequestParam String data, Model model) {
        model.addAttribute("data", visualizationService.drawChart(data));
        return "chart/line/index";
    }

    @RequestMapping("/draw/chart/pie")
    public String drawPieChart(@RequestParam String data, Model model) {
        model.addAttribute("data", visualizationService.drawChart(data));
        return "chart/pie/index";
    }

    @RequestMapping("/draw/chart/polar-area")
    public String drawPolarAreaChart(@RequestParam String data, Model model) {
        model.addAttribute("data", visualizationService.drawChart(data));
        return "chart/polar-area/index";
    }

    @RequestMapping("/draw/chart/radar")
    public String drawRadarChart(@RequestParam String data, Model model) {
        model.addAttribute("data", visualizationService.drawChart(data));
        return "chart/radar/index";
    }

}
