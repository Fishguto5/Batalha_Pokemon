package pokemon;

import java.util.ArrayList;

public class Pokemon  {

    private String nome;
    private TipoPokemon tipo;
    private int vida;
    private int vidaMaxima;
    private int ataque;
    private int defesa;
    private int velocidade;
    private EstadoPokemon estado;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoPokemon getTipo() {
        return tipo;
    }

    public void setTipo(TipoPokemon tipo) {
        this.tipo = tipo;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefesa() {
        return defesa;
    }

    public void setDefesa(int defesa) {
        this.defesa = defesa;
    }

    public int getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(int velocidade) {
        this.velocidade = velocidade;
    }

    public EstadoPokemon getEstado() {
        return estado;
    }

    public void setEstado(EstadoPokemon estado) {
        this.estado = estado;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public void setVidaMaxima(int vidaMaxima) {
        this.vidaMaxima = vidaMaxima;
    }

    // Construtor
    public Pokemon(String nome, TipoPokemon tipo, int vida, int ataque, int defesa, int velocidade) {
        this.nome = nome;
        this.tipo = tipo;
        this.vida = vida;
        this.ataque = ataque;
        this.defesa = defesa;
        this.velocidade = velocidade;
        estado = EstadoPokemon.VIVO;
        this.vidaMaxima = vida;
    }

    public Pokemon(Pokemon outro) {
        this.nome = outro.nome;
        this.tipo = outro.tipo;
        this.vida = outro.vida;
        this.ataque = outro.ataque;
        this.defesa = outro.defesa;
        this.velocidade = outro.velocidade;
        this.vidaMaxima = vida;
    }

    public void receberDano(int dano) {
        vida -= dano;
    }

    public boolean isDerrotado() {
        if(this.vida <= 0) {
            setEstado(EstadoPokemon.MORTO);
            return true;
        }
        return false;
    }
}
