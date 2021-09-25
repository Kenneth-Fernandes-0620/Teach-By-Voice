package com.rosarycollege.teachbyvoice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {

    private Button Login;
    private EditText email, password;
    private View view;
    private ProgressBar loading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_login, container, false);
        Login = view.findViewById(R.id.login);
        email = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        loading = view.findViewById(R.id.loading);
        Login.setOnClickListener(view1 -> {
            Login.setVisibility(View.INVISIBLE);
            loading.setVisibility(View.VISIBLE);
            //TODO: Data Validation

            // Data Verification
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnSuccessListener(authResult -> Navigation.findNavController(view).navigate(R.id.homeFragment));
            loading.setVisibility(View.INVISIBLE);
            Login.setVisibility(View.VISIBLE);
        });
        return view;
    }
}