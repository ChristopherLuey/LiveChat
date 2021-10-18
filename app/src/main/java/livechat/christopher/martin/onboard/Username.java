package livechat.christopher.martin.onboard;

import androidx.appcompat.app.AppCompatActivity;
import livechat.christopher.martin.R;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.util.Objects;

public class Username extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_username);
    }
}