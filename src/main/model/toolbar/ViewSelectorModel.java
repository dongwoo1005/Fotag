package main.model.toolbar;

import java.util.Observable;

/**
 * Created by Dongwoo on 05/03/2016.
 */

public class ViewSelectorModel extends Observable{

    private ViewOptions selectedView;

    public ViewSelectorModel() {
        selectedView = ViewOptions.GRIDVIEW;
    }

    public ViewOptions getSelectedView() {
        return selectedView;
    }

    public void setSelectedView(ViewOptions selectedView) {
        this.selectedView = selectedView;
        setChanged();
        notifyObservers("setSelectedView");
    }
}
