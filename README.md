# GameOfLife

This repository has a simple GameOfLife implementation in Java.

# Overview

## code

This contains the source code for the Game Of Life code implementation.

## provision

This folder holds two subfolders `aws` and `docker`

### docker

This directory holds the necessary images for the local stack. 

Each image is in its own entire folder.

# Root

In the root of the repo there are three os specific `docker-compose.<OS>.yml` files. Choose the one that represents your operating system and rename it to `docker-compose.yml` by running the following command: 

### Linux

`mv docker-compose.linux.yml docker-compose.yml`  

### Windows

`rename docker-compose.windows.yml docker-compose.yml` 

### MacOS

`mv docker-compose.macos.yml docker-compose.yml` 

# Getting started

The first place to start is reading [Getting Started](GETTING_STARTED.md)

Next all questions should be asked to team lead concerning application layout and to DevOps concerning infrastructure. 