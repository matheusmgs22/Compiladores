import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class AnalisadorLexico {
    ArrayList<String> num_int = new ArrayList<String>();
    ArrayList<String> num_decimal = new ArrayList<String>();
    ArrayList<String> identificador = new ArrayList<String>();
    ArrayList<String> texto = new ArrayList<String>();
    ArrayList<String> palavraReservada = new ArrayList<String>();
    ArrayList<String> operadores = new ArrayList<String>();
    ArrayList<String> simbolosEspeciais = new ArrayList<String>();

    public void run() {
        // Primeiro declara o path do arquivo:
        String path = "C:\\Users\\marce\\OneDrive\\Documentos\\VscodeProjetos\\AnalisadorLexico\\texto.txt";
        ArrayList<String> linhas = new ArrayList<>();

        // instacia a classe que verifica o tipo de token
        Verificador verificador = new Verificador();

        // leitura de arquivo
        FileReader fr = null;
        BufferedReader br = null;

        //  variáveis usadas
        int i = 0, ultimaPosicao = 0;
        char posicao, posicaoAnterior;
        boolean achou, id = false, decimal = false;
        String token = "";
        String [] palavraReservadas = {"int", "float", "char", "boolean", "void", "double", "if", "else", "for", 
        "while", "scanf", "println", "main", "abstract", "assert", "return", "break", "byte", "case", "catch",
        "class", "const", "continue", "default", "do", "enum", "extends", "final", "finally", "implements", "import",
        "interface", "long", "native", "new", "package", "private", "protected", "public", "short", "static", 
        "strictfp", "super", "switch", "synchronized", "throw", "throws", "transient", "try", "volatile", 
        "true", "false", "null" };
        Arrays.sort(palavraReservadas);

        // Abre bloco Try:
        try {
            // Instacia os dois objetos e dentro do FileReader botar o path do arquivo desejado:
            fr = new FileReader(path);
            br = new BufferedReader(fr);

            // isso lerá cada linha do arquivo enquanto ele não for nulo:
            String line = br.readLine();

            int index = 0, estado = 0;
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

            // loop principal para categorizar os tokens
            for(i = 0; i < linhas.size(); i++) {

                for(int z = 0; z < linhas.get(i).length(); z++) {
                    posicao = linhas.get(i).charAt(z);
                    ultimaPosicao = linhas.get(i).length() - 1;

                    // verifica se é inteiro
                    if(verificador.isNumber(posicao)) {
                        estado = 1;
                        token += posicao;
                        if(z > 0) {
                            posicaoAnterior = linhas.get(i).charAt(z - 1);
                            
                            // verifica se é decimal
                            if(verificador.isDecimal(posicaoAnterior)) {
                                estado = 2;
                                decimal = true;

                                // se for ultima posição, reinicia o token para não colar com a próxima linha
                                if(z == ultimaPosicao) {
                                    num_decimal.add(token);
                                    token = "";
                                    estado = 0;
                                    continue;
                                }

                                // verifica se é identificador
                            } else if(verificador.isChar(posicaoAnterior)) {
                                estado = 3;
                                id = true;

                                if(z == ultimaPosicao) {
                                    num_decimal.add(token);
                                    token = "";
                                    estado = 0;
                                    continue;
                                }
                            }
                        }
                        if(id) {
                            estado = 3; // evitar que mude o estado para inteiro quando é identificador
                        }
                        else if(decimal) {
                            estado = 2; // evitar que mude o estado para inteiro quando é decimal
                        }
                        if(z == ultimaPosicao) {
                            num_int.add(token);
                            token = "";
                            estado = 0;
                            continue;
                        }
                    }
                    // usado no if acima como parâmetro para decimal
                    else if(verificador.isDecimal(posicao)) {
                        token += posicao;

                        // verifica se é identificador também
                    } else if(verificador.isChar(posicao)) {

                        token += posicao;
                        estado = 3;

                            // verifica se é palavra reservada com busca binária para mais desempenho na busca
                            achou = buscaBinaria(palavraReservadas, token);
                        
                        if(z == ultimaPosicao) {
                           if(achou) {
                            palavraReservada.add(token);
                            token = "";
                            estado = 0;
                           }
                           else {
                            identificador.add(token);
                            token = "";
                            estado = 0;
                           }
                        }
                        else {
                            if(achou) {
                                palavraReservada.add(token);
                                estado = 0;
                                token = "";
                            }
                        }
                        continue;

                        // verifica se é operador
                    } else if (verificador.isOperatorLogico(posicao) || verificador.isOperatorAritmetico(posicao) ||
                        verificador.isOperatorAtribuicao(posicao) || verificador.isOperatorComparacao(posicao)) {
                        
                        operadores.add(String.valueOf(posicao));
                        token = "";

                        // verifica se é símbolo especial
                    } else if (verificador.isOperatorSymb(posicao)) {

                        simbolosEspeciais.add(String.valueOf(posicao));
                        token = "";

                    }
                    // ignora espaço em branco e a partir dele finaliza a concatenação do token para adicioná-lo
                    // a sua respectiva lista
                    else if(verificador.isOperatorLineBreak(posicao) || verificador.isEndOfLine(posicao)) {

                        // verifica se é o final da linha e onde adicionar o respectivo token pelo seu estado 
                        if(verificador.isEndOfLine(posicao)) {

                            simbolosEspeciais.add(String.valueOf(posicao));
                            
                            if(z > 0) {
                                if(z == linhas.get(i).length() - 1) {

                                    if(estado == 1) {
                                        num_int.add(token);
                                        token = "";
                                        estado = 0;
                                    }
                                    else if(estado == 2) {
                                        num_decimal.add(token);
                                        decimal = false;
                                        token = "";
                                        estado = 0;
                                    }
                                    else if(estado == 3) {
                                        identificador.add(token);
                                        id = false;
                                        token = "";
                                        estado = 0;
                                    }
                                    break;
                                }
                            }
                        }
                        if(estado == 1) {
                            num_int.add(token);
                        }
                        else if(estado == 2) {
                            num_decimal.add(token);
                            decimal = false;
                        }
                        else if(estado == 3) {
                            identificador.add(token);
                            id = false;
                        }
                        estado = 0;
                        token = "";
                        continue;
                    }
                }
            }
        }
        catch (IOException e) {
            System.out.print("Não foi possível ler o arquivo:\n" + e.getMessage());
            // caso arquivo não possa ser lido
        }
        finally {
            type(); // chama função que exibe as listas dos tipos de token (palavra reservada, id, inteiro.. etc)
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
    // usado para procurar e comparar o token com as palavras reservadas da linguagem no array 
    public static boolean buscaBinaria(String[] vetor, String elemento) {
        int inicio = 0;
        int fim = vetor.length - 1;

        while (inicio <= fim) {
            int meio = (inicio + fim) / 2;
            int comparacao = elemento.compareToIgnoreCase(vetor[meio]);

            if (comparacao == 0) {
                return true; // Elemento encontrado
            } else if (comparacao < 0) {
                fim = meio - 1; // Procurar na metade inferior
            } else {
                inicio = meio + 1; // Procurar na metade superior
            }
        }

        return false; // Elemento não encontrado
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