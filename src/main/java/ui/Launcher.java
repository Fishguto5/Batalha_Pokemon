package ui;

import java.io.InputStream;

import batalha.Acao;
import batalha.Batalha;
import batalha.EstadoBatalha;
import batalha.TipoAcao;
import pokemon.Pokedex;
import treinador.TreinadorRobo;

public class Launcher {
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("test")) {
            System.exit(test());
        }

        MainApplication.main(args);
    }

    private static int test() {
        Pokedex pokedex = new Pokedex();
        InputStream inputStream = Pokedex.class.getResourceAsStream("/ListaPokemons.csv");
        try {
            pokedex.carregarPokemons(inputStream);
        } catch (Exception e) {
            System.err.print(e);
            return 1;
        }

        TreinadorRobo t1 = new TreinadorRobo("r1");
        t1.escolherPokemon(pokedex);

        TreinadorRobo t2 = new TreinadorRobo("r2");
        t2.escolherPokemon(pokedex);

        Batalha bat = new Batalha(t1, t2);
        bat.iniciarBatalha();
        while (bat.getEstado() != EstadoBatalha.FIM_DE_JOGO) {
            System.out.println("Iniciando turno");
            bat.executarTurno(new Acao(TipoAcao.ATACAR), new Acao(TipoAcao.ATACAR));
        }

        return 0;
    }
}
