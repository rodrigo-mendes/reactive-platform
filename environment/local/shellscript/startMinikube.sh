#!/usr/bin/env bash

sudo minikube start --vm-driver=none

sudo minikube addons enable dashboard

sudo minikube dashboard --url