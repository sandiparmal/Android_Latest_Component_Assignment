package com.infosys.androidassignment.mvp.base;

import com.infosys.androidassignment.mvp.contract.FactsContract;

public interface BasePresenter<CountryView> {

    /**
     * Called when the view is created and wants to attach its presenter
     */
    void attach(FactsContract.FactsView view);

    /**
     * Called when the view is destroyed to get rid of its presenter
     */
    void detach();
}
