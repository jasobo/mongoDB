package de.sobotta.DTO;

public class BankDTO {
    private String name;
    private String area;

    public BankDTO() {
    }

    public BankDTO(String name, String area) {
        this.name = name;
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "BankDTO{" +
                "bankName='" + name + '\'' +
                ", bankArea='" + area + '\'' +
                '}';
    }
}
