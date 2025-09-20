@echo off
echo Starting ProAim Development Environment...

echo.
echo Starting Backend...
start "Backend" cmd /k "cd backend && mvnw.cmd spring-boot:run"

echo.
echo Waiting for backend to start...
timeout /t 10 /nobreak > nul

echo.
echo Starting Frontend...
start "Frontend" cmd /k "cd frontend && npm run dev"

echo.
echo Both services are starting...
echo Backend: http://localhost:8080
echo Frontend: http://localhost:5173
echo.
echo Press any key to exit...
pause > nul
