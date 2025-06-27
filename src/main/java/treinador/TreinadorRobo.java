package treinador;

import pokemon.Pokedex;
import pokemon.Pokemon;

import java.util.ArrayList;
import java.util.Random;

public class TreinadorRobo extends Treinador {

    private Random random = new Random();

    public TreinadorRobo(String nome) {
        super(nome);
    }

    @Override
    public void escolherPokemon(Pokedex pokedex) {
        System.out.println("\n" + getNome() + " está escolhendo seus Pokémons...");
        ArrayList<Pokemon> pokemons = pokedex.getPokemons();
        ArrayList<Integer> indicesEscolhidos = new ArrayList<>();

        while (getTime().size() < 3) {
            int indice = random.nextInt(pokemons.size());
            if (!indicesEscolhidos.contains(indice)) {
                indicesEscolhidos.add(indice);
                Pokemon escolhido = pokemons.get(indice);
                getTime().add(new Pokemon(escolhido)); // cópia
                System.out.println(getNome() + " escolheu " + escolhido.getNome());
            }
        }
    }
}