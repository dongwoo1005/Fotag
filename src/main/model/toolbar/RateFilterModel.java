package main.model.toolbar;

import java.util.Observable;

/**
 * Created by Dongwoo on 05/03/2016.
 */
public class RateFilterModel extends Observable {

    int FilterValue;

    public RateFilterModel() {

        FilterValue = 0;
    }

    public int getFilterValue() {
        return FilterValue;
    }

    public void setFilterValue(int filterValue) {
        FilterValue = filterValue;
        setChanged();
        notifyObservers("setFilterValue");
    }
}
