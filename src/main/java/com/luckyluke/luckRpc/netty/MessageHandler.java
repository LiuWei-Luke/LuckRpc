package com.luckyluke.luckRpc.netty;

public interface MessageHandler {
  void handle(Client client, Object object);

  void handle(Server server, Object object);
}
