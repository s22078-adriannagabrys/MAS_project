package Pharmacy.Action.Sale;

import Pharmacy.Action.*;
import Pharmacy.ObjectPlus;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Sale extends Action implements Serializable {

    private String methodOfPayment;
    private List<Document> documents = new ArrayList<>();

    public Sale(int id, LocalDate date) {
        super(id, date);
    }
    public void setMethodOfPayment(String methodOfPayment) {
        this.methodOfPayment = methodOfPayment;
    }

    public void createDocument(String id, DocumentType documentType, String description){
        this.addDocument(new Document(id, documentType, description));
    }

    public void addDocument(Document newDocument) {
        if(!documents.contains(newDocument)) {
            documents.add(newDocument);
        }
    }
    public List<Document> getDocuments(){
        return documents;
    }

    @Override
    public String toString() {
        String info = "Sale: " + "\n";
        String documentsInfo = " ";

        for(Document document : documents) {
            documentsInfo += "   Document: " + document.id + " - " +  document + "\n";
        }

        return documents.isEmpty() ? info : info + documentsInfo;
    }
    public enum DocumentType {
        INVOICE("Invoice"),
        PAYMENTCONFIRMATION("Payment confirmation");
        public String label;
        private DocumentType(String label){
            this.label = label;
        }
        public String getLabel(){
            return label;
        }

    }
    public class Document implements Serializable{

        private final DocumentType documentType;
        private String id;
        private String name;
        private String description;

        public Document(String id, DocumentType documentType, String description) {
            this.id = id;
            this.documentType = documentType;
            this.description = description;
        }


        @Override
        public String toString() {
            String info = "Document " + id + " ";
            return info += documentType.getLabel() + description;
        }
    }
}
