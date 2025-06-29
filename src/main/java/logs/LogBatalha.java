package logs;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LogBatalha {
    private static final String logRota = "RegistroBatalha.txt";
    public static void  registrar(String mensagem) {
        try (PrintWriter out = new PrintWriter(new FileWriter(logRota, true))) {
            out.println(mensagem);
        } catch (IOException e){
            System.err.println("Erro ao registrar log da batalha");
            e.printStackTrace();
        }
    }
}
