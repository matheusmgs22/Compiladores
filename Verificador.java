
public class Verificador {

    // Verifica se é um número.
    public boolean isNumber(char x) {
        return x >= '0' && x <= '9';
    }

    // Verificar se é um caracter.
    public boolean isChar(char x) {
        return (x >= 'a' && x <= 'z') || (x >= 'A' && x <= 'Z');
    }

    // Verificar se é um operador matematico.
    public boolean isOperatorMath(char x) {
        return (x == '*' || x == '/' || x == '%');
    }

    // Verificar se é uma soma.
    public boolean isOperatorMost(char x) {
        return x == '+';
    }

    // Verificar se é uma subtração.
    public boolean isOperatorLess(char x) {
        return x == '-';
    }

    // Verificar se é uma igualdade.
    public boolean isOperatorEquals(char x) {
        return x == '=';
    }

    // Verificar se é uma comparação.
    public boolean isOperatorComparacao(char x) {
        return x == '|' || x == '&';
    }

    // Verificar se é maior ou menor que.
    public boolean isOperatorComparacaoMa(char x) {
        return x == '>' || x == '<';
    }

    // Verificar se é maior ou menor que.
    public boolean isOperatorFinalLine(char x) {
        return x == ';';
    }

    // Verificar se é negação.
    public boolean isOperatorDenial(char x) {
        return x == '!';
    }

    // Verificar se é negação.
    public boolean isOperatorBar(char x) {
        return x == '/';

    }

    // Verificar quebra de linha ou espaco
    public boolean isOperatorLineBreak(char x) {
        return x == ' ' || x == '\n' || x == '\r';

    }

    // Verificar simbolos Especiais
    public boolean isOperatorSymb(char x) {
        return x == '(' || x == ')' || x == '[' || x == ']' || x == '[' ||
                x == ']' || x == '{' || x == '}' || x == ',' || x == '.' ||
                x == ':';
    }

    // Verificar se é caracter
    public boolean isOperatorCaractere(char x) {
        return x == '\'';
    }

    // Verificar se é texto
    public boolean isOperatorText(char x) {
        return x == '"';
    }

    // Verificar se é uma palavra reservada.
    public boolean isReservedWord(String x) {
        try {
            ReservedWord.valueOf(x.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }

    }

    // Verificar se é um caracter fora do padrão
    public boolean notToken(char x) {
        return x == '@' || x == '$' || x == '^' || x == '#';
    }

}
