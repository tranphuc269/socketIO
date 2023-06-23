package com.example.socketio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sun.misc.Signal;

import java.io.IOException;
import java.lang.management.ManagementFactory;

@SpringBootApplication
public class SocketIoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocketIoApplication.class, args);
        // Lắng nghe sự kiện kill (SIGINT hoặc SIGTERM) để giải phóng cổng
        Signal.handle(new Signal("INT"), signal -> {
            releasePort();
            System.exit(0);
        });
        Signal.handle(new Signal("TERM"), signal -> {
            releasePort();
            System.exit(0);
        });
    }

    private static void releasePort() {
        String processName = ManagementFactory.getRuntimeMXBean().getName();
        String pid = processName.split("@")[0];
        System.out.println("pid : " + pid);
        String releasePortCommand = "kill -9 " + pid;
        try {
            Runtime.getRuntime().exec(releasePortCommand);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
