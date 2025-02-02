package org.inbank.scoring;

import org.inbank.scoring.config.LocalDevTestcontainersConfig;
import org.springframework.boot.SpringApplication;

public class LocalScoringApplication {

	public static void main(String[] args) {
		SpringApplication
				.from(ScoringApplication::main)
				.with(LocalDevTestcontainersConfig.class)
				.run(args);
	}

}
