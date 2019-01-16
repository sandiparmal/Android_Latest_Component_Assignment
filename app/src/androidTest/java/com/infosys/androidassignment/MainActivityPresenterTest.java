package com.infosys.androidassignment;

import android.support.test.runner.AndroidJUnit4;

import com.infosys.androidassignment.base.AbstractTest;
import com.infosys.androidassignment.mvp.contract.FactsContract;
import com.infosys.androidassignment.mvp.model.FactsInteractorImpl;
import com.infosys.androidassignment.mvp.presenter.FactsPresenterImpl;
import com.infosys.androidassignment.network.data.Facts;
import com.infosys.androidassignment.network.data.FactsResponse;
import com.infosys.androidassignment.network.interfaces.NetworkService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;

import io.reactivex.Observable;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class MainActivityPresenterTest extends AbstractTest {

    private FactsPresenterImpl objFactsPresenter;
    private FactsContract.FactsView factsView;

    @Before
    public void setUp() {
        this.objFactsPresenter = spy(new FactsPresenterImpl(new FactsInteractorImpl()));
        factsView = mock(FactsContract.FactsView.class);
    }

    @Test
    public void testAttach() {
        assertNull(objFactsPresenter.factsView);

        objFactsPresenter.attach(factsView);
        assertNotNull(objFactsPresenter.factsView);
    }

    @Test
    public void testDetach() {
        objFactsPresenter.attach(factsView);
        assertNotNull(objFactsPresenter.factsView);

        objFactsPresenter.detach();
        assertNull(objFactsPresenter.factsView);
    }

    @Test
    public void testFetchFacts() {

        // attach view to presenter
        objFactsPresenter.attach(factsView);

        // Mock Network Services
        NetworkService networkService = Mockito.mock(NetworkService.class);

        // get mock results
        FactsResponse factsResponse = getMockFactsResults();

        //Test ok response
        when(networkService.getFactsDetails()).thenReturn(Observable.just(factsResponse));
        objFactsPresenter.factsFetchCall("https://dl.dropboxusercontent.com");
        waitFor(50);
        verify(factsView, atLeastOnce()).showWait();
        waitFor(2500);
        ArgumentCaptor<FactsResponse> argument = ArgumentCaptor.forClass(FactsResponse.class);
        verify(objFactsPresenter).onFetchingSuccess(argument.capture());
        waitFor(50);
        verify(factsView, atLeastOnce()).hideWait();
        waitFor(50);
        verify(factsView).onResponse(argument.capture());

    }

    private FactsResponse getMockFactsResults() {

        FactsResponse objFactsResponse = new FactsResponse();
        objFactsResponse.title = "About India";

        ArrayList<Facts> list = new ArrayList<>();
        Facts objFacts = new Facts();
        objFacts.setTitle("Languages");
        objFacts.setDescription("As per the 2011 Census, there are about 122 languages");
        objFacts.setImageHref("https://upload.wikimedia.org/wikipedia/commons/thumb/1/12/South_Asian_Language_Families.jpg/450px-South_Asian_Language_Families.jpg");
        list.add(objFacts);

        objFacts = new Facts();
        objFacts.setTitle("States");
        objFacts.setDescription("Total Number of States in India are 29, The India has 7 Union Territories which includes the National Capital Territory of Delhi.");
        objFacts.setImageHref("http://static.ibnlive.in.com/ibnlive/pix/ibnhome/animated-map-of-creation-of-indian-states-010614.gif");
        list.add(objFacts);

        objFacts = new Facts();
        objFacts.setTitle("");
        objFacts.setDescription("");
        objFacts.setImageHref("");
        list.add(objFacts);

        objFacts = new Facts();
        objFacts.setTitle("Geography");
        objFacts.setDescription("");
        objFacts.setImageHref("");
        list.add(objFacts);

        objFactsResponse.rows = list;

        return objFactsResponse;

    }

}
