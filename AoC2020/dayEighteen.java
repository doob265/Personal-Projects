import java.util.*;
import java.io.*;

public class dayEighteen {
    public static void main(String[] args) throws IOException{
        String exp, ch, opr = "", postfix = "";
        char pop = ' ';
        int i, operand, cnt = 0;
        Stack<Integer> ints = new Stack<Integer>();
        Stack<Character> ops = new Stack<Character>();
        Scanner s = new Scanner(new File("dayEighteenInput.txt"));
        while(s.hasNextLine()){
            exp = s.nextLine();
            postfix = "";
            System.out.print(exp + " = ");
            for(i = 0; i < exp.length(); i++){
                //System.out.println(i + " " + exp.charAt(i));
                try{
                    operand = Integer.parseInt(exp.substring(i, i+1));
                    postfix = postfix.concat(exp.substring(i, i+1));
                } catch(NumberFormatException e){
                    if(exp.substring(i, i+1).equals("(")){
                        ops.push('(');
                        //System.out.println("peek: " + ops.peek());
                    }
                    else if(exp.charAt(i) == ')'){
                        try{
                            pop = ops.pop();
                        } catch(EmptyStackException ee){
                            pop = '!';
                        }
                        System.out.println("pop: " + pop);
                        while(pop != '(' && pop != '!'){
                            System.out.println("pop: " + pop);
                            postfix = postfix.concat(Character.toString(pop));
                            try{
                                pop = ops.pop();
                            } catch(EmptyStackException ee){
                                pop = '!';
                            }
                        }
                        try{
                            ops.pop();
                        } catch(EmptyStackException ee){}
                    }
                    else if(exp.charAt(i) != ' '){
                        if(ops.size() == 0) ops.push(exp.charAt(i));
                        else if(ops.peek() == '(') ops.push(exp.charAt(i));
                        else{
                            try{
                                while(true && ops.peek() != '('){ 
                                    //System.out.println("2");
                                    pop = ops.pop();
                                    postfix = postfix.concat(Character.toString(pop));
                                    if(ops.peek() != '('){
                                        pop = ops.pop();
                                        System.out.println("Pop: " + pop);
                                    }
                                }
                            } catch(EmptyStackException ee){
                                ops.push(exp.charAt(i));
                                //System.out.println("peek: " + ops.peek());
                            }
                        }
                    }
                }
            }
            System.out.println(postfix);
            cnt++;
            if(cnt == 2) return;
        }
    }
}
