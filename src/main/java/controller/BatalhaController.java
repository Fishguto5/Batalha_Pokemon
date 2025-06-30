package controller;

import batalha.Acao;
import batalha.Batalha;
import batalha.EstadoBatalha;
import batalha.TipoAcao;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logs.LogBatalha;
import pokemon.Pokemon;
import pokemon.Pokedex;
import treinador.Treinador;
import treinador.TreinadorHumano;
import treinador.TreinadorRobo;
import ui.MainApplication;

import java.io.IOException;
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
    @FXML private HBox boxFimDeJogo;
    @FXML private Button btnJogarNovamente;
    @FXML private Button btnFecharJogo;
    @FXML private HBox boxBotoesTroca;
    @FXML private VBox boxAcoesBatalha;

    private Batalha batalha;
    private List<Button> botoesDeTroca;

    @FXML
    public void initialize() { //Inicializa a batalha
        botoesDeTroca = List.of(btnTrocar1, btnTrocar2, btnTrocar3);
        boxFimDeJogo.setVisible(false);
        boxFimDeJogo.setManaged(false);
        try {
            reiniciarBatalha();
            adicionarLog("A batalha começou!");
            atualizarUI();
            gerenciarBotoesDeAcao(false);
        } catch (Exception e) {
            adicionarLog("Ocorreu um erro fatal na inicialização: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAtacar() throws IOException { //Função Responsável por atacar o Pokémon Adversário
        processarAcaoDoJogador(new Acao(TipoAcao.ATACAR));
    }

    @FXML
    private void handleTrocarParaPokemon1() throws IOException { //Responsável pela troca do Pokémon 1
        processarTroca(0);
    }

    @FXML
    private void handleTrocarParaPokemon2() throws IOException { //Responsável pela troca do Pokémon 2
        processarTroca(1);
    }

    @FXML
    private void handleTrocarParaPokemon3() throws IOException { //Responsável pela troca do Pokémon 3
        processarTroca(2);
    }

    /*
        A função jogar novamente desabilita os @botoes presentes no meio da batalha, como o de trocar pokemon e atacar
     */

    @FXML
    private void jogarNovamente(ActionEvent event) throws IOException {
        btnJogarNovamente.setDisable(true);
        btnFecharJogo.setDisable(true);
        boxFimDeJogo.setVisible(false);
        boxFimDeJogo.setManaged(false);
        boxAcoesBatalha.setVisible(true);
        boxAcoesBatalha.setManaged(true);
        try {
            logBatalha.clear();
            reiniciarBatalha();
            adicionarLog("Uma nova batalha começou!");
            atualizarUI();
            gerenciarBotoesDeAcao(false);
        } catch (Exception e) {
            adicionarLog("Erro ao reiniciar a batalha: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void fecharJogo(ActionEvent event) {
        Platform.exit();
    }

    private void processarTroca(int indicePokemon) throws IOException { //relialza a troca de pokemon e atualiza a tela do usuário
        if (batalha.getEstado() == EstadoBatalha.AGUARDANDO_TROCA_JOGADOR) {
            batalha.trocarPokemonDerrotado(indicePokemon).forEach(this::adicionarLog);
            atualizarUI();
            gerenciarBotoesDeAcao(false);
        } else {
            processarAcaoDoJogador(new Acao(TipoAcao.TROCAR, indicePokemon));
        }
    }

    private void processarAcaoDoJogador(Acao acaoJogador) {
        gerenciarBotoesDeAcao(true);
        batalha.executarAcaoJogador(acaoJogador).forEach(this::adicionarLog);
        atualizarUI();

        if (batalha.getEstado() == EstadoBatalha.FIM_DE_JOGO) {
            adicionarLog("FIM DE JOGO!");
            ativarOpcoesFimDeJogo();
        } else {
            gerenciarBotoesDeAcao(false);
        }
    }

    private void ativarOpcoesFimDeJogo() {
        boxAcoesBatalha.setVisible(false);
        boxAcoesBatalha.setManaged(false);
        boxFimDeJogo.setVisible(true);
        boxFimDeJogo.setManaged(true);
        btnJogarNovamente.setDisable(false);
        btnFecharJogo.setDisable(false);
    }

    private void gerenciarBotoesDeAcao(boolean desabilitar) { //Gerencia os estados dos botões durante as ações do usuário
        btnAtacar.setDisable(desabilitar);
        if (desabilitar) {
            botoesDeTroca.forEach(btn -> btn.setDisable(true));
        } else {
            atualizarUI();
            if (batalha.getEstado() == EstadoBatalha.AGUARDANDO_TROCA_JOGADOR) {
                btnAtacar.setDisable(true);
            }
        }
    }

    private void atualizarUI() { //Atualiza a interface gráfica, seja a Imagem dos pokémons ou barra de vida
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
                btn.setDisable(p.isDerrotado() || p == jogador.getPokemonEmCampo());
            } else {
                botoesDeTroca.get(i).setVisible(false);
            }
        }
    }

    private void carregarImagem(ImageView imageView, Pokemon pokemon) { //Carrega a Imagem dos Pokemons
        if (pokemon == null || pokemon.getNome() == null) {
            return;
        }
        String arquivo = pokemon.getNome().toLowerCase() + ".png";
        try {
            String caminho = "/pokemon_images/" + arquivo;
            InputStream stream = getClass().getResourceAsStream(caminho);
            if (stream == null) {
                System.err.println("Imagem não encontrada: " + caminho);
            } else {
                Image imagem = new Image(stream);
                imageView.setImage(imagem);
                stream.close();
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar a imagem: " + arquivo);
            e.printStackTrace();
        }
    }

    private void reiniciarBatalha() throws Exception { // Reinicia a Batalha, trazendo novos pokémons
        Pokedex pokedex = new Pokedex();
        InputStream inputStream = Pokedex.class.getResourceAsStream("/ListaPokemons.csv");
        if (inputStream == null) {
            throw new Exception("Arquivo ListaPokemons.csv não encontrado.");
        }
        pokedex.carregarPokemons(inputStream);

        ArrayList<Pokemon> pokemonsDisponiveis = new ArrayList<>(pokedex.getPokemons());
        final int TAMANHO_DO_TIME = 3;

        if (pokemonsDisponiveis.size() < TAMANHO_DO_TIME * 2) {
            throw new Exception("Pokémons insuficientes no CSV para dois times de " + TAMANHO_DO_TIME);
        }

        Collections.shuffle(pokemonsDisponiveis);

        String nomeJogador = MainApplication.nome_jogador != null ? MainApplication.nome_jogador : "Jogador";
        Treinador jogador = new TreinadorHumano(nomeJogador);
        Treinador robo = new TreinadorRobo("Gary");

        for (int i = 0; i < TAMANHO_DO_TIME; i++) {
            jogador.getTime().add(pokemonsDisponiveis.remove(0));
        }
        for (int i = 0; i < TAMANHO_DO_TIME; i++) {
            robo.getTime().add(pokemonsDisponiveis.remove(0));
        }

        this.batalha = new Batalha(jogador, robo);
        this.batalha.iniciarBatalha();
    }

    private void adicionarLog(String mensagem) { //Adiciona as mensagens do arquivo RegistroBatalha.txt
        if(mensagem == null) return;
        logBatalha.appendText(mensagem + "\n");
        LogBatalha.registrar(mensagem);
    }
}