package de.sobotta.Entity;

public class Bank {

    private String name;
    private String area;

    // Getter und Setter

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
        return "Bank{" +
                "name='" + name + '\'' +
                ", area='" + area + '\'' +
                '}';
    }
}
