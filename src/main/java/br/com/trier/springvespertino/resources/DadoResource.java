package br.com.trier.springvespertino.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dado")
public class DadoResource {

	@GetMapping("/{numeroEscolhido}")
	public String sorteio(@PathVariable int numeroEscolhido, @RequestParam int quantidadeDado) {
		String str = "";
		List<Integer> list = numeroAleatorio(quantidadeDado);

		if (validaNumeroEscolhido(numeroEscolhido, quantidadeDado, list)) {

			for (int i = 0; i < quantidadeDado; i++) {
				str += numeroAleatorio(quantidadeDado).get(i) + " ";
			}
			str += " -> " + comparaNumero(numeroEscolhido, list);

			str += porcentagem(numeroEscolhido, list);
		}
		return str;
	}

	public boolean validaNumeroEscolhido(int numeroEscolhido, int quantidadeDados, List<Integer> list) {
		int soma = somaNumeroAleatorio(list);
		if (numeroEscolhido < quantidadeDados && numeroEscolhido > soma) {
			return false;
		}
		return true;
	}

	public int porcentagem(int numeroEscolhido, List<Integer> list) {
		int valorMaximo = somaNumeroAleatorio(list);

		return (valorMaximo / 100) * numeroEscolhido;
	}

	//
	private List<Integer> numeroAleatorio(int quantidadeDado) {
		Random random = new Random();
		Integer n;
		List<Integer> list = new ArrayList<>();

		for (int i = 0; i < quantidadeDado; i++) {
			do {
				n = random.nextInt(12);
			} while (n == 0 || n > 12);
			list.add(n);
		}
		return list;
	}

	public Integer somaNumeroAleatorio(List<Integer> list) {
		Integer soma = 0;
		return soma += list.stream().mapToInt(Integer::intValue).sum();
	}

	public String comparaNumero(int numeroEscolhido, List<Integer> list) {
		if (numeroEscolhido == somaNumeroAleatorio(list)) {
			return "Número certo";
		}
		return "Número errado";
	}
}
