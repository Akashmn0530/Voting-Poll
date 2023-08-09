package com.example.votingpoll.user;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.votingpoll.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class TermsAndConditionsFragment extends Fragment {
    ScrollView scrollView;
    CheckBox ch, ch1;
    Button button;
    FirebaseFirestore db;
    LinearLayout linearLayout;


    public TermsAndConditionsFragment() {
    }

    @SuppressLint("MissingInflatedId")
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

        button.setOnClickListener(view1 -> {
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
        });
        return view;
    }
}