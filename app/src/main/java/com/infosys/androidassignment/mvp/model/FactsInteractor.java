package com.infosys.androidassignment.mvp.model;

import com.infosys.androidassignment.network.data.FactsResponse;

public interface FactsInteractor {

    /**
     * OnFetchListener will trigger onFetchingSuccess and onFetchingFailure depends on result
     */
    interface OnFetchFinishListener {
        /**
         * Trigger when facts details fetching success
         *
         * @param factsDetails FactsResponse
         */
        void onFetchingSuccess(FactsResponse factsDetails);

        /**
         * Trigger when facts details fetching failure
         *
         * @param message String
         */
        void onFetchingFailure(String message);
    }

    /**
     * Request network call and get data from REST url
     *
     * @param URL String
     */
    void fetchFactsDetails(String URL, FactsInteractor.OnFetchFinishListener onFetchListener);

    /**
     * Dispose CompositeDisposable
     */
    void clearCompositeDisposable();
}
