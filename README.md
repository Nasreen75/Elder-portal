💊 ElderCare — Smart Medicine Reminder System
ElderCare is a full-stack web application designed to help elderly patients manage their medication schedules and stay connected with caregivers and doctors. The system provides role-based dashboards for Patients, Doctors, and Agents/Admins, along with automated email reminders to ensure timely medicine intake.
---
🌟 Features
📅 Medicine Reminder Scheduling — set dosage, frequency, timing, and food-interaction alerts
👥 Role-Based Dashboards — separate views for Patients, Doctors, and Agents/Admins
📧 Automated Email Notifications — reminders and alerts sent via SMTP
🩺 Complaint / Support Routing — patients can raise issues directly to doctors/agents
🔐 Secure Authentication — JWT-based login and access control
📦 Medicine Stock Tracking — monitor inventory and availability
🔄 RESTful API Architecture — clean separation between frontend and backend
---
🛠️ Tech Stack
Layer	Technology
Frontend	React.js
Backend	Spring Boot (Java), Spring Data JPA, Hibernate
Database	MySQL
Authentication	JWT (JSON Web Token)
Email Service	Spring Mail (SMTP)
Build Tools	Maven (backend), npm (frontend)
---
📸 Screenshots
> _Add screenshots of your dashboards here — e.g. login page, patient dashboard, doctor dashboard._
Login Page	Patient Dashboard	Doctor Dashboard
![Login](docs/screenshots/login.png)	![Patient](docs/screenshots/patient-dashboard.png)	![Doctor](docs/screenshots/doctor-dashboard.png)
---
🎥 Demo
> _Add a short demo video or GIF link here once recorded (e.g. YouTube link or embedded GIF)._
▶️ Watch Demo Video
---
🚀 Getting Started
Prerequisites
Java 21+
Node.js 18+
MySQL 8+
Maven (or use the included `mvnw` wrapper)
1. Clone the repository
```bash
git clone https://github.com/Nasreen75/Elder-portal.git
cd Elder-portal
```
2. Backend Setup
Set the following environment variables before running (do not hardcode secrets in `application.properties`):
```bash
DB_USERNAME=root
DB_PASSWORD=your_mysql_password
MAIL_USERNAME=your_email@example.com
MAIL_PASSWORD=your_app_password
```
Run the backend:
```bash
./mvnw clean install
./mvnw spring-boot:run
```
Backend runs on: `http://localhost:5000` (or the port configured in `application.properties`)
3. Frontend Setup
```bash
cd frontend
npm install
npm run dev
```
Frontend runs on: `http://localhost:5173`
4. Database
Create a MySQL database matching the name in `application.properties` (e.g. `medicine_db`) before starting the backend.
---
📁 Project Structure
```
Elder-portal/
├── src/                    # Spring Boot backend
│   └── main/java/com/medicine/reminder/
├── frontend/               # React frontend
│   └── src/
├── pom.xml                 # Maven config
└── README.md
```
---
👥 Team
Built collaboratively by:
Vempalli Nasreen
Karunakar
Nithish
---
📄 License
This project is for academic/portfolio purposes.
