package org.nearbyshops.serviceprovider.Markets.ViewHolders;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.Gson;


import org.nearbyshops.serviceprovider.DaggerComponentBuilder;
import org.nearbyshops.serviceprovider.ItemsByCategorySimple.Utility.HeaderItemsList;
import org.nearbyshops.serviceprovider.Markets.Interfaces.listItemMarketNotifications;
import org.nearbyshops.serviceprovider.Markets.Model.ConnectWithURLMarker;
import org.nearbyshops.serviceprovider.Markets.Model.CreateMarketMarker;
import org.nearbyshops.serviceprovider.Markets.Model.ServiceConfigurationGlobal;
import org.nearbyshops.serviceprovider.Markets.Model.ServiceConfigurationLocal;
import org.nearbyshops.serviceprovider.Markets.Model.SignInMarker;
import org.nearbyshops.serviceprovider.ModelRoles.User;
import org.nearbyshops.serviceprovider.R;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sumeet on 13/6/16.
 */
public class AdapterMarkets extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> dataset = null;



    private static final int view_type_current_market = 1;
    private static final int view_type_saved_markets_list = 2;
    private static final int view_type_user_profile = 3;
    private static final int view_type_sign_in = 4;
    private static final int view_type_connect_with_url = 5;
    private static final int view_type_markets_header = 6;
    private static final int VIEW_TYPE_Market = 7;
    private static final int VIEW_TYPE_create_market = 8;
    private static final int VIEW_TYPE_SCROLL_PROGRESS_BAR = 9;


    @Inject Gson gson;
    private Fragment fragment;
//    private int total_items_count;
    private boolean loadMore;



    public AdapterMarkets(List<Object> dataset, Fragment fragment) {

        this.dataset = dataset;
        this.fragment = fragment;




        DaggerComponentBuilder.getInstance()
                .getNetComponent()
                .Inject(this);
    }





    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;

        if(viewType == view_type_current_market)
        {
//            view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.list_item_market_current,parent,false);
//
//            return new ViewHolderCurrentMarket(view,fragment.getActivity());


            return ViewHolderCurrentMarket.create(parent,fragment.getActivity());

        }
        else if(viewType==view_type_saved_markets_list)
        {

            return ViewHolderSavedMarketList.create(parent,fragment.getActivity(), (listItemMarketNotifications) fragment);

        }
        else if(viewType == view_type_user_profile)
        {
//            view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.list_item_user_profile,parent,false);
//
//            return new ViewHolderUserProfile(view,fragment.getActivity());

            return ViewHolderUserProfile.create(parent,fragment.getActivity());

        }
        else if(viewType == view_type_sign_in)
        {
            return ViewHolderSignIn.create(parent,fragment.getActivity());
        }
        else if(viewType == view_type_connect_with_url)
        {
            return ViewHolderConnectWithURL.create(parent,fragment.getActivity(), (listItemMarketNotifications) fragment);

        }
        else if(viewType == view_type_markets_header)
        {
//            view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.list_item_header_markets,parent,false);
//
//            return new ViewHolderHeaderMarket(view);

            return ViewHolderHeaderMarket.create(parent,fragment.getActivity());

        }
        else if (viewType == VIEW_TYPE_Market) {


//            view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.list_item_market, parent, false);

//            return new ViewHolderMarket(view, parent, fragment.getActivity(), (ViewHolderMarket.listItemMarketNotifications) fragment);

            return ViewHolderMarket.create(parent,fragment.getActivity(), (listItemMarketNotifications) fragment);


        } else if (viewType == VIEW_TYPE_SCROLL_PROGRESS_BAR) {

            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_progress_bar, parent, false);

            return new LoadingViewHolder(view);
        }
        else if(viewType==VIEW_TYPE_create_market)
        {
            return ViewHolderEmptyScreen.create(parent,fragment.getActivity());
        }


        return null;
    }


    @Override
    public int getItemViewType(int position) {

        super.getItemViewType(position);

        if (position == dataset.size()) {

            return VIEW_TYPE_SCROLL_PROGRESS_BAR;
        }
        else if(dataset.get(position) instanceof ConnectWithURLMarker)
        {
            return view_type_connect_with_url;
        }
        else if(dataset.get(position) instanceof ServiceConfigurationLocal)
        {
            return view_type_current_market;
        }
        else if(dataset.get(position) instanceof List<?>)
        {
            return view_type_saved_markets_list;
        }
        else if(dataset.get(position) instanceof User)
        {
            return view_type_user_profile;
        }
        else if(dataset.get(position) instanceof SignInMarker)
        {
            return view_type_sign_in;
        }
        else if(dataset.get(position) instanceof HeaderItemsList)
        {
            return view_type_markets_header;
        }
        else if(dataset.get(position) instanceof ServiceConfigurationGlobal){

            return VIEW_TYPE_Market;
        }
        else if(dataset.get(position) instanceof CreateMarketMarker)
        {
            return VIEW_TYPE_create_market;
        }

        return -1;
    }




    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holderVH, int position) {


        if(holderVH instanceof ViewHolderCurrentMarket)
        {
            ViewHolderCurrentMarket holderCurrentMarket = (ViewHolderCurrentMarket) holderVH;
            holderCurrentMarket.setItem((ServiceConfigurationLocal) dataset.get(position));

        }
        else if(holderVH instanceof ViewHolderSavedMarketList)
        {
            ((ViewHolderSavedMarketList) holderVH).setItem((List<ServiceConfigurationGlobal>) dataset.get(position));
        }
        else if(holderVH instanceof ViewHolderUserProfile)
        {
            ViewHolderUserProfile viewHolderUserProfile = (ViewHolderUserProfile) holderVH;
            viewHolderUserProfile.setItem((User) dataset.get(position));

        }
        else if (holderVH instanceof ViewHolderMarket) {

//            ViewHolderMarket holder = (ViewHolderMarket) holderVH;
//            holder.setItem((ServiceConfigurationGlobal) dataset.get(position));


            ((ViewHolderMarket)holderVH).setItem((ServiceConfigurationGlobal) dataset.get(position));

        }
        else if (holderVH instanceof LoadingViewHolder) {


            LoadingViewHolder viewHolder = (LoadingViewHolder) holderVH;


            if (loadMore) {
                viewHolder.progressBar.setVisibility(View.VISIBLE);
                viewHolder.progressBar.setIndeterminate(true);
            }
            else {
                viewHolder.progressBar.setVisibility(View.GONE);
            }
        }
    }




    public void setLoadMore(boolean loadMore)
    {
        this.loadMore = loadMore;
    }





    public void setData(List<Object> data)
    {
        dataset = data;
    }


//
//    void setTotalItemsCount(int totalItemsCount)
//    {
//        total_items_count = totalItemsCount;
//    }





    @Override
    public int getItemCount() {


//
//        if(dataset!=null)
//        {
//            return (dataset.size()+1);
//        }
//        else
//        {
//            return 0;
//        }


        return (dataset.size()+1);
    }




    public class LoadingViewHolder extends  RecyclerView.ViewHolder{

        @BindView(R.id.progress_bar)
        ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }




    public class SavedMarketsMarker{

    }


}
