package Model;

import Control.btlController;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

public class modNetwork extends  Thread {

    public static final boolean CONNECT_AS_SERVER = true;
    public static final boolean CONNECT_AS_CLIENT = false;

    private btlController      pController;
    private static String      sErrMsg;
    private static boolean     bServer;
    private static InetAddress ipAddress;
    private static int         ipPort;

    private static Thread      pThread;

    ServerSocket               serverSocket;
    Socket                     clientSocket;

    private final Object       lock = new Object();

    private static ObjectInputStream inStream;
    private static ObjectOutputStream outStream;

    public modNetwork( btlController pCtl ) {
        pController = pCtl;
        bServer = true;
        ipAddress = null;
        ipPort = 0;
        serverSocket = null;
        clientSocket = null;
        pThread = null;
    }

    public void CloseConnect() {
        sErrMsg = "";
        synchronized (lock) {
            try {
                if (serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                sErrMsg += "CloseConnect : serverSocket - " + e.getMessage() + "\n";
            }
            try {
                if (clientSocket != null) clientSocket.close();
            } catch (IOException e) {
                sErrMsg += "CloseConnect : clientSocket - " + e.getMessage() + "\n";
            }
        }
        try {
            this.join();
        } catch (InterruptedException e) {
            sErrMsg += "CloseConnect : Join - " + e.getMessage() + "\n";
        }
        pThread = null;
        System.out.println( sErrMsg );
    }

    public boolean OpenConnect( boolean bSrv, String sIP, String sPort ) {
        boolean ret = true;
        if ( pThread != null ) return false;
        try {
            bServer = bSrv;
            ipAddress = InetAddress.getByName( sIP );
            ipPort = Integer.valueOf( sPort );
        }
        catch (UnknownHostException e) {
            sErrMsg += "OpenConnect : " + e.getMessage() + "\n";
            ret = false;
        }
        System.out.print( sErrMsg );
        if  ( ret  ) {
// create connect thread
            pThread = new Thread("Connect"){
                @Override
                public void run(){
// *********************************************
                    sErrMsg = "";
                    try {
                        if ( bServer ) {
                            serverSocket = new ServerSocket( ipPort, 1, ipAddress );
//System.out.println("server socket created. wait....");
                            clientSocket = serverSocket.accept();
//System.out.println("************ client socket created");
                            serverSocket.close();
                            serverSocket = null;
                        }
                        else
                            clientSocket = new Socket( ipAddress, ipPort );
                        try {
                            outStream = new ObjectOutputStream( clientSocket.getOutputStream() );
                            inStream = new ObjectInputStream( clientSocket.getInputStream() );
                            sendMsg("{ \"operation\": \"setname\", \"name\": \"" + pController.getGamerName() + "\" }" );
                            while ( true ) {
                                Object o = inStream.readObject();
                                pController.gotNetworkMessage( new JSONObject( (String)o ) );
                            }
                        }
                        catch( Exception e ) {
                            sErrMsg += "Finish Streams Exception : " + e.getMessage() + "\n";
                        }
                        finally {
                            synchronized (lock) {
                                sErrMsg += "Close Streams\n";
                                try {
                                    if (inStream != null) inStream.close();
                                    inStream = null;
                                } catch (IOException e) {
                                    sErrMsg += "Close Input Stream Exception : " + e.getMessage() + "\n";
                                }
                                try {
                                    if (outStream != null) outStream.close();
                                    outStream = null;
                                } catch (IOException e) {
                                    sErrMsg += "Close Output Stream Exception : " + e.getMessage() + "\n";
                                }
                            }
                        }
                    }
                    catch( UnknownHostException e ) {
                        sErrMsg += "Client Exception : " + e.getMessage() + "\n";
                    }
                    catch( IOException e ) {
                        sErrMsg += "Server Exception : " + e.getMessage() + "\n";
                    }
                    finally {
                        sErrMsg += "Close Socket\n";
                        synchronized (lock) {
                            try {
                                if (serverSocket != null) {
                                    serverSocket.close();
                                    serverSocket = null;
                                } else if (clientSocket != null) {
                                    clientSocket.close();
                                    clientSocket = null;
                                }
                            } catch (IOException e) {
                                sErrMsg += "Close Socket Exception : " + e.getMessage() + "\n";
                            }
                        }
                    }
// *********************************************
                }
            };
// create connect thread
            pThread.start();
System.out.println("Thread started");
        }
        return ret;
    }

    public void sendMsg( String s ) {
        try {
            outStream.writeObject(s);
            outStream.flush();
        } catch ( IOException e ) {
System.out.println( "Send Message Exception : " + e.getMessage() + "\n" );
        }
    }

    public static ArrayList<String> EnumNetworkInterfaces() {
        ArrayList<String> rets = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> enumNI = NetworkInterface.getNetworkInterfaces();
            for ( NetworkInterface netInterface : Collections.list( enumNI ) ) {
                if ( netInterface.isUp() ) {
                    for (InterfaceAddress interfaceAddress : netInterface.getInterfaceAddresses()) {
                        if ( !( ( interfaceAddress.getBroadcast() == null ) && ( interfaceAddress.getNetworkPrefixLength() != 8 ) ) ) {
//                            System.out.println(String.format("IPv4: %s; Subnet Mask: %s; Broadcast: %s", interfaceAddress.getAddress(), interfaceAddress.getNetworkPrefixLength(), interfaceAddress.getBroadcast()));
                            rets.add( interfaceAddress.getAddress().getHostAddress().toString() );
                        } else {
//                            System.out.println(String.format("IPv6: %s; Network Prefix Length: %s", interfaceAddress.getAddress(), interfaceAddress.getNetworkPrefixLength()));
                        }
                    }
                }
            }
        } catch ( SocketException ex ) {
            String msg = ex.getMessage();
System.out.println( "EnumNetworkInterfaces error - " + msg );
        }
        return rets;
    }

}
