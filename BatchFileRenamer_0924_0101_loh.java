// 代码生成时间: 2025-09-24 01:01:36
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A utility class for batch renaming files.
 */
public class BatchFileRenamer {

    private static final String DIRECTORY_PATH = "path/to/your/directory"; // Change this to target directory

    /**
     * Renames files in a directory based on a naming pattern.
     *
     * @param pattern The pattern to use for renaming files.
     * @return A list of renamed file paths.
     */
    public List<String> renameFiles(String pattern) {
        List<String> renamedFiles = new ArrayList<>();
        File directory = new File(DIRECTORY_PATH);
        
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IllegalArgumentException("The specified directory does not exist or is not a directory.");
        }
        
        for (int i = 0; i < directory.listFiles().length; i++) {
            File file = directory.listFiles()[i];
            if (file.isFile()) {
                String newName = String.format(pattern, i + 1);
                String newPath = DIRECTORY_PATH + "/" + newName;
                try {
                    Files.move(file.toPath(), Paths.get(newPath));
                    renamedFiles.add(newPath);
                } catch (IOException e) {
                    System.err.println("Error renaming file: " + file.getName() + " - " + e.getMessage());
                }
            }
        }
        return renamedFiles;
    }

    public static void main(String[] args) {
        BatchFileRenamer renamer = new BatchFileRenamer();
        try {
            // Example usage: rename files to "fileX.txt" where X is the index of the file.
            List<String> renamedPaths = renamer.renameFiles("file%d.txt");
            System.out.println("Renamed files: " + renamedPaths.stream().collect(Collectors.joining(", ")));
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
