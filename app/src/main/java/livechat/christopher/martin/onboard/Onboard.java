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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkFirstRun();
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_onboard);

        layouts = new int[]{R.layout.onboard_fragment, R.layout.onboard_fragment, R.layout.onboard_fragment};

        WormDotsIndicator wdi = findViewById(R.id.onboard_worm_dots);
        ViewPager2 viewpager2 = findViewById(R.id.onboard_viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter();
        viewpager2.setAdapter(adapter);
        wdi.setViewPager2(viewpager2);


    }

    public void signup(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    public class ViewPagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private static final String TAG = "VALUE";

        public ViewPagerAdapter() {
        }

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

            // This is just a normal run
            startActivity(new Intent(this, SignIn.class));
            return;

        } else if (savedVersionCode == DOESNT_EXIST) {

            // TODO This is a new install (or the user cleared the shared preferences)

        } else if (currentVersionCode > savedVersionCode) {
            startActivity(new Intent(this, SignIn.class));
            // TODO This is an upgrade
        }

        // Update the shared preferences with the current version code
        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();
    }
}
