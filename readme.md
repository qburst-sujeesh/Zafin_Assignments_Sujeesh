# University API Test Automation Suite

Automated API test framework built with **Java**, **REST Assured**, and **TestNG** for testing against mock endpoints stood up with **Stoplight Prism** using `University.yml`. Includes detailed test execution reporting via **ExtentReports** and **Allure**.

---

##  Features & Coverage

- **Mock Server Integration**: Validates OpenAPI contract mock behaviors using Stoplight Prism.
- **Authentication**: Supports global header-based `api_key` injection (`f3c84cbb-1f9a-4b87-bb5b-2d1691b24e1e`).
- **Happy Path Tests (200 OK)**:
  - Full path and HTTP method validation across `/universities` and `/universities/{id}`.
- **Negative & Edge Cases**:
  - **422 Unprocessable Entity**: Invalid or missing required body parameters.
  - **401 Unauthorized**: Missing or invalid `api_key` headers.
  - **404 Server Mismatch**: Missing auth combined with the `__server` query parameter override.
  - **404 URL Path Errors**: Non-existent API routes.
  - **Unencoded Parameters**: Disabling REST Assured automatic URL encoding to test raw server parameter parsing.
- **Rich Reporting**:
  - TestNG Execution Reports (`target/surefire-reports`)
  - ExtentReports Interactive HTML (`target/ExtentReport.html`)
  - Allure Results and Live Dashboard (`target/allure-results`)

---

##  Prerequisites

Make sure the following tools are installed:

- **Java JDK**: 11 or higher
- **Apache Maven**: 3.8+
- **Node.js & npm** (to run Stoplight Prism):
  ```bash
  npm install -g @stoplight/prism-cli
  ```
- **Allure CLI** (optional, for viewing Allure reports locally):
  ```bash
  # macOS (Homebrew)
  brew install allure

  # Linux / Windows (Scoop/NPM)
  npm install -g allure-commandline
  ```

---

## Setup

### 1. Start the Prism Mock Server

From the directory containing `University.yml` (the project root or `university-api-tests` folder), start the mock server on port `4010`:

```bash
prism mock University.yml -p 4010
```

Prism will output confirmation that endpoints are listening on `http://127.0.0.1:4010`.

---

### 2. Execute the Test Suite

Open a new terminal session, navigate to the `university-api-tests` module folder, and run:

```bash
mvn clean test
```

#### Custom Base URL (Optional)
If your Prism instance runs on a different port or host, pass the `base.url` system property:

```bash
mvn clean test -Dbase.url=http://127.0.0.1:4010
```

---

## Test Reports

### ExtentReports
After test execution, open the generated HTML report directly in your browser:

- **Path**: `target/ExtentReport.html`

### Allure Report
Generate and open the Allure dashboard locally:

```bash
allure serve target/allure-results
```

### TestNG Reports
Standard Surefire and TestNG reports can be found at:

- `target/surefire-reports/index.html`
- `target/surefire-reports/emailable-report.html`

---

## Project Structure

```text
university-api-tests/
├── University.yml                         # OpenAPI specification file
├── pom.xml                                # Project dependencies and plugins
├── testNG.xml                             # TestNG suite configuration
└── src/
    └── test/
        └── java/
            └── com/
                └── example/
                    └── university/
                        ├── clients/
                        │   └── UniversityApi.java         # Rest Assured client layer
                        ├── config/
                        │   └── ApiConfig.java             # Base URI, auth headers, specs
                        ├── listeners/
                        │   └── ExtentReportListener.java  # ExtentReports TestNG listener
                        └── tests/
                            ├── UniversityHappyPathTest.java # 200 OK acceptance tests
                            └── UniversityNegativeTest.java  # 422, 401, 404, raw params
```