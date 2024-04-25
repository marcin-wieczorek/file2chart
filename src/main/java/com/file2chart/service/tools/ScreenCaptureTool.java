package com.file2chart.service.tools;

import com.github.kklisura.cdt.launch.ChromeLauncher;
import com.github.kklisura.cdt.protocol.commands.Emulation;
import com.github.kklisura.cdt.protocol.commands.Page;
import com.github.kklisura.cdt.protocol.types.page.FrameTree;
import com.github.kklisura.cdt.services.ChromeDevToolsService;
import com.github.kklisura.cdt.services.ChromeService;
import com.github.kklisura.cdt.services.types.ChromeTab;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

@Service
@AllArgsConstructor
public class ScreenCaptureTool {

    private final SpringTemplateEngine templateEngine;

    @SneakyThrows
    public InputStreamResource captureScreen(Model model, String templateName) {
        Context context = new Context();

        model.asMap().forEach((key, value) -> context.setVariable(key, value));

        String htmlContent = templateEngine.process(templateName, context);

        // Create chrome launcher.
        final ChromeLauncher launcher = new ChromeLauncher();

        // Launch chrome either as headless (true) or regular (false).
        final ChromeService chromeService = launcher.launch(true);

        // Create empty tab ie about:blank.
        final ChromeTab tab = chromeService.createTab();

        // Get DevTools service to this tab
        final ChromeDevToolsService devToolsService = chromeService.createDevToolsService(tab);

        // Get Emulation interface
        Emulation emulation = devToolsService.getEmulation();

        // Set browsing to fullscreen
        emulation.setDeviceMetricsOverride(1920, 1080, 1d, false); // Width > Height

        // Get individual commands
        final Page page = devToolsService.getPage();

        // Set HTML content
        page.enable();
        page.navigate("about:blank");

        // Get frame tree
        FrameTree frameTree = page.getFrameTree();

        // Use the first frame's id
        String frameId = frameTree.getFrame().getId();

        // Set document content
        page.setDocumentContent(frameId, htmlContent);

        Thread.sleep(1000);
        // Capture screenshot
        String screenshotBase64 = page.captureScreenshot();

        // Decode base64 string to byte array
        byte[] screenshotBytes = Base64.getDecoder().decode(screenshotBase64);

        // Convert byte array to BufferedImage
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(screenshotBytes));

        // Convert BufferedImage to InputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        // Create InputStreamResource
        InputStreamResource inputStreamResource = new InputStreamResource(inputStream);

        // Close the tab and close the browser when done
        chromeService.closeTab(tab);
        launcher.close();

        return inputStreamResource;
    }
}
