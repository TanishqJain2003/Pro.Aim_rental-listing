# Backend-Frontend Connection Test Guide

## Prerequisites
1. Java 17+ installed
2. Node.js 16+ installed
3. MySQL database running (for production) or H2 (for development)

## Backend Setup

1. Navigate to the backend directory:
   ```bash
   cd backend
   ```

2. Run the backend application:
   ```bash
   ./mvnw spring-boot:run
   ```
   Or on Windows:
   ```bash
   mvnw.cmd spring-boot:run
   ```

3. The backend should start on `http://localhost:8080`

4. Test the backend API directly:
   ```bash
   curl http://localhost:8080/api/test/hello
   ```
   Expected response: "Hello! The application is running successfully!"

## Frontend Setup

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the frontend development server:
   ```bash
   npm run dev
   ```

4. The frontend should start on `http://localhost:5173`

## Testing the Connection

### 1. Test API Proxy
- Open browser to `http://localhost:5173`
- Open browser developer tools (F12)
- Go to Network tab
- Try to register a new user or login
- Check that API calls are being made to `/api/auth/*` and proxied to the backend

### 2. Test CORS
- The frontend should be able to make requests to the backend without CORS errors
- Check browser console for any CORS-related errors

### 3. Test Authentication Flow
1. Try registering a new user
2. Try logging in with the registered user
3. Check that the JWT token is stored in localStorage
4. Verify that protected routes work correctly

### 4. Test Error Handling
1. Try logging in with invalid credentials
2. Try registering with an existing username/email
3. Verify that error messages are displayed properly

## Expected Behavior

- ✅ Backend starts on port 8080
- ✅ Frontend starts on port 5173
- ✅ API calls are proxied correctly
- ✅ CORS is properly configured
- ✅ Authentication works end-to-end
- ✅ Error handling works properly
- ✅ JWT tokens are handled correctly

## Troubleshooting

### Backend Issues
- Check if port 8080 is available
- Verify database connection
- Check backend logs for errors

### Frontend Issues
- Check if port 5173 is available
- Verify that backend is running
- Check browser console for errors
- Verify proxy configuration in vite.config.js

### Connection Issues
- Verify CORS configuration
- Check that both servers are running
- Verify API endpoint URLs
- Check network tab in browser dev tools
