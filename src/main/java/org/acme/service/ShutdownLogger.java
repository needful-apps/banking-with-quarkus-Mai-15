package org.acme.service;

import io.quarkus.runtime.ShutdownEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import lombok.extern.java.Log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

@ApplicationScoped
@Log
public class ShutdownLogger {



    public void onShutdown(@Observes ShutdownEvent event) {
        log.info("Shutting down the application...");

        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        String message = "Heruntergefahren um " + timestamp;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("~/shutdown.log", true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            log.severe("Fehler beim Schreiben der Shutdown-Logdatei" + e.getMessage());
        }
    }
}