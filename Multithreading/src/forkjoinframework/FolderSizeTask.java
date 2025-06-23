package forkjoinframework;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.logging.Logger;

public class FolderSizeTask extends RecursiveTask<Long> {

    private final File folder;
    private final Logger logger = Logger.getLogger("FolderSizeTask.class");
    //private final AtomicInteger count = new AtomicInteger(0);

    public FolderSizeTask(File folder) {
        this.folder = folder;
    }

    @Override
    protected Long compute() {
        long totalSize = 0;
        List<FolderSizeTask> subTasks = new ArrayList<>();
        File[] files = folder.listFiles();
        if(files!=null){
            for(File file: files){
                if(file.isFile()){
                    totalSize+=file.length();
                    logger.info("reading file "+file.getName()+" ,size: "+file.length()+" Bytes");
                    //count.increment();
                    //System.out.println("Total size after reading "+ count.getValue() +" files: "+totalSize+ " Bytes");
                } else if (file.isDirectory()) {
                    FolderSizeTask task = new FolderSizeTask(file);
                    task.fork();
                    subTasks.add(task);
                }
            }
            for(FolderSizeTask task: subTasks){
                totalSize+=task.join();
            }
        }
        return totalSize;
    }
}
