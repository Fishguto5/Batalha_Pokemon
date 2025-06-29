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
    private EstadoBatalha estado;

    public Batalha(Treinador treinador1, Treinador treinador2) {
        this.treinador1 = treinador1;
        this.treinador2 = treinador2;
        this.turno = 1;
        this.estado = EstadoBatalha.EM_ANDAMENTO;
    }

    public void setTreinador1(Treinador treinador1) {
        this.treinador1 = treinador1;
    }

    public void setTreinador2(Treinador treinador2) {
        this.treinador2 = treinador2;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public EstadoBatalha getEstado() {
        return estado;
    }

    public void setEstado(EstadoBatalha estado) {
        this.estado = estado;
    }

    public void iniciarBatalha() {
        treinador1.setPokemonEmCampo(treinador1.getTime().get(0));
        treinador2.setPokemonEmCampo(treinador2.getTime().get(0));
    }

    private List<String> processarAcao(Treinador atacante, Treinador defensor, Acao acao) {
        List<String> logDaAcao = new ArrayList<>();

        if (acao.getTipo() == TipoAcao.ATACAR) {
            logDaAcao.addAll(realizarAtaque(atacante, defensor));
        } else if (acao.getTipo() == TipoAcao.TROCAR) {
            String msgTroca = realizarTroca(atacante, acao.getIndiceAlvo());
            logDaAcao.add(msgTroca);
        }

        // --- VERIFICAÇÃO PÓS-AÇÃO ---
        if (defensor.getPokemonEmCampo().isDerrotado()) {
            logDaAcao.add(defensor.getPokemonEmCampo().getNome() + " foi derrotado!");
            if (defensor.timeDerrotado(defensor.getTime())) {
                defensor.setEstadoTreinador(EstadoTreinador.PERDEDOR);
                this.estado = EstadoBatalha.FIM_DE_JOGO; // Fim de jogo!
                logDaAcao.add(defensor.getNome() + " não tem mais Pokémon!");
                logDaAcao.add(atacante.getNome() + " venceu a batalha!");
            } else {
                // Se o Pokémon derrotado é do JOGADOR
                if (defensor == treinador1) {
                    this.estado = EstadoBatalha.AGUARDANDO_TROCA_JOGADOR; // >>> PONTO CRÍTICO <<<
                    logDaAcao.add("Você precisa escolher seu próximo Pokémon!");
                } else { // Se o derrotado é do ROBÔ
                    Pokemon proximo = defensor.proximoPokemonDisponivel();
                    defensor.setPokemonEmCampo(proximo);
                    logDaAcao.add(defensor.getNome() + " enviou " + proximo.getNome() + "!");
                }
            }
        }
        return logDaAcao;
    }

    public List<String> executarAcaoJogador(Acao acaoJogador) {
        List<String> logDoTurno = new ArrayList<>();

        // Se o jogador atacar ou fizer uma troca VOLUNTÁRIA
        if (acaoJogador.getTipo() == TipoAcao.ATACAR || acaoJogador.getTipo() == TipoAcao.TROCAR) {
            logDoTurno.addAll(processarAcao(treinador1, treinador2, acaoJogador));
        }

        if (estado == EstadoBatalha.EM_ANDAMENTO) {
            // Lógica simples para o robô: ele sempre ataca.
            Acao acaoRobo = new Acao(TipoAcao.ATACAR);
            logDoTurno.addAll(processarAcao(treinador2, treinador1, acaoRobo));
        }

        turno++;
        return logDoTurno;
    }

    public List<String> trocarPokemonDerrotado(int indiceNovoPokemon) {
        List<String> logDaTroca = new ArrayList<>();
        // Só permite esta ação se o estado for o correto.
        if (estado == EstadoBatalha.AGUARDANDO_TROCA_JOGADOR) {
            String msgTroca = realizarTroca(treinador1, indiceNovoPokemon);
            logDaTroca.add(msgTroca);
            estado = EstadoBatalha.EM_ANDAMENTO; // A batalha volta ao normal!
        }
        return logDaTroca;
    }

    public List<String> executarTurno(Acao acaoTreinador1, Acao acaoTreinador2) {
        List<String> logDoTurno = new ArrayList<>();


        Treinador primeiro = treinador1;
        Acao acaoPrimeiro = acaoTreinador1;
        Treinador segundo = treinador2;
        Acao acaoSegundo = acaoTreinador2;

        // --- TURNO DO PRIMEIRO JOGADOR (HUMANO) ---
        logDoTurno.addAll(processarAcao(primeiro, segundo, acaoPrimeiro));

        // Se o segundo jogador foi derrotado após a ação do primeiro, o turno acaba.
        if (segundo.getEstadoTreinador() == EstadoTreinador.PERDEDOR) {
            turno++;
            return logDoTurno;
        }

        // --- TURNO DO SEGUNDO JOGADOR (ROBÔ) ---
        logDoTurno.addAll(processarAcao(segundo, primeiro, acaoSegundo));

        turno++;
        return logDoTurno;
    }




    public List<String> realizarAtaque(Treinador atacante, Treinador defensor) {
        List<String> logAtaque = new ArrayList<>();
        int dano = Math.abs((atacante.getPokemonEmCampo().getAtaque() - (int)(0.7 * defensor.getPokemonEmCampo().getDefesa())));
        if (dano <= 0) dano = 1; // Garante dano mínimo

        defensor.getPokemonEmCampo().setVida(Math.max(0, defensor.getPokemonEmCampo().getVida() - dano));

        logAtaque.add(atacante.getPokemonEmCampo().getNome() + " usou " + atacante.getPokemonEmCampo().getTipo().getHabilidadePadrao() );
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

