package pokemon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Pokedex {

    private static ArrayList<Pokemon> pokemons = new ArrayList<>();

    public static void setPokemons(ArrayList<Pokemon> pokemons) {
        Pokedex.pokemons = pokemons;
    }

    public ArrayList<Pokemon> getPokemons() {
        return pokemons;
    }

    public void carregarPokemons(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String linha = reader.readLine();

        while ((linha = reader.readLine()) != null) {
            String[] partes = linha.split(",");

            String nome = partes[0];
            TipoPokemon tipo = TipoPokemon.valueOf(partes[1].trim().toUpperCase());
            int vida = Integer.parseInt(partes[2]);
            int ataque = Integer.parseInt(partes[3]);
            int defesa = Integer.parseInt(partes[4]);
            int velocidade = Integer.parseInt(partes[5]);

            Pokemon p = new Pokemon(nome, tipo, vida, ataque, defesa, velocidade);
            pokemons.add(p);
        }

        reader.close();
    }

    public Pokemon buscarPokemonPorNome(String nome) {
        for (Pokemon p : pokemons) {
            if (p.getNome().equalsIgnoreCase(nome)) {
                return p;
            }
        }
        return null;
    }


}