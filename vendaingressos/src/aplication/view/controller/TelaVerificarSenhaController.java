package aplication.view.controller;

import aplication.model.Usuario;
import aplication.view.WindowState;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class TelaVerificarSenhaController {

    @FXML
    private PasswordField campoSenha;

    @FXML
    private Label erroSenha;

    private Usuario usuarioAtual;
    private Scene telaEdicaoDados;
    private Scene cenaAnterior;

    /**
     * Define a cena anterior para permitir retorno.
     *
     * @param cenaAnterior a cena anterior
     */
    public void setCenaAnterior(Scene cenaAnterior) {
        this.cenaAnterior = cenaAnterior;
    }

    /**
     * Define os dados necessários para a verificação da senha.
     *
     * @param usuario          o usuário atual
     * @param telaEdicaoDados  a cena de edição de dados do usuário
     */
    public void setDados(Usuario usuario, Scene telaEdicaoDados) {
        this.usuarioAtual = usuario;
        this.telaEdicaoDados = telaEdicaoDados;
    }

    /**
     * Verifica a senha digitada no campo.
     * Caso seja correta, troca para a tela de edição de dados.
     * Caso contrário, exibe uma mensagem de erro.
     */
    @FXML
    private void verificarSenha() {
        String senhaDigitada = campoSenha.getText();

        if (usuarioAtual.getSenha().equals(senhaDigitada)) {
            Stage stage = (Stage) campoSenha.getScene().getWindow();
            stage.setScene(telaEdicaoDados);
        } else {
            erroSenha.setText("Senha incorreta!");
            campoSenha.setStyle("-fx-border-color: red;");
        }
    }

    /**
     * Cancela a operação e retorna para a cena anterior,
     * restaurando o estado da janela.
     */
    @FXML
    private void cancelar() {
        Stage stage = (Stage) campoSenha.getScene().getWindow();
        WindowState.saveState(stage);
        stage.setScene(cenaAnterior);
        WindowState.applyState(stage);
    }
}
