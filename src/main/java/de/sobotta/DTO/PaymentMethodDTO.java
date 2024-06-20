package de.sobotta.DTO;

public class PaymentMethodDTO {
    private String iban;
    private String bic;
    private BankDTO bank;

    public PaymentMethodDTO() {
    }

    public PaymentMethodDTO(String iban, String bic, BankDTO bank) {
        this.iban = iban;
        this.bic = bic;
        this.bank = bank;
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

    public BankDTO getBank() {
        return bank;
    }

    public void setBank(BankDTO bank) {
        this.bank = bank;
    }

    @Override
    public String toString() {
        return "PaymentMethodDTO{" +
                "iban='" + iban + '\'' +
                ", bic='" + bic + '\'' +
                ", bank=" + bank +
                '}';
    }
}
