package org.nearbyshops.serviceprovider.Settings;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
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


public class AccountSettingsFragment extends Fragment implements NotifySettings{

    private static final String ARG_SECTION_NUMBER = "section_number";

//    @State
//    Settings settings;

    @BindView(R.id.switch_distributor_account)
    Switch switchDistributor;

    @BindView(R.id.switch_end_user_account)
    Switch switchEndUser;

    @BindView(R.id.switch_shop_account)
    Switch switchShopEnabled;



    @BindView(R.id.button_save)
    TextView buttonSave;

    @State
    boolean isFragmentStopped = true;


    public AccountSettingsFragment() {
    }

    public static AccountSettingsFragment newInstance(int sectionNumber) {
        AccountSettingsFragment fragment = new AccountSettingsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View rootView = inflater.inflate(R.layout.fragment_user_account_settings_constraint, container, false);
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
            switchEndUser.setChecked(settings.getEndUserEnabledByDefault());
            switchDistributor.setChecked(settings.getDistributorEnabledByDefault());
            switchShopEnabled.setChecked(settings.isShopEnabledByDefault());
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
        settings.setDistributorEnabledByDefault(switchDistributor.isChecked());
        settings.setEndUserEnabledByDefault(switchEndUser.isChecked());
        settings.setShopEnabledByDefault(switchShopEnabled.isChecked());

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

