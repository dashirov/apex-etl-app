package com.iaccap.data.apex.etl.app;

/**
 * Created by dtadmin on 8/3/16.
 */
public class ULEventDetail {
    private ULEvent parentULEvent;

    public ULEvent getParentULEvent() {
        return parentULEvent;
    }

    public void setParentULEvent(ULEvent parentULEvent) {
        this.parentULEvent = parentULEvent;
    }
}
