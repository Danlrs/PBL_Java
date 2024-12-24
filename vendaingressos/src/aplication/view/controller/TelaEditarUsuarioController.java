package aplication.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import aplication.model.Usuario;
import aplication.controller.UsuarioController;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TelaEditarUsuarioController {

    @FXML
    private TextField campoLogin;

    @FXML
    private TextField campoNome;

    @FXML
    private PasswordField campoSenha;

    @FXML
    private TextField campoEmail;

    @FXML
    private Label erroNome;
    @FXML
    private Label erroLogin;
    @FXML
    private Label erroEmail;
    @FXML
    private Label erroSenha;

    private Usuario usuarioAtual;
    private Scene cenaAnterior;

    /**
     * Define o usuário atual a ser editado.
     *
     * @param usuario o usuário a ser configurado
     */
    public void setUsuario(Usuario usuario) {
        this.usuarioAtual = usuario;
        if (usuario != null) {
            campoLogin.setText(usuario.getLogin());
            campoNome.setText(usuario.getNome());
            campoEmail.setText(usuario.getEmail());
        }
    }

    /**
     * Define a cena anterior para permitir retorno.
     *
     * @param cenaAnterior a cena anterior
     */
    public void setCenaAnterior(Scene cenaAnterior) {
        this.cenaAnterior = cenaAnterior;
    }

    /**
     * Salva as alterações realizadas nos campos de edição do usuário.
     */
    @FXML
    private void salvarAlteracoes() {
        String novoLogin = campoLogin.getText().trim();
        String novaSenha = campoSenha.getText().trim();
        String novoNome = campoNome.getText().trim();
        String novoEmail = campoEmail.getText().trim();

        resetarErros();

        boolean erro = false;

        if (novoLogin.isEmpty()) {
            mostrarErro(campoLogin, erroLogin, "O login não pode estar vazio.");
            erro = true;
        }

        if (novaSenha.isEmpty()) {
            mostrarErro(campoSenha, erroSenha, "A senha não pode estar vazia.");
            erro = true;
        }

        if (novoNome.isEmpty()) {
            mostrarErro(campoNome, erroNome, "O nome não pode estar vazio.");
            erro = true;
        }

        if (novoEmail.isEmpty()) {
            mostrarErro(campoEmail, erroEmail, "O e-mail não pode estar vazio.");
            erro = true;
        } else if (!isEmailValido(novoEmail)) {
            mostrarErro(campoEmail, erroEmail, "Formato de e-mail inválido.");
            erro = true;
        }

        if (erro) return;

        try {
            UsuarioController usuarioController = new UsuarioController();
            usuarioController.update(usuarioAtual, novoLogin, novaSenha, novoNome, novoEmail);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alteração Realizada");
            alert.setHeaderText("Dados Alterados");
            alert.setContentText("Os dados foram atualizados com sucesso!");
            alert.showAndWait();

            voltarParaDadosUsuario();

        } catch (SecurityException e) {
            if (e.getMessage().contains("E-mail")) {
                mostrarErro(campoEmail, erroEmail, "E-mail já cadastrado.");
            } else if (e.getMessage().contains("Login")) {
                mostrarErro(campoLogin, erroLogin, "Login já cadastrado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reseta os estilos e mensagens de erro dos campos de entrada.
     */
    private void resetarErros() {
        campoLogin.setStyle("");
        campoSenha.setStyle("");
        campoNome.setStyle("");
        campoEmail.setStyle("");

        erroLogin.setText("");
        erroSenha.setText("");
        erroNome.setText("");
        erroEmail.setText("");
    }

    /**
     * Mostra uma mensagem de erro em um campo específico.
     *
     * @param campo     o campo de entrada onde ocorreu o erro
     * @param labelErro o label onde será exibida a mensagem de erro
     * @param mensagem  a mensagem de erro
     */
    private void mostrarErro(TextField campo, Label labelErro, String mensagem) {
        campo.setStyle("-fx-border-color: red;");
        labelErro.setText(mensagem);
        labelErro.setStyle("-fx-text-fill: red;");
    }

    /**
     * Verifica se o e-mail informado possui um formato válido.
     *
     * @param email o e-mail a ser validado
     * @return true se o e-mail for válido, false caso contrário
     */
    private boolean isEmailValido(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Retorna para a tela de dados do usuário.
     */
    @FXML
    private void voltarParaDadosUsuario() {
        try {
            Stage stage = (Stage) campoLogin.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/aplication/view/fxml/TelaDadosUsuario.fxml"));
            Parent root = loader.load();

            TelaDadosUsuarioController dadosUsuarioController = loader.getController();
            dadosUsuarioController.setUsuario(usuarioAtual);
            dadosUsuarioController.carregarDados();

            Scene cenaDadosUsuario = new Scene(root);
            cenaDadosUsuario.getStylesheets().add(getClass().getResource("/aplication/view/estilos.css").toExternalForm());
            stage.setScene(cenaDadosUsuario);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
