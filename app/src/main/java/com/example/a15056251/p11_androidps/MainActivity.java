package com.example.a15056251.p11_androidps;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    String msg = "";
    ArrayList<String> sg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.lv);

        String[] objects = new String[]{
                "Singapore 123",
                "Singapore 456",
                "Singapore 789"
        };
        //create list from string array elements
        sg = new ArrayList<String>(Arrays.asList(objects));

        //create an arrayadapter from list
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,sg);

        lv.setAdapter(arrayAdapter);

        LayoutInflater inflater = (LayoutInflater)
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout passPhrase =
                (LinearLayout) inflater.inflate(R.layout.passphrase, null);
        final EditText etPassphrase = (EditText) passPhrase
                .findViewById(R.id.editTextPassPhrase);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please login")
                .setView(passPhrase)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        int permissionCheck_Fine = ContextCompat.checkSelfPermission(
                                MainActivity.this, android.Manifest.permission.CALL_PHONE);

                        if (permissionCheck_Fine != PermissionChecker.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.CALL_PHONE}, 0);
                            // stops the action from proceeding further as permission not
                            //  granted yet
                            return;
                        }
                        String value = etPassphrase.getText().toString();
                        if (Integer.parseInt(value) != 738964){

                            Toast.makeText(MainActivity.this, "You had entered the wrong code", Toast.LENGTH_LONG).show();
                            finish();

                        }
                        Toast.makeText(MainActivity.this, "You had entered " +
                                etPassphrase.getText().toString(), Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("NO ACCESS CODE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        Toast.makeText(MainActivity.this, "You clicked no access code.", Toast.LENGTH_LONG).show();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Tally against the respective action item clicked
        //  and implement the appropriate action
        if (item.getItemId() == R.id.action_send) {

            String [] list = new String[] { "Email", "SMS" };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select the way to enrich your friend")
                    .setItems(list, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                Intent email = new Intent(Intent.ACTION_SEND);
                                email.putExtra(Intent.EXTRA_SUBJECT,
                                        "Singapore");
                                String msg = " ";
                                email.putExtra(Intent.EXTRA_TEXT,
                                        msg);
                                email.setType("message/rfc822");
                                startActivity(Intent.createChooser(email,
                                        "Choose an Email client :"));

                                Toast.makeText(MainActivity.this, "Email selected",
                                        Toast.LENGTH_LONG).show();


                            } else if (which == 1) {
                                for (int x = 0; x < sg.size(); x++){
                                    msg += sg.get(x);
                                }

                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage("84997645", null, msg , null, null);

                                Toast.makeText(MainActivity.this, "SMS selected",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        } else if (item.getItemId() == R.id.action_quit) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Quit?")
                    .setPositiveButton("QUIT", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {			  Toast.makeText(MainActivity.this, "You clicked yes",
                                Toast.LENGTH_LONG).show();
                            finish();
                        }
                    })
                    .setNegativeButton("NOT REALLY", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {			  Toast.makeText(MainActivity.this, "You clicked no",
                                Toast.LENGTH_LONG).show();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }
}

