package org.nearbyshops.serviceprovider.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;


import org.nearbyshops.serviceprovider.DaggerComponentBuilder;
import org.nearbyshops.serviceprovider.Login.Interfaces.NotifyAboutLogin;
import org.nearbyshops.serviceprovider.Markets.Markets;
import org.nearbyshops.serviceprovider.ModelRoles.User;
import org.nearbyshops.serviceprovider.MyApplication;
import org.nearbyshops.serviceprovider.R;
import org.nearbyshops.serviceprovider.RetrofitRESTContract.UserService;
import org.nearbyshops.serviceprovider.SignUp.ForgotPassword.ForgotPassword;
import org.nearbyshops.serviceprovider.SignUp.PrefSignUp.PrefrenceForgotPassword;
import org.nearbyshops.serviceprovider.SignUp.PrefSignUp.PrefrenceSignUp;
import org.nearbyshops.serviceprovider.SignUp.SignUp;
import org.nearbyshops.serviceprovider.Preferences.PrefGeneral;
import org.nearbyshops.serviceprovider.Preferences.PrefLogin;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sumeet on 19/4/17.
 */

public class LoginFragment extends Fragment {

    public static final String TAG_SERVICE_INDICATOR = "service_indicator";

    boolean isDestroyed = false;



    @Inject Gson gson;
//    @BindView(R.id.ccp) CountryCodePicker ccp;
    @BindView(R.id.username) TextInputEditText username;
    @BindView(R.id.password) TextInputEditText password;
    @BindView(R.id.progress_bar_login) ProgressBar progressBar;



//    @BindView(R.id.clear) TextView clear;
//    @BindView(R.id.select_service) TextView selectAutomatic;




    @BindView(R.id.change_market) TextView changeMarket;




    public LoginFragment() {

        DaggerComponentBuilder.getInstance()
                .getNetComponent()
                .Inject(this);
    }





    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        setRetainInstance(true);
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this,rootView);


        if(getChildFragmentManager().findFragmentByTag(TAG_SERVICE_INDICATOR)==null)
        {
            getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.service_indicator,new ServiceIndicatorFragment(),TAG_SERVICE_INDICATOR)
                    .commit();
        }





        if(PrefGeneral.getServiceURL(getActivity())==null)
        {
            changeMarket.setText("Select Market");
        }
        else
        {
            changeMarket.setText("Change Market");
        }


        return rootView;
    }




    void showToastMessage(String message)
    {
        Toast.makeText(getActivity(),message, Toast.LENGTH_SHORT).show();
    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1&& resultCode==1)
        {
//            Fragment fragment = getChildFragmentManager()
//                    .findFragmentByTag(TAG_SERVICE_INDICATOR);

//            if(fragment instanceof ServiceIndicatorFragment)
//            {
//                ((ServiceIndicatorFragment)fragment).refresh();
//            }
        }
        else if(requestCode==5)
        {
//            Fragment fragment = getChildFragmentManager()
//                    .findFragmentByTag(TAG_SERVICE_INDICATOR);
//
//            if(fragment instanceof ServiceIndicatorFragment)
//            {
////            showToastMessage("Clear Click : Inside If Block");
//                ((ServiceIndicatorFragment)fragment).refresh();
//            }
        }
        else if(requestCode==567)
        {

            Fragment fragment = getChildFragmentManager()
                    .findFragmentByTag(TAG_SERVICE_INDICATOR);

            if(fragment instanceof ServiceIndicatorFragment)
            {
                ((ServiceIndicatorFragment)fragment).refresh();
            }
        }



    }


//    @OnClick(R.id.clear)
//    void clear()
//    {
//        PrefGeneral.saveServiceURL(null);
//        PrefServiceConfig.saveServiceConfig(null,getActivity());
//
//
//        Fragment fragment = getChildFragmentManager()
//                .findFragmentByTag(TAG_SERVICE_INDICATOR);
//
//        if(fragment instanceof ServiceIndicatorFragment)
//        {
//            ((ServiceIndicatorFragment)fragment).refresh();
//        }
//    }



    @OnClick(R.id.sign_up)
    void signUp()
    {

        PrefrenceSignUp.saveUser(null,getActivity());
        Intent intent = new Intent(getActivity(), SignUp.class);
        startActivity(intent);
    }





    @OnClick(R.id.forgot_password)
    void forgotPasswordClick()
    {

        PrefrenceForgotPassword.saveUser(null,getActivity());
        Intent intent = new Intent(getActivity(), ForgotPassword.class);
        startActivity(intent);
    }





    @OnTextChanged(R.id.username)
    void usernameChanged()
    {
//        UtilityLogin.saveUsername(getActivity(),username.getText().toString());
    }



    @OnTextChanged(R.id.password)
    void passwordChanged()
    {
//        UtilityLogin.savePassword(getActivity(),password.getText().toString());
    }



    boolean validateData()
    {
        boolean isValid = true;
//        boolean phoneValidity = false;
//        boolean emailValidity = false;
//
//
//        emailValidity = EmailValidator.getInstance().isValid(username.getText().toString());
//        phoneValidity = android.util.Patterns.PHONE.matcher(username.getText().toString()).matches();



        if(password.getText().toString().isEmpty())
        {
            password.requestFocus();
            password.setError("Password cannot be empty !");
            isValid = false;
        }


//        if(!emailValidity && !phoneValidity)
//        {
//            username.setError("Not a valid email or phone !");
//            username.requestFocus();
//
//            isValid = false;
//        }

        if(username.getText().toString().isEmpty())
        {
            password.requestFocus();
            username.setError("username cannot be empty !");
            username.requestFocus();

            isValid = false;
        }



        return isValid;
    }



    @Override
    public void onStart() {
        super.onStart();
        isDestroyed= false;
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();

        isDestroyed= true;
    }




    @BindView(R.id.login)
    Button loginButton;

    @OnClick(R.id.login)
    void makeRequestLogin()
    {

        if(!validateData())
        {
            // validation failed return
            return;
        }

        final String phoneWithCode = username.getText().toString();
//        final String phoneWithCode = ccp.getSelectedCountryCode()+ username.getText().toString();

        progressBar.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.INVISIBLE);


        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(PrefGeneral.getServiceURL(MyApplication.getAppContext()))
                .client(new OkHttpClient().newBuilder().build())
                .build();



        Call<User> call = retrofit.create(UserService.class).getProfileWithLogin(
                PrefLogin.baseEncoding(phoneWithCode,password.getText().toString())
        );



        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                progressBar.setVisibility(View.GONE);
                loginButton.setVisibility(View.VISIBLE);

                if(response.code()==200)
                {
                    // save username and password

                    User user = response.body();




                    if(user.getRole()==User.ROLE_ADMIN_CODE)
                    {

                    }
                    else if(user.getRole()==User.ROLE_STAFF_CODE)
                    {

                    }
                    else
                    {
                        showToastMessage("Only Staff is allowed to login");
                        return;
                    }





                    PrefLogin.saveCredentials(
                            getActivity(),
                            phoneWithCode,
                            password.getText().toString()

                    );





                    // save token and token expiry timestamp
//                    PrefLogin.saveToken(
//                            getActivity(),
//                            response.body().getToken(),
//                            response.body().getTimestampTokenExpires()
//                    );


                    // save user profile information
                    PrefLogin.saveUserProfile(
                            response.body(),
                            getActivity()
                    );









//                    PrefOneSignal.saveToken(getActivity(),PrefOneSignal.getLastToken(getActivity()));
//
//                    if(PrefOneSignal.getToken(getActivity())!=null)
//                    {
//                        // update one signal id if its not updated
//                        getActivity().startService(new Intent(getActivity(), UpdateOneSignalID.class));
//                    }



                    if(getActivity() instanceof NotifyAboutLogin)
                    {
                        ((NotifyAboutLogin) getActivity()).loginSuccess();
                    }




                }
                else
                {
                    showToastMessage("Login Failed : Username or password is incorrect !");
                    System.out.println("Login Failed : Code " + String.valueOf(response.code()));
                }

            }




            @Override
            public void onFailure(Call<User> call, Throwable t) {
                showToastMessage("Network connection problem !");
                progressBar.setVisibility(View.GONE);
                loginButton.setVisibility(View.VISIBLE);
            }
        });

    }














//    @OnClick(R.id.select_service)
//    void getServices()
//    {
//
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .baseUrl(PrefServiceConfig.getSDSURL(MyApplication.getAppContext()))
//                .client(new OkHttpClient().newBuilder().build())
//                .build();
//
//
//
//        ServiceDiscoveryService service = retrofit.create(ServiceDiscoveryService.class);
//
//
//
//        Call<ServiceConfigEndpoint> call = service.getServicesListSimple(
//                (double) PrefLocationDeprecated.getLatitideCurrent(getActivity()),(double)PrefLocationDeprecated.getLongitudeCurrent(getActivity()),
//                null, null,null,null,null,null,
//                " distance ",1,0
//        );
//
//
//        call.enqueue(new Callback<ServiceConfigEndpoint>() {
//            @Override
//            public void onResponse(Call<ServiceConfigEndpoint> call, Response<ServiceConfigEndpoint> response) {
//
//
//                if(isDestroyed)
//                {
//                    return;
//                }
//
//                if(response.code() == 200 && response.body()!=null) {
//
//
//                    if(response.body().getItemCount()==1)
//                    {
//                        PrefGeneral.saveServiceURL(
//                                response.body().getResults().get(0).getServiceURL(),
//                                getActivity()
//                        );
//
//
//                        PrefServiceConfig.saveServiceConfig(null,getActivity());
//
//                        Fragment fragment = getChildFragmentManager()
//                                .findFragmentByTag(TAG_SERVICE_INDICATOR);
//
//                        if(fragment instanceof ServiceIndicatorFragment)
//                        {
//                            ((ServiceIndicatorFragment)fragment).refresh();
//                        }
//
//                    }
//                }
//                else
//                {
//                    showToastMessage("Failed Code : " + String.valueOf(response.code()));
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<ServiceConfigEndpoint> call, Throwable t) {
//
//                if(isDestroyed)
//                {
//                    return;
//                }
//
//                showToastMessage("Network Connection Failed !");
//
//
//            }
//        });
//
//    }






    @OnClick(R.id.change_market)
    void changeMarketClick()
    {
        Intent intent = new Intent(getActivity(), Markets.class);
        startActivityForResult(intent,567);
    }





}
