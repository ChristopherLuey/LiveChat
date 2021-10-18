package livechat.christopher.martin.signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.CheckBox;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import livechat.christopher.martin.R;
import livechat.christopher.martin.signin.SignIn;

public class SignUp extends AppCompatActivity {

    FirebaseAuth fbAuth;
    public EditText editText_email, editText_password, editText_password_retype;
    CheckBox checkBox_signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_sign_up);

        fbAuth = FirebaseAuth.getInstance();

        editText_email = findViewById(R.id.signup_email);
        editText_password = findViewById(R.id.signup_password);
        editText_password_retype = findViewById(R.id.signup_password_retype);
        checkBox_signup = findViewById(R.id.signup_checkbox);
    }

    public void onClick_signup(View view) {
        String email = editText_email.getText().toString();
        String password = editText_password.getText().toString();
        String password_retype = editText_password_retype.getText().toString();

        if (password_retype.isEmpty() || password.isEmpty() || email.isEmpty()) {

            if (password_retype.isEmpty()) {
                editText_password_retype.setError("Retype your Password");
                editText_password_retype.requestFocus();
            }
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
            if (password_retype.equals(password)) {
                if (checkBox_signup.isChecked()) {
                    fbAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUp.this, new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(SignUp.this.getApplicationContext(),
                                        task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                // TODO: New Activty
                                startActivity(new Intent(SignUp.this, SignIn.class));
                            }
                        }
                    });
                } else {
                    Toast.makeText(SignUp.this, "Please Accept the Terms of Service", Toast.LENGTH_SHORT).show();
                }
            } else {
                editText_password_retype.setError("Passwords Don't Match");
                editText_password_retype.requestFocus();
            }
        }
    }

    public void onClick_switchSignin(View view) {
        startActivity(new Intent(this, SignIn.class));
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