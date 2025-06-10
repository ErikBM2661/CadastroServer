package cadastroclientv2;

import java.io.*;
import java.net.Socket;

public class CadastroClientV2 {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 4321);
            ObjectOutputStream saida = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());

            // Login
            saida.writeObject("op1");
            saida.writeObject("op1");

            String resposta = (String) entrada.readObject();
            if (!resposta.equals("LOGIN OK")) {
                System.out.println("Login falhou!");
                return;
            }

            // Janela de saída e thread de leitura
            SaidaFrame frame = new SaidaFrame();
            frame.setVisible(true);
            new ThreadClient(entrada, frame.texto).start();

            // Teclado
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                System.out.println("Comando (L - Listar, E - Entrada, S - Saída, X - Sair): ");
                String comando = teclado.readLine();
                saida.writeObject(comando);

                if (comando.equalsIgnoreCase("X")) break;

                if (comando.equalsIgnoreCase("E") || comando.equalsIgnoreCase("S")) {
                    System.out.print("Id da Pessoa: ");
                    int idPessoa = Integer.parseInt(teclado.readLine());
                    saida.writeInt(idPessoa);

                    System.out.print("Id do Produto: ");
                    int idProduto = Integer.parseInt(teclado.readLine());
                    saida.writeInt(idProduto);

                    System.out.print("Quantidade: ");
                    int qtd = Integer.parseInt(teclado.readLine());
                    saida.writeInt(qtd);

                    System.out.print("Valor unitário: ");
                    double valor = Double.parseDouble(teclado.readLine());
                    saida.writeDouble(valor);
                }
            }

            socket.close();
            System.out.println("Cliente finalizado.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
