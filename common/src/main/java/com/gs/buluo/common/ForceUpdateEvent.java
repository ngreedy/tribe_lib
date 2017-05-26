package com.gs.buluo.common;

import java.util.List;

/**
 * Created by hjn on 2017/5/26.
 */

public class ForceUpdateEvent {
    public boolean isForce;
    public String lasetVerision;
    public List<String> releaseNote;

    public ForceUpdateEvent() {
    }

    public ForceUpdateEvent(boolean isForce) {
        this.isForce = isForce;
    }

    public ForceUpdateEvent(boolean isForce, String lasetVerision, List<String> releaseNote) {
        this.isForce = isForce;
        this.lasetVerision = lasetVerision;
        this.releaseNote = releaseNote;
    }
}
