//Mark Dubin
//12/04/2020
//AoC Day 4: Passport Processing

import java.util.*;
import java.io.*;

public class dayFour {
    //method used for part 2 to determine validity of passport
    public static boolean validate(String line, int[] indices, int num){
        int i, year, pid, hei;
        String temp;
        for(i = 0; i < num; i++){
            //switch on fields to check
            switch(line.substring(indices[i] - 3, indices[i])){
                //must be between 1920 and 2002 to be valid
                case "byr":
                temp = line.substring(indices[i]+1, indices[i]+5);
                try{
                year = Integer.parseInt(temp);
                if(year < 1920 || year > 2002){
                    //System.out.println("byr rng " + year);
                    return false;
                }
                } catch(NumberFormatException nfe){
                    //System.out.println("byr int " + temp);
                    return false;
                }
                break;
                //must be between 2010 and 2020 to be valid
                case "iyr":
                temp = line.substring(indices[i]+1, indices[i]+5);
                try{
                    year = Integer.parseInt(temp);
                    if(year < 2010 || year > 2020){
                        //System.out.println("iyr rng " + year);
                        return false;
                    }
                    } catch(NumberFormatException nfe){
                        //System.out.println("iyr int " + temp);
                        return false;
                    }
                break;
                //must be between 2020 and 2030 to be valid
                case "eyr":
                temp = line.substring(indices[i]+1, indices[i]+5);
                try{
                    year = Integer.parseInt(temp);
                    if(year < 2020 || year > 2030){
                        //System.out.println("eyr rng " + year);
                        return false;
                    }
                    } catch(NumberFormatException nfe){
                        //System.out.println("eyr int " + temp);
                        return false;
                    }
                break;
                //depending on unit of measure, must be between given range to be valid
                case "hgt":
                temp = line.substring(indices[i]+1, line.indexOf(" ", indices[i]+1));
                //System.out.println(temp);
                if(!temp.substring(temp.length()-2, temp.length()).equals("cm") && !temp.substring(temp.length()-2, temp.length()).equals("in")){
                    //System.out.println("hgt end" + temp.substring(temp.length()-2, temp.length()));
                    return false;
                }
                if(temp.substring(temp.length()-2, temp.length()).equals("cm")){
                    try{
                        hei = Integer.parseInt(temp.substring(0, temp.length()-2));
                        if(hei < 150 || hei > 193){
                            //System.out.println("cm hei " + hei);
                            return false;
                        }
                    } catch(NumberFormatException nfe){
                        //System.out.println("hgt int " + temp.substring(0, temp.length()-2));
                        return false;
                    }
                }
                if(temp.substring(temp.length()-2, temp.length()).equals("in")){
                    try{
                        hei = Integer.parseInt(temp.substring(0, temp.length()-2));
                        if(hei < 59 || hei > 76){
                            //System.out.println("in hei " + hei);
                            return false;
                        }
                    } catch(NumberFormatException nfe){
                        //System.out.println("hgt int " + temp.substring(0, temp.length()-2));
                        return false;
                    }
                }
                break;
                //must start with # and have len of 7 to be valid
                case "hcl":
                temp = line.substring(indices[i]+1, line.indexOf(" ", indices[i]+1));
                //System.out.println(temp);
                if(temp.charAt(0) != '#' && temp.length() != 7){
                    //System.out.println("hcl " + temp);
                    return false;
                }
                break;
                //must be one of given strings to be valid
                case "ecl":
                temp = line.substring(indices[i]+1, indices[i]+4);
                if(!temp.equals("amb") && !temp.equals("blu") && !temp.equals("brn") && !temp.equals("gry") && !temp.equals("grn") && !temp.equals("hzl") && !temp.equals("oth")) return false;
                break;
                //must have len of 9 to be valid
                case "pid":
                temp = line.substring(indices[i]+1, line.indexOf(" ", indices[i]+1));
                if(temp.length() != 9){
                    //System.out.println("pid len " + temp);
                    return false;
                }
                try{
                    pid = Integer.parseInt(temp);
                } catch(NumberFormatException nfe){
                    //System.out.println("pid int " + temp);
                    return false;
                }
                break;
                //don't need to check
                case "cid":
                break;
                default:
                //System.out.println("default");
                return false;
            }
        }

        return true;
    }
    public static void main(String[] args)throws IOException{
        boolean blank = false, cid = false, valid = false;
        String line, temp;
        int index = 0, count = 0, answerOne = 0, answerTwo = 0, total = 0, i = 0, j, begin, end;
        int[] indices = new int[8];
        Scanner s = new Scanner(new File("dayFourInput.txt"));
        while(s.hasNextLine()){
            blank = false;
            cid = false;
            valid = false;
            index = 0;
            count = 0;
            i = 0;
            line = s.nextLine();
            //get each passport, each is separated by blank line
            while(!blank && s.hasNextLine()){
                temp = s.nextLine();
                if(temp.isBlank()) blank = true;
                line = line.concat(" " + temp);
            }
            //determine the field that value goes with and how many fields are present
            while(index != -1){
                index = line.indexOf(':', index+1);
                if(index != -1){
                    count++;
                    indices[i] = index;
                    i++;
                }
            }
            //part one
            //all 8 needed fields are there
            if(count == 8){
                answerOne++;
                valid = true;
            }
            //need to check if missing field is cid
            else if(count == 7){
                for(j = 0; j < i; j++){
                    //System.out.println(line.substring(indices[j] - 3, indices[j]));
                    if(line.substring(indices[j] - 3, indices[j]).equals("cid")) cid = true;
                    indices[i] = 0;
                }
                if(!cid){
                    valid = true;
                    answerOne++;
                }
            }
            //part two
            //if all 8 fields are there and validate function returns true, passport is valid
            if(count == 8){
                if(validate(line, indices, 8)){
                    answerTwo++;
                    valid = true;
                }
            }
            //if cid is only field missing and validate function returns true, passport is valid
            else if(count == 7){
                for(j = 0; j < i; j++){
                    //System.out.println(line.substring(indices[j] - 3, indices[j]));
                    if(line.substring(indices[j] - 3, indices[j]).equals("cid")) cid = true;
                    indices[i] = 0;
                }
                if(!cid){
                    if(validate(line, indices, 7)){
                        answerTwo++;
                        valid = true;
                    }
                }
            }
            //System.out.println(line + " " + count + " " +  cid + " " + valid);
        }
        System.out.println("Part One: " + answerOne + " Part Two: " + answerTwo);
    }
}
