package livechat.christopher.martin.onboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import livechat.christopher.martin.BuildConfig;
import livechat.christopher.martin.R;
import livechat.christopher.martin.signin.SignIn;
import livechat.christopher.martin.signup.SignUp;


public class Onboard extends AppCompatActivity {

    private int[] layouts;
    WormDotsIndicator wdi;
    ViewPager2 viewpager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_onboard);

        layouts = new int[]{R.layout.onboard_fragment, R.layout.onboard_fragment, R.layout.onboard_fragment};

        wdi = findViewById(R.id.onboard_worm_dots);
        viewpager2 = findViewById(R.id.onboard_viewpager);
        viewpager2.setAdapter(new ViewPagerAdapter());
        wdi.setViewPager2(viewpager2);
    }

    public void onClick_signup(View view) {
        startActivity(new Intent(this, SignUp.class));
    }

    public class ViewPagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        public ViewPagerAdapter() {}

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
            return new SliderViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            TextView title = holder.itemView.findViewById(R.id.onboard_title);
            TextView desc = holder.itemView.findViewById(R.id.onboard_desc);

            switch (position) {
                case 0:
                    title.setText("Welcome to App Name");
                    desc.setText("Chat with people with similar interests!");
                    break;
                case 1:
                    title.setText("Choose your interests!");
                    desc.setText("IDK");
                    break;
                case 2:
                    title.setText("We can change these");
                    desc.setText("we can also change the colors etc. add images, add more slides this is for u to change");
            }
        }

        @Override
        public int getItemViewType(int position) {
            return layouts[position];
        }

        @Override
        public int getItemCount() {
            return layouts.length;
        }

        public class SliderViewHolder extends RecyclerView.ViewHolder {

            public SliderViewHolder(View view) {
                super(view);
            }
        }
    }

    private void checkFirstRun() {

        final String PREFS_NAME = "MyPrefsFile";
        final String PREF_VERSION_CODE_KEY = "version_code";
        final int DOESNT_EXIST = -1;

        // Get current version code
        int currentVersionCode = BuildConfig.VERSION_CODE;

        // Get saved version code
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);

        // Check for first run or upgrade
        if (currentVersionCode == savedVersionCode) {
            startActivity(new Intent(this, SignIn.class));
            return;

        } else if (savedVersionCode == DOESNT_EXIST) {


        } else if (currentVersionCode > savedVersionCode) {
            startActivity(new Intent(this, SignIn.class));
        }

        // Update the shared preferences with the current version code
        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();
    }

    @Override
    public void onBackPressed() {}

    @Override
    public void onStart() {
        super.onStart();
        //checkFirstRun();
    }
}
