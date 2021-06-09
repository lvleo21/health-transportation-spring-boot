package com.pbd.project.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Configuration
@EnableScheduling
public class DBAutoBackupController {

    //! "0/20 * * * * ?" - Para backup a cada 20s;
    //! "0 30 1 * * ?" - Para backup agendado para 1:30 A.M;
    @Scheduled(cron = "0 30 1 * * ?")
    public void scheduleDbBackup() {
        try {
            String path = "/home/leonardo/Git/PBD_20-1_Leonardo-Veras/backup/backup-health-transportation-" + LocalDateTime.now() + ".sql";

            Properties props = getProperties();
            String datasourceUrl = props.getProperty("spring.datasource.url");
            List<String> tempUrl = Arrays.asList(datasourceUrl.split("/"));

            String database = tempUrl.get(tempUrl.size()-1);
            String user = props.getProperty("spring.datasource.username");
            String password = props.getProperty("spring.datasource.password");

            ProcessBuilder pb = null;
            Process p;
            BufferedReader br = null;

            if (System.getProperty("os.name").equalsIgnoreCase("Linux")) {
                pb = new ProcessBuilder("/usr/bin/pg_dump", "--file", path, "--host", "localhost", "--port", "5432",
                        "--username", user, "--no-password", "--verbose", "--format=t", "--blobs", database);
            }

            else if (System.getProperty("os.name").equalsIgnoreCase("Windows"))
                pb = new ProcessBuilder("C:\\Program Files\\PostgreSQL\\10\\bin\\pg_dump.exe", "-i", "-h", "localhost",
                        "-p", "5432", "-U", "postgres", "-F", "c", "-b", "-v", "-f", path, "Veiculos Pajeu");

            pb.environment().put("PGPASSWORD", password);
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

    private static Properties getProperties() throws IOException {
        Properties prop = new Properties();
        String path = "src/main/resources/application.properties";
        prop.load(DBAutoBackupController.class.getClassLoader().getResourceAsStream("application.properties"));
        return prop;
    }



}
