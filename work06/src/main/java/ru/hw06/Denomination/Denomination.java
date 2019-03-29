package ru.hw06.Denomination;

public class Denomination implements IDenomination {

    private int nominal;
    private String nominalName;

    public Denomination() {
        this.nominal = 0;
        this.nominalName = "";
    }

    public Denomination(int nominal, String nominalName) {
        this.nominal = nominal;
        this.nominalName = nominalName;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public String getNominalName() {
        return nominalName;
    }

    public void setNominalName(String nominalName) {
        this.nominalName = nominalName;
    }
}

