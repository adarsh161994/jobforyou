package com.efashtech.jobforyou;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class employer_home_fragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerViewJobs;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;

    public employer_home_fragment() {
        // Required empty public constructor
    }


    public static employer_home_fragment newInstance(String param1, String param2) {
        employer_home_fragment fragment = new employer_home_fragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_employer_home_fragment, container, false);

        recyclerViewJobs = view.findViewById(R.id.recview);
        recyclerViewJobs.setLayoutManager(new LinearLayoutManager(getContext()));

        firebaseFirestore = FirebaseFirestore.getInstance();

        //Query
        Query query = firebaseFirestore.collection("Users");
        //Recyclerview
        FirestoreRecyclerOptions<model> options = new FirestoreRecyclerOptions.Builder<model>()
                .setQuery(query, model.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<model, employeeViewHolder>(options) {
            @NonNull
            @Override
            public employeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_employee_recl, parent, false);
                return new employeeViewHolder(view1);
            }

            @Override
            protected void onBindViewHolder(@NonNull employeeViewHolder holder, int position, @NonNull model model) {


                holder.emp_name.setText(model.getName());
                holder.emp_relegion.setText(model.getReligion());
                holder.emp_age.setText(model.getEmployee_age());
                holder.emp_city.setText(model.getUserCity());
                holder.emp_expertise.setText(model.getExpertise());
                Glide.with(holder.img1.getContext()).load(model.getPurl()).into(holder.img1);
            }
        };

        recyclerViewJobs.setHasFixedSize(true);
//        recyclerViewJobs.setLayoutManager(new LinearLayoutManager());
        recyclerViewJobs.setAdapter(adapter);
        //ViewHolder


        return view;
    }

    private class employeeViewHolder extends RecyclerView.ViewHolder {

        private ImageView img1;
        private TextView emp_name;
        private TextView emp_relegion;
        private TextView emp_age;
        private TextView emp_city;
        private TextView emp_expertise;

        public employeeViewHolder(@NonNull View itemView) {
            super(itemView);

            img1 = itemView.findViewById(R.id.img1);
            emp_name = itemView.findViewById(R.id.emp_name);
            emp_relegion = itemView.findViewById(R.id.emp_relegion);
            emp_age = itemView.findViewById(R.id.emp_age);
            emp_city = itemView.findViewById(R.id.emp_city);
            emp_expertise = itemView.findViewById(R.id.emp_expertise);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}