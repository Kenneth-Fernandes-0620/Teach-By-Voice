package com.rosarycollege.teachbyvoice;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    private View thisView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        thisView = inflater.inflate(R.layout.fragment_home, container, false);
        return thisView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (FirebaseAuth.getInstance().getCurrentUser() == null)
            signin();
        else displayData();
    }

    private void displayData() {
        ArrayList<String> s = new ArrayList<>();
        getDataFromServer();
        s.add("Hello");
        s.add("world");
        initRecyclerView(s);
    }

    private void getDataFromLocal() {
        Source source = Source.CACHE;
    }


    private void getDataFromServer() {
        try {
            String email = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
            if(email == null)
                throw new NullPointerException();
            FirebaseFirestore.getInstance().collection("Users").document(email)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (Map.Entry<String, Object> result : Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getData()).entrySet())
                                Log.d(TAG, " " + result.getKey() + " : " + result.getValue());
                        } else
                            Log.w(TAG, "Error getting documents.", task.getException());
                    });
        } catch (NullPointerException np) {
            Log.d(TAG, "getDataFromServer: "+ np);
        }
    }

    public void signin() {
        Navigation.findNavController(thisView).navigate(R.id.loginFragment);
    }

    private void initRecyclerView(ArrayList<String> list) {
        Log.d(TAG, "initRecyclerView:");
        RecyclerView recyclerView = thisView.findViewById(R.id.RecyclerView);
        recyclerViewAdapter adapter = new recyclerViewAdapter(list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(thisView.getContext()));
    }
}