public class Verificador {
    // Verifica se é um número.
    public boolean isNumber(char x) {
        return x >= '0' && x <= '9';
    }
    public boolean isDecimal(char x) {
        return x == '.';
    }
    
    // Verificar se é um caracter.
    public boolean isChar(char x) {
        return (x >= 'a' && x <= 'z') || (x >= 'A' && x <= 'Z');
    }

    // Verificar se é um operador matematico.
    public boolean isOperatorAritmetico(char x) {
        return (x == '*' || x == '/' || x == '%' || x == '+' || x == '-' || x == '/');
    }

    // Verificar se é uma igualdade.
    public boolean isOperatorAtribuicao(char x) {
        return x == '=';
    }

    // Verificar se é uma comparação.
    public boolean isOperatorLogico(char x) {
        return x == '|' || x == '&' || x == '!';
    }

    // Verificar se é maior ou menor que.
    public boolean isOperatorComparacao(char x) {
        return x == '>' || x == '<';
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
    public boolean isEndOfLine(char x) {
        return x == ';';
    }

    // Verificar se é caracter
    public boolean isOperatorCaractere(char x) {
        return x == '\'';
    }

    // Verificar se é texto
    public boolean isOperatorText(char x) {
        return x == '"';
    }

    // Verificar se é um caracter fora do padrão
    public boolean notToken(char x) {
        return x == '@' || x == '$' || x == '^' || x == '#';
    }

}
