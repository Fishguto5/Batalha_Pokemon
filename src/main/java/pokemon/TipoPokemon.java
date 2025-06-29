package pokemon;

public enum TipoPokemon implements Efetividade {

    NORMAL("Impacto Brutal") {
        @Override
        public double getEfetividadeContra(TipoPokemon tipoDefensor) {
            if (tipoDefensor == FANTASMA) return 0.0;
            if (tipoDefensor == PEDRA || tipoDefensor == METAL) return 0.5;
            return 1.0;
        }
    },
    FOGO("Lança-chamas") {
        @Override
        public double getEfetividadeContra(TipoPokemon tipoDefensor) {
            switch (tipoDefensor) {
                case PLANTA, GELO, INSETO, METAL: return 2.0;
                case FOGO, AGUA, PEDRA, DRAGAO: return 0.5;
                default: return 1.0;
            }
        }
    },
    AGUA("Jato d'Água") {
        @Override
        public double getEfetividadeContra(TipoPokemon tipoDefensor) {
            switch (tipoDefensor) {
                case FOGO, TERRA, PEDRA: return 2.0;
                case AGUA, PLANTA, DRAGAO: return 0.5;
                default: return 1.0;
            }
        }
    },
    PLANTA("Chicote de Espinhos") {
        @Override
        public double getEfetividadeContra(TipoPokemon tipoDefensor) {
            switch (tipoDefensor) {
                case AGUA, TERRA, PEDRA: return 2.0;
                case FOGO, PLANTA, VENENO, VOADOR, INSETO, DRAGAO, METAL: return 0.5;
                default: return 1.0;
            }
        }
    },
    ELETRICO("Raio Trovejante") {
        @Override
        public double getEfetividadeContra(TipoPokemon tipoDefensor) {
            switch (tipoDefensor) {
                case AGUA, VOADOR: return 2.0;
                case PLANTA, ELETRICO, DRAGAO: return 0.5;
                case TERRA: return 0.0;
                default: return 1.0;
            }
        }
    },

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


    @Override
    public double getEfetividadeContra(TipoPokemon tipoDefensor) {
        return 1.0;
    }
}