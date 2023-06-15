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
		int somaNumero = somaNumeroAleatorio(list);

		if (validaNumeroEscolhido(numeroEscolhido, quantidadeDado)) {

			for (int i = 0; i < quantidadeDado; i++) {
				str += list.get(i) + " ";
			}
			str += " -> " + comparaNumero(numeroEscolhido, somaNumero);

			str +=  " = " +porcentagem(numeroEscolhido, somaNumero) + "% - " + somaNumero;
		}
		return str;
	}

	public boolean validaNumeroEscolhido(int numeroEscolhido, int quantidadeDados) {
		if (numeroEscolhido >= quantidadeDados && numeroEscolhido <= quantidadeDados * 12) {
			return true;
		}
		return false;
	}

	public double porcentagem(int numeroEscolhido, int soma) {
		double porcento;
		if(soma >= numeroEscolhido) {
		porcento = (100 * numeroEscolhido) / soma;
		} else {
			porcento = (100 * soma) / numeroEscolhido;
		}
		return porcento;
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
		for (Integer integer : list) {
			soma += integer;
		}
		return soma;
	}

	public String comparaNumero(int numeroEscolhido, int soma) {
		if (numeroEscolhido == soma) {
			return "Número certo";
		}
		return "Número errado";
	}
}
