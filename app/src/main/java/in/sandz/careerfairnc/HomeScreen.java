package in.sandz.careerfairnc;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.sandz.careerfairnc.api.CareerFair;
import in.sandz.careerfairnc.api.RestAdapter;
import in.sandz.careerfairnc.utils.APIUtils;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class HomeScreen extends Activity {
    EditText un, pw;
    TextView error;
    Button ok,register;
    private String resp;
    private String errorMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());

        String auth_token = settings.getString("auth_token", null);
        if (auth_token != null) {
            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
            startActivity(intent);
            return;
        }
        setContentView(R.layout.activity_home_screen);
        un = (EditText) findViewById(R.id.et_un);
        pw = (EditText) findViewById(R.id.et_pw);
        ok = (Button) findViewById(R.id.btn_login);
        register = (Button) findViewById(R.id.btn_reg);
        error = (TextView) findViewById(R.id.tv_error);

        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /** According with the new StrictGuard policy,  running long tasks on the Main UI thread is not possible
                 So creating new thread to create and execute http operations */
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
                        postParameters.add(new BasicNameValuePair("email", un.getText().toString()));
                        postParameters.add(new BasicNameValuePair("password", pw.getText().toString()));

                        String response = null;
                        try {
                            HttpPost hp = new HttpPost("http://204.84.8.248:8000/auth/login/");
                            hp.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));
                            HttpClient ahc = new DefaultHttpClient();

                            HttpResponse response1 = ahc.execute(hp);

                            String res = EntityUtils.toString(response1.getEntity());
                            JSONObject jsonObject = new JSONObject(res);
                            if (!jsonObject.has("auth_token")) {
                                Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
                                startActivity(intent);
                                return;
                            }
                            String token = (String) jsonObject.get("auth_token");
                            Log.w("Auth oken", "AUth token : " + token);
                            if (token == null) {
                                Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
                                startActivity(intent);
                                return;
                            }
                            SharedPreferences settings = PreferenceManager
                                    .getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("auth_token", token);
                            editor.commit();

                            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                            startActivity(intent);

                            resp = res.replaceAll("\\s+", "");

                        } catch (Exception e) {
                            e.printStackTrace();
                            errorMsg = e.getMessage();
                        }
                    }

                }).start();
                try {
                    /** wait a second to get response from server */
                    Thread.sleep(1000);
                    /** Inside the new thread we cannot update the main thread
                     So updating the main thread outside the new thread */

                    error.setText(resp);

                    if (null != errorMsg && !errorMsg.isEmpty()) {
                        error.setText(errorMsg);
                    }
                } catch (Exception e) {
                    error.setText(e.getMessage());
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onRegButtonClick (View view)
    {
        Intent intent = new Intent(view.getContext(), UserRegister.class);
        startActivity(intent);
    }
}
