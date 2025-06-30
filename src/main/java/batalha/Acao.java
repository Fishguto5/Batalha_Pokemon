package batalha;

public class Acao {
    private TipoAcao tipo;
    private int indiceAlvo = -1;

    //Getters, não foram incluidos os Setters por não serem utilizados
    public TipoAcao getTipo() { return tipo; }
    public int getIndiceAlvo() { return indiceAlvo; }
    //Construtor 1
    public Acao(TipoAcao tipo) {
        this.tipo = tipo;
    }
    //Construtor 2
    public Acao(TipoAcao tipo, int indiceAlvo) {
        this.tipo = tipo;
        this.indiceAlvo = indiceAlvo;
    }
}
