import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculadora {

    List<String> operadoresBooleanos = List.of("==", "!=", ">=", "<=", "<", ">");
    String operadorDeAtribuicao = ":=";
    private HashMap<String, String> variaveis = new HashMap<String, String>();

    public String resolveParenteses(String expressao) {
        Pattern pattern = Pattern.compile("\\([^()]*\\)");
        Matcher matcher = pattern.matcher(expressao);
        boolean temParenteses = matcher.find();

        if (!temParenteses) return String.valueOf(avaliaSemParenteses(expressao));

        while (temParenteses) {

            String parenteses = matcher.group();
            String conteudoParenteses = parenteses.substring(1, parenteses.length() - 1);
            String[] array = expressao.split("\\([^()]*\\)", 2);
            String subExpressaoEsquerda = array[0];
            String subExpressaoDireita = array[1];

            expressao = subExpressaoEsquerda + " " + resolveParenteses(conteudoParenteses) + " " + subExpressaoDireita;

            matcher = pattern.matcher(expressao);
            temParenteses = matcher.find();
        }

        return expressao;
    }

    public double avaliaSoma(String expressao) {
        expressao = expressao.trim();

        String[] somatorio = expressao.split("\\+");
        double Soma = 0;

        for (String subExpressao : somatorio) {

            Soma += avaliaSubtracao(subExpressao);

        }

        return Soma;
    }

    public double avaliaSubtracao(String expressao) {
        expressao = expressao.trim();
        String[] subtratorio = expressao.split("-");

        if (subtratorio.length == 1) return avaliaMultiplicacao(subtratorio[0]);

        if(subtratorio[0].isEmpty()) subtratorio[0] = "0";

        double resultado = avaliaMultiplicacao(subtratorio[0]) * 2;
        int sinal = 1;

        for (String subExpressao : subtratorio) {

            if(subExpressao.trim().isEmpty()){
                sinal *= -1;
            }else{
                resultado -= avaliaMultiplicacao(subExpressao)*sinal;
                sinal = 1;
            }
        }

        return resultado;
    }

    public double avaliaMultiplicacao(String expressao) {
        String[] produtorio = expressao.split("\\*");
        double resultado = 1;

        for (String subExpressao : produtorio) {

            resultado *= avaliaDivisao(subExpressao);

        }

        return resultado;
    }

    public double avaliaDivisao(String expressao) {
        String[] divisorio = expressao.split("/");

        if (divisorio.length == 1) return avaliaNumero(divisorio[0]);

        double resultado = avaliaNumero(divisorio[0]) * avaliaNumero(divisorio[0]);

        for (String subExpressao : divisorio) {

            double avaliacao = avaliaNumero(subExpressao);

            if (avaliacao == 0) {

                throw new ArithmeticException("Divisão por zero.");

            }

            resultado /= avaliacao;

        }

        return resultado;
    }

    public double avaliaNumero(String expressao) {
        expressao = expressao.trim();

        if(variaveis.containsKey(expressao)){

            expressao = variaveis.get(expressao);

        }

        return Double.parseDouble(expressao);
    }

    public double avaliaSemParenteses(String expressao) {
        return avaliaSoma(expressao);
    }

    public String avaliaExpressao(String expressao) {
        if(ehAtribuicao(expressao)){
            atribui(expressao);
            return null;
        }

        if(ehBooleana(expressao)){

            return String.valueOf(avaliaBooleano(expressao));

        }

        return String.valueOf(avaliaExpressaoAlgebrica(expressao));
    }

    public boolean ehBooleana(String expressao) {
        boolean resultado = false;

        for (String operadorBooleano : operadoresBooleanos) {

            if (expressao.contains(operadorBooleano)) resultado = true;

        }

        return resultado;
    }

    public boolean ehAtribuicao(String expressao){
        return expressao.contains(operadorDeAtribuicao);
    }

    public boolean avaliaBooleano(String booleano) {
        for (String operadorBooleano : operadoresBooleanos) {
            if(booleano.contains(operadorBooleano)) return operacao(booleano, operadorBooleano);
        }

        throw new IllegalArgumentException("Operador booleano inválido.");
    }

    public boolean operacao(String booleano, String operadorBooleano) {
        String[] ed = booleano.split(operadorBooleano, 2);

        double numeroEsquerdo = avaliaExpressaoAlgebrica(ed[0]);
        double numeroDireito = avaliaExpressaoAlgebrica(ed[1]);

        switch (operadorBooleano) {

            case "==":

                return numeroEsquerdo == numeroDireito;

            case "!=":

                return numeroEsquerdo != numeroDireito;

            case ">=":

                return numeroEsquerdo >= numeroDireito;

            case "<=":

                return numeroEsquerdo <= numeroDireito;

            case "<":

                return numeroEsquerdo < numeroDireito;

            case ">":

                return numeroEsquerdo > numeroDireito;
        }

        throw new IllegalArgumentException("Operador booleano inválido.");
    }

    public double avaliaExpressaoAlgebrica(String expressaoAlgebrica){
        expressaoAlgebrica = resolveParenteses(expressaoAlgebrica);

        return avaliaSemParenteses(expressaoAlgebrica);
    }

    public void atribui(String expressao){
        String[] ed = expressao.split(operadorDeAtribuicao, 2);
        ed[0] = ed[0].trim();

        if(ed[0].length() != 1 || !Character.isLetter(ed[0].charAt(0))){

            throw new IllegalArgumentException("O nome da variável é inválido");

        }

        String avaliacao = String.valueOf(avaliaExpressaoAlgebrica(ed[1]));
        variaveis.put(ed[0], avaliacao);
    }

    public void visualizaResultado(String expressao){

        String resultado = avaliaExpressao(expressao);

        if(resultado != null){

            System.out.println(resultado);

        }
    }
}
