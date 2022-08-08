@echo off
docker network create devh-network
docker-compose up -d
pause