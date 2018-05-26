/*package com.wiffieverywhere.mattd.wiffi_everywhere;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//import butterknife.BindView;
//import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity
{
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    */
/*
    private EditText _emailText;
    private EditText _passwordText;
    private Button _loginButton;
    private TextView _signupLink;

    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        _emailText = findViewById(R.id.input_email);
        _passwordText = findViewById(R.id.input_password);
        _loginButton = findViewById(R.id.btn_login);
        _signupLink = findViewById(R.id.link_signup);

        _loginButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    @Override
    public void onStart()
    {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser)
    {

    }

    public void login()
    {
        Log.d(TAG, "Login");

        if (!validate())
        {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });

        new android.os.Handler().postDelayed(
                new Runnable()
                {
                    public void run()
                    {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_SIGNUP)
        {
            if (resultCode == RESULT_OK)
            {
                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically

                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess()
    {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed()
    {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate()
    {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
*/

package com.wiffieverywhere.mattd.wiffi_everywhere;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatPreferenceActivity implements
        View.OnClickListener {

    private static final String TAG = "EmailPassword";

    private TextView mStatusTextView;
    private TextView mDetailTextView;

    private EditText input_email;
    private EditText input_password;

    // [START declare_auth]
    private FirebaseAuth mAuth;

    private Button btn_login;
    private Button btn_register;
    // [END declare_auth]

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Views
        input_email = (EditText) findViewById(R.id.input_email);
        input_password = (EditText) findViewById(R.id.input_password);

        // Buttons
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);

        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();

        // [END initialize_auth]
    }

    // [START on_start_check_user]
    @Override
    public void onStart()
    {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    // [END on_start_check_user]

    private void createAccount(String email, String password)
    {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
        {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            Toast.makeText(LoginActivity.this, "Registration successful.", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    private void signIn(final String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }


        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, "Authentication successful.", Toast.LENGTH_SHORT).show();
                            updateUI(user);

                            try
                            {

                                Intent menuprincipal = new Intent(getBaseContext(), MainActivity.class);
                                menuprincipal.putExtra("EXTRA_SESSION_ID", email);
                                startActivity(menuprincipal);

                            }
                            catch(Exception e)
                            {
                                e.printStackTrace();
                            }

                        }
                        else
                        {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful())
                        {
                            //mStatusTextView.setText("Auth failed");
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    private void signOut()
    {
        mAuth.signOut();
        updateUI(null);
    }

    private boolean validateForm() 
    {
        boolean valid = true;

        String email = input_email.getText().toString();
        if (TextUtils.isEmpty(email))
        {
            input_email.setError("Required.");
            valid = false;
        } 
        else 
        {
            if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {
                input_email.setError("Bad mail format.");
                valid = false;
            }
            else
            {
                input_email.setError(null);
            }
        }

        String password = input_password.getText().toString();
        if (TextUtils.isEmpty(password))
        {
            input_password.setError("Required.");
            valid = false;
        }
        else
        {
            if(password.length()<6)
            {
                input_password.setError("Min. 6 characters.");
            }
            else
            {
                input_password.setError(null);
            }
        }

        return valid;
    }

    @SuppressLint("StringFormatInvalid")
    private void updateUI(FirebaseUser user)
    {
        if (user != null)
        {
            //Toast.makeText(LoginActivity.this, "Authenticated.", Toast.LENGTH_SHORT).show();
            //mStatusTextView.setText(getString(R.string.emailpassword_status_fmt, user.getEmail(), user.isEmailVerified()));
            //mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            //findViewById(R.id.email_password_buttons).setVisibility(View.GONE);
            //findViewById(R.id.email_password_fields).setVisibility(View.GONE);
            //findViewById(R.id.signed_in_buttons).setVisibility(View.VISIBLE);

            //findViewById(R.id.verify_email_button).setEnabled(!user.isEmailVerified());
        }
        else
        {
            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

            //mStatusTextView.setText("Signed Out");
            //mDetailTextView.setText(null);

            //findViewById(R.id.email_password_buttons).setVisibility(View.VISIBLE);
            //findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);
            //findViewById(R.id.signed_in_buttons).setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v)
    {
        int i = v.getId();

        if (i == R.id.btn_register) 
        {
            if(validateForm())
            {
                createAccount(input_email.getText().toString(), input_password.getText().toString());
            }
            else
            {
                Toast.makeText(LoginActivity.this, "Error registering", Toast.LENGTH_SHORT).show();
            }
        }
        else if (i == R.id.btn_login) 
        {
            if(validateForm())
            {
                signIn(input_email.getText().toString(), input_password.getText().toString());
            }
            else
            {
                Toast.makeText(LoginActivity.this, "Error loging in", Toast.LENGTH_SHORT).show();

            }
        }
    }
}



