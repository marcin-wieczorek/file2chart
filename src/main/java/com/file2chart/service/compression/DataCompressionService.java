package com.file2chart.service.compression;

import com.file2chart.exceptions.custom.DataCompressionException;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@Service
public class DataCompressionService {

    public String compress(String data) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
            gzipOutputStream.write(data.getBytes());
            gzipOutputStream.close();
            return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            throw new DataCompressionException("Error occurred while compressing data.", e);
        }
    }

    public String decompress(String compressedData) {
        byte[] compressedBytes = Base64.getDecoder().decode(compressedData);
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(compressedBytes);
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = gzipInputStream.read(buffer)) > 0) {
                byteArrayOutputStream.write(buffer, 0, length);
            }
            return byteArrayOutputStream.toString();
        } catch (IOException e) {
            throw new DataCompressionException("Error occurred while decompressing data.", e);
        }
    }
}

