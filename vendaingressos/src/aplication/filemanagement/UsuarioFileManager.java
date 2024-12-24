package aplication.filemanagement;

import aplication.model.Usuario;
import com.google.gson.reflect.TypeToken;
import java.util.List;

/**
 * Gerenciador de arquivos para o tipo Usuario.
 * Esta classe herda de FileManager e é especializada para manipular dados do tipo Usuario.
 */
public class UsuarioFileManager extends FileManager<Usuario> {

    /**
     * Construtor da classe UsuarioFileManager.
     * Define o caminho do arquivo de usuários e o tipo concreto de lista de usuários.
     */
    public UsuarioFileManager() {
        // Passa o tipo específico para o construtor da classe base
        super("src/aplication/jsonfiles/usuarios.json", new TypeToken<List<Usuario>>() {}.getType());
    }
}
