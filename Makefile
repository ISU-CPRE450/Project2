all:
	idlj -fall Auction.idl
	javac AuctionApp/*.java
	javac client/*.java
	javac server/*.java
	cp -r AuctionApp server/
	cp -r AuctionApp client/

.PHONY: clean
clean:
	-rm client/*.class
	-rm server/*.class
	-rm -rf AuctionApp/
	-rm -rf server/AuctionApp/
	-rm -rf client/AuctionApp/
	-rm -rf orb.db/
