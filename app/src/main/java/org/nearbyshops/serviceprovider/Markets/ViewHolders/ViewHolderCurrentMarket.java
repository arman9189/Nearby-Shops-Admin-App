package org.nearbyshops.serviceprovider.Markets.ViewHolders;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


import org.nearbyshops.serviceprovider.Login.Interfaces.NotifyAboutLogin;
import org.nearbyshops.serviceprovider.Markets.Model.ServiceConfigurationLocal;
import org.nearbyshops.serviceprovider.Preferences.PrefGeneral;
import org.nearbyshops.serviceprovider.Preferences.PrefLogin;
import org.nearbyshops.serviceprovider.Preferences.PrefLoginGlobal;
import org.nearbyshops.serviceprovider.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewHolderCurrentMarket extends RecyclerView.ViewHolder {


    @BindView(R.id.market_name) TextView marketName;
    @BindView(R.id.address) TextView marketAddress;
//    @BindView(R.id.distance) TextView distance;
    @BindView(R.id.description) TextView marketDescription;
    @BindView(R.id.market_image) ImageView marketPhoto;
    @BindView(R.id.log_out_button) TextView logOutButton;


    private ServiceConfigurationLocal configurationLocal;
    private Context context;




    public static ViewHolderCurrentMarket create(ViewGroup parent, Context context)
    {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_market_current,parent,false);

        return new ViewHolderCurrentMarket(view,context);
    }




    public ViewHolderCurrentMarket(@NonNull View itemView, Context context) {
        super(itemView);
        ButterKnife.bind(this,itemView);

        this.context = context;
    }




    void setItem(ServiceConfigurationLocal item)
    {

        this.configurationLocal = item;

        marketName.setText(configurationLocal.getServiceName());
        marketAddress.setText(configurationLocal.getAddress() + ", " + configurationLocal.getCity());
//        distance.setText("Distance : " + String.format("%.2f",configurationLocal.getRt_distance()));
        marketDescription.setText(configurationLocal.getDescriptionShort());



        String imagePath = PrefGeneral.getServiceURL(context)
                + "/api/serviceconfiguration/Image/three_hundred_" + configurationLocal.getLogoImagePath() + ".jpg";


//                System.out.println("Service LOGO : " + imagePath);

        Drawable placeholder = VectorDrawableCompat
                .create(context.getResources(),
                        R.drawable.ic_nature_people_white_48px, context.getTheme());


        Picasso.get()
                .load(imagePath)
                .placeholder(placeholder)
                .into(marketPhoto);



        if(PrefLoginGlobal.getUser(context)==null)
        {
            // logged out
            logOutButton.setVisibility(View.INVISIBLE);
        }
        else
        {
            logOutButton.setVisibility(View.VISIBLE);
        }



    }



    @OnClick(R.id.log_out_button)
    void logOutClick()
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);

        dialog.setTitle("Confirm Logout !")
                .setMessage("Do you want to log out !")
                .setPositiveButton("Yes",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        logout();

                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        showToastMessage("Cancelled !");
                    }
                })
                .show();

    }





    void logout()
    {
        // log out
        PrefLogin.saveUserProfile(null,context);
        PrefLogin.saveCredentials(context,null,null);

        PrefLoginGlobal.saveUserProfile(null,context);
        PrefLoginGlobal.saveCredentials(context,null,null);


        if(context instanceof NotifyAboutLogin)
        {
            ((NotifyAboutLogin) context).loggedOut();
        }

    }






    void showToastMessage(String message)
    {
        Toast.makeText(context,message, Toast.LENGTH_SHORT).show();
    }


}
