package org.nearbyshops.serviceprovider.DaggerModules;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.nearbyshops.serviceprovider.MyApplication;
import org.nearbyshops.serviceprovider.RetrofitRESTContract.AdminService;
import org.nearbyshops.serviceprovider.RetrofitRESTContract.DistributorAccountService;
import org.nearbyshops.serviceprovider.RetrofitRESTContract.ItemCategoryService;
import org.nearbyshops.serviceprovider.RetrofitRESTContract.StaffLoginService;
import org.nearbyshops.serviceprovider.RetrofitRESTContract.UserService;
import org.nearbyshops.serviceprovider.RetrofitRESTContractItem.ItemImageService;
import org.nearbyshops.serviceprovider.RetrofitRESTContractItem.ItemImageSubmissionService;
import org.nearbyshops.serviceprovider.RetrofitRESTContractItem.ItemService;
import org.nearbyshops.serviceprovider.RetrofitRESTContractItem.ItemSpecItemService;
import org.nearbyshops.serviceprovider.RetrofitRESTContractItem.ItemSpecNameService;
import org.nearbyshops.serviceprovider.RetrofitRESTContractItem.ItemSpecValueService;
import org.nearbyshops.serviceprovider.RetrofitRESTContract.ServiceConfigurationService;
import org.nearbyshops.serviceprovider.RetrofitRESTContract.SettingsService;
import org.nearbyshops.serviceprovider.RetrofitRESTContract.ShopAdminService;
import org.nearbyshops.serviceprovider.RetrofitRESTContract.ShopService;
import org.nearbyshops.serviceprovider.RetrofitRESTContract.StaffService;
import org.nearbyshops.serviceprovider.RetrofitRESTContractGIDB.ItemCategoryServiceGIDB;
import org.nearbyshops.serviceprovider.RetrofitRESTContractGIDB.ItemServiceGIDB;
import org.nearbyshops.serviceprovider.RetrofitRESTContractItem.ItemSubmissionService;
import org.nearbyshops.serviceprovider.RetrofitRESTContractSDS.ServiceConfigService;
import org.nearbyshops.serviceprovider.Utility.PrefGeneral;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sumeet on 14/5/16.
 */

        /*
        retrofit = new Retrofit.Builder()
                .baseUrl(UtilityGeneral.getServiceURL(context))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        */

@Module
public class NetModule {

    String serviceURL;

    // Constructor needs one parameter to instantiate.
    public NetModule() {

    }

    // Dagger will only look for methods annotated with @Provides
    @Provides
    @Singleton
    // Application reference must come from AppModule.class
    SharedPreferences providesSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    /*
    @Provides
    @Singleton
    Cache provideOkHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    */

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        //gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

//        .setDateFormat("yyyy-MM-dd hh:mm:ss.S")
    }


    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {

        // Cache cache

        // cache is commented out ... you can add cache by putting it back in the builder options
        //.cache(cache)

        return new OkHttpClient()
                .newBuilder()
                .build();
    }


    //    @Singleton

    @Provides @Named("normal")
//    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(PrefGeneral.getServiceURL(MyApplication.getAppContext()))
                .build();

        //        .client(okHttpClient)

        Log.d("applog","Retrofit : " + PrefGeneral.getServiceURL(MyApplication.getAppContext()));


        return retrofit;
    }



    @Provides @Named("gidb")
    Retrofit provideRetrofitGIDB(Gson gson, OkHttpClient okHttpClient) {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(PrefGeneral.getServiceURL_GIDB(MyApplication.getAppContext()))
                .build();

        //        .client(okHttpClient)

        Log.d("applog","Retrofit : " + PrefGeneral.getServiceURL_GIDB(MyApplication.getAppContext()));


        return retrofit;
    }



    @Provides @Named("reactive")
//    @Singleton
    Retrofit provideRetrofitReactive(Gson gson, OkHttpClient okHttpClient) {


//        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.create();

//        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(PrefGeneral.getServiceURL(MyApplication.getAppContext()))
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        //        .client(okHttpClient)

        Log.d("applog","Retrofit : " + PrefGeneral.getServiceURL(MyApplication.getAppContext()));


        return retrofit;
    }

    @Provides @Named("sds")
    Retrofit provideRetrofitSDS(Gson gson, OkHttpClient okHttpClient) {

        //        .client(okHttpClient)

//        Log.d("applog","Retrofit : " + UtilityGeneral.getServiceURL_SDS(MyApplication.getAppContext()));


        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(PrefGeneral.getServiceURL_SDS(MyApplication.getAppContext()))
                .build();
    }



    @Provides
    ServiceConfigService provideServiceConfig(@Named("sds")Retrofit retrofit)
    {
        return retrofit.create(ServiceConfigService.class);
    }






    @Provides
    ItemCategoryServiceGIDB provideItemCategoryServiceGIDB(@Named("gidb")Retrofit retrofit)
    {
        return retrofit.create(ItemCategoryServiceGIDB.class);
    }

    @Provides
    ItemServiceGIDB provideItemServiceGIDB(@Named("gidb")Retrofit retrofit)
    {
        return retrofit.create(ItemServiceGIDB.class);
    }




    @Provides
    ServiceConfigurationService provideConfigurationService(@Named("normal")Retrofit retrofit)
    {
        return retrofit.create(ServiceConfigurationService.class);
    }


    @Provides
    ItemCategoryService provideItemCategoryService(@Named("normal")Retrofit retrofit)
    {
        return retrofit.create(ItemCategoryService.class);
    }



    @Provides
    ItemService provideItemService(@Named("normal")Retrofit retrofit)
    {
        return retrofit.create(ItemService.class);
    }


    @Provides
    ItemImageService provideItemImageService(@Named("normal")Retrofit retrofit)
    {
        return retrofit.create(ItemImageService.class);
    }


    @Provides
    ItemSpecItemService provideItemSpecItem(@Named("normal")Retrofit retrofit)
    {
        return retrofit.create(ItemSpecItemService.class);
    }

    @Provides
    ItemSpecNameService provideSpecNameService(@Named("normal")Retrofit retrofit)
    {
        return retrofit.create(ItemSpecNameService.class);
    }



    @Provides
    ItemSpecValueService provideSpecValueService(@Named("normal")Retrofit retrofit)
    {
        return retrofit.create(ItemSpecValueService.class);
    }





    @Provides
    AdminService provideAdminService(@Named("normal")Retrofit retrofit)
    {
        return retrofit.create(AdminService.class);
    }


    @Provides
    UserService provideUserService(@Named("normal")Retrofit retrofit)
    {
        return retrofit.create(UserService.class);
    }

    @Provides
    StaffLoginService provideStaffLogin(@Named("normal")Retrofit retrofit)
    {
        return retrofit.create(StaffLoginService.class);
    }


    @Provides
    StaffService provideStaffService(@Named("normal")Retrofit retrofit)
    {
        return retrofit.create(StaffService.class);
    }


    @Provides
    SettingsService provideSettingsService(@Named("normal")Retrofit retrofit)
    {
        return retrofit.create(SettingsService.class);
    }

    @Provides
    DistributorAccountService provideDistributorService(@Named("normal")Retrofit retrofit)
    {
        return retrofit.create(DistributorAccountService.class);
    }


    @Provides
    ShopAdminService provideShopAdmin(@Named("normal")Retrofit retrofit)
    {
        return retrofit.create(ShopAdminService.class);
    }


    @Provides
    ShopService provideShopService(@Named("normal")Retrofit retrofit)
    {
        return retrofit.create(ShopService.class);
    }



    @Provides
    ItemSubmissionService provideItemSubmission(@Named("normal")Retrofit retrofit)
    {
        return retrofit.create(ItemSubmissionService.class);
    }


    @Provides
    ItemImageSubmissionService provideItemImageSubmission(@Named("normal")Retrofit retrofit)
    {
        return retrofit.create(ItemImageSubmissionService.class);
    }




//    @Provides
//    OrderService provideOrderService(Retrofit retrofit)
//    {
//        OrderService service = retrofit.create(OrderService.class);
//
//        return service;
//    }

}
