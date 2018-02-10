package capstone.udacity.com.readit.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import capstone.udacity.com.readit.R;

/**
 * Created by Sayed El-Abady on 2/9/2018.
 */

public class FavouriteFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View favouriteFragment = inflater.inflate(R.layout.favourite_fragment , container , false);

        return favouriteFragment;

    }
}
