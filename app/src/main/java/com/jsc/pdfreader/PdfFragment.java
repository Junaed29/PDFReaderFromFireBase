package com.jsc.pdfreader;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.barteksc.pdfviewer.PDFView;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;
import com.tuyenmonkey.mkloader.MKLoader;

import java.io.File;

public class PdfFragment extends Fragment {
    TextView pdfNameTextView;
    PDFView pdfView;
    MKLoader loader;

    String pdfUrl = "", pdfName = "";

    public PdfFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pdf, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pdfName = PdfFragmentArgs.fromBundle(getArguments()).getPdfName();
        pdfUrl = PdfFragmentArgs.fromBundle(getArguments()).getPdfUrl();

        pdfNameTextView = (TextView) view.findViewById(R.id.pdfTitleId);
        pdfNameTextView.setText(pdfName);

        pdfView = view.findViewById(R.id.pdfViewId);
        loader = view.findViewById(R.id.loaderId);
        loader.setVisibility(View.VISIBLE);


        loadPdf();
    }


    private void loadPdf() {
        FileLoader.with(getContext())
                .load(pdfUrl)
                .fromDirectory("PDFFiles", FileLoader.DIR_EXTERNAL_PUBLIC)
                .asFile(new FileRequestListener<File>() {
                    @Override
                    public void onLoad(FileLoadRequest request, FileResponse<File> response) {
                        loader.setVisibility(View.GONE);

                        File file = response.getBody();
                        pdfView.fromFile(file).load();
                    }

                    @Override
                    public void onError(FileLoadRequest request, Throwable t) {
                        Toast.makeText(getContext(), "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        loader.setVisibility(View.GONE);
                    }
                });
    }

}