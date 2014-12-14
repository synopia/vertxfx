package org.synopia.vertxfx.contact;

import org.synopia.vertxfx.persist.PK;

import java.io.Serializable;

/**
 * @author synopia
 */
public class Contact implements Serializable {
    private PK<Contact> id = new PK<>();
    private String name;

    private String street;
    private String zipCode;
    private String country;

    public Contact() {
    }

    public Contact(String name, String street, String zipCode, String country) {
        this.name = name;
        this.street = street;
        this.zipCode = zipCode;
        this.country = country;
    }

    public PK<Contact> getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact)) return false;

        Contact contact = (Contact) o;

        if (country != null ? !country.equals(contact.country) : contact.country != null) return false;
        if (name != null ? !name.equals(contact.name) : contact.name != null) return false;
        if (street != null ? !street.equals(contact.street) : contact.street != null) return false;
        if (zipCode != null ? !zipCode.equals(contact.zipCode) : contact.zipCode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (zipCode != null ? zipCode.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }
}
