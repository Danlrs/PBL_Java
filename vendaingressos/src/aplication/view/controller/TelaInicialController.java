package aplication.view.controller;

import aplication.controller.UsuarioController;
import aplication.model.Usuario;
import aplication.view.WindowState;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controlador para a tela inicial da aplicação.
 * Responsável por lidar com as interações do usuário, como login e navegação para outras telas.
 */
public class TelaInicialController {

    /** Controlador de lógica relacionado aos usuários. */
    private final UsuarioController usuarioController = new UsuarioController();

    /** Campo de entrada para o login do usuário. */
    @FXML
    private TextField campoLogin;

    /** Campo de entrada para a senha do usuário. */
    @FXML
    private PasswordField campoSenha;

    /** Rótulo para exibir mensagens de erro relacionadas ao login. */
    @FXML
    private Label erroLogin;

    /** Rótulo para exibir mensagens de erro relacionadas à senha. */
    @FXML
    private Label erroSenha;

    /**
     * Metodo acionado ao tentar realizar o login.
     * Valida os campos de entrada e verifica as credenciais do usuário no sistema.
     */
    @FXML
    private void entrar() {
        String login = campoLogin.getText().trim();
        String senha = campoSenha.getText().trim();
        boolean valido = true;
        // Limpar mensagens anteriores
        erroLogin.setText("");
        erroSenha.setText("");
        campoLogin.setStyle("");
        campoSenha.setStyle("");
        // Validação de campos
        if (login.isEmpty()) {
            erroLogin.setText("O campo de login não pode estar vazio!");
            campoLogin.setStyle("-fx-border-color: red;");
            valido = false;
        }
        if (senha.isEmpty()) {
            erroSenha.setText("O campo de senha não pode estar vazio!");
            campoSenha.setStyle("-fx-border-color: red;");
            valido = false;
        }
        if (!valido) {
            return;
        }
        // Verificação de login no back-end
        boolean loginValido = usuarioController.login(login, senha);
        Usuario usuarioLogado = usuarioController.getByLogin(login);

        if (loginValido && usuarioLogado != null) {
            abrirTelaEventos(usuarioLogado);
        } else {
            erroSenha.setText("Login ou senha incorretos!");
            campoLogin.setStyle("-fx-border-color: red;");
            campoSenha.setStyle("-fx-border-color: red;");
        }
    }

    /**
     * Abre a tela de eventos para o usuário autenticado.
     *
     * @param usuarioLogado o usuário atualmente autenticado.
     */
    private void abrirTelaEventos(Usuario usuarioLogado) {
        try {
            Stage stage = (Stage) campoLogin.getScene().getWindow();

            // Salva o estado atual da janela
            WindowState.saveState(stage);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/aplication/view/fxml/TelaEventos.fxml"));
            Scene scene = new Scene(loader.load(), stage.getWidth(), stage.getHeight());

            TelaEventosController controller = loader.getController();
            controller.setUsuarioAtual(usuarioLogado);

            scene.getStylesheets().add(getClass().getResource("/aplication/view/estilos.css").toExternalForm());
            stage.setScene(scene);

            // Aplica o estado anterior da janela
            WindowState.applyState(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Abre a tela de cadastro de novos usuários.
     */
    @FXML
    private void abrirCadastro() {
        try {
            Stage stage = (Stage) campoLogin.getScene().getWindow();

            // Salva o estado atual da janela
            WindowState.saveState(stage);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/aplication/view/fxml/TelaCadastro.fxml"));
            Scene scene = new Scene(loader.load());

            scene.getStylesheets().add(getClass().getResource("/aplication/view/estilos.css").toExternalForm());
            stage.setScene(scene);

            // Aplica o estado anterior da janela
            WindowState.applyState(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
