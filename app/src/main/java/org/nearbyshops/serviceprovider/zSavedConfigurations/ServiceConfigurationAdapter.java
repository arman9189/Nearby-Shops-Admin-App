package org.nearbyshops.serviceprovider.zSavedConfigurations;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import org.nearbyshops.serviceprovider.ModelServiceConfig.ServiceConfigurationLocal;
import org.nearbyshops.serviceprovider.R;
import org.nearbyshops.serviceprovider.ServiceConfiguration.EditServiceConfiguration;
import org.nearbyshops.serviceprovider.Utility.PrefGeneral;

import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sumeet on 6/6/16.
 */
public class ServiceConfigurationAdapter extends RecyclerView.Adapter<ServiceConfigurationAdapter.ViewHolder>{


    List<ServiceConfigurationLocal> dataset = null;


    Context context;
    NotificationReceiver notificationReceiver;



    public ServiceConfigurationAdapter(List<ServiceConfigurationLocal> dataset, Context context, NotificationReceiver notificationReceiver) {

        this.dataset = dataset;
        this.context = context;
        this.notificationReceiver = notificationReceiver;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_service,parent,false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ServiceConfigurationLocal serviceConfiguration = dataset.get(position);

        if(serviceConfiguration != null)
        {

            holder.serviceID.setText("ID : " + String.valueOf(serviceConfiguration.getServiceID()));

//            holder.nickname.setText(serviceConfiguration.getConfigurationNickname());
            holder.serviceName.setText(serviceConfiguration.getServiceName());

            holder.serviceCityCountry.setText(serviceConfiguration.getCity() + ", " + serviceConfiguration.getCountry());
            holder.serviceRange.setText("Coverage : " + serviceConfiguration.getServiceRange() + " Km");

            holder.serviceDistance.setText("Distance : " + String.format("%.2f", serviceConfiguration.getRt_distance()) + " Km");

//            holder.serviceTypeBadge.setText();

            holder.serviceTypeBadge.setText( Constants.getServiceType(serviceConfiguration.getServiceType()) + " | " +  Constants.getServiceLevel(serviceConfiguration.getServiceLevel()));

            Picasso.with(context).load(PrefGeneral
                    .getConfigImageEndpointURL(context) + serviceConfiguration.getLogoImagePath())
                    .into(holder.serviceImage);




        }
    }



    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {



        TextView editButton;
        TextView removeButton;
        RelativeLayout listItemDeliveryAddress;

        @BindView(R.id.service_id)
        TextView serviceID;

        @BindView(R.id.nickname)
        TextView nickname;

        @BindView(R.id.service_name)
        TextView serviceName;

        @BindView(R.id.service_city_country)
        TextView serviceCityCountry;

        @BindView(R.id.service_image)
        ImageView serviceImage;

        @BindView(R.id.service_range)
        TextView serviceRange;

        @BindView(R.id.service_distance)
        TextView serviceDistance;

        @BindView(R.id.service_rating)
        TextView serviceRating;

        @BindView(R.id.more_options)
        ImageView moreOptions;

        @BindView(R.id.service_type_badge)
        TextView serviceTypeBadge;







        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);



//            editButton.setOnClickListener(this);
//            removeButton.setOnClickListener(this);
//            listItemDeliveryAddress.setOnClickListener(this);
        }




        @OnClick(R.id.more_options)
        void optionsOverflowClick(View v)
        {
            PopupMenu popup = new PopupMenu(context, v);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.saved_configuration_item_overflow, popup.getMenu());
            popup.setOnMenuItemClickListener(this);
            popup.show();
        }

        @Override
        public void onClick(View v) {

            switch (v.getId())
            {
//                case R.id.editButton:

//                    editClick();
//
//                    break;
//
//                case R.id.removeButton:
//
//                    removeClick();
//
//                    break;
//
//                case R.id.list_item_delivery_vehicle_self:
//
//                    listItemClick();
//
//                    break;


                default:
                    break;
            }

        }


        public void listItemClick()
        {
            notificationReceiver.notifyListItemClick(dataset.get(getLayoutPosition()));
        }

        public void removeClick()
        {
            notificationReceiver.notifyRemove(dataset.get(getLayoutPosition()));
        }

        public void editClick()
        {
            notificationReceiver.notifyEdit(dataset.get(getLayoutPosition()));
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {

            switch (item.getItemId())
            {
                case R.id.action_remove:

                    item.setChecked(true);



                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    builder.setTitle("Confirm Remove ServiceConfigurationLocal Configuration !")
                            .setMessage("Do you want to remove this configuration.")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    //showToastMessage("Removed in process. ");



                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Toast.makeText(context,"Cancelled !",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .show();


                    break;

                case R.id.action_edit:

//                    Toast.makeText(context,"Real Edit !",Toast.LENGTH_SHORT).show();

                    Intent editIntent = new Intent(context,EditServiceConfiguration.class);

//                    editIntent.putExtra(EditServiceConfiguration.SERVICE_CONFIG_INTENT_KEY,dataset.get(getLayoutPosition()));
                    context.startActivity(editIntent);

                    break;



                default:

                    break;

            }


            return false;
        }
    }// view holder ends


    void showToastMessage(String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }



    public interface NotificationReceiver{

        void notifyEdit(ServiceConfigurationLocal serviceConfiguration);

        void notifyRemove(ServiceConfigurationLocal serviceConfiguration);

        void notifyListItemClick(ServiceConfigurationLocal serviceConfiguration);

    }

}
