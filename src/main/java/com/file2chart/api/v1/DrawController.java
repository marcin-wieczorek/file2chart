package com.file2chart.api.v1;

import com.file2chart.model.dto.output.ChartOutput;
import com.file2chart.model.dto.output.TableOutput;
import com.file2chart.service.DrawingService;
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

    private final DrawingService drawingService;

    @RequestMapping("/draw/table")
    public String drawTable(@RequestParam String data, Model model) throws Exception {
        TableOutput tableOutput = drawingService.drawTable(data);
        model.addAttribute("tableOutput", tableOutput);
        return "table/index";
    }

    @RequestMapping("/draw/chart/bar")
    public String drawChartBar(@RequestParam String data, Model model) throws Exception {
        ChartOutput chartOutput = drawingService.drawBarChart(data);
        model.addAttribute("chartOutput", chartOutput);
        return "chart/bar/index";
    }
}
