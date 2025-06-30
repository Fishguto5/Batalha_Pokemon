package pokemon;

public enum TipoPokemon implements Efetividade {

    //Lista a habilidade Padrão de acordo com cada tipo de Pokémon e a efetivade do ataque contra determinados tipos de pokémons

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
                case PLANTA, GELO, INSETO, METAL:
                    return 2.0;
                case FOGO, AGUA, PEDRA, DRAGAO:
                    return 0.5;
                default:
                    return 1.0;
            }
        }
    },
    AGUA("Jato d'Água") {
        @Override
        public double getEfetividadeContra(TipoPokemon tipoDefensor) {
            switch (tipoDefensor) {
                case FOGO, TERRA, PEDRA:
                    return 2.0;
                case AGUA, PLANTA, DRAGAO:
                    return 0.5;
                default:
                    return 1.0;
            }
        }
    },
    PLANTA("Chicote de Espinhos") {
        @Override
        public double getEfetividadeContra(TipoPokemon tipoDefensor) {
            switch (tipoDefensor) {
                case AGUA, TERRA, PEDRA:
                    return 2.0;
                case FOGO, PLANTA, VENENO, VOADOR, INSETO, DRAGAO, METAL:
                    return 0.5;
                default:
                    return 1.0;
            }
        }
    },
    ELETRICO("Raio Trovejante") {
        @Override
        public double getEfetividadeContra(TipoPokemon tipoDefensor) {
            switch (tipoDefensor) {
                case AGUA, VOADOR:
                    return 2.0;
                case PLANTA, ELETRICO, DRAGAO:
                    return 0.5;
                case TERRA:
                    return 0.0;
                default:
                    return 1.0;
            }
        }
    },

    GELO("Raio de Gelo") {
        @Override
        public double getEfetividadeContra(TipoPokemon tipoDefensor) {
            switch (tipoDefensor) {
                case FOGO, AGUA, GELO, METAL:
                    return 0.5;
                case TERRA, VOADOR, DRAGAO:
                    return 2.0;
                default:
                    return 1.0;
            }
        }
    },
    LUTADOR("Rajada de Socos") {
        @Override
        public double getEfetividadeContra(TipoPokemon tipoDefensor) {
            switch (tipoDefensor) {
                case NORMAL, GELO, PEDRA, SOMBRIO, METAL:
                    return 2.0;
                case VENENO, VOADOR, PSIQUICO, INSETO, FADA:
                    return 0.5;
                case FANTASMA:
                    return 0;
                default:
                    return 1.0;
            }
        }
    },
    VENENO("Miasma Tóxico") {
        @Override
        public double getEfetividadeContra(TipoPokemon tipoDefensor) {
            switch (tipoDefensor) {
                case FADA:
                    return 2.0;
                case VENENO, TERRA, PEDRA, FANTASMA:
                    return 0.5;
                case METAL:
                    return 0;
                default:
                    return 1.0;
            }
        }
    },
    TERRA("Terremoto") {
        @Override
        public double getEfetividadeContra(TipoPokemon tipoDefensor) {
            switch (tipoDefensor) {
                case FOGO, ELETRICO, VENENO, PEDRA, METAL:
                    return 2.0;
                case INSETO:
                    return 0.5;
                case VOADOR:
                    return 0;
                default:
                    return 1.0;
            }
        }
    },
    VOADOR("Corte Aéreo") {
        @Override
        public double getEfetividadeContra(TipoPokemon tipoDefensor) {
            switch (tipoDefensor) {
                case LUTADOR, INSETO:
                    return 2.0;
                case ELETRICO, PEDRA, METAL:
                    return 0.5;
                default:
                    return 1.0;
            }
        }
    },
    PSIQUICO("Explosão Mental") {
        @Override
        public double getEfetividadeContra(TipoPokemon tipoDefensor) {
            switch (tipoDefensor) {
                case LUTADOR, VENENO:
                    return 2.0;
                case PSIQUICO, METAL:
                    return 0.5;
                case SOMBRIO:
                    return 0;
                default:
                    return 1.0;
            }
        }
    },
    INSETO("Ferroada Selvagem") {
        @Override
        public double getEfetividadeContra(TipoPokemon tipoDefensor) {
            switch (tipoDefensor) {
                case PSIQUICO, SOMBRIO:
                    return 2.0;
                case FOGO, LUTADOR, VENENO, VOADOR, FANTASMA, METAL, FADA:
                    return 0.5;
                default:
                    return 1.0;
            }
        }
    },
    PEDRA("Colapso Rochoso") {
        @Override
        public double getEfetividadeContra(TipoPokemon tipoDefensor) {
            switch (tipoDefensor) {
                case FOGO, GELO, VOADOR, INSETO:
                    return 2.0;
                case LUTADOR, TERRA, METAL:
                    return 0.5;
                default:
                    return 1.0;
            }
        }
    },
    FANTASMA("Vulto Espectral") {
        @Override
        public double getEfetividadeContra(TipoPokemon tipoDefensor) {
            switch (tipoDefensor) {
                case PSIQUICO, FANTASMA:
                    return 2.0;
                case SOMBRIO:
                    return 0.5;
                case NORMAL:
                    return 0;
                default:
                    return 1.0;
            }
        }
    },
    DRAGAO("Garra do Dragão") {
        @Override
        public double getEfetividadeContra(TipoPokemon tipoDefensor) {
            switch (tipoDefensor) {
                case DRAGAO:
                    return 2.0;
                case METAL:
                    return 0.5;
                case FADA:
                    return 0;
                default:
                    return 1.0;
            }
        }
    },
    SOMBRIO("Terror Noturno") {
        @Override
        public double getEfetividadeContra(TipoPokemon tipoDefensor) {
            switch (tipoDefensor) {
                case PSIQUICO, FANTASMA:
                    return 2.0;
                case LUTADOR, SOMBRIO, FADA:
                    return 0.5;
                default:
                    return 1.0;
            }
        }
    },
    METAL("Canhão de Titânio") {
        @Override
        public double getEfetividadeContra(TipoPokemon tipoDefensor) {
            switch (tipoDefensor) {
                case GELO, PEDRA, FADA:
                    return 2.0;
                case FOGO, AGUA, ELETRICO, METAL:
                    return 0.5;
                default:
                    return 1.0;
            }
        }
    },
    FADA("Explosão Lunar") {
        @Override
        public double getEfetividadeContra(TipoPokemon tipoDefensor) {
            switch (tipoDefensor) {
                case LUTADOR, DRAGAO, SOMBRIO:
                    return 2.0;
                case FOGO, VENENO, METAL:
                    return 0.5;
                default:
                    return 1.0;
            }
        }
    };

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