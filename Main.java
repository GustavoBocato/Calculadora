import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Calculadora cal = new Calculadora();

        while (true){
            System.out.println("Digite uma expressão ou escreva desligar.");
            String expressao = sc.nextLine();

            if(expressao.equalsIgnoreCase("desligar")) break;

            try {
                cal.avaliaExpressao(expressao);
            } catch (Exception e) {
                System.out.println(e.getClass() + ": " + e.getMessage());
            }
        }
    }
}
