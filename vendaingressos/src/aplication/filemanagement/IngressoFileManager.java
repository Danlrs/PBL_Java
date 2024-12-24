package aplication.filemanagement;

import aplication.model.Ingresso;
import com.google.gson.reflect.TypeToken;
import java.util.List;

/**
 * Gerenciador de arquivos para o tipo Ingresso.
 * Esta classe herda de FileManager e é especializada para manipular dados do tipo Ingresso.
 */
public class IngressoFileManager extends FileManager<Ingresso> {

    /**
     * Construtor da classe IngressoFileManager.
     * Define o caminho do arquivo de ingressos e o tipo concreto de lista de ingressos.
     */
    public IngressoFileManager() {
        // Passa o tipo específico para o construtor da classe base
        super("src/aplication/jsonfiles/ingressos.json", new TypeToken<List<Ingresso>>() {}.getType());
    }
}
