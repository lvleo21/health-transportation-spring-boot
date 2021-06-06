package com.pbd.project.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Configuration
@EnableScheduling
public class DBAutoBackupController {

    // 0/20 * * * * ? - A cada 20s
    // 1:30 da manhã, todo dia
    @Scheduled(cron = "0/20 * * * * ?")
    public void scheduleDbBackup() {
        String path = "/home/leonardo/Git/PBD_20-1_Leonardo-Veras/backup/backup-health-transportation-" + LocalDateTime.now() + ".sql";


//        System.out.println(System.getProperty("os.name").equalsIgnoreCase("Linux"));
//        System.out.println(System.getProperty("user.home"));
//        System.out.println(path);

//
        try {

            ProcessBuilder pb = null;
            Process p;
            BufferedReader br = null;
            if (System.getProperty("os.name").equalsIgnoreCase("Linux")) {
                pb = new ProcessBuilder("/usr/bin/pg_dump", "--file", path, "--host", "localhost", "--port", "5432",
                        "--username", "postgres", "--no-password", "--verbose", "--format=t", "--blobs", "pbd");
            } else if (System.getProperty("os.name").equalsIgnoreCase("Windows"))
                pb = new ProcessBuilder("C:\\Program Files\\PostgreSQL\\10\\bin\\pg_dump.exe", "-i", "-h", "localhost",
                        "-p", "5432", "-U", "postgres", "-F", "c", "-b", "-v", "-f", path, "Veiculos Pajeu");

            pb.environment().put("PGPASSWORD", "1027210916");
            pb.redirectErrorStream(true);
            p = pb.start();

            final InputStreamReader isr = new InputStreamReader(p.getInputStream());
            br = new BufferedReader(isr);

            String line;
            String temp = null;
            int i = 0;
            while ((line = br.readLine()) != null) {
                if (i == 0) {
                    temp = line;
                }
                System.out.println(temp);
            }

        } catch (Exception e) {
            System.out.println("BACKUP ERROR -> " + e.getMessage());
        }
//

    }



}
