package main.view.image;

import main.model.image.ImageModel;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Dongwoo on 05/03/2016.
 */
public class ImageThumbnail extends JPanel implements Observer{

    ImageIcon originalImage;
    ImageIcon thumbnailImage, enlargedImage;
    int originalWidth, originalHeight;
    int thumbnailWidth, thumbnailHeight;
    int enlargedWidth, enlargedHeight;
    JLabel thumbnail, enlarged;
    JFrame enlargedWindow;
    boolean hasEnlarged;
    private ImageModel imageModel;

    public ImageThumbnail(ImageModel imageModel) {

        this.imageModel = imageModel;

        originalImage = new ImageIcon(imageModel.getFilePath());

        thumbnailWidth = 300;
        thumbnailHeight = 200;

        enlargedWidth = 800;
        enlargedHeight = 600;
        hasEnlarged = false;

        originalWidth = originalImage.getIconWidth();
        originalHeight = originalImage.getIconHeight();

        thumbnailImage = new ImageIcon(originalImage.getImage().getScaledInstance(thumbnailWidth, thumbnailHeight, Image.SCALE_SMOOTH));
        thumbnail = new JLabel(thumbnailImage);

        this.layoutView();
        this.registerControllers();
    }

    private void layoutView(){
        setPreferredSize(new Dimension(thumbnailWidth, thumbnailHeight));
        add(thumbnail);
    }

    private void registerControllers(){
        MouseInputListener thumbnailListener = new ThumbnailController();
        this.thumbnail.addMouseListener(thumbnailListener);
    }

    private class ThumbnailController extends MouseInputAdapter {

        @Override
        public void mousePressed(MouseEvent e) {

            if (!hasEnlarged){
                enlargedWindow = new JFrame();
                enlargedWindow.setSize(enlargedWidth, enlargedHeight);
                enlargedWindow.setLocationRelativeTo(null);
                enlargedWindow.setResizable(false);
                enlargedImage = new ImageIcon(originalImage.getImage().getScaledInstance(enlargedWidth, enlargedHeight, Image.SCALE_SMOOTH));
                enlarged = new JLabel(enlargedImage);
                MouseInputListener enlargedListener = new EnlargedController();
                enlarged.addMouseListener(enlargedListener);
                enlargedWindow.add(enlarged);
            }

            enlargedWindow.setVisible(true);
        }
    }

    private class EnlargedController extends MouseInputAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            enlargedWindow.setVisible(false);
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
