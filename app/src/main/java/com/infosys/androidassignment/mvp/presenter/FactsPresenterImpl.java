package com.infosys.androidassignment.mvp.presenter;


import com.infosys.androidassignment.mvp.contract.FactsContract;
import com.infosys.androidassignment.mvp.model.FactsInteractor;
import com.infosys.androidassignment.network.data.FactsResponse;

public class FactsPresenterImpl implements FactsContract.FactsPresenter, FactsInteractor.OnFetchFinishListener {

    // initialization
    public FactsContract.FactsView factsView;
    private final FactsInteractor factsInteractor;

    public FactsPresenterImpl(FactsInteractor factsInteractor) {

        this.factsInteractor = factsInteractor;
    }

    /**
     * Request network call and get data from REST url
     *
     * @param extendedURL String
     */
    @Override
    public void factsFetchCall(String extendedURL) {
        // show progress bar
        factsView.showWait();
        // call interactor to fetch facts details
        factsInteractor.fetchFactsDetails(extendedURL, this);
    }

    /**
     * Trigger when facts details fetching success
     *
     * @param factsResponse FactsResponse
     */
    @Override
    public void onFetchingSuccess(FactsResponse factsResponse) {
        if(factsView != null){
            factsView.hideWait();
            factsView.onResponse(factsResponse);
        }
    }

    /**
     * Trigger when facts details fetching failure
     *
     * @param message String
     */
    @Override
    public void onFetchingFailure(String message) {
        if(factsView != null){
            factsView.hideWait();
            factsView.onFailure(message);
        }

    }

    /**
     * Called when the view is created and wants to attach its presenter
     *
     * @param view view
     */
    @Override
    public void attach(FactsContract.FactsView view) {
        factsView = view;
    }

    /**
     * Called when the view is destroyed to get rid of its presenter
     */
    @Override
    public void detach() {
        if (factsView != null) {
            factsView = null;
        }
        factsInteractor.clearCompositeDisposable();
    }
}
