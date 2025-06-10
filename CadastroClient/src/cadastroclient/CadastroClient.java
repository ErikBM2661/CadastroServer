package cadastroclient;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import model.Produto;

public class CadastroClient {

    public static void main(String[] args) {
        try (
            Socket socket = new Socket("localhost", 4321);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ) {
            out.writeObject("op1");
            out.writeObject("op1");

            String respostaLogin = (String) in.readObject();
            System.out.println("Resposta do servidor: " + respostaLogin);

            if ("LOGIN OK".equals(respostaLogin)) {
                out.writeObject("L");

                List<Produto> produtos = (List<Produto>) in.readObject();
                System.out.println("Produtos recebidos:");
                for (Produto p : produtos) {
                    System.out.println("- " + p.getNome()); // ajusta de acordo com sua entidade
                }
            }

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
