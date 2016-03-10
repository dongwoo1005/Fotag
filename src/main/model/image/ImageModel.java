package main.model.image;

import java.io.Serializable;
import java.util.Observable;

/**
 * Created by Dongwoo on 04/03/2016.
 */
public class ImageModel extends Observable implements Serializable{

    private String filePath;
    private String fileName;
    private String fileCreatedDate;
    private int rating;

    public ImageModel(String filePath, String fileName, String fileCreatedDate) {

        this.filePath = filePath;
        this.fileName = fileName;
        this.fileCreatedDate = fileCreatedDate;

        rating = 0;

    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
        setChanged();
        notifyObservers("setRating");
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileCreatedDate() {
        return fileCreatedDate;
    }
}
