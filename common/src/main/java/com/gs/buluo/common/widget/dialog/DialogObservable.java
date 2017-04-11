package com.gs.buluo.common.widget.dialog;

import java.util.Observable;

/**
 * Created by hjn on 2017/4/11.
 */

public class DialogObservable extends Observable {
    public void showDialog(String msg){
        setChanged();
        notifyObservers(msg);
    }
}
