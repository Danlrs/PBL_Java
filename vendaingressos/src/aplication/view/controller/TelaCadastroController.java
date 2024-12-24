package aplication.view.controller;

import aplication.view.WindowState;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import aplication.controller.UsuarioController;
import aplication.model.Usuario;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controlador para a tela de cadastro de usuários.
 * Gerencia a validação dos campos e a lógica de cadastro de novos usuários.
 */
public class TelaCadastroController {

    /** Campo de entrada para o nome do usuário. */
    @FXML
    private TextField campoNome;

    /** Campo de entrada para o e-mail do usuário. */
    @FXML
    private TextField campoEmail;

    /** Campo de entrada para o CPF do usuário. */
    @FXML
    private TextField campoCpf;

    /** Campo de entrada para o login do usuário. */
    @FXML
    private TextField campoLogin;

    /** Campo de entrada para a senha do usuário. */
    @FXML
    private PasswordField campoSenha;

    /** Checkbox para definir se o usuário será administrador. */
    @FXML
    private CheckBox checkAdmin;

    /** Label para exibir mensagens de erro relacionadas ao e-mail. */
    @FXML
    private Label erroEmail;

    /** Label para exibir mensagens de erro relacionadas ao CPF. */
    @FXML
    private Label erroCpf;

    /** Label para exibir mensagens gerais de erro no cadastro. */
    @FXML
    private Label erroCadastro;

    /** Controlador para a lógica de usuários. */
    private UsuarioController usuarioController;

    /**
     * Construtor padrão, inicializa o controlador de usuários.
     */
    public TelaCadastroController() {
        this.usuarioController = new UsuarioController();
    }

    /**
     * Inicializa o controlador e configura os listeners necessários.
     * Limita o campo CPF a 11 dígitos.
     */
    @FXML
    private void initialize() {
        campoCpf.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 11) {
                campoCpf.setText(oldValue); // Impede a inserção além do limite
            }
        });
    }

    /**
     * Realiza o cadastro do usuário com os dados fornecidos.
     * Verifica a validade dos campos e registra o usuário no sistema.
     */
    @FXML
    private void cadastrar() {
        String nome = campoNome.getText();
        String email = campoEmail.getText();
        String cpf = campoCpf.getText();
        String login = campoLogin.getText();
        String senha = campoSenha.getText();
        boolean isAdmin = checkAdmin.isSelected();

        try {
            // Verificar formato do e-mail e CPF
            if (!isEmailValido(email)) {
                throw new IllegalArgumentException("Formato de e-mail inválido!");
            }
            if (!isCpfValido(cpf)) {
                throw new IllegalArgumentException("O CPF deve conter apenas números!");
            }

            // Tentando cadastrar o usuário
            Usuario usuario = usuarioController.cadastrar(login, senha, nome, cpf, email, isAdmin);

            // Se o cadastro for bem-sucedido, mostrar mensagem de sucesso
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cadastro Realizado");
            alert.setHeaderText("Usuário Cadastrado");
            alert.setContentText("O usuário foi cadastrado com sucesso!");

            // Configurar a ação do botão OK no alert para retornar à TelaInicial
            alert.setOnHidden(e -> voltar()); // Quando o alert for fechado, voltar para TelaInicial
            alert.showAndWait();

            // Limpar a mensagem de erro, caso o cadastro tenha sido bem-sucedido
            erroCadastro.setText("");

        } catch (Exception e) {
            // Se houver erro, mostrar mensagem de erro na label
            erroCadastro.setText(e.getMessage());
            erroCadastro.setStyle("-fx-text-fill: red;");
        }
    }

    /**
     * Valida o formato do e-mail inserido no campo correspondente.
     * Exibe mensagens de erro ou sucesso no campo e estiliza sua borda.
     */
    @FXML
    private void validarEmail() {
        String email = campoEmail.getText();

        if (email.isEmpty()) {
            erroEmail.setText("");
            campoEmail.setStyle("");
        } else if (!isEmailValido(email)) {
            erroEmail.setText("E-mail inválido!");
            campoEmail.setStyle("-fx-border-color: red;");
        } else {
            erroEmail.setText("");
            campoEmail.setStyle("-fx-border-color: green;");
        }
    }

    /**
     * Valida o formato do CPF inserido no campo correspondente.
     * Exibe mensagens de erro ou sucesso no campo e estiliza sua borda.
     */
    @FXML
    private void validarCpf() {
        String cpf = campoCpf.getText();

        if (cpf.isEmpty()) {
            erroCpf.setText("");
            campoCpf.setStyle("");
        } else if (!isCpfValido(cpf)) {
            erroCpf.setText("CPF inválido!");
            campoCpf.setStyle("-fx-border-color: red;");
        } else {
            erroCpf.setText("");
            campoCpf.setStyle("-fx-border-color: green;");
        }
    }

    /**
     * Verifica se o e-mail segue um formato válido.
     *
     * @param email o e-mail a ser validado.
     * @return {@code true} se o e-mail for válido, caso contrário, {@code false}.
     */
    private boolean isEmailValido(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Verifica se o CPF contém apenas números e possui 11 dígitos.
     *
     * @param cpf o CPF a ser validado.
     * @return {@code true} se o CPF for válido, caso contrário, {@code false}.
     */
    private boolean isCpfValido(String cpf) {
        return cpf.matches("\\d{11}");
    }

    /**
     * Retorna à tela inicial, aplicando o estado anterior da janela.
     */
    @FXML
    private void voltar() {
        try {
            Stage stage = (Stage) campoNome.getScene().getWindow();

            // Salva o estado atual da janela
            WindowState.saveState(stage);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/aplication/view/fxml/TelaInicial.fxml"));
            Scene scene = new Scene(loader.load(), stage.getWidth(), stage.getHeight());

            scene.getStylesheets().add(getClass().getResource("/aplication/view/estilos.css").toExternalForm());
            stage.setScene(scene);

            // Aplica o estado anterior da janela
            WindowState.applyState(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
