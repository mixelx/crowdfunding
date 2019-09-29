# StartIt Company Wep Application

## Used technologies
- Back-end: Java 8, Spring boot.
- Database: JDBC, MySQL.
- Front-end: HTML5, Bootstrap, JavaScript, responsive and adaptive web design for devices with small screens.
- Other: Tomcat, Maven, Git.

## Application architecture:
- **Layered architecture** where main backend layers are: Single FrontEnd Controller with Commands <-> Service Layer <-> DAO Layer <-> MySQL RDBMS.
### Admin
- Login/logout.
- Add/edit/delete campaigns.
- Block/delete/change_role users.

### User
- Registration(with email validation), login/logout.
- Choose campaings categories.
- Make donate to campaigns.
- Look at the privileges granted to him
- View other user account information.
- Create their own campaign.
- Set campaign rating.

