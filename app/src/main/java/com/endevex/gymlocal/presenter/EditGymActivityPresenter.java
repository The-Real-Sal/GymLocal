package com.endevex.gymlocal.presenter;

import com.endevex.gymlocal.model.Gym;
import com.endevex.gymlocal.model.User;
import com.endevex.gymlocal.view.EditGymView;

import java.util.List;

/**
 * Created by Leivant on 20/10/2017.
 */

public class EditGymActivityPresenter {
    private EditGymView mEditGymView;
    private Gym mGym;

    public EditGymActivityPresenter(EditGymView view) {
        mEditGymView = view;
    }

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

    public void saveGym(String gymName, String gymType, String phone) {
        if (isValid(gymName, gymType, phone)) {
            mGym.setName(gymName);
            mGym.setType(gymType);
            mGym.setPhone(phone);
            mGym.save();
        }
    }

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
