package controller;

import batalha.Acao;
import batalha.Batalha;
import batalha.TipoAcao;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pokemon.Pokemon;
import pokemon.Pokedex;
import treinador.EstadoTreinador;
import treinador.Treinador;
import treinador.TreinadorHumano;
import treinador.TreinadorRobo;

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

    private Batalha batalha;

    @FXML
    public void initialize() {
        try {
            Pokedex pokedex = new Pokedex();
            InputStream inputStream = Pokedex.class.getResourceAsStream("/ListaPokemons.csv");
            pokedex.carregarPokemons(inputStream);

            List<Pokemon> pokemonsDisponiveis = new ArrayList<>(pokedex.getPokemons());

            final int TAMANHO_DO_TIME = 3;
            if (pokemonsDisponiveis.size() < TAMANHO_DO_TIME * 2) {
                logBatalha.setText("Erro: O arquivo CSV não contém Pokémon suficientes para dois times de " + TAMANHO_DO_TIME + ".");
                return;
            }

            Collections.shuffle(pokemonsDisponiveis);

            Treinador jogador = new TreinadorHumano("Ash");
            Treinador robo = new TreinadorRobo("Gary");

            for (int i = 0; i < TAMANHO_DO_TIME; i++) {
                jogador.getTime().add(pokemonsDisponiveis.remove(0));
            }

            for (int i = 0; i < TAMANHO_DO_TIME; i++) {
                robo.getTime().add(pokemonsDisponiveis.remove(0));
            }

            // O restante do código para iniciar a batalha
            this.batalha = new Batalha(jogador, robo);
            this.batalha.iniciarBatalha();

            logBatalha.appendText("A batalha começou!\n");
            atualizarUI();

        } catch (Exception e) {
            logBatalha.setText("Ocorreu um erro na inicialização da batalha: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @FXML
    private void handleAtacar() {
        Acao acaoJogador = new Acao(TipoAcao.ATACAR);

        Acao acaoRobo = new Acao(TipoAcao.ATACAR);

        btnAtacar.setDisable(true);

        // Executa o turno na lógica de batalha
        List<String> logDoTurno = batalha.executarTurno(acaoJogador, acaoRobo);

        // Mostra o resultado do turno na tela
        for (String linha : logDoTurno) {
            logBatalha.appendText(linha + "\n");
        }

        atualizarUI();

        if (batalha.getTreinador1().getEstadoTreinador() != EstadoTreinador.PERDEDOR &&
                batalha.getTreinador2().getEstadoTreinador() != EstadoTreinador.PERDEDOR) {
            btnAtacar.setDisable(false);
        } else {
            logBatalha.appendText("FIM DE JOGO!\n");
        }
    }


    private void atualizarUI() {

        Pokemon pJogador = batalha.getTreinador1().getPokemonEmCampo();
        Pokemon pInimigo = batalha.getTreinador2().getPokemonEmCampo();
        carregarImagem(imgInimigo, pInimigo);
        carregarImagem(imgJogador, pJogador);

        //EU
        labelNomeJogador.setText(pJogador.getNome());
        // Linhas que faltavam:
        labelVidaJogador.setText(pJogador.getVida() + " / " + pJogador.getVidaMaxima());
        barVidaJogador.setProgress((double) pJogador.getVida() / pJogador.getVidaMaxima());

        // Inimigo
        labelNomeInimigo.setText(pInimigo.getNome());
        labelVidaInimigo.setText(pInimigo.getVida() + " / " + pInimigo.getVidaMaxima());
        barVidaInimigo.setProgress((double) pInimigo.getVida() / pInimigo.getVidaMaxima());

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
            System.err.println("Ocorreu um erro ao tentar carregar a imagem: " + arquivo);
            e.printStackTrace();
        }
    }

}