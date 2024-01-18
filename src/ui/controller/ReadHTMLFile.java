package ui.controller;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.nio.charset.StandardCharsets;

public class ReadHTMLFile {

    public String getMail() {
        // Specify the path to your HTML file
        try {

            URI uri = ClassLoader.getSystemResource("com/stackoverflow/json").toURI();
            String mainPath = Paths.get(uri).toString();
            Path path = Paths.get(mainPath, "Movie.class"); 

            String filePath = getClass().getClassLoader().getResource("ui/controller/Email.html").toString();

            String htmlContent = null;

            System.out.println(filePath);
            htmlContent = readHtmlFile(filePath);

            return htmlContent;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return "";
    }

    private String readHtmlFile(String filePath) throws IOException {
        // Use Paths.get to create a Path object from the file path
        Path path = Paths.get(filePath);

        // Use Files.readAllBytes to read the content of the file into a byte array
        byte[] bytes = Files.readAllBytes(path);

        // Convert the byte array to a string using UTF-8 encoding
        String htmlContent = new String(bytes, StandardCharsets.UTF_8);

        return htmlContent;
    }
}