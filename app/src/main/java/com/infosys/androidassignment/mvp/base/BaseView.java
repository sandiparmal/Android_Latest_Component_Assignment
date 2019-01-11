package com.infosys.androidassignment.mvp.base;

public interface BaseView {

    /**
     * Show progress dialog while fetching details
     */
    void showWait();

    /**
     * hide progress dialog when fetching complete or error occur
     */
    void hideWait();


}
