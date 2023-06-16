package Pharmacy.Action.Sale;

import Pharmacy.Action.*;
import Pharmacy.CommunityPharmacyEmployee;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a sale action in a community pharmacy.
 * A sale is a type of action and can include various documents.
 * It implements the Serializable interface
 */
public class Sale extends Action implements Serializable {

    private String methodOfPayment;
    private List<Document> documents = new ArrayList<>();

    /**
     * Creates a new Sale instance with the specified date and employee
     * @param date
     * @param employee
     */
    public Sale(LocalDate date, CommunityPharmacyEmployee employee) {
        super(date, employee);

    }
    public void setMethodOfPayment(String methodOfPayment) {
        this.methodOfPayment = methodOfPayment;
    }

    /**
     * Creates a new document and adds it to the sale association
     * @param id
     * @param documentType
     * @param description
     */
    public void createDocument(String id, DocumentType documentType, String description){
        this.addDocument(new Document(id, documentType, description));
    }

    /**
     * Adds a new association with Document
     * @param newDocument
     */
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

    /**
     * Represents a document associated with a sale.
     */
    public class Document implements Serializable{

        private final DocumentType documentType;
        private String id;
        private String name;
        private String description;

        /**
         * Creates a new Document instance with the specified ID, document type, and description
         * @param id
         * @param documentType
         * @param description
         */
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
