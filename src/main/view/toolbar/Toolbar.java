package main.view.toolbar;

import main.model.image_collection.ImageCollectionModel;
import main.model.toolbar.AddNewImageModel;
import main.model.toolbar.RateFilterModel;
import main.model.toolbar.ToolbarModel;
import main.model.toolbar.ViewSelectorModel;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Dongwoo on 04/03/2016.
 */
public class Toolbar extends JPanel implements Observer{

    private ImageIcon logoIcon;
    private JLabel logo;

    private RateFilter rateFilter;
    private AddNewImage addNewImage;
    private ViewSelector viewSelector;

    private Color bgColor;
    private String iconColor;

    private ToolbarModel toolbarModel;
//    private RateFilterModel rateFilterModel;
    private ViewSelectorModel viewSelectorModel;

    public Toolbar(ToolbarModel toolbarModel, ImageCollectionModel imageCollectionModel,  RateFilterModel rateFilterModel, ViewSelectorModel viewSelectorModel) {

        this.toolbarModel = toolbarModel;
        this.viewSelectorModel = viewSelectorModel;
//        this.rateFilterModel = rateFilterModel;

        bgColor = new Color(29, 29, 29);
        iconColor = "white";

        viewSelector = new ViewSelector(viewSelectorModel, bgColor);
        viewSelectorModel.addObserver(viewSelector);

        AddNewImageModel addNewImageModel = new AddNewImageModel();
        addNewImage = new AddNewImage(addNewImageModel, imageCollectionModel, bgColor);
        addNewImageModel.addObserver(addNewImage);

        rateFilter = new RateFilter(rateFilterModel, bgColor);
        rateFilterModel.addObserver(rateFilter);

        logoIcon = new ImageIcon(
                new ImageIcon(getClass().getResource("/resources/icons/fotag-logo-" + iconColor + ".png"))
                        .getImage().getScaledInstance(200, 87, Image.SCALE_SMOOTH));
        logo = new JLabel(logoIcon);

        this.layoutView();
        this.registerControllers();
    }

    private void layoutView(){

        setBorder(BorderFactory.createEmptyBorder(7,0,7,0));
        setBackground(bgColor);
        setLayout(new GridBagLayout());

        // grid view / list view
        GridBagConstraints c_left = new GridBagConstraints();
        c_left.fill = GridBagConstraints.HORIZONTAL;
        c_left.gridwidth = 1;
        c_left.gridheight = 1;
        c_left.gridx = 0;
        c_left.gridy = 0;
        c_left.weightx = 0.025;
        c_left.weighty = 1;
        c_left.anchor = GridBagConstraints.LINE_START;
        add(viewSelector, c_left);

        // logo
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(bgColor);
        logoPanel.setLayout(new BorderLayout());
        logoPanel.add(logo, BorderLayout.LINE_START);
        GridBagConstraints c_center = new GridBagConstraints();
        c_center.fill = GridBagConstraints.HORIZONTAL;
        c_center.gridwidth = 1;
        c_center.gridheight = 1;
        c_center.gridx = 1;
        c_center.gridy = 0;
        c_center.weightx = 0.33;
        c_center.weighty = 1;
        add(logoPanel, c_center);

        // add new
        GridBagConstraints c_addnew = new GridBagConstraints();
        c_addnew.fill = GridBagConstraints.HORIZONTAL;
        c_addnew.gridwidth = 1;
        c_addnew.gridheight = 1;
        c_addnew.gridx = 2;
        c_addnew.gridy = 0;
        c_addnew.weightx = 0.001;
        c_addnew.weighty = 1;
        add(addNewImage, c_addnew);

        // filter by rating
        GridBagConstraints c_right = new GridBagConstraints();
        c_right.fill = GridBagConstraints.HORIZONTAL;
        c_right.gridwidth = 1;
        c_right.gridheight = 1;
        c_right.gridx = 3;
        c_right.gridy = 0;
        c_right.weightx = 0.025;
        c_right.weighty = 1;
        add(rateFilter, c_right);
    }

    private void registerControllers() {

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
