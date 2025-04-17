
SpiceJet Automation Test Suite

This repository contains end-to-end automated UI test cases for the [SpiceJet](https://www.spicejet.com/) website using Selenium WebDriver, TestNG, and Java. 
The test suite verifies core booking functionalities including flight search, passenger updates, invalid input handling, and more.

📁 Project Structure

```
/src/test/java/
├── CompareFlightTest.java      # Extract one-way flight numbers
├── FlightSearchTest.java             # One-way flight search + screenshot
├── InvalidCityTest.java              # Invalid city error handling
├── PassengerCountTest.java           # Passenger selection and summary validation
└── RoundTripPassengerTest.java       # Round trip with date, passenger, screenshot
```

Test Scenarios Covered

| Test Case                       | Description                                                |
|------------------------------------|----------------------------------------------------------------|
| One-Way Flight Numbers             | Extracts flight numbers for May 2 (Chennai → Delhi)            |
| Flight Search + Screenshot         | Searches for flights and captures result screenshot            |
| Invalid City Input                 | Verifies error when entering invalid city in “To” field        |
| Passenger Count Update             | Adds adults/children/infants and validates summary             |
| Round Trip Booking                 | Books round trip with return date and passengers + screenshot  |

Tech Stack

Language: Java  
Framework: TestNG  
Browser Driver: ChromeDriver (via WebDriverManager)  
Build Tool: Maven  
Reporting: Console (TestNG XML can be extended)

How to Run Tests

1. Clone the repository
2. Open the project in Eclipse or IntelliJ
3. Install dependencies with Maven (`mvn clean install`)
4. Right-click on each test class and choose `Run As > TestNG Test`

> Ensure you have Chrome installed and ChromeDriver is managed by WebDriverManager.

Screenshots

Screenshots are saved to the `/screenshots/` folder when applicable:
- `result_page.png`
- `invalid_to_city.png`
- `round_trip_passenger_result.png`

Author

Aiswarya R : QA Automation Enthusiast  
Tools: Selenium, TestNG, Maven, Eclipse
