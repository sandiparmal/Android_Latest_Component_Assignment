package com.infosys.androidassignment.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.infosys.androidassignment.R;
import com.infosys.androidassignment.adapters.FactsRecyclerAdapter;
import com.infosys.androidassignment.mvp.contract.FactsContract;
import com.infosys.androidassignment.mvp.model.FactsInteractorImpl;
import com.infosys.androidassignment.mvp.presenter.FactsPresenterImpl;
import com.infosys.androidassignment.network.data.FactsResponse;
import com.infosys.androidassignment.utils.ConnectivityUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import static com.infosys.androidassignment.utils.Constants.EXTENDED_URL;


public class FactsFragment extends Fragment implements FactsContract.FactsView {

    @BindView(R.id.progress)
    ProgressBar mProgressBar;
    private FactsPresenterImpl mFactsPresenter;
    private Unbinder mUnbinder;

    // Bind view using ButterKnife
    @BindView(R.id.facts_recycler_view)
    RecyclerView mFactsDetailsRecyclerView;
    @BindView(R.id.simpleSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private FactsRecyclerAdapter mAdapter = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.facts_fragment, container, false);

        // binding ButterKnife
        mUnbinder = ButterKnife.bind(this, view);

        // Initialize Presenter
        mFactsPresenter = new FactsPresenterImpl( new FactsInteractorImpl());
        mFactsPresenter.attach(this);

        // Set Layout Manager
        mFactsDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // avoid re downloading images and data
        mFactsDetailsRecyclerView.setHasFixedSize(true);
        mFactsDetailsRecyclerView.setDrawingCacheEnabled(true);
        mFactsDetailsRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        // setting empty adapter
        mFactsDetailsRecyclerView.setAdapter(mAdapter);

        // implement setOnRefreshListener event on SwipeRefreshLayout
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // cancel the Visual indication of a refresh
                mSwipeRefreshLayout.setRefreshing(false);
                initiateFetchRequest();
            }
        });

        // initiate data fetch request
        initiateFetchRequest();
        return view;
    }


    /**
     * initiate data fetch request if network is present else
     * show toast message
     */
    private void initiateFetchRequest() {

        ConnectivityUtils objConnectivityUtils = new ConnectivityUtils();

        // check if network is available
        boolean isNetwork = objConnectivityUtils.isNetworkAvailable(getActivity());
        if (isNetwork) {
            mFactsPresenter.factsFetchCall(EXTENDED_URL);
        } else {
            // network is not available
            Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
        }

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
     * @param factsResponse FactsResponse
     */
    @Override
    public void onResponse(FactsResponse factsResponse) {

        try {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(factsResponse.title);

            mFactsDetailsRecyclerView.setItemViewCacheSize(factsResponse.rows.size());
            mAdapter = new FactsRecyclerAdapter(getActivity(), factsResponse.rows);
            mFactsDetailsRecyclerView.setAdapter(mAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Butter Knife returns an Unbinder instance when we call bind to do this for you. Call its unbind method in onDestroy
        mUnbinder.unbind();

        // Destroy presenter which holding current view
        mFactsPresenter.detach();

    }
}
