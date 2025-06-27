package batalha;

// Classes auxiliares para definir as ações
public class Acao {
    private TipoAcao tipo;
    private int alvo; // Ex: índice do pokémon para troca

    public Acao(TipoAcao tipo) { this.tipo = tipo; this.alvo = -1; }
    public Acao(TipoAcao tipo, int alvo) { this.tipo = tipo; this.alvo = alvo; }

    public TipoAcao getTipo() { return tipo; }
    public int getAlvo() { return alvo; }
}
