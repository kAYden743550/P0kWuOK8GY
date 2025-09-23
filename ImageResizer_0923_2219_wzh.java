// 代码生成时间: 2025-09-23 22:19:56
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
# FIXME: 处理边界情况
import java.util.Arrays;
import java.util.List;
# 优化算法效率

public class ImageResizer {
    // 主方法，用于执行图片尺寸批量调整
# 改进用户体验
    public static void main(String[] args) {
# NOTE: 重要实现细节
        // 图片文件夹路径
        String directoryPath = "/path/to/image/directory";
        // 缩放比例
        double scale = 0.5;

        try {
            resizeImagesInDirectory(directoryPath, scale);
            System.out.println("All images have been resized successfully.");
# 添加错误处理
        } catch (IOException e) {
            System.err.println("Error resizing images: " + e.getMessage());
        }
    }

    /**
     * 调整指定目录下所有图片的尺寸
     * 
     * @param directoryPath 图片目录路径
     * @param scale 缩放比例
     * @throws IOException 如果读取或写入文件时发生错误
     */
    public static void resizeImagesInDirectory(String directoryPath, double scale) throws IOException {
        File directory = new File(directoryPath);
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("Provided path is not a directory: " + directoryPath);
        }

        // 获取目录下所有图片文件
        File[] imageFiles = directory.listFiles((dir, name) -> name.endsWith(".jpg") || name.endsWith(".png"));
        if (imageFiles == null) {
            throw new IOException("Error reading directory: " + directoryPath);
        }

        // 遍历所有图片文件
        for (File imageFile : imageFiles) {
            resizeImage(imageFile, scale);
# 改进用户体验
        }
    }

    /**
     * 调整单个图片的尺寸
     * 
     * @param imageFile 图片文件
# 扩展功能模块
     * @param scale 缩放比例
     * @throws IOException 如果读取或写入文件时发生错误
# 增强安全性
     */
    public static void resizeImage(File imageFile, double scale) throws IOException {
        BufferedImage originalImage = ImageIO.read(imageFile);
        if (originalImage == null) {
            throw new IOException("Error reading image file: " + imageFile.getAbsolutePath());
        }

        int newWidth = (int) (originalImage.getWidth() * scale);
# 改进用户体验
        int newHeight = (int) (originalImage.getHeight() * scale);

        // 创建新的缩放后的图片
# 扩展功能模块
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
        resizedImage.getGraphics().drawImage(originalImage.getScaledInstance(newWidth, newHeight, java.awt.Image.SCALE_SMOOTH), 0, 0, null);

        // 保存缩放后的图片
        File resizedFile = new File(imageFile.getAbsolutePath() + "_resized");
        ImageIO.write(resizedImage, "png", resizedFile);
# 扩展功能模块
        System.out.println("Resized image saved: " + resizedFile.getAbsolutePath());
    }
}
