package com.medicine.reminder.model;

import jakarta.persistence.*;

@Entity
@Table(name = "medicines")
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_name", nullable = false)
    private String patientName;

    @Column(name = "child_email", nullable = false)
    private String childEmail;

    @Column(name = "name", nullable = false)
    private String name;

    private String dosage;

    @Column(name = "timing", nullable = false)
    private String timing;

    private boolean taken = false;

    // స్టాక్ కాలమ్ (Default: 10 pills)
    @Column(name = "stock_count")
    private Integer stockCount = 10;

    // 🔹 కొత్తగా యాడ్ చేసిన కాలమ్స్ (Frequency & Medicine Type)
    @Column(name = "frequency")
    private String frequency;    // Daily, Weekly, Monthly, etc.

    @Column(name = "medicine_type")
    private String medicineType; // Tablet, Tonic, Injection, etc.

    public Medicine() {}

    public Medicine(String patientName, String childEmail, String name, String dosage, String timing, boolean taken, Integer stockCount, String frequency, String medicineType) {
        this.patientName = patientName;
        this.childEmail = childEmail;
        this.name = name;
        this.dosage = dosage;
        this.timing = timing;
        this.taken = taken;
        this.stockCount = stockCount;
        this.frequency = frequency;
        this.medicineType = medicineType;
    }
    @Column(name = "instructions")
    private String instructions; // e.g., "10 ml" or "20 mg"

    @Column(name = "food_interaction")
    private String foodInteraction; // "Before Food" or "After Food"


    // Getters & Setters
    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }

    public String getFoodInteraction() { return foodInteraction; }
    public void setFoodInteraction(String foodInteraction) { this.foodInteraction = foodInteraction; }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getChildEmail() { return childEmail; }
    public void setChildEmail(String childEmail) { this.childEmail = childEmail; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDosage() { return dosage; }
    public void setDosage(String dosage) { this.dosage = dosage; }

    public String getTiming() { return timing; }
    public void setTiming(String timing) { this.timing = timing; }

    public boolean isTaken() { return taken; }
    public void setTaken(boolean taken) { this.taken = taken; }

    public Integer getStockCount() { return stockCount; }
    public void setStockCount(Integer stockCount) { this.stockCount = stockCount; }

    public String getFrequency() { return frequency; }
    public void setFrequency(String frequency) { this.frequency = frequency; }

    public String getMedicineType() { return medicineType; }
    public void setMedicineType(String medicineType) { this.medicineType = medicineType; }
}