 RecordsKeeper-JAVA-SDK
 =========================
 [![Build Status](https://travis-ci.org/RecordsKeeper/recordskeeper-java-sdk.svg?branch=master)](https://travis-ci.org/RecordsKeeper/recordskeeper-java-sdk)

It is an infrastructure to build RecordsKeeper blockchain-based applications, products and is used to work around applications that are built on top of this blockchain platform.

**Note:** If you're looking for the RecordsKeeper Java Library please see: [RecordsKeeper Java Library](https://github.com/RecordsKeeper/recordskeeper-java-sdk/tree/master)


## Getting Started

Before you begin you need to setup Java Development Environment.  


Import these java libraries first to get started with the library classes and functions.


```bash
import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import package2.Config;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.Arrays;
```


Creating Connection
-------------------

Entry point for accessing Address class resources.

Make Config file to import config parameters.
   
Importing chain url and chain name from config file:

* URL: Url to connect to the chain ([RPC Host]:[RPC Port])
* Chain-name: chain name

```bash

    url = network['url']
    chain = network['chain']

``` 

Node Authentication
-------------------

Importing user name and password values from config file to authenticate the node:

* User name: The rpc user is used to call the APIs.
* Password: The rpc password is used to authenticate the APIs.

```bash
    
    user = network['rkuser']
    password = network['passwd']

``` 

## Libraries

- [Addresses](https://github.com/RecordsKeeper/recordskeeper-java-sdk/blob/master/src/package2/address.java) Library to work with RecordsKeeper addresses. You can generate new address, check all addresses, check address validity, check address permissions, check address balance by using Address class. You just have to pass parameters to invoke the pre-defined functions.

- [Assets](https://github.com/RecordsKeeper/recordskeeper-java-sdk/blob/master/src/package2/Assets.java) Library to work with RecordsKeeper assets. You can create new assets and list all assets by using Assets class. You just have to pass parameters to invoke the pre-defined functions.

- [Block](https://github.com/RecordsKeeper/recordskeeper-java-sdk/blob/master/src/package2/block.java) Library to work with RecordsKeeper block informaion. You can collect block information by using block class. You just have to pass parameters to invoke the pre-defined functions.

- [Blockchain](https://github.com/RecordsKeeper/recordskeeper-java-sdk/blob/master/src/package2/blockchain.java) Library to work with RecordsKeeper block informaion. You can collect block information by using block class. You just have to pass parameters to invoke the pre-defined functions.

- [Permissions](https://github.com/RecordsKeeper/recordskeeper-java-sdk/blob/master/src/package2/Permissions.java) Library to work with RecordsKeeper permissions. You can grant and revoke permissions like connect, send, receive, create, issue, mine, activate, admin by using Assets class. You just have to pass parameters to invoke the pre-defined functions.

- [Stream](https://github.com/RecordsKeeper/recordskeeper-java-sdk/blob/master/src/package2/stream.java) Library to work with RecordsKeeper streams. You can publish, retrieve and verify stream data by using stream class. You just have to pass parameters to invoke the pre-defined functions.

- [Transaction](https://github.com/RecordsKeeper/recordskeeper-java-sdk/blob/master/src/package2/transactions.java) Library to work with RecordsKeeper transactions. You can send transaction, create raw transaction, sign raw transaction, send raw transaction, send signed transaction, retrieve transaction information and calculate transaction's fees by using transaction class. You just have to pass parameters to invoke the pre-defined functions.

- [Wallet](https://github.com/RecordsKeeper/recordskeeper-java-sdk/blob/master/src/package2/Wallet.java) Library to work with RecordsKeeper wallet functionalities. You can create wallet, dump wallet into a file, backup wallet into a file, import wallet from a file, lock wallet, unlock wallet, change wallet's password, retrieve private key, retrieve wallet's information, sign and verify message by using wallet class. You just have to pass parameters to invoke the pre-defined functions.


## Unit Tests

Under recordskeeper_java_lib/test using test data from config.java file. 


## Documentation

The complete docs are here: [RecordsKeeper java library documentation](https://github.com/RecordsKeeper/recordskeeper-java-sdk/tree/master/doc).

