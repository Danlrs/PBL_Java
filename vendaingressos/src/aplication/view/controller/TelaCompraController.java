package aplication.view.controller;

import aplication.controller.*;
import aplication.model.Cartao;
import aplication.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import aplication.model.Evento;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Controlador para a tela de compra de ingressos.
 */
public class TelaCompraController {

    private Usuario usuarioAtual;
    private CompraController compraController = new CompraController();
    private CartaoController cartaoController = new CartaoController();
    private IngressoController ingressoController = new IngressoController();
    private UsuarioController usuarioController = new UsuarioController();

    private Stage stageConfirmacaoCompra;

    private Stage popUpStage;

    @FXML
    private Label labelEvento;

    @FXML
    private Label detalhesEvento;

    @FXML
    private FlowPane assentosContainer;

    @FXML
    private Label opcaoBoleto;

    @FXML
    private Label opcaoCartao;

    @FXML
    private VBox containerCartao;

    @FXML
    private VBox containerBoleto;

    @FXML
    private TextField numeroCartao;

    @FXML
    private TextField nomeCartao;

    @FXML
    private TextField validadeCartao;

    @FXML
    private PasswordField cvvCartao;

    @FXML
    private Label erroNumeroCartao;

    @FXML
    private Label erroCVVCartao;

    @FXML
    private Label erroNomeCartao;

    @FXML
    private Label erroValidadeCartao;

    @FXML
    private Label erroFormaPagamento;

    @FXML
    private Label erroAssento;

    @FXML
    private Button carregarCartao;

    private Evento eventoAtual;
    private Scene cenaAnterior;

    private String formaPagamentoSelecionada = null;

    /**
     * Define o evento atual.
     * @param evento Evento selecionado.
     */
    public void setEvento(Evento evento) {
        this.eventoAtual = evento;
        detalhesEvento.setText(
                "Nome: " + evento.getNome() + "\n" +
                        "Descrição: " + evento.getDescricao() + "\n" +
                        "Data: " + evento.getData() + "\n" +
                        "Preço: R$ " + evento.getPreco()
        );
        carregarAssentos();
    }

    /**
     * Define o usuário atual.
     * @param usuario Usuário logado.
     */
    public void setUsuario(Usuario usuario) {
        this.usuarioAtual = usuario;
    }

    /**
     * Define a cena anterior.
     * @param cenaAnterior Cena anterior.
     */
    public void setCenaAnterior(Scene cenaAnterior) {
        this.cenaAnterior = cenaAnterior;
    }

    /**
     * Carrega os assentos disponíveis para o evento.
     */
    private void carregarAssentos() {
        assentosContainer.getChildren().clear();
        List<String> assentosDisponiveis = eventoAtual.getAssentosDisponiveis();

        for (String nomeAssento : assentosDisponiveis) {
            Label assento = new Label(nomeAssento);
            assento.getStyleClass().add("assento");
            assento.setOnMouseClicked(e -> selecionarAssento(assento));
            assentosContainer.getChildren().add(assento);
        }
    }

    /**
     * Seleciona ou desseleciona um assento.
     * @param assento Assento clicado.
     */
    private void selecionarAssento(Label assento) {
        String selecionado = "-fx-background-color: #27ae60; -fx-text-fill: #ecf0f1;";
        String naoSelecionado = "-fx-background-color: #34495e; -fx-text-fill: #ecf0f1; -fx-border-color: #7f8c8d;";

        assento.setStyle(
                assento.getStyle().contains("background-color: #27ae60;") ? naoSelecionado : selecionado
        );
    }

    /**
     * Inicializa os componentes da interface.
     */
    @FXML
    public void initialize() {
        formaPagamentoSelecionada = null;
        opcaoBoleto.setStyle("-fx-underline: true; -fx-text-fill: #d35400;");
        opcaoCartao.setStyle("-fx-underline: true; -fx-text-fill: #d35400;");
        containerCartao.setVisible(false);
        containerCartao.setManaged(false);
        containerBoleto.setVisible(false);
        containerBoleto.setManaged(false);
        carregarCartao.setVisible(false);
        carregarCartao.setOnAction(e -> carregarCartoes());

        configurarNumeroCartao();
        configurarCvvCartao();
        configurarValidadeCartao();
        configurarNomeCartao();
    }

    /**
     * Configura a validação do campo de número do cartão.
     */
    private void configurarNumeroCartao() {
        numeroCartao.setTextFormatter(new TextFormatter<>(change -> {
            String novoTexto = change.getControlNewText();

            if (!novoTexto.matches("\\d{0,16}")) {
                return null;
            }

            return change;
        }));

        numeroCartao.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                String texto = numeroCartao.getText();

                if (texto.matches("\\d{16}")) {
                    numeroCartao.setStyle("");
                    erroNumeroCartao.setText("");
                } else {
                    numeroCartao.setStyle("-fx-border-color: red;");
                    erroNumeroCartao.setText("Número do cartão inválido. Deve conter 16 dígitos.");
                }
            }
        });
    }

    /**
     * Configura a validação do campo CVV do cartão.
     */
    private void configurarCvvCartao() {
        cvvCartao.setTextFormatter(new TextFormatter<>(change -> {
            String novoTexto = change.getControlNewText();
            return novoTexto.matches("\\d{0,3}") ? change : null;
        }));

        cvvCartao.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                String texto = cvvCartao.getText();

                if (texto.matches("\\d{3}")) {
                    cvvCartao.setStyle("");
                    erroCVVCartao.setText("");
                } else {
                    cvvCartao.setStyle("-fx-border-color: red;");
                    erroCVVCartao.setText("CVV inválido. Deve conter 3 dígitos.");
                }
            }
        });
    }

    /**
     * Configura a validação do campo de validade do cartão.
     */
    private void configurarValidadeCartao() {
        validadeCartao.setTextFormatter(new TextFormatter<>(change -> {
            String novoTexto = change.getControlNewText();

            if (!novoTexto.matches("\\d{0,2}/?\\d{0,2}")) {
                return null;
            }

            if (change.isAdded()) {
                int length = novoTexto.length();

                if (length == 2 && !novoTexto.contains("/")) {
                    change.setText(novoTexto.charAt(1) + "/");
                    change.setCaretPosition(3);
                    change.setAnchor(3);
                }
            }

            return change;
        }));

        validadeCartao.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                String texto = validadeCartao.getText();

                if (texto.matches("\\d{2}/\\d{2}")) {
                    try {
                        int mes = Integer.parseInt(texto.substring(0, 2));
                        int ano = Integer.parseInt(texto.substring(3, 5));

                        if (mes >= 1 && mes <= 12) {
                            validadeCartao.setStyle("");
                            erroValidadeCartao.setText("");
                        } else {
                            validadeCartao.setStyle("-fx-border-color: red;");
                            erroValidadeCartao.setText("Mês inválido. Deve ser entre 01 e 12.");
                        }
                    } catch (NumberFormatException e) {
                        validadeCartao.setStyle("-fx-border-color: red;");
                        erroValidadeCartao.setText("Formato inválido. Use MM/AA.");
                    }
                } else {
                    validadeCartao.setStyle("-fx-border-color: red;");
                    erroValidadeCartao.setText("Formato inválido. Use MM/AA.");
                }
            }
        });
    }

    /**
     * Configura o campo de texto para o nome do cartão, incluindo formatação e validação.
     */
    private void configurarNomeCartao() {
        nomeCartao.setTextFormatter(new TextFormatter<>(change -> {
            String novoTexto = change.getControlNewText();
            return novoTexto.matches("[a-zA-Z\\s]*") ? change : null;
        }));

        nomeCartao.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                String texto = nomeCartao.getText();

                if (!texto.trim().isEmpty()) {
                    nomeCartao.setStyle("");
                    erroNomeCartao.setText("");
                } else {
                    nomeCartao.setStyle("-fx-border-color: red;");
                    erroNomeCartao.setText("O nome do cartão não pode estar vazio.");
                }
            }
        });
    }

    /**
     * Obtém a lista de assentos selecionados pelo usuário na interface.
     *
     * @return Lista de assentos selecionados.
     */
    private List<String> obterAssentosSelecionados() {
        List<String> assentos = new ArrayList<>();
        for (var node : assentosContainer.getChildren()) {
            if (node instanceof Label) {
                Label assento = (Label) node;
                if (assento.getStyle().contains("#27ae60")) {
                    assentos.add(assento.getText());
                }
            }
        }
        return assentos;
    }

    /**
     * Converte uma string de validade no formato MM/AA em um objeto Date.
     *
     * @param validade String no formato MM/AA.
     * @return Objeto Date correspondente à validade.
     * @throws IllegalArgumentException Se a string não for válida.
     */
    private Date obterDataValidade(String validade) {
        try {
            String[] partes = validade.split("/");
            int mes = Integer.parseInt(partes[0]);
            int ano = Integer.parseInt(partes[1]) + 2000;
            Calendar calendar = Calendar.getInstance();
            calendar.set(ano, mes - 1, 1, 0, 0, 0);
            return calendar.getTime();
        } catch (Exception e) {
            throw new IllegalArgumentException("Data de validade inválida.");
        }
    }

    /**
     * Seleciona a opção de pagamento por boleto, ajustando a interface.
     */
    @FXML
    private void selecionarBoleto() {
        formaPagamentoSelecionada = "Boleto";

        opcaoBoleto.setStyle("-fx-underline: true; -fx-text-fill: #d35400;");
        opcaoCartao.setStyle("-fx-underline: false; -fx-text-fill: #ecf0f1;");

        containerBoleto.setVisible(true);
        containerBoleto.setManaged(true);
        containerCartao.setVisible(false);
        containerCartao.setManaged(false);
        carregarCartao.setVisible(false);
        erroFormaPagamento.setVisible(false);
    }

    /**
     * Seleciona a opção de pagamento por cartão, ajustando a interface.
     */
    @FXML
    private void selecionarCartao() {
        formaPagamentoSelecionada = "Cartão";

        opcaoCartao.setStyle("-fx-underline: true; -fx-text-fill: #d35400;");
        opcaoBoleto.setStyle("-fx-underline: false; -fx-text-fill: #ecf0f1;");

        containerCartao.setVisible(true);
        containerCartao.setManaged(true);
        containerBoleto.setVisible(false);
        containerBoleto.setManaged(false);
        carregarCartao.setVisible(true);
        erroFormaPagamento.setVisible(false);
    }

    /**
     * Realiza a confirmação da compra, incluindo validação de dados e tratamento da forma de pagamento.
     */
    @FXML
    private void confirmarCompra() {
        String numero = null;
        String cvv = null;
        String nome = null;
        Date data = null;
        Cartao cartao = null;
        if (formaPagamentoSelecionada == null) {
            erroFormaPagamento.setText("Selecione uma forma de pagamento.");
            return;
        }
        try {
            List<String> assentosSelecionados = obterAssentosSelecionados();
            if (assentosSelecionados.isEmpty()) {
                erroAssento.setText("Selecione pelo menos um assento.");
                return;
            }

            if (formaPagamentoSelecionada.equals("Cartão")) {
                boolean camposValidos = true;

                if (numeroCartao.getText().isEmpty() || !numeroCartao.getText().matches("\\d{16}")) {
                    numeroCartao.setStyle("-fx-border-color: red;");
                    erroNumeroCartao.setText("Número do cartão inválido. Deve conter 16 dígitos.");
                    camposValidos = false;
                } else {
                    numeroCartao.setStyle("");
                    erroNumeroCartao.setText("");
                }

                if (cvvCartao.getText().isEmpty() || !cvvCartao.getText().matches("\\d{3}")) {
                    cvvCartao.setStyle("-fx-border-color: red;");
                    erroCVVCartao.setText("CVV inválido. Deve conter 3 dígitos.");
                    camposValidos = false;
                } else {
                    cvvCartao.setStyle("");
                    erroCVVCartao.setText("");
                }

                if (nomeCartao.getText().trim().isEmpty()) {
                    nomeCartao.setStyle("-fx-border-color: red;");
                    erroNomeCartao.setText("O nome do cartão não pode estar vazio.");
                    camposValidos = false;
                } else {
                    nomeCartao.setStyle("");
                    erroNomeCartao.setText("");
                }

                if (validadeCartao.getText().isEmpty() || !validadeCartao.getText().matches("\\d{2}/\\d{2}")) {
                    validadeCartao.setStyle("-fx-border-color: red;");
                    erroValidadeCartao.setText("Formato inválido. Use MM/AA.");
                    camposValidos = false;
                } else {
                    try {
                        Date date = new Date();
                        if (obterDataValidade(validadeCartao.getText()).before(date)) {
                            validadeCartao.setStyle("-fx-border-color: red;");
                            erroValidadeCartao.setText("O cartão já expirou.");
                            camposValidos = false;
                        } else {
                            validadeCartao.setStyle("");
                            erroValidadeCartao.setText("");
                        }
                    } catch (Exception e) {
                        validadeCartao.setStyle("-fx-border-color: red;");
                        erroValidadeCartao.setText("Data de validade inválida.");
                        camposValidos = false;
                    }
                }

                if (!camposValidos) {
                    return;
                }

                List<Cartao> cartoesUsuario = usuarioAtual.getCartoes();
                for (Cartao encontrarCartao : cartoesUsuario) {
                    SimpleDateFormat formato = new SimpleDateFormat("MM/yy");
                    String dataValidade = formato.format(encontrarCartao.getDataValidade());

                    if (encontrarCartao.getNumero().equals(numeroCartao.getText()) &&
                            encontrarCartao.getNome().equals(nomeCartao.getText()) &&
                            dataValidade.equals(validadeCartao.getText())) {
                        if (!encontrarCartao.getCvv().equals(cvvCartao.getText())) {
                            erroCVVCartao.setText("CVV incorreto para o cartão informado.");
                            return;
                        }
                        cartao = encontrarCartao;
                        break;
                    }
                }
                data = obterDataValidade(validadeCartao.getText());
            }
            abrirTelaConfirmacao(formaPagamentoSelecionada, assentosSelecionados, numeroCartao.getText(),
                    cvvCartao.getText(), data, nomeCartao.getText(), cartao);

        } catch (Exception e) {
            mostrarAlerta("Erro", "Falha ao Confirmar Compra", "Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Retorna para a tela de eventos.
     */
    @FXML
    private void voltarParaEventos() {
        try {
            Stage stage = (Stage) labelEvento.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/aplication/view/fxml/TelaEventos.fxml"));
            Parent root = loader.load();

            TelaEventosController eventosController = loader.getController();

            usuarioAtual.setIngressos(ingressoController.getByUsuarioId(usuarioAtual.getId()));
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
     * Abre a tela de confirmação da compra.
     *
     * Esta tela exibe um resumo dos dados da compra, incluindo informações sobre o evento,
     * os assentos selecionados, o preço total e a forma de pagamento. O usuário pode
     * confirmar ou cancelar a compra.
     *
     * @param pagamento Forma de pagamento selecionada.
     * @param assentos Lista de assentos selecionados.
     * @param numero Número do cartão de crédito.
     * @param cvv CVV do cartão.
     * @param dataValidade Data de validade do cartão.
     * @param nome Nome do titular do cartão.
     * @param cartao Objeto Cartão correspondente.
     */
    private void abrirTelaConfirmacao(String pagamento, List<String> assentos, String numero, String cvv, Date dataValidade, String nome, Cartao cartao) {
        final Stage popUpStage = new Stage();

        String dadosCompra = String.format(
                "Evento: %s\nDescrição: %s\nData: %s\nAssentos: %s\nPreço Total: R$ %.2f\nForma de Pagamento: %s",
                eventoAtual.getNome(),
                eventoAtual.getDescricao(),
                eventoAtual.getData(),
                String.join(", ", obterAssentosSelecionados()),
                eventoAtual.getPreco() * obterAssentosSelecionados().size(),
                formaPagamentoSelecionada
        );

        VBox contentBox = new VBox();
        contentBox.setSpacing(15);
        contentBox.setPadding(new Insets(15));
        contentBox.setStyle("-fx-border-color: #d35400; -fx-border-width: 3; "
                + " -fx-background-color: #2c3e50;");

        Label labelTitulo = new Label("Confirmar compra?");
        labelTitulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");

        TextArea textAreaDadosCompra = new TextArea(dadosCompra);
        textAreaDadosCompra.setEditable(false);
        textAreaDadosCompra.setWrapText(true);
        textAreaDadosCompra.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-background-color: #34495e; -fx-text-fill: white;");

        HBox botoesBox = new HBox();
        botoesBox.setSpacing(10);
        botoesBox.setPadding(new Insets(10));
        botoesBox.setAlignment(Pos.CENTER);

        Button btnConfirmar = new Button("Confirmar");
        btnConfirmar.getStyleClass().add("botao");
        btnConfirmar.setOnAction(event -> {
            try {
                finalizarCompra(pagamento, assentos, numero, cvv, dataValidade, nome, cartao);
                popUpStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.getStyleClass().add("botao-primario");
        btnCancelar.setOnAction(event -> popUpStage.close());

        botoesBox.getChildren().addAll(btnConfirmar, btnCancelar);

        contentBox.getChildren().addAll(labelTitulo, textAreaDadosCompra, botoesBox);

        Scene scene = new Scene(contentBox);
        scene.getStylesheets().add(getClass().getResource("/aplication/view/estilos.css").toExternalForm());

        popUpStage.setTitle("Confirmação de Compra");
        popUpStage.setScene(scene);
        popUpStage.setWidth(400);
        popUpStage.setHeight(300);
        popUpStage.setResizable(false);
        popUpStage.initStyle(StageStyle.UNDECORATED);

        popUpStage.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                popUpStage.close();
            }
        });

        popUpStage.show();
    }

    /**
     * Finaliza a compra processando o pagamento e gerando os recibos.
     *
     * Dependendo da forma de pagamento, o metodo realiza o processamento do boleto
     * ou do pagamento via cartão de crédito, e em seguida gera os recibos correspondentes.
     * Após a compra, a lista de ingressos do usuário é atualizada.
     *
     * @param pagamento Forma de pagamento selecionada.
     * @param assentos Lista de assentos selecionados.
     * @param numero Número do cartão de crédito.
     * @param cvv CVV do cartão.
     * @param dataValidade Data de validade do cartão.
     * @param nome Nome do titular do cartão.
     * @param cartao Objeto Cartão correspondente.
     * @throws Exception Se ocorrer algum erro durante o processo de finalização da compra.
     */
    @FXML
    private void finalizarCompra(String pagamento, List<String> assentos, String numero, String cvv, Date dataValidade, String nome, Cartao cartao) throws Exception {
        if (formaPagamentoSelecionada.equals("Boleto")) {
            UUID idBoleto = UUID.randomUUID();
            List<UUID> recibos = compraController.criarRecibo(
                    usuarioAtual.getId(),
                    eventoAtual.getId(),
                    assentos,
                    eventoAtual.getPreco(),
                    idBoleto
            );
            mostrarAlerta("Aguardando pagamento do boleto", "Recibos Enviados para o e-mail", "\nID do Boleto: " + idBoleto);
        } else if (formaPagamentoSelecionada.equals("Cartão")) {
            if (cartao == null) {
                cartao = cartaoController.criarCartao(
                        usuarioAtual.getId(),
                        numero,
                        cvv,
                        dataValidade,
                        nome
                );
                usuarioAtual.addCartao(cartao);
                usuarioController.update(usuarioAtual, usuarioAtual.getLogin(), usuarioAtual.getSenha(),
                        usuarioAtual.getNome(), usuarioAtual.getEmail());
            }
            List<UUID> idsRecibos = compraController.criarRecibo(
                    usuarioAtual.getId(),
                    eventoAtual.getId(),
                    assentos,
                    cartao.getId(),
                    eventoAtual.getPreco()
            );
            mostrarAlerta("Compra Confirmada", "Recibos Gerados com Sucesso", "IDs dos recibos: " + idsRecibos);
        }
        usuarioAtual.setIngressos(ingressoController.getByUsuarioId(usuarioAtual.getId()));
        voltarParaEventos();
    }


    /**
     * Comportamento ao cancelar a tela de confirmação.
     */
    private void voltarParaTelaCompra() {
    }

    /**
     * Carrega os cartões cadastrados do usuário e atualiza os campos na interface.
     */
    @FXML
    private void carregarCartoes() {
        if (usuarioAtual.getCartoes().isEmpty()) {
            erroFormaPagamento.setVisible(true);
            erroFormaPagamento.setText("Você não tem cartões cadastrados!");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/aplication/view/fxml/TelaCartoes.fxml"));
            Parent root = loader.load();

            TelaCartoesController cartoesController = loader.getController();
            cartoesController.setUsuario(usuarioAtual);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/aplication/view/estilos.css").toExternalForm());

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Seus Cartões");
            stage.showAndWait();

            String numero = cartoesController.getNumeroCartaoSelecionado();
            String nome = cartoesController.getNomeCartaoSelecionado();
            String validade = cartoesController.getValidadeCartaoSelecionado();

            if (numero != null && nome != null && validade != null) {
                numeroCartao.setText(numero);
                nomeCartao.setText(nome);
                validadeCartao.setText(validade);
            }
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Falha ao carregar cartões", "Erro ao abrir a tela de cartões.");
        }
    }

    /**
     * Exibe um alerta para o usuário.
     *
     * @param titulo Título do alerta.
     * @param cabecalho Cabeçalho do alerta.
     * @param conteudo Conteúdo da mensagem do alerta.
     */
    private void mostrarAlerta(String titulo, String cabecalho, String conteudo) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecalho);
        alerta.setContentText(conteudo);
        alerta.showAndWait();
    }
}
