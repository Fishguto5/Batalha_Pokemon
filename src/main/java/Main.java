import batalha.Batalha;
import pokemon.Pokedex;
import treinador.TreinadorHumano;
import treinador.TreinadorRobo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Criação e carregamento da pokemon.Pokedex
        Pokedex pokedex = new Pokedex();
        try {
            InputStream is = pokedex.getClass().getClassLoader().getResourceAsStream("ListaPokemons.csv");
            pokedex.carregarPokemons(is);
        } catch (IOException e) {
            System.out.println("Erro ao carregar a pokemon.Pokedex: " + e.getMessage());
            scanner.close();
            return;
        }

        // Criação dos treinadores
        System.out.print("Digite seu nome: ");
        String nomeJogador = scanner.nextLine();


        TreinadorHumano humano = new TreinadorHumano(nomeJogador);
        TreinadorRobo robo = new TreinadorRobo("Robo");

        // Escolha dos Pokémons
        humano.escolherPokemon(pokedex);
        robo.escolherPokemon(pokedex);

        // Início da batalha
        Batalha batalha = new Batalha(humano, robo);
        batalha.iniciarBatalha();

        scanner.close();
    }
}