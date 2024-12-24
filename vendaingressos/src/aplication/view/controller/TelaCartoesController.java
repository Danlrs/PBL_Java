package aplication.view.controller;

import aplication.model.Cartao;
import aplication.model.Usuario;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;

public class TelaCartoesController {

    @FXML
    private VBox cartoesContainer;

    private Usuario usuarioAtual;
    private Scene cenaAnterior;
    private String numeroCartaoSelecionado;
    private String nomeCartaoSelecionado;
    private String validadeCartaoSelecionado;

    /**
     * Define o usuário atual e carrega os cartões associados.
     *
     * @param usuarioAtual o usuário atual
     */
    public void setUsuario(Usuario usuarioAtual) {
        this.usuarioAtual = usuarioAtual;
        carregarCartoes();
    }

    /**
     * Retorna à cena anterior escondendo a janela atual.
     */
    @FXML
    private void voltar() {
        Stage stage = (Stage) cartoesContainer.getScene().getWindow();
        stage.hide();
    }

    /**
     * Carrega os cartões associados ao usuário e os exibe na interface.
     */
    private void carregarCartoes() {
        cartoesContainer.getChildren().clear();

        for (Cartao cartao : usuarioAtual.getCartoes()) {
            VBox cartaoBox = criarBoxCartao(cartao);
            cartoesContainer.getChildren().add(cartaoBox);
        }
    }

    /**
     * Cria um componente visual para exibir informações de um cartão.
     *
     * @param cartao o cartão a ser exibido
     * @return um VBox contendo os detalhes do cartão
     */
    private VBox criarBoxCartao(Cartao cartao) {
        VBox box = new VBox(5);
        box.getStyleClass().add("cartao-box");
        box.setPadding(new Insets(10));
        box.setStyle("-fx-background-color: #34495e; -fx-border-color: #ecf0f1; -fx-border-radius: 5;");

        String numeroCartao = cartao.getNumero();
        String digitos = numeroCartao.substring(numeroCartao.length() - 4);

        // Formatação da data de validade
        SimpleDateFormat formato = new SimpleDateFormat("MM/yy");
        String dataValidade = formato.format(cartao.getDataValidade());

        Label nomeNoCartao = new Label("Nome no Cartão: " + cartao.getNome());
        Label validade = new Label("Validade: " + dataValidade);
        Label ultimosDigitos = new Label("Número: **** **** **** " + digitos);

        nomeNoCartao.setStyle("-fx-font-weight: bold;");

        Button botaoUsar = new Button("Usar Cartão");
        botaoUsar.getStyleClass().add("botao");

        botaoUsar.setOnAction(e -> usarCartao(cartao));

        HBox linhaInferior = new HBox(10);
        linhaInferior.setStyle("-fx-alignment: center-right;");
        linhaInferior.getChildren().addAll(botaoUsar);

        box.getChildren().addAll(nomeNoCartao, validade, ultimosDigitos, linhaInferior);

        return box;
    }

    /**
     * Ação executada ao selecionar um cartão para uso. Armazena os dados do cartão e fecha a janela atual.
     *
     * @param cartao o cartão selecionado
     */
    @FXML
    private void usarCartao(Cartao cartao) {
        SimpleDateFormat formato = new SimpleDateFormat("MM/yy");
        String dataValidade = formato.format(cartao.getDataValidade());

        // Armazena os dados do cartão selecionado
        numeroCartaoSelecionado = cartao.getNumero();
        nomeCartaoSelecionado = cartao.getNome();
        validadeCartaoSelecionado = dataValidade;

        // Ação quando o cartão for selecionado: fecha o popup
        Stage stage = (Stage) cartoesContainer.getScene().getWindow();
        stage.close();
    }

    /**
     * Obtém o número do cartão selecionado.
     *
     * @return o número do cartão selecionado
     */
    public String getNumeroCartaoSelecionado() {
        return numeroCartaoSelecionado;
    }

    /**
     * Obtém o nome associado ao cartão selecionado.
     *
     * @return o nome do cartão selecionado
     */
    public String getNomeCartaoSelecionado() {
        return nomeCartaoSelecionado;
    }

    /**
     * Obtém a validade do cartão selecionado.
     *
     * @return a validade do cartão selecionado
     */
    public String getValidadeCartaoSelecionado() {
        return validadeCartaoSelecionado;
    }
}
