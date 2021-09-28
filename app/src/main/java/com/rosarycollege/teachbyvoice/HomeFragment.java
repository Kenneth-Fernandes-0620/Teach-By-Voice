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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.rosarycollege.utility.recyclerViewAdapter;

import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {
    private View view;
    private final String storageBucketReference = "gs://teach-by-voice-51f86.appspot.com";
    private static final String TAG = "HomeFragment";
    private SwipeRefreshLayout refreshLayout;
    private String collegeId;
    private long items;
    private recyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (FirebaseAuth.getInstance().getCurrentUser() == null)
            signIn();
        else asynchronousGetDataLoadingFromServer();
    }


    private void getDataFromLocal() {
        Source source = Source.CACHE;
    }


    private void asynchronousGetDataLoadingFromServer() {
        try {
            FirebaseFirestore.getInstance().collection("users").document(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            collegeId = (String) Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getData()).get("collegeId"));
                            loadCollegeFiles();
                        } else
                            Log.d(TAG, "Error getting documents.", task.getException());
                    });
        } catch (NullPointerException np) {
            Log.d(TAG, "getDataFromServer: " + np);
        }
    }


    public void loadCollegeFiles() {
        FirebaseStorage.getInstance(storageBucketReference).getReference().child(collegeId)
                .listAll().addOnSuccessListener(listResult -> {
            List<StorageReference> itemList = listResult.getItems();
            this.items = itemList.size();
            initRecyclerView(itemList);
        });
    }

    public void getCollegeFilesList() {
        FirebaseStorage.getInstance(storageBucketReference).getReference().child(collegeId)
                .listAll().addOnSuccessListener(listResult -> {
            long count = listResult.getItems().size();
            refreshLayout.setRefreshing(false);
            Log.d(TAG, "getCollegeFilesList new: " + count);
            Log.d(TAG, "getCollegeFilesList initial: " + items);
            if (count > items) {
                adapter.refreshData(listResult.getItems(),(int)items,(int)count-1);
                items = count;
            }
        }).addOnFailureListener(e -> refreshLayout.setRefreshing(false));

    }

    public void signIn() {
        Navigation.findNavController(view).navigate(R.id.loginFragment);
    }

    private void initRecyclerView(List<StorageReference> itemList) {
        Log.d(TAG, "initRecyclerView:");
        RecyclerView recyclerView = view.findViewById(R.id.RecyclerView);
        adapter = new recyclerViewAdapter(itemList, ((MainActivity) requireActivity()).getPlayer());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        refreshLayout.setOnRefreshListener(this::getCollegeFilesList);
    }
}