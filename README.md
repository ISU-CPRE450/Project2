## Project 2: Distributed Auction Service Using CORBA

## Description
TODO


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
