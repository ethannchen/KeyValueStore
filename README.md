# Project2: Key Value Store with Java RMI
Student: Yichen Yan

## 1. Build server and client 
1. unzip the project package
2. make sure docker daemon is running in the background
3. open a terminal and route to the root folder (same level of this README file)
4. use `sh deploy.sh` to build server and client docker images

## 2. Run server and client 
1. server will be running automatically after you run `deploy.sh`
2. open a new terminal at the same folder 
3. run client: `sh run_client.sh my-cient 1111`
4. check logs in both server and client terminal: data will be pre-populate, automatic operations will be performed (5 PUTs 5 GETs 5 DELETEs), and then enter the interactive mode where user is able to send request to the server 
5. multiple clients: open another terminal and run `sh run_client.sh my-cient 1111`


## 3. Executive summary
### 3.1 Assignment overview

The purpose of Project 2 is to extend the functionality of Project 1 by incorporating Remote Procedure Calls (RPC) for client-server communication and enhancing the server architecture to support multi-threading. In Project 1, the key-value store server was designed to handle basic operations—PUT, GET, and DELETE—using either UDP or TCP communication protocols, with a focus on single-threaded operation. In this second phase, the emphasis shifts to enabling more robust and efficient interactions between the client and server through RPC, allowing for potentially different programming languages to be used and facilitating a more scalable approach to communication. Additionally, by implementing multi-threading, the server will be capable of handling multiple concurrent client requests simultaneously, significantly improving its responsiveness and overall performance. This involves designing a thread management strategy, such as using thread pools, and ensuring proper mutual exclusion to handle shared data safely. Overall, Project 2 aims to reinforcing key concepts of network programming, concurrency, and data management.


### 3.2 Technical Impression
Working on this assignment gave me valuable hands-on experience with Java RMI (Remote Method Invocation). I learned how RMI allows the client and server to communicate seamlessly, making it easier to send requests and receive responses. Setting up the server to handle multiple client requests simultaneously was a significant challenge. However, once I understood the threading model in Java, I was able to implement a multi-threaded server that could handle concurrent operations effectively.

One of the most important features I implemented was the logging system, which included auto-assigned client IDs in both the client and server logs. This was crucial for tracking interactions and debugging issues. Seeing the logs show each client's unique ID helped me understand whether there were any errors or delays in processing their requests.

I also faced difficulties when integrating the project with Docker. I faced difficulties in creating Docker images and running containers properly. Initially, I struggled with network settings and ensuring that the client and server could communicate when running in separate containers. After several attempts and some research, I finally figured out how to set up. This experience taught me the importance of understanding Docker, which are crucial for deploying applications effectively.