package com.file2chart.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "File2Chart API",
                version = "v1",
                description = "File2Chart API documentation.",
                contact = @Contact(
                        name = "Marcin Wieczorek",
                        email = "kontakt@marcinwieczorek.pl",
                        extensions = @Extension(
                                name = "x-contact", properties = {
                                @ExtensionProperty(name = "name", value = "Marcin")
                        })
                ),
                license = @License(
                        name = "License: Freeware"
                )
        ),
        security = {
                @SecurityRequirement(
                        name = "Currently permit all | Ultimately over token."
                )
        }
)
public class OpenApiConfiguration {
}
