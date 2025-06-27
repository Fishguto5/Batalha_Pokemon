package batalha;

import pokemon.Pokemon;
import treinador.EstadoTreinador;
import treinador.Treinador;
import treinador.TreinadorHumano;
import treinador.TreinadorRobo;

import java.util.Random;
import java.util.Scanner;

public class Batalha {

    private Treinador treinador1;
    private Treinador treinador2;
    private int turno;
    private Scanner scanner;

    public Batalha(Treinador treinador1, Treinador treinador2) {
        this.treinador1 = treinador1;
        this.treinador2 = treinador2;
        this.turno = 1;
        this.scanner = new Scanner(System.in);
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public void iniciar() {

        System.out.println("\n=== BATALHA INICIADA ===\n");
        treinador1.setPokemonEmCampo(treinador1.getTime().get(0));
        treinador2.setPokemonEmCampo(treinador2.getTime().get(0));

        //Pokemons no terminal
        System.out.println(treinador1.getNome() + " envia " + treinador1.getPokemonEmCampo().getNome() + "!");
        System.out.println(treinador2.getNome() + " envia " + treinador2.getPokemonEmCampo().getNome() + "!");

        // Lógica da batalha
        while (treinador1.getEstadoTreinador() != EstadoTreinador.PERDEDOR && treinador2.getEstadoTreinador() != EstadoTreinador.PERDEDOR) {

            System.out.println("\n TURNO: " + turno);
            System.out.println("O pokemon.Pokemon " + treinador1.getPokemonEmCampo().getNome() + " de "
                    + treinador1.getNome() + " está com " + treinador1.getPokemonEmCampo().getVida() + " de vida!");
            System.out.println("O pokemon.Pokemon " + treinador2.getPokemonEmCampo().getNome() + " de "
                    + treinador2.getNome() + " está com " + treinador2.getPokemonEmCampo().getVida() + " de vida!");

            boolean treinador1ForcouTroca = false;
            boolean treinador2ForcouTroca = false;


            if (treinador1.getPokemonEmCampo().isDerrotado() && !treinador1.timeDerrotado(treinador1.getTime())) {
                if (treinador1 instanceof TreinadorRobo) {
                    System.out.println(treinador1.getPokemonEmCampo().getNome() + " foi derrotado!");
                    realizarTrocaAutomatica(treinador1);
                    treinador1ForcouTroca = true;
                }
            }
            if (treinador2.getPokemonEmCampo().isDerrotado() && !treinador2.timeDerrotado(treinador2.getTime())) {
                if (treinador2 instanceof TreinadorRobo) {
                    System.out.println(treinador2.getPokemonEmCampo().getNome() + " foi derrotado!");
                    realizarTrocaAutomatica(treinador2);
                    treinador2ForcouTroca = true;
                }
            }

            if(treinador1ForcouTroca || treinador2ForcouTroca) {
                if(treinador1ForcouTroca && treinador2ForcouTroca) {
                    turno++;
                    continue;
                }
                if(treinador1ForcouTroca && !treinador2ForcouTroca) {
                    int escolha2 = escolhaTurno(treinador2);
                    if (escolha2 == 1) {
                        realizarAtaque(treinador2, treinador1);
                        verificaDerrota(treinador1, treinador2);
                    } else if (escolha2 == 2) {
                        realizarTroca(treinador2);
                    }
                    turno++;
                    continue;
                }
                if(!treinador1ForcouTroca && treinador2ForcouTroca) {
                    int escolha1 = escolhaTurno(treinador1);
                    if (escolha1 == 1) {
                        realizarAtaque(treinador1, treinador2);
                        verificaDerrota(treinador2, treinador1);
                    } else if (escolha1 == 2) {
                        realizarTroca(treinador1);
                    }
                    turno++;
                    continue;
                }
            }


            int escolha1 = escolhaTurno(treinador1);
            int escolha2 = escolhaTurno(treinador2);

            // Caso ambos escolham trocar
            if (escolha1 == 2 && escolha2 == 2) {
                // Troca do Treinador1 é realizada
                realizarTroca(treinador1);
                // Troca do Treinador2 é realizada
                realizarTroca(treinador2);
            }

            // Caso apenas uma das escolhas seja troca e a outra ataque
            else if ((escolha1 == 2 && escolha2 == 1) || (escolha1 == 1 && escolha2 == 2)) {
                if (escolha1 == 2) {
                    // Troca do Treinador1 é realizada
                    realizarTroca(treinador1);
                    // Ataque do Treinador2 é realizado
                    realizarAtaque(treinador2, treinador1);
                    verificaDerrota(treinador1, treinador2);
                } else {
                    // Troca do Treinador2 é realizada
                    realizarTroca(treinador2);
                    // Ataque do treinador.Treinador 1 é realizado
                    realizarAtaque(treinador1, treinador2);
                    verificaDerrota(treinador2, treinador1);
                }
            }

            // Caso ambos escolham atacar, o pokemon.Pokemon mais rápido ataca primeiro
            else {
                if (treinador1.getPokemonEmCampo().getVelocidade() > treinador2.getPokemonEmCampo()
                        .getVelocidade()) {
                    realizarAtaque(treinador1, treinador2);
                    verificaDerrota(treinador2, treinador1); // Verifica derrota após o primeiro ataque
                    if (treinador2.getEstadoTreinador() == EstadoTreinador.PERDEDOR || treinador2.getPokemonEmCampo().isDerrotado() && treinador2 instanceof TreinadorRobo) {
                        //alterar
                    } else {
                        realizarAtaque(treinador2, treinador1);
                        verificaDerrota(treinador1, treinador2);
                    }
                } else if (treinador2.getPokemonEmCampo().getVelocidade() > treinador1.getPokemonEmCampo()
                        .getVelocidade()) {
                    realizarAtaque(treinador2, treinador1);
                    verificaDerrota(treinador1, treinador2); // Verifica derrota após o primeiro ataque
                    if (treinador1.getEstadoTreinador() == EstadoTreinador.PERDEDOR || treinador1.getPokemonEmCampo().isDerrotado() && treinador1 instanceof TreinadorRobo) {
                        // Adiciona condição para robô
                        //alterar
                    } else {
                        realizarAtaque(treinador1, treinador2);
                        verificaDerrota(treinador2, treinador1);
                    }
                } else {
                    // Caso as velocidades sejam iguais, decide aleatoriamente quem ataca primeiro
                    Random random = new Random();
                    if (random.nextBoolean()) {
                        realizarAtaque(treinador1, treinador2);
                        verificaDerrota(treinador2, treinador1);
                        if (treinador2.getEstadoTreinador() == EstadoTreinador.PERDEDOR || treinador2.getPokemonEmCampo().isDerrotado() && treinador2 instanceof TreinadorRobo) {
                            // Se o defensor foi derrotado e é um treinador.TreinadorRobo, ele já trocou automaticamente
                        } else {
                            realizarAtaque(treinador2, treinador1);
                            verificaDerrota(treinador1, treinador2);
                        }
                    } else {
                        realizarAtaque(treinador2, treinador1);
                        verificaDerrota(treinador1, treinador2);
                        if (treinador1.getEstadoTreinador() == EstadoTreinador.PERDEDOR || treinador1.getPokemonEmCampo().isDerrotado() && treinador1 instanceof TreinadorRobo) {
                            // Se o defensor foi derrotado e é um treinador.TreinadorRobo, ele já trocou automaticamente
                        } else {
                            realizarAtaque(treinador1, treinador2);
                            verificaDerrota(treinador2, treinador1);
                        }
                    }
                }
            }
            // Incrementa o contador de turno
            turno++;
        }
    }

    // Escolha da ação de cada jogador
    private int escolhaTurno(Treinador treinador) {
        // Jogador ataca ou troca de pokemon.Pokemon
        System.out.println("\n" + treinador.getNome() + ", o que deseja fazer?");
        System.out.println("1. Atacar");
        System.out.println("2. Trocar de Pokémon");

        // Escolha (treinador Robô sempre escolhe 1, vai mudar no futuro)
        int escolha = 1; // Padrão é atacar
        if (treinador instanceof TreinadorHumano) {
            escolha = scanner.nextInt();
        } else if (treinador instanceof TreinadorRobo) {
            escolha = 1;
        }

        return escolha;
    }

    private void realizarTroca(Treinador treinador) {
        System.out.println("Escolha outro Pokémon:");

        for (int i = 0; i < treinador.getTime().size(); i++) {
            Pokemon p = treinador.getTime().get(i);
            if (!p.equals(treinador.getPokemonEmCampo()) && !p.isDerrotado()) {
                System.out.println("Índice " + i + ". " + p.getNome());
            }
        }

        int indexTroca = -1; // Inicializa com valor inválido
        if (treinador instanceof TreinadorHumano) {
            System.out.println("Digite o índice do Pokémon que você deseja colocar em campo, substituindo "
                    + treinador.getPokemonEmCampo().getNome() + ":");
            indexTroca = scanner.nextInt();
        } else if (treinador instanceof TreinadorRobo) {
            for (int i = 0; i < treinador.getTime().size(); i++) {
                Pokemon p = treinador.getTime().get(i);
                if (!p.equals(treinador.getPokemonEmCampo()) && !p.isDerrotado()) {
                    indexTroca = i;
                    break;
                }
            }
        }

        if (indexTroca != -1 && indexTroca < treinador.getTime().size()) {
            Pokemon substituto = treinador.getTime().get(indexTroca);
            treinador.setPokemonEmCampo(substituto);
            System.out.println("\n" + treinador.getNome() + " trocou para " + treinador.getPokemonEmCampo().getNome());
        } else {
            System.out.println("Não foi possível realizar a troca. Nenhum Pokémon válido disponível.");
        }
    }

    private void realizarTrocaAutomatica(Treinador treinador) {
        System.out.println(treinador.getNome() + " precisa trocar de Pokémon!");
        int indexTroca = -1;
        for (int i = 0; i < treinador.getTime().size(); i++) {
            Pokemon p = treinador.getTime().get(i);
            if (!p.equals(treinador.getPokemonEmCampo()) && !p.isDerrotado()) {
                indexTroca = i;
                break;
            }
        }

        if (indexTroca != -1 && indexTroca < treinador.getTime().size()) {
            Pokemon substituto = treinador.getTime().get(indexTroca);
            treinador.setPokemonEmCampo(substituto);
            System.out.println("\n" + treinador.getNome() + " trocou automaticamente para " + treinador.getPokemonEmCampo().getNome() + "!");
        } else {
            System.out.println("Erro: " + treinador.getNome() + " não tem Pokémon disponível para troca, mas a lógica de derrota deveria ter impedido isso.");
            // Ajustar lógica de timeDerrotado
        }
    }


    private void realizarAtaque(Treinador treinadorAtacante, Treinador treinadorDefensor) {
        int dano = (int) Math.abs((treinadorAtacante.getPokemonEmCampo().getAtaque()
                - 0.7 * treinadorDefensor.getPokemonEmCampo().getDefesa()));
        treinadorAtacante.getPokemonEmCampo().usarHabilidade();
        treinadorDefensor.getPokemonEmCampo()
                .setVida(Math.max((treinadorDefensor.getPokemonEmCampo().getVida() - dano), 0));
        System.out.println(treinadorAtacante.getPokemonEmCampo().getNome() + " causou " + dano + " de dano a "
                + treinadorDefensor.getPokemonEmCampo().getNome());
    }

    private void verificaDerrota(Treinador treinadorDerrota, Treinador treinadorVitoria) {
        if (treinadorDerrota.getPokemonEmCampo().isDerrotado()) {
            System.out.println(treinadorDerrota.getPokemonEmCampo().getNome() + " foi derrotado!");
            // Verifica se o treinador perdeu a batalha
            if (treinadorDerrota.timeDerrotado(treinadorDerrota.getTime())) {
                System.out.println("\n" + treinadorDerrota.getNome() + " foi derrotado!");
                System.out.println(treinadorVitoria.getNome() + " venceu a batalha!");
                treinadorDerrota.setEstadoTreinador(EstadoTreinador.PERDEDOR);
            } else {
                if (treinadorDerrota instanceof TreinadorHumano) {
                    realizarTroca(treinadorDerrota);
                } else if (treinadorDerrota instanceof TreinadorRobo) {
                    realizarTrocaAutomatica(treinadorDerrota);
                }
            }
        }
    }
}