package batalha;

import pokemon.Pokemon;
import pokemon.TipoPokemon;
import treinador.EstadoTreinador;
import treinador.Treinador;
import treinador.TreinadorHumano;

import java.util.ArrayList;
import java.util.List;

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
    //Getters
    public EstadoBatalha getEstado() {
        return estado;
    }
    public Treinador getTreinador1() { return treinador1; }
    public Treinador getTreinador2() { return treinador2; }
    //Responsável por iniciar a lógica da batalha
    public void iniciarBatalha() {
        treinador1.setPokemonEmCampo(treinador1.getTime().get(0));
        treinador2.setPokemonEmCampo(treinador2.getTime().get(0));
    }

    private ArrayList<String> processarAcao(Treinador atacante, Treinador defensor, Acao acao) {
        ArrayList<String> logDaAcao = new ArrayList<>();

        if (acao.getTipo() == TipoAcao.ATACAR) {
            logDaAcao.addAll(realizarAtaque(atacante, defensor));
        } else if (acao.getTipo() == TipoAcao.TROCAR) {
            String msgTroca = realizarTroca(atacante, acao.getIndiceAlvo());
            logDaAcao.add(msgTroca);
        }

        if (defensor.getPokemonEmCampo().isDerrotado()) {
            logDaAcao.add(defensor.getPokemonEmCampo().getNome() + " foi derrotado!");
            if (defensor.timeDerrotado(defensor.getTime())) {
                defensor.setEstadoTreinador(EstadoTreinador.PERDEDOR);
                this.estado = EstadoBatalha.FIM_DE_JOGO; // Fim de jogo!
                logDaAcao.add(defensor.getNome() + " não tem mais Pokémon!");
                logDaAcao.add(atacante.getNome() + " venceu a batalha!");
            } else {
                // Se o Pokémon derrotado é do Usuário
                if (defensor instanceof TreinadorHumano) {
                    this.estado = EstadoBatalha.AGUARDANDO_TROCA_JOGADOR;
                    logDaAcao.add("Você precisa escolher seu próximo Pokémon!");
                } else { // Se o derrotado é do Robô
                    Pokemon proximo = defensor.proximoPokemonDisponivel();
                    defensor.setPokemonEmCampo(proximo);
                    logDaAcao.add(defensor.getNome() + " enviou " + proximo.getNome() + "!");
                }
            }
        }
        return logDaAcao;
    }

    public ArrayList<String> executarAcaoJogador(Acao acaoJogador) {
        ArrayList<String> logDoTurno = new ArrayList<>();

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

    public ArrayList<String> trocarPokemonDerrotado(int indiceNovoPokemon) {
        ArrayList<String> logDaTroca = new ArrayList<>();
        // Só permite esta ação se o estado for o correto
        if (estado == EstadoBatalha.AGUARDANDO_TROCA_JOGADOR) {
            String msgTroca = realizarTroca(treinador1, indiceNovoPokemon);
            logDaTroca.add(msgTroca);
            estado = EstadoBatalha.EM_ANDAMENTO;
        }
        return logDaTroca;
    }

    public ArrayList<String> executarTurno(Acao acaoTreinador1, Acao acaoTreinador2) {
        ArrayList<String> logDoTurno = new ArrayList<>();


        Treinador primeiro = treinador1;
        Acao acaoPrimeiro = acaoTreinador1;
        Treinador segundo = treinador2;
        Acao acaoSegundo = acaoTreinador2;

        logDoTurno.addAll(processarAcao(primeiro, segundo, acaoPrimeiro));

        if (segundo.getEstadoTreinador() == EstadoTreinador.PERDEDOR) {
            turno++;
            return logDoTurno;
        }

        logDoTurno.addAll(processarAcao(segundo, primeiro, acaoSegundo));

        turno++;
        return logDoTurno;
    }


    public ArrayList<String> realizarAtaque(Treinador atacante, Treinador defensor) {
        ArrayList<String> logAtaque = new ArrayList<>();
        Pokemon pAtacante = atacante.getPokemonEmCampo();
        Pokemon pDefensor = defensor.getPokemonEmCampo();

        if (pAtacante.isDerrotado()) return logAtaque;

        TipoPokemon tipoDoAtaque = pAtacante.getTipo();

        TipoPokemon tipoDoDefensor = pDefensor.getTipo();

        double multiplicador = tipoDoAtaque.getEfetividadeContra(tipoDoDefensor);

        int danoBase = Math.abs((pAtacante.getAtaque() - (int) (0.7 * pDefensor.getDefesa())));
        if (danoBase <= 0) danoBase = 1;

        int danoFinal = (int) (danoBase * multiplicador);
        pDefensor.setVida(Math.max(0, pDefensor.getVida() - danoFinal));

        String nomeHabilidade = tipoDoAtaque.getHabilidadePadrao();
        logAtaque.add(pAtacante.getNome() + " usou " + nomeHabilidade + "!");
        //Lógica da efetividade dos poderes do Pokémon
        if (multiplicador > 1.0) logAtaque.add("Ataque do " + atacante.getPokemonEmCampo().getNome() + " é super efetivo!");
        else if (multiplicador < 1.0 && multiplicador > 0.0) logAtaque.add(" Ataque do " + atacante.getPokemonEmCampo().getNome() + " não é muito efetivo");
        else if (multiplicador == 0.0) logAtaque.add("Ataque " + atacante.getPokemonEmCampo().getNome() + " não foi efetivo");

        logAtaque.add(pDefensor.getNome() + " sofreu " + danoFinal + " de dano.");

        return logAtaque;
    }

    public String realizarTroca(Treinador treinador, int indiceNovoPokemon) {
        if (indiceNovoPokemon >= 0 && indiceNovoPokemon < treinador.getTime().size()) {
            Pokemon novoPokemon = treinador.getTime().get(indiceNovoPokemon);
            //Verifica se é possíevl realizar a troca do Pokémon
            if (!novoPokemon.isDerrotado() && novoPokemon != treinador.getPokemonEmCampo()) {
                treinador.setPokemonEmCampo(novoPokemon);
                return treinador.getNome() + " enviou " + novoPokemon.getNome() + "!";
            }
        }
        return "Troca inválida.";
    }

}

