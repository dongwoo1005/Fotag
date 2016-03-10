package main.view.toolbar;

import main.model.toolbar.ViewOptions;
import main.model.toolbar.ViewSelectorModel;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Dongwoo on 05/03/2016.
 */
public class ViewSelector extends JPanel implements Observer{

    private ImageIcon gridViewIconInactive, listViewIconInactive;
    private ImageIcon gridViewIconActive, listViewIconActive;
    private JLabel gridView, listView;

    private Color bgColor;

    private ViewSelectorModel viewSelectorModel;

    public ViewSelector(ViewSelectorModel viewSelectorModel, Color bgColor) {

        this.viewSelectorModel = viewSelectorModel;
        this.bgColor = bgColor;

        String activeColor = "yellow";
        String inactiveColor = "white";

        gridViewIconInactive = new ImageIcon(
                new ImageIcon(getClass().getResource("/resources/icons/gridview_" + inactiveColor + ".png"))
                        .getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));

        gridViewIconActive = new ImageIcon(
                new ImageIcon(getClass().getResource("/resources/icons/gridview_" + activeColor + ".png"))
                        .getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));

        listViewIconInactive = new ImageIcon(
                new ImageIcon(getClass().getResource("/resources/icons/listview_" + inactiveColor + ".png"))
                        .getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));

        listViewIconActive = new ImageIcon(
                new ImageIcon(getClass().getResource("/resources/icons/listview_" + activeColor + ".png"))
                        .getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));

        if (viewSelectorModel.getSelectedView() == ViewOptions.GRIDVIEW){
            gridView = new JLabel(gridViewIconActive);
            listView = new JLabel(listViewIconInactive);
        } else if (viewSelectorModel.getSelectedView() == ViewOptions.LISTVIEW){
            gridView = new JLabel(gridViewIconInactive);
            listView = new JLabel(listViewIconActive);
        }


        this.viewLayout();
        this.registerControllers();
    }

    private void viewLayout(){
        setBackground(bgColor);
        add(gridView);
        add(Box.createRigidArea(new Dimension(1, 0)));
        add(listView);
    }

    private void registerControllers(){
        MouseInputListener mil = new ViewController();
        this.gridView.addMouseListener(mil);
        this.listView.addMouseListener(mil);
    }

    private class ViewController extends MouseInputAdapter{

        @Override
        public void mousePressed(MouseEvent e) {
            JLabel labelPressed = (JLabel) e.getSource();
            if (labelPressed == gridView){
                if (viewSelectorModel.getSelectedView() == ViewOptions.LISTVIEW){
                    viewSelectorModel.setSelectedView(ViewOptions.GRIDVIEW);
                }
            } else if (labelPressed == listView){
                if (viewSelectorModel.getSelectedView() == ViewOptions.GRIDVIEW){
                    viewSelectorModel.setSelectedView(ViewOptions.LISTVIEW);
                }
            }
        }
    }

    private void updateSelectorView(){
        if (viewSelectorModel.getSelectedView() == ViewOptions.GRIDVIEW){
            gridView.setIcon(gridViewIconActive);
            listView.setIcon(listViewIconInactive);
        } else {
            gridView.setIcon(gridViewIconInactive);
            listView.setIcon(listViewIconActive);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        updateSelectorView();
    }
}
