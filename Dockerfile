# Build the client
FROM bellsoft/liberica-openjdk-alpine-musl:11 AS client-build
COPY . /usr/src/myapp
WORKDIR /usr/src/myapp/src
RUN javac keyvaluestore/server/KeyValueStoreInterface.java keyvaluestore/client/*.java

# Build the server
FROM bellsoft/liberica-openjdk-alpine-musl:11 AS server-build
COPY . /usr/src/myapp
WORKDIR /usr/src/myapp/src
RUN javac keyvaluestore/server/KeyValueStoreInterface.java keyvaluestore/server/*.java

# Command to run the server with the default ports
CMD ["java", "keyvaluestore.server.KeyValueStoreServer", "1111"]
