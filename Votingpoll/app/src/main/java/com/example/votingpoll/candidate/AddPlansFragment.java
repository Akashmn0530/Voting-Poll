package com.example.votingpoll.candidate;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.votingpoll.R;
import com.example.votingpoll.user.Login;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddPlansFragment extends Fragment {
    String plans;
    EditText plansEdittext;
    Button addPlansBtn;
    FirebaseFirestore db;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        plansEdittext = getView().findViewById(R.id.addPlans123);
        addPlansBtn = getView().findViewById(R.id.addPlansBtn);
        String aadhar = CandiLogin.cidpass;
        db = FirebaseFirestore.getInstance();
        addPlansBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plans = plansEdittext.getText().toString();
                Log.d("Akash","Addplan plans"+plans);
                updateData(plans,aadhar);
            }
        });
    }
    void updateData(String plans,String aadhar){
        if (aadhar != null) {
            // Use the userId to update the Firestore data
            DocumentReference update1 = db.collection("CandiData").document(aadhar);
            //Update DB
            update1
                    .update("aucPlans",plans
                    )
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getActivity(), "Successfully updated", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
    public AddPlansFragment() { }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {// Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_plans, container, false);
    }
}