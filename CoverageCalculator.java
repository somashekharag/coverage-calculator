//package com.itaf.core.util;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//public class CoverageCalculator {
//    public static void main(String[] args) throws IOException {
//        String excelFilePath = "D:\\SELENIUM-4\\ITAF-2.0\\itaf-aut\\src\\main\\resources\\test_cases.xlsx";
//        String featureDirectory = "D:\\SELENIUM-4\\ITAF-2.0\\itaf-aut\\src\\main\\resources\\features";
//
//        Map<String, TestCaseReader.TestCaseInfo> testCases = TestCaseReader.getTestCases(excelFilePath);
//        Set<String> automatedTags = FeatureFileScanner.getTags(featureDirectory);
//        List<String> untaggedScenarios = FeatureFileScanner.getUntaggedScenarios(featureDirectory);
//
//        int totalTestCases = testCases.size();
//        int totalAutomatable = 0;
//        int automatedCount = 0;
//        int nonAutomatableCount = 0;
//        int notYetAutomated = 0;
//
//        List<String> pendingTestCaseNames = new ArrayList<>(); // To store names of pending automation test cases
//
//        for (Map.Entry<String, TestCaseReader.TestCaseInfo> entry : testCases.entrySet()) {
//            String testCaseId = entry.getKey();
//            TestCaseReader.TestCaseInfo info = entry.getValue();
//            boolean isAutomatable = info.isAutomatable();
//
//            if (isAutomatable) {
//                totalAutomatable++;
//                if (automatedTags.contains(testCaseId)) {
//                    automatedCount++;
//                } else {
//                    notYetAutomated++;
//                    pendingTestCaseNames.add(info.getName()); // Add the name of the test case
//                }
//            } else {
//                nonAutomatableCount++;
//            }
//        }
//
//        // Calculate metrics
//        double automationCoverage = (double) automatedCount / totalAutomatable * 100;
//        double automationGapPercentage = (double) notYetAutomated / totalAutomatable * 100;
//
//        // Print results
//        System.out.printf("Total Test Cases: %d%n", totalTestCases);
//        System.out.printf("Automatable Test Cases: %d%n", totalAutomatable);
//        System.out.printf("Automated Test Cases: %d%n", automatedCount);
//        System.out.printf("Non-Automatable Test Cases: %d%n", nonAutomatableCount);
//        System.out.printf("Not Yet Automated: %d%n", notYetAutomated);
//        System.out.printf("Automation Coverage: %.2f%% (%d/%d)%n", automationCoverage, automatedCount, totalAutomatable);
//        System.out.printf("Automation Gap Percentage: %.2f%%%n", automationGapPercentage);
//
//        // Display untagged scenarios
//        System.out.println("\nUntagged Scenarios:");
//        for (String scenario : untaggedScenarios) {
//            System.out.println(scenario);
//        }
//
//        // Display pending automation test case names
//        System.out.println("\nPending Automation Test Cases:");
//        for (String name : pendingTestCaseNames) {
//            System.out.println(name);
//        }
//    }
//}
