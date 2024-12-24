package aplication.view.controller;

import aplication.controller.EventoController;
import aplication.model.Usuario;
import aplication.model.Evento;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * Controlador da tela de adição de eventos.
 * Responsável por gerenciar a interação entre o usuário e os dados relacionados à criação de eventos.
 */
public class TelaAdicionarEventoController {

    private final EventoController eventoController = new EventoController();
    private Usuario usuarioAtual;
    private Evento evento;

    @FXML
    private TextField campoNome;

    @FXML
    private DatePicker campoData;

    @FXML
    private TextField campoHorario;

    @FXML
    private TextField campoPreco;

    @FXML
    private TextField campoCapacidade;

    @FXML
    private TextArea campoDescricao;

    @FXML
    private Label erroData;

    @FXML
    private Label erroHorario;

    @FXML
    private Label erroCadastro;

    /**
     * Inicializa os campos da interface gráfica, configurando restrições e validações.
     */
    @FXML
    private void initialize() {
        configurarCampoHorario();
        configurarCampoData();
        configurarCampoPreco();
        configurarCampoCapacidade();
    }

    /**
     * Define o usuário atualmente autenticado.
     *
     * @param usuario Usuário autenticado.
     */
    public void setUsuarioAtual(Usuario usuario) {
        this.usuarioAtual = usuario;
    }

    /**
     * Configura o campo de horário com restrições de entrada e validação de formato.
     */
    private void configurarCampoHorario() {
        campoHorario.setTextFormatter(new TextFormatter<>(change -> {
            String novoTexto = change.getControlNewText();

            if (!novoTexto.matches("\\d{0,2}:?\\d{0,2}")) {
                return null;
            }

            if (change.isAdded()) {
                int length = novoTexto.length();
                if (length == 2 && !novoTexto.contains(":")) {
                    change.setText(novoTexto.charAt(1) + ":");
                    change.setCaretPosition(3);
                    change.setAnchor(3);
                }
            }

            return change;
        }));

        campoHorario.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                campoHorario.positionCaret(campoHorario.getText().length());
            }
        });

        campoHorario.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                String textoHorario = campoHorario.getText();

                if (textoHorario.matches("\\d{2}:\\d{2}")) {
                    try {
                        String horas = textoHorario.substring(0, 2);
                        String minutos = textoHorario.substring(3, 5);

                        int horasInt = Integer.parseInt(horas);
                        int minutosInt = Integer.parseInt(minutos);

                        if (horasInt < 0 || horasInt > 23 || minutosInt < 0 || minutosInt > 59) {
                            campoHorario.setStyle("-fx-border-color: red;");
                            erroHorario.setText("Horário inválido! (00:00 - 23:59)");
                        } else {
                            campoHorario.setStyle("");
                            erroHorario.setText("");
                        }
                    } catch (NumberFormatException e) {
                        campoHorario.setStyle("-fx-border-color: red;");
                        erroHorario.setText("Formato inválido! (Ex.: 14:30)");
                    }
                } else {
                    campoHorario.setStyle("-fx-border-color: red;");
                    erroHorario.setText("Formato inválido! (Ex.: 14:30)");
                }
            }
        });
    }

    /**
     * Configura o campo de data com restrições de entrada e validação de formato.
     */
    private void configurarCampoData() {
        campoData.getEditor().setTextFormatter(new TextFormatter<>(change -> {
            String novoTexto = change.getControlNewText();

            if (!novoTexto.matches("\\d{0,10}|\\d{2}/\\d{0,8}|\\d{2}/\\d{2}/\\d{0,4}")) {
                return null;
            }

            if (change.isAdded()) {
                int length = novoTexto.length();
                if (length == 2 || length == 5) {
                    change.setText(change.getText() + "/");
                    change.setCaretPosition(change.getCaretPosition() + 1);
                    change.setAnchor(change.getAnchor() + 1);
                }
            }

            return change;
        }));

        campoData.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                try {
                    String textoData = campoData.getEditor().getText();
                    LocalDate data = LocalDate.parse(textoData, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                    if (data.isBefore(LocalDate.now())) {
                        campoData.setStyle("-fx-border-color: red;");
                        erroData.setText("Data inválida! Deve ser uma data futura");
                    } else {
                        campoData.setStyle("");
                        erroData.setText("");
                    }
                } catch (Exception e) {
                    campoData.setStyle("-fx-border-color: red;");
                    erroData.setText("Formato inválido! (Ex.: 12/12/2024)");
                }
            }
        });
    }

    /**
     * Configura o campo de preço com restrições de entrada.
     */
    private void configurarCampoPreco() {
        campoPreco.setTextFormatter(new TextFormatter<>(change -> {
            String novoTexto = change.getControlNewText();

            if (!novoTexto.matches("\\d*\\.?\\d{0,2}")) {
                return null;
            }

            try {
                if (!novoTexto.isEmpty() && Double.parseDouble(novoTexto) < 0) {
                    return null;
                }
            } catch (NumberFormatException e) {
                return null;
            }

            return change;
        }));
    }

    /**
     * Configura o campo de capacidade com restrições de entrada.
     */
    private void configurarCampoCapacidade() {
        campoCapacidade.setTextFormatter(new TextFormatter<>(change -> {
            String novoTexto = change.getControlNewText();

            if (!novoTexto.matches("[0-9]*")) {
                return null;
            }

            if (novoTexto.isEmpty()) {
                return change;
            }

            if (Integer.parseInt(novoTexto) <= 0) {
                return null;
            }

            return change;
        }));
    }

    /**
     * Realiza o cadastro de um novo evento com base nos dados fornecidos.
     */
    @FXML
    private void cadastrarEvento() {
        if (campoNome.getText().isEmpty() || campoData.getEditor().getText().isEmpty() ||
                campoHorario.getText().isEmpty() || campoPreco.getText().isEmpty() ||
                campoCapacidade.getText().isEmpty() || campoDescricao.getText().isEmpty()) {

            erroCadastro.setText("Por favor, preencha todos os campos.");
            return;
        }else{
            erroCadastro.setText("");
        }

        String nome = campoNome.getText();
        LocalDate localDate = LocalDate.parse(campoData.getEditor().getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String horario = campoHorario.getText();
        String descricao = campoDescricao.getText();
        double preco = Double.parseDouble(campoPreco.getText());
        int capacidade = Integer.parseInt(campoCapacidade.getText());

        Calendar calendar = Calendar.getInstance();
        calendar.set(localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth());

        String[] horarioParts = horario.split(":");
        int hora = Integer.parseInt(horarioParts[0]);
        int minuto = Integer.parseInt(horarioParts[1]);
        calendar.set(Calendar.HOUR_OF_DAY, hora);
        calendar.set(Calendar.MINUTE, minuto);
        calendar.set(Calendar.SECOND, 0);

        Date data = calendar.getTime();
        Date dataAtual = new Date();
        if (data.before(dataAtual)) {
            campoData.setStyle("-fx-border-color: red;");
            erroData.setText("Data inválida! Deve ser uma data futura");
            return;
        } else {
            campoData.setStyle("");
            erroData.setText("");
        }

        evento = eventoController.cadastrar(usuarioAtual, nome, descricao, data, preco);

        for (int i = 0; i < capacidade; i++) {
            if (i < 9) {
                evento.adicionarAssento("A0" + (i + 1));
            } else {
                evento.adicionarAssento("A" + (i + 1));
            }
        }
        eventoController.update(evento);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText("Evento cadastrado com sucesso!");
        alert.setContentText("Nome: " + nome + "\nData: " + campoData.getEditor().getText() +
                "\nHorário: " + horario + "\nCapacidade: " + capacidade + "\nDescrição:" + descricao);
        alert.showAndWait();

        Stage stage = (Stage) campoNome.getScene().getWindow();
        stage.close();
    }

    /**
     * Fecha a janela atual e retorna para a tela anterior.
     */
    @FXML
    private void voltar() {
        Stage stage = (Stage) campoNome.getScene().getWindow();
        stage.close();
    }
}
