package tradeprocessor;

import java.io.File;
import java.nio.file.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class TradeProcessorApp {

    private final static Logger logger = Logger.getLogger("TradeProcessorApp.class");

    public static void main(String[] args) throws Exception {

        Path incomingDir = Paths.get("C:\\Users\\RajaNarasimhanKundet\\OneDrive - Atyeti Inc\\Desktop\\Self\\JavaPOC\\Project_Training\\FileHandling\\src\\tradeprocessor\\trade_data\\incoming\\");
        ExecutorService executor = Executors.newFixedThreadPool(4);
        WatchService watchService = FileSystems.getDefault().newWatchService();
        incomingDir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

        logger.info("Watching folder: " + incomingDir.toAbsolutePath());

        while (true) {
            WatchKey key = watchService.take();
            for (WatchEvent<?> event : key.pollEvents()) {
                Path fileName = (Path) event.context();
                File file = incomingDir.resolve(fileName).toFile();

                executor.submit(() -> {
                    try {
                        TradeProcessor.process(file);
                    } catch (Exception e) {
                        logger.severe(e.getLocalizedMessage());
                    }
                });
            }
            key.reset();
        }
    }
}