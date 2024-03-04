1. Reset mappings: post, **http://localhost:8080/__admin/mappings/reset**
2. Request url: Get, **http://localhost:8080/v3.1/name/india**
json body
**{
    "firstname":"india"
}**
3. Copy the local files to docker container:
**docker cp /local/path/to/mappings $CONTAINER_NAME:/home/wiremock/mapping**

4. Start WireMock container
**docker run -it -d --name $CONTAINER_NAME -p 8080:8080 -v $LOCAL_WIREMOCK:/home/wiremock $WIREMOCK_IMAGE:3.3.1**

5. Delayed response (globally): post, **http://localhost:8080/__admin/settings**
   **{
    "fixedDelay": 2500
    }**
6. Request XML from API
<outer><inner>india1</inner></outer>
