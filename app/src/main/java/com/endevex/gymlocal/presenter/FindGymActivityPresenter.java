package com.endevex.gymlocal.presenter;

import com.endevex.gymlocal.model.Gym;
import com.endevex.gymlocal.view.FindGymView;

import java.util.List;

/**
 * Presenter for the findGymActivity, essentially to grab data and pass back to view.
 * Created by Leivant on 20/10/2017.
 */

public class FindGymActivityPresenter {

    private FindGymView mFindGymView;

    /**
     * Constuct the presenter and pass in the view to call back to.
     * @param view
     */
    public FindGymActivityPresenter(FindGymView view) {
        mFindGymView = view;
    }

    /**
     * Get each of the gyms and create their own marker by passing in their data.
     */
    public void loadMarkers() {

        List<Gym> gymList = Gym.listAll(Gym.class);
        for(Gym gym : gymList)
        {
            mFindGymView.addMarker(gym.getLatitude(), gym.getLongitude(), gym.getName()+"~"+ gym.getType()+"~"+gym.getPhone());
        }
    }
}
