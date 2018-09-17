 RecordsKeeper-JAVA-SDK
 =========================
 [![Build Status](https://travis-ci.com/RecordsKeeper/recordskeeper-java-sdk.svg?branch=master)](https://travis-ci.org/RecordsKeeper/recordskeeper-java-sdk)

It is an infrastructure to build RecordsKeeper blockchain-based java applications, products and is used to work around applications that are built on top of this blockchain platform. This sdk provides support for all the java applications by acting as a client to interact with the RecordsKeeper blockchain directly. You just have to connect with the credentials of a RecordsKeeper node to use this java library.

**Note:** If you're looking for the RecordsKeeper Java Library please see: [RecordsKeeper Java Library](https://github.com/RecordsKeeper/recordskeeper-java-sdk/tree/master/doc/RecordsKeeperJava)


## Getting Started

Before you begin you need to setup Java Development Environment.

Import these java libraries first to get started with the classes and functions of this java library.


```bash
import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import RecordsKeeperJavaLib.Config;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.Arrays;
```


Creating Connection
-------------------

Entry point for accessing Address class resources.

Make Config file to import config parameters directly into the applications using this library.
   
Importing chain url and chain name from config file:

* URL: Url to connect to the chain ([RPC Host]:[RPC Port])
* Chain-name: chain name

```bash
    url = Config.getProperty("url");
    chain = Config.getProperty("chain");
``` 

Node Authentication
-------------------

Importing user name and password values from config file to authenticate the node:

* User name: The rpc user is used to call the APIs.
* Password: The rpc password is used to authenticate the APIs.

```bash   
    rkuser = Config.getProperty("rkuser");
    passwd = Config.getProperty("passwd");
``` 
## Java applications
For Java applications use the latest published v1.3.x releases:
```
     <!-- https://mvnrepository.com/artifact/org.hyperledger.java/RecordsKeeperJavaLib -->
     <dependency>
         <modelVersion>4.0.0</modelVersion>
         <groupId>org.recordskeeper.java</groupId>
         <artifactId>RecordsKeeperJavaLib</artifactId>
         <version>0.0.1-SNAPSHOT</version>
     </dependency>

```

Setting Up Eclipse
------------------
To get started using the Fabric Java SDK with Eclipse, refer to the instructions at: ./docs/EclipseSetup.md

## Libraries

- [Address](https://github.com/RecordsKeeper/recordskeeper-java-sdk/blob/master/src/RecordsKeeperJavaLib/address.java) Library to work with RecordsKeeper addresses. You can generate new address, check all addresses, check address validity, check address permissions, check address balance by using Address class. You just have to pass parameters to invoke the pre-defined functions.

- [Assets](https://github.com/RecordsKeeper/recordskeeper-java-sdk/blob/master/src/RecordsKeeperJavaLib/Assets.java) Library to work with RecordsKeeper assets. You can create new assets and list all assets by using Assets class. You just have to pass parameters to invoke the pre-defined functions.

- [Block](https://github.com/RecordsKeeper/recordskeeper-java-sdk/blob/master/src/RecordsKeeperJavaLib/block.java) Library to work with RecordsKeeper block informaion. You can collect block information by using block class. You just have to pass parameters to invoke the pre-defined functions.

- [Blockchain](https://github.com/RecordsKeeper/recordskeeper-java-sdk/blob/master/src/RecordsKeeperJavaLib/Blockchain.java) Library to work with RecordsKeeper block informaion. You can collect block information by using block class. You just have to pass parameters to invoke the pre-defined functions.

- [Permissions](https://github.com/RecordsKeeper/recordskeeper-java-sdk/blob/master/src/RecordsKeeperJavaLib/Permissions.java) Library to work with RecordsKeeper permissions. You can grant and revoke permissions like connect, send, receive, create, issue, mine, activate, admin by using Assets class. You just have to pass parameters to invoke the pre-defined functions.

- [Stream](https://github.com/RecordsKeeper/recordskeeper-java-sdk/blob/master/src/RecordsKeeperJavaLib/stream.java) Library to work with RecordsKeeper streams. You can publish, retrieve and verify stream data by using stream class. You just have to pass parameters to invoke the pre-defined functions.

- [Transaction](https://github.com/RecordsKeeper/recordskeeper-java-sdk/blob/master/src/RecordsKeeperJavaLib/transactions.java) Library to work with RecordsKeeper transactions. You can send transaction, create raw transaction, sign raw transaction, send raw transaction, send signed transaction, retrieve transaction information and calculate transaction's fees by using transaction class. You just have to pass parameters to invoke the pre-defined functions.

- [Wallet](https://github.com/RecordsKeeper/recordskeeper-java-sdk/blob/master/src/RecordsKeeperJavaLib/Wallet.java) Library to work with RecordsKeeper wallet functionalities. You can create wallet, dump wallet into a file, backup wallet into a file, import wallet from a file, lock wallet, unlock wallet, change wallet's password, retrieve private key, retrieve wallet's information, sign and verify message by using wallet class. You just have to pass parameters to invoke the pre-defined functions.


## SDK dependencies
SDK depends on few third party libraries that must be included in your classpath when using the JAR file. To get a list of dependencies, refer to pom.xml file or run
<code>mvn dependency:tree</code> or <code>mvn dependency:list</code>.
Alternatively, <code> mvn dependency:analyze-report </code> will produce a report in HTML format in target directory listing all the dependencies in a more readable format.

To build this project, the following dependencies must be met
 * JDK 1.8 or above
 * Apache Maven 3.5.0

### Compiling

Once your JAVA_HOME points to your installation of JDK 1.8 (or above) and JAVA_HOME/bin and Apache maven are in your PATH, issue the following command to build the jar file:
<code>
  mvn install
</code>
or
<code>
  mvn install -DskipTests
</code> if you don't want to run the unit tests

### Running the unit tests
To run the unit tests, please use <code>mvn install</code> which will run the unit tests and build the jar file.

**Many unit tests will test failure condition's resulting in exceptions and stack traces being displayed. This is not an indication of failure!**

**[INFO] BUILD SUCCESS**  **_At the end is usually a very reliable indication that all tests have passed successfully!_**


## Documentation

The complete docs are here: [RecordsKeeper java library documentation](https://github.com/RecordsKeeper/recordskeeper-java-sdk/tree/master/doc/RecordsKeeperJava).

