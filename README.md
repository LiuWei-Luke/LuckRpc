# LuckRpc
A Rpc learning project For self-use.

- 对协议进行封装，使用Zookeeper进行服务注册和发现
- 使用Netty作为传输层进行请求的传送
- 对内容的序列化方式使用的是kryo：
- 最后使用JDK动态代理的方式对服务接口进行代理
- Netty传输层的使用参考了Motan的代码进行学习