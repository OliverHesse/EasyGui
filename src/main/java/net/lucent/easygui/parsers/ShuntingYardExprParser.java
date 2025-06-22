package net.lucent.easygui.parsers;

import com.electronwill.nightconfig.core.io.ParsingException;

import java.util.*;

//TODO im starting to debate if i actualy want this or not... since most UI requires some amount of programming...
//this seems kind of pointless now

//TODO add variables. like parent.width, parent.height ,parent.scaledWidth, parent.scaledHeight
//TODO even allow parent.parent.width
//TODO potentially add functions
//i miss peek...
//todo update to use number() variable() and operand() obj
public class ShuntingYardExprParser {
    List<String> operandStack = new ArrayList<>();
    List<Token> output = new ArrayList<>();
    //gets it in reverse Polish notation
    public String parseInput(String input){
        Set<String> digits = Set.of("1","2","3","4","5","6","7","8","9","0");
        HashMap<String,Integer> operandPriority = new HashMap<>(){{
           put("+",2);
           put("-",2);
           put("*",3);
           put("/",3);
           put("^",4);
           put("(",1);
        }};
        input = input.replaceAll("\\s","");
        //if not in this set assumed to be right associative
        Set<String> leftAssociative = Set.of("+","-","*","/");
        List<String> inputList = new ArrayList<>();
        for (Character ch : input.toCharArray()){
            inputList.add(ch.toString());
        }
        Iterator<String> iterator = inputList.iterator();
        while(iterator.hasNext()){
            String ch = iterator.next();

            if(digits.contains(ch)){
                StringBuilder builder = new StringBuilder();
                do {
                    builder.append(ch);
                } while (iterator.hasNext() && digits.contains(ch = iterator.next()));
                output.add(new Value(builder.toString()));
            }
            char chC = ch.toCharArray()[0];
            if(Character.isAlphabetic(chC) || ch.equals(".")){
                StringBuilder builder = new StringBuilder();
                do {
                    builder.append(ch);
                    if(iterator.hasNext()){
                        ch = iterator.next();
                        chC = ch.toCharArray()[0];
                    }
                } while (iterator.hasNext() && Character.isAlphabetic(chC) || ch.equals("."));
                output.add(new Variable(builder.toString()));
            }

            if(operandPriority.containsKey(ch)){
                while(!operandStack.isEmpty() && (operandPriority.get(operandStack.getLast()) >= operandPriority.get(ch) && leftAssociative.contains(ch))){
                    output.add(new Operator(operandStack.removeLast()));
                }
                operandStack.add(ch);
            }

            if(Objects.equals(ch, ")")){


                while(!operandStack.isEmpty() && !Objects.equals(operandStack.getLast(), "(")){

                    output.add(new Operator(operandStack.removeLast()));
                }

                if(!operandStack.isEmpty()) operandStack.removeLast();

            }
        }
        if(!operandStack.isEmpty()) {
            for(String op : operandStack){
                output.add(new Operator(op));
            }
        }
        return output.toString();
    }

    public interface Token{}

    public record Function(String value)implements Token {};
    public record Variable(String value)implements Token {};
    public record Operator(String value)implements Token {};
    public record Value(String value)implements Token {};
}
