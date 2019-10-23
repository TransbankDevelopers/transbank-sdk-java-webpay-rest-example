#!/usr/bin/env bash

#get name of the current directory
NAME=$(basename "$PWD")

#get docker id
ID=$(docker ps -a | grep $NAME | grep Up | awk '{print $1}')

#stop docker by id
echo "Stop docker ID: $ID"
docker stop $ID
