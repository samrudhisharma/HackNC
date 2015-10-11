package in.sandz.careerfairnc;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

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
 * Created by Applications MS on 10-10-2015.
 */
public class CompanyList  extends Activity{
    ArrayList<String> tryF = new ArrayList<String>();
    Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Retrofit restAdapter = new Retrofit.Builder().baseUrl(RestAdapter.API).
                addConverterFactory(GsonConverterFactory.create()).build();

        CareerFairCompany cfu1  = restAdapter.create(CareerFairCompany.class);
        APIUtils apiUtils = new APIUtils(this);
        String authorizationToken = apiUtils.getHeaderAuthorizationToken();
        Call<List<Company>> repos1 = cfu1.getCompanyDetails(authorizationToken);

        repos1.enqueue(new Callback<List<Company>>() {

            int length = 0;
            ArrayList<String> companyNames = new ArrayList<String>();


            public void onResponse(Response<List<Company>> response, Retrofit retrofit) {
                length = (response.body()).size();
                //TableLayout table1 = (TableLayout)CompanyList.this.findViewById(R.id.tableLayout1);

                for (Company o : response.body()) {
                    //Log.w("Company response", o.getName());
                    companyNames.add(o.getCompanyName());
                    tryF.add(o.getCompanyName());
                }

                LinearLayout lView = new LinearLayout(CompanyList.this);
                TextView myText[]= new TextView[length];
               final Button myButton[] = new Button[length];

                for (int i=0; i<length;i++){
                    myText[i] = new TextView(CompanyList.this);
                    myText[i].append(companyNames.get(i)+"\n\n\n\n\n");
                    //myText[i].append();
                    lView.addView(myText[i]);
                    myButton[i] = new Button(CompanyList.this);
                    myButton[i].setText("Apply");
                    lView.addView(myButton[i]);

                }

                setContentView(lView);
                int i=0;
                for(i=0;i<myButton.length;i++)
                {
                    final int k= i;
                    myButton[k].setOnClickListener(new View.OnClickListener(){

                                                      public void onClick(View v)
                                                      {
                                                          Log.w("sss","ssss");
                                                      }



                                                   });
                }

             /* TableLayout table = (TableLayout)CompanyList.this.findViewById(R.id.attrib_table);
                for(int i=0;i<1;i++)
                {
                    // Inflate your row "template" and fill out the fields.
                    TableRow row = (TableRow)LayoutInflater.from(CompanyList.this).inflate(R.layout.activity_table, null);
                    ((TextView)row.findViewById(R.id.attrib_name)).setText("SSSS");
                    ((TextView)row.findViewById(R.id.attrib_value)).setText("SSSSdjksc");
                    table.addView(row);
                }
                table.requestLayout();*/

            }


            @Override
            public void onFailure(Throwable t) {

            }
        });


    }


}


