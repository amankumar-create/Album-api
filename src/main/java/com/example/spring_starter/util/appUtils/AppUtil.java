package com.example.spring_starter.util.appUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
 

public class AppUtil {
    public static String get_photo_upload_path(String filename, Long album_id) throws IOException{
        String albumPath = "src/main/resources/static/uploads/"+album_id;
        System.out.println(albumPath);
        Files.createDirectories(Paths.get(albumPath));
        return new File(albumPath).getAbsolutePath() + "/" + filename;
    }
}
