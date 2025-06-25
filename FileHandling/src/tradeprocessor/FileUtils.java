package tradeprocessor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileUtils {
    public static void moveFile(Path source, Path target) throws IOException {
        Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
    }

    public static void appendSummary(TradeSummary summary) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\RajaNarasimhanKundet\\OneDrive - Atyeti Inc\\Desktop\\Self\\JavaPOC\\Project_Training\\FileHandling\\src\\tradeprocessor\\trade_data\\output\\summary.log", true))) {
            writer.write(summary.format());
        }
    }
}