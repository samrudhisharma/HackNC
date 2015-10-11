package in.sandz.careerfairnc;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import in.sandz.careerfairnc.api.CareerFair;
import in.sandz.careerfairnc.api.RestAdapter;
import in.sandz.careerfairnc.models.Company;
import in.sandz.careerfairnc.utils.APIUtils;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class AppliedCompany extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applied_company);

        ArrayList<Company> list = new ArrayList<Company>();
        final ListView listview = (ListView) findViewById(R.id.listview);

        final ArrayAdapter<Company> adapter = new ArrayAdapter<Company>(this,
                R.layout.activity_applied_company, R.id.textViewS, list);

        listview.setAdapter(adapter);

        APIUtils apiUtils = new APIUtils(this);
        String authToken = apiUtils.getHeaderAuthorizationToken();
        Retrofit restAdapter = new Retrofit.Builder().baseUrl(RestAdapter.API).
                addConverterFactory(GsonConverterFactory.create()).build();

        CareerFair careerFair = restAdapter.create(CareerFair.class);

        Call<List<Company>> callableCompanies = careerFair.companiesApplied(authToken);

        callableCompanies.enqueue(new Callback<List<Company>>() {
            @Override
            public void onResponse(Response<List<Company>> response, Retrofit retrofit) {
                List<Company> companies = response.body();
                adapter.clear();
                adapter.addAll(companies);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }

    public void setOnItemClickListener() {
        Log.w("cc","cdd");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_applied_company, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
