package br.com.trier.springvespertino.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dado")
public class DadoResource {

	@GetMapping("/{numeroEscolhido}")
	public ResponseEntity<String> sorteio(@PathVariable int numeroEscolhido, @RequestParam int quantidadeDado) {
		String str = null;
		List<Integer> list = numeroAleatorio(quantidadeDado);
		int somaNumero = somaNumeroAleatorio(list);

		if (validaNumeroEscolhido(numeroEscolhido, quantidadeDado)) {

				str = "Números: ";
			
			for (int i = 0; i < quantidadeDado; i++) {
				str += list.get(i) + " ";
			}
			str += "\nO número que você escolheu está: " 
			+ comparaNumero(numeroEscolhido, somaNumero);

			str += "\nO valor total de todos os dados é: " + somaNumero;
		
			str += "\nDiferença entre o número escolhido e o valor total em porcentagem: "
					+ porcentagem(numeroEscolhido, somaNumero) + "%";
		} else {
			str = "Números inválidos";
		}
		return str != null ? ResponseEntity.ok(str) : ResponseEntity.noContent().build();
	}

	private boolean validaNumeroEscolhido(int numeroEscolhido, int quantidadeDados) {
		if (numeroEscolhido >= quantidadeDados && numeroEscolhido <= quantidadeDados * 6) {
			return true;
		}
		return false;
	}

	private int porcentagem(int numeroEscolhido, int soma) {
		int porcento;
		
		if(numeroEscolhido == soma) {
			return porcento = 0;
		}
		else if(soma >= numeroEscolhido) {
			int diferenca = Math.abs(numeroEscolhido - soma);
		porcento = (100 * diferenca) / soma;
		} else {
			int diferenca = Math.abs(numeroEscolhido - soma);
			porcento = (100 * diferenca) / numeroEscolhido;
		}
		return porcento;
	}

	private List<Integer> numeroAleatorio(int quantidadeDado) {
		Random random = new Random();
		Integer n;
		List<Integer> list = new ArrayList<>();

		for (int i = 0; i < quantidadeDado; i++) {
			do {
				n = random.nextInt(6);
			} while (n == 0 || n > 6);
			list.add(n);
		}
		return list;
	}

	private Integer somaNumeroAleatorio(List<Integer> list) {
		Integer soma = 0;
		for (Integer integer : list) {
			soma += integer;
		}
		return soma;
	}

	private String comparaNumero(int numeroEscolhido, int soma) {
		if (numeroEscolhido == soma) {
			return "--CERTO!!-- ";
		}
		return "--ERRADO-- ";
	}
}
