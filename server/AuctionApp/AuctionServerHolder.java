package AuctionApp;

/**
* AuctionApp/AuctionServerHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Auction.idl
* Thursday, February 23, 2017 7:00:48 PM CST
*/

public final class AuctionServerHolder implements org.omg.CORBA.portable.Streamable
{
  public AuctionApp.AuctionServer value = null;

  public AuctionServerHolder ()
  {
  }

  public AuctionServerHolder (AuctionApp.AuctionServer initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = AuctionApp.AuctionServerHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    AuctionApp.AuctionServerHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return AuctionApp.AuctionServerHelper.type ();
  }

}
