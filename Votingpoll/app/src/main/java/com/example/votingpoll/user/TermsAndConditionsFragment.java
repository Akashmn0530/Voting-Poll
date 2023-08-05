package com.example.votingpoll.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.votingadmin.TermsAndConditions;
import com.example.votingpoll.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class TermsAndConditionsFragment extends Fragment {
    ScrollView scrollView;
    CheckBox ch, ch1;
    Button button;
    FirebaseFirestore db;
    LinearLayout linearLayout;


    public TermsAndConditionsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_terms_and_conditions, container, false);
        ch = view.findViewById(R.id.checkBox);
        ch1 = view.findViewById(R.id.checkBox2);
        button = view.findViewById(R.id.button);
        scrollView = view.findViewById(R.id.termsScrollView);
        db = FirebaseFirestore.getInstance();
        linearLayout = view.findViewById(R.id.termsandconditions);
        ScrollFragment scrollFragment = new ScrollFragment();
        getChildFragmentManager().beginTransaction()
                .replace(R.id.votefragmentContainer11, scrollFragment)
                .commit();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ch.isChecked() && ch1.isChecked()) {
                    linearLayout.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Successfully clicked on check box.", Toast.LENGTH_SHORT).show();
                    VotingPageFragment votingPageFragment = new VotingPageFragment();
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.votefragmentContainer1, votingPageFragment)
                            .commit();
                } else {
                    Toast.makeText(getActivity(), "Please Accept Terms  & Conditions", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}