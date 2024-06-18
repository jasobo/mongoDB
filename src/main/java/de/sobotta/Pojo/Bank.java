package de.sobotta.Pojo;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Bank {
    private String id;
    private String name;
    private String area;

    public Bank() {
    }

    public Bank(String name, String area) {
        this.name = name;
        this.area = area;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
