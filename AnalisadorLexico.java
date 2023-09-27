
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AnalisadorLexico {

    // vetor grupo de caracteres
    private char[] group;

    // Serve para auxiliar a percorrer o vetor group
    private int next = 0;

    Token token = new Token();

    private String aux;

    Verificador verificador = new Verificador();

    private int linha = 1;
    private int coluna = 0;

    // String path =
    // "C:\\Users\\Matheus\\Desktop\\compiladores\\Compilador\\src\\teste.txt";

    public AnalisadorLexico(String file) {
        try {
            String text = new String(Files.readAllBytes(Paths.get(file)), StandardCharsets.UTF_8);
            group = text.toCharArray();
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public Token getToken() {

        int state = 0;

        char caracter;

        boolean invalideCaracter = false;

        aux = "";

        while (true) {

            if (next == group.length)
                return null;
            {

                caracter = group[next];
                next++;
                coluna++;
            }

            if (caracter == '\n') {
                linha++;
                coluna = 0;

            }

            switch (state) {
                case 0: {
                    if (verificador.isOperatorLineBreak(caracter))
                        state = 0;

                    else if (verificador.isChar(caracter)) { // caracter
                        state = 1;
                        if (!invalideCaracter)
                            token.setColuna(caracter);
                        mountWord(caracter);
                    } else if (verificador.isNumber(caracter)) { // numeros
                        state = 3;
                        if (!invalideCaracter)
                            token.setColuna(caracter);
                        mountWord(caracter);
                    } else if (verificador.isOperatorEquals(caracter)) { // Igualdade
                        state = 5;
                        if (!invalideCaracter)
                            token.setColuna(caracter);
                        mountWord(caracter);
                    } else if (verificador.isOperatorComparacaoMa(caracter)) { // maior e menor que
                        state = 8;
                        if (!invalideCaracter)
                            token.setColuna(caracter);
                        mountWord(caracter);
                    } else if (verificador.isOperatorComparacao(caracter)) { // Comparacao
                        state = 9;
                        if (!invalideCaracter)
                            token.setColuna(caracter);
                        mountWord(caracter);
                    } else if (verificador.isOperatorMost(caracter)) { // Soma
                        state = 10;
                        if (!invalideCaracter)
                            token.setColuna(caracter);
                        mountWord(caracter);
                    } else if (verificador.isOperatorMath(caracter)) { // Operadores matematicos
                        state = 13;
                        if (!invalideCaracter)
                            token.setColuna(caracter);
                        mountWord(caracter);
                    } else if (verificador.isOperatorSymb(caracter)) { // Simbolos
                        state = 14;
                        if (!invalideCaracter)
                            token.setColuna(caracter);
                        mountWord(caracter);
                    } else if (verificador.isOperatorFinalLine(caracter)) { // final de linha
                        state = 16;
                        if (!invalideCaracter)
                            token.setColuna(caracter);
                        mountWord(caracter);
                    } else if (verificador.isOperatorLess(caracter)) { // Subtracao
                        state = 17;
                        if (!invalideCaracter)
                            token.setColuna(caracter);
                        mountWord(caracter);
                    } else if (verificador.isOperatorDenial(caracter)) { // Negacao
                        state = 18;
                        if (!invalideCaracter)
                            token.setColuna(caracter);
                        mountWord(caracter);
                    } else { // caracter invalido
                        state = 0;
                        mountWord(caracter);
                        invalideCaracter = true;

                    }
                    break;
                }
                case 1: {
                    if (verificador.isChar(caracter) || verificador.isNumber(caracter)) {
                        state = 1;
                        mountWord(caracter);
                    } else if (verificador.notToken(caracter)) {
                        state = 1;
                        mountWord(caracter);
                        invalideCaracter = true;
                    } else {
                        if (invalideCaracter) {
                            back();
                            setToken(TokenName.TOKEN_MAL_FORMADO, aux, linha);
                            return token;
                        } else if (verificador.isReservedWord(aux)) {
                            back();
                            setToken(TokenName.PALAVRAS_RESERVADAS, aux, linha);
                            return token;
                        } else {
                            back();
                            setToken(TokenName.NOME_VARIAVEL, aux, linha);
                            return token;
                        }

                    }
                    break;
                }
                case 3: {
                    if (verificador.isNumber(caracter)) {
                        state = 3;
                        mountWord(caracter);
                    } else if (verificador.notToken(caracter)) {
                        state = 3;
                        mountWord(caracter);
                        invalideCaracter = true;
                    } else {
                        if (invalideCaracter) {
                            back();
                            setToken(TokenName.TOKEN_MAL_FORMADO, aux, linha);
                            return token;
                        } else {
                            back();
                            setToken(TokenName.NUM_INT, aux, linha);
                            return token;
                        }

                    }
                    break;

                }
                case 5: {
                    if (verificador.isOperatorEquals(caracter)) {
                        mountWord(caracter);

                        if (invalideCaracter) {
                            back();
                            setToken(TokenName.TOKEN_MAL_FORMADO, aux, linha);
                            return token;
                        }

                        setToken(TokenName.OPERADOR_COMPARACAO, aux, linha);
                        return token;
                    } else if (verificador.notToken(caracter)) {
                        state = 5;
                        mountWord(caracter);
                        invalideCaracter = true;
                    } else {
                        if (invalideCaracter) {
                            back();
                            setToken(TokenName.OPERADOR_ATRIBUICAO, aux, linha);
                            return token;
                        }
                    }
                    break;

                }
                case 8: {
                    if (verificador.isOperatorEquals(caracter)) {
                        setToken(TokenName.OPERADOR_COMPARACAO, aux, linha);
                        return token;
                    } else if (verificador.notToken(caracter)) {
                        mountWord(caracter);
                        setToken(TokenName.TOKEN_MAL_FORMADO, aux, linha);
                        return token;
                    } else {
                        if (invalideCaracter) {
                            back();
                            setToken(TokenName.TOKEN_MAL_FORMADO, aux, linha);
                            return token;
                        } else {
                            back();
                            setToken(TokenName.OPERADOR_COMPARACAO, aux, linha);
                            return token;
                        }
                    }

                }
                case 9: {
                    if (verificador.isOperatorComparacao(caracter) && aux.equalsIgnoreCase(String.valueOf(caracter))) {
                        mountWord(caracter);
                        setToken(TokenName.OPERADOR_COMPARACAO, aux, linha);
                        return token;
                    } else {
                        mountWord(caracter);
                        setToken(TokenName.TOKEN_MAL_FORMADO, aux, linha);
                        return token;
                    }
                }
                case 10: {
                    if (verificador.isOperatorMost(caracter) || verificador.isOperatorEquals(caracter)) {
                        mountWord(caracter);
                        setToken(TokenName.OPERADOR_ATRIBUICAO, aux, linha);
                        return token;
                    } else if (verificador.notToken(caracter)) {
                        mountWord(caracter);
                        setToken(TokenName.TOKEN_MAL_FORMADO, aux, linha);
                        return token;
                    } else {
                        if (invalideCaracter) {
                            back();
                            setToken(TokenName.TOKEN_MAL_FORMADO, aux, linha);
                            return token;
                        }
                    }

                }
                case 13: {
                    if (invalideCaracter) {
                        back();
                        setToken(TokenName.OPERADOR_ATRIBUICAO, aux, linha);
                        return token;
                    } else if (verificador.notToken(caracter)) {
                        mountWord(caracter);
                        setToken(TokenName.TOKEN_MAL_FORMADO, aux, linha);
                        return token;
                    } else {
                        if (invalideCaracter) {
                            back();
                            setToken(TokenName.TOKEN_MAL_FORMADO, aux, linha);
                            return token;
                        } else {
                            back();
                            setToken(TokenName.OPERADOR_MATEMATICO, aux, linha);
                            return token;

                        }
                    }
                }
                case 14: {
                    if (invalideCaracter) {
                        back();
                        setToken(TokenName.TOKEN_MAL_FORMADO, aux, linha);
                        return token;
                    } else {
                        back();
                        setToken(TokenName.SIMBOLO, aux, linha);
                        return token;
                    }

                }
                case 16: {
                    if (invalideCaracter) {
                        back();
                        setToken(TokenName.TOKEN_MAL_FORMADO, aux, linha);
                        return token;
                    } else {
                        back();
                        setToken(TokenName.FINAL_LINHA, aux, linha);
                        return token;
                    }

                }
                case 17: {
                    if (verificador.isOperatorEquals(caracter) || verificador.isOperatorLess(caracter)) {
                        mountWord(caracter);
                        setToken(TokenName.OPERADOR_ATRIBUICAO, aux, linha);
                        return token;
                    } else if (verificador.notToken(caracter)) {
                        mountWord(caracter);
                        setToken(TokenName.TOKEN_MAL_FORMADO, aux, linha);
                        return token;
                    } else {
                        if (invalideCaracter) {
                            back();
                            setToken(TokenName.TOKEN_MAL_FORMADO, aux, linha);
                            return token;
                        } else {
                            back();
                            setToken(TokenName.OPERADOR_MATEMATICO, aux, linha);
                            return token;
                        }
                    }

                }
                case 18: {
                    if (verificador.isOperatorEquals(caracter)) {
                        mountWord(caracter);
                        setToken(TokenName.OPERADOR_COMPARACAO, aux, linha);
                        return token;
                    } else if (verificador.notToken(caracter)) {
                        mountWord(caracter);
                        setToken(TokenName.TOKEN_MAL_FORMADO, aux, linha);
                        return token;
                    } else {
                        if (invalideCaracter) {
                            back();
                            setToken(TokenName.TOKEN_MAL_FORMADO, aux, linha);
                            return token;
                        } else {
                            back();
                            setToken(TokenName.NEGACAO, aux, linha);
                            return token;
                        }

                    }
                }
                case 22: {
                    if (caracter == '\n' || caracter == '\n') {
                        mountWord(caracter);
                        setToken(TokenName.TOKEN_MAL_FORMADO, aux, linha);
                        return token;
                    } else {
                        state = 22;
                        mountWord(caracter);
                        break;
                    }

                }
                case 25: {
                    state = 26;
                    mountWord(caracter);
                    break;
                }
                case 26: {
                    if (verificador.isOperatorCaractere(caracter)) {
                        mountWord(caracter);
                        setToken(TokenName.CARACTER, aux, linha);
                        return token;
                    } else {
                        mountWord(caracter);
                        setToken(TokenName.TOKEN_MAL_FORMADO, aux, linha);
                        return token;
                    }
                }
                case 28: {
                    if (verificador.isOperatorText(caracter)) {
                        state = 28;
                        
                    }
                }

            }

        }

    }

    //
    private void setToken(TokenName type, String word, int linha) {
        token.setType(type);
        token.setText(word);
        token.setLinha(linha);
    }

    private void back() {
        coluna--;
        next--;
    }

    private void mountWord(char caracter) {
        aux += String.valueOf(caracter);
    }

}
