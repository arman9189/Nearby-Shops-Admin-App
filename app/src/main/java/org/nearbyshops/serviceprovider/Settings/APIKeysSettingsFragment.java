package org.nearbyshops.serviceprovider.Settings;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputEditText;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.nearbyshops.serviceprovider.ModelSettings.Settings;
import org.nearbyshops.serviceprovider.R;
import org.nearbyshops.serviceprovider.Settings.Interface.NotifySettings;
import org.nearbyshops.serviceprovider.Settings.Interface.UpdateSettings;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icepick.Icepick;
import icepick.State;

/**
 * Created by sumeet on 26/9/16.
 */


public class APIKeysSettingsFragment extends Fragment implements NotifySettings{

    private static final String ARG_SECTION_NUMBER = "section_number";

//    @State
//    Settings settings;



    @BindView(R.id.edit_text_maps_key)
    TextInputEditText mapsApiKey;

    @BindView(R.id.button_save)
    TextView buttonSave;

    @State
    boolean isFragmentStopped = true;


    public APIKeysSettingsFragment() {
    }

    public static APIKeysSettingsFragment newInstance(int sectionNumber) {
        APIKeysSettingsFragment fragment = new APIKeysSettingsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View rootView = inflater.inflate(R.layout.fragment_user_api_key_settings, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.end_user_account_enabled);
//        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

        // reset the fragment
        ButterKnife.bind(this,rootView);
        isFragmentStopped = false;
        return rootView;
    }



    void bindViews()
    {

        if(isFragmentStopped)
        {
            return;
        }
            Settings settings = ((SettingsActivity)getActivity()).getSettings();
            mapsApiKey.setText(settings.getGoogleMapsAPIKey());
    }


    @OnClick(R.id.button_save)
    void buttonSaveClick()
    {
        getDataFromViews();
        ((UpdateSettings)getActivity()).notifyUpdateSettings();
    }



    void getDataFromViews()
    {
        if(isFragmentStopped)
        {
            return;
        }

        Settings settings = ((SettingsActivity)getActivity()).getSettings();
        settings.setGoogleMapsAPIKey(mapsApiKey.getText().toString());
    }


    @Override
    public void onStop() {
        super.onStop();

        isFragmentStopped = true;
    }

    @Override
    public void settingsFetched() {
        bindViews();
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Icepick.saveInstanceState(this,outState);
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Icepick.restoreInstanceState(this,savedInstanceState);
    }
}

