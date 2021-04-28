package com.efashtech.jobforyou;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class employee_profile_fragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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

    private String mParam1;
    private String mParam2;



    public employee_profile_fragment() {

    }


    public static employee_profile_fragment newInstance(String param1, String param2) {
        employee_profile_fragment fragment = new employee_profile_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_employee_profile_fragment, container, false);

        multi_special_operation = view.findViewById(R.id.spcl_oper_updt);
        //For spinners
        spinner_department = view.findViewById(R.id.job_department_spnr_updt);
        spinner_job_cat =(Spinner) view.findViewById(R.id.job_category_spnr_updt);
        spinner_operators = (Spinner) view.findViewById(R.id.spinner_operator_updt);
        //               All editText
        dob = view.findViewById(R.id.et_dob_updt);
        radioGroup = view.findViewById(R.id.radio_updt);
        fatherName = view.findViewById(R.id.father_name_updt);
        alterPhone = view.findViewById(R.id.alter_phone_updt);
        adharNmbr = view.findViewById(R.id.adhar_number_updt);
        pAddress = view.findViewById(R.id.permanent_address_updt);
        employeeAge= view.findViewById(R.id.age_updt);
        userName = view.findViewById(R.id.name_resume_updt);
        userPhone = view.findViewById(R.id.resume_phone_updt);
        userPincode = view.findViewById(R.id.pin_code_updt);
        userCAddress = view.findViewById(R.id.address_updt);
        submitBtn = view.findViewById(R.id.sbmtbtn_updt);
        user_exp = view.findViewById(R.id.experience_updt);
        user_prev_cmpny = view.findViewById(R.id.previous_company_updt);
        user_prfrd_lctn = view.findViewById(R.id.prfrd_location_updt);
        //           Autocomplete TextView
        userCity = view.findViewById(R.id.auto_cmplt_city_updt);
        userRelegion = view.findViewById(R.id.auto_religion_updt);
        userCast = view.findViewById(R.id.auto_cast_updt);
        userExpertise = view.findViewById(R.id.expertise_updt);
        return view;
    }
}