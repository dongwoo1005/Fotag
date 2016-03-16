package main.view.image;

import main.model.image.ImageModel;
import main.model.toolbar.ViewOptions;
import main.model.toolbar.ViewSelectorModel;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Dongwoo on 04/03/2016.
 */
public class ImageView extends JPanel implements Observer{

    private ImageThumbnail imageThumbnail;
    private JPanel imageName, imageDate;
    private Rating rating;
    private GridBagLayout gridViewLayout;
    private FlowLayout listViewLayout;
    private JPanel listDescription;
    private GridBagConstraints c_grid_image, c_grid_name, c_grid_date, c_grid_rating;

    private ImageModel imageModel;
    private ViewSelectorModel viewSelectorModel;

    public ImageView(ImageModel imageModel, ViewSelectorModel viewSelectorModel) {

        this.imageModel = imageModel;
        this.viewSelectorModel = viewSelectorModel;

        gridViewLayout = new GridBagLayout();
        listViewLayout = new FlowLayout(FlowLayout.LEFT);

        imageThumbnail = new ImageThumbnail(imageModel);
        imageModel.addObserver(imageThumbnail);

        imageName = new JPanel();
        JLabel imageNameLabel = new JLabel(imageModel.getFileName());
        imageName.add(imageNameLabel);

        imageDate = new JPanel();
        JLabel imageDateLabel = new JLabel(imageModel.getFileCreatedDate());
        imageDate.add(imageDateLabel);

        rating = new Rating(imageModel);
        imageModel.addObserver(rating);

        listDescription = new JPanel();
        listDescription.setLayout(new BoxLayout(listDescription, BoxLayout.Y_AXIS));

        imageName.setLayout(new FlowLayout(FlowLayout.CENTER));
        imageDate.setLayout(new FlowLayout(FlowLayout.CENTER));
        rating.setLayout(new FlowLayout(FlowLayout.CENTER));

        c_grid_image = new GridBagConstraints();
        c_grid_image.fill = GridBagConstraints.BOTH;
        c_grid_image.gridwidth = 2;
        c_grid_image.gridheight = 1;
        c_grid_image.gridx = 0;
        c_grid_image.gridy = 0;
        c_grid_image.weightx = 1;
        c_grid_image.weighty = 1;

        c_grid_name = new GridBagConstraints();
        c_grid_name.fill = GridBagConstraints.BOTH;
        c_grid_name.gridwidth = 2;
        c_grid_name.gridheight = 1;
        c_grid_name.gridx = 0;
        c_grid_name.gridy = 1;
        c_grid_name.weightx = 1;
        c_grid_name.weighty = 0.001;

        c_grid_date = new GridBagConstraints();
        c_grid_date.fill = GridBagConstraints.BOTH;
        c_grid_date.gridwidth = 1;
        c_grid_date.gridheight = 1;
        c_grid_date.gridx = 0;
        c_grid_date.gridy = 2;
        c_grid_date.weightx = 0.5;
        c_grid_date.weighty = 0.001;

        c_grid_rating = new GridBagConstraints();
        c_grid_rating.fill = GridBagConstraints.BOTH;
        c_grid_rating.gridwidth = 1;
        c_grid_rating.gridheight = 1;
        c_grid_rating.gridx = 1;
        c_grid_rating.gridy = 2;
        c_grid_rating.weightx = 0.5;
        c_grid_rating.weighty = 0.001;

        this.viewLayout();
        this.registerControllers();
    }

    private void viewLayout(){

        if (viewSelectorModel.getSelectedView() == ViewOptions.GRIDVIEW){
            setLayout(gridViewLayout);
            add(imageThumbnail, c_grid_image);
            add(imageName, c_grid_name);
            add(imageDate, c_grid_date);
            add(rating, c_grid_rating);

        } else if (viewSelectorModel.getSelectedView() == ViewOptions.LISTVIEW){
            setLayout(listViewLayout);

//            add(Box.createRigidArea(new Dimension(5,0)));
            add(imageThumbnail);
            add(Box.createRigidArea(new Dimension(75,0)));

            listDescription.add(Box.createRigidArea(new Dimension(0,100)));
            imageName.setLayout(new FlowLayout(FlowLayout.LEFT));
            imageDate.setLayout(new FlowLayout(FlowLayout.LEFT));
            rating.setLayout(new FlowLayout(FlowLayout.LEFT));
            listDescription.add(imageName);
            listDescription.add(imageDate);
            listDescription.add(rating);
            add(listDescription);
        }
    }

    private void registerControllers(){

    }

    @Override
    public void update(Observable o, Object arg) {

        if (arg.equals("setSelectedView")){
//            System.out.println("update from ImageView - setSelectedView");
            if (viewSelectorModel.getSelectedView() == ViewOptions.GRIDVIEW){
                setLayout(gridViewLayout);

                removeAll();
                imageName.setLayout(new FlowLayout(FlowLayout.CENTER));
                imageDate.setLayout(new FlowLayout(FlowLayout.CENTER));
                rating.setLayout(new FlowLayout(FlowLayout.CENTER));
                add(imageThumbnail, c_grid_image);
                add(imageName, c_grid_name);
                add(imageDate, c_grid_date);
                add(rating, c_grid_rating);
                revalidate();
                repaint();
            } else if (viewSelectorModel.getSelectedView() == ViewOptions.LISTVIEW){
                setLayout(listViewLayout);

                removeAll();
                listDescription.removeAll();

//                add(Box.createRigidArea(new Dimension(5,0)));
                add(imageThumbnail);
                add(Box.createRigidArea(new Dimension(75,0)));

                listDescription.add(Box.createRigidArea(new Dimension(0,100)));
                imageName.setLayout(new FlowLayout(FlowLayout.LEFT));
                imageDate.setLayout(new FlowLayout(FlowLayout.LEFT));
                rating.setLayout(new FlowLayout(FlowLayout.LEFT));
                listDescription.add(imageName);
                listDescription.add(imageDate);
                listDescription.add(rating);
                add(listDescription);

                revalidate();
                repaint();
            }
        }
    }
}
