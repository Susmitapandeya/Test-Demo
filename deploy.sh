#!/bin/bash

# Todo App Deployment Script
# This script helps deploy the Todo application in different environments

set -e

echo "🚀 Todo App Deployment Script"
echo "=============================="

# Function to check if command exists
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# Function to check prerequisites
check_prerequisites() {
    echo "📋 Checking prerequisites..."
    
    if ! command_exists java; then
        echo "❌ Java is not installed. Please install Java 24 or higher."
        exit 1
    fi
    
    if ! command_exists mvn; then
        echo "❌ Maven is not installed. Please install Maven 3.6 or higher."
        exit 1
    fi
    
    if ! command_exists docker; then
        echo "⚠️  Docker is not installed. Docker deployment will not be available."
    fi
    
    if ! command_exists docker-compose; then
        echo "⚠️  Docker Compose is not installed. Docker deployment will not be available."
    fi
    
    echo "✅ Prerequisites check completed"
}

# Function to build the application
build_app() {
    echo "🔨 Building the application..."
    mvn clean package -DskipTests
    echo "✅ Build completed"
}

# Function to run locally
run_local() {
    echo "🏃 Running application locally..."
    
    # Check if MySQL is running
    if ! command_exists mysql; then
        echo "⚠️  MySQL not found. Please ensure MySQL is running on localhost:3306"
    fi
    
    # Set active profile
    export SPRING_PROFILES_ACTIVE=dev
    
    # Run the application
    mvn spring-boot:run
}

# Function to run with Docker
run_docker() {
    echo "🐳 Running application with Docker..."
    
    if ! command_exists docker; then
        echo "❌ Docker is not installed. Please install Docker first."
        exit 1
    fi
    
    if ! command_exists docker-compose; then
        echo "❌ Docker Compose is not installed. Please install Docker Compose first."
        exit 1
    fi
    
    # Build and run with Docker Compose
    docker-compose up -d --build
    
    echo "✅ Application is running!"
    echo "🌐 Access the API at: http://localhost:8080"
    echo "📊 View logs with: docker-compose logs -f"
    echo "🛑 Stop with: docker-compose down"
}

# Function to deploy to production
deploy_production() {
    echo "☁️  Deploying to production..."
    
    # Build the application
    build_app
    
    # Set production profile
    export SPRING_PROFILES_ACTIVE=prod
    
    # Check for required environment variables
    if [ -z "$DATABASE_URL" ]; then
        echo "⚠️  DATABASE_URL environment variable not set"
    fi
    
    if [ -z "$JWT_SECRET" ]; then
        echo "⚠️  JWT_SECRET environment variable not set"
    fi
    
    # Run the application
    java -jar target/todo-0.0.1-SNAPSHOT.jar
}

# Function to show help
show_help() {
    echo "Usage: $0 [OPTION]"
    echo ""
    echo "Options:"
    echo "  local     Run the application locally"
    echo "  docker    Run the application with Docker"
    echo "  prod      Deploy to production"
    echo "  build     Build the application only"
    echo "  check     Check prerequisites only"
    echo "  help      Show this help message"
    echo ""
    echo "Examples:"
    echo "  $0 local     # Run locally"
    echo "  $0 docker    # Run with Docker"
    echo "  $0 prod      # Deploy to production"
}

# Main script logic
case "${1:-help}" in
    "local")
        check_prerequisites
        run_local
        ;;
    "docker")
        check_prerequisites
        run_docker
        ;;
    "prod")
        check_prerequisites
        deploy_production
        ;;
    "build")
        check_prerequisites
        build_app
        ;;
    "check")
        check_prerequisites
        ;;
    "help"|*)
        show_help
        ;;
esac 