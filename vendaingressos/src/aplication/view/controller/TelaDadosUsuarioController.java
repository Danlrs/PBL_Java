package aplication.view.controller;

import aplication.view.WindowState;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import aplication.model.Usuario;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Controlador para a tela de dados do usuário.
 */
public class TelaDadosUsuarioController {

    @FXML
    private Label labelLogin;

    @FXML
    private Label labelNome;

    @FXML
    private Label labelCpf;

    @FXML
    private Label labelEmail;

    @FXML
    private Button botaoNotificacoes;

    private Scene cenaAnterior;
    private Usuario usuarioAtual;

    /**
     * Define a cena anterior para permitir a navegação de volta.
     *
     * @param scene Cena anterior.
     */
    public void setSceneAnterior(Scene scene) {
        this.cenaAnterior = scene;
    }

    /**
     * Configura os dados do usuário atual e preenche os campos correspondentes na interface.
     *
     * @param usuario O usuário cujos dados serão exibidos.
     */
    public void setUsuario(Usuario usuario) {
        this.usuarioAtual = usuario;
        if (usuario != null) {
            labelLogin.setText("Login: " + usuario.getLogin());
            labelNome.setText("Nome: " + usuario.getNome());
            labelCpf.setText("CPF: " + usuario.getCpf());
            labelEmail.setText("E-mail: " + usuario.getEmail());
        }
    }

    /**
     * Carrega os dados do usuário atual para exibição na interface.
     */
    public void carregarDados() {
        if (usuarioAtual != null) {
            labelLogin.setText(usuarioAtual.getLogin());
            labelNome.setText(usuarioAtual.getNome());
            labelEmail.setText(usuarioAtual.getEmail());
            labelCpf.setText(usuarioAtual.getCpf());
        }
    }

    /**
     * Retorna à tela de eventos do sistema.
     */
    @FXML
    private void voltarParaEventos() {
        try {
            Stage stage = (Stage) labelLogin.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/aplication/view/fxml/TelaEventos.fxml"));
            Parent root = loader.load();

            TelaEventosController eventosController = loader.getController();
            eventosController.setUsuarioAtual(usuarioAtual);
            eventosController.carregarEventos();

            Scene cenaEventos = new Scene(root);
            cenaEventos.getStylesheets().add(getClass().getResource("/aplication/view/estilos.css").toExternalForm());
            stage.setScene(cenaEventos);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Desconecta o usuário atual e retorna à tela inicial.
     */
    @FXML
    private void desconectar() {
        try {
            Stage stage = (Stage) labelLogin.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/aplication/view/fxml/TelaInicial.fxml"));
            Parent root = loader.load();

            Scene cenaLogin = new Scene(root);
            String css = getClass().getResource("/aplication/view/estilos.css").toExternalForm();
            cenaLogin.getStylesheets().add(css);

            stage.setScene(cenaLogin);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Navega para a tela de compras do usuário atual.
     */
    @FXML
    private void mostrarCompras() {
        try {
            Stage stage = (Stage) labelLogin.getScene().getWindow();
            WindowState.saveState(stage);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/aplication/view/fxml/TelaMinhasCompras.fxml"));
            Parent root = loader.load();

            TelaMinhasComprasController controller = loader.getController();
            controller.setUsuarioAtual(usuarioAtual);
            controller.setCenaAnterior(labelLogin.getScene());

            Scene novaCena = new Scene(root);
            novaCena.getStylesheets().add(getClass().getResource("/aplication/view/estilos.css").toExternalForm());

            stage.setScene(novaCena);
            WindowState.applyState(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Permite a edição dos dados do usuário após a verificação de senha.
     */
    @FXML
    private void editarDados() {
        try {
            Stage stage = (Stage) labelLogin.getScene().getWindow();
            WindowState.saveState(stage);

            FXMLLoader loaderVerificarSenha = new FXMLLoader(getClass().getResource("/aplication/view/fxml/TelaVerificarSenha.fxml"));
            Parent rootVerificarSenha = loaderVerificarSenha.load();

            TelaVerificarSenhaController verificarSenhaController = loaderVerificarSenha.getController();

            FXMLLoader loaderEdicao = new FXMLLoader(getClass().getResource("/aplication/view/fxml/TelaEditarUsuario.fxml"));
            Parent rootEdicao = loaderEdicao.load();

            TelaEditarUsuarioController editarController = loaderEdicao.getController();
            editarController.setUsuario(usuarioAtual);
            editarController.setCenaAnterior(stage.getScene());
            verificarSenhaController.setCenaAnterior(stage.getScene());

            Scene cenaEdicao = new Scene(rootEdicao);
            cenaEdicao.getStylesheets().add(getClass().getResource("/aplication/view/estilos.css").toExternalForm());

            verificarSenhaController.setDados(usuarioAtual, cenaEdicao);

            Scene cenaVerificarSenha = new Scene(rootVerificarSenha);
            cenaVerificarSenha.getStylesheets().add(getClass().getResource("/aplication/view/estilos.css").toExternalForm());

            stage.setScene(cenaVerificarSenha);
            WindowState.applyState(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Exibe um pop-up contendo as notificações do usuário (recibos). O pop-up é posicionado ao lado do botão de notificações.
     * O pop-up exibe os recibos do usuário em áreas de texto (TextArea) e permite rolagem caso o conteúdo ultrapasse a altura do pop-up.
     * O pop-up é fechado automaticamente quando perde o foco ou quando o usuário clica fora dele.
     */
    @FXML
    private void mostrarNotificacoes() {
        VBox contentBox = new VBox();
        contentBox.setSpacing(15);
        contentBox.setPadding(new Insets(15));

        for (String recibo : usuarioAtual.getRecibos()) {
            TextArea reciboArea = new TextArea(recibo);
            reciboArea.setEditable(false);
            reciboArea.setWrapText(true);
            reciboArea.setPrefHeight(150);
            reciboArea.setStyle("-fx-font-family: 'Bold'; -fx-font-size: 12px;");
            contentBox.getChildren().add(reciboArea);
        }

        ScrollPane scrollPane = new ScrollPane(contentBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        Scene scene = new Scene(scrollPane);
        scene.getStylesheets().add(getClass().getResource("/aplication/view/estilos.css").toExternalForm());

        Stage popUpStage = new Stage();
        popUpStage.setTitle("Notificações");
        popUpStage.setScene(scene);

        popUpStage.setWidth(250);
        popUpStage.setHeight(200);
        popUpStage.setResizable(false);

        popUpStage.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                popUpStage.close();
            }
        });

        javafx.geometry.Point2D buttonLocation = botaoNotificacoes.localToScreen(botaoNotificacoes.getBoundsInLocal().getMinX(), botaoNotificacoes.getBoundsInLocal().getMinY());
        double xPosition = buttonLocation.getX() + botaoNotificacoes.getWidth() + 5;
        double yPosition = buttonLocation.getY();

        popUpStage.setX(xPosition);
        popUpStage.setY(yPosition);

        popUpStage.initStyle(StageStyle.UNDECORATED);
        popUpStage.show();
    }

}
