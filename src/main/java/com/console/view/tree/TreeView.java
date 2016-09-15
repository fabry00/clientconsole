package com.console.view.tree;

import com.airhacks.afterburner.views.FXMLView;

/**
 *
 * @author fabry
 */
public class TreeView extends FXMLView {

    public TreePresenter getRealPresenter() {
        return (TreePresenter) super.getPresenter();
    }

}
