package com.example.david.cfpbm;

import android.content.Intent;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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
import java.util.List;

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
    private double travelAmount = 23257.98;

    private ListView listView;
    Person person = new Person();

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

//        //Checks email
//        this.listView = (ListView) findViewById(R.id.listView);
//        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
//        databaseAccess.open();
//        List<String> quotes = databaseAccess.getQuotes();
//        databaseAccess.close();
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, quotes);
//        this.listView.setAdapter(adapter);


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

    public void calcCheck(){
        if(email.equals("dgreen88@uncc.edu")){
            travelAmount += 200.23;
            incomeButton.setText("Travelll : $" + df2.format(travelAmount));
        } else if(email.equals("davidfelygreene@gmail.com")) {
            travelAmount -= 50.90;
            incomeButton.setText("Income : $" + df2.format(travelAmount));
            transportationButton.setText("Transportation : $" + df2.format(travelAmount));
            dietButton.setText("Diet : $" + df2.format(travelAmount));
            utilitiesButton.setText("Utilities : $" + df2.format(travelAmount));
            healthButton.setText("Health : $" + df2.format(travelAmount));
            extrasButton.setText("Extras : $" + df2.format(travelAmount));
        }
    }

    public void emailValidation(String email) {

        //Checks email
        this.listView = (ListView) findViewById(R.id.listView);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this); //OBJECT
        databaseAccess.open();

        ArrayList<HashMap<String, String>> quotes = databaseAccess.getQuotes();

        for(int i = 0; i < quotes.size(); i++){
            HashMap<String, String> content = new HashMap<String, String>();
            content = quotes.get(i);
            if(content.containsValue(content.get("google_email").toString())){
                textViewName.setText(email);
            } else {textViewName.setText("TEST");}
        }


        databaseAccess.close();
        //Display
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, quotes);
//        this.listView.setAdapter(adapter);



        //calcCheck();
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
            //gu.setKEY_email(email);
            //gu.setKEY_name(name);
            calcCheck();
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
