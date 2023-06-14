package br.com.trier.springvespertino.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exemplos")
public class exemplos {

	@GetMapping
	public String exemplo1() {
		return "Bem vindo(a)";
	}
	
	@GetMapping("/ex3")
	public String exemplo3(@RequestParam String sexo) {
		if(sexo.equalsIgnoreCase("M")) {
			return "Bem vindo";
		}
		return "Bem vinda";
	}
	
	@GetMapping("/somar/{n1}/{n2}")
	public int somar(@PathVariable int n1,@PathVariable int n2) {
		return n1 + n2;
	}
}
