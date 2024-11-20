package itaf.tools.utils;

import java.nio.file.*;
import java.io.IOException;
import java.util.*;
import java.util.regex.*;

public class FeatureFileScanner {
    private static final String TAG_PATTERN = "@tc\\d+";
    private static final String SCENARIO_PATTERN = "^(Scenario|Scenario Outline):";

    public static Set<String> getTags(String featureDirectory) throws IOException {
        Set<String> tags = new HashSet<>();
        Pattern pattern = Pattern.compile(TAG_PATTERN);

        Files.walk(Paths.get(featureDirectory))
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".feature"))
                .forEach(path -> {
                    try {
                        List<String> lines = Files.readAllLines(path);
                        for (String line : lines) {
                            Matcher matcher = pattern.matcher(line);
                            while (matcher.find()) {
                                tags.add(matcher.group());
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        return tags;
    }

    public static List<String> getUntaggedScenarios(String featureDirectory) throws IOException {
        List<String> untaggedScenarios = new ArrayList<>();
        Pattern scenarioPattern = Pattern.compile(SCENARIO_PATTERN);
        Pattern tagPattern = Pattern.compile(TAG_PATTERN);

        Files.walk(Paths.get(featureDirectory))
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".feature"))
                .forEach(path -> {
                    try {
                        List<String> lines = Files.readAllLines(path);
                        boolean hasTag = false;
                        String currentScenario = null;

                        for (String line : lines) {
                            line = line.trim(); // Trim whitespace
                            if (tagPattern.matcher(line).find()) {
                                hasTag = true; // Line has a tag
                            }

                            // Check for scenario definitions
                            Matcher matcher = scenarioPattern.matcher(line);
                            if (matcher.find()) {
                                // If a previous scenario is found without a tag, add it to the list
                                if (currentScenario != null && !hasTag) {
                                    untaggedScenarios.add("File: " + path.getFileName() + " - " + currentScenario);
                                }

                                // Capture the current scenario name
                                currentScenario = line; // Capture only the scenario line
                                hasTag = false; // Reset tag flag for the new scenario
                            }
                        }

                        // Check for the last scenario in the file
                        if (currentScenario != null && !hasTag) {
                            untaggedScenarios.add("File: " + path.getFileName() + " - " + currentScenario);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        return untaggedScenarios;
    }
}
