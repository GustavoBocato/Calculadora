import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculadora {

    public String resolveParenteses(String expressao){

        Pattern pattern = Pattern.compile("\\([^()]*\\)");
        Matcher matcher = pattern.matcher(expressao);
        boolean temParenteses = matcher.find();

        if(!temParenteses) return String.valueOf(avaliaSemParenteses(expressao));

        while(temParenteses) {

            String parenteses = matcher.group();
            String conteudoParenteses = parenteses.substring(1, parenteses.length() - 1);
            String[] array = expressao.split("\\([^()]*\\)", 2);
            String subExpressaoEsquerda = array[0];
            String subExpressaoDireita = array[1];

            expressao = subExpressaoEsquerda + resolveParenteses(conteudoParenteses) + subExpressaoDireita;

            matcher = pattern.matcher(expressao);
            temParenteses = matcher.find();
        }

        return expressao;
    }

    public double avaliaSoma(String expressao){
        String[] somatorio = expressao.split("\\+");
        double Soma = 0;

        for (String subExpressao : somatorio){

            Soma += avaliaSubtracao(subExpressao);

        }

        return Soma;
    }

    public double avaliaSubtracao(String expressao){
        String[] subtratorio = expressao.split("-");

        if(subtratorio.length == 1) return avaliaMultiplicacao(subtratorio[0]);

        double resultado = avaliaMultiplicacao(subtratorio[0])*2;

        for (String subExpressao : subtratorio){

            resultado -= avaliaMultiplicacao(subExpressao);

        }

        return resultado;
    }

    public double avaliaMultiplicacao(String expressao){
        String[] produtorio = expressao.split("\\*");
        double resultado = 1;

        for(String subExpressao : produtorio){

            resultado *= avaliaDivisao(subExpressao);

        }

        return resultado;
    }

    public double avaliaDivisao(String expressao){
        String[] divisorio = expressao.split("/");

        if(divisorio.length == 1) return avaliaNumero(divisorio[0]);

        double resultado = avaliaNumero(divisorio[0])*avaliaNumero(divisorio[0]);

        for(String subExpressao : divisorio){

            double avaliacao = avaliaNumero(subExpressao);

            if(avaliacao == 0){

                throw new ArithmeticException("Divisão por zero.");

            }

            resultado /= avaliacao;

        }

        return resultado;
    }

    public double avaliaNumero(String expressao){
        return Double.parseDouble(expressao);
    }

    public double avaliaSemParenteses(String expressao){
        return avaliaSoma(expressao);
    }

    public void avaliaExpressao(String expressao){

        String expressaoSemParenteses = resolveParenteses(expressao);

        System.out.println(avaliaSemParenteses(expressaoSemParenteses));
    }
}
