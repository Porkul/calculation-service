package com.ppa.calculationservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CalculationController {
    private final CalculationService calculationService;

    @Autowired
    public CalculationController(CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @GetMapping("/add")
    public Calculation add(@RequestParam int a, @RequestParam int b) {
        ValidationUtils.validateNumbers(a, b);
        return calculationService.add(a, b);
    }

    @GetMapping("/find")
    public List<Calculation> findCalculations(@RequestParam(required = false) Integer number,
                                              @RequestParam String order) {
        if (number != null) {
            ValidationUtils.validateNumbers(number);
        }
        return calculationService.findCalculations(number, order.trim());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
