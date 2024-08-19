package com.finofo.financialprojection.controller;

import com.finofo.financialprojection.service.FinancialProjectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class FinancialProjectionController {


    @Autowired
    private FinancialProjectionService financialProjectionService;

    @PostMapping("/projections/analyze")
    public List<Map<String, Object>> uploadExcel(@RequestParam("file") MultipartFile file,
                                                 @RequestParam("productSalesGrowth") double productSalesGrowth,
                                                 @RequestParam("serviceSalesGrowth") double serviceSalesGrowth,
                                                 @RequestParam("cogsGrowth") double cogsGrowth,
                                                 @RequestParam("marketingGrowth") double marketingGrowth,
                                                 @RequestParam("staffSalariesGrowth") double staffSalariesGrowth,
                                                 @RequestParam("futureMonths") int futureMonths) throws IOException {
        Map<String, Double> growthRates = parseGrowthRates(productSalesGrowth, serviceSalesGrowth, cogsGrowth, marketingGrowth, staffSalariesGrowth);
        return financialProjectionService.calculateFinancialProjections(file, growthRates, futureMonths);
    }


    private Map<String, Double> parseGrowthRates(double productSalesGrowth, double serviceSalesGrowth, double cogsGrowth, double marketingGrowth, double staffSalariesGrowth) {
        Map<String, Double> growthRates = new HashMap<>();

        growthRates.put("Product Sales", productSalesGrowth / 100);
        growthRates.put("Service Sales", serviceSalesGrowth / 100);
        growthRates.put("Cost of Goods Sold", cogsGrowth / 100);
        growthRates.put("Marketing", marketingGrowth / 100);
        growthRates.put("Staff salaries", staffSalariesGrowth / 100);
        return growthRates;
    }
}
