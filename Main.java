import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Calculadora cal = new Calculadora();
        String expressao;

        while (true){

            System.out.println("Digite uma expressão ou escreva desligar.");
            expressao = sc.nextLine();

            if(expressao.equals("desligar")) break;

            try {
                cal.avaliaExpressao(expressao);
            } catch (Exception e) {
                System.out.println(e.getClass() + ": " + e.getMessage());
            }

        }
    }
}
