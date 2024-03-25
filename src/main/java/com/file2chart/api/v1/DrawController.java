package com.file2chart.api.v1;

import com.file2chart.service.DrawingService;
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

    @RequestMapping("/draw/table")
    public String drawTable(@RequestParam String data, Model model) {
        return processDrawing(drawingService::drawTable, data, model, "table/index");
    }

    @RequestMapping("/draw/chart/bar")
    public String drawChartBar(@RequestParam String data, Model model) {
        return processDrawing(drawingService::drawChart, data, model, "chart/bar/index");
    }

    @RequestMapping("/draw/chart/line")
    public String drawLineBar(@RequestParam String data, Model model) {
        return processDrawing(drawingService::drawChart, data, model, "chart/line/index");
    }

    private static <T, R> String processDrawing(Function<T, R> function, T input, Model model, String view) {
        R result = function.apply(input);
        model.addAttribute("data", result);
        return view;
    }
}
