public class Token {

    // Tipo do token
    private TokenName type;

    private String text;

    private int linha;

    private int coluna;

    public Token() {

    }

    public Token(TokenName type, String text) {
        this.type = type;
        this.text = text;

    }

    public Token(TokenName type, String text, int linha, int coluna) {
        this.type = type;
        this.text = text;
        this.linha = linha;
        this.coluna = coluna;

    }

    public TokenName getType() {
        return type;
    }

    public void setType(TokenName type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public int getColuna() {
        return coluna;
    }

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }

}
