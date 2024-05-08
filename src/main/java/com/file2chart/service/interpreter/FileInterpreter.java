package com.file2chart.service.interpreter;

import com.file2chart.model.dto.local.ChartModel;
import com.file2chart.model.dto.local.MapModel;
import com.file2chart.model.dto.local.TableModel;
import com.file2chart.model.enums.FileFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileInterpreter {

    ChartModel toChart(MultipartFile file);

    MapModel toMap(MultipartFile file);

    TableModel toTable(MultipartFile file);

    List<FileFormat> getSupportedFileFormat();

}
