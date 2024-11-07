CLIENT_IMAGE='project-client-image'
PROJECT_NETWORK='project-network'
SERVER_CONTAINER='my-server'

if [ $# -ne 2 ]
then
  echo "Usage: ./run_client.sh <container-name> <port-number>"
  exit
fi

# run client docker container with cmd args
docker run -it --rm --name "$1" \
 --network $PROJECT_NETWORK $CLIENT_IMAGE \
 java keyvaluestore.client.KeyValueStoreClient $SERVER_CONTAINER "$2"
