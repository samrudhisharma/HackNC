package in.sandz.careerfairnc;

import android.app.Activity;
import android.content.Intent;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import in.sandz.careerfairnc.api.CareerFairCompany;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

import in.sandz.careerfairnc.api.CareerFair;
import in.sandz.careerfairnc.api.CareerFairUser;
import in.sandz.careerfairnc.api.RestAdapter;
import in.sandz.careerfairnc.models.Company;
import in.sandz.careerfairnc.models.User;
import in.sandz.careerfairnc.utils.APIUtils;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import android.view.*;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class DashboardActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Retrofit restAdapter = new Retrofit.Builder().baseUrl(RestAdapter.API).
                addConverterFactory(GsonConverterFactory.create()).build();

        CareerFairUser cfu = restAdapter.create(CareerFairUser.class);

        APIUtils apiUtils = new APIUtils(this);
        String authorizationToken = apiUtils.getHeaderAuthorizationToken();
        Call<User> repos = cfu.getUserDetails(authorizationToken);


        repos.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Response<User> response, Retrofit retrofit) {
                User user = response.body();
                Log.w("User response", user.getEmail());
            }


            @Override
            public void onFailure(Throwable t) {

            }
        });


        Button myImageButton = (Button) findViewById(R.id.Button01);
        setContentView(R.layout.activity_dashboard);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically 0handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            new Thread(new Runnable() {

                public void run() {
                    logout();
                }
            }).start();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onMyButtonClick(View view) {
        List<TextView> textList = new ArrayList<TextView>(3);
        Intent intent = new Intent(view.getContext(), CompanyList.class);
        startActivity(intent);
    }

    public void viewAppliedCompanies(View view) {
        Intent intent = new Intent(view.getContext(), AppliedCompany.class);
        startActivity(intent);
    }

    private void logout() {
        try {
            HttpPost hp = new HttpPost("http://204.84.8.248:8000/auth/logout/");
            APIUtils apiUtils = new APIUtils(this);
            String authorizationToken = apiUtils.getHeaderAuthorizationToken();

            hp.setHeader("Authentication", authorizationToken);
            HttpClient ahc = new DefaultHttpClient();

            ahc.execute(hp);
            SharedPreferences settings = PreferenceManager
                    .getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = settings.edit();
            editor.remove("auth_token");
            editor.commit();

            Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
            startActivity(intent);
        } catch (IOException e) {

        }

    }
}

