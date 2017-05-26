package com.gs.buluo.common;

import java.util.List;

/**
 * Created by hjn on 2017/5/26.
 */

public class UpdateEvent {
    public boolean supported;
    public String lastVersion;
    public List<String> releaseNote;

    public UpdateEvent() {
    }

    public UpdateEvent(boolean supported) {
        this.supported = supported;
    }

    public UpdateEvent(boolean supported, String lastVersion, List<String> releaseNote) {
        this.supported = supported;
        this.lastVersion = lastVersion;
        this.releaseNote = releaseNote;
    }
}
