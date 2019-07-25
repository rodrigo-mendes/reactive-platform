#!/usr/bin/env bash

sudo apt-get update -y
sudo apt-get upgrade -y

curl -Lo minikube https://storage.googleapis.com/minikube/releases/v1.2.0/minikube-linux-amd64
chmod +x minikube
sudo cp minikube /usr/local/bin/
rm minikube

curl -LO https://storage.googleapis.com/kubernetes-release/release/v1.15.0/bin/linux/amd64/kubectl
chmod +x ./kubectl
sudo mv ./kubectl /usr/local/bin/kubectl
