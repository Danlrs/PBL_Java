package aplication.filemanagement;

import aplication.model.Avaliacao;
import com.google.gson.reflect.TypeToken;
import java.util.List;

/**
 * Gerenciador de arquivos para o tipo Avaliacao.
 * Esta classe herda de FileManager e é especializada para manipular dados do tipo Avaliacao.
 */
public class AvaliacaoFileManager extends FileManager<Avaliacao> {

    /**
     * Construtor da classe AvaliacaoFileManager.
     * Define o caminho do arquivo de avaliações e o tipo concreto de lista de avaliações.
     */
    public AvaliacaoFileManager() {
        // Passa o tipo específico para o construtor da classe base
        super("src/aplication/jsonfiles/avaliacoes.json", new TypeToken<List<Avaliacao>>() {}.getType());
    }
}
