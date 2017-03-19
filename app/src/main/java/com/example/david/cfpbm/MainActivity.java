package com.example.david.cfpbm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private SignInButton sIgnInButton; //Signing button
    private GoogleSignInOptions googleSignInOptions; //Signing Options
    private GoogleApiClient mGoogleApiClient; //google api client
    private int RC_SIGN_IN = 100; //Signing constant to check the activity result

    private static DecimalFormat df2 = new DecimalFormat(".##");

    private Button incomeButton;
    private Button transportationButton;
    private Button dietButton;
    private Button utilitiesButton;
    private Button healthButton;
    private Button extrasButton;
    private TextView textViewName;
    private String email, name;
    private String _email = "";

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewName = (TextView) findViewById(R.id.textViewName);
        incomeButton = (Button)findViewById(R.id.btn_income);
        transportationButton = (Button)findViewById(R.id.btn_transportation);
        dietButton = (Button)findViewById(R.id.btn_diet);
        utilitiesButton = (Button)findViewById(R.id.btn_utilities);
        healthButton = (Button)findViewById(R.id.btn_health);
        extrasButton = (Button)findViewById(R.id.btn_extras);

        //Initializing google signin option
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        //Initializing signinbutton
        sIgnInButton = (SignInButton) findViewById(R.id.sign_in_button);
        sIgnInButton.setSize(SignInButton.SIZE_WIDE);
        sIgnInButton.setScopes(googleSignInOptions.getScopeArray());
        //Initializing google api client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApiIfAvailable(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
        //Setting onclick listener to signing button
        sIgnInButton.setOnClickListener(this);
    }

    public void emailValidation(String email) {

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this); //OBJECT
        databaseAccess.open();

        ArrayList<HashMap<String, String>> quotes = databaseAccess.getQuotes(); //takes list from DatabaseAccess

        _email = "'davidfelygreene@gmail.com'";
        DatabaseAccess repo = new DatabaseAccess(this);
        Person person = new Person();
        person = repo.getByEmail(_email);



        textViewName.setText(person.getName());
        incomeButton.setText("$ " + df2.format(Double.parseDouble(person.getIncome())));
        transportationButton.setText("Travel: $ " + df2.format(Double.parseDouble(person.getTransportation())));
        dietButton.setText("Diet: $ " + df2.format(Double.parseDouble(person.getDiet())));
        utilitiesButton.setText("Utilities: $ " + df2.format(Double.parseDouble(person.getUtilities())));
        healthButton.setText("Health: $ " + df2.format(Double.parseDouble(person.getHealth())));
        extrasButton.setText("Extras: $ " + df2.format(Double.parseDouble(person.getExtras())));

        databaseAccess.close();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    //After the signing we are calling this function SECOND
    private void handleSignInResult(GoogleSignInResult result) {
        //If the login succeed
        if (result.isSuccess()) {
            //Getting google account
            GoogleSignInAccount acct = result.getSignInAccount();
            //Displaying name and email

            name = new String(acct.getDisplayName());
            email  = new String(acct.getEmail());
            emailValidation(email);
            setTitle(email); //sets actionbar to email!

        } else { Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show(); }
    }

    @Override//FIRST
    public void onClick(View v) {
        if (v == sIgnInButton) {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }
        if(mGoogleApiClient.isConnected()) { Auth.GoogleSignInApi.signOut(mGoogleApiClient);}
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {}

}
