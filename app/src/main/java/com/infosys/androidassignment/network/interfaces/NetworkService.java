package com.infosys.androidassignment.network.interfaces;

import com.infosys.androidassignment.network.data.FactsResponse;
import com.infosys.androidassignment.utils.Constants;

import io.reactivex.Observable;
import retrofit2.http.GET;


public interface NetworkService {

    // retrofit get call
    @GET(Constants.FACTS_URL)
    Observable<FactsResponse> getFactsDetails();


}

