package com.infosys.androidassignment.mvp.model;


import android.support.annotation.NonNull;

import com.infosys.androidassignment.App;
import com.infosys.androidassignment.R;
import com.infosys.androidassignment.network.data.FactsResponse;
import com.infosys.androidassignment.network.interfaces.NetworkService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class FactsInteractorImpl implements FactsInteractor {

    //Collects all subscriptions to un-subscribe later
    @NonNull
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    /**
     * Request network call and get data from REST url
     *
     * @param URL                   String
     * @param onFetchFinishListener OnFetchFinishListener
     */
    @Override
    public void fetchFactsDetails(String URL, final OnFetchFinishListener onFetchFinishListener) {
        try {
            //configure Retrofit using Retrofit Builder
            NetworkService networkService = App.getClient(URL).create(NetworkService.class);

            mCompositeDisposable.add(networkService.getCountryDetails()
                    .subscribeOn(Schedulers.io()) // “work” on io thread
                    .observeOn(AndroidSchedulers.mainThread()) // “listen” on UIThread
                    .map(new Function<FactsResponse, FactsResponse>() {
                        @Override
                        public FactsResponse apply(
                                @io.reactivex.annotations.NonNull final FactsResponse factsResponse) throws Exception {
                            // we want to have the country and not the wrapper object
                            return factsResponse;
                        }
                    })
                    .subscribe(new Consumer<FactsResponse>() {
                        @Override
                        public void accept(
                                @io.reactivex.annotations.NonNull final FactsResponse factsResponse)
                                throws Exception {
                            onFetchFinishListener.onFetchingSuccess(factsResponse);
                        }
                    })
            );
        } catch (Exception e) {
            onFetchFinishListener.onFetchingFailure(App.getContext().getString(R.string.fetch_failed_message));
        }


    }

    /**
     * Dispose CompositeDisposable
     */
    @Override
    public void clearCompositeDisposable() {
        mCompositeDisposable.clear();
    }

}
