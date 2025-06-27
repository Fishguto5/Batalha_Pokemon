package pokemon;

public enum TipoPokemon {

    NORMAL("Impacto Brutal"),
    FOGO("Lança-chamas"),
    AGUA("Jato d'Água"),
    PLANTA("Chicote de Espinhos"),
    ELETRICO("Raio Trovejante"),
    GELO("Raio de Gelo"),
    LUTADOR("Rajada de Socos"),
    VENENO("Miasma Tóxico"),
    TERRA("Terremoto"),
    VOADOR("Corte Aéreo"),
    PSIQUICO("Explosão Mental"),
    INSETO("Ferroada Selvagem"),
    PEDRA("Colapso Rochoso"),
    FANTASMA("Vulto Espectral"),
    DRAGAO("Garra do Dragão"),
    SOMBRIO("Terror Noturno"),
    METAL("Canhão de Titânio"),
    FADA("Explosão Lunar");

    private final String habilidadePadrao;

    private TipoPokemon(String habilidadePadrao) {
        this.habilidadePadrao = habilidadePadrao;
    }

    public String getHabilidadePadrao() {
        return habilidadePadrao;
    }
}
