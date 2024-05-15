# My Connected API
The aim of this project is to provide a test web
application using Spring Boot that can
be used within a Kubernetes cluster.

It was created to experiment with using private APIs
within a Kubernetes cluster along with the
[my-connected-app](https://github.com/MartinHodges/my-connected-app)
Next.js web application.

You can find a more detailed explanation of the project in my associated
[Medium article](https://medium.com/@martin.hodges/how-to-access-your-apis-from-next-js-within-kubernetes-86677770f941).

## Profiles
The application has been created with a single Spring Boot profile: `dev`

You should add your own profiles for other scenarios.

## What does it do?
As a bit of fun, the API provides a set of basic API endpoints to allow you to
manage fishes in fish tanks.

The features include the ability to:
* Manage a set of fish tanks (`GET`, `POST`, `PUT`, `DELETE`)
* Create and manage fishes (`GET`, `POST`, `PUT`, `DELETE`)
* Add and remove fish to/from a tank (`PUT`, `DELETE`)

The REST API end points are at:

    /api/v1/fish-tanks
    /api/v1/fish-tanks/{id}
    /api/v1/fish-tanks/{id}/fishes/{id}
    /api/v1/fish-types
    /api/v1/fish-types/{id}
    /api/v1/fishes
    /api/v1/fishes/{id}

## Additional documentation
You can find more documentation about the project in
[my medium article](https://medium.com/@martin.hodges). You will also find references 
in this article to other articles that explain how to
set up a local Kubernetes cluster using Kind.

## Setting up a local Kind Kubernetes cluster

The files to set up a 3 node cluster are included in this repository
(1 master and 2 workers).

From the project root, assuming you have Kind and Helm installed,
create a base-level KInd cluster with:
```
 kind create cluster --config kind/kind-config.yml
```
Once this has been created, set up the required Helm repositories:
```
helm repo add grafana https://grafana.github.io/helm-charts
helm repo add cnpg https://cloudnative-pg.github.io/charts
helm repo add hashicorp https://helm.releases.hashicorp.com
helm repo add external-secrets https://charts.external-secrets.io
helm repo update
```
Now install Grafana, Loki, and Postgres (note that some
steps can take up to 2 minutes to start):
```
kubectl create namespace monitoring
helm install loki grafana/loki-stack -n monitoring -f kind/loki-config.yml
kubectl apply -f kind/grafana-svc.yml

kubectl create namespace pg
helm install cnpg cnpg/cloudnative-pg -n pg --wait
kubectl apply -f kind/db-user-config.yml
kubectl apply -f kind/db-config.yml
```

## Running this application
You can run this application using the `dev` profile mentioned earlier.

In this profile, the application runs in the Kubernetes cluster
as a JAR file in a Docker image. It connects to the cluster database.

You can build this and create the image with:
```
gradle jar
docker build -t my-connected-apis .
```
You can now upload it to your cluster with:
```
kind load docker-image my-connected-apis .
```
Before you can deploy it, you need to create the username
and password in a Kubernetes secret:
```
kubectl create secret generic static-db-credentials --from-literal username=app-user --from-literal password=app-secret
```
The API will be available on http://localhost:30080
You can connect a DB client to the database on http://localhost:32321

## Test Pod
To test internal services, there is an included Ubuntu deployment.
Deploy it with:
```
kubectl apply -f k8s/test-deployment.yml
```
You can then get a command line on the pod with:
```
kubectl exec -it ubunutu -- bash
```