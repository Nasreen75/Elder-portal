package com.medicine.reminder.controller;

import com.medicine.reminder.model.History;
import com.medicine.reminder.model.Medicine;
import com.medicine.reminder.repository.HistoryRepository;
import com.medicine.reminder.repository.MedicineRepository;
import com.medicine.reminder.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping
@CrossOrigin(origins = "*")
public class MedicineController {

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private EmailService emailService;

    // 0. Redirect root URL to login.html
    @GetMapping("/")
    public ModelAndView redirectToLogin() {
        return new ModelAndView("redirect:/login.html");
    }

    // 1. Get all active medicines
    @GetMapping("/api/medicines")
    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }

    // 2. Get all audit history logs
    @GetMapping("/api/medicines/history")
    public List<History> getHistoryLogs() {
        return historyRepository.findAllByOrderByIdDesc();
    }

    // 3. Create new medicine with default values
    @PostMapping("/api/medicines")
    public Medicine createMedicine(@RequestBody Medicine medicine) {
        if (medicine.getStockCount() == null) {
            medicine.setStockCount(10);
        }
        if (medicine.getFrequency() == null) {
            medicine.setFrequency("Daily");
        }
        if (medicine.getMedicineType() == null) {
            medicine.setMedicineType("Tablet");
        }
        if (medicine.getFoodInteraction() == null) {
            medicine.setFoodInteraction("After Food");
        }
        return medicineRepository.save(medicine);
    }

    // 4. Toggle Taken status, update stock & log history
    @PutMapping("/api/medicines/{id}/toggle")
    public ResponseEntity<Medicine> toggleStatus(@PathVariable Long id) {
        return medicineRepository.findById(id).map(medicine -> {
            boolean wasTaken = medicine.isTaken();
            medicine.setTaken(!wasTaken);

            if (!wasTaken) {
                int currentStock = (medicine.getStockCount() != null) ? medicine.getStockCount() : 10;
                if (currentStock > 0) {
                    medicine.setStockCount(currentStock - 1);
                }

                // History Log నమోదు చేయడం
                historyRepository.save(new History(
                        medicine.getPatientName(),
                        medicine.getName(),
                        "MARKED AS TAKEN",
                        LocalDateTime.now()
                ));

                // Refill Alert Check
                if (medicine.getStockCount() <= 3) {
                    emailService.sendRefillAlert(
                            medicine.getChildEmail(),
                            medicine.getPatientName(),
                            medicine.getName(),
                            medicine.getStockCount()
                    );
                }
            }

            Medicine updated = medicineRepository.save(medicine);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    // 5. Update medicine details (including frequency, dosage, type, instructions, food interaction)
    @PutMapping("/api/medicines/{id}")
    public ResponseEntity<Medicine> updateMedicine(@PathVariable Long id, @RequestBody Medicine updatedDetails) {
        return medicineRepository.findById(id).map(medicine -> {
            medicine.setName(updatedDetails.getName());
            medicine.setPatientName(updatedDetails.getPatientName());
            medicine.setTiming(updatedDetails.getTiming());
            medicine.setChildEmail(updatedDetails.getChildEmail());
            medicine.setDosage(updatedDetails.getDosage());

            if (updatedDetails.getStockCount() != null) {
                medicine.setStockCount(updatedDetails.getStockCount());
            }
            if (updatedDetails.getFrequency() != null) {
                medicine.setFrequency(updatedDetails.getFrequency());
            }
            if (updatedDetails.getMedicineType() != null) {
                medicine.setMedicineType(updatedDetails.getMedicineType());
            }
            if (updatedDetails.getInstructions() != null) {
                medicine.setInstructions(updatedDetails.getInstructions());
            }
            if (updatedDetails.getFoodInteraction() != null) {
                medicine.setFoodInteraction(updatedDetails.getFoodInteraction());
            }

            Medicine saved = medicineRepository.save(medicine);
            return ResponseEntity.ok(saved);
        }).orElse(ResponseEntity.notFound().build());
    }

    // 6. Delete medicine
    @DeleteMapping("/api/medicines/{id}")
    public ResponseEntity<Void> deleteMedicine(@PathVariable Long id) {
        if (medicineRepository.existsById(id)) {
            medicineRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}