import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class analisador {
    ArrayList<String> num_int = new ArrayList<String>();
    ArrayList<String> num_decimal = new ArrayList<String>();
    ArrayList<String> identificador = new ArrayList<String>();
    ArrayList<String> texto = new ArrayList<String>();
    ArrayList<String> palavraReservada = new ArrayList<String>();
    ArrayList<String> operadores = new ArrayList<String>();
    ArrayList<String> simbolosEspeciais = new ArrayList<String>();

    public void run() {
        // Primeiro declara o path do arquivo:
        String path = "C:\\Users\\marce\\OneDrive\\Documentos\\VscodeProjetos\\Faculdade\\ComPOO\\AnalisadorLexico\\texto.txt";
        ArrayList<String> linhas = new ArrayList<>();
        FileReader fr = null;
        BufferedReader br = null;

        int i = 0;
        char posicao;
        String posicao2;
        String token = "";

        // Abre bloco Try:
        try {
            // Instacia os dois objetos e dentro do FileReader botar o path do arquivo desejado:
            fr = new FileReader(path);
            br = new BufferedReader(fr);

            // isso lerá cada linha do arquivo enquanto ele não for nulo:
            String line = br.readLine();

            int index = 0;
            String linhaSemComentario;

            // Loop para retirar comentários das linhas.
            while(line != null) {
                if(line.contains("//")) {
                    index = line.indexOf('/');
                    linhaSemComentario = line.substring(0, index - 1);
                    linhas.add(linhaSemComentario);
                }
                else {
                    linhas.add(line);
                }
                line = br.readLine();
            }
            //for (String linha : linhas) {
            //    System.out.println(linha);
            //}

            ArrayList<String> tokensLinha = new ArrayList<String>();
            // String posicaoLista = linhas.get(i).length(); 
            for(i = 0; i < linhas.size(); i++) {

                for(int z = 0; z < linhas.get(i).length(); z++) {
                    posicao = linhas.get(i).charAt(z);

                    if(Character.isSpaceChar(posicao)) {
                        tokensLinha.add(token);
                        token = "";
                        continue;
                    }
                    else if(posicao == ';') {
                        tokensLinha.add(token);
                        tokensLinha.add(String.valueOf(posicao));
                        token = "";
                        break;
                    }
                    else {
                        token += posicao;
                    }
                }
            }
            /*for(i = 0; i < tokensLinha.size(); i++) {
                posicao2 = tokensLinha.get(i);

                if(Character.isDigit(posicao2)) {
                    num_int.add(token);
                } 
                else if(Character.isLetter(posicao2)) {
                    identificador.add(token);
                }
                else if(Character.isSpaceChar(posicao2)) {
                    continue;
                }
                else if(posicao2 == '/') {
                    break;
                }
                else {
                    operadores.add(token);
                }
                }
            }*/

            // USO DE TESTE PARA VER SE CONSEGUIR SEPARAR CADA CARACTER EM UMA LISTA:
            System.out.println("Lista de tokens de todas as linhas: ");
            for (String tokens : tokensLinha) {
                System.out.println(tokens);
            }
        }

        catch (IOException e) {
            System.out.print("Não foi possível ler o arquivo:\n" + e.getMessage());
        }
        finally {
            type();
            try {
               if(br != null) {
                br.close();
               }
               if(fr != null) {
                fr.close();
               }
            }
            catch(IOException e) {
                System.out.print("Exceção ao fechar arquivo: " + e.getMessage());
                // getMessage() para mostrar de forma descrita qual é a exceção em questão.
            }
        }
    }
    public void type() {
        System.out.println("Inteiros: " + num_int.toString());
        System.out.println("Decimais: " + num_decimal.toString());
        System.out.println("Identificadores: " + identificador.toString());
        System.out.println("Texto: " + texto.toString());
        System.out.println("Palavras reservadas: " + palavraReservada.toString());
        System.out.println("Operadores: " + operadores.toString());
        System.out.println("Símbolos especiais: " + simbolosEspeciais.toString());
    }
}