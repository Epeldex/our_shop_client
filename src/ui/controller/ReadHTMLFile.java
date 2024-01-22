package ui.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.nio.charset.StandardCharsets;

public class ReadHTMLFile {

    public String getMail() {
        // Specify the path to your HTML file
        try {

            URL url = getClass().getResource("/resources/Email.html");
            File file = new File(url.getPath());

            return readHtmlFile(file);

        } catch (Exception e) {
            e.printStackTrace();

        }
        return "";
    }

    private String readHtmlFile(File file) throws IOException {
        // Use Paths.get to create a Path object from the file path
        Path path = Paths.get(file.getAbsolutePath());

        // Use Files.readAllBytes to read the content of the file into a byte array
        byte[] bytes = Files.readAllBytes(path);

        // Convert the byte array to a string using UTF-8 encoding
        String htmlContent = new String(bytes, StandardCharsets.UTF_8);

        return htmlContent;
    }
}