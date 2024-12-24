package aplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe principal para a aplicação, responsável por iniciar a interface gráfica.
 * Carrega a tela inicial e aplica configurações visuais básicas.
 */
public class TelaInicial extends Application {

    /**
     * Metodo principal de inicialização da aplicação.
     * Configura e exibe a tela inicial com base no arquivo FXML e nos estilos definidos.
     *
     * @param primaryStage o palco principal da aplicação.
     * @throws IOException se ocorrer um erro ao carregar o arquivo FXML.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Carrega o arquivo FXML da tela inicial
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/fxml/TelaInicial.fxml"));
        BorderPane root = loader.load();

        // Configura a cena com o arquivo de estilos CSS
        Scene scene = new Scene(root, 650, 650);
        scene.getStylesheets().add(getClass().getResource("view/estilos.css").toExternalForm());

        // Configura o palco principal
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tela Inicial");

        // Define as dimensões mínimas para a janela
        primaryStage.setMinWidth(650);
        primaryStage.setMinHeight(650);

        // Exibe o palco principal
        primaryStage.show();
    }

    /**
     * Metodo principal da aplicação. Lança a interface gráfica.
     *
     * @param args argumentos de linha de comando.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
