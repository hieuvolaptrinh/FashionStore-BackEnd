# Google OAuth2 Integration Setup Guide

This guide provides step-by-step instructions to set up Google OAuth2 authentication for your Spring Boot backend and React frontend application.

## Prerequisites

1. Java 21
2. Spring Boot 3.x
3. Node.js and npm
4. Google Developer Account

## Step 1: Create Google OAuth2 Credentials

1. Go to the [Google Cloud Console](https://console.cloud.google.com/)
2. Create a new project or select an existing one
3. Navigate to "APIs & Services" > "Credentials"
4. Click "Create Credentials" and select "OAuth client ID"
5. Choose "Web application" as the application type
6. Set a name for your OAuth client
7. Add authorized JavaScript origins:
   - `http://localhost:8080` (Spring Boot server)
   - `http://localhost:5173` (React frontend)
8. Add authorized redirect URIs:
   - `http://localhost:8080/login/oauth2/code/google` (Spring Boot OAuth2 callback URL)
9. Click "Create" and note down your Client ID and Client Secret

## Step 2: Configure Spring Boot Application

1. Add the required dependencies in `pom.xml` (already included in your project):

   - `spring-boot-starter-oauth2-client`
   - `spring-boot-starter-security`

2. Configure OAuth2 properties in your `.env` file:

   ```
   GOOGLE_CLIENT_ID=your_client_id
   GOOGLE_CLIENT_SECRET=your_client_secret
   DB_USERNAME=your_db_username
   DB_PASSWORD=your_db_password
   MAIL_USERNAME=your_mail_username
   MAIL_PASSWORD=your_mail_password
   FRONTEND_URL=http://localhost:5173
   ```

3. Make sure your application.properties includes the OAuth2 configuration:

   ```properties
   spring.security.oauth2.client.registration.google.client-id=${GOOGLE_CLIENT_ID}
   spring.security.oauth2.client.registration.google.client-secret=${GOOGLE_CLIENT_SECRET}
   spring.security.oauth2.client.registration.google.scope=email,profile
   spring.security.oauth2.client.registration.google.redirect-uri={baseUrl}/login/oauth2/code/{registrationId}
   spring.security.oauth2.client.registration.google.client-name=Google
   spring.security.oauth2.client.provider.google.authorization-uri=https://accounts.google.com/o/oauth2/v2/auth
   spring.security.oauth2.client.provider.google.token-uri=https://www.googleapis.com/oauth2/v4/token
   spring.security.oauth2.client.provider.google.user-info-uri=https://www.googleapis.com/oauth2/v3/userinfo
   spring.security.oauth2.client.provider.google.jwk-set-uri=https://www.googleapis.com/oauth2/v3/certs
   spring.security.oauth2.client.provider.google.user-name-attribute=sub
   ```

4. Create or update the necessary Java classes:
   - `CustomOAuth2UserService.java`
   - `OAuth2SuccessHandler.java`
   - Update `SecurityConfiguration.java`
   - Update `UserRepository.java` and `RoleRepository.java`
   - Update `OAuth2Controller.java`

## Step 3: Set Up React Frontend

1. Create a new React project or use your existing one
2. Install required dependencies:

   ```bash
   npm install react-router-dom axios jwt-decode
   ```

3. Create the necessary components:

   - `GoogleLoginButton.jsx`
   - `OAuth2RedirectHandler.jsx`
   - `PrivateRoute.jsx`
   - `Login.jsx`
   - `Home.jsx`
   - API and Auth services

4. Configure routing in your App component

## Step 4: Testing the Integration

1. Start your Spring Boot backend:

   ```bash
   mvn spring-boot:run
   ```

2. Start your React frontend:

   ```bash
   npm run dev
   ```

3. Navigate to the login page at `http://localhost:5173/login`
4. Click on the "Sign in with Google" button
5. Complete the Google authentication process
6. You should be redirected back to your application with your Google profile information

## Troubleshooting

- **Redirect URI mismatch**: Make sure the redirect URI in your Google Cloud Console matches exactly with the one in your Spring Boot configuration
- **CORS issues**: Check the CORS configuration in your Spring Security setup
- **Token validation errors**: Verify that your JWT service is correctly validating tokens
- **User not found errors**: Ensure your database has the necessary tables and the USER role exists

## Additional Resources

- [Spring Security OAuth2 Documentation](https://docs.spring.io/spring-security/reference/servlet/oauth2/index.html)
- [Google OAuth2 Documentation](https://developers.google.com/identity/protocols/oauth2)
- [React Router Documentation](https://reactrouter.com/)
