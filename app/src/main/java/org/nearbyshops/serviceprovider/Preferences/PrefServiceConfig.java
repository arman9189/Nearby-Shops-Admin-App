package org.nearbyshops.serviceprovider.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;


import org.nearbyshops.serviceprovider.Markets.Model.ServiceConfigurationLocal;
import org.nearbyshops.serviceprovider.MyApplication;
import org.nearbyshops.serviceprovider.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by sumeet on 20/4/17.
 */



public class PrefServiceConfig {


    public static final String SDS_URL_NEARBY_SHOPS = "http://sds.nearbyshops.org";
    public static final String SDS_URL_LOCAL_HOTSPOT = "http://192.168.43.73:5125";


    // to change your sds url declare a constant above and assign its value to service_url_sds given below
    public static final String SERVICE_URL_SDS = SDS_URL_NEARBY_SHOPS;







    // simple or advanced at service selection screen
    // role selected at login screen


    // constants
    public static final int SERVICE_SELECT_MODE_SIMPLE = 1;
    public static final int SERVICE_SELECT_MODE_ADVANCED = 2;


    private static final String TAG_PREF_CONFIG = "configuration";
    private static final String TAG_SDS_URL = "url_for_sds";





    public static void saveServiceConfigLocal(ServiceConfigurationLocal currentTrip, Context context)
    {
        //Creating a shared preference

        if(context==null)
        {
            return;
        }

        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_name), MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPref.edit();

        Gson gson = UtilityFunctions.provideGson();
        String json = gson.toJson(currentTrip);
        prefsEditor.putString(TAG_PREF_CONFIG, json);

        prefsEditor.apply();
    }





    public static ServiceConfigurationLocal getServiceConfigLocal(Context context)
    {
        if(context==null)
        {
            return null;
        }

        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_name), MODE_PRIVATE);

        Gson gson = UtilityFunctions.provideGson();
        String json = sharedPref.getString(TAG_PREF_CONFIG, null);

        return gson.fromJson(json, ServiceConfigurationLocal.class);
    }






    public static String getServiceName(Context context)
    {
        ServiceConfigurationLocal serviceConfigurationLocal = getServiceConfigLocal(context);


        if(serviceConfigurationLocal==null)
        {
            return null;
        }
        else
        {
            return serviceConfigurationLocal.getServiceName() + " | " + serviceConfigurationLocal.getCity();
        }
    }





    public static String getServiceURL_SDS(Context context) {

        context = MyApplication.getAppContext();

        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_name), MODE_PRIVATE);

        //service_url = "http://localareademo-env.ap-southeast-1.elasticbeanstalk.com";

        return sharedPref.getString(TAG_SDS_URL, SERVICE_URL_SDS);
    }







    public static void saveServiceURL_SDS(String service_url, Context context)
    {
//        Context context = MyApplication.getAppContext();
        // get a handle to shared Preference
        SharedPreferences sharedPref;

        sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_name),
                MODE_PRIVATE);



        // write to the shared preference
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(TAG_SDS_URL, service_url);

        editor.apply();
    }


}
