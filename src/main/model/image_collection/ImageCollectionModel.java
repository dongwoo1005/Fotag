package main.model.image_collection;

import main.model.image.ImageModel;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by Dongwoo on 04/03/2016.
 */
public class ImageCollectionModel extends Observable implements Serializable{

    private List<ImageModel> imageList;

    public ImageCollectionModel() {

        imageList = new ArrayList<>();
    }

    // mutator
    public void addToImageList(ImageModel img){

        imageList.add(img);

        setChanged();
        notifyObservers("addToImageList");
    }
//
//    public void removeFromImageList(ImageModel img){
//
//        imageList.remove(img);
//
//        setChanged();
//        notifyObservers("removeFromImageList");
//    }

    // getter
    public List<ImageModel> getImageList() {
        return imageList;
    }

    // search
    public boolean findByPath(String path){
        for (ImageModel imageModel : imageList){
            if (imageModel.getFilePath().equals(path)){
                return true;
            }
        }
        return false;
    }

    // loader
    public void loadModel(ImageCollectionModel newModel){
        imageList.clear();
        System.out.println("Loading...");
        for (ImageModel imageModel : newModel.getImageList()){
            String filePath = imageModel.getFilePath();
            File file = new File(filePath);
            if (file.exists()){
                addToImageList(imageModel);
            } else {
                System.out.println("file does not exist");
            }
        }
        System.out.println("Loading Done");
    }
}
