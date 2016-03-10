package main.view.image_collection;

import main.model.image.ImageModel;
import main.model.image_collection.ImageCollectionModel;
import main.model.toolbar.RateFilterModel;
import main.model.toolbar.ViewOptions;
import main.model.toolbar.ViewSelectorModel;
import main.view.image.ImageView;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Dongwoo on 04/03/2016.
 */
public class ImageCollectionView extends JPanel implements Observer{

    private ImageCollectionModel imageCollectionModel;
    private RateFilterModel rateFilterModel;
    private ViewSelectorModel viewSelectorModel;

    public ImageCollectionView(ImageCollectionModel imageCollectionModel, RateFilterModel rateFilterModel, ViewSelectorModel viewSelectorModel) {

        this.imageCollectionModel = imageCollectionModel;
        this.rateFilterModel = rateFilterModel;
        this.viewSelectorModel = viewSelectorModel;

        this.viewLayout();
        this.registerControllers();
    }

    private void addImageToCollection(ImageModel imageModel){
        ImageView imageView = new ImageView(imageModel, viewSelectorModel);
        imageModel.addObserver(imageView);
        imageModel.addObserver(this);
        viewSelectorModel.addObserver(imageView);
        add(imageView);
    }

    private void viewLayout(){

        if (viewSelectorModel.getSelectedView() == ViewOptions.GRIDVIEW){
            setLayout(new WrapLayout(FlowLayout.LEFT));
        } else if (viewSelectorModel.getSelectedView() == ViewOptions.LISTVIEW){
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        }
        for (ImageModel imageModel : imageCollectionModel.getImageList()){
            addImageToCollection(imageModel);
        }
    }

    private void registerControllers(){

    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg.equals("addToImageList")){
//            System.out.println("update from ImageCollectionView - addToImageList");

            int lastIndex = imageCollectionModel.getImageList().size() - 1;
            ImageModel imageModel = imageCollectionModel.getImageList().get(lastIndex);

            if (rateFilterModel.getFilterValue() != 0){
                if (rateFilterModel.getFilterValue() == imageModel.getRating()){
                    addImageToCollection(imageModel);
                }
            } else {
                addImageToCollection(imageModel);
            }
            revalidate();

        } else if (arg.equals("setFilterValue")){
//            System.out.println("update from ImageCollectionView - setFilterValue");
            this.removeAll();
            if (rateFilterModel.getFilterValue() == 0){
                for (ImageModel imageModel : imageCollectionModel.getImageList()){
                    addImageToCollection(imageModel);
                }
            } else {
                for (ImageModel imageModel : imageCollectionModel.getImageList()){
                    int rating = imageModel.getRating();
                    if (rating == rateFilterModel.getFilterValue()){
                        addImageToCollection(imageModel);
                    }
                }
            }
            revalidate();
            repaint();
        } else if (arg.equals("setRating")){
            if (rateFilterModel.getFilterValue() != 0){
                this.removeAll();
                for (ImageModel imageModel : imageCollectionModel.getImageList()){
                    if (rateFilterModel.getFilterValue() == imageModel.getRating()){
                        addImageToCollection(imageModel);
                    }
                }
                revalidate();
                repaint();
            }
        } else if (arg.equals("setSelectedView")){
//            System.out.println("update from Image Collection View - setSelectedView");
            if (viewSelectorModel.getSelectedView() == ViewOptions.GRIDVIEW){
                setLayout(new WrapLayout(FlowLayout.LEFT));
            } else if (viewSelectorModel.getSelectedView() == ViewOptions.LISTVIEW){
                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            }
            revalidate();
        }
    }
}
