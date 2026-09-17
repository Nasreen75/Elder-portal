package com.medicine.reminder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired(required = false)
    private JavaMailSender mailSender;

    public void sendMissedAlert(String toEmail, String patientName, String medicineName, String timing) {
        if (mailSender == null) {
            System.err.println("❌ MailSender is not configured. Check pom.xml or application.properties.");
            return;
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("🚨 URGENT: Missed Medicine Alert for " + patientName);
            message.setText("Hello Caregiver,\n\n"
                    + "This is an automated urgent alert from CareGiver Smart System.\n\n"
                    + patientName + " has NOT taken their medicine: " + medicineName
                    + " scheduled for " + timing + ".\n\n"
                    + "Please check on them immediately.\n\n"
                    + "Regards,\nCareGiver Smart Health Team");

            mailSender.send(message);
            System.out.println("✅ REAL EMAIL SUCCESSFULLY SENT TO: " + toEmail);
        } catch (Exception e) {
            System.err.println("❌ Failed to send email: " + e.getMessage());
        }
    }

    public void sendRefillAlert(String toEmail, String patientName, String medicineName, int remainingStock) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("⚠️ REFILL ALERT: Medicine Stock Running Low!");
        message.setText("Hello,\n\nThis is an automated stock alert for " + patientName + ".\n\n"
                + "Medicine: " + medicineName + "\n"
                + "Remaining Stock: " + remainingStock + " pills left.\n\n"
                + "Please refill or re-order this medicine soon to avoid missing doses!\n\n"
                + "Regards,\nElder Caregiver Network");

        try {
            mailSender.send(message);
            System.out.println("📦 REFILL EMAIL SENT TO: " + toEmail);
        } catch (Exception e) {
            System.err.println("❌ Failed to send refill email: " + e.getMessage());
        }
    }
}