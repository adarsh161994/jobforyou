package com.efashtech.jobforyou;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class employer_details extends AppCompatActivity {

    AutoCompleteTextView company_expertise;
    EditText companyName,contactPersonName,contactPersonPhone,alterPhone,
            companyEmail,companyAddress,companyPincode,companyCity,companyGST;
    Button companySubmitBtn;
    AwesomeValidation awesomeValidation;
    String user_id;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_details);


        viewInitialize();

        //All Autocomplete textviews
        allAutoTv();

        //For checking validation or field mandatory
        awsmValidMethod();


        progressDialog = new ProgressDialog(this);


        firebaseAuth = FirebaseAuth.getInstance();

        firebaseFirestore = FirebaseFirestore.getInstance();

        companySubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resumeDb();
            }
        });
    }


    private void awsmValidMethod() {
        //Initialize validation style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        //add validation name phone et_dob radio
        awesomeValidation.addValidation(this, R.id.name_company,
                RegexTemplate.NOT_EMPTY, R.string.invalid_company_name);
        awesomeValidation.addValidation(this, R.id.company_phone,
                "[5-9]{1}[0-9]{9}$", R.string.invalid_company_phone);
        awesomeValidation.addValidation(this, R.id.company_email,
                RegexTemplate.NOT_EMPTY, R.string.invalid_company_email);
        awesomeValidation.addValidation(this, R.id.company_address,
                RegexTemplate.NOT_EMPTY, R.string.invalid_company_address);
    }

    private void allAutoTv() {
        //Auto complete tv

        company_expertise = findViewById(R.id.specialised_working);
        String[] expertise = {"KNIT", "WOVEN", "BOTH"};
        ArrayAdapter<String> adapter_expertise = new ArrayAdapter(this, android.R.layout.simple_list_item_1, expertise);
        company_expertise.setAdapter(adapter_expertise);
    }


    private void viewInitialize() {


        contactPersonName = findViewById(R.id.contact_person_name);
        alterPhone = findViewById(R.id.company_alter_phone);
        companyGST = findViewById(R.id.company_gst);
        companyAddress = findViewById(R.id.company_address);
        companyName = findViewById(R.id.name_company);
        contactPersonPhone = findViewById(R.id.company_phone);
        companyPincode = findViewById(R.id.company_pincode);
        companySubmitBtn = findViewById(R.id.sbmtbtn);
        companyEmail = findViewById(R.id.company_email);
        companyCity = findViewById(R.id.company_city);
    }

    private void resumeDb() {

        //For database
        final String companyname = companyName.getText().toString();
        final String contactperson = contactPersonName.getText().toString();
        final String alterphone = alterPhone.getText().toString();
        final String companyGst = companyGST.getText().toString();
        final String companyaddress = companyAddress.getText().toString();
        final String companyexpertise = company_expertise.getText().toString();
        final String companyphone = contactPersonPhone.getText().toString();
        final String companypincode = companyPincode.getText().toString();
        final String companycity = companyCity.getText().toString();
        final String companyemail = companyEmail.getText().toString();

        if (awesomeValidation.validate()) {
            //do your work here

            progressDialog.setMessage("Profile created successfully..");
            progressDialog.show();

            user_id = firebaseAuth.getCurrentUser().getUid();
            DocumentReference documentReference = firebaseFirestore.collection("Users_Employers").document(user_id).collection("Employer_profile").document();

            Map<String, String> userData = new HashMap<>();

            userData.put("company_name", companyname);
            userData.put("contact_person_Name", contactperson);
            userData.put("alter_phone", alterphone);
            userData.put("GST_no.", companyGst);
            userData.put("company_address", companyaddress);
            userData.put("company_expertise", companyexpertise);
            userData.put("company_phone", companyphone);
            userData.put("company_pincode", companypincode);
            userData.put("company_city", companycity);
            userData.put("company_email", companyemail);

            documentReference.set(userData)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast.makeText(employer_details.this, "Stored user data successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(employer_details.this, Dashboard_employer.class));
                            } else {
                                Toast.makeText(employer_details.this, "Firebase error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


        } else {
            Toast.makeText(employer_details.this, "Validation failed", Toast.LENGTH_SHORT).show();
        }
    }

}