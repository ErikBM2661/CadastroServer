package cadastroclientv2;

import java.io.ObjectInputStream;
import java.util.List;
import javax.swing.*;
import model.Produto;

public class ThreadClient extends Thread {
    private ObjectInputStream entrada;
    private JTextArea textArea;

    public ThreadClient(ObjectInputStream entrada, JTextArea textArea) {
        this.entrada = entrada;
        this.textArea = textArea;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Object obj = entrada.readObject();
                if (obj instanceof String) {
                    String msg = (String) obj;
                    SwingUtilities.invokeLater(() -> textArea.append(msg + "\n"));
                } else if (obj instanceof List) {
                    List<?> lista = (List<?>) obj;
                    SwingUtilities.invokeLater(() -> {
                        for (Object o : lista) {
                            if (o instanceof Produto p) {
                                textArea.append("Produto: " + p.getNome() + " | Quantidade: " + p.getQuantidade() + "\n");
                            }
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
