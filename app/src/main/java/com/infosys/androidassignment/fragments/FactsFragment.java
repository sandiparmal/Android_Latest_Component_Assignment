package com.infosys.androidassignment.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.infosys.androidassignment.mvp.contract.FactsContract;
import com.infosys.androidassignment.network.data.FactsResponse;

import butterknife.BindView;


public class FactsFragment extends Fragment implements FactsContract.FactsView {

    @BindView(R.id.progress)
    ProgressBar mProgressBar;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.facts_fragment, container, false);

        return view;
    }


    /**
     * initiate data fetch request if network is present else
     * show toast message
     */
    private void initiateFetchRequest() {

    }

    /**
     * Show progress dialog while fetching details
     */
    @Override
    public void showWait() {

        mProgressBar.setVisibility(View.VISIBLE);
    }

    /**
     * hide progress dialog when fetching complete or error occur
     */
    @Override
    public void hideWait() {
        mProgressBar.setVisibility(View.GONE);
    }

    /**
     * Show toast if failed to fetched data
     *
     * @param errorMessage String Message
     */
    @Override
    public void onFailure(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    /**
     * Update the list items in list view through adapter after successfully
     * fetching data
     *
     * @param countryResponse countryResponse
     */
    @Override
    public void onResponse(FactsResponse countryResponse) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
