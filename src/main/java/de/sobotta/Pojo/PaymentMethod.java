package de.sobotta.Pojo;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "payment")
public class PaymentMethod {
    private String id;
    private String iban;
    private String bic;

    @DBRef
    private Bank bank;

    public PaymentMethod() {
    }

    public PaymentMethod(String iban, String bic, Bank bank) {
        this.iban = iban;
        this.bic = bic;
        this.bank = bank;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }
}

