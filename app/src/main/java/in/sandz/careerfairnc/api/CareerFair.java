package in.sandz.careerfairnc.api;

import java.util.List;

import in.sandz.careerfairnc.models.Company;
import in.sandz.careerfairnc.models.User;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by sandz on 10/10/15.
 */
public interface CareerFair {

    @GET("/api/applied/")
    public Call<List<Company>> companiesApplied(@Header("Authorization") String authorization);
}

