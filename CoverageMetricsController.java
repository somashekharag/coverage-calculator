package itaf.tools.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import itaf.tools.utils.FeatureFileScanner;
import itaf.tools.utils.TestCaseReader;

import java.io.IOException;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/coverage")

public class CoverageMetricsController {
    @GetMapping
    public Map<String, Object> getCoverageMetrics() {
        String excelFilePath = "D:\\SELENIUM-4\\ITAF-2.0\\itaf-aut\\src\\main\\resources\\test_cases.xlsx";
        String featureDirectory = "D:\\SELENIUM-4\\ITAF-2.0\\itaf-aut\\src\\main\\resources\\features";

        Map<String, Object> response = new HashMap<>();

        try {
            Map<String, TestCaseReader.TestCaseInfo> testCases = TestCaseReader.getTestCases(excelFilePath);
            Set<String> automatedTags = FeatureFileScanner.getTags(featureDirectory);
            List<String> untaggedScenarios = FeatureFileScanner.getUntaggedScenarios(featureDirectory);

            int totalTestCases = testCases.size();
            int totalAutomatable = 0;
            int automatedCount = 0;
            int nonAutomatableCount = 0;
            int notYetAutomated = 0;
            List<String> pendingTestCaseNames = new ArrayList<>();

            for (Map.Entry<String, TestCaseReader.TestCaseInfo> entry : testCases.entrySet()) {
                String testCaseId = entry.getKey();
                TestCaseReader.TestCaseInfo info = entry.getValue();
                boolean isAutomatable = info.isAutomatable();

                if (isAutomatable) {
                    totalAutomatable++;
                    if (automatedTags.contains(testCaseId)) {
                        automatedCount++;
                    } else {
                        notYetAutomated++;
                        pendingTestCaseNames.add(info.getName());
                    }
                } else {
                    nonAutomatableCount++;
                }
            }

            // Calculate metrics
            double automationCoverage = totalAutomatable > 0 ? (double) automatedCount / totalAutomatable * 100 : 0;
            double automationGapPercentage = totalAutomatable > 0 ? (double) notYetAutomated / totalAutomatable * 100 : 0;

            // Populate response
            response.put("totalTestCases", totalTestCases);
            response.put("automatableTestCases", totalAutomatable);
            response.put("automatedTestCases", automatedCount);
            response.put("nonAutomatableTestCases", nonAutomatableCount);
            response.put("notYetAutomated", notYetAutomated);
            response.put("automationCoverage", automationCoverage);
            response.put("automationGapPercentage", automationGapPercentage);
            response.put("untaggedScenarios", untaggedScenarios);
            response.put("pendingAutomationTestCases", pendingTestCaseNames);

        } catch (IOException e) {
            response.put("error", "An error occurred while processing the metrics: " + e.getMessage());
        }

        return response;
    }
}
