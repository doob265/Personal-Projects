import java.io.*;
import java.util.*;

public class dayTwentyTwo{
    public static ArrayList<Integer> mainGame(ArrayList<Integer> one, ArrayList<Integer> two){
        int o, t, i, n, len, score = 0, win;
        ArrayList<Integer> winner = new ArrayList<Integer>(), cOne, cTwo, ccOne, ccTwo;
        ArrayList<ArrayList<Integer>> allOne = new ArrayList<ArrayList<Integer>>(), allTwo = new ArrayList<ArrayList<Integer>>();
        //keep going until one player wins game
        while(one.size() > 0 && two.size() > 0){
            cOne = new ArrayList<Integer>();
            cTwo = new ArrayList<Integer>();
            System.out.println("One: " + one);
            System.out.println("Two: " + two);
            //check if same deck has occured before in this game for this player
            //System.out.println("allOne:");
            for(ArrayList<Integer> a : allOne){
                //System.out.println(a);
                if(a.equals(one)){
                    System.out.println("Returning one...");
                    return one;
                }
            }
            //System.out.println("allTwo:");
            for(ArrayList<Integer> a : allTwo){
                //System.out.println(a);
                if(a.equals(two)){
                    System.out.println("Returning two...");
                    return two;
                }
            }
            //keep track of what decks have already been used
            for(int e : one) cOne.add(e);
            for(int e : two) cTwo.add(e);
            allOne.add(cOne);
            allTwo.add(cTwo);
            //get and remove top from both decks
            o = one.get(0);
            t = two.get(0);
            one.remove(0);
            two.remove(0);
            //recurse on this case
            if(one.size() >= o && two.size() >= t){
                System.out.println("Going to subgame...");
                ccOne = new ArrayList<Integer>();
                ccTwo = new ArrayList<Integer>();
                for(int e : one) ccOne.add(e);
                for(int e : two) ccTwo.add(e);
                win = subGame(ccOne, ccTwo);
                System.out.println("Returned from subgame. Win " + win + " o " + o + " t " + t);
                System.out.println("One: " + one);
                System.out.println("Two: " + two);
                //player one wins round
                if(win == 1){
                    one.add(o);
                    one.add(t);
                }
                //player two wins round
                else{
                    two.add(t);
                    two.add(o);
                }
            }
            //no recursion
            else{
                //player one wins round
                if(o > t){
                    one.add(o);
                    one.add(t);
                }
                //player two wins round
                else{
                    two.add(t);
                    two.add(o);
                }
            }
            System.out.println("One: " + one);
            System.out.println("Two: " + two);
        }

        //determine winner, return their info
        if(one.size() != 0) return one;
        else return two;
        
        //calculate winner's score
        /*for(i = 0; i < len; i++){
            n = (len - i) * winner.get(i);
            score += n;
        }
        return score;*/
    }

    public static int subGame(ArrayList<Integer> one, ArrayList<Integer> two){
        int o, t, i, n, len, score = 0, win;
        ArrayList<Integer> winner = new ArrayList<Integer>(), cOne, cTwo, ccOne, ccTwo;
        ArrayList<ArrayList<Integer>> allOne = new ArrayList<ArrayList<Integer>>(), allTwo = new ArrayList<ArrayList<Integer>>();
        //keep going until one player wins game
        while(one.size() > 0 && two.size() > 0){
            cOne = new ArrayList<Integer>();
            cTwo = new ArrayList<Integer>();
            //System.out.println("One: " + one);
            //System.out.println("Two: " + two);
            //check if same deck has occured before in this game for this player
            for(ArrayList<Integer> a : allOne){
                if(a.equals(one)) return 1;
            }
            for(ArrayList<Integer> a : allTwo){
                if(a.equals(two)) return 2;
            }
            //keep track of what decks have already been used
            for(int e : one) cOne.add(e);
            for(int e : two) cTwo.add(e);
            allOne.add(cOne);
            allTwo.add(cTwo);
            //get and remove top from both decks
            o = one.get(0);
            t = two.get(0);
            one.remove(0);
            two.remove(0);
            //recurse on this case
            if(one.size() >= o && two.size() >= t){
                ccOne = new ArrayList<Integer>();
                ccTwo = new ArrayList<Integer>();
                for(int e : one) ccOne.add(e);
                for(int e : two) ccTwo.add(e);
                win = subGame(ccOne, ccTwo);
                //player one wins round
                if(win == 1){
                    one.add(o);
                    one.add(t);
                }
                //player two wins round
                else{
                    two.add(t);
                    two.add(o);
                }
            }
            else{
                //player one wins round
                if(o > t){
                    one.add(o);
                    one.add(t);
                }
                //player two wins round
                else{
                    two.add(t);
                    two.add(o);
                }
            }
        }

        //determine winner, return their info
        if(one.size() != 0) return 1;
        else return 2;
        
        //calculate winner's score
        /*for(i = 0; i < len; i++){
            n = (len - i) * winner.get(i);
            score += n;
        }
        return score;*/
    }
    public static void main(String[] args) throws IOException{
        Scanner s = new Scanner(new File("dayTwentyTwoInput.txt"));
        String temp;
        int o, t, len, i, score = 0, n;
        ArrayList<Integer> one = new ArrayList<Integer>(), two = new ArrayList<Integer>(), winner;

        //read in Player One's deck
        s.nextLine();
        temp = s.nextLine();
        while(!temp.isBlank()){
            one.add(Integer.parseInt(temp));
            temp = s.nextLine();
        }

        //read in Player Two's deck
        s.nextLine();
        while(s.hasNextLine()){
            temp = s.nextLine();
            two.add(Integer.parseInt(temp));
        }

        System.out.println(one);
        System.out.println();
        System.out.println(two);
        System.out.println("len combined: " + one.size() + two.size());

        /*Part 1: Regular Combat
        //keep going until one player wins game
        while(one.size() > 0 && two.size() > 0){
            //get and remove top from both decks
            o = one.get(0);
            t = two.get(0);
            one.remove(0);
            two.remove(0);
            //player one wins round
            if(o > t){
                one.add(o);
                one.add(t);
            }
            //player two wins round
            else{
                two.add(t);
                two.add(o);
            }
        }
        System.out.println("one: " + one);
        System.out.println();
        System.out.println("two: " + two);

        //determine winner, save their info
        if(one.size() != 0){
            len = one.size();
            winner = one;
        }
        else{
            len = two.size();
            winner = two;
        }
        
        score = 0;
        //calculate winner's score
        for(i = 0; i < len; i++){
            n = (len - i) * winner.get(i);
            score += n;
        }
        System.out.println("Part One: " + score);*/

        //Part Two: Recursive Combat
        winner = mainGame(one, two);
        len = winner.size();
        System.out.println("Winner: " + winner + " len " + len);
        //calculate winner's score
        for(i = 0; i < len; i++){
            n = (len - i) * winner.get(i);
            score += n;
        }
        System.out.println("Part Two: " + score);
    }
}