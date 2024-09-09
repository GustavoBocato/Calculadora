import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Calculadora cal = new Calculadora();
        String expressao = sc.next();
        cal.avaliaExpressao(expressao);

    }
}
