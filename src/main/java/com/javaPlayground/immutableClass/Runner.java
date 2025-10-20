package com.javaPlayground.immutableClass;

import java.util.Date;

public class Runner {
    public static void main(String[] args) {
        BankDetails bankDetails = new BankDetails("Barclays", 234566, 123423333);
        PersonalDetails personalDetails = new PersonalDetails(1, "Tim");
        ImmutableProfile profile = new ImmutableProfile(3, personalDetails, bankDetails, new Date());

        System.out.println("Before setup any details.");
        System.out.println(profile.getPersonalDetails());
        System.out.println(profile.getBankDetails());

        profile.getPersonalDetails().setName("Mike");
        profile.getBankDetails().setBankName("HSBC");

        System.out.println("\nAfter try to mutate some details.");
        System.out.println(profile.getPersonalDetails());
        System.out.println(profile.getBankDetails());
    }
}
