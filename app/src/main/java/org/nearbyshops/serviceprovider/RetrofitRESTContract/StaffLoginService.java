package org.nearbyshops.serviceprovider.RetrofitRESTContract;



import org.nearbyshops.serviceprovider.ModelEndPoints.UserEndpoint;
import org.nearbyshops.serviceprovider.ModelRoles.StaffPermissions;
import org.nearbyshops.serviceprovider.ModelRoles.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by sumeet on 30/8/17.
 */


public interface StaffLoginService {



    @PUT("/api/v1/User/StaffLogin/UpdateProfileStaff")
    Call<ResponseBody> updateProfileStaff(
            @Header("Authorization") String headers,
            @Body User user
    );


    @PUT("/api/v1/User/StaffLogin/UpdateStaffLocation")
    Call<ResponseBody> updateStaffLocation(
            @Header("Authorization") String headers,
            @Body StaffPermissions permissions
    );



    @PUT("/api/v1/User/StaffLogin/{UserID}")
    Call<ResponseBody> updateStaffByAdmin(
            @Header("Authorization") String headers,
            @Body User user,
            @Path("UserID") int userID
    );


    @GET("/api/v1/User/StaffLogin/GetStaffForAdmin")
    Call<UserEndpoint> getStaffForAdmin(
            @Header("Authorization") String headers,
            @Query("latCurrent") Double latPickUp, @Query("lonCurrent") Double lonPickUp,
            @Query("PermitProfileUpdate") Boolean permitProfileUpdate,
            @Query("PermitRegistrationAndRenewal") Boolean permitRegistrationAndRenewal,
            @Query("Gender") Boolean gender,
            @Query("SortBy") String sortBy,
            @Query("Limit") Integer limit, @Query("Offset") Integer offset,
            @Query("GetRowCount") boolean getRowCount,
            @Query("MetadataOnly") boolean getOnlyMetaData
    );

}
