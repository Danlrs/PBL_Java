package aplication.filemanagement;

import aplication.model.Compra;
import com.google.gson.reflect.TypeToken;
import java.util.List;

/**
 * Gerenciador de arquivos para o tipo Compra.
 * Esta classe herda de FileManager e é especializada para manipular dados do tipo Compra.
 */
public class CompraFileManager extends FileManager<Compra> {

    /**
     * Construtor da classe CompraFileManager.
     * Define o caminho do arquivo de compras e o tipo concreto de lista de compras.
     */
    public CompraFileManager() {
        // Passa o tipo específico para o construtor da classe base
        super("src/aplication/jsonfiles/compras.json", new TypeToken<List<Compra>>() {}.getType());
    }
}
