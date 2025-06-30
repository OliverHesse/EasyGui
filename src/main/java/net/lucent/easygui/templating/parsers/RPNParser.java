package net.lucent.easygui.templating.parsers;

import net.lucent.easygui.templating.IRenderableDeserializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RPNParser {

    public interface Eval{
        Double eval(double a,double b);
    }
    public static HashMap<String, Eval> operator = new HashMap<>(){{
        put("+", Double::sum);
        put("-",(a,b)->b-a);
        put("*",(a,b)->a*b);
        put("/",(a,b)->b/a);
        put("^", (a,b)->Math.pow(b,a));
    }};
    public static Double parse(List<ShuntingYardExprParser.Token> input, IRenderableDeserializer deserializer){
        List<ShuntingYardExprParser.Token> stack = new ArrayList<>();
        System.out.println("RPN");
        while(!input.isEmpty()){
            System.out.println(stack);
            ShuntingYardExprParser.Token token = input.removeFirst();

            if(token instanceof ShuntingYardExprParser.Operator){
                ShuntingYardExprParser.Token token1 = stack.removeLast();
                ShuntingYardExprParser.Token token2 = stack.removeLast();
                //todo add in variable parsing
                double value1;
                double value2;
                if(token1 instanceof ShuntingYardExprParser.Value) value1 = Double.parseDouble(((ShuntingYardExprParser.Value) token1).value());
                else value1 = deserializer.call(((ShuntingYardExprParser.Variable)token1).value().split("\\."));

                if(token2 instanceof ShuntingYardExprParser.Value) value2 = Double.parseDouble(((ShuntingYardExprParser.Value) token2).value());
                else value2 = deserializer.call(((ShuntingYardExprParser.Variable)token2).value().split("\\."));

                stack.add(new ShuntingYardExprParser.Value(operator.get(((ShuntingYardExprParser.Operator) token).value()).eval(value1,value2).toString()));
            }else {
                stack.add(token);
            }

        }
        System.out.println("finished parsing RPN");
        return Double.parseDouble(((ShuntingYardExprParser.Value) stack.getFirst()).value());
    }
}
