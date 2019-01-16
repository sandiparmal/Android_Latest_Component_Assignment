package com.infosys.androidassignment.mvp.contract;

import com.infosys.androidassignment.mvp.base.BasePresenter;
import com.infosys.androidassignment.mvp.base.BaseView;
import com.infosys.androidassignment.network.data.FactsResponse;

public class FactsContract {

    public interface FactsView extends BaseView {

        /**
         * Update the list items in list view through adapter
         */
        void onResponse(FactsResponse factsResponse);

        /**
         * Show toast on data fetching failure
         */
        void onFailure(String message);
    }

    public interface FactsPresenter extends BasePresenter {

        /**
         * Request network call and get data from REST url
         *
         * @param extendedURL String
         */
        void factsFetchCall(String extendedURL);
    }
}
