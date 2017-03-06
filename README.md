# Project 2: Distributed Auction Service Using CORBA

## Description
In this project, I created an auction service using CORBA, written in Java.
The service has a few components:

1) Auction server

2) Buyer client

3) Seller client

Buyer clients may connect to the server to see what auction item is available
to bid on. They may place bids and see if they are the highest bidder.

Seller clients can offer auction items. They can check the status of their
item (whether or not it has been purchased, the highest bidder, etc.). They
can also choose to sell their item if it has at least one bid.

Overall, this was a fun project. I thought it was neat to learn about using
CORBA in Java to create a service.


## Usage
To run this program, execute the following commands in your shell:

To build the file system:

`make`

To start the orb port:

`orbd -ORBInitialPort 1050`

To start the Auction server:

`java -cp server Server -ORBInitialPort 1050 -ORBInitialHost $(hostname)`

To start a client connection as a seller:

`java -cp client SellerClient -ORBInitialPort 1050 -ORBInitialHost $(hostname)`

To start a client connection as a buyer:

`java -cp client BuyerClient -ORBInitialPort 1050 -ORBInitialHost $(hostname)`

You can run `make clean` to clean up the file system
