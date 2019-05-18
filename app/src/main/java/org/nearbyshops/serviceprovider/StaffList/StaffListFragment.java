package org.nearbyshops.serviceprovider.StaffList;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;


import org.nearbyshops.serviceprovider.DaggerComponentBuilder;
import org.nearbyshops.serviceprovider.ItemSubmissionsList.HeaderTitle;
import org.nearbyshops.serviceprovider.ModelEndPoints.UserEndpoint;
import org.nearbyshops.serviceprovider.ModelRoles.User;
import org.nearbyshops.serviceprovider.R;
import org.nearbyshops.serviceprovider.RetrofitRESTContract.StaffLoginService;
import org.nearbyshops.serviceprovider.RetrofitRESTContract.UserService;
import org.nearbyshops.serviceprovider.StaffList.EditProfileStaff.EditProfileStaff;
import org.nearbyshops.serviceprovider.StaffList.EditProfileStaff.FragmentEditProfileStaff;
import org.nearbyshops.serviceprovider.Preferences.PrefLocationDeprecated;
import org.nearbyshops.serviceprovider.Preferences.PrefLogin;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sumeet on 14/6/17.
 */

public class StaffListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, Adapter.NotificationsFromAdapter{

    boolean isDestroyed = false;

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;


    @Inject
    UserService userService;

    @Inject
    StaffLoginService service;

    GridLayoutManager layoutManager;
    Adapter listAdapter;

    ArrayList<Object> dataset = new ArrayList<>();


    // flags
    boolean clearDataset = false;

    boolean getRowCountVehicle = false;
    boolean resetOffsetVehicle = false;


    private int limit_vehicle = 10;
    int offset_vehicle = 0;
    public int item_count_vehicle = 0;


//    @BindView(R.id.drivers_count) TextView driversCount;
//    int i = 1;

    public StaffListFragment() {

        DaggerComponentBuilder.getInstance()
                .getNetComponent().Inject(this);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        setRetainInstance(true);
        View rootView = inflater.inflate(R.layout.fragment_staff_list, container, false);
        ButterKnife.bind(this,rootView);


//        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
//        toolbar.setTitleTextColor(ContextCompat.getColor(getActivity(),R.color.white));
//        toolbar.setTitle("Trip History");
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);



        setupSwipeContainer();
        setupRecyclerView();

        if(savedInstanceState == null)
        {
            makeRefreshNetworkCall();
        }


//        driversCount.setText("Drivers COunt : " + String.valueOf(++i));

        return rootView;
    }



    void setupSwipeContainer()
    {

        if(swipeContainer!=null) {

            swipeContainer.setOnRefreshListener(this);
            swipeContainer.setColorSchemeResources(
                    android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);
        }

    }



    void setupRecyclerView()
    {

        listAdapter = new Adapter(dataset,getActivity(),this,this);
        recyclerView.setAdapter(listAdapter);

        layoutManager = new GridLayoutManager(getActivity(),1, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));


        final DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {


            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


                if(layoutManager.findLastVisibleItemPosition()==dataset.size())
                {

                    if(offset_vehicle + limit_vehicle > layoutManager.findLastVisibleItemPosition())
                    {
                        return;
                    }


                    // trigger fetch next page

                    if((offset_vehicle + limit_vehicle)<= item_count_vehicle)
                    {
                        offset_vehicle = offset_vehicle + limit_vehicle;

                        getTripHistory();
                    }


                }
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        isDestroyed = false;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        isDestroyed = true;
    }



    void makeRefreshNetworkCall()
    {
        swipeContainer.post(new Runnable() {
            @Override
            public void run() {

                swipeContainer.setRefreshing(true);
                onRefresh();
            }
        });
    }




    @Override
    public void onRefresh() {

        clearDataset = true;
        getRowCountVehicle = true;
        resetOffsetVehicle = true;

        getTripHistory();
    }






    /* Token renewal variables : BEGIN */

    // constants - request codes for token renewal
    public static final int REQUEST_CODE_GET_REQUESTS = 1;
    private static final int REQUEST_CODE_GET_CURRENT_TRIP = 2;

    // housekeeping for token renewal
    int token_renewal_attempts = 0;  // variable to keep record of renewal attempts
    int token_renewal_request_code = -1; // variable to store the request code;

    /* Token renewal variables : END */





    void getTripHistory()
    {

        if(resetOffsetVehicle)
        {
            offset_vehicle = 0;
            resetOffsetVehicle = false;
        }


        User user = PrefLogin.getUser(getActivity());

        if(user ==null)
        {
            swipeContainer.setRefreshing(false);
            return;
        }



        Call<UserEndpoint> call = service.getStaffForAdmin(
                PrefLogin.getAuthorizationHeaders(getActivity()),
                (double) PrefLocationDeprecated.getLatitideCurrent(getActivity()),(double) PrefLocationDeprecated.getLongitudeCurrent(getActivity()),
                null,null,
                null,null,
                limit_vehicle,offset_vehicle,
                getRowCountVehicle,false
        );


        call.enqueue(new Callback<UserEndpoint>() {
            @Override
            public void onResponse(Call<UserEndpoint> call, Response<UserEndpoint> response) {


                if(isDestroyed)
                {
                    return;
                }

                if(response.code() == 200 && response.body()!=null) {

                    if (clearDataset) {
                        dataset.clear();
                        clearDataset = false;

//                        dataset.add(new FilterSubmissions());
                    }


                    if (getRowCountVehicle) {

                        item_count_vehicle = response.body().getItemCount();
                        getRowCountVehicle = false;

//                            dataset.add(new HeaderTitle("Type of Data"));

                        dataset.add(new HeaderTitle("Staff Members"));
                    }


                    if(response.body().getResults()!=null)
                    {
                        dataset.addAll(response.body().getResults());
                    }

                    listAdapter.notifyDataSetChanged();
                }


                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<UserEndpoint> call, Throwable t) {


                if(isDestroyed)
                {
                    return;
                }

                showToastMessage("Network Connection Failed !");

                swipeContainer.setRefreshing(false);
            }
        });


    }




    void showToastMessage(String message)
    {
        Toast.makeText(getActivity(),message, Toast.LENGTH_SHORT).show();
    }



//    @Override
//    public void taxiFiltersChanged() {
//        makeRefreshNetworkCall();
//    }



    @Override
    public void notifyTripRequestSelected() {

    }





    @Override
    public void listItemClick(User user, int position) {

        Gson gson = new Gson();
        String jsonString = gson.toJson(user);
//
//        Intent intent = new Intent(getActivity(), TripHistoryDetail.class);
//        intent.putExtra(TripHistoryDetail.TRIP_HISTORY_DETAIL_INTENT_KEY,jsonString);
//        startActivity(intent);


        Intent intent = new Intent(getActivity(), EditProfileStaff.class);
        intent.putExtra("staff_profile",jsonString);
        intent.putExtra(FragmentEditProfileStaff.EDIT_MODE_INTENT_KEY, FragmentEditProfileStaff.MODE_UPDATE);
        startActivity(intent);
    }




    @Override
    public boolean listItemLongClick(View view, User tripRequest, int position) {
        return false;
    }






}
