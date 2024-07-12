package com.efoxdev.literalura;

import com.efoxdev.literalura.principal.Principal;
import com.efoxdev.literalura.repositorios.AutoresRepositorio;
import com.efoxdev.literalura.repositorios.LibrosRepositorio;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {
	@Autowired
	private LibrosRepositorio librosRepositorio;
	@Autowired
	private AutoresRepositorio autoresRepositorio;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(librosRepositorio, autoresRepositorio);
		principal.mostrarMenu();
	}

}
