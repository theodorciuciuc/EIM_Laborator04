package ro.pub.cs.systems.eim.lab04.contactsmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText name;
    private EditText phone;
    private EditText email;
    private EditText address;
    private EditText job;
    private EditText company;
    private EditText website;
    private EditText IM;

    private Button showHide;
    private Button save;
    private Button cancel;
    private LinearLayout secondContainer;

    private ButtonTapListener buttonTapListener = new ButtonTapListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone_number);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        job = findViewById(R.id.job);
        company = findViewById(R.id.company);
        website = findViewById(R.id.website);
        IM = findViewById(R.id.IM);

        showHide = findViewById(R.id.show_hide);
        save = findViewById(R.id.save);
        cancel = findViewById(R.id.cancel);
        secondContainer = findViewById(R.id.second_container);

        showHide.setOnClickListener(buttonTapListener);
        save.setOnClickListener(buttonTapListener);
        cancel.setOnClickListener(buttonTapListener);

        Intent intent = getIntent();
        if (intent != null) {
            String phoneSTR = intent.getStringExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY");
            if (phoneSTR != null) {
                phone.setText(phoneSTR);
            } else {
                Toast.makeText(this, getResources().getString(R.string.phone_error), Toast.LENGTH_LONG).show();
            }
        }
    }

    private class ButtonTapListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.show_hide) {
                if (secondContainer.getVisibility() == View.VISIBLE) {
                    showHide.setText(getResources().getString(R.string.show));
                    secondContainer.setVisibility(View.INVISIBLE);
                }
                else {
                    showHide.setText(getResources().getString(R.string.hide));
                    secondContainer.setVisibility(View.VISIBLE);
                }
            }
            if (view.getId() == R.id.save) {
                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                if (name.getText().toString() != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.NAME, name.getText().toString());
                }
                if (phone.getText().toString() != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone.getText().toString());
                }
                if (email.getText().toString() != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email.getText().toString());
                }
                if (address.getText().toString() != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address.getText().toString());
                }
                if (job.getText().toString() != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, job.getText().toString());
                }
                if (company.getText().toString() != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company.getText().toString());
                }
                ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
                if (website.getText().toString() != null) {
                    ContentValues websiteRow = new ContentValues();
                    websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                    websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website.getText().toString());
                    contactData.add(websiteRow);
                }
                if (IM.getText().toString() != null) {
                    ContentValues imRow = new ContentValues();
                    imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                    imRow.put(ContactsContract.CommonDataKinds.Im.DATA, IM.getText().toString());
                    contactData.add(imRow);
                }
                intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
                startActivityForResult(intent, 2022);
            }
            if (view.getId() == R.id.cancel) {
                setResult(Activity.RESULT_CANCELED, new Intent());
                finish();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 2022) {
            setResult(resultCode, new Intent());
            finish();
        }
    }
}