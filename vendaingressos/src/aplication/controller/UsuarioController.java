package aplication.controller;

import aplication.model.Usuario;
import aplication.filemanagement.UsuarioFileManager;
import java.util.UUID;
import java.util.List;

/**
 * Classe responsável pelo controle de operações relacionadas ao usuário.
 * Utiliza o arquivo de gerenciamento de usuários para salvar, editar e buscar informações de usuários.
 */
public class UsuarioController {
    private final UsuarioFileManager usuarioFileManager;

    /**
     * Construtor da classe UsuarioController.
     * Inicializa o gerenciador de arquivos de usuários.
     */
    public UsuarioController() {
        this.usuarioFileManager = new UsuarioFileManager();
    }

    /**
     * Cadastra um novo usuário.
     * Verifica se o e-mail, login ou CPF já estão cadastrados, caso contrário, salva o novo usuário.
     *
     * @param login Login do usuário.
     * @param senha Senha do usuário.
     * @param nome Nome do usuário.
     * @param cpf CPF do usuário.
     * @param email E-mail do usuário.
     * @param isAdmin Define se o usuário é administrador ou não.
     * @return O objeto usuário recém-cadastrado.
     * @throws Exception Lança exceção caso e-mail, login ou CPF já estejam cadastrados.
     */
    public Usuario cadastrar(String login, String senha, String nome, String cpf, String email, boolean isAdmin) throws Exception{
        Usuario usuario = new Usuario(login, senha, nome, cpf, email, isAdmin);
        if(this.getByEmail(usuario.getEmail()) != null){
            throw new SecurityException("E-mail já cadastrado!");
        }
        if (this.getByLogin(usuario.getLogin()) != null){
            throw new SecurityException("Login já cadastrado!");
        }
        if (this.getByCpf(usuario.getCpf()) != null){
            throw new SecurityException("CPF já cadastrado!");
        }

        usuarioFileManager.save(usuario);
        return usuario;
    }

    /**
     * Retorna todos os usuários cadastrados.
     *
     * @return Lista de todos os usuários.
     */
    public List<Usuario> getAll(){
        return usuarioFileManager.getAll();
    }

    /**
     * Retorna o usuário pelo ID.
     *
     * @param id ID do usuário.
     * @return O objeto usuário correspondente ao ID.
     */
    public Usuario getById(UUID id){
        return usuarioFileManager.getById(id);
    }

    /**
     * Remove o usuário pelo ID.
     *
     * @param id ID do usuário a ser removido.
     */
    public void delete(UUID id){
        usuarioFileManager.delete(id);
    }

    /**
     * Atualiza as informações de um usuário existente.
     * Verifica se os novos e-mail ou login já estão em uso por outro usuário.
     *
     * @param usuario Usuário a ser atualizado.
     * @param login Novo login do usuário.
     * @param senha Nova senha do usuário.
     * @param nome Novo nome do usuário.
     * @param email Novo e-mail do usuário.
     * @throws Exception Lança exceção caso e-mail, login ou CPF já estejam cadastrados.
     */
    public void update(Usuario usuario, String login, String senha, String nome, String email) throws Exception{
        if(this.getByEmail(email) != null && !email.equalsIgnoreCase(usuario.getEmail())){
            throw new SecurityException("E-mail já cadastrado!");
        }
        if (this.getByLogin(login) != null && !login.equalsIgnoreCase(usuario.getLogin())){
            throw new SecurityException("Login já cadastrado!");
        }
        usuario.setLogin(login);
        usuario.setSenha(senha);
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuarioFileManager.update(usuario);
    }

    /**
     * Realiza o login de um usuário.
     * Verifica se o login e a senha correspondem a um usuário existente.
     *
     * @param login Login do usuário.
     * @param senha Senha do usuário.
     * @return True se o login for bem-sucedido, false caso contrário.
     */
    public boolean login(String login, String senha){
        Usuario usuario = this.getByLogin(login);
        if (usuario == null) {
            System.out.println("Usuário não encontrado: " + login);
            return false; // Login falhou, pois o usuário não foi encontrado
        }
        return usuario.login(login, senha);
    }

    /**
     * Busca um usuário pelo e-mail.
     *
     * @param email E-mail do usuário.
     * @return O objeto usuário correspondente ao e-mail ou null se não encontrado.
     */
    public Usuario getByEmail(String email) {
        List<Usuario> usuarios = usuarioFileManager.getAll();
        return usuarios.stream()
                .filter(usuario -> usuario.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    /**
     * Busca um usuário pelo CPF.
     *
     * @param cpf CPF do usuário.
     * @return O objeto usuário correspondente ao CPF ou null se não encontrado.
     */
    public Usuario getByCpf(String cpf) {
        List<Usuario> usuarios = usuarioFileManager.getAll();
        return usuarios.stream()
                .filter(usuario -> usuario.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);
    }

    /**
     * Busca um usuário pelo login.
     *
     * @param login Login do usuário.
     * @return O objeto usuário correspondente ao login ou null se não encontrado.
     */
    public Usuario getByLogin(String login) {
        List<Usuario> usuarios = usuarioFileManager.getAll();
        return usuarios.stream()
                .filter(usuario -> usuario.getLogin().equals(login))
                .findFirst()
                .orElse(null);
    }

    /**
     * Remove todos os usuários cadastrados.
     */
    public void deleteAll(){
        usuarioFileManager.deleteAll();
    }
}
