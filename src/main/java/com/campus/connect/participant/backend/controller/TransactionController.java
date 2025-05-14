package com.campus.connect.participant.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.campus.connect.participant.backend.model.Transaction;
import com.campus.connect.participant.backend.repository.TransactionRepository;
import com.campus.connect.participant.backend.repository.SocietyRepository;


@RestController
@RequestMapping("/api/budget")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private SocietyRepository societyRepository;

    // Endpoint to add a transaction
    @PostMapping("/add")
    public ResponseEntity<String> addTransaction(@RequestBody Transaction transaction) {
        LocalDate currentDate = LocalDate.now();

        if (!transaction.getTransactionType().equals("income") && !transaction.getTransactionType().equals("expense")) {
            return ResponseEntity.badRequest().body("Invalid transaction type. Must be 'income' or 'expense'.");
        }

        if (transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.badRequest().body("Amount must be greater than zero.");
        }

        if(transaction.getTransactionType().equals("expense")) {
            // Check if the society has enough budget
            double currentBudget = societyRepository.getTotalBudgetOfSociety(transaction.getSocietyId());

            if(currentBudget < transaction.getAmount().doubleValue()) {
                return ResponseEntity.badRequest().body("Insufficient budget for this expense.");
            }
            else
            {
                // Subtract the budget from the society
                societyRepository.subtractBudgetFromSociety(transaction.getSocietyId(), transaction.getAmount().doubleValue());
            }
        }
        else if(transaction.getTransactionType().equals("income")) {
            // Add the budget to the society
            societyRepository.addBudgetToSociety(transaction.getSocietyId(), transaction.getAmount().doubleValue());
        }
        transaction.setId(UUID.randomUUID());
        transactionRepository.addTransaction(transaction.getId(), transaction.getSocietyId(), transaction.getAmount(), transaction.getTransactionType(), transaction.getDescription(), transaction.getTitle(),currentDate);
        return ResponseEntity.ok("Transaction added successfully");
    }

    // Endpoint to get all transactions by society ID
    @GetMapping("/get/{societyId}")
    public ResponseEntity<List<Transaction>> getTransactionsBySocietyId(@PathVariable UUID societyId) {
        List<Transaction> transactions = transactionRepository.getTransactionsBySocietyId(societyId);
        return ResponseEntity.ok(transactions);
    }

    // Endpoint to get all transactions by society ID and transaction type
    @GetMapping("/get/{societyId}/{transactionType}")
    public ResponseEntity<List<Transaction>> getTransactionsBySocietyIdAndType(@PathVariable UUID societyId, @PathVariable String transactionType) {

        if (!transactionType.equals("income") && !transactionType.equals("expense")) {
            return ResponseEntity.badRequest().body(null);
        }

        List<Transaction> transactions = transactionRepository.getTransactionsBySocietyIdAndType(societyId, transactionType);
        return ResponseEntity.ok(transactions);
    }
    
}
