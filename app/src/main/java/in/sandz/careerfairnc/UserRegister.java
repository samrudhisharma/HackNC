package in.sandz.careerfairnc;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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
import android.os.*;
import android.os.Bundle;
import java.util.ArrayList;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.*;
import android.net.sip.SipAudioCall;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.sandz.careerfairnc.api.CareerFairCompany;
import in.sandz.careerfairnc.api.RestAdapter;
import in.sandz.careerfairnc.models.Company;
import in.sandz.careerfairnc.utils.APIUtils;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Applications MS on 11-10-2015.
 */
public class UserRegister extends Activity {

    EditText un, pw, pwc, email;
    TextView error, un_tv, pw_tv, pwc_tv, email_tv;
    Button submit;
    private String resp;
    private String errorMsg;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_register);

        final EditText un = (EditText) findViewById(R.id.et_un);
        final EditText pw = (EditText) findViewById(R.id.et_pw);
        final EditText pwc = (EditText) findViewById(R.id.et_pwc);
        final EditText email = (EditText) findViewById(R.id.et_email);

        TextView un_tv = (TextView) findViewById(R.id.tv_un);
        TextView pw_tv = (TextView) findViewById(R.id.tv_pw);
        TextView pwc_tv = (TextView) findViewById(R.id.tv_pwc);
        TextView email_tv = (TextView) findViewById(R.id.tv_email);

        final Button button = (Button) findViewById(R.id.user_reg);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (pw.getText().toString().trim().matches(pwc.getText().toString().trim())) {
                    // then do your work

                    //Pass email and password via /auth/register

                    button.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            /** According with the new StrictGuard policy,  running long tasks on the Main UI thread is not possible
                             So creating new thread to create and execute http operations */
                            new Thread(new Runnable() {

                                @Override
                                public void run() {
                                    ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
                                    postParameters.add(new BasicNameValuePair("email", email.getText().toString()));
                                    postParameters.add(new BasicNameValuePair("password", pw.getText().toString()));

                                    String response = null;
                                    try {
                                        HttpPost hp = new HttpPost("http://204.84.8.248:8000/auth/register/");
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

                } else {
                    //otherwise show error message.
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            UserRegister.this);

                    // set title
                    alertDialogBuilder.setTitle("Passwords entered don't match");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Click Okay to exit!")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // if this button is clicked, close
                                    // current activity
                                    // MainActivity.this.finish();
                                    dialog.cancel();
                                }
                            });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                }
            }
        });


    }
    public void onCheckPasswords (View view)
    {
       // Intent intent = new Intent(view.getContext(), UserRegister.class);
       // startActivity(intent);
    }
}