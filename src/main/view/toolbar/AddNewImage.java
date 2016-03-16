package main.view.toolbar;

import main.model.image.ImageModel;
import main.model.image_collection.ImageCollectionModel;
import main.model.toolbar.AddNewImageModel;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Dongwoo on 05/03/2016.
 */
public class AddNewImage extends JPanel implements Observer{

    private ImageIcon addNewIconInactive, addNewIconActive;
    private JLabel addNew;

    private JFileChooser fc;

    private Color bgColor;

    private AddNewImageModel addNewImageModel;
    private ImageCollectionModel imageCollectionModel;


    public AddNewImage(AddNewImageModel addNewImageModel, ImageCollectionModel imageCollectionModel, Color bgColor) {

        this.addNewImageModel = addNewImageModel;
        this.imageCollectionModel = imageCollectionModel;
        this.bgColor = bgColor;

        String inactiveColor = "white";
        String activeColor = "yellow";

        fc = new JFileChooser();
        fc.setMultiSelectionEnabled(true);
        fc.setCurrentDirectory(new File("."));

        addNewIconInactive = new ImageIcon(
                new ImageIcon(getClass().getResource("/resources/icons/add_picture_" + inactiveColor + ".png"))
                        .getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        addNewIconActive = new ImageIcon(
                new ImageIcon(getClass().getResource("/resources/icons/add_picture_" + activeColor + ".png"))
                        .getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));

        addNew = new JLabel(addNewIconInactive);

        this.layoutView();
        this.registerControllers();
    }

    private void layoutView(){
        setBackground(bgColor);
        add(addNew);
    }

    private void registerControllers(){

        MouseInputListener mil = new AddNewController();
        this.addNew.addMouseListener(mil);
    }

    private class AddNewController extends MouseInputAdapter {

        @Override
        public void mousePressed(MouseEvent e) {

            JLabel labelPressed = (JLabel) e.getSource();

            labelPressed.setIcon(addNewIconActive);

            if (labelPressed == addNew){
                int ret = fc.showOpenDialog(null);
                if (ret == JFileChooser.APPROVE_OPTION){
//                    File file = fc.getSelectedFile();
                    File[] files = fc.getSelectedFiles();

                    // handled opened file
                    try {
                        for (File file : files){
                            String filePath = file.getAbsolutePath();

                            String fileName = file.getName();

                            BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
                            FileTime fileCreated = attr.creationTime();
                            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                            String fileCreatedDate = dateFormat.format(fileCreated.toMillis());

                            if (!imageCollectionModel.findByPath(filePath)){
                                ImageModel imageModel = new ImageModel(filePath, fileName, fileCreatedDate);
                                imageCollectionModel.addToImageList(imageModel);
                            }
                        }

                    } catch(IOException err){
                        err.printStackTrace();
                    }
                }
                labelPressed.setIcon(addNewIconInactive);
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
