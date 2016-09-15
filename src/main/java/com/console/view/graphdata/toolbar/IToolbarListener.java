package com.console.view.graphdata.toolbar;

import com.console.domain.Metric;

/**
 *
 * @author fabry
 */
public interface IToolbarListener {

    public void resetSeriesClicked();

    public void addAllClicked();

    public void removeAllClicked();
    
    public void metricSelected(Metric metric);

}

