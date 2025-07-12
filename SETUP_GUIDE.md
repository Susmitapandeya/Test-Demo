# Setup Guide: Making Your Todo App Editable and Runnable Anywhere

This guide will help you make your Spring Boot Todo application accessible and runnable from any computer or location.

## 🚀 Quick Start Options

### Option 1: Git Repository (Recommended)
The easiest way to share and collaborate on your code.

### Option 2: Docker Deployment
Deploy your app to run on any machine with Docker.

### Option 3: Cloud Deployment
Deploy to cloud platforms for global access.

---

## 📋 Option 1: Git Repository Setup

### Step 1: Create a Git Repository

1. **Initialize Git** (if not already done):
```bash
git init
git add .
git commit -m "Initial commit: Todo application with Spring Boot"
```

2. **Create a .gitignore file** (if not exists):
```bash
# Maven
target/
!.mvn/wrapper/maven-wrapper.jar
!**/src/main/**/target/
!**/src/test/**/target/

# IDE
.idea/
*.iws
*.iml
*.ipr
.vscode/

# OS
.DS_Store
Thumbs.db

# Logs
*.log

# Environment variables
.env
```

3. **Push to GitHub/GitLab**:
```bash
# Create a new repository on GitHub/GitLab
git remote add origin https://github.com/yourusername/todo-app.git
git branch -M main
git push -u origin main
```

### Step 2: Share with Others

**For other developers:**
```bash
git clone https://github.com/yourusername/todo-app.git
cd todo-app
```

---

## 🐳 Option 2: Docker Deployment

### Local Docker Setup

1. **Build and run with Docker Compose**:
```bash
# Start the entire application (app + database)
docker-compose up -d

# Check if containers are running
docker-compose ps

# View logs
docker-compose logs -f
```

2. **Access the application**:
- API: http://localhost:8080
- Database: localhost:3306

### Share Docker Image

1. **Build and push to Docker Hub**:
```bash
# Build the image
docker build -t yourusername/todo-app:latest .

# Push to Docker Hub
docker push yourusername/todo-app:latest
```

2. **Others can run it**:
```bash
# Pull and run
docker pull yourusername/todo-app:latest
docker run -p 8080:8080 yourusername/todo-app:latest
```

---

## ☁️ Option 3: Cloud Deployment

### Deploy to Heroku

1. **Create Heroku app**:
```bash
# Install Heroku CLI
heroku create your-todo-app-name
```

2. **Add Heroku PostgreSQL**:
```bash
heroku addons:create heroku-postgresql:mini
```

3. **Deploy**:
```bash
git push heroku main
```

### Deploy to Railway

1. **Connect your GitHub repo to Railway**
2. **Add environment variables**:
   - `SPRING_DATASOURCE_URL`
   - `JWT_SECRET`
   - `JWT_EXPIRATION`

### Deploy to AWS/DigitalOcean

1. **Create a VPS instance**
2. **Install Docker**:
```bash
curl -fsSL https://get.docker.com -o get-docker.sh
sh get-docker.sh
```

3. **Deploy your app**:
```bash
# Clone your repository
git clone https://github.com/yourusername/todo-app.git
cd todo-app

# Run with Docker Compose
docker-compose up -d
```

---

## 🔧 Environment Configuration

### For Different Environments

Create environment-specific configuration files:

1. **Development** (`application-dev.properties`):
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/todo_db
spring.datasource.username=root
spring.datasource.password=root123
```

2. **Production** (`application-prod.properties`):
```properties
spring.datasource.url=${DATABASE_URL}
spring.datasource.username=${DATABASE_USERNAME}
spring.datasource.password=${DATABASE_PASSWORD}
jwt.secret=${JWT_SECRET}
```

3. **Docker** (`application-docker.properties`):
```properties
spring.datasource.url=jdbc:mysql://mysql:3306/todo_db
spring.datasource.username=todo_user
spring.datasource.password=todo_pass
```

---

## 📱 Making it Accessible from Any Device

### 1. Local Network Access

If running on your computer, others on the same network can access:
```
http://YOUR_COMPUTER_IP:8080
```

Find your IP:
- **Windows**: `ipconfig`
- **Mac/Linux**: `ifconfig` or `ip addr`

### 2. Port Forwarding (For Remote Access)

1. **Configure your router** to forward port 8080 to your computer
2. **Use your public IP**: `http://YOUR_PUBLIC_IP:8080`

### 3. Use ngrok (Temporary Public URL)

```bash
# Install ngrok
# Download from https://ngrok.com/

# Expose your local server
ngrok http 8080
```

This gives you a public URL like: `https://abc123.ngrok.io`

---

## 🔐 Security Considerations

### For Production Deployment

1. **Change default passwords**:
```properties
# In application.properties
spring.datasource.password=STRONG_PASSWORD_HERE
```

2. **Use environment variables**:
```bash
export SPRING_DATASOURCE_PASSWORD=your_secure_password
export JWT_SECRET=your_very_long_secret_key
```

3. **Enable HTTPS** (for production):
```properties
server.ssl.enabled=true
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password=your_keystore_password
```

---

## 🛠️ Troubleshooting

### Common Issues

1. **Port already in use**:
```bash
# Find what's using port 8080
netstat -ano | findstr :8080  # Windows
lsof -i :8080                 # Mac/Linux

# Kill the process or change port
server.port=8081
```

2. **Database connection issues**:
```bash
# Check if MySQL is running
docker-compose ps

# Restart services
docker-compose restart
```

3. **Memory issues**:
```bash
# Increase JVM memory
java -Xmx2g -jar target/todo-0.0.1-SNAPSHOT.jar
```

---

## 📞 Getting Help

- **GitHub Issues**: Create issues in your repository
- **Stack Overflow**: Tag with `spring-boot`, `java`, `docker`
- **Spring Boot Documentation**: https://spring.io/guides

---

## 🎯 Next Steps

1. **Choose your deployment method** (Git, Docker, or Cloud)
2. **Set up your chosen method** following the steps above
3. **Test the deployment** from another device/network
4. **Share the access URL** with your team/collaborators

Your Todo app is now ready to be shared and run from anywhere! 🚀 