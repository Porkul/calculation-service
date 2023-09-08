package com.ppa.calculationservice;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CalculationService {
    private final List<Calculation> calculations = Collections.synchronizedList(new ArrayList<>());

    public Calculation add(int a, int b) {
        Calculation calculation = new Calculation(a, b, a + b);
        synchronized (calculations) {
            calculations.add(calculation);
        }
        return calculation;
    }

    public List<Calculation> findCalculations(Integer number, String order) {
        List<Calculation> filteredCalculations;
        synchronized (calculations) {
            Stream<Calculation> stream = calculations.stream();
            if (number != null) {
                stream = stream.filter(calc ->
                        calc.a() == number ||
                        calc.b() == number ||
                        calc.sum() == number
                );
            }

            filteredCalculations = stream
                    .sorted(orderingBySum(order))
                    .collect(Collectors.toList());
        }
        return filteredCalculations;
    }

    private Comparator<Calculation> orderingBySum(String order) {
        Comparator<Calculation> comparator = Comparator.comparing(Calculation::sum);
        return "desc".equalsIgnoreCase(order) ? comparator.reversed() : comparator;
    }
}
