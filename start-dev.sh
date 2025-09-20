#!/bin/bash

echo "Starting ProAim Development Environment..."

echo ""
echo "Starting Backend..."
cd backend
./mvnw spring-boot:run &
BACKEND_PID=$!

echo ""
echo "Waiting for backend to start..."
sleep 10

echo ""
echo "Starting Frontend..."
cd ../frontend
npm run dev &
FRONTEND_PID=$!

echo ""
echo "Both services are starting..."
echo "Backend: http://localhost:8080"
echo "Frontend: http://localhost:5173"
echo ""
echo "Press Ctrl+C to stop both services"

# Function to cleanup processes on exit
cleanup() {
    echo ""
    echo "Stopping services..."
    kill $BACKEND_PID 2>/dev/null
    kill $FRONTEND_PID 2>/dev/null
    exit
}

# Set trap to cleanup on script exit
trap cleanup SIGINT SIGTERM

# Wait for user to stop
wait
