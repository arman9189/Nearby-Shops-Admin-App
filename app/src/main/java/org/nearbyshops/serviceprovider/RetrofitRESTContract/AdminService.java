package org.nearbyshops.serviceprovider.RetrofitRESTContract;

import org.nearbyshops.serviceprovider.Model.Image;
import org.nearbyshops.serviceprovider.ModelRoles.OldFiles.Admin;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by sumeet on 12/3/16.
 */
public interface AdminService {

//    @GET("/api/v1/Admin/Login")
//    Observable<Admin> getAdmin(@Header("Authorization")String headers);


    @GET("/api/v1/Admin/Login")
    Call<Admin> getAdminSimple(@Header("Authorization") String headers);



    @PUT("/api/v1/Admin")
    Call<ResponseBody> putAdmin(@Header("Authorization") String headers,
                                @Body Admin admin);


    @GET("/api/v1/Staff/CheckUsernameExists/{username}")
    Call<ResponseBody> checkUsernameExist(@Path("username") String username);



    // Image Calls

    @POST("/api/v1/Admin/Image")
    Call<Image> uploadImage(@Header("Authorization") String headers,
                            @Body RequestBody image);

    @DELETE("/api/v1/Admin/Image/{name}")
    Call<ResponseBody> deleteImage(@Header("Authorization") String headers,
                                   @Path("name") String fileName);

}
