package aplication.view;

import javafx.stage.Stage;

/**
 * Classe responsável por salvar e aplicar o estado de uma janela.
 * Armazena dimensões e posição da janela para manter consistência ao longo do uso.
 */
public class WindowState {
    /** Largura da janela. */
    private static double width = 650;

    /** Altura da janela. */
    private static double height = 650;

    /** Posição horizontal da janela. Valor negativo indica posição não definida. */
    private static double x = -1;

    /** Posição vertical da janela. Valor negativo indica posição não definida. */
    private static double y = -1;

    /**
     * Salva o estado atual da janela, incluindo dimensões e posição.
     *
     * @param stage a instância da janela cuja posição e dimensões serão salvas.
     */
    public static void saveState(Stage stage) {
        width = stage.getWidth();
        height = stage.getHeight();
        x = stage.getX();
        y = stage.getY();
    }

    /**
     * Aplica o estado salvo à janela, ajustando dimensões e posição.
     * Caso a posição não esteja definida, centraliza a janela na tela.
     *
     * @param stage a instância da janela que terá o estado aplicado.
     */
    public static void applyState(Stage stage) {
        stage.setWidth(width);
        stage.setHeight(height);

        if (x >= 0 && y >= 0) {
            stage.setX(x);
            stage.setY(y);
        } else {
            stage.centerOnScreen();
        }
    }
}
