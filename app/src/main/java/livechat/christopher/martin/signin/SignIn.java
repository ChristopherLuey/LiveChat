package livechat.christopher.martin.signin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

import livechat.christopher.martin.R;
import livechat.christopher.martin.signup.SignUp;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;




public class SignIn extends AppCompatActivity {

    FirebaseAuth fbAuth;
    private FirebaseAuth.AuthStateListener fbAuthListener;

    EditText editText_email;
    EditText editText_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_in);

        fbAuth = FirebaseAuth.getInstance();

        editText_email = findViewById(R.id.resetPassword_email);
        editText_password = findViewById(R.id.signin_password);
    }

    public void onClick_forgotPassword(View view) {
        startActivity(new Intent(this, ResetPassword.class));

    }

    public void onClick_signin(View view) {
        String email = editText_email.getText().toString();
        String password = editText_password.getText().toString();

        if (password.isEmpty() || email.isEmpty()) {

            if (password.isEmpty()) {
                editText_password.setError("Set your Password");
                editText_password.requestFocus();
            }

            if (email.isEmpty()) {
                editText_email.setError("Provide your Email");
                editText_email.requestFocus();
            }
        }
        else {
            fbAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(SignIn.this, new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(SignIn.this.getApplicationContext(),
                                task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        // TODO New Activty
                        startActivity(new Intent(SignIn.this, SignIn.class));
                    }
                }
            });
        }
    }

    public void onClick_newAccount(View view) {
        startActivity(new Intent(this, SignUp.class));
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
}