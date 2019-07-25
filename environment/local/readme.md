# How to start miniKube on a VM for local development

## Create VM
You can execute **"executeVagrantUp"** gradle task, that will create a VM with minikube installed, this task **required Vagrant** is installed on developer machine.

## Start Minikube
When you already have the VM up and running you could using **"startMinikube"** gradle task, that task will connect through ssh into VM and execute script **"/environment/locl/shellscript/startMinikube.sh"** 

