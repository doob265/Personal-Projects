//Mark Dubin   
//12/11/2020
//AoC 2020 Day 11: Seating System
//To change between parts 1 and 2, go to lines 348-351 and change which function copy becomes = to

import java.util.*;
import java.io.*;

public class dayEleven {
    //checks how many # there are to specific seat using part 1's rules
    public static int testOne(char[][] seats, int row, int col, int i, int j){
        int k,l, count = 0;
        //the following checks specific edge cases of oob indices, count increments for each valid adjacent #
        //normal
        if((i-1 >= 0 && i+1 < row) && (j-1 >= 0 && j+1 < col)){
            for(k = i-1; k <= i+1; k++){
                for(l = j-1; l <= j+1; l++){
                    if((k != i || l != j) && seats[k][l] == '#') count++;
                }
            }
            //i out left
        } else if(((i-1 < 0) && (j-1 >= 0 && j+1 < col))){
            for(k = i; k <= i+1; k++){
                for(l = j-1; l <= j+1; l++){
                    if((k != i || l != j) && seats[k][l] == '#') count++;
                }
            }
            //i out right
        } else if(((i+1 > row-1) && (j-1 >= 0 && j+1 < col))){
            for(k = i-1; k <= i; k++){
                for(l = j-1; l <= j+1; l++){
                    if((k != i || l != j) && seats[k][l] == '#') count++;
                }
            }
            //j out up
        } else if((i-1 >= 0 && i+1 < row) && (j-1 < 0)){
            for(k = i-1; k <= i+1; k++){
                for(l = j; l <= j+1; l++){
                    if((k != i || l != j) && seats[k][l] == '#') count++;
                }
            }
            //j out down
        } else if((i-1 >= 0 && i+1 < row) && (j+1 > col-1)){
            for(k = i-1; k <= i+1; k++){
                for(l = j-1; l <= j; l++){
                    if((k != i || l != j) && seats[k][l] == '#') count++;
                }
            }
            //out up left
        } else if((i-1 < 0) && (j-1 < 0)){
            for(k = i; k <= i+1; k++){
                for(l = j; l <= j+1; l++){
                    if((k != i || l != j) && seats[k][l] == '#') count++;
                }
            }
            //out down right
        } else if((i+1 > row-1) && (j+1 > col-1)){
            for(k = i-1; k <= i; k++){
                for(l = j-1; l <= j; l++){
                    if((k != i || l != j) && seats[k][l] == '#') count++;
                }
            }
            //out down left
        } else if((i-1 < 0) && (j+1 > col-1)){
            for(k = i; k <= i+1; k++){
                for(l = j-1; l <= j; l++){
                    if((k != i || l != j) && seats[k][l] == '#') count++;
                }
            }
            //out up right
        } else if((i+1 > row-1) && (j-1 < 0)){
            for(k = i-1; k <= i; k++){
                for(l = j; l <= j+1; l++){
                    if((k != i || l != j) && seats[k][l] == '#') count++;
                }
            }
        }
        return count;
    }

    //counts number of # in line from current seat based on part 2's rules
    public static int testTwo(char[][] seats, int row, int col, int i, int j){
        int count = 0, k, l;
        boolean done = false;
        //System.out.println("next");
        //there are 8 possible lines to check from seat, each one being a specific case. for each line, if a # is encountered, # increments once and is done
        //up
        if(i-1 >= 0){
            //System.out.println("in up");
            done = false;
            for(k = i-1; k >= 0; k--){
                if(seats[k][j] == '#' && done == false){
                    //System.out.println("Found #");
                    count++;
                    done = true;
                    break;
                }
                else if(seats[k][j] == 'L'){
                    //System.out.println("Found L");
                    done = true;
                    break;
                }
            }
        }
        //down
        if(i+1 < row){
            //System.out.println("in down");
            done = false;
            for(k = i+1; k < row; k++){
                if(seats[k][j] == '#' && done == false){
                    //System.out.println("Found #");
                    count++;
                    done = true;
                    break;
                }
                else if(seats[k][j] == 'L'){
                    //System.out.println("Found L");
                    done = true;
                    break;
                }
            }
        }
        //left
        if(j-1 >= 0){
            //System.out.println("in left");
            done = false;
            for(k = j-1; k >= 0; k--){
                if(seats[i][k] == '#' && done == false){
                    //System.out.println("Found #");
                    count++;
                    done = true;
                    break;
                }
                else if(seats[i][k] == 'L'){
                    //System.out.println("Found L");
                    done = true;
                    break;
                }
            }
        }
        //right
        if(j+1 < col){
            //System.out.println("in right");
            done = false;
            for(k = j+1; k < col; k++){
                if(seats[i][k] == '#' && done == false){
                    //System.out.println("Found #");
                    count++;
                    done = true;
                    break;
                }
                else if(seats[i][k] == 'L'){
                    //System.out.println("Found L");
                    done = true;
                    break;
                }
            }
        }
        //up-left
        if(i-1 >= 0 && j-1 >= 0){
            //System.out.println("in up-left");
            k = i-1;
            l = j-1;
            done = false;
            while(k >= 0 && l >= 0){
                if(seats[k][l] == '#' && done == false){
                    //System.out.println("Found #");
                    count++;
                    done = true;
                    break;
                }
                else if(seats[k][l] == 'L'){
                    //System.out.println("Found L");
                    done = true;
                    break;
                }
                k--;
                l--;
            }
        }
        //down-left
        if(i+1 < row && j-1 >= 0){
            //System.out.println("in down-left");
            k = i+1;
            l = j-1;
            done = false;
            while(k < row && l >= 0){
                if(seats[k][l] == '#' && done == false){
                    //System.out.println("Found #");
                    count++;
                    done = true;
                    break;
                }
                else if(seats[k][l] == 'L'){
                    //System.out.println("Found L");
                    done = true;
                    break;
                }
                k++;
                l--;
            }
        }
        //up-right
        if(i-1 >= 0 && j+1 < col){
            //System.out.println("in up-right");
            k = i-1;
            l = j+1;
            done = false;
            while(k >= 0 && l < col){
                if(seats[k][l] == '#' && done == false){
                    //System.out.println("Found #");
                    count++;
                    done = true;
                    break;
                }
                else if(seats[k][l] == 'L'){
                    //System.out.println("Found L");
                    done = true;
                    break;
                }
                k--;
                l++;
            }
        }
        //down-right
        if(i+1 < row && j+1 < col){
            //System.out.println("in down-right");
            k = i+1;
            l = j+1;
            done = false;
            while(k < row && l < col){
                if(seats[k][l] == '#' && done == false){
                    //System.out.println("Found #");
                    count++;
                    done = true;
                    break;
                }
                else if(seats[k][l] == 'L'){
                    //System.out.println("Found L");
                    done = true;
                    break;
                }
                k++;
                l++;
            }
        }
        return count;
    }

    //function performs each individual "round" of seat swaps for round one
    public static char[][] roundOne(char[][] seats, int row, int col){
        int i, j, count = 0;
        char[][] copy = new char[row][col];
        char cur;

        //make copy seats
        for(i = 0; i < row; i++){
            for(j = 0; j < col; j++){
                copy[i][j] = seats[i][j];
            }
        }
        
        //double for will go to each individual seat
        for(i = 0; i < row; i++){
            for(j = 0; j < col; j++){
                cur = seats[i][j];
                //run testOne, if no adjacent # are found, make L #
                if(cur == 'L'){
                    count = testOne(seats, row, col, i, j);
                    if(count == 0) copy[i][j] = '#';
                }
                //run testOne, if four or more adjacent # are found, make # L
                else if(cur == '#'){
                    count = testOne(seats, row, col, i, j);
                    if(count >= 4) copy[i][j] = 'L';
                }
            }
        }
        return copy;
    }

    //function performs each individual "round" of seat swaps for round two
    public static char[][] roundTwo(char[][] seats, int row, int col){
        int i, j, count = 0;
        char[][] copy = new char[row][col];
        char cur;

        //make copy seats
        for(i = 0; i < row; i++){
            for(j = 0; j < col; j++){
                copy[i][j] = seats[i][j];
            }
        }
        
        //double for will go to each individual seat
        for(i = 0; i < row; i++){
            for(j = 0; j < col; j++){
                cur = seats[i][j];
                //run testTwo, if no # in line are found, make L #
                if(cur == 'L'){
                    count = testTwo(seats, row, col, i, j);
                    if(count == 0){
                        //System.out.println(count);
                        copy[i][j] = '#';
                    }
                }
                //run testTwo, if five or more # in line are found, make # L
                else if(cur == '#'){
                    count = testTwo(seats, row, col, i, j);
                    if(count >= 5) {
                        //System.out.println(count);
                        copy[i][j] = 'L';
                    }
                }
            }
        }
        return copy;
    }
    public static void main(String[] args) throws IOException{
        final int row = 92, col = 95;
        char[][] seats = new char[row][col], copy = new char[row][col];
        char[] line = new char[col];
        int index = 0, i, j, ans = 0, cnt = 0;
        String temp;
        boolean same = false, check;
        Scanner s = new Scanner(new File("dayElevenInput.txt"));
        //read in seating map from file
        while(s.hasNextLine()){
            temp = s.nextLine();
            line = temp.toCharArray();
            for(i = 0; i < temp.length(); i++){
                seats[index][i] = line[i];
            }
            index++;
        }
        /*System.out.println("seats:");
        for(i = 0; i < row; i++){
            for(j = 0; j < col; j++){
                System.out.print(seats[i][j]);
            }
            System.out.println();
        }*/
        //this will continue to run until the seats stabilize
        while(!same){
            //System.out.println("run " + cnt);
            cnt++;
            //part one
            //System.out.println("Part one:");
            //copy = roundOne(seats, row, col);
            //part two
            //System.out.println("Part two:");
            copy = roundTwo(seats, row, col);
            check = true;
            for(i = 0; i < row; i++){
                for(j = 0; j < col; j++){
                    if(seats[i][j] != copy[i][j]){
                        check = false;
                        //System.out.println(i + " " +  j + " " + same);
                    }
                }
            }
            //if they're not the same, make seats copy in preperation for next round
            if(!check){
                for(i = 0; i < row; i++){
                    for(j = 0; j < col; j++){
                        seats[i][j] = copy[i][j];
                    }
                }
            }
            else same = true;
            /*System.out.println("copy:");
            for(i = 0; i < row; i++){
                for(j = 0; j < col; j++){
                    System.out.print(copy[i][j]);
                }
                System.out.println();
            }*/
        }
        /*System.out.println("copy:");
        for(i = 0; i < row; i++){
            for(j = 0; j < col; j++){
                System.out.print(copy[i][j]);
            }
            System.out.println();
        }*/
        //System.out.println("seats:");
        //calculate number of # in the end
        for(i = 0; i < row; i++){
            for(j = 0; j < col; j++){
                //System.out.print(seats[i][j]);
                if(seats[i][j] == '#') ans++;
            }
            //System.out.println();
        }
        System.out.println(ans);
    }
}
