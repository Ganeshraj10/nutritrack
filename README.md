# NutriTrack Pro

A professional-looking nutrition and wellness dashboard built with Java 21, Spring Boot, Thymeleaf, Spring Data JPA and H2. The codebase is designed for Software Testing and Quality Assurance coursework.

## Features

- Dashboard with daily calorie and macronutrient totals, weekly trend bars, daily goal progress and hydration summary
- Food diary with create, edit, delete, date selection and calculated totals
- Food catalogue seeded with 40 demo foods, search and category filtering; add a catalogue item to today's diary
- Nutrition-goal settings with boundary validation
- Hydration tracking with daily totals and delete support
- Meal planning by date and meal category
- Analytics with seven-day and thirty-day views and CSV export
- Simple settings/privacy information page
- H2 file-backed database for local persistence
- A standalone JUnit 3.8.1 / SwingUI test harness in `junit381/` with five independent Java module test classes; the tests run with `javac` + `junit.swingui.TestRunner`, not `mvn test`
- SRS document, module/screen list, requirements traceability matrix, manual test matrix template, test-summary template and design screenshots under `docs/`

> Educational demonstration only. Nutrition values are illustrative demo data and are not clinical advice. This app is not intended to diagnose, treat or prevent any medical condition. Authentication is not implemented; use only synthetic local demo data.

## Requirements

- JDK 21
- Apache Maven 3.6.3+ (Maven 3.9.x recommended)
- A modern browser (Chrome or Edge recommended)

Spring Boot runs on Java 17 or later; this project targets Java 21. See the official [Spring Boot tutorial](https://docs.spring.io/spring-boot/tutorial/first-application/) and [system requirements](https://docs.spring.io/spring-boot/system-requirements.html).

## Run on Windows (PowerShell)

1. Extract the ZIP.
2. Open the `NutriTrack-Pro` folder in VS Code (the folder containing `pom.xml`).
3. Check your tools:

   ```powershell
   java -version
   mvn -version
   ```

4. Run all JUnit tests before starting the website:

   ```powershell
   mvn clean test
   ```

5. Start the app:

   ```powershell
   mvn spring-boot:run
   ```

6. Open <http://localhost:8080>.

If port 8080 is already in use, start the app on 8081 without editing files:

```powershell
$env:SERVER_PORT=8081
mvn spring-boot:run
```

Then open <http://localhost:8081>. To return to the default port, close that terminal or run `Remove-Item Env:SERVER_PORT`.

## Useful routes

| Page | URL |
|---|---|
| Overview | `/` |
| Food diary | `/diary` |
| Food catalogue | `/foods` |
| Meal planner | `/planner` |
| Nutrition goals | `/goals` |
| Hydration | `/hydration` |
| Analytics | `/analytics` |
| Settings | `/settings` |
| Health endpoint | `/api/health` |
| Daily summary JSON | `/api/summary?date=YYYY-MM-DD` |
| Food catalogue JSON | `/api/foods` |

## Run the five JUnit 3.8.1 module tests using SwingUI (no Maven test command)

The classic JUnit 3.8.1 sources and commands are kept in the `junit381/` folder. Open a terminal in that folder and run:

```bat
download-junit381.cmd
compile.cmd
run-one.cmd FoodDiaryModuleTest
run-one.cmd FoodCatalogueModuleTest
run-one.cmd NutritionGoalModuleTest
run-one.cmd HydrationModuleTest
run-one.cmd NutritionAnalyticsModuleTest
```

Each `run-one.cmd` command opens `junit.swingui.TestRunner`; click **Run** in the GUI. To open all tests together use `run-all.cmd`. To run a text-only report use `run-console.cmd`.

The standalone JUnit 3.8.1 module logic exercises are pure Java and independently compilable. They test the business rules represented by the five modules, but do not start Spring Boot or test the database, HTTP controllers, UI, performance or security. The JUnit 3.8.1 jar is retrieved by `download-junit381.cmd` because it could not be bundled from this build environment. The main Spring Boot website still uses Maven to build and run; only the JUnit test workflow here avoids Maven.

## Database and reset

The H2 database is stored under `./data/`. Application data persists between restarts. To reset the demonstration environment, stop the app and delete the local database files in `data/`, then run the app again; the demo catalogue, goals and starter diary entries will be reseeded. Do not delete the `data/` folder while the application is running.

## Assignment documentation

See `docs/SRS-NutriTrack-Pro.docx` and `docs/SRS-NutriTrack-Pro.md` for the specification, `docs/RTM.csv` for requirement traceability, `docs/Manual-Test-Matrix.csv` for black-box test cases, `docs/Test-Summary-Report-Template.md` for execution closure and `docs/screen-designs/` for conceptual screen design screenshots.

The RTM/test matrix intentionally starts with `Not Executed` statuses. Replace actual-output placeholders and statuses only after you run the application and testing tools. Do not present these templates as completed test evidence.
