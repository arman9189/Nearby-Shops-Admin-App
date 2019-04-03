package org.nearbyshops.serviceprovider.EditProfileAdmin;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import org.nearbyshops.serviceprovider.DaggerComponentBuilder;
import org.nearbyshops.serviceprovider.Model.Image;
import org.nearbyshops.serviceprovider.ModelRoles.OldFiles.Admin;
import org.nearbyshops.serviceprovider.R;
import org.nearbyshops.serviceprovider.RetrofitRESTContract.AdminService;
import org.nearbyshops.serviceprovider.Preferences.PrefGeneral;
import org.nearbyshops.serviceprovider.Preferences.PrefLogin;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.inject.Inject;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//import rx.Subscription;
//import rx.android.schedulers.AndroidSchedulers;
//import rx.functions.Action1;

import static android.app.Activity.RESULT_OK;


public class EditAdminFragment extends Fragment {

    public static int PICK_IMAGE_REQUEST = 21;
    // Upload the image after picked up
    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 56;


//    Validator validator;


//    @Inject
//    DeliveryGuySelfService deliveryService;

    @Inject
    AdminService adminService;


    // flag for knowing whether the image is changed or not
    boolean isImageChanged = false;
    boolean isImageRemoved = false;


    // bind views
    @BindView(R.id.uploadImage)
    ImageView resultView;


    @BindView(R.id.item_id) EditText item_id;
    @BindView(R.id.name) EditText name;
    @BindView(R.id.username) EditText username;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.about) EditText about;

    @BindView(R.id.phone_number) EditText phone;
    @BindView(R.id.designation) EditText designation;
//    @BindView(R.id.switch_enable) Switch switchEnable;

    @BindView(R.id.make_account_private) CheckBox makeAccountPrivate;

//    @BindView(R.id.govt_id_name) EditText govtIDName;
//    @BindView(R.id.govt_id_number) EditText govtIDNumber;

//    @BindView(R.id.permit_create_update_item_cat) CheckBox createUpdateItemCat;
//    @BindView(R.id.permit_create_update_items) CheckBox createUpdateItems;
//    @BindView(R.id.approve_shop_admin_accounts) CheckBox approveShopAdminAccounts;
//    @BindView(R.id.approve_shops) CheckBox approveShops;
//    @BindView(R.id.approve_end_user_accounts) CheckBox approveEndUserAccounts;


    @BindView(R.id.saveButton) Button buttonUpdateItem;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    boolean isDestroyed = false;


    public static final String STAFF_INTENT_KEY = "staff_intent_key";
    public static final String EDIT_MODE_INTENT_KEY = "edit_mode";

    public static final int MODE_UPDATE = 52;
    public static final int MODE_ADD = 51;

    int current_mode = MODE_ADD;

//    DeliveryGuySelf deliveryGuySelf = new DeliveryGuySelf();
    Admin admin = null;


    public EditAdminFragment() {

        DaggerComponentBuilder.getInstance()
                .getNetComponent().Inject(this);
    }


//    Subscription editTextSub;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        setRetainInstance(true);
        View rootView = inflater.inflate(R.layout.content_edit_admin, container, false);

        ButterKnife.bind(this,rootView);

        if(savedInstanceState==null)
        {
//            shopAdmin = getActivity().getIntent().getParcelableExtra(SHOP_ADMIN_INTENT_KEY);

            current_mode = getActivity().getIntent().getIntExtra(EDIT_MODE_INTENT_KEY,MODE_UPDATE);

            if(current_mode == MODE_UPDATE)
            {
//                admin = UtilityLogin.getAdmin(getContext());

//                System.out.println("Current Mode : " + current_mode + " Admin ID" + admin.getAdminID());
            }


            if(admin !=null) {

                bindDataToViews();
            }


            showLogMessage("Inside OnCreateView - Saved Instance State !");
        }



//        if(validator==null)
//        {
//            validator = new Validator(this);
//            validator.setValidationListener(this);
//        }

        updateIDFieldVisibility();


        if(admin !=null) {
            loadImage(admin.getProfileImageURL());
            showLogMessage("Inside OnCreateView : DeliveryGUySelf : Not Null !");
        }


        showLogMessage("Inside On Create View !");


//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .unsubscribeOn(AndroidSchedulers.mainThread())

//        EditText user = (EditText) rootView.findViewById(R.id.username);



        return rootView;
    }

    void updateIDFieldVisibility()
    {

        if(current_mode==MODE_ADD)
        {
            buttonUpdateItem.setText("Create Account");
            item_id.setVisibility(View.GONE);
        }
        else if(current_mode== MODE_UPDATE)
        {
            item_id.setVisibility(View.VISIBLE);
            buttonUpdateItem.setText("Save");
        }
    }


    public static final String TAG_LOG = "TAG_LOG";

    void showLogMessage(String message)
    {
        Log.i(TAG_LOG,message);
        System.out.println(message);
    }



    void loadImage(String imagePath) {

        String iamgepath = PrefGeneral.getServiceURL(getContext()) + "/api/v1/Admin/Image/" + imagePath;

        Picasso.with(getContext())
                .load(iamgepath)
                .into(resultView);
    }





    @Override
    public void onResume() {
        super.onResume();
        isDestroyed = false;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isDestroyed = true;
    }






    @OnClick(R.id.saveButton)
    public void UpdateButtonClick()
    {

        if(!validateData())
        {
//            showToastMessage("Please correct form data before save !");
            return;
        }

        if(current_mode == MODE_ADD)
        {
            admin = new Admin();
            addAccount();
        }
        else if(current_mode == MODE_UPDATE)
        {
            update();
        }
    }


    boolean validateData()
    {
        boolean isValid = true;


        if(phone.getText().toString().length()==0)
        {
            phone.setError("Please enter Phone Number");
            phone.requestFocus();
            isValid= false;
        }


        if(password.getText().toString().length()==0)
        {
            password.requestFocus();
            password.setError("Password cannot be empty");
            isValid = false;
        }


        if(username.getText().toString().length()==0)
        {
            username.requestFocus();
            username.setError("Username cannot be empty");
            isValid= false;
        }


        if(name.getText().toString().length()==0)
        {

//            Drawable drawable = ContextCompat.getDrawable(getContext(),R.drawable.ic_close_black_24dp);
            name.requestFocus();
            name.setError("Name cannot be empty");
            isValid = false;
        }


        return isValid;
    }



//    @OnTextChanged(R.id.username)
    void usernameCheck()
    {


        if(admin !=null && admin.getUsername()!=null
                &&
                username.getText().toString().equals(admin.getUsername()))
        {
            username.setTextColor(ContextCompat.getColor(getContext(),R.color.gplus_color_1));
            return;
        }


        Call<ResponseBody> call = adminService.checkUsernameExist(username.getText().toString());


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.code()==200)
                {
                    //username already exists
                    username.setTextColor(ContextCompat.getColor(getContext(),R.color.gplus_color_4));
                    username.setError("Username already exist !");
                }
                else if(response.code() == 204)
                {
                    username.setTextColor(ContextCompat.getColor(getContext(),R.color.gplus_color_1));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }




    void addAccount()
    {
        if(isImageChanged)
        {
            if(!isImageRemoved)
            {
                // upload image with add
                uploadPickedImage(false);
            }


            // reset the flags
            isImageChanged = false;
            isImageRemoved = false;

        }
        else
        {
            // post request
//            retrofitPOSTRequest();
        }

    }


    void update()
    {

        if(isImageChanged)
        {


            // delete previous Image from the Server
            deleteImage(admin.getProfileImageURL());

            /*ImageCalls.getInstance()
                    .deleteImage(
                            itemForEdit.getItemImageURL(),
                            new DeleteImageCallback()
                    );*/


            if(isImageRemoved)
            {

                admin.setProfileImageURL(null);
                retrofitPUTRequest();

            }else
            {

                uploadPickedImage(true);
            }


            // resetting the flag in order to ensure that future updates do not upload the same image again to the server
            isImageChanged = false;
            isImageRemoved = false;

        }else {

            retrofitPUTRequest();
        }
    }



    void bindDataToViews()
    {
        if(admin !=null) {

            item_id.setText(String.valueOf(admin.getAdminID()));
            name.setText(admin.getAdministratorName());
            username.setText(admin.getUsername());
            password.setText(admin.getPassword());
            about.setText(admin.getAbout());
            designation.setText(admin.getDesignation());
            phone.setText(admin.getPhone());


//            switchEnable.setChecked(admin.getEnabled());

            makeAccountPrivate.setChecked(admin.isAccountPrivate());

//            govtIDName.setText(admin.getGovtIDName());
//            govtIDNumber.setText(admin.getGovtIDNumber());

//            createUpdateItemCat.setChecked(admin.isCreateUpdateItemCategory());
//            createUpdateItems.setChecked(admin.isCreateUpdateItems());

//            approveShopAdminAccounts.setChecked(admin.isApproveShopAdminAccounts());
//            approveShops.setChecked(admin.isApproveShops());
//            approveEndUserAccounts.setChecked(admin.isApproveEndUserAccounts());

        }
    }


    void getDataFromViews()
    {
        if(admin ==null)
        {
            if(current_mode == MODE_ADD)
            {
                admin = new Admin();
            }
            else
            {
                return;
            }
        }

//        if(current_mode == MODE_ADD)
//        {
//            deliveryGuySelf.setShopID(UtilityShopHome.getShop(getContext()).getShopID());
//        }

        admin.setAdministratorName(name.getText().toString());
        admin.setUsername(username.getText().toString());
        admin.setPassword(password.getText().toString());
        admin.setAbout(about.getText().toString());
        admin.setDesignation(designation.getText().toString());
        admin.setPhone(phone.getText().toString());


//        admin.setEnabled(switchEnable.isChecked());

        admin.setAccountPrivate(makeAccountPrivate.isChecked());

//        admin.setGovtIDName(govtIDName.getText().toString());
//        admin.setGovtIDNumber(govtIDNumber.getText().toString());

//        admin.setCreateUpdateItemCategory(createUpdateItemCat.isChecked());
//        admin.setCreateUpdateItems(createUpdateItems.isChecked());

//        admin.setApproveShopAdminAccounts(approveShopAdminAccounts.isChecked());
//        admin.setApproveShops(approveShops.isChecked());
//        admin.setApproveEndUserAccounts(approveEndUserAccounts.isChecked());
    }



    public void retrofitPUTRequest()
    {

        getDataFromViews();


        buttonUpdateItem.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

//        final Staff admin = UtilityStaff.getStaff(getContext());
        Call<ResponseBody> call = adminService.putAdmin(PrefLogin.getAuthorizationHeaders(
                                                        getContext()), admin);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                if(isDestroyed)
                {
                    return;
                }



                if(response.code()==200)
                {
                    showToastMessage("Update Successful !");
                    PrefLogin.saveAdmin(admin,getContext());
                    PrefLogin.saveCredentials(getActivity(),admin.getUsername(),admin.getPassword());
                }
                else
                {
                    showToastMessage("Update Failed Code : " + String.valueOf(response.code()));
                }



                buttonUpdateItem.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                if(isDestroyed)
                {
                    return;
                }

                showToastMessage("Update Failed !");


                buttonUpdateItem.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }


//    void retrofitPOSTRequest()
//    {
//        getDataFromViews();
//
////        final Staff staffTemp = UtilityStaff.getStaff(getContext());
//        Call<Staff> call = adminService.putAdmin(UtilityLogin.getAuthorizationHeaders(getContext()), admin);
//
//        call.enqueue(new Callback<Staff>() {
//            @Override
//            public void onResponse(Call<Staff> call, Response<Staff> response) {
//
//                if(response.code()==201)
//                {
//                    showToastMessage("Add successful !");
//
//                    current_mode = MODE_UPDATE;
//                    updateIDFieldVisibility();
//                    admin = response.body();
//                    bindDataToViews();
//
//                    UtilityStaff.saveStaff(admin,getContext());
//
//                }
//                else
//                {
//                    showToastMessage("Add failed !");
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<Staff> call, Throwable t) {
//
//                showToastMessage("Add failed !");
//
//            }
//        });
//    }






    /*
        Utility Methods
     */




    void showToastMessage(String message)
    {
        Toast.makeText(getContext(),message, Toast.LENGTH_SHORT).show();
    }




    @BindView(R.id.textChangePicture)
    TextView changePicture;


    @OnClick(R.id.removePicture)
    void removeImage()
    {

        File file = new File(getContext().getCacheDir().getPath() + "/" + "SampleCropImage.jpeg");
        file.delete();

        resultView.setImageDrawable(null);

        isImageChanged = true;
        isImageRemoved = true;
    }



    public static void clearCache(Context context)
    {
        File file = new File(context.getCacheDir().getPath() + "/" + "SampleCropImage.jpeg");
        file.delete();
    }



    @OnClick(R.id.textChangePicture)
    void pickShopImage() {

//        ImageCropUtility.showFileChooser(()getActivity());



        // code for checking the Read External Storage Permission and granting it.
        if (PermissionChecker.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {


            /// / TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_READ_EXTERNAL_STORAGE);

            return;
        }



        clearCache(getContext());

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent result) {

        super.onActivityResult(requestCode, resultCode, result);



        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && result != null
                && result.getData() != null) {


            Uri filePath = result.getData();

            //imageUri = filePath;

            if (filePath != null) {

                startCropActivity(result.getData(),getContext());
            }

        }


        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {

            resultView.setImageURI(null);
            resultView.setImageURI(UCrop.getOutput(result));

            isImageChanged = true;
            isImageRemoved = false;


        } else if (resultCode == UCrop.RESULT_ERROR) {

            final Throwable cropError = UCrop.getError(result);

        }
    }



    // upload image after being picked up
    void startCropActivity(Uri sourceUri, Context context) {



        final String SAMPLE_CROPPED_IMAGE_NAME = "SampleCropImage.jpeg";

        Uri destinationUri = Uri.fromFile(new File(getContext().getCacheDir(), SAMPLE_CROPPED_IMAGE_NAME));

        UCrop.Options options = new UCrop.Options();
        options.setFreeStyleCropEnabled(true);

//        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
//        options.setCompressionQuality(100);

        options.setToolbarColor(ContextCompat.getColor(getContext(),R.color.blueGrey800));
        options.setStatusBarColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));
        options.setAllowedGestures(UCropActivity.ALL, UCropActivity.ALL, UCropActivity.ALL);


        // this function takes the file from the source URI and saves in into the destination URI location.
        UCrop.of(sourceUri, destinationUri)
                .withOptions(options)
                .start(context,this);

        //.withMaxResultSize(400,300)
        //.withMaxResultSize(500, 400)
        //.withAspectRatio(16, 9)
    }





    /*

    // Code for Uploading Image

     */

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_CODE_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

//                    showToastMessage("Permission Granted !");
                    pickShopImage();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {


                    showToastMessage("Permission Denied for Reading External Storage ! ");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }

    }





    public void uploadPickedImage(final boolean isModeEdit)
    {

        Log.d("applog", "onClickUploadImage");


        // code for checking the Read External Storage Permission and granting it.
        if (PermissionChecker.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {


            /// / TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_READ_EXTERNAL_STORAGE);

            return;
        }


        File file = new File(getContext().getCacheDir().getPath() + "/" + "SampleCropImage.jpeg");


        // Marker

        RequestBody requestBodyBinary = null;

        InputStream in = null;

        try {
            in = new FileInputStream(file);

            byte[] buf;
            buf = new byte[in.available()];
            while (in.read(buf) != -1) ;

            requestBodyBinary = RequestBody.create(MediaType.parse("application/octet-stream"), buf);

        } catch (Exception e) {
            e.printStackTrace();
        }




        showToastMessage("Uploading Image ...");
        buttonUpdateItem.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);



        Call<Image> imageCall = adminService.uploadImage(PrefLogin.getAuthorizationHeaders(getContext()),
                requestBodyBinary);


        imageCall.enqueue(new Callback<Image>() {
            @Override
            public void onResponse(Call<Image> call, Response<Image> response) {


                if(isDestroyed)
                {
                    return;
                }



                if(response.code()==201)
                {
//                    showToastMessage("Image UPload Success !");

                    Image image = response.body();
                    // check if needed or not . If not needed then remove this line
//                    loadImage(image.getPath());


                    admin.setProfileImageURL(image.getPath());

                }
                else if(response.code()==417)
                {
                    showToastMessage("Cant Upload Image. Image Size should not exceed 2 MB.");

                    admin.setProfileImageURL(null);

                }
                else
                {
                    showToastMessage("Image Upload failed !");
                    admin.setProfileImageURL(null);

                }

                if(isModeEdit)
                {
                    retrofitPUTRequest();
                }
                else
                {
//                    retrofitPOSTRequest();
                }




                buttonUpdateItem.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);

                showToastMessage("Image Uploaded !");
            }

            @Override
            public void onFailure(Call<Image> call, Throwable t) {


                if(isDestroyed)
                {
                    return;
                }



                showToastMessage("Image Upload failed !");
                admin.setProfileImageURL(null);

                if(isModeEdit)
                {
                    retrofitPUTRequest();
                }
                else
                {
//                    retrofitPOSTRequest();
                }



                buttonUpdateItem.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }



    void deleteImage(String filename)
    {
        Call<ResponseBody> call = adminService.deleteImage(PrefLogin.getAuthorizationHeaders(getContext()),filename);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if(isDestroyed)
                    {
                        return;
                    }


                    if(response.code()==200)
                    {
                        showToastMessage("Image Removed !");
                    }
                    else
                    {
//                        showToastMessage("Image Delete failed");
                    }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                if(isDestroyed)
                {
                    return;
                }

//                showToastMessage("Image Delete failed");
            }
        });
    }


}
