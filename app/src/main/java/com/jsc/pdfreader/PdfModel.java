package com.jsc.pdfreader;

import com.google.firebase.firestore.DocumentId;

public class PdfModel {

    @DocumentId
    String documentId;
    String pdfName;
    String pdfUrl;



    public PdfModel(String pdfName, String pdfUrl) {
        this.pdfName = pdfName;
        this.pdfUrl = pdfUrl;
    }

    public PdfModel() {
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getPdfName() {
        return pdfName;
    }

    public void setPdfName(String pdfName) {
        this.pdfName = pdfName;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    @Override
    public String toString() {
        return "PdfModel{" +
                "documentId='" + documentId + '\'' +
                ", pdfName='" + pdfName + '\'' +
                ", pdfUrl='" + pdfUrl + '\'' +
                '}';
    }
}
