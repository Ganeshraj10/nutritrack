# NutriTrack Pro — Classic JUnit 3.8.1 / SwingUI tests

This folder contains standalone Java classes for five NutriTrack business areas and tests written in the **JUnit 3.8.1 style** (`extends junit.framework.TestCase`, `public void test...()` methods). These run in the classic Swing test runner, **not through `mvn test`**.

## Important distinction

The standalone logic classes in `src/nutritrack/core` are plain-Java exercises for the five business rules (Food Diary, Food Catalogue, Nutrition Goals, Hydration, Analytics). They can be compiled and tested directly with `javac` and JUnit 3.8.1. They do not start Spring Boot or call the web app's H2 database/controllers, so these are module-level logic tests rather than integration, UI, security, or performance tests.

## 1. Requirements

- JDK 8 or later (JDK 21 is fine)
- Windows PowerShell / Command Prompt
- Internet connection once to download the official `junit-3.8.1.jar`

JUnit 3.8.1 is a legacy framework. The jar is not pre-packaged here because the build environment could not download the binary. Use the included `download-junit381.cmd`, which attempts the official Maven Central artifact first and a public Maven mirror as a fallback. Official artifact details: https://central.sonatype.com/artifact/junit/junit/3.8.1

## 2. Download the JUnit 3.8.1 jar

Open PowerShell or Command Prompt in this folder and run:

```bat
download-junit381.cmd
```

This saves the library as `lib\junit-3.8.1.jar`. No Maven command is used to run these tests.

## 3. Compile the Java code

```bat
compile.cmd
```

The script compiles the five module logic classes and test classes with `javac`, outputting class files into `out`. It requires `lib\junit-3.8.1.jar` from Step 2.

## 4. Open SwingUI for each module

Run each command separately from this folder:

```bat
run-one.cmd FoodDiaryModuleTest
run-one.cmd FoodCatalogueModuleTest
run-one.cmd NutritionGoalModuleTest
run-one.cmd HydrationModuleTest
run-one.cmd NutritionAnalyticsModuleTest
```

The command launches `junit.swingui.TestRunner` with the selected test class. Click **Run** in the SwingUI window to execute the tests and inspect failures.

To open one SwingUI window for the full set:

```bat
run-all.cmd
```

For terminal-only output (without a Swing window):

```bat
run-console.cmd
```

## 5. Screenshot evidence

Capture the SwingUI window for each individual module. Make sure the window shows the test class and the result after clicking **Run**. Save your actual screenshots under `docs/test-evidence/` in the project if needed. Do not copy sample numbers into the report; enter the counts shown by your own test run.

## Included suites

| Module | JUnit class | Covered behaviour |
|---|---|---|
| Food Diary | `FoodDiaryModuleTest` | input validation, add/delete, date-specific entries and calorie totals |
| Food Catalogue | `FoodCatalogueModuleTest` | search, case-insensitive matching, category filtering and sorting |
| Nutrition Goals | `NutritionGoalModuleTest` | boundaries, validation, goal progress and defaults reset |
| Hydration | `HydrationModuleTest` | intake validation, date-specific totals, progress and deletion |
| Nutrition Analytics | `NutritionAnalyticsModuleTest` | daily totals, inclusive date ranges, averages and min/max days |

No Maven `test` command is required for the standalone JUnit 3.8.1 test workflow. The main Spring Boot website itself still needs its usual build/runtime dependencies when you want to run the web application.
