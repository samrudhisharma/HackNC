package in.sandz.careerfairnc.api;

import java.util.List;

import in.sandz.careerfairnc.models.Company;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;

/**
 * Created by Applications MS on 10-10-2015.
 */
public interface CareerFairCompany {

    @GET("/api/company/")
    public Call<List<Company>> getCompanyDetails(@Header("Authorization") String authorization);
}
