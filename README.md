# Kubernetes Multi-Tier Employee Management System

A cloud-native multi-tier application demonstrating containerized microservices deployment on Kubernetes with Spring Boot API and MongoDB database.

## 🚀 Project Overview

This project implements a complete employee management system following microservices architecture, deployed on Kubernetes with proper security, scalability, and persistence configurations.

### 🏗️ Architecture

- **Service API Tier**: Spring Boot REST API (4 replicas)
- **Database Tier**: MongoDB with persistent storage (1 replica)
- **External Access**: Nginx Ingress Controller
- **Configuration Management**: Kubernetes ConfigMaps and Secrets

## 📋 Features

- ✅ Full CRUD operations for employee management
- ✅ Kubernetes-native configuration management
- ✅ Persistent data storage with automatic backups
- ✅ Rolling updates with zero downtime
- ✅ Service discovery and internal communication
- ✅ External API access via Ingress
- ✅ Database initialization with sample data
- ✅ Container health monitoring and auto-restart

**Docker Hub Repository**: [shubhendumishra/employee-api](https://hub.docker.com/r/shubhendumishra/employee-api)

## 🛠️ Technology Stack

| Component             | Technology  | Version |
| --------------------- | ----------- | ------- |
| **Backend API**       | Spring Boot | 3.2.0   |
| **Database**          | MongoDB     | 6.0     |
| **Container Runtime** | Docker      | Latest  |
| **Orchestration**     | Kubernetes  | 1.28+   |
| **Language**          | Java        | 17      |
| **Build Tool**        | Maven       | 3.9+    |

## 📦 Repository Structure

```
├── k8s/                           # Kubernetes manifests
│   ├── mongodb/                   # Database tier resources
│   │   ├── mongo-configmap.yaml   # Database configuration
│   │   ├── mongo-secret.yaml      # Database credentials
│   │   ├── mongo-pvc.yaml         # Persistent storage
│   │   ├── mongo-statefulset.yaml # MongoDB deployment
│   │   ├── mongo-service.yaml     # Database service
│   │   └── mongo-init-job.yaml    # Data initialization
│   ├── employee-api-service/      # API service tier
│   │   └── employee-api-service.yaml # API deployment & service
│   └── ingress/                   # External access
│       └── employee-api-ingress.yaml # Ingress configuration
├── service/                       # Spring Boot application
│   ├── src/main/java/            # Application source code
│   ├── src/main/resources/       # Configuration files
│   ├── Dockerfile                # Container definition
│   └── pom.xml                   # Maven dependencies
└── docs/                         # Documentation
```

## 🐳 Docker Images

- **API Service**: `shubhendumishra/employee-api:11`
- **Database**: `mongo:6`

## 🚀 Quick Start

### Prerequisites

- Kubernetes cluster (minikube, Docker Desktop, or cloud provider)
- kubectl configured
- Internet access for image pulling

### 1. Clone Repository

```bash
git clone https://github.com/shubhendumishra/kubernetes-spring-next.git
cd kubernetes-spring-next
```

### 2. Deploy Application

#### Kubernetes Commands

##### Show all pods

```bash
kubectl get pods
```

##### Show all services

```bash
kubectl get services
```

##### Show all deployments

```bash
kubectl get deployments
```

##### Show all statefulsets

```bash
kubectl get statefulsets
```

##### Show all configmaps

```bash
kubectl get configmaps
```

##### Show all secrets

```bash
kubectl get secrets
```

##### Show all ingress

```bash
kubectl get ingress
```

#### Deploy ConfigMap

##### Create MongoDB configuration

```bash
kubectl apply -f k8s/mongodb/mongo-configmap.yaml
```

#### Deploy Secrets

##### Create MongoDB credentials

```bash
kubectl apply -f k8s/mongodb/mongo-secret.yaml
```

#### Deploy PVC

##### Create persistent volume claim

```bash
kubectl apply -f k8s/mongodb/mongo-pvc.yaml
```

#### Deploy StatefulSet and Service

##### Create MongoDB StatefulSet

```bash
kubectl apply -f k8s/mongodb/mongo-statefulset.yaml
```

##### Create MongoDB Service

```bash
kubectl apply -f k8s/mongodb/mongo-service.yaml
```

##### Initialize database with sample data

```bash
kubectl apply -f k8s/mongodb/mongo-init-job.yaml
```

#### Deploy API Service

##### Create Employee API deployment and service

```bash
kubectl apply -f k8s/employee-api-service/employee-api-service.yaml
```

#### Deploy Ingress

##### Create ingress for external access

```bash
kubectl apply -f k8s/ingress/employee-api-ingress.yaml
```

### Deletion Commands

#### Delete API deployment and service

```bash
kubectl delete -f k8s/employee-api-service/employee-api-service.yaml
```

##### Delete MongoDB initialization job

```bash
kubectl delete -f k8s/mongodb/mongo-init-job.yaml
```

##### Delete MongoDB service

```bash
kubectl delete -f k8s/mongodb/mongo-service.yaml
```

#### Delete MongoDB StatefulSet

```bash
kubectl delete -f k8s/mongodb/mongo-statefulset.yaml
```

##### Delete persistent volume claim

```bash
kubectl delete -f k8s/mongodb/mongo-pvc.yaml
```

##### Delete MongoDB configuration

```bash
kubectl delete -f k8s/mongodb/mongo-configmap.yaml
```

##### Delete MongoDB credentials

```bash
kubectl delete -f k8s/mongodb/mongo-secret.yaml
```

##### Delete ingress

```bash
kubectl delete -f k8s/ingress/employee-api-ingress.yaml
```

#### MongoDB Service Check

##### Check MongoDB pod status

```bash
kubectl get pods -l app=mongo
```

#### Rolling Updates Demo

##### Check current deployment image

```bash
kubectl get deployment employee-api -o wide
```

##### Update deployment image

```bash
kubectl set image deployment/employee-api employee-api=shubhendumishra/employee-api:12
```

##### Watch rolling update progress

```bash
kubectl rollout status deployment/employee-api
```

##### Check rollout history

```bash
kubectl rollout history deployment/employee-api
```

#### Data Persistence Demo

##### Delete MongoDB pod to test persistence

```bash
kubectl delete pod mongo-0
```

##### Verify data persists after pod recreation

```bash
curl -X GET http://35.190.5.27/employees
```

## 🔗 API Endpoints

| Method | Endpoint                 | Description         |
| ------ | ------------------------ | ------------------- |
| GET    | `/`                      | Health check        |
| GET    | `/employees/ping`        | Service status      |
| GET    | `/employees`             | List all employees  |
| GET    | `/employees/{id}`        | Get employee by ID  |
| POST   | `/employees/addEmployee` | Create new employee |
| DELETE | `/employees/{id}`.       | Delete employee     |

### 📝 Sample API Usage

```bash
# Get all employees
curl -X GET http://35.190.5.27/employees

# Add new employee
curl -X POST http://35.190.5.27/employees/addEmployee \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","designation":"Developer","department":"IT","salary":75000}'
```

## 📊 Sample Data

The system initializes with 10 sample employee records:

| Name    | Designation | Department | Salary  |
| ------- | ----------- | ---------- | ------- |
| Alice   | Engineer    | IT         | $65,000 |
| Bob     | Manager     | HR         | $75,000 |
| Charlie | Analyst     | Finance    | $60,000 |
| ...     | ...         | ...        | ...     |

## 🔧 Configuration

### Environment Variables

| Variable                | Source    | Description          |
| ----------------------- | --------- | -------------------- |
| `MONGO_HOST`            | ConfigMap | MongoDB service name |
| `MONGO_PORT`            | ConfigMap | MongoDB port         |
| `MONGO_INITDB_DATABASE` | ConfigMap | Database name        |
| `MONGO_APP_USERNAME`    | Secret    | Database username    |
| `MONGO_APP_PASSWORD`    | Secret    | Database password    |

### Kubernetes Resources

| Resource Type   | Name                 | Purpose                          |
| --------------- | -------------------- | -------------------------------- |
| **Deployment**  | employee-api         | API service (4 replicas)         |
| **StatefulSet** | mongo                | Database with persistent storage |
| **Service**     | employee-api, mongo  | Internal service discovery       |
| **Ingress**     | employee-api-ingress | External access                  |
| **ConfigMap**   | mongo-config         | Database configuration           |
| **Secret**      | mongo-secret         | Database credentials             |
| **PVC**         | mongo-pvc            | Persistent storage (1Gi)         |
| **Job**         | mongo-init-job       | Database initialization          |

## 🔒 Security Features

- 🔐 Database credentials stored in Kubernetes Secrets
- 🛡️ No hardcoded passwords in source code
- 🚫 Pod-to-pod communication via service names (no IP exposure)
- 🔍 Environment-based configuration management

## 📈 Scalability & Reliability

- **Horizontal Scaling**: API service runs 4 replicas
- **Rolling Updates**: Zero-downtime deployments
- **Auto-restart**: Kubernetes automatically restarts failed pods
- **Persistent Storage**: Data survives pod restarts
- **Health Checks**: Built-in application health monitoring

## 🧪 Testing & Validation

The deployment includes comprehensive testing scenarios:

1. **API Functionality**: All CRUD operations working
2. **Pod Resilience**: API pods auto-recreate when deleted
3. **Data Persistence**: Database survives pod restarts
4. **Service Discovery**: Internal communication working
5. **External Access**: Ingress routing functional

## 📚 Documentation

- [Deployment Guide](DEPLOYMENT_STEPS.md) - Step-by-step deployment instructions
- [Cleanup Guide](DELETE_STEPS.md) - Resource deletion procedures
- [Technical Documentation](TECHNICAL_DOCUMENTATION.md) - Detailed technical analysis

## 🤝 Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👤 Author

**Shubhendu Mishra**

- GitHub: [@shubhendumishra](https://github.com/shubhendumishra)
- Docker Hub: [shubhendumishra](https://hub.docker.com/u/shubhendumishra)

## 🎯 Assignment Requirements Compliance

✅ Multi-tier architecture (API + Database)  
✅ Containerized with Docker  
✅ Deployed on Kubernetes  
✅ External API access via Ingress  
✅ ConfigMap for database configuration  
✅ Secrets for sensitive data  
✅ Persistent storage implementation  
✅ Service-based communication (no pod IPs)  
✅ Rolling updates support  
✅ Sample data initialization

---
