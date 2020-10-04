package com.sensiblemetrics.api.customiere.crm.clients.configuration;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;

@Configuration
@Getter(AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class NettyConfiguration implements WebServerFactoryCustomizer<NettyReactiveWebServerFactory> {

    private final ServerProperties serverProperties;

    @Override
    public void customize(final NettyReactiveWebServerFactory serverFactory) {
        serverFactory.setUseForwardHeaders(true);
        serverFactory.setAddress(this.getServerProperties().getAddress());
        serverFactory.setPort(this.getServerProperties().getPort());
        //serverFactory.addServerCustomizers(new EventLoopNettyCustomizer());
    }

    //    @Bean
//    public DisposableServer disposableServer(final ApplicationContext context) {
//        final ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler(context));
//        final HttpServer httpServer = HttpServer.create().host(properties.getAddress()).port(properties.getPort());
//        return httpServer.handle(adapter).bindNow();
//        //return httpServer.newHandler(adapter).block();
//    }

    @Bean
    public ReactiveWebServerFactory reactiveWebServerFactory() {
        return new NettyReactiveWebServerFactory();
    }

    @Bean
    public HttpHandler httpHandler(final ApplicationContext context) {
        return WebHttpHandlerBuilder.applicationContext(context).build();
    }

//    private static class EventLoopNettyCustomizer implements NettyServerCustomizer {
//
//        @Override
//        public HttpServer apply(HttpServer httpServer) {
//            final EventLoopGroup parentGroup = new NioEventLoopGroup();
//            final EventLoopGroup childGroup = new NioEventLoopGroup();
//            return httpServer.tcpConfiguration(tcpServer -> tcpServer
//                .bootstrap(serverBootstrap -> serverBootstrap
//                    .group(parentGroup, childGroup)
//                    .channel(NioServerSocketChannel.class)));
//        }
//    }

//    private class NettyWebServerFactorySslCustomizer
//        implements WebServerFactoryCustomizer<NettyReactiveWebServerFactory> {
//
//        @Override
//        public void customize(NettyReactiveWebServerFactory serverFactory) {
//            final Ssl ssl = new Ssl();
//            ssl.setEnabled(true);
//            ssl.setKeyStore("classpath:sample.jks");
//            ssl.setKeyAlias("alias");
//            ssl.setKeyPassword("password");
//            ssl.setKeyStorePassword("secret");
//            Http2 http2 = new Http2();
//            http2.setEnabled(false);
//            serverFactory.addServerCustomizers(new SslServerCustomizer(ssl, http2, null));
//            serverFactory.setPort(8443);
//        }
//    }
}
