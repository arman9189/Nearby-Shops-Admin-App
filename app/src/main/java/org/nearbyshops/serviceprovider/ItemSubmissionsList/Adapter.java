package org.nearbyshops.serviceprovider.ItemSubmissionsList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import org.nearbyshops.serviceprovider.Model.Item;
import org.nearbyshops.serviceprovider.ModelItemSubmission.ItemSubmission;
import org.nearbyshops.serviceprovider.R;
import org.nearbyshops.serviceprovider.Utility.UtilityGeneral;

import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sumeet on 19/12/15.
 */


public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private List<Object> dataset;
    private Context context;
    private NotificationsFromAdapter notificationReceiver;
    private Fragment fragment;

    public static final int VIEW_TYPE_ITEM_ADDITIONS = 1;
    public static final int VIEW_TYPE_ITEM_UPDATES = 2;
    public static final int VIEW_TYPE_HEADER = 3;
    private final static int VIEW_TYPE_PROGRESS_BAR = 4;



    public Adapter(List<Object> dataset, Context context, NotificationsFromAdapter notificationReceiver, Fragment fragment) {

//        DaggerComponentBuilder.getInstance()
//                .getNetComponent().Inject(this);

        this.notificationReceiver = notificationReceiver;
        this.dataset = dataset;
        this.context = context;
        this.fragment = fragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;

        if(viewType == VIEW_TYPE_ITEM_ADDITIONS)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_item_submission,parent,false);
            return new ViewHolderItemSubmissions(view);
        }
        else if(viewType == VIEW_TYPE_ITEM_UPDATES)
        {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_item_submission_update,parent,false);
            return new ViewHolderItemsUpdated(view);
        }
        else if(viewType == VIEW_TYPE_HEADER)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_header_type_simple,parent,false);
            return new ViewHolderHeader(view);
        }
        else if (viewType == VIEW_TYPE_PROGRESS_BAR)
        {

            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_progress_bar,parent,false);

            return new LoadingViewHolder(view);
        }


//        else
//        {
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_item_guide,parent,false);
//            return new ViewHolderItemSimple(view);
//        }

        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof ViewHolderItemSubmissions)
        {
            bindItemSubissions((ViewHolderItemSubmissions) holder,position);
        }
        else if(holder instanceof ViewHolderItemsUpdated)
        {
            bindItem((ViewHolderItemsUpdated) holder,position);
        }
        else if(holder instanceof ViewHolderHeader)
        {
            if(dataset.get(position) instanceof HeaderTitle)
            {
                HeaderTitle header = (HeaderTitle) dataset.get(position);

                ((ViewHolderHeader) holder).header.setText(header.getHeading());
            }

        }
        else if(holder instanceof LoadingViewHolder)
        {

            LoadingViewHolder viewHolder = (LoadingViewHolder) holder;

            int itemCount = 0;

            if(fragment instanceof ItemReviewFragment)
            {
                itemCount = (((ItemReviewFragment) fragment).item_count_item_added + ((ItemReviewFragment) fragment).item_count_item_updated + ((ItemReviewFragment) fragment).item_count_images_updated+ 3);
            }


            if(position == 0 || position == itemCount)
            {
                viewHolder.progressBar.setVisibility(View.GONE);
            }
            else
            {
                viewHolder.progressBar.setVisibility(View.VISIBLE);
                viewHolder.progressBar.setIndeterminate(true);

            }

        }


    }


    @Override
    public int getItemViewType(int position) {

        super.getItemViewType(position);

        if(position == dataset.size())
        {
            return VIEW_TYPE_PROGRESS_BAR;
        }
        if(dataset.get(position) instanceof ItemSubmission)
        {
            return VIEW_TYPE_ITEM_ADDITIONS;
        }
        else if (dataset.get(position) instanceof Item)
        {
            return VIEW_TYPE_ITEM_UPDATES;
        }
        else if(dataset.get(position) instanceof HeaderTitle)
        {
            return VIEW_TYPE_HEADER;
        }


        return -1;
    }

    @Override
    public int getItemCount() {

        return (dataset.size()+1);
    }





    class ViewHolderHeader extends RecyclerView.ViewHolder{


        @BindView(R.id.header)
        TextView header;

        public ViewHolderHeader(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }// ViewHolder Class declaration ends



    void bindItemSubissions(ViewHolderItemSubmissions holder,int position)
    {

            Item item = ((ItemSubmission)dataset.get(position)).getItem();

            holder.itemName.setText(item.getItemName());
            holder.item_unit.setText(item.getQuantityUnit());
            holder.itemDescription.setText(item.getItemDescription());

            Drawable drawable = ContextCompat.getDrawable(context,R.drawable.ic_nature_people_white_48px);
            String imagePath = UtilityGeneral.getServiceURL(context) + "/api/v1/Item/Image/" + "three_hundred_"+ item.getItemImageURL() + ".jpg";

            Picasso.with(context)
                    .load(imagePath)
                    .placeholder(drawable)
                    .into(holder.itemImage);
    }



    class ViewHolderItemSubmissions extends RecyclerView.ViewHolder{


        @BindView(R.id.description_short) TextView itemDescription;
        @BindView(R.id.quantity_unit) TextView item_unit;
        @BindView(R.id.itemName) TextView itemName;
        @BindView(R.id.items_list_item) CardView itemCategoryListItem;
        @BindView(R.id.itemImage) ImageView itemImage;

        public ViewHolderItemSubmissions(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }


        @OnClick(R.id.items_list_item)
        void listItemClick()
        {
            notificationReceiver.itemAddedClick((ItemSubmission) dataset.get(getLayoutPosition()),getLayoutPosition());

        }

    }// ViewHolder Class declaration ends




    void bindItem(ViewHolderItemsUpdated holder,int position)
    {

        Item item = (Item) dataset.get(position);

        holder.itemName.setText(item.getItemName());
        holder.latestUpdate.setText("Latest Update :\n" + item.getItemStats().getLatestUpdate().toLocaleString());
        holder.updatesCount.setText("Updates Count : " + String.valueOf(item.getItemStats().getSubmissionsCount()));


        String imagePath = UtilityGeneral.getServiceURL(context)
                + "/api/v1/Item/Image/three_hundred_" + item.getItemImageURL() + ".jpg";


        Drawable drawable = VectorDrawableCompat
                .create(context.getResources(),
                        R.drawable.ic_nature_people_white_48px, context.getTheme());

        Picasso.with(context).load(imagePath)
                .placeholder(drawable)
                .into(holder.itemImage);

    }



    class ViewHolderItemsUpdated extends RecyclerView.ViewHolder {


        @BindView(R.id.itemName) TextView itemName;
        @BindView(R.id.updates_count) TextView updatesCount;
        @BindView(R.id.latest_update) TextView latestUpdate;
        @BindView(R.id.itemImage) ImageView itemImage;




        public ViewHolderItemsUpdated(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }


        @OnClick(R.id.items_list_item)
        void listItemClick()
        {
            notificationReceiver.itemUpdatedClick((Item) dataset.get(getLayoutPosition()),getLayoutPosition());
        }


    }// ViewHolder Class declaration ends


    private void showToastMessage(String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }




    interface NotificationsFromAdapter
    {
        // method for notifying the list object to request sub category

        void notifyDeleteItem(Item item, int position);
        void itemAddedClick(ItemSubmission itemSubmission, int position);
        void itemUpdatedClick(Item item, int position);
    }



    public class LoadingViewHolder extends  RecyclerView.ViewHolder{

        @BindView(R.id.progress_bar)
        ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }






}