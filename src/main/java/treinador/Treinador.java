package treinador;

import pokemon.Pokedex;
import pokemon.Pokemon;

import java.util.ArrayList;

public abstract class Treinador {

    private String nome;
    private ArrayList<Pokemon> time;
    private Pokemon pokemonEmCampo;
    private EstadoTreinador estadoTreinador;

    public Treinador(String nome) {
        this.nome = nome;
        this.time = new ArrayList<>();
        estadoTreinador = EstadoTreinador.NEUTRO;
    }

    //Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Pokemon> getTime() {
        return time;
    }


    public Pokemon getPokemonEmCampo() {
        return pokemonEmCampo;
    }

    public void setPokemonEmCampo(Pokemon pokemonEmCampo) {
        this.pokemonEmCampo = pokemonEmCampo;
    }

    public EstadoTreinador getEstadoTreinador() {
        return estadoTreinador;
    }

    public void setEstadoTreinador(EstadoTreinador estadoTreinador) {
        this.estadoTreinador = estadoTreinador;
    }


    public void escolherPokemon(Pokedex pokedex) {
    }
    //Verifica se o time foi derrotado
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


    public Pokemon proximoPokemonDisponivel() {
        for (Pokemon p : this.time) {
            if (!p.isDerrotado()) {
                return p;
            }
        }
        return null;
    }
}