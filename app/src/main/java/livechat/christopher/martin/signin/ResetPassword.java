package livechat.christopher.martin.signin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import livechat.christopher.martin.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class ResetPassword extends AppCompatActivity {

    FirebaseAuth fbAuth;
    EditText editText_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_reset_password);
        editText_email = findViewById(R.id.resetPassword_email);
        fbAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onBackPressed() {}

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = fbAuth.getCurrentUser();
        if (user != null){
            // TODO New Activity
        }
    }

    public void onClick_returnSignIn(View view) {
        startActivity(new Intent(this, SignIn.class));
    }

    public void onClick_resetPassword(View view) {
        String email = editText_email.getText().toString();

        if (!email.isEmpty()) {
            fbAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ResetPassword.this.getApplicationContext(),
                                        "Check your email to reset your password!",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ResetPassword.this.getApplicationContext(),
                                        task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();                               }
                        }
                    });
        } else {
            editText_email.setError("Enter your Email");
            editText_email.requestFocus();
        }

    }
}