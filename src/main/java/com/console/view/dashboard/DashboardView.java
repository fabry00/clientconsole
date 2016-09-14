package com.console.view.dashboard;

import com.airhacks.afterburner.views.FXMLView;

/**
 *
 * @author adam-bien.com
 */
public class DashboardView extends FXMLView {

    public DashboardPresenter getRealPresenter() {
        return (DashboardPresenter) super.getPresenter();
    }
}
