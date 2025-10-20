package com.javaPlayground.functionalInterfaces;

public class EmailService {
    public void send(String to, String subject, String body) {
        // Simulate sending an email
        System.out.println("Sending email to: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
        System.out.println("Email sent successfully!\n");
    }
}

