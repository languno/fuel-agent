package de.etino.fuelagent;

import de.etino.fuelagent.gasstation.GasStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class FuelAgentApplication implements CommandLineRunner {

	private static long DATA_COLLECTION_INTERVALL_MIN = 30;

	@Autowired
	GasStationService service;

	public static void main(String[] args) {
		SpringApplication.run(FuelAgentApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.scheduleAtFixedRate(() -> service.collectAndPersistData(),
				0, DATA_COLLECTION_INTERVALL_MIN, TimeUnit.MINUTES);
	}
}
