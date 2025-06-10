package cadastroserver;

import controller.ProdutoJpaController;
import controller.UsuarioJpaController;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import model.Produto;
import model.Usuario;
import java.util.List;

public class CadastroServer extends Thread {

    private ProdutoJpaController ctrl;
    private UsuarioJpaController ctrlUsu;
    private Socket s1;

    public CadastroServer(ProdutoJpaController ctrl, UsuarioJpaController ctrlUsu, Socket s1) {
        this.ctrl = ctrl;
        this.ctrlUsu = ctrlUsu;
        this.s1 = s1;
    }

    @Override
    public void run() {
        try (
            ObjectOutputStream out = new ObjectOutputStream(s1.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(s1.getInputStream());
        ) {
            String login = (String) in.readObject();
            String senha = (String) in.readObject();

            Usuario u = ctrlUsu.findUsuario(login, senha);
            if (u == null) {
                out.writeObject("LOGIN INVALIDO");
                s1.close();
                return;
            } else {
                out.writeObject("LOGIN OK");
            }

            while (true) {
                String comando = (String) in.readObject();
                if ("L".equalsIgnoreCase(comando)) {
                    List<Produto> produtos = ctrl.findProdutoEntities();
                    out.writeObject(produtos);
                } else {
                    break;
                }
            }

            s1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
