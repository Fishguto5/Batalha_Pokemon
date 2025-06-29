package batalha;

// Classes auxiliares para definir as ações
public class Acao {
    private TipoAcao tipo;
    private int indiceAlvo = -1;

    public void setTipo(TipoAcao tipo) {
        this.tipo = tipo;
    }

    public void setIndiceAlvo(int indiceAlvo) {
        this.indiceAlvo = indiceAlvo;
    }

    public TipoAcao getTipo() { return tipo; }
    public int getIndiceAlvo() { return indiceAlvo; }

    public Acao(TipoAcao tipo) {
        this.tipo = tipo;
    }

    public Acao(TipoAcao tipo, int indiceAlvo) {
        this.tipo = tipo;
        this.indiceAlvo = indiceAlvo;
    }


}
