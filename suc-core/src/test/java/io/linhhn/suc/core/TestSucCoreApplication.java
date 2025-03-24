package io.linhhn.suc.core;

import org.springframework.boot.SpringApplication;

public class TestSucCoreApplication {

	public static void main(String[] args) {
		SpringApplication.from(SucCoreApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
