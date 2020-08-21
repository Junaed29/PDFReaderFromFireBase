package com.jsc.pdfreader;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.List;


public class PDFListFragment extends Fragment implements PdfListAdapter.PdfListListener {
    private static final String TAG = "PDFListFragment";

    RecyclerView recyclerView;
    PdfListAdapter adapter;
    NavController navController;
    List<PdfModel> pdfModelList;

    int position;

    public PDFListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pdf_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new PdfListAdapter(this);

        setRecyclerView();
    }

    private void setRecyclerView() {
        FirebaseFirestore.getInstance().collection("AllPDF")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            pdfModelList = task.getResult().toObjects(PdfModel.class);
                            Log.d(TAG, "onComplete: " + pdfModelList.get(0).toString());
                            adapter.setPdfModelList(pdfModelList);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                });
    }

    @Override
    public void onItemPDFClickListener(int index) {
        position = index;
        TedPermission.with(getContext())
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }


    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            PDFListFragmentDirections.ActionPDFListFragmentToPdfFragment action = PDFListFragmentDirections.actionPDFListFragmentToPdfFragment();
            action.setPdfName(pdfModelList.get(position).getPdfName());
            action.setPdfUrl(pdfModelList.get(position).getPdfUrl());
            navController.navigate(action);
        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {
            Toast.makeText(getContext(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }

    };
}