package aplication.filemanagement;

import aplication.model.Cartao;
import com.google.gson.reflect.TypeToken;
import java.util.List;

/**
 * Gerenciador de arquivos para o tipo Cartao.
 * Esta classe herda de FileManager e é especializada para manipular dados do tipo Cartao.
 */
public class CartaoFileManager extends FileManager<Cartao> {

    /**
     * Construtor da classe CartaoFileManager.
     * Define o caminho do arquivo de cartões e o tipo concreto de lista de cartões.
     */
    public CartaoFileManager() {
        // Passa o tipo específico para o construtor da classe base
        super("src/aplication/jsonfiles/cartoes.json", new TypeToken<List<Cartao>>() {}.getType());
    }
}
