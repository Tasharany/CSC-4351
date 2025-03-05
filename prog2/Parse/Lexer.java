package Parse;
import java_cup.runtime.Symbol;
import java.io.IOException;

public interface Lexer {
    Symbol nextToken() throws IOException;
}

