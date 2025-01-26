This project is a backend service to the Task Management Application through Restful Apis.

Core Entities and APIs
 - User Management
   - Implemented the User entity and related APIs.
   - Features: Create, fetch, update, and delete users.
   - Added role-based user management for the ADMIN role.

 - Task Management
   - Implemented the Task entity and related APIs.  
   - Features: Create, fetch, update, and delete tasks for regular users.
   - Implemented admin-level task management:
       - View all tasks in the system.
       - Assign tasks to specific users.
       - Delete tasks.

Security
  - Authentication and Authorization
  - Integrated JWT-based authentication:
    - Tokens include user-specific details such as username and role.
  - Added refresh token functionality:
  - Securely renew expired access tokens using refresh tokens.
  - Implemented token expiration handling:
    - Automatically handle expired JWTs.
  - Return meaningful responses for expired or invalid tokens.
  - Role-Based Access Control (RBAC)
  - Defined roles such as ADMIN, USER, and optionally MANAGER.
  - Restricted API access based on roles:
      - Regular users have limited access to their tasks.
      - Admins have elevated privileges for user and task management.
      - Role Extraction
          - Successfully extracted the role claim from JWT tokens for authorization.

ADMIN-Specific Functionalities
  - User Management
     - Admins can:
        - View all users.
        - Create new users.
        - Update user roles and statuses (e.g., enable/disable accounts).
        - Delete users.
  - Task Management
     - Admins can:
        - View all tasks system-wide (irrespective of ownership).
        - Assign tasks to specific users.
        - Delete tasks.

Database Integration
  - Set up PostgreSQL for data storage.
  - Designed schemas for entities:
      - User: Includes roles, active status, and credentials.
      - Task: Includes title, description, status, and assignment details.

Security Configuration
  - Customized Spring Security:
  - Allowed public access for authentication endpoints and Swagger documentation.
  - Secured sensitive endpoints with @PreAuthorize and custom RBAC logic.
  - Enabled stateless session management with Spring Security.

Next Steps:
  - Exception Handling:
      - Add custom error handling and consistent API responses.
  - Notification System:
      - Implement reminders and notifications for task deadlines or updates.
  - Testing:
      - Write comprehensive unit, integration, and end-to-end tests.
  - Dashboard and Analytics:
      - Add an admin dashboard to track active users, task progress, and overall system usage.
  - Frontend Development:
      - Start creating a user-friendly interface to manage tasks and users.
