package org.nearbyshops.serviceprovider.ItemsByCategorySimple.ItemImageList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import org.nearbyshops.serviceprovider.ItemSubmissionsList.HeaderTitle;
import org.nearbyshops.serviceprovider.Model.ItemImage;
import org.nearbyshops.serviceprovider.R;
import org.nearbyshops.serviceprovider.Utility.PrefGeneral;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * Created by sumeet on 19/12/15.
 */


public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    // keeping track of selections
    Map<Integer, ItemImage> selectedItems = new HashMap<>();
    ItemImage selectedItemSingle;


    private List<Object> dataset;
    private Context context;
    private NotificationsFromAdapter notificationReceiver;
    private Fragment fragment;

    public static final int VIEW_TYPE_TRIP_REQUEST = 1;
    public static final int VIEW_TYPE_HEADER = 5;
    private final static int VIEW_TYPE_PROGRESS_BAR = 6;
//    private final static int VIEW_TYPE_FILTER = 7;
//    private final static int VIEW_TYPE_FILTER_SUBMISSIONS = 8;


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

        if (viewType == VIEW_TYPE_TRIP_REQUEST) {

            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_item_image_new, parent, false);

            return new ViewHolderItemImage(view);
        }
        else if (viewType == VIEW_TYPE_HEADER) {

            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_header_type_simple, parent, false);

            return new ViewHolderHeader(view);

        } else if (viewType == VIEW_TYPE_PROGRESS_BAR) {

            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_progress_bar, parent, false);

            return new LoadingViewHolder(view);

        }

        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ViewHolderItemImage) {

            bindTripRequest((ViewHolderItemImage) holder, position);
        }
        else if (holder instanceof ViewHolderHeader) {

            if (dataset.get(position) instanceof HeaderTitle) {
                HeaderTitle header = (HeaderTitle) dataset.get(position);

                ((ViewHolderHeader) holder).header.setText(header.getHeading());
            }

        } else if (holder instanceof LoadingViewHolder) {

            LoadingViewHolder viewHolder = (LoadingViewHolder) holder;

            int itemCount = 0;


            if (fragment instanceof ImageListFragment) {
                itemCount = (((ImageListFragment) fragment).item_count_vehicle + 1 );
            }


//            itemCount = dataset.size();

            if (position == 0 || position == itemCount) {
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

        if (position == dataset.size()) {
            return VIEW_TYPE_PROGRESS_BAR;
        } else if (dataset.get(position) instanceof HeaderTitle) {
            return VIEW_TYPE_HEADER;
        } else if (dataset.get(position) instanceof ItemImage) {
            return VIEW_TYPE_TRIP_REQUEST;
        }

        return -1;
    }


    @Override
    public int getItemCount() {

        return (dataset.size() + 1);
    }




    void bindTripRequest(ViewHolderItemImage holder, int position)
    {

        if(dataset.get(position) instanceof ItemImage)
        {
            ItemImage taxiImage = (ItemImage) dataset.get(position);

            holder.imageTitle.setText(taxiImage.getCaptionTitle());
            holder.imageDescription.setText(taxiImage.getCaption());
            holder.copyrights.setText(taxiImage.getImageCopyrights());


            Drawable drawable = ContextCompat.getDrawable(context,R.drawable.ic_nature_people_white_48px);

//            String imagePath = PrefGeneral.getServiceURL(context) + "/api/v1/TaxiImages/Image/" + "nine_hundred_"+ taxiImage.getImageFilename() + ".jpg";
//            String image_url = PrefGeneral.getServiceURL(context) + "/api/v1/TaxiImages/Image/" + taxiImage.getImageFilename();


            String imagePath = PrefGeneral.getServiceURL(context) + "/api/v1/ItemImage/Image/five_hundred_" + taxiImage.getImageFilename() + ".jpg";


            Picasso.with(context)
                    .load(imagePath)
                    .placeholder(drawable)
                    .into(holder.taxiImage);



            if(selectedItems.containsKey(taxiImage.getImageID()))
            {
//                holder.listItem.setBackgroundColor(ContextCompat.getColor(context,R.color.gplus_color_2));
//                holder.listItem.animate().scaleXBy(-3);
//                holder.listItem.animate().scaleYBy(-3);
//                holder.listItem.animate().scaleY(-2);

                holder.checkIcon.setVisibility(View.VISIBLE);

            }
            else
            {
//                holder.listItem.setBackgroundColor(ContextCompat.getColor(context,R.color.light_grey));

                holder.checkIcon.setVisibility(View.INVISIBLE);
            }

        }
    }








    class ViewHolderItemImage extends RecyclerView.ViewHolder{


        @BindView(R.id.title)
        TextView imageTitle;
        @BindView(R.id.description)
        TextView imageDescription;
        @BindView(R.id.copyright_info)
        TextView copyrights;
        @BindView(R.id.taxi_image)
        ImageView taxiImage;
        @BindView(R.id.list_item)
        ConstraintLayout listItem;
        @BindView(R.id.check_icon)
        ImageView checkIcon;
//        @BindView(R.id.is_enabled)
//        CheckBox isEnabled;





        public ViewHolderItemImage(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }


        @OnClick(R.id.popup_menu)
        void popupClick(View v)
        {
            PopupMenu menu = new PopupMenu(context,v);

            menu.getMenuInflater().inflate(R.menu.menu_item_image_item,menu.getMenu());

            menu.show();
            menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    switch (item.getItemId())
                    {
                        case R.id.delete :

                            notificationReceiver.deleteClick(
                                    (ItemImage) dataset.get(getLayoutPosition()),
                                    getLayoutPosition()
                            );



                            break;
                    }


                    return false;
                }
            });

        }




        @OnLongClick(R.id.list_item)
        boolean listItemLongClick(View view)
        {

            ItemImage taxiImage = (ItemImage) dataset.get(getLayoutPosition());



            if(selectedItems.containsKey(
                    taxiImage.getImageID()
            ))
            {
                selectedItems.remove(taxiImage.getImageID());
                checkIcon.setVisibility(View.INVISIBLE);

            }else
            {
                selectedItems.put(taxiImage.getImageID(),taxiImage);
                checkIcon.setVisibility(View.VISIBLE);
                selectedItemSingle = taxiImage;
            }



            notificationReceiver.notifyListItemSelected();
//                    notifyItemChanged(getLayoutPosition());



//                    if(selectedItems.containsKey(taxiImage.getImageID()))
//                    {
//
//
//                    }
//                    else
//                    {
//
//                        checkIcon.setVisibility(View.INVISIBLE);
//                    }


            return notificationReceiver.listItemLongClick(view,
                    (ItemImage) dataset.get(getLayoutPosition()),
                    getLayoutPosition()
            );
        }



        @OnClick(R.id.list_item)
        void listItemClick()
        {

            notificationReceiver.listItemClick(
                    (ItemImage) dataset.get(getLayoutPosition()),
                    getLayoutPosition()
            );

        }



//            notifyItemChanged(getLayoutPosition());
        }



    // ViewHolder Class declaration ends






    class ViewHolderHeader extends RecyclerView.ViewHolder{

        @BindView(R.id.header)
        TextView header;

        public ViewHolderHeader(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }// ViewHolder Class declaration ends








    interface NotificationsFromAdapter
    {
        void deleteClick(ItemImage taxiImage, int position);
        void notifyListItemSelected();
        void listItemClick(ItemImage taxiImage, int position);
        boolean listItemLongClick(View view, ItemImage taxiImage, int position);
    }





    class LoadingViewHolder extends  RecyclerView.ViewHolder{

        @BindView(R.id.progress_bar)
        ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }



}
