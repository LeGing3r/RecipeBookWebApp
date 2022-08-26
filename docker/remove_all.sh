./stop_all.sh
docker rm $(docker ps -a -q)
docker volume prune -f