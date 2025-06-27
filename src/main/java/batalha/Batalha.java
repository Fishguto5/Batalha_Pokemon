package batalha;

import pokemon.EstadoPokemon;
import pokemon.Pokemon;
import treinador.EstadoTreinador;
import treinador.Treinador;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Batalha {

    private Treinador treinador1;
    private Treinador treinador2;
    private int turno;

    public Batalha(Treinador treinador1, Treinador treinador2) {
        this.treinador1 = treinador1;
        this.treinador2 = treinador2;
        this.turno = 1;
    }

    public void iniciarBatalha() {
        treinador1.setPokemonEmCampo(treinador1.getTime().get(0));
        treinador2.setPokemonEmCampo(treinador2.getTime().get(0));
    }

    public List<String> executarTurno(Acao acaoTreinador1, Acao acaoTreinador2) {
        List<String> logDoTurno = new ArrayList<>();

        if (acaoTreinador1.getTipo() == TipoAcao.ATACAR) {
            logDoTurno.addAll(realizarAtaque(treinador1, treinador2));
            if (treinador2.getPokemonEmCampo().isDerrotado()) {
                logDoTurno.add(treinador2.getPokemonEmCampo().getNome() + " foi derrotado!");
                treinador2.getPokemonEmCampo().setEstado(EstadoPokemon.MORTO);
                Pokemon proximoPokemon = treinador2.proximoPokemonDisponivel();
                treinador2.setPokemonEmCampo(proximoPokemon);
                if(treinador2.timeDerrotado(treinador2.getTime())) {
                    treinador2.setEstadoTreinador(EstadoTreinador.PERDEDOR);
                    logDoTurno.add(treinador2.getNome() + " não tem mais Pokémon!");
                    logDoTurno.add(treinador1.getNome() + " venceu a batalha!");
                }
            }
        }

        if(treinador2.getEstadoTreinador() == EstadoTreinador.PERDEDOR) {
            turno++;
            return logDoTurno;
        }

        if (acaoTreinador2.getTipo() == TipoAcao.ATACAR) {
            logDoTurno.addAll(realizarAtaque(treinador2, treinador1));
            if (treinador1.getPokemonEmCampo().isDerrotado()) {
                logDoTurno.add(treinador1.getPokemonEmCampo().getNome() + " foi derrotado!");
                treinador1.getPokemonEmCampo().setEstado(EstadoPokemon.MORTO);
                Pokemon proximoPokemon = treinador1.proximoPokemonDisponivel();
                treinador1.setPokemonEmCampo(proximoPokemon);
                if(treinador1.timeDerrotado(treinador1.getTime())) {
                    treinador1.setEstadoTreinador(EstadoTreinador.PERDEDOR);
                    logDoTurno.add(treinador1.getNome() + " não tem mais Pokémon!");
                    logDoTurno.add(treinador2.getNome() + " venceu a batalha!");
                }
            }
        }

        turno++;
        return logDoTurno;
    }


    public List<String> realizarAtaque(Treinador atacante, Treinador defensor) {
        List<String> logAtaque = new ArrayList<>();
        int dano = Math.abs((atacante.getPokemonEmCampo().getAtaque() - (int)(0.7 * defensor.getPokemonEmCampo().getDefesa())));
        if (dano <= 0) dano = 1; // Garante dano mínimo

        defensor.getPokemonEmCampo().setVida(Math.max(0, defensor.getPokemonEmCampo().getVida() - dano));

        logAtaque.add(atacante.getPokemonEmCampo().getNome() + " usou um ataque!");
        logAtaque.add(defensor.getPokemonEmCampo().getNome() + " sofreu " + dano + " de dano.");

        return logAtaque;
    }

    public String realizarTroca(Treinador treinador, int indiceNovoPokemon) {
        if (indiceNovoPokemon >= 0 && indiceNovoPokemon < treinador.getTime().size()) {
            Pokemon novoPokemon = treinador.getTime().get(indiceNovoPokemon);
            if (!novoPokemon.isDerrotado() && novoPokemon != treinador.getPokemonEmCampo()) {
                treinador.setPokemonEmCampo(novoPokemon);
                return treinador.getNome() + " enviou " + novoPokemon.getNome() + "!";
            }
        }
        return "Troca inválida.";
    }

    public Treinador getTreinador1() { return treinador1; }
    public Treinador getTreinador2() { return treinador2; }
    public int getTurno() { return turno; }
}

