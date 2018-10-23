package mywebserver;

import BIF.SWE1.interfaces.Url;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import uebungen.UEB1Impl;

public class Server {

  private ServerSocket serverSocket;
  private Socket socket;
  private BufferedReader in;
  private PrintWriter out;

  /**
   * Constructs a handler thread, squirreling away the socket. All the interesting work is done in
   * the run method.
   */
  public Server(int port) throws IOException {
    this.serverSocket = new ServerSocket(port);
    this.socket = serverSocket.accept();
    this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
  }


  public void start() throws IOException {
    try {
      readMessage();
      writeMessage();
    } finally {
      socket.close();
    }
  }

  private void readMessage() throws IOException {
    final String input = in.readLine();

    if (input == null) {
      return;
    }

    final String[] requestParam = input.split(" "); // GET .. HTTP1.1
    final Url url = new UEB1Impl().getUrl(requestParam[1]);

    System.out.println(url.getPath());
    System.out.println(url.getRawUrl());
    System.out.println(url.getParameter());
  }

  private void writeMessage() throws IOException {
  }


}