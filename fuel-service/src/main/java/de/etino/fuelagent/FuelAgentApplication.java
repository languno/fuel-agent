package de.etino.fuelagent;

import de.etino.fuelagent.gasstation.GasStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;
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

	// If the zuul proxy has no route for an url and there is no static resource
	// in the tomcat this resolver gets called. It maps the 404-not found to the
	// Angular2 front-end.
	// There the angular router resolves the url to the component or page not
	// found.
	@Bean
	ErrorViewResolver supportPathBasedLocationStrategyWithoutHashes() {
		return new ErrorViewResolver() {
			@Override
			public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status,
												 Map<String, Object> model) {
				return status == HttpStatus.NOT_FOUND
						? new ModelAndView("index.html", Collections.<String, Object> emptyMap(), HttpStatus.OK) : null;
			}
		};
	}
}
