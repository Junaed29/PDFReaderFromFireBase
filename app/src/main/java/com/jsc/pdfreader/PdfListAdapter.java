package com.jsc.pdfreader;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PdfListAdapter extends RecyclerView.Adapter<PdfListAdapter.PdfViewHolder> {

    private List<PdfModel> pdfModelList;
    private PdfListListener pdfListListener;

    public PdfListAdapter(PdfListListener pdfListListener) {
        this.pdfListListener = pdfListListener;
    }

    public void setPdfModelList(List<PdfModel> pdfModelList) {
        this.pdfModelList = pdfModelList;
    }

    @NonNull
    @Override
    public PdfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pdf_item, parent, false);

        return new PdfViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PdfViewHolder holder, int position) {
        holder.pdfNameTextView.setText(pdfModelList.get(position).getPdfName());
    }

    @Override
    public int getItemCount() {
        return pdfModelList.size();
    }

    public class PdfViewHolder extends RecyclerView.ViewHolder{
        TextView pdfNameTextView;
        public PdfViewHolder(@NonNull View itemView) {
            super(itemView);
            pdfNameTextView = (TextView) itemView.findViewById(R.id.pdfItem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pdfListListener.onItemPDFClickListener(getAdapterPosition());
                }
            });
        }
    }

    interface  PdfListListener{
        void onItemPDFClickListener(int index);
    }
}
