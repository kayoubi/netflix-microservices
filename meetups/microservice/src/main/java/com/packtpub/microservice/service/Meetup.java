package com.packtpub.microservice.service;

/**
 * @author khaled
 */
public class Meetup {
    private String name;
    private String typez;

    public Meetup() {}

    public Meetup(String name, String typez) {
        super();
        this.name = name;
        this.typez = typez;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypez() {
        return typez;
    }

    public void setTypez(String typez) {
        this.typez = typez;
    }

    @Override
    public String toString() {
        return "Meetup{" +
            "name='" + name + '\'' +
            ", typez='" + typez + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Meetup meetup = (Meetup) o;

        if (!name.equals(meetup.name)) return false;
        return typez.equals(meetup.typez);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + typez.hashCode();
        return result;
    }
}
