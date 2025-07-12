@echo off
setlocal enabledelayedexpansion

echo 🚀 Todo App Deployment Script
echo ==============================

REM Function to check if command exists
:check_command
set "command=%~1"
where %command% >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ %command% is not installed or not in PATH
    exit /b 1
) else (
    echo ✅ %command% is available
    exit /b 0
)

REM Function to check prerequisites
:check_prerequisites
echo 📋 Checking prerequisites...

call :check_command java
if %errorlevel% neq 0 (
    echo Please install Java 24 or higher.
    exit /b 1
)

call :check_command mvn
if %errorlevel% neq 0 (
    echo Please install Maven 3.6 or higher.
    exit /b 1
)

call :check_command docker
if %errorlevel% neq 0 (
    echo ⚠️  Docker is not installed. Docker deployment will not be available.
)

call :check_command docker-compose
if %errorlevel% neq 0 (
    echo ⚠️  Docker Compose is not installed. Docker deployment will not be available.
)

echo ✅ Prerequisites check completed
goto :eof

REM Function to build the application
:build_app
echo 🔨 Building the application...
call mvn clean package -DskipTests
if %errorlevel% neq 0 (
    echo ❌ Build failed
    exit /b 1
)
echo ✅ Build completed
goto :eof

REM Function to run locally
:run_local
echo 🏃 Running application locally...

call :check_command mysql
if %errorlevel% neq 0 (
    echo ⚠️  MySQL not found. Please ensure MySQL is running on localhost:3306
)

set SPRING_PROFILES_ACTIVE=dev
call mvn spring-boot:run
goto :eof

REM Function to run with Docker
:run_docker
echo 🐳 Running application with Docker...

call :check_command docker
if %errorlevel% neq 0 (
    echo ❌ Docker is not installed. Please install Docker first.
    exit /b 1
)

call :check_command docker-compose
if %errorlevel% neq 0 (
    echo ❌ Docker Compose is not installed. Please install Docker Compose first.
    exit /b 1
)

call docker-compose up -d --build
if %errorlevel% neq 0 (
    echo ❌ Docker deployment failed
    exit /b 1
)

echo ✅ Application is running!
echo 🌐 Access the API at: http://localhost:8080
echo 📊 View logs with: docker-compose logs -f
echo 🛑 Stop with: docker-compose down
goto :eof

REM Function to deploy to production
:deploy_production
echo ☁️  Deploying to production...

call :build_app
if %errorlevel% neq 0 (
    exit /b 1
)

set SPRING_PROFILES_ACTIVE=prod

if "%DATABASE_URL%"=="" (
    echo ⚠️  DATABASE_URL environment variable not set
)

if "%JWT_SECRET%"=="" (
    echo ⚠️  JWT_SECRET environment variable not set
)

java -jar target/todo-0.0.1-SNAPSHOT.jar
goto :eof

REM Function to show help
:show_help
echo Usage: %0 [OPTION]
echo.
echo Options:
echo   local     Run the application locally
echo   docker    Run the application with Docker
echo   prod      Deploy to production
echo   build     Build the application only
echo   check     Check prerequisites only
echo   help      Show this help message
echo.
echo Examples:
echo   %0 local     # Run locally
echo   %0 docker    # Run with Docker
echo   %0 prod      # Deploy to production
goto :eof

REM Main script logic
if "%1"=="" goto show_help
if "%1"=="local" goto run_local
if "%1"=="docker" goto run_docker
if "%1"=="prod" goto deploy_production
if "%1"=="build" goto build_app
if "%1"=="check" goto check_prerequisites
if "%1"=="help" goto show_help

echo Unknown option: %1
goto show_help 