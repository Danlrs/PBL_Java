package aplication.facade.UserTestFacade;

import aplication.model.Usuario;
import aplication.controller.UsuarioController;

import java.util.UUID;

/**
 * Facade para facilitar os testes e interações com os usuários.
 *
 * Esta classe oferece uma interface simplificada para criar, modificar e acessar dados de usuários,
 * além de permitir testes com o controlador de usuários.
 */
public class UserTestFacade {

    // Controlador de Usuário utilizado para gerenciar as operações.
    private final UsuarioController usuarioController;

    /**
     * Construtor da classe UserTestFacade.
     * Inicializa o controlador de usuário.
     */
    public UserTestFacade() {
        this.usuarioController = new UsuarioController();
    }

    /**
     * Cria um novo usuário.
     *
     * @param login    O login do usuário.
     * @param password A senha do usuário.
     * @param name     O nome do usuário.
     * @param cpf      O CPF do usuário.
     * @param email    O email do usuário.
     * @param isAdmin  Indica se o usuário é administrador.
     * @return true se o usuário foi criado com sucesso, false se falhou.
     */
    public boolean create(String login, String password, String name, String cpf, String email, Boolean isAdmin) {
        Usuario usuario = null;
        try {
            usuario = usuarioController.cadastrar(login, password, name, cpf, email, isAdmin);
        } catch (Exception e) {
            throw new SecurityException(e.getMessage());
        }
        return usuario != null;
    }

    /**
     * Retorna o login do usuário com base no email.
     *
     * @param email O email do usuário.
     * @return O login do usuário.
     */
    public String getLoginByUserEmail(String email) {
        Usuario usuario = usuarioController.getByEmail(email);
        return usuario.getLogin();
    }

    /**
     * Retorna o nome do usuário com base no email.
     *
     * @param email O email do usuário.
     * @return O nome do usuário.
     */
    public String getNameByUserEmail(String email) {
        Usuario usuario = usuarioController.getByEmail(email);
        return usuario.getNome();
    }

    /**
     * Retorna o email do usuário com base no email fornecido.
     *
     * @param email O email do usuário.
     * @return O email do usuário.
     */
    public String getEmailByUserEmail(String email) {
        Usuario usuario = usuarioController.getByEmail(email);
        return usuario.getEmail();
    }

    /**
     * Retorna a senha do usuário com base no email.
     *
     * @param email O email do usuário.
     * @return A senha do usuário.
     */
    public String getPasswordByUserEmail(String email) {
        Usuario usuario = usuarioController.getByEmail(email);
        return usuario.getSenha();
    }

    /**
     * Retorna o CPF do usuário com base no email.
     *
     * @param email O email do usuário.
     * @return O CPF do usuário.
     */
    public String getCpfByUserEmail(String email) {
        Usuario usuario = usuarioController.getByEmail(email);
        return usuario.getCpf();
    }

    /**
     * Verifica se o usuário com o email fornecido é administrador.
     *
     * @param email O email do usuário.
     * @return true se o usuário for administrador, false caso contrário.
     */
    public boolean getIsAdminByUserEmail(String email) {
        Usuario usuario = usuarioController.getByEmail(email);
        return usuario.isAdmin();
    }

    /**
     * Retorna o objeto de usuário com base no email.
     *
     * @param email O email do usuário.
     * @return O objeto Usuario.
     */
    public Usuario getByEmail(String email) {
        return usuarioController.getByEmail(email);
    }

    /**
     * Retorna o ID do usuário com base no email.
     *
     * @param email O email do usuário.
     * @return O ID do usuário.
     */
    public UUID getUserIdByEmail(String email) {
        return usuarioController.getByEmail(email).getId();
    }

    /**
     * Atualiza o nome do usuário com base no email.
     *
     * @param name  O novo nome do usuário.
     * @param email O email do usuário a ser atualizado.
     */
    public void setNameByUserEmail(String name, String email) {
        Usuario usuario = usuarioController.getByEmail(email);
        usuario.setNome(name);
        try {
            usuarioController.update(usuario, usuario.getLogin(), usuario.getSenha(), name, email);
        } catch (Exception e) {
            throw new SecurityException(e.getMessage());
        }
    }

    /**
     * Atualiza a senha do usuário com base no email.
     *
     * @param password A nova senha do usuário.
     * @param email    O email do usuário a ser atualizado.
     */
    public void setPasswordByUserEmail(String password, String email) {
        Usuario usuario = usuarioController.getByEmail(email);
        usuario.setSenha(password);
        try {
            usuarioController.update(usuario, usuario.getLogin(), password, usuario.getNome(), email);
        } catch (Exception e) {
            throw new SecurityException(e.getMessage());
        }
    }

    /**
     * Atualiza o email do usuário com base no email anterior.
     *
     * @param newEmail O novo email do usuário.
     * @param email    O email atual do usuário.
     */
    public void setEmailByUserEmail(String newEmail, String email) {
        Usuario usuario = usuarioController.getByEmail(email);
        try {
            usuarioController.update(usuario, usuario.getLogin(), usuario.getSenha(), usuario.getNome(), newEmail);
        } catch (Exception e) {
            throw new SecurityException(e.getMessage());
        }
    }

    /**
     * Retorna o número total de usuários cadastrados.
     *
     * @return O tamanho da lista de usuários.
     */
    public int getSizeUsers() {
        return usuarioController.getAll().size();
    }

    /**
     * Realiza o login do usuário com base no login e senha.
     *
     * @param login    O login do usuário.
     * @param password A senha do usuário.
     * @return true se o login for bem-sucedido, false caso contrário.
     */
    public boolean login(String login, String password) {
        return usuarioController.login(login, password);
    }

    /**
     * Verifica se existe um usuário com o email fornecido.
     *
     * @param email O email do usuário.
     * @return true se o usuário existir, false caso contrário.
     */
    public boolean thereIsUserWithEmail(String email) {
        Usuario usuario = usuarioController.getByEmail(email);
        return usuario != null;
    }

    /**
     * Deleta um usuário com base no email fornecido.
     *
     * @param email O email do usuário a ser deletado.
     */
    public void deleteByUserEmail(String email) {
        Usuario usuario = usuarioController.getByEmail(email);
        usuarioController.delete(usuario.getId());
    }

    /**
     * Deleta todos os usuários cadastrados.
     */
    public void deleteAllUsers() {
        usuarioController.deleteAll();
    }
}
