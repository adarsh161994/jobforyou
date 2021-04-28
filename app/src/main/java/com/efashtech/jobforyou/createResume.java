package com.efashtech.jobforyou;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Guideline;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.service.autofill.RegexValidator;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class createResume extends AppCompatActivity {

    AutoCompleteTextView userCity,userRelegion,userCast,userExpertise;

    RadioGroup radioGroup;
    RadioButton radioButton;
    EditText userName, userPhone, userPincode, userCAddress, dob, user_exp, user_prev_cmpny, user_prfrd_lctn;
    EditText fatherName,alterPhone,adharNmbr,pAddress,employeeAge;
    Spinner spinner_department,spinner_job_cat,spinner_operators;
    Button submitBtn;
    DatePickerDialog datePickerDialog;
    AwesomeValidation awesomeValidation;
    TextView multi_special_operation;
//    multiple select specialised operation
    boolean[] select_operation;
    ArrayList<Integer> oprList = new ArrayList<>();
    String[] oprArray = {"COLLAR ATTACH", "WAISTBAND ATTACH AND FINISH", "ZIP MAKING"
            , "PLACKET MAKING", "SHIRT PLACKET MAKING", "T-SHIRT BOTTOM", "OTHER"};

    String user_id;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private ProgressDialog progressDialog;

    ArrayList<String> arrayList_deprtmnt;
    ArrayAdapter<String> arrayAdapter_deprtmnt;

    ArrayList<String> arrayList_fabric,arrayList_store,arrayList_cutting,
            arrayList_sewing,arrayList_finishing,arrayList_washing,arrayList_sampling;

    ArrayAdapter<String> arrayAdapter_jobs;
    ArrayAdapter<String> arrayAdapter_operators;
    ArrayList<String> arrayList_operator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_resume);





        //        Date picker code
        viewInitialize();
        dobFun();
        //All spinner working
        allSpinnerwork();

        //All Autocomplete textviews
        allAutoTv();

        //For checking validation or field mandatory
        awsmValidMethod();
        select_operation = new boolean[oprArray.length];
        multi_special_operation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOperation();
            }
        });

        progressDialog = new ProgressDialog(this);


        firebaseAuth = FirebaseAuth.getInstance();
        user_id = firebaseAuth.getCurrentUser().getUid();

        firebaseFirestore = FirebaseFirestore.getInstance();


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resumeDb();
            }
        });
    }



    private void showOperation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(createResume.this);
        //set title
        builder.setTitle("Select special operation");
        builder.setCancelable(false);
        builder.setMultiChoiceItems(oprArray, select_operation, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i, boolean b) {
                if (b) {
                    oprList.add(i);
//                    Collections.sort(oprList);
                } else {
                    oprList.remove(i);
                }
            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int j = 0; j < oprList.size(); j++) {
                    stringBuilder.append(oprArray[oprList.get(j)]);

                    if (j != oprList.size() - 1) {
                        stringBuilder.append(", ");
                    }
                }
                multi_special_operation.setText(stringBuilder.toString());
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int j = 0; j < select_operation.length; j++) {
                    select_operation[j] = false;
                    oprList.clear();
                    multi_special_operation.setText("");
                }
            }
        });
        builder.show();


    }

    private void awsmValidMethod() {
        //Initialize validation style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        //add validation name phone et_dob radio
        awesomeValidation.addValidation(this, R.id.name_resume,
                RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.resume_phone,
                "[5-9]{1}[0-9]{9}$", R.string.invalid_phone);
        awesomeValidation.addValidation(this, R.id.et_dob,
                RegexTemplate.NOT_EMPTY, R.string.invalid_dob);
        awesomeValidation.addValidation(this, R.id.radio,
                RegexTemplate.NOT_EMPTY, R.string.invalid_gender);
        awesomeValidation.addValidation(this,R.id.age,
                RegexTemplate.NOT_EMPTY,R.string.invalid_age);
    }

    private void allAutoTv() {
        //Auto complete tv
        userCity = findViewById(R.id.auto_cmplt_city);
        String[] city = {"Faridabad", "Delhi", "Noida", "Gurugram"};
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, city);
        userCity.setAdapter(adapter);

        userRelegion = findViewById(R.id.auto_religion);
        String[] relegion = {"Hinduism", "Islam", "Christianity", "Sikhism"};
        ArrayAdapter<String> adapter_relegion = new ArrayAdapter(this, android.R.layout.simple_list_item_1, relegion);
        userRelegion.setAdapter(adapter_relegion);

        userCast = findViewById(R.id.auto_cast);
        String[] cast = {"GENERAL", "OBC", "SC", "ST","OTHERS"};
        ArrayAdapter<String> adapter_cast = new ArrayAdapter(this, android.R.layout.simple_list_item_1, cast);
        userCast.setAdapter(adapter_cast);

        userExpertise = findViewById(R.id.expertise);
        String[] expertise = {"KNIT", "WOVEN", "BOTH"};
        ArrayAdapter<String> adapter_expertise = new ArrayAdapter(this, android.R.layout.simple_list_item_1, expertise);
        userExpertise.setAdapter(adapter_expertise);
    }

    private void allSpinnerwork() {
        //        spinner code
        arrayList_deprtmnt = new ArrayList<>();
        arrayList_deprtmnt.add("FABRIC");
        arrayList_deprtmnt.add("STORE");
        arrayList_deprtmnt.add("CUTTING");
        arrayList_deprtmnt.add("SEWING");
        arrayList_deprtmnt.add("FINISHING");
        arrayList_deprtmnt.add("WASHING");
        arrayList_deprtmnt.add("SAMPLING DEPARTMENT");

        arrayAdapter_deprtmnt = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item,arrayList_deprtmnt);
        spinner_department.setAdapter(arrayAdapter_deprtmnt);

        //     job category spinner process
        arrayList_fabric = new ArrayList<>();
        arrayList_fabric.add("CHECKER");
        arrayList_fabric.add("HELPER");

        arrayList_store = new ArrayList<>();
        arrayList_store.add("CHECKER");
        arrayList_store.add("HELPER");

        arrayList_cutting = new ArrayList<>();
        arrayList_cutting.add("LAYER MAN");
        arrayList_cutting.add("LAYER CUTTER");

        arrayList_finishing = new ArrayList<>();
        arrayList_finishing.add("INITIAL CHECKER");
        arrayList_finishing.add("FINAL CHECKER");
        arrayList_finishing.add("RE FINAL VISUAL CHECKER");
        arrayList_finishing.add("MEASUREMENT");

        arrayList_washing = new ArrayList<>();
        arrayList_washing.add("WASHING HELPER");
        arrayList_washing.add("WASHING MASTER");

        arrayList_sampling = new ArrayList<>();
        arrayList_sampling.add("SAMPLE TAILOR");
        arrayList_sampling.add("SAMPLE COORDINATOR");

        arrayList_sewing = new ArrayList<>();
        arrayList_sewing.add("HELPER");
        arrayList_sewing.add("CHECKER");
        arrayList_sewing.add("LINE LOADER");
        arrayList_sewing.add("OPERATOR");

        //     Arraylist for third spinner(operator selection)
        arrayList_operator = new ArrayList<>();
        arrayList_operator.add("");
        arrayList_operator.add("SINGLE NEEDLE");
        arrayList_operator.add("5TH OVERLOCK");
        arrayList_operator.add("3TH OVERLOCK");
        arrayList_operator.add("4TH OVERLOCK");
        arrayList_operator.add("FLATLOCK FLATBED");
        arrayList_operator.add("FLATLOCK COMPRESSOR");
        arrayList_operator.add("KANSAI");
        arrayList_operator.add("BUTTON HOLE");
        arrayList_operator.add("BUTTON STITCH");
        arrayList_operator.add("BARTACK");
        arrayList_operator.add("SNAP BUTTON");

        spinner_department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    arrayAdapter_jobs = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item,arrayList_fabric);
                }
                if (position==1){
                    arrayAdapter_jobs = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item,arrayList_store);
                }
                if (position==2){
                    arrayAdapter_jobs = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item,arrayList_cutting);
                }
                if (position==3){
                    arrayAdapter_jobs = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item,arrayList_sewing);
                }
                if (position==4){
                    arrayAdapter_jobs = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item,arrayList_finishing);
                }
                if (position==5){
                    arrayAdapter_jobs = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item,arrayList_washing);
                }
                if (position==6){
                    arrayAdapter_jobs = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item,arrayList_sampling);
                }
                spinner_job_cat.setAdapter(arrayAdapter_jobs);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_job_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    spinner_operators.setVisibility(View.GONE);
                    multi_special_operation.setVisibility(View.GONE);
                    arrayAdapter_operators = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_operator);

                }
                if (position == 1) {

                    spinner_operators.setVisibility(View.GONE);
                    multi_special_operation.setVisibility(View.GONE);
                    arrayAdapter_operators = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_operator);

                }
                if (position == 2) {
                    spinner_operators.setVisibility(View.GONE);
                    multi_special_operation.setVisibility(View.GONE);
                    arrayAdapter_operators = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_operator);

                }

                if (position == 3) {
                    spinner_operators.setVisibility(View.VISIBLE);
                    multi_special_operation.setVisibility(View.VISIBLE);
                    arrayAdapter_operators = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_operator);

                }
                spinner_operators.setAdapter(arrayAdapter_operators);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void viewInitialize() {

        multi_special_operation = findViewById(R.id.spcl_oper);
        //For spinners
        spinner_department = findViewById(R.id.job_department_spnr);
        spinner_job_cat =(Spinner) findViewById(R.id.job_category_spnr);
        spinner_operators = (Spinner) findViewById(R.id.spinner_operator);

        dob = findViewById(R.id.et_dob);
        radioGroup = findViewById(R.id.radio);
        fatherName = findViewById(R.id.father_name);
        alterPhone = findViewById(R.id.alter_phone);
        adharNmbr = findViewById(R.id.adhar_number);
        pAddress = findViewById(R.id.permanent_address);
        employeeAge= findViewById(R.id.age);
        userName = findViewById(R.id.name_resume);
        userPhone = findViewById(R.id.resume_phone);
        userPincode = findViewById(R.id.pin_code);
        userCAddress = findViewById(R.id.address);
        submitBtn = findViewById(R.id.sbmtbtn);
        user_exp = findViewById(R.id.experience);
        user_prev_cmpny = findViewById(R.id.previous_company);
        user_prfrd_lctn = findViewById(R.id.prfrd_location);
    }

    private void resumeDb() {
        int ID = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(ID);
        final String gender = radioButton.getText().toString();

        //For database
        final String username = userName.getText().toString();
        final String userfathername = fatherName.getText().toString();
        final String useralterphone = alterPhone.getText().toString();
        final String useradhar = adharNmbr.getText().toString();
        final String userpaddress = pAddress.getText().toString();
        final String userreligiion = userRelegion.getText().toString();
        final String usercastcat = userCast.getText().toString();
        final String userexpertise = userExpertise.getText().toString();
        final String userdepartment = spinner_department.getSelectedItem().toString();
        final String userjobcat = spinner_job_cat.getSelectedItem().toString();
        final String useroperatorcat = spinner_operators.getSelectedItem().toString();
        final String userphone = userPhone.getText().toString();
        final String userpincode = userPincode.getText().toString();
        final String user_cur_address = userCAddress.getText().toString();
        final String usercity = userCity.getText().toString();
        final String dob_user = dob.getText().toString();
        final String exp = user_exp.getText().toString();
        final String prv_exp = user_prev_cmpny.getText().toString();
        final String prfrd_location = user_prfrd_lctn.getText().toString();
        final String special_operation = multi_special_operation.getText().toString();
        final String employee_age = employeeAge.getText().toString();

        if (awesomeValidation.validate()) {
            //do your work here

            progressDialog.setMessage("Profile created successfully..");
            progressDialog.show();

            user_id = firebaseAuth.getCurrentUser().getUid();
            DocumentReference documentReference = firebaseFirestore.collection("Users").document(user_id);

            Map<String, String> userData = new HashMap<>();

            userData.put("current_address", user_cur_address);
            userData.put("userPincode", userpincode);
            userData.put("userCity", usercity);
            userData.put("Name", username);
            userData.put("phone", userphone);
            userData.put("dob", dob_user);
            userData.put("gender", gender);
            userData.put("experience", exp);
            userData.put("previous_company", prv_exp);
            userData.put("preferred_location", prfrd_location);
            userData.put("father_name", userfathername);
            userData.put("alternate_phone", useralterphone);
            userData.put("Aadhar_number", useradhar);
            userData.put("permanent_address", userpaddress);
            userData.put("religion", userreligiion);
            userData.put("cast_category", usercastcat);
            userData.put("expertise", userexpertise);
            userData.put("department", userdepartment);
            userData.put("job_category", userjobcat);
            userData.put("operator_category",useroperatorcat);
            userData.put("specialized_operations",special_operation);
            userData.put("employee_age",employee_age);

            documentReference.set(userData)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast.makeText(createResume.this, "Stored user data successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(createResume.this, Dashboard.class));
                            } else {
                                Toast.makeText(createResume.this, "Firebase error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


        } else {
            Toast.makeText(createResume.this, "Validation failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void dobFun() {
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(createResume.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        dob.setText(day + "/" + (month + 1) + "/" + year);
//                        dob.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
                        dob.setText(dayOfMonth + "/" + (month+1) + "/" + year);

                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

    }
}
