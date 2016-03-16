package main.model.toolbar;

import java.util.Observable;

/**
 * Created by Dongwoo on 05/03/2016.
 */
public class RateFilterModel extends Observable {

    private int filterValue;

    public RateFilterModel() {

        filterValue = 0;
    }

    public int getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(int filterValue) {
        this.filterValue = filterValue;
        setChanged();
        notifyObservers("setFilterValue");
    }
}
