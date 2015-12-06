package com.callisto.friendinneed;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends Activity implements View.OnClickListener{

    private static final String CONTACTS_SET_KEY = "contacts";

    SharedPreferences sPref;
    Button btnAddContact;
    EditText etEnterContactNumber;
    ListView lvContactNumbers;
    Set<String> contactsDefaultSet = new HashSet<String>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEnterContactNumber = (EditText) findViewById(R.id.etEnterContactNumber);
        btnAddContact = (Button) findViewById(R.id.btnAddContact);
        lvContactNumbers = (ListView) findViewById(R.id.lvContactNumbers);

        btnAddContact.setOnClickListener(this);
        showContact();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void addContact(){

        sPref = getPreferences(MODE_PRIVATE);
        Editor ed = sPref.edit();
        String phoneNumber = etEnterContactNumber.getText().toString();
       phoneNumber = PhoneNumberUtils.formatNumber(phoneNumber);
        Toast.makeText(this, phoneNumber, Toast.LENGTH_SHORT).show();
        if(PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)){
            Set<String> contactsStringSet = sPref.getStringSet(CONTACTS_SET_KEY, contactsDefaultSet);
            contactsStringSet.add(phoneNumber);
            ed.putStringSet(CONTACTS_SET_KEY, contactsStringSet);
            ed.commit();
            etEnterContactNumber.setText("");
            Toast.makeText(this, "Contact saved", Toast.LENGTH_SHORT).show();
        } else {

//            etEnterContactNumber.setHint("Enter valide phone number");
            etEnterContactNumber.setError("Enter valide phone number");
        }





    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private Set<String> getContacts(){
        sPref = getPreferences(MODE_PRIVATE);
        Set<String> contactsStringSet = sPref.getStringSet(CONTACTS_SET_KEY, contactsDefaultSet);

        return contactsStringSet;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAddContact:
                Log.i("btn", "press btn");
                addContact();
                showContact();
                break;
        }

    }

    private void showContact(){
        Set<String> contactsFromSharedPref = getContacts();
        String[] contactsArray = contactsFromSharedPref.toArray(new String[contactsFromSharedPref.size()]);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactsArray);
        lvContactNumbers.setAdapter(adapter);
    }
}
