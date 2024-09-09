public class Calculadora {
    public String resolveParenteses(String expressao){

        return "";
    }

    public double avaliaNumero(String expressao){
        return Double.parseDouble(expressao);
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

    public void avaliaExpressao(String expressao){
        double avaliacao;

        try{

            avaliacao = avaliaSoma(expressao);

        }catch (Exception ex){

            System.out.println(ex.getMessage());
            return;

        }

        System.out.println(avaliacao);
    }
}
