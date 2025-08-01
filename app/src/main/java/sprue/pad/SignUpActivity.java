package sprue.pad;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {


    private Button SignUpButton;
    private EditText SignUpEmail, SignUpPassword;
    private TextView loginRedirect;

    private FirebaseAuth mAuth;

    private DatabaseReference database;
    String databaseLink = "https://spruepad-default-rtdb.asia-southeast1.firebasedatabase.app/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        SignUpEmail = findViewById(R.id.signUpEmail);
        SignUpPassword = findViewById(R.id.signUpPassword);
        SignUpButton = findViewById(R.id.signUpButton);
        loginRedirect = findViewById(R.id.loginRedirectText);

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;
                email = SignUpEmail.getText().toString();
                password = SignUpPassword.getText().toString();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(SignUpActivity.this, "no empty fields allowed!",Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(SignUpActivity.this, "Registration completed",Toast.LENGTH_SHORT).show();
                                mAuth.getCurrentUser().getUid();

                                addNewUser(mAuth.getCurrentUser().getUid(), email, password);
                            } else {
                                Toast.makeText(SignUpActivity.this, "Registration failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        loginRedirect.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    public startDatabase(DatabaseReference database){
        spruePadDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void addNewUser(String userId, String email, String password) {
        database = FirebaseDatabase.getInstance().getReference(databaseLink);
        userId = mAuth.getCurrentUser().getUid();

        User user = new User(email, password);
        database.child("users").child(userId).setValue(user);
    }
}