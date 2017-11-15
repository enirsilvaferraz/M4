package com.system.m4.views.vos;

/**
 * Created by eferraz on 17/06/17.
 */

public class RedirectButtomVO implements VOItemListInterface {

    private Integer homeVisibility;
    private Integer relativePosition;

    public RedirectButtomVO(int homeVisibility, int relativePosition) {
        this.homeVisibility = homeVisibility;
        this.relativePosition = relativePosition;
    }

    public Integer getHomeVisibility() {
        return homeVisibility;
    }

    public void setHomeVisibility(Integer homeVisibility) {
        this.homeVisibility = homeVisibility;
    }

    public Integer getRelativePosition() {
        return relativePosition;
    }

    public void setRelativePosition(Integer relativePosition) {
        this.relativePosition = relativePosition;
    }
}
