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
public class Rating extends JPanel implements Observer{

    private ImageIcon rateStarIconBefore, rateStarIconAfter;
    private JLabel[] rateStars;
    private ImageModel imageModel;

    public Rating(ImageModel imageModel) {

        this.imageModel = imageModel;
        int rating = imageModel.getRating();

        rateStarIconBefore = new ImageIcon(
                new ImageIcon(getClass().getResource("/resources/icons/star_lined_yellow.png"))
                        .getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
        rateStarIconAfter = new ImageIcon(
                new ImageIcon(getClass().getResource("/resources/icons/star_filled_yellow.png"))
                        .getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));

        rateStars = new JLabel[5];
        for (int i=0; i<rating; ++i){
            rateStars[i] = new JLabel(rateStarIconAfter);
        }
        for (int i=rating; i<5; ++i){
            rateStars[i] = new JLabel(rateStarIconBefore);
        }

        this.layoutView();
        this.registerControllers();
    }

    private void layoutView(){
        for (int i=0; i<5; ++i){
            add(rateStars[i]);
        }
    }

    private void registerControllers(){
        MouseInputListener rateListener = new RateController();
        for (int i=0; i<5; ++i){
            this.rateStars[i].addMouseListener(rateListener);
        }
    }

    private class RateController extends MouseInputAdapter {

        @Override
        public void mousePressed(MouseEvent e) {

            JLabel labelPressed = (JLabel) e.getSource();
            int pressedIndex = Arrays.asList(rateStars).indexOf(labelPressed);
            if (labelPressed.getIcon() == rateStarIconBefore){
                imageModel.setRating(pressedIndex+1);
            } else {
                imageModel.setRating(pressedIndex);
            }
        }
    }

    private void updateRateView(){
        int rating = imageModel.getRating();
//        System.out.println("curr rating set to " + rating);
        for (int i=0; i<rating; ++i){
            rateStars[i].setIcon(rateStarIconAfter);
        }
        for (int i=rating; i<5; ++i){
            rateStars[i].setIcon(rateStarIconBefore);
        }
    }


    @Override
    public void update(Observable o, Object arg) {
        if (arg.equals("setRating")){
//            System.out.println("setRating");
            updateRateView();
        }
    }
}
