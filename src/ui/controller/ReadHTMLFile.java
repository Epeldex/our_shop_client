package ui.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.nio.charset.StandardCharsets;
import logic.exceptions.LogicException;

public class ReadHTMLFile {

    public String getMail() throws LogicException {
        // Specify the path to your HTML file
        try {
            InputStream mailStream = getClass().getClassLoader().getResourceAsStream("resources/Email.html");
            byte[] mailBytes = new byte[mailStream.available()];
            mailStream.read(mailBytes);

            return new String(mailBytes);
        } catch (Exception e) {
            throw new LogicException(e.getMessage(), e);
        }
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
