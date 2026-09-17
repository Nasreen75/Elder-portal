package com.medicine.reminder.service;

import com.medicine.reminder.model.History;
import com.medicine.reminder.model.Medicine;
import com.medicine.reminder.repository.HistoryRepository;
import com.medicine.reminder.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class ReminderScheduler {

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private EmailService emailService;

    // ప్రతి 30 సెకన్లకు చెక్ చేస్తుంది
    @Scheduled(fixedRate = 30000)
    public void checkPendingMedicines() {
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        List<Medicine> medicines = medicineRepository.findAll();
        for (Medicine med : medicines) {
            if (!med.isTaken() && med.getTiming() != null && !med.getTiming().isEmpty()) {
                try {
                    LocalTime scheduledTime = LocalTime.parse(med.getTiming(), formatter);

                    // Scheduled time కి 15 నిమిషాలు యాడ్ చేయండి
                    LocalTime escalationTime = scheduledTime.plusMinutes(15);

                    // సమయం 15 నిమిషాల దాటినా (now >= escalationTime) మందు వేసుకోకపోతేనే అలర్ట్ వెళ్తుంది
                    if (now.isAfter(escalationTime) || now.equals(escalationTime)) {

                        emailService.sendMissedAlert(
                                med.getChildEmail(),
                                med.getPatientName(),
                                med.getName(),
                                med.getTiming()
                        );

                        // Audit Log లో నమోదు చేయడం
                        historyRepository.save(new History(
                                med.getPatientName(),
                                med.getName(),
                                "URGENT ESCALATION ALERT SENT (15+ Min Delayed)",
                                LocalDateTime.now()
                        ));
                    }
                } catch (Exception e) {
                    System.err.println("Error parsing time for medicine " + med.getName() + ": " + e.getMessage());
                }
            }
        }
    }
}