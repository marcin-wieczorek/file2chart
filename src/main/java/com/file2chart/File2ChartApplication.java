package com.file2chart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class File2ChartApplication {

    public static void main(String[] args) {
        SpringApplication.run(File2ChartApplication.class, args);
    }

}
