package in.sandz.careerfairnc;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import java.io.IOException;
import java.lang.*;
import in.sandz.careerfairnc.api.CareerFairUser;
import in.sandz.careerfairnc.api.RestAdapter;
import in.sandz.careerfairnc.models.User;
import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.*;
import android.widget.*;

/**
 * A placeholder fragment containing a simple view.
 */
public class DashboardActivityFragment extends Fragment {

    public DashboardActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }
}
