package forkjoinframework;

import java.io.File;
import java.nio.file.Files;
import java.util.concurrent.ForkJoinPool;

public class Main {

    public static void main(String[] args) {

        String path = "C:\\Users\\RajaNarasimhanKundet\\OneDrive - Atyeti Inc\\Desktop\\Self\\JavaPOC\\Atyeti_Raja_Java\\PairProgramming";
        File root = new File(path);
        if(!root.exists()|| !root.isDirectory()){
            System.out.println("invalid path");
            return;
        }
        ForkJoinPool pool = new ForkJoinPool();
        FolderSizeTask task = new FolderSizeTask(root);
        long size = pool.invoke(task);
        System.out.println("Total Size of "+path+" : "+size +" Bytes");
    }
}