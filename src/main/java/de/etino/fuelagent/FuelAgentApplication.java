package de.etino.fuelagent;

import de.etino.fuelagent.gasstation.diariosur.GasStationsDiariosurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FuelAgentApplication implements CommandLineRunner {

	@Autowired
	GasStationsDiariosurService service;

	public static void main(String[] args) {
		SpringApplication.run(FuelAgentApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(service.getGasStationsPriceList());
	}
}
