package org.nearbyshops.serviceprovider.StaffAccounts;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import org.nearbyshops.serviceprovider.ModelRoles.Staff;
import org.nearbyshops.serviceprovider.R;
import org.nearbyshops.serviceprovider.Utility.UtilityGeneral;

import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sumeet on 13/6/16.
 */
class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    private List<Staff> dataset = null;
    private NotifyConfirmOrder notifyConfirmOrder;
    private Context context;

    Adapter(List<Staff> dataset, NotifyConfirmOrder notifyConfirmOrder, Context context) {
        this.dataset = dataset;
        this.notifyConfirmOrder = notifyConfirmOrder;
        this.context = context;
    }

    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_staff,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Adapter.ViewHolder holder, int position) {

        Staff staff = dataset.get(position);

        holder.staffID.setText("Staff ID : " + String.valueOf(staff.getUserID()));
        holder.staffName.setText(staff.getStaffName());
        holder.phoneNumber.setText(staff.getPhone());
        holder.designation.setText(staff.getDesignation());


//        holder.shopID.setText("Shop ID : " + String.valueOf(shop.getShopID()));
//        holder.address.setText(shop.getShopAddress() + "\n" + shop.getCity() + " - " + shop.getPincode());
//        holder.shopName.setText(shop.getShopName());

        Drawable drawable = ContextCompat.getDrawable(context,R.drawable.ic_nature_people_white_48px);
        String imagePath = UtilityGeneral.getServiceURL(context) + "/api/v1/Staff/Image/" + "three_hundred_"+ staff.getProfileImageURL() + ".jpg";

        Picasso.with(context)
                .load(imagePath)
                .placeholder(drawable)
                .into(holder.profilePicture);

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.staff_id) TextView staffID;
        @BindView(R.id.staff_name) TextView staffName;
        @BindView(R.id.phone_number) TextView phoneNumber;
        @BindView(R.id.designation) TextView designation;

//        @BindView(R.id.shop_id) TextView shopID;
//        @BindView(R.id.shop_name) TextView shopName;
//        @BindView(R.id.address) TextView address;
        @BindView(R.id.profile_picture) ImageView profilePicture;


        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @OnClick({R.id.edit_icon,R.id.edit_text})
        void editClick()
        {
            notifyConfirmOrder.notifyEditClick(dataset.get(getLayoutPosition()));
        }


        @Override
        public void onClick(View v) {
            notifyConfirmOrder.notifyListItemClick(dataset.get(getLayoutPosition()));
        }
    }






    interface NotifyConfirmOrder{
        void notifyEditClick(Staff staff);
        void notifyListItemClick(Staff staff);
    }

}
