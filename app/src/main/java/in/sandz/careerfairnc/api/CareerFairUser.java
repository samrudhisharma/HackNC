package in.sandz.careerfairnc.api;


import java.util.List;

import in.sandz.careerfairnc.models.Company;
import in.sandz.careerfairnc.models.User;
import retrofit.Call;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;

/**
 * Created by sandz on 10/10/15.
 */
public interface CareerFairUser {

    @GET("/json_new/")
    public Call<User> getUserDetails(@Header("Authorization") String authorization);
}
