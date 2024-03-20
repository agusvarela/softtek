package com.softtek.interview.integration;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;

@Configuration
@Profile("test")
public class IntegrationTestConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2Server() throws SQLException, IOException {
        int port = findAvailablePort();
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", Integer.toString(port));
    }

    private int findAvailablePort() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(0)) {
            return serverSocket.getLocalPort();
        }
    }
}
