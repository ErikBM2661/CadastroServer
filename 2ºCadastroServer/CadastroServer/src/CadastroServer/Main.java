package CadastroServer;

import controller.MovimentoJpaController;
import controller.PessoaJpaController;
import controller.ProdutoJpaController;
import controller.UsuarioJpaController;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Iniciando servidor...");

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("CadastroPU");

            ProdutoJpaController ctrlProd = new ProdutoJpaController(emf);
            UsuarioJpaController ctrlUsu = new UsuarioJpaController(emf);
            MovimentoJpaController ctrlMov = new MovimentoJpaController(emf);
            PessoaJpaController ctrlPessoa = new PessoaJpaController(emf);

            ServerSocket server = new ServerSocket(4321);
            System.out.println("Servidor escutando na porta 4321...");

            while (true) {
                try {
                    Socket s = server.accept();
                    System.out.println("Cliente conectado: " + s.getInetAddress().getHostAddress());

                    // Substituindo CadastroServer por CadastroServer2
                    Thread t = new Thread(new CadastroServer2(ctrlProd, ctrlUsu, ctrlMov, ctrlPessoa, s));
                    t.start();
                } catch (EOFException eof) {
                    System.out.println("Conexão encerrada pelo cliente.");
                } catch (IOException e) {
                    System.err.println("Erro ao aceitar conexão: " + e.getMessage());
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            System.err.println("Erro ao iniciar o servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
