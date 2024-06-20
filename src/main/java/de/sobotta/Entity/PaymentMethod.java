package de.sobotta.Entity;

public class PaymentMethod {

    private String iban;
    private String bic;
    private Bank bank;

    // Getter und Setter

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

    @Override
    public String toString() {
        return "PaymentMethod{" +
                "iban='" + iban + '\'' +
                ", bic='" + bic + '\'' +
                ", bank=" + bank +
                '}';
    }
}
