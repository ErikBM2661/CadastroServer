package CadastroServer;

import controller.MovimentoJpaController;
import controller.PessoaJpaController;
import controller.ProdutoJpaController;
import controller.UsuarioJpaController;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import model.Movimento;
import model.Pessoa;
import model.Produto;
import model.Usuario;

public class CadastroServer2 implements Runnable {

    private ProdutoJpaController ctrlProd;
    private UsuarioJpaController ctrlUsu;
    private MovimentoJpaController ctrlMov;
    private PessoaJpaController ctrlPessoa;
    private Socket socket;

    public CadastroServer2(ProdutoJpaController ctrlProd, UsuarioJpaController ctrlUsu,
                           MovimentoJpaController ctrlMov, PessoaJpaController ctrlPessoa,
                           Socket socket) {
        this.ctrlProd = ctrlProd;
        this.ctrlUsu = ctrlUsu;
        this.ctrlMov = ctrlMov;
        this.ctrlPessoa = ctrlPessoa;
        this.socket = socket;
    }

    @Override
    public void run() {
        try (ObjectOutputStream saida = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream())) {

            String login = (String) entrada.readObject();
            String senha = (String) entrada.readObject();

            Usuario usuario = ctrlUsu.findUsuario(login, senha);
            if (usuario == null) {
                saida.writeObject("LOGIN INVALIDO");
                return;
            } else {
                saida.writeObject("LOGIN OK");
            }

            while (true) {
                String comando = (String) entrada.readObject();

                if (comando.equalsIgnoreCase("L")) {
                    saida.writeObject(ctrlProd.findProdutoEntities());
                } else if (comando.equalsIgnoreCase("E") || comando.equalsIgnoreCase("S")) {
                    Movimento mov = new Movimento();
                    mov.setIDUsuario(usuario);
                    mov.setTipo(comando);

                    int idPessoa = entrada.readInt();
                    int idProduto = entrada.readInt();
                    int qtd = entrada.readInt();
                    double valor = entrada.readDouble();

                    Pessoa pessoa = ctrlPessoa.findPessoa(idPessoa);
                    Produto produto = ctrlProd.findProduto(idProduto);

                    mov.setIDPessoa(pessoa);
                    mov.setIDProduto(produto);
                    mov.setQuantidade(qtd);
                    mov.setValorUnitario(valor);

                    ctrlMov.create(mov);

                    // Atualiza a quantidade do produto
                    int novaQtd = produto.getQuantidade();
                    if (comando.equalsIgnoreCase("E")) {
                        novaQtd += qtd;
                    } else {
                        novaQtd -= qtd;
                    }
                    produto.setQuantidade(novaQtd);

                    ctrlProd.edit(produto);

                    saida.writeObject("Movimento registrado com sucesso.");
                } else if (comando.equalsIgnoreCase("X")) {
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
