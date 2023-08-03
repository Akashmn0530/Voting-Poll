package com.example.votingpoll.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.votingpoll.R;

public class TermsAndConditionsFragment extends Fragment {
    public TermsAndConditionsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_terms_and_conditions, container, false);
    }


    CheckBox ch, ch1;
    Button button;
    LinearLayout linearLayout;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ch = getView().findViewById(R.id.checkBox);
        ch1 = getView().findViewById(R.id.checkBox2);
        button = getView().findViewById(R.id.button);
        linearLayout = getView().findViewById(R.id.termsandconditions);
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

    }
}