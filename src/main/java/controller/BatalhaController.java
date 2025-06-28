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
            //Arrumar lógica caso nome seja vazio
            Treinador jogador = new TreinadorHumano(MainApplication.nome_jogador);
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
        // 1. Desabilita o botão para prevenir múltiplos cliques durante o turno.
        btnAtacar.setDisable(true);

        // --- AQUI ESTÁ A DEFINIÇÃO QUE FALTAVA ---
        // Criamos as ações para cada treinador. Neste momento, ambos sempre escolhem atacar.
        // Estas variáveis são locais e existem apenas dentro deste método.
        Acao acaoJogador = new Acao(TipoAcao.ATACAR);
        Acao acaoRobo = new Acao(TipoAcao.ATACAR);
        // --- FIM DA DEFINIÇÃO ---

        // 2. Executa toda a lógica do turno na sua classe Batalha, passando as ações.
        List<String> logDoTurno = batalha.executarTurno(acaoJogador, acaoRobo);

        // 3. Mostra o resultado do turno no log da tela.
        for (String linha : logDoTurno) {
            logBatalha.appendText(linha + "\n");
        }

        // 4. ATUALIZA A INTERFACE GRÁFICA.
        // Isto irá garantir que a barra de vida do Pokémon derrotado seja zerada visualmente.
        atualizarUI();

        // 5. AGORA, VERIFICA O ESTADO FINAL DA BATALHA.
        // Esta verificação acontece DEPOIS da UI ter sido atualizada.
        if (batalha.getTreinador1().getEstadoTreinador() == EstadoTreinador.PERDEDOR ||
                batalha.getTreinador2().getEstadoTreinador() == EstadoTreinador.PERDEDOR) {

            // Se a batalha terminou, apenas adiciona a mensagem final.
            // O botão já está desabilitado e não será reativado.
            logBatalha.appendText("FIM DE JOGO!\n");

        } else {
            // Se a batalha NÃO terminou, reabilita o botão para o próximo turno.
            btnAtacar.setDisable(false);
        }
    }


    private void atualizarUI() {
        Pokemon pJogador = batalha.getTreinador1().getPokemonEmCampo();
        Pokemon pInimigo = batalha.getTreinador2().getPokemonEmCampo();

        // Atualiza a UI do Jogador, mas SÓ SE o Pokémon não for nulo.
        if (pJogador != null) {
            labelNomeJogador.setText(pJogador.getNome());
            labelVidaJogador.setText(pJogador.getVida() + " / " + pJogador.getVidaMaxima());
            barVidaJogador.setProgress((double) pJogador.getVida() / pJogador.getVidaMaxima());
            carregarImagem(imgJogador, pJogador);
        }

        // Atualiza a UI do Inimigo, mas SÓ SE o Pokémon não for nulo.
        if (pInimigo != null) {
            labelNomeInimigo.setText(pInimigo.getNome());
            labelVidaInimigo.setText(pInimigo.getVida() + " / " + pInimigo.getVidaMaxima());
            barVidaInimigo.setProgress((double) pInimigo.getVida() / pInimigo.getVidaMaxima());
            carregarImagem(imgInimigo, pInimigo);
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
            System.err.println("Ocorreu um erro ao tentar carregar a imagem: " + arquivo);
            e.printStackTrace();
        }
    }

}