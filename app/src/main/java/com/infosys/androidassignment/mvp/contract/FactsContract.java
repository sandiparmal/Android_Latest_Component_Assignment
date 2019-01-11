package com.infosys.androidassignment.mvp.contract;

import com.infosys.androidassignment.mvp.base.BasePresenter;
import com.infosys.androidassignment.mvp.base.BaseView;

public class FactsContract {

    public interface FactsView extends BaseView {

        /**
         * Update the list items in list view through adapter
         */
        void onResponse(FactsResponse countryResponse);

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
        void fetchCountryDetails(String extendedURL);
    }
}
