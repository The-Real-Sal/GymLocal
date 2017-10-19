package com.endevex.gymlocal.presenter;

import com.endevex.gymlocal.model.Gym;
import com.endevex.gymlocal.model.User;
import com.endevex.gymlocal.view.EditGymView;

import java.util.List;

/**
 * Presenter for the Activity view where it will load data from the Gym model and pass to presenter
 * and visa-versa.
 * Created by Leivant on 20/10/2017.
 */
public class EditGymActivityPresenter {

    private EditGymView mEditGymView;
    private Gym mGym;

    /**
     * Constructor for the presenter which takes in a view so it may call methods and pass data.
     */
    public EditGymActivityPresenter(EditGymView view) {
        mEditGymView = view;
    }

    /**
     * Grabs the gym of the user logged in and returns the data to the view to display.
     * @param email
     */
    public void loadGymDetails(String email) {
        List<User> userList = User.find(User.class, "M_EMAIL = ?", email);
        User user = userList.get(0);
        List<Gym> gymList = Gym.listAll(Gym.class);
        Gym gym = new Gym();
        for (Gym g : gymList) {
            if (g.getOwner().getEmail().equalsIgnoreCase(user.getEmail())) {
                mGym = g;
                gym = mGym;
                break;
            }
        }
        mEditGymView.loadGymDetails(gym.getName(), gym.getType(), gym.getPhone());
    }

    /**
     * Update the users gym model and save using Sugar ORM.
     * @param gymName
     * @param gymType
     * @param phone
     */
    public void saveGym(String gymName, String gymType, String phone) {
        if (isValid(gymName, gymType, phone)) {
            mGym.setName(gymName);
            mGym.setType(gymType);
            mGym.setPhone(phone);
            mGym.save();
        }
    }

    /**
     * Checks the parameters are valid and returns the return or calls and error display message
     * on the view.
     * @param gymName
     * @param gymType
     * @param phone
     * @return
     */
    private boolean isValid(String gymName, String gymType, String phone) {
        if (gymName.isEmpty()) {
            mEditGymView.errorGymName();
            return false;
        }
        if (gymType.isEmpty()) {
            mEditGymView.errorGymType();
            return false;
        }
        if (phone.isEmpty()) {
            mEditGymView.errorPhone();
            return false;
        }
        return true;
    }
}
