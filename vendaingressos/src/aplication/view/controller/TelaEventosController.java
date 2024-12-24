package aplication.view.controller;

import aplication.view.WindowState;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import aplication.controller.EventoController;
import aplication.model.Evento;
import aplication.model.Usuario;

import java.io.IOException;

/**
 * Controlador para a tela de exibição de eventos.
 * Gerencia a exibição, compra de ingressos e funcionalidades relacionadas a eventos.
 */
public class TelaEventosController {

    private Stage telaAdicionarEventoStage;

    /** Contêiner que lista os eventos disponíveis. */
    @FXML
    private VBox eventosContainer;

    /** Botão para adicionar novos eventos (visível apenas para administradores). */
    @FXML
    private Button botaoCadastrar;

    /** Label que exibe uma saudação com o nome do usuário. */
    @FXML
    private Label labelUsuario;

    /** Link para desconectar o usuário atual. */
    @FXML
    private Label linkDesconectar;

    /** Ícone que redireciona para os dados do usuário. */
    @FXML
    private Button iconeUsuario;

    /** Controlador para gerenciar os eventos. */
    private final EventoController eventoController = new EventoController();

    /** Usuário atualmente logado. */
    private Usuario usuarioAtual;

    /**
     * Inicializa o controlador, configurando elementos visuais e carregando eventos.
     */
    @FXML
    private void initialize() {
        // Inicialmente oculta o botão de cadastrar eventos
        botaoCadastrar.setVisible(false);
        botaoCadastrar.setManaged(false);
        carregarEventos();
    }

    /**
     * Define o usuário atualmente logado e ajusta a interface de acordo com seu papel.
     *
     * @param usuario o usuário logado.
     */
    public void setUsuarioAtual(Usuario usuario) {
        this.usuarioAtual = usuario;

        if (usuarioAtual != null) {
            labelUsuario.setText("Olá, " + usuarioAtual.getNome() + "!");
            if (usuarioAtual.isAdmin()) {
                botaoCadastrar.setVisible(true);
                botaoCadastrar.setManaged(true);
            }
        }
    }

    /**
     * Desconecta o usuário atual e retorna à tela inicial.
     */
    @FXML
    private void desconectar() {
        try {
            Stage stage = (Stage) eventosContainer.getScene().getWindow();
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
     * Exibe a tela de dados do usuário atual.
     */
    @FXML
    private void mostrarDadosUsuario() {
        try {
            Stage stage = (Stage) eventosContainer.getScene().getWindow();

            WindowState.saveState(stage);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/aplication/view/fxml/TelaDadosUsuario.fxml"));
            Parent root = loader.load();

            TelaDadosUsuarioController controller = loader.getController();
            controller.setUsuario(usuarioAtual);
            controller.setSceneAnterior(eventosContainer.getScene());

            Scene novaCena = new Scene(root);
            String css = getClass().getResource("/aplication/view/estilos.css").toExternalForm();
            novaCena.getStylesheets().add(css);

            stage.setScene(novaCena);

            WindowState.applyState(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carrega a lista de eventos disponíveis no contêiner da interface.
     */
    public void carregarEventos() {
        eventosContainer.getChildren().clear();

        for (Evento evento : eventoController.getDisponiveis()) {
            VBox eventoBox = criarBoxEvento(evento);
            eventosContainer.getChildren().add(eventoBox);
        }
    }

    /**
     * Cria uma representação visual de um evento na interface.
     *
     * @param evento o evento a ser exibido.
     * @return um contêiner visual representando o evento.
     */
    private VBox criarBoxEvento(Evento evento) {
        VBox box = new VBox(5);
        box.getStyleClass().add("evento-box");
        box.setPadding(new Insets(10));
        box.setStyle("-fx-background-color: #34495e; -fx-border-color: #ecf0f1; -fx-border-radius: 5;");

        Label nome = new Label("Nome: " + evento.getNome());
        Label descricao = new Label("Descrição: " + evento.getDescricao());
        Label data = new Label("Data: " + evento.getData().toString());
        Label preco = new Label("Preço: R$ " + evento.getPreco());
        Label capacidade = new Label("Capacidade: " + evento.getAssentosDisponiveis().size());

        nome.setStyle("-fx-font-weight: bold;");

        HBox linhaInferior = new HBox(10);

        Button botaoComprar = new Button("Comprar Ingresso");
        botaoComprar.getStyleClass().add(
                evento.getAssentosDisponiveis().isEmpty() ? "botao-desativado" : "botao"
        );
        botaoComprar.setOnAction(e -> {
            if (!evento.getAssentosDisponiveis().isEmpty()) {
                comprarIngresso(evento);
            }
        });

        linhaInferior.setStyle("-fx-alignment: center-right;");
        linhaInferior.getChildren().addAll(preco, botaoComprar);

        box.getChildren().addAll(nome, descricao, data, capacidade, linhaInferior);

        return box;
    }

    /**
     * Inicia o processo de compra de ingressos para o evento selecionado.
     *
     * @param evento o evento para o qual o ingresso será comprado.
     */
    private void comprarIngresso(Evento evento) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/aplication/view/fxml/TelaCompra.fxml"));
            Parent root = loader.load();

            TelaCompraController controller = loader.getController();
            controller.setEvento(evento);
            controller.setUsuario(usuarioAtual);
            controller.setCenaAnterior(eventosContainer.getScene());

            Scene novaCena = new Scene(root);
            String css = getClass().getResource("/aplication/view/estilos.css").toExternalForm();
            novaCena.getStylesheets().add(css);

            Stage stage = (Stage) eventosContainer.getScene().getWindow();

            WindowState.saveState(stage);

            stage.setScene(novaCena);

            WindowState.applyState(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Exibe a tela para adicionar um novo evento.
     */
    @FXML
    private void adicionarEvento() {
        try {
            // Verifica se a tela já está aberta
            if (telaAdicionarEventoStage != null) {
                // Se o Stage já existe, traz para frente
                if (telaAdicionarEventoStage.isIconified()) {
                    telaAdicionarEventoStage.setIconified(false); // Restaura se minimizado
                }
                telaAdicionarEventoStage.toFront(); // Traz para frente
                return;
            }

            // Cria uma nova tela, se não existir
            telaAdicionarEventoStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/aplication/view/fxml/TelaAdicionarEvento.fxml"));
            Parent root = loader.load();

            TelaAdicionarEventoController controller = loader.getController();
            controller.setUsuarioAtual(usuarioAtual);

            Scene scene = new Scene(root);
            String css = getClass().getResource("/aplication/view/estilos.css").toExternalForm();
            scene.getStylesheets().add(css);

            telaAdicionarEventoStage.setScene(scene);
            telaAdicionarEventoStage.setTitle("Adicionar Evento");

            // Evento para limpar a referência ao fechar a tela
            telaAdicionarEventoStage.setOnHidden(event -> {
                telaAdicionarEventoStage = null; // Remove a referência ao Stage
                carregarEventos();
            });

            telaAdicionarEventoStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
