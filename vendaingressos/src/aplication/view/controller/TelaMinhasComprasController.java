package aplication.view.controller;

import aplication.controller.EventoController;
import aplication.controller.UsuarioController;
import aplication.controller.IngressoController;
import aplication.controller.AvaliacaoController;
import aplication.model.Avaliacao;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import aplication.model.Ingresso;
import aplication.model.Usuario;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para a tela "Minhas Compras".
 * Gerencia as interações e exibições relacionadas às compras realizadas pelo usuário.
 */
public class TelaMinhasComprasController {

    private EventoController eventoController = new EventoController();
    private UsuarioController usuarioController = new UsuarioController();
    private IngressoController ingressoController = new IngressoController();
    private AvaliacaoController avaliacaoController = new AvaliacaoController();

    private int rating = 0;

    @FXML
    private VBox comprasContainer;

    private Usuario usuarioAtual;
    private Scene cenaAnterior;

    /**
     * Define o usuário atual para o controlador.
     *
     * @param usuarioAtual o usuário atualmente logado.
     */
    public void setUsuarioAtual(Usuario usuarioAtual) {
        this.usuarioAtual = usuarioAtual;
        carregarCompras();
    }

    /**
     * Define a cena anterior para permitir navegação de volta.
     *
     * @param cenaAnterior a cena que será exibida ao voltar.
     */
    public void setCenaAnterior(Scene cenaAnterior) {
        this.cenaAnterior = cenaAnterior;
    }

    /**
     * Retorna para a cena anterior.
     */
    @FXML
    private void voltar() {
        Stage stage = (Stage) comprasContainer.getScene().getWindow();
        stage.setScene(cenaAnterior);
    }

    /**
     * Carrega as compras realizadas pelo usuário atual.
     */
    private void carregarCompras() {
        comprasContainer.getChildren().clear();

        for (Ingresso ingresso : usuarioAtual.getIngressos()) {
            VBox ingressoBox = criarBoxIngresso(ingresso);
            comprasContainer.getChildren().add(ingressoBox);
        }
    }

    /**
     * Cria um componente visual para exibir informações de um ingresso.
     *
     * @param ingresso o ingresso a ser exibido.
     * @return um VBox contendo os detalhes do ingresso.
     */
    private VBox criarBoxIngresso(Ingresso ingresso) {
        VBox box = new VBox(5);
        box.getStyleClass().add("ingresso-box");
        box.setPadding(new Insets(10));
        box.setStyle("-fx-background-color: #34495e; -fx-border-color: #ecf0f1; -fx-border-radius: 5;");
        String reciboIngresso = null;

        for (String reciboTemp : usuarioAtual.getRecibos()) {
            if (reciboTemp.contains(ingresso.getId().toString())) {
                reciboIngresso = reciboTemp;
            }
        }

        Label nomeEvento = new Label("Evento: " + eventoController.getById(ingresso.getEventoId()).getNome());
        Label ingressoAtivo = new Label("Status do ingresso: " + (ingresso.isAtivo() ? "Ativo" : "Inativo"));
        Label assento = new Label("Assento: " + ingresso.getAssento());
        Label dataEvento = new Label("Data: " + eventoController.getById(ingresso.getEventoId()).getData().toString());
        Label preco = new Label("Preço: R$ " + ingresso.getPreco());
        Label recibo = new Label("Recibo: \n" + reciboIngresso);

        nomeEvento.setStyle("-fx-font-weight: bold;");

        VBox dadosIngressoBox = new VBox(10);
        dadosIngressoBox.getChildren().addAll(nomeEvento, ingressoAtivo, assento, dataEvento, preco, recibo);

        Node elementoLateral;
        if (!eventoController.getById(ingresso.getEventoId()).isAtivo()) {
            elementoLateral = criarAvaliacaoBox(ingresso);
        } else if (!ingresso.isAtivo() && eventoController.getById(ingresso.getEventoId()).isAtivo()) {
            Button botaoCancelar = new Button("Cancelar Compra");
            botaoCancelar.setDisable(true);
            botaoCancelar.getStyleClass().add("botao-desativado");
            elementoLateral = botaoCancelar;
        } else {
            Button botaoCancelar = new Button("Cancelar Compra");
            botaoCancelar.setOnAction(e -> exibirConfirmacaoCancelar(ingresso, botaoCancelar));
            botaoCancelar.getStyleClass().add("botao");
            elementoLateral = botaoCancelar;
        }

        HBox layoutPrincipal = new HBox(20);
        layoutPrincipal.setAlignment(Pos.CENTER_LEFT);
        layoutPrincipal.getChildren().addAll(dadosIngressoBox, elementoLateral);

        box.getChildren().add(layoutPrincipal);
        return box;
    }

    /**
     * Cria um componente visual para avaliação de um evento associado a um ingresso.
     *
     * @param ingresso o ingresso relacionado ao evento a ser avaliado.
     * @return um HBox contendo os elementos de avaliação.
     */
    private HBox criarAvaliacaoBox(Ingresso ingresso) {
        Label labelAvaliacao = new Label("Sua avaliação:");
        HBox estrelasBox = new HBox(5);
        estrelasBox.setStyle("-fx-alignment: center-left;");

        List<Label> estrelas = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Label estrela = new Label("★");
            estrela.getStyleClass().add("estrela");
            final int nota = i;
            estrela.setOnMouseClicked(e -> atualizarEstrelas(estrelas, nota));
            estrelas.add(estrela);
            estrelasBox.getChildren().add(estrela);
        }

        TextArea comentario = new TextArea();
        comentario.setPromptText("Deixe seu comentário...");
        comentario.setWrapText(true);
        comentario.setPrefWidth(200);
        comentario.setPrefHeight(120);
        comentario.setMaxWidth(Double.MAX_VALUE);
        comentario.setMaxHeight(Double.MAX_VALUE);
        comentario.getStyleClass().add("campo-texto");

        Avaliacao avaliacaoExistente = avaliacaoController.getAvaliacaoEventoUsuario(
                ingresso.getEventoId(), usuarioAtual.getId()
        );

        if (avaliacaoExistente != null) {
            rating = avaliacaoExistente.getRating();
            atualizarEstrelas(estrelas, avaliacaoExistente.getRating());
            comentario.setText(avaliacaoExistente.getComentarios());
        }

        Button botaoConfirmar = new Button("Confirmar");
        botaoConfirmar.getStyleClass().add("botao");
        botaoConfirmar.setOnAction(e -> {
            int estrelasContadas = (int) estrelas.stream()
                    .filter(estrela -> estrela.getStyle().contains("orange"))
                    .count();
            salvarAvaliacao(ingresso, estrelasContadas, comentario.getText(), avaliacaoExistente);
        });

        VBox avaliacaoContainer = new VBox(8, labelAvaliacao, estrelasBox, comentario, botaoConfirmar);
        avaliacaoContainer.setPadding(new Insets(8));
        avaliacaoContainer.setStyle("-fx-background-color: #2c3e50; -fx-border-color: #ecf0f1; -fx-border-radius: 5;");
        avaliacaoContainer.setPrefWidth(250);

        HBox avaliacaoBox = new HBox(10, avaliacaoContainer);
        avaliacaoBox.setStyle("-fx-alignment: center-right;");
        return avaliacaoBox;
    }

    /**
     * Salva a avaliação de um ingresso.
     *
     * @param ingresso o ingresso avaliado.
     * @param estrelas a quantidade de estrelas atribuídas.
     * @param comentario o comentário associado à avaliação.
     * @param avaliacao a avaliação existente, se houver.
     */
    private void salvarAvaliacao(Ingresso ingresso, int estrelas, String comentario, Avaliacao avaliacao) {
        if (avaliacao == null) {
            avaliacao = avaliacaoController.criarAvaliacao(comentario, estrelas, usuarioAtual.getId(),
                    ingressoController.getEventoById(ingresso.getId()).getId());
        } else {
            avaliacaoController.update(avaliacao.getId(), comentario, estrelas);
        }
        System.out.println("Avaliando ingresso " + ingresso.getId() + ": " + estrelas + " estrelas. Comentário: " + comentario);
    }

    /**
     * Atualiza as estrelas selecionadas para a avaliação.
     *
     * @param estrelas a lista de labels representando as estrelas.
     * @param nota a nota selecionada.
     */
    private void atualizarEstrelas(List<Label> estrelas, int nota) {
        rating = nota;
        for (int i = 0; i < estrelas.size(); i++) {
            Label estrela = estrelas.get(i);
            if (i < nota) {
                estrela.setStyle("-fx-text-fill: orange;");
            } else {
                estrela.setStyle("-fx-text-fill: white;");
            }
        }
    }

    /**
     * Exibe uma caixa de diálogo de confirmação para cancelar a compra de um ingresso.
     *
     * @param ingresso o ingresso a ser cancelado.
     * @param botaoCancelar o botão associado ao cancelamento.
     */
    private void exibirConfirmacaoCancelar(Ingresso ingresso, Button botaoCancelar) {
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmação");
        confirmacao.setHeaderText("Deseja realmente cancelar este ingresso?");
        confirmacao.setContentText("Esta ação é irreversível.");

        confirmacao.showAndWait().ifPresent(resposta -> {
            if (resposta == ButtonType.OK) {
                try {
                    cancelarCompra(ingresso, botaoCancelar);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    /**
     * Cancela a compra de um ingresso.
     *
     * @param ingresso o ingresso a ser cancelado.
     * @param botaoCancelar o botão associado ao cancelamento.
     * @throws Exception se houver um erro ao cancelar a compra.
     */
    private void cancelarCompra(Ingresso ingresso, Button botaoCancelar) throws Exception {
        // Desativa o ingresso e garante a atualização do evento
        boolean canceladoComSucesso = ingressoController.desativarIngresso(ingresso.getId());

        if (canceladoComSucesso) {
            // Desativa o botão de cancelamento após a confirmação
            botaoCancelar.setDisable(true);
            botaoCancelar.getStyleClass().add("botao-desativado");

            // Recarrega os ingressos do usuário a partir do metodo getByUsuarioId
            usuarioAtual.setIngressos(ingressoController.getByUsuarioId(usuarioAtual.getId()));

            // Atualiza a lista de compras para refletir a mudança
            carregarCompras();

            System.out.println("Compra do ingresso para o evento " + eventoController.getById(ingresso.getEventoId()).getNome() + " cancelada.");
        } else {
            // Se o ingresso não puder ser cancelado (evento inativo), exibe uma mensagem
            Alert erro = new Alert(Alert.AlertType.ERROR);
            erro.setTitle("Erro");
            erro.setHeaderText("Erro ao cancelar ingresso");
            erro.setContentText("O ingresso não pode ser cancelado, pois o evento já está inativo.");
            erro.showAndWait();
        }
    }


}
