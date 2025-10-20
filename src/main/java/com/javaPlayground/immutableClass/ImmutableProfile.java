package com.javaPlayground.immutableClass;

import java.util.Date;

public final class ImmutableProfile {
    private final  int id;
    private final  PersonalDetails personalDetails; // mutable
    private final BankDetails bankDetails; // mutable
    private final Date doj; // mutable

    public int getId() {
        return id;
    }

    public PersonalDetails getPersonalDetails() {
        return new PersonalDetails(personalDetails.getId(), personalDetails.getName());
    }

    public BankDetails getBankDetails() {
        return new BankDetails(bankDetails.getBankName(), bankDetails.getSortCode(), bankDetails.getAccount());
    }

    public Date getDoj() {
        return (Date) doj.clone();
    }

    public ImmutableProfile(int id, PersonalDetails personalDetails, BankDetails bankDetails, Date doj) {
        this.id = id;
        this.personalDetails = personalDetails;
        this.bankDetails = bankDetails;
        this.doj = doj;
    }

    @Override
    public String toString() {
        return "ImmutableProfile{" +
                "id=" + id +
                ", personalDetails=" + personalDetails +
                ", bankDetails=" + bankDetails +
                ", doj=" + doj +
                '}';
    }
}
