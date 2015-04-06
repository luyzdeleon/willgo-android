package edu.intec.willgo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;


public class AddEditActivity extends ActionBarActivity {


    // has the id of the preference
    public int messageID;
    public SqliteHelper sql;
    public final static String EXTRA_MESSAGE = "EXTRA_MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_form);
        Intent intent = getIntent();

        //receives the id of the preference, 0 if it is a new one
        messageID = Integer.parseInt(intent.getStringExtra(EXTRA_MESSAGE));

        if(messageID != 0){
            fillFields();
        }

    }

    // this method executes when user clicks save on the form
    public void savePreference(View button) {

        EditText preferenceName = (EditText) findViewById(R.id.EditTextName);
        EditText preferencePlace = (EditText) findViewById(R.id.EditTextPlace);
        EditText preferenceLocation = (EditText) findViewById(R.id.EditTextLocation);


        sql = new SqliteHelper(this);
        Preference p = new Preference(messageID, preferenceName.getText().toString(), preferencePlace.getText().toString(), preferenceLocation.getText().toString());
        //adds new preference if 0, else updates preference
        if(messageID == 0){

            sql.insert(p);
            this.finish();

        } else {

            sql.update(p);
            this.finish();

        }


    }

    // this method executes when user clicks cancel on the form
    public void cancelActivity(View button) {
        //finishes current activity and returns to previous activity
        this.finish();
    }


    //fills the fields of the form with preference data
    public void fillFields(){

        EditText preferenceName = (EditText) findViewById(R.id.EditTextName);
        EditText preferencePlace = (EditText) findViewById(R.id.EditTextPlace);
        EditText preferenceLocation = (EditText) findViewById(R.id.EditTextLocation);

        //fill fields from database

        sql = new SqliteHelper(this);
        Preference p = sql.findById(messageID);
        preferenceName.setText(p.getName());
        preferencePlace.setText(p.getPlace());
        preferenceLocation.setText(p.getCoor());
    }
}
