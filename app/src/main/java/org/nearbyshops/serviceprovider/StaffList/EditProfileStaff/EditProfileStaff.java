package org.nearbyshops.serviceprovider.StaffList.EditProfileStaff;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.nearbyshops.serviceprovider.R;

import butterknife.ButterKnife;


public class EditProfileStaff extends AppCompatActivity {

    public static final String TAG_FRAGMENT_EDIT = "fragment_edit";
    public static final String TAG_FRAGMENT_CHANGE_PASSWORD = "fragment_change_password";
    public static final String TAG_FRAGMENT_CHANGE_EMAIL = "fragment_change_email_old";

//    @BindView(R.id.appbar) AppBarLayout appBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        overridePendingTransition(R.anim.enter_from_right,R.anim.exit_to_left);
        setContentView(R.layout.activity_edit_profile_staff);
        ButterKnife.bind(this);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
        if(getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_EDIT)==null)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container,new FragmentEditProfileStaff(),TAG_FRAGMENT_EDIT)
                    .commit();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enter_from_left,R.anim.exit_to_right);
    }


}

