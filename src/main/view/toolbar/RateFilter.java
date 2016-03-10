package main.view.toolbar;

import main.model.toolbar.RateFilterModel;
import main.model.toolbar.ToolbarModel;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Dongwoo on 04/03/2016.
 */
public class RateFilter extends JPanel implements Observer{

    private ImageIcon rateStarIconBefore, rateStarIconAfter;
    private JLabel filterBy;
    private JLabel[] rateStars;
    private RateFilterModel rateFilterModel;
    private Color bgColor;

    public RateFilter(RateFilterModel rateFilterModel, Color bgColor) {

        this.rateFilterModel = rateFilterModel;
        this.bgColor = bgColor;

        filterBy = new JLabel("Filter: ");
        filterBy.setForeground(Color.WHITE);

        rateStarIconBefore = new ImageIcon(
                new ImageIcon(getClass().getResource("/resources/icons/star_lined_yellow.png"))
                        .getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
        rateStarIconAfter = new ImageIcon(
                new ImageIcon(getClass().getResource("/resources/icons/star_filled_yellow.png"))
                        .getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));

        rateStars = new JLabel[5];
        for (int i=0; i<5; ++i){
            rateStars[i] = new JLabel(rateStarIconBefore);
        }

        this.layoutView();
        this.registerControllers();
    }

    private void layoutView(){
        setBackground(bgColor);
        add(filterBy);
        for (int i=0; i<5; ++i){
            add(rateStars[i]);
        }
    }

    private void registerControllers(){
        MouseInputListener filterListener = new FilterController();
        for (int i=0; i<5; ++i){
            this.rateStars[i].addMouseListener(filterListener);
        }
    }

    private class FilterController extends MouseInputAdapter {

        @Override
        public void mousePressed(MouseEvent e) {

            JLabel labelPressed = (JLabel) e.getSource();
            int pressedIndex = Arrays.asList(rateStars).indexOf(labelPressed);
            if (labelPressed.getIcon() == rateStarIconBefore){
                rateFilterModel.setFilterValue(pressedIndex+1);
            } else {
                rateFilterModel.setFilterValue(pressedIndex);
            }
        }
    }

    private void updateFilterView(){
        int filterValue = rateFilterModel.getFilterValue();
        for (int i=0; i<filterValue; ++i){
            rateStars[i].setIcon(rateStarIconAfter);
        }
        for (int i=filterValue; i<5; ++i){
            rateStars[i].setIcon(rateStarIconBefore);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        updateFilterView();
    }
}
