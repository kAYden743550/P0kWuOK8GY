// 代码生成时间: 2025-10-03 03:06:45
// FolderStructureOrganizer.java

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

public class FolderStructureOrganizer {

    private static final String[] EXTENSIONS = {".jpg", ".jpeg", ".png", ".gif", ".bmp", ".txt", ".pdf", ".doc", ".docx", ".xls", ".xlsx", ".ppt", ".pptx"};
    private static final String[] FOLDERS = {"Images", "Documents", "Spreadsheets", "Presentations"};

    public static void main(String[] args) {
        try {
            if (args.length != 1) {
                System.err.println("Usage: java FolderStructureOrganizer <source directory>");
                System.exit(1);
            }

            Path sourcePath = Paths.get(args[0]);
            if (!Files.isDirectory(sourcePath)) {
                throw new IllegalArgumentException("The provided path is not a directory.");
            }

            organizeFiles(sourcePath);
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error organizing folder structure: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private static void organizeFiles(Path sourcePath) throws IOException {
        File sourceDir = sourcePath.toFile();
        Arrays.stream(FOLDERS).forEach(folderName -> {
            File folder = new File(sourceDir, folderName);
            if (!folder.exists() && !folder.mkdirs()) {
                throw new IOException("Failed to create folder: " + folder.getAbsolutePath());
            }
        });

        Arrays.stream(sourceDir.listFiles()).forEach(file -> {
            if (file.isFile()) {
                String fileName = file.getName().toLowerCase();
                String extension = getExtension(fileName);
                if (extension != null) {
                    String folderName = getFolderName(extension);
                    File targetFolder = new File(sourceDir, folderName);

                    if (!targetFolder.exists() && !targetFolder.mkdirs()) {
                        throw new IOException("Failed to create folder: " + targetFolder.getAbsolutePath());
                    }

                    File targetFile = new File(targetFolder, fileName);
                    if (!file.renameTo(targetFile)) {
                        throw new IOException("Failed to move file: " + file.getAbsolutePath());
                    }
                }
            }
        });
    }

    private static String getExtension(String fileName) {
        for (String extension : EXTENSIONS) {
            if (fileName.endsWith(extension)) {
                return extension;
            }
        }
        return null;
    }

    private static String getFolderName(String extension) {
        switch (extension) {
            case ".jpg", ".jpeg", ".png", ".gif", ".bmp" -> return "Images";
            case ".txt", ".pdf", ".doc", ".docx" -> return "Documents";
            case ".xls", ".xlsx" -> return "Spreadsheets";
            case ".ppt", ".pptx" -> return "Presentations";
            default -> return null;
        }
    }
}