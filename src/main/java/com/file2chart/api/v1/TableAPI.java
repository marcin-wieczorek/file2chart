package com.file2chart.api.v1;

import com.file2chart.exceptions.ApiError;
import com.file2chart.model.dto.input.EmbeddedTableVisualizationRequest;
import com.file2chart.model.dto.input.ImageTableVisualizationRequest;
import com.file2chart.model.dto.output.VisualizationHashResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Table")
@RequestMapping("/v1")
public interface TableAPI {

    @Operation(description = "Generate hash from your data source based on file.")
    @ApiResponse(responseCode = "200", description = "Visualization hash", content = @Content(schema = @Schema(implementation = VisualizationHashResponse.class)))
    @ApiResponse(
            responseCode = "400",
            content = @Content(schema = @Schema(implementation = ApiError.class),
                    examples = {
                            @ExampleObject(
                                    name = "RECORD_VALIDATION_ERROR",
                                    value = """
                                            {
                                               "status":"BAD_REQUEST",
                                               "code":"RECORD_VALIDATION_ERROR",
                                               "timestamp":"13-05-2024 10:29:29",
                                               "message":"An error occurred while validating records.",
                                               "debugMessage":""
                                            }
                                            """
                            ),
                            @ExampleObject(
                                    name = "HEADER_VALIDATION_ERROR",
                                    value = """
                                            {
                                               "status":"BAD_REQUEST",
                                               "code":"HEADER_VALIDATION_ERROR",
                                               "timestamp":"13-05-2024 10:29:29",
                                               "message":"An error occurred while validating headers.",
                                               "debugMessage":""
                                            }
                                            """
                            ),
                            @ExampleObject(
                                    name = "FILE_FORMAT_ERROR",
                                    value = """
                                            {
                                               "status":"BAD_REQUEST",
                                               "code":"FILE_FORMAT_ERROR",
                                               "timestamp":"13-05-2024 10:29:29",
                                               "message":"An error occurred while validating file format.",
                                               "debugMessage":""
                                            }
                                            """
                            ),
                            @ExampleObject(
                                    name = "FILE_INTERPRETER_ERROR",
                                    value = """
                                            {
                                               "status":"BAD_REQUEST",
                                               "code":"FILE_INTERPRETER_ERROR",
                                               "timestamp":"13-05-2024 10:29:29",
                                               "message":"An error occurred while loading file interpreter.",
                                               "debugMessage":""
                                            }
                                            """
                            ),
                            @ExampleObject(
                                    name = "HASH_VALIDATION_ERROR",
                                    value = """
                                            {
                                               "status":"BAD_REQUEST",
                                               "code":"HASH_VALIDATION_ERROR",
                                               "timestamp":"13-05-2024 10:29:29",
                                               "message":"An error occurred while validating hash. Please check if the hash is correct and try again.",
                                               "debugMessage":""
                                            }
                                            """
                            ),
                    }))
    @ApiResponse(
            responseCode = "401",
            content = @Content(schema = @Schema(implementation = ApiError.class),
                    examples = {
                            @ExampleObject(
                                    name = "AUTHORIZATION_BAD_CREDENTIALS",
                                    value = """
                                            {
                                               "status":"UNAUTHORIZED",
                                               "code":"AUTHORIZATION_BAD_CREDENTIALS",
                                               "timestamp":"13-05-2024 10:29:29",
                                               "message":"Bad credentials.",
                                               "debugMessage":""
                                            }
                                            """
                            )
                    }))
    @ApiResponse(
            responseCode = "403",
            content = @Content(schema = @Schema(implementation = ApiError.class),
                    examples = {
                            @ExampleObject(
                                    name = "AUTHORIZATION_FORBIDDEN_ACCESS",
                                    value = """
                                            {
                                               "status":"FORBIDDEN",
                                               "code":"AUTHORIZATION_FORBIDDEN_ACCESS",
                                               "timestamp":"13-05-2024 10:29:29",
                                               "message":"Bad credentials.",
                                               "debugMessage":""
                                            }
                                            """
                            )
                    }))
    @ApiResponse(
            responseCode = "404",
            content = @Content(schema = @Schema(implementation = ApiError.class),
                    examples = {
                            @ExampleObject(
                                    name = "RESOURCE_NOT_FOUND",
                                    value = """
                                            {
                                               "status":"NOT_FOUND",
                                               "code":"RESOURCE_NOT_FOUND",
                                               "timestamp":"13-05-2024 10:29:29",
                                               "message":"Resource not found.",
                                               "debugMessage":""
                                            }
                                            """
                            )
                    }))
    @ApiResponse(
            responseCode = "500",
            content = @Content(schema = @Schema(implementation = ApiError.class),
                    examples = {
                            @ExampleObject(
                                    name = "UNKNOWN",
                                    value = """
                                            {
                                               "status":"INTERNAL_SERVER_ERROR",
                                               "code":"UNKNOWN",
                                               "timestamp":"13-05-2024 10:29:29",
                                               "message":"Unexpected error.",
                                               "debugMessage":""
                                            }
                                            """
                            )
                    }))
    @PostMapping(value = "/table/hash", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VisualizationHashResponse> generateTableHash(@RequestParam MultipartFile file);

    @Operation(description = "Generate visualisation data based on provided hash as a embedded html page.")
    @ApiResponse(responseCode = "200", description = "Visualization data as a embedded html page.", content = @Content(schema = @Schema(implementation = String.class), mediaType = MediaType.TEXT_HTML_VALUE))
    @ApiResponse(
            responseCode = "400",
            content = @Content(schema = @Schema(implementation = ApiError.class), mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "RECORD_VALIDATION_ERROR",
                                    value = """
                                            {
                                               "status":"BAD_REQUEST",
                                               "code":"RECORD_VALIDATION_ERROR",
                                               "timestamp":"13-05-2024 10:29:29",
                                               "message":"An error occurred while validating records.",
                                               "debugMessage":""
                                            }
                                            """
                            ),
                            @ExampleObject(
                                    name = "HEADER_VALIDATION_ERROR",
                                    value = """
                                            {
                                               "status":"BAD_REQUEST",
                                               "code":"HEADER_VALIDATION_ERROR",
                                               "timestamp":"13-05-2024 10:29:29",
                                               "message":"An error occurred while validating headers.",
                                               "debugMessage":""
                                            }
                                            """
                            ),
                            @ExampleObject(
                                    name = "FILE_FORMAT_ERROR",
                                    value = """
                                            {
                                               "status":"BAD_REQUEST",
                                               "code":"FILE_FORMAT_ERROR",
                                               "timestamp":"13-05-2024 10:29:29",
                                               "message":"An error occurred while validating file format.",
                                               "debugMessage":""
                                            }
                                            """
                            ),
                            @ExampleObject(
                                    name = "FILE_INTERPRETER_ERROR",
                                    value = """
                                            {
                                               "status":"BAD_REQUEST",
                                               "code":"FILE_INTERPRETER_ERROR",
                                               "timestamp":"13-05-2024 10:29:29",
                                               "message":"An error occurred while loading file interpreter.",
                                               "debugMessage":""
                                            }
                                            """
                            ),
                            @ExampleObject(
                                    name = "HASH_VALIDATION_ERROR",
                                    value = """
                                            {
                                               "status":"BAD_REQUEST",
                                               "code":"HASH_VALIDATION_ERROR",
                                               "timestamp":"13-05-2024 10:29:29",
                                               "message":"An error occurred while validating hash. Please check if the hash is correct and try again.",
                                               "debugMessage":""
                                            }
                                            """
                            ),
                    }))
    @ApiResponse(
            responseCode = "401",
            content = @Content(schema = @Schema(implementation = ApiError.class), mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "AUTHORIZATION_BAD_CREDENTIALS",
                                    value = """
                                            {
                                               "status":"UNAUTHORIZED",
                                               "code":"AUTHORIZATION_BAD_CREDENTIALS",
                                               "timestamp":"13-05-2024 10:29:29",
                                               "message":"Bad credentials.",
                                               "debugMessage":""
                                            }
                                            """
                            )
                    }))
    @ApiResponse(
            responseCode = "403",
            content = @Content(schema = @Schema(implementation = ApiError.class), mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "AUTHORIZATION_FORBIDDEN_ACCESS",
                                    value = """
                                            {
                                               "status":"FORBIDDEN",
                                               "code":"AUTHORIZATION_FORBIDDEN_ACCESS",
                                               "timestamp":"13-05-2024 10:29:29",
                                               "message":"Bad credentials.",
                                               "debugMessage":""
                                            }
                                            """
                            )
                    }))
    @ApiResponse(
            responseCode = "404",
            content = @Content(schema = @Schema(implementation = ApiError.class), mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "RESOURCE_NOT_FOUND",
                                    value = """
                                            {
                                               "status":"NOT_FOUND",
                                               "code":"RESOURCE_NOT_FOUND",
                                               "timestamp":"13-05-2024 10:29:29",
                                               "message":"Resource not found.",
                                               "debugMessage":""
                                            }
                                            """
                            )
                    }))
    @ApiResponse(
            responseCode = "500",
            content = @Content(schema = @Schema(implementation = ApiError.class), mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "UNKNOWN",
                                    value = """
                                            {
                                               "status":"INTERNAL_SERVER_ERROR",
                                               "code":"UNKNOWN",
                                               "timestamp":"13-05-2024 10:29:29",
                                               "message":"Unexpected error.",
                                               "debugMessage":""
                                            }
                                            """
                            )
                    }))
    @PostMapping(value = "/table/visualization/embedded", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = {MediaType.TEXT_HTML_VALUE})
    String generateEmbeddedVisualization(@RequestBody EmbeddedTableVisualizationRequest input, Model model);

    @Operation(description = "Generate visualisation data based on provided hash as a image (.png)")
    @ApiResponse(responseCode = "200", description = "Visualization data as a image (.png) .", content = @Content(schema = @Schema(implementation = Byte.class), mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE))
    @ApiResponse(
            responseCode = "400",
            content = @Content(schema = @Schema(implementation = ApiError.class), mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "RECORD_VALIDATION_ERROR",
                                    value = """
                                            {
                                               "status":"BAD_REQUEST",
                                               "code":"RECORD_VALIDATION_ERROR",
                                               "timestamp":"13-05-2024 10:29:29",
                                               "message":"An error occurred while validating records.",
                                               "debugMessage":""
                                            }
                                            """
                            ),
                            @ExampleObject(
                                    name = "HEADER_VALIDATION_ERROR",
                                    value = """
                                            {
                                               "status":"BAD_REQUEST",
                                               "code":"HEADER_VALIDATION_ERROR",
                                               "timestamp":"13-05-2024 10:29:29",
                                               "message":"An error occurred while validating headers.",
                                               "debugMessage":""
                                            }
                                            """
                            ),
                            @ExampleObject(
                                    name = "FILE_FORMAT_ERROR",
                                    value = """
                                            {
                                               "status":"BAD_REQUEST",
                                               "code":"FILE_FORMAT_ERROR",
                                               "timestamp":"13-05-2024 10:29:29",
                                               "message":"An error occurred while validating file format.",
                                               "debugMessage":""
                                            }
                                            """
                            ),
                            @ExampleObject(
                                    name = "FILE_INTERPRETER_ERROR",
                                    value = """
                                            {
                                               "status":"BAD_REQUEST",
                                               "code":"FILE_INTERPRETER_ERROR",
                                               "timestamp":"13-05-2024 10:29:29",
                                               "message":"An error occurred while loading file interpreter.",
                                               "debugMessage":""
                                            }
                                            """
                            ),
                            @ExampleObject(
                                    name = "HASH_VALIDATION_ERROR",
                                    value = """
                                            {
                                               "status":"BAD_REQUEST",
                                               "code":"HASH_VALIDATION_ERROR",
                                               "timestamp":"13-05-2024 10:29:29",
                                               "message":"An error occurred while validating hash. Please check if the hash is correct and try again.",
                                               "debugMessage":""
                                            }
                                            """
                            ),
                    }))
    @ApiResponse(
            responseCode = "401",
            content = @Content(schema = @Schema(implementation = ApiError.class), mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "AUTHORIZATION_BAD_CREDENTIALS",
                                    value = """
                                            {
                                               "status":"UNAUTHORIZED",
                                               "code":"AUTHORIZATION_BAD_CREDENTIALS",
                                               "timestamp":"13-05-2024 10:29:29",
                                               "message":"Bad credentials.",
                                               "debugMessage":""
                                            }
                                            """
                            )
                    }))
    @ApiResponse(
            responseCode = "403",
            content = @Content(schema = @Schema(implementation = ApiError.class), mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "AUTHORIZATION_FORBIDDEN_ACCESS",
                                    value = """
                                            {
                                               "status":"FORBIDDEN",
                                               "code":"AUTHORIZATION_FORBIDDEN_ACCESS",
                                               "timestamp":"13-05-2024 10:29:29",
                                               "message":"Bad credentials.",
                                               "debugMessage":""
                                            }
                                            """
                            )
                    }))
    @ApiResponse(
            responseCode = "404",
            content = @Content(schema = @Schema(implementation = ApiError.class), mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "RESOURCE_NOT_FOUND",
                                    value = """
                                            {
                                               "status":"NOT_FOUND",
                                               "code":"RESOURCE_NOT_FOUND",
                                               "timestamp":"13-05-2024 10:29:29",
                                               "message":"Resource not found.",
                                               "debugMessage":""
                                            }
                                            """
                            )
                    }))
    @ApiResponse(
            responseCode = "500",
            content = @Content(schema = @Schema(implementation = ApiError.class), mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "UNKNOWN",
                                    value = """
                                            {
                                               "status":"INTERNAL_SERVER_ERROR",
                                               "code":"UNKNOWN",
                                               "timestamp":"13-05-2024 10:29:29",
                                               "message":"Unexpected error.",
                                               "debugMessage":""
                                            }
                                            """
                            )
                    }))
    @PostMapping(value = "/table/visualization/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    ResponseEntity<InputStreamResource> generateImageVisualization(@RequestBody ImageTableVisualizationRequest input, Model model);
}
