package treinador;

import pokemon.Pokedex;
import pokemon.Pokemon;

import java.util.Scanner;

public class TreinadorHumano extends Treinador {

    private Scanner scanner = new Scanner(System.in);

    public TreinadorHumano(String nome) {
        super(nome);
    }

    public void escolherPokemon(Pokedex pokedex) {
        System.out.println("Escolha 3 Pokémon para seu time:");
        int escolhidos = 0;

        while (escolhidos < 3) {
            System.out.print("Digite o nome do Pokémon #" + (escolhidos + 1) + ": ");
            String nomeEscolhido = scanner.nextLine();

            Pokemon p = pokedex.buscarPokemonPorNome(nomeEscolhido);
            if (p != null) {
                super.getTime().add(new Pokemon(p)); // Cópia
                escolhidos++;
            } else {
                System.out.println("Pokémon não encontrado. Tente novamente.");
            }
        }
    }
}