package controller;

import batalha.Acao;
import batalha.Batalha;
import batalha.EstadoBatalha;
import batalha.TipoAcao;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import pokemon.Pokemon;
import pokemon.Pokedex;
import treinador.EstadoTreinador;
import treinador.Treinador;
import treinador.TreinadorHumano;
import treinador.TreinadorRobo;
import ui.MainApplication;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BatalhaController {

    @FXML private Label labelNomeInimigo;
    @FXML private ProgressBar barVidaInimigo;
    @FXML private Label labelVidaInimigo;
    @FXML private ImageView imgInimigo;

    @FXML private Label labelNomeJogador;
    @FXML private ProgressBar barVidaJogador;
    @FXML private Label labelVidaJogador;
    @FXML private ImageView imgJogador;

    @FXML private TextArea logBatalha;
    @FXML private Button btnAtacar;
    @FXML private Button btnTrocar1;
    @FXML private Button btnTrocar2;
    @FXML private Button btnTrocar3;

    private Batalha batalha;
    private List<Button> botoesDeTroca;

    @FXML
    private void handleTrocarParaPokemon1() {
        processarTroca(0);
    }

    @FXML
    private void handleTrocarParaPokemon2() {
        processarTroca(1);
    }

    @FXML
    private void handleTrocarParaPokemon3() {
        processarTroca(2);
    }

    @FXML
    public void initialize() {
        try {
            Pokedex pokedex = new Pokedex();
            InputStream inputStream = Pokedex.class.getResourceAsStream("/ListaPokemons.csv");
            pokedex.carregarPokemons(inputStream);

            ArrayList<Pokemon> pokemonsDisponiveis = new ArrayList<>(pokedex.getPokemons());

            final int TAMANHO_DO_TIME = 3;
            if (pokemonsDisponiveis.size() < TAMANHO_DO_TIME * 2) {
                logBatalha.setText("Sem pokemons suficientes na planilha para batalha" );
                return;
            }

            Collections.shuffle(pokemonsDisponiveis);
            //Arrumar lógica caso nome seja vazio
            Treinador jogador = new TreinadorHumano(MainApplication.nome_jogador);
            Treinador robo = new TreinadorRobo("Gary");

            for (int i = 0; i < TAMANHO_DO_TIME; i++) {
                jogador.getTime().add(pokemonsDisponiveis.remove(0));
            }

            for (int i = 0; i < TAMANHO_DO_TIME; i++) {
                robo.getTime().add(pokemonsDisponiveis.remove(0));
            }

            this.batalha = new Batalha(jogador, robo);
            this.batalha.iniciarBatalha();

            logBatalha.appendText("A batalha começou!\n");
            botoesDeTroca = List.of(btnTrocar1, btnTrocar2, btnTrocar3);
            atualizarUI();

        } catch (Exception e) {
            logBatalha.setText("Ocorreu um erro na inicialização da batalha: " + e.getMessage());
            e.printStackTrace();
        }

    }

    private void gerenciarBotoesDeAcao(boolean desabilitar) {
        if (desabilitar) {
            btnAtacar.setDisable(true);
            botoesDeTroca.forEach(btn -> btn.setDisable(true));
        } else {

            EstadoBatalha estadoAtual = batalha.getEstado();

            if (estadoAtual == EstadoBatalha.FIM_DE_JOGO) {
                btnAtacar.setDisable(true);
                botoesDeTroca.forEach(btn -> btn.setDisable(true));
            } else if (estadoAtual == EstadoBatalha.AGUARDANDO_TROCA_JOGADOR) {
                btnAtacar.setDisable(true); // Não pode atacar, deve trocar
                atualizarUI();
            } else {
                btnAtacar.setDisable(false);
                atualizarUI();
            }
        }
    }

    private void executarAcaoDoJogador(Acao acaoJogador) {
        gerenciarBotoesDeAcao(true);

        Acao acaoRobo = new Acao(TipoAcao.ATACAR);

        List<String> logDoTurno = batalha.executarTurno(acaoJogador, acaoRobo);

        for (String linha : logDoTurno) {
            logBatalha.appendText(linha + "\n");
        }

        atualizarUI();

        if (batalha.getTreinador1().getEstadoTreinador() == EstadoTreinador.PERDEDOR ||
                batalha.getTreinador2().getEstadoTreinador() == EstadoTreinador.PERDEDOR) {
            logBatalha.appendText("FIM DE JOGO!\n");
        } else {
            gerenciarBotoesDeAcao(false);
        }
    }

    private void processarTroca(int indicePokemon) {
        List<String> logDaAcao;

        if (batalha.getEstado() == EstadoBatalha.AGUARDANDO_TROCA_JOGADOR) {
            logDaAcao = batalha.trocarPokemonDerrotado(indicePokemon);
            logDaAcao.forEach(linha -> logBatalha.appendText(linha + "\n"));
            atualizarUI();
            gerenciarBotoesDeAcao(false);
        } else {
            Acao acaoJogador = new Acao(TipoAcao.TROCAR, indicePokemon);
            executarTurnoCompleto(acaoJogador);
        }
    }

    @FXML
    private void handleAtacar() {
        Acao acaoJogador = new Acao(TipoAcao.ATACAR);
        executarAcaoDoJogador(acaoJogador);
    }

    private void executarTurnoCompleto(Acao acaoJogador) {
        gerenciarBotoesDeAcao(true); // Desabilita tudo

        // Chama o método da Batalha que processa a ação do jogador e a resposta do robô
        List<String> logDoTurno = batalha.executarAcaoJogador(acaoJogador);

        logDoTurno.forEach(linha -> logBatalha.appendText(linha + "\n"));

        atualizarUI();

        gerenciarBotoesDeAcao(false);
    }


    private void atualizarUI() {
        Pokemon pJogador = batalha.getTreinador1().getPokemonEmCampo();
        Pokemon pInimigo = batalha.getTreinador2().getPokemonEmCampo();

        if (pJogador != null) {
            labelNomeJogador.setText(pJogador.getNome());
            labelVidaJogador.setText(pJogador.getVida() + " / " + pJogador.getVidaMaxima());
            barVidaJogador.setProgress((double) pJogador.getVida() / pJogador.getVidaMaxima());
            carregarImagem(imgJogador, pJogador);
        }
        if (pInimigo != null) {
            labelNomeInimigo.setText(pInimigo.getNome());
            labelVidaInimigo.setText(pInimigo.getVida() + " / " + pInimigo.getVidaMaxima());
            barVidaInimigo.setProgress((double) pInimigo.getVida() / pInimigo.getVidaMaxima());
            carregarImagem(imgInimigo, pInimigo);
        }
        Treinador jogador = batalha.getTreinador1();
        for (int i = 0; i < botoesDeTroca.size(); i++) {
            if (i < jogador.getTime().size()) {
                Pokemon p = jogador.getTime().get(i);
                Button btn = botoesDeTroca.get(i);
                btn.setText(p.getNome());

                if (p.isDerrotado()) {
                    btn.setDisable(true);
                } else {
                    btn.setDisable(false);
                }
            }
        }
    }
    private void carregarImagem(ImageView imageView, Pokemon pokemon) {
        String arquivo = pokemon.getNome().toLowerCase() + ".png";
        System.out.println(arquivo + "\n");
        try {
            String caminho = "/pokemon_images/" + arquivo;
            InputStream stream = this.getClass().getResourceAsStream(caminho);
            if(stream ==  null){
                System.err.println("Imagem não encontrada");
            }else{
                Image imagem = new Image(stream);
                imageView.setImage(imagem);
            }
        } catch (Exception e){
            System.err.println("Erro ao encontrar a imagem " + arquivo);
            e.printStackTrace();
        }
    }

}