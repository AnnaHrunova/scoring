<h1>Purchase Approval Service</h1>

<h3>Installation and running</h3>


<h4>Tools</h4>

JDK 23<br>
Gradle 8.12.1<br>
Docker Desktop<br>
Intellij Idea 2024.2.4 (Ultimate Edition)<br>

**Build:**

`./gradlew clean build`

**Run:**

LocalScoringApplication main class is available for local run ** <br>
** Local run uses Testcontainers for running PostgreSQLContainer in SpringBoot Devtools <br>
** ScoringApplication main class not supposed for local usage <br>

http://localhost:8080/scoring

**Testing data:**

1. Any person id ending with '0' is an ineligible customer<br>
2. Financial factors for personal id ending with 'x' where x =<br>
   1 -> 50,<br>
   2 -> 200,<br>
   3 -> 300,<br>
   4 -> 400,<br>
   5 -> 500,<br>
   default -> 100;<br>
