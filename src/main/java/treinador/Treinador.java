package treinador;

import pokemon.Pokedex;
import pokemon.Pokemon;

import java.util.ArrayList;

public class Treinador {

    private String nome;
    private ArrayList<Pokemon> time;
    private Pokemon pokemonEmCampo;

    public EstadoTreinador getEstadoTreinador() {
        return estadoTreinador;
    }

    public void setEstadoTreinador(EstadoTreinador estadoTreinador) {
        this.estadoTreinador = estadoTreinador;
    }

    private EstadoTreinador estadoTreinador;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Pokemon> getTime() {
        return time;
    }

    public void setTime(ArrayList<Pokemon> time) {
        this.time = time;
    }

    public Pokemon getPokemonEmCampo() {
        return pokemonEmCampo;
    }

    public void setPokemonEmCampo(Pokemon pokemonEmCampo) {
        this.pokemonEmCampo = pokemonEmCampo;
    }

    public void escolherPokemon(Pokedex pokedex) {
    }

    public boolean timeDerrotado(ArrayList<Pokemon> time) {
        int pokeDerrotado = 0;
        for (Pokemon p : time) {
            if (p.isDerrotado()) {
                pokeDerrotado++;
            }
        }
        if(pokeDerrotado >= 3) {
            estadoTreinador = EstadoTreinador.PERDEDOR;
            return true;
        }else{
            return false;
        }
    }

    public Treinador(String nome) {
        this.nome = nome;
        this.time = new ArrayList<>();
        estadoTreinador = EstadoTreinador.NEUTRO;
    }
}
