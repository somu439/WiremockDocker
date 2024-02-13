#!/bin/bash

# Define variables
WIREMOCK_IMAGE="wiremock/wiremock"
CONTAINER_NAME="wiremock"
MAPPINGS_DIR="/Users/Sreeni/Documents/Wiremock"

# Start WireMock container
docker run -it -d --name $CONTAINER_NAME -p 8080:8080 -v $MAPPINGS_DIR:/home/wiremock $WIREMOCK_IMAGE:3.3.1

# Copy mappings to container (if needed)
# docker cp /local/path/to/mappings $CONTAINER_NAME:/home/wiremock/mappings


# Stop WireMock container
# docker stop $CONTAINER_NAME
# docker rm $CONTAINER_NAME


# docker cp /Users/Sreeni/Documents/Wiremock/. wiremock:/home/wiremock


