//Mark Dubin, 9/20/20, Cipher Collection
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

//class declaration
public class cipher{
    //number of words in topWords.txt
    final public static int MAXWORDS = 996;
    //for hill brute force decryption
    final public static int COUNT = 20;

    //get list of top words from file
    public static String[] readFromFile() throws IOException{
        //given there are exactly 1000 words to choose from
        String[] list = new String[MAXWORDS];
        int i;
        //initialize file, scanner, iterating variables
        File input = new File("topWords.txt");
        Scanner s = new Scanner(input);
        //get each word
        for(i = 0; i < MAXWORDS; i++){
            list[i] = s.nextLine();
        }

        //convert each word to uppercase
        for(i = 0; i < MAXWORDS; i++){
            list[i] = list[i].toUpperCase();
        }

        //close scanner and return list
        s.close();
        return list;
    }

    //menu function, gets user input for what encryption/decryption they want to perform
    public static int menu(Scanner scan){
        String choice;
        int cho;
        System.out.println("Press 1 to encrypt using Caesar's Cipher, 2 to decrypt using Caesar's Cipher, 3 to encrypt using the Vigenère Cipher, 4 to decrypt using the Vigenère Cipher, 5 to encrpyt using the Affine Cipher, 6 to decrypt using the Affine Cipher, 7 to decrypt using a 2x2 Hill Cipher or 8 to exit.");
        choice = scan.nextLine();
        //ensures valid input
        while(!menuCheck(choice)){
            System.out.println("Sorry, please try again. Press 1 to encrypt using Caesar's Cipher, 2 to decrypt using Caesar's Cipher, 3 to encrypt using the Vigenère Cipher, 4 to decrypt using the Vigenère Cipher, 5 to encrpyt using the Affine Cipher, 7 to decrypt using a 2x2 Hill Cipher or 8 to exit.");
            choice = scan.nextLine();
        }
        //convert to int and return
        cho = Integer.parseInt(choice);
        return cho;
    }

    //ensures user input for menu is acceptable
    public static boolean menuCheck(String choice){
        int i;
        for(i = 0; i < choice.length(); i++){
            if(choice.codePointAt(i) < 49 || choice.codePointAt(i) > 56){
                return false;
            }
        }
        return true;
    }

    //ensures user input for strings is acceptable
    public static boolean strCheck(String word, int mode, int len){
        int i;
        //used for caesar encrypt/decrypt
        if(mode == 0){
            for(i = 0; i < word.length(); i++){
                if(word.codePointAt(i) != 32 && (word.codePointAt(i) < 97 || word.codePointAt(i) > 122)){
                    return false;
                }
            }
        }
        //used for vigenere encrypt
        else if(mode == 1){
            for(i = 0; i < word.length(); i++){
                if(word.codePointAt(i) < 97 || word.codePointAt(i) > 122){
                    return false;
                }
            }
        }
        //used for vingenere decrypt
        else if(mode == 2){
            if(len != word.length()){
                return false;
            }
            for(i = 0; i < word.length(); i++){
                if(word.codePointAt(i) < 97 || word.codePointAt(i) > 122){
                    return false;
                }
            }
        }
        else{
            return false;
        }
        return true;
    }

    //used to ensure user input for ints is acceptable
    public static boolean intCheck(String shift){
        int i;
        for(i = 0; i < shift.length(); i++){
            if(shift.codePointAt(i) < 48 || shift.codePointAt(i) > 57){
                return false;
            }
        }
        return true;
    }

    //used to ensure user input for ints is acceptable
    public static boolean binaryIntCheck(String shift){
        if(shift.length() != 1){
            return false;
        }
        if(shift.codePointAt(0) == 48 || shift.codePointAt(0) == 49){
            return true;
        }
        return false;
    }

    //encryption method for Caesar's Cipher
    public static String caesarEncrypt(String word, int key){
        //alphabet char array used for reference, char array temp used to perform shift
        char[] alph = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'}, temp = word.toCharArray();
        int i, count = 0;
        for(i = 0; i < word.length(); i++){
            //these chars are allowed, but don't get shifted, so skip interior during this iteration
            if(temp[i] != ' ' && temp[i] != '.' && temp[i] != '!' && temp[i] != '?' && temp[i] != '\'' && temp[i] != '"' && temp[i] != ',' && temp[i] != ';'){
                //find numeric value of letter
                count = temp[i] - 'a';
                //perform shift based on key, reassign
                count += key;
                count %= 26;
                temp[i] = alph[count];
            }
        }
        
        //reformat char array into string, return
        String ret = new String(temp);
        word = ret;
        return word;
    }

    //decryption method fo Caesar's Cipher
    public static String caesarDecrypt(String word, int key){
        //alphabet char array used for reference, char array temp used to perform shift
        char[] alph = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'}, temp = word.toCharArray();
        int i, count = 0;
        for(i = 0; i < word.length(); i++){
            //these chars are allowed, but don't get shifted, so skip interior during this iteration
            if(temp[i] != ' ' && temp[i] != '.' && temp[i] != '!' && temp[i] != '?' && temp[i] != '\'' && temp[i] != '"' && temp[i] != ',' && temp[i] != ';'){
                //find numeric value of letter
                count = temp[i] - 'a';
                //perform shift based on key, reassign
                key -= 26;
                count -= key;
                count %= 26;
                count = Math.abs(count);
                temp[i] = alph[count];
            }
        }

        //reformat char array into string, return
        String ret = new String(temp);
        word = ret;
        return word;
    }

    //encryption method for the Vigenère Cipher
    public static String vigenereEncrypt(String word, String key){
        //alphabet char array used for reference, char arrays used to perform shift
        char[] alph = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'}, wor = word.toCharArray(), k = key.toCharArray();
        int i, count = 0, track = 0, kc = 0, klen = key.length();

        for(i = 0; i < word.length(); i++){
            //find cooresponding letters numeric values
            count = wor[i] - 'a';
            track = k[kc] - 'a';

            //keep track of which letter of key to use
            kc++;
            kc %= klen;

            //find new letter, reassign
            count += track;
            count %= 26;
            wor[i] = alph[count];
        }

        //reformat char array into string, return
        String ret = new String(wor);
        word = ret;
        return word;
    }

        //decryption method for the Vigenère Cipher
    public static String vigenereDecrypt(String word, String key){
        //alphabet char array used for reference, char arrays used to perform shift
        char[] alph = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'}, wor = word.toCharArray(), k = key.toCharArray();
        int i, count = 0, kk = 0, klen = key.length(), kc = 0;
        System.out.println("klen: " + klen);

        for(i = 0; i < word.length(); i++){
            //find cooresponding letters numeric values
            count = wor[i] - 'a';
            kk = k[kc] - 'a';

            //keep track of which letter of key to use
            kc++;
            kc %= klen;

            //find new letter, reassign
            kk -= 26;
            count -= kk;
            count %= 26;
            count = Math.abs(count);
            wor[i] = alph[count];
        }

        //reformat string into word, return
        String ret = new String(wor);
        word = ret;
        return word;
    }

    public static String affineEncrypt(String word, int a, int b){
        //alphabet char array used for reference, char array temp used to perform shift
        char[] alph = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'}, temp = word.toCharArray();
        int i, count = 0, result;
        for(i = 0; i < word.length(); i++){
            //these chars are allowed, but don't get shifted, so skip interior during this iteration
            if(temp[i] != ' ' && temp[i] != '.' && temp[i] != '!' && temp[i] != '?' && temp[i] != '\'' && temp[i] != '"' && temp[i] != ',' && temp[i] != ';'){
                //find numeric value of letter
                count = temp[i] - 'a';
                //perform shift based on a and b, reassign
                result = ((a * count) + b) % 26;
                temp[i] = alph[result];
            }
        }

        //reformat char array into string, return
        String ret = new String(temp);
        word = ret;
        return word;
    }

    //affine cipher decryption
    public static String affineDecrypt(String word, int a, int b){
        //alphabet char array used for reference, char array temp used to perform shift
        char[] alph = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'}, temp = word.toCharArray();
        int i = 0, count = 0, result = 0, diff, v = -1;
        int valid[] = {1,3,5,7,9,11,15,17,19,21,23,25};
        int inverse[] = {1,9,21,15,3,19,7,23,11,5,17,25};
        double div;

        //find a within valid array, assign index
        while(v == -1){
            if(valid[i] == a){
                v = i;
            }
            else{
                i++;
            }
        }
        
        //inverse contains cooresponding mod inverse value, adjusts a and b accordingly
        b = -b * inverse[v];
        a = inverse[v];

        //finds value that is divisble, reassigns
        for(i = 0; i < 25; i++){
            diff = b-i;
            div = (double)diff / 26.0;
            //determines if div is divisible by 1 (an integer)
            if((div == Math.floor(div)) && !Double.isInfinite(div)){
                b = i;
                i = 26;
            }
        }

        //performs actual decryption
        for(i = 0; i < word.length(); i++){
            //find numeric value of letter
            count = temp[i] - 'a';
            //perform shift based on a and b, reassign
            result = ((a * count + b) % 26);
            //if result goes negative, put back into proper range
            if(result < 0){
                result += 26;
            }
            temp[i] = alph[result];
        }

        //reformat char array into string, return
        String ret = new String(temp);
        word = ret;
        return word;
    }

    //performs Hill Cipher Decryption
    public static String hillDecrypt(String cipher, int[][] key){
        char[] plain = new char[cipher.length()];
        int i, j, sum;
        //two rows
        for(i = 0; i < 2; i++){
            //start at 0 each time encrypting
            sum = 0;
            //two columns
            for(j = 0; j < 2; j++){
                //System.out.println(cipher.charAt((j)));
                //multiply the letter by each value in column of key, add these together
                sum += (key[i][j]*(cipher.charAt((j))-'A'));
            }
            //get letter into appropriate range
            plain[i] = (char)((sum%26)+'A');
        }
        //turn plain into a String, return it
        return new String(plain);
    }
    
    //main driver function
    public static void main(String[]args) throws IOException{
        int key, choice = 0, aa, bru, i, j, k, l, m, n, o, len, count;
        //matrix
        int[][] matrix = new int[2][2];
        String word, temp, kk, a, b, brute, plain = "", cipher;
        //get wordlist from file
        String[] words = readFromFile();
        Scanner scan = new Scanner(System.in);
        int[] validA = {1,3,5,7,9,11,15,17,19,21,23,25};

        //while loop used to continually execute until user is done with program
        System.out.print("Welcome! ");
        while(choice != 8){
            choice = menu(scan);
            //caesar encrypt
            if(choice == 1){
                System.out.println("Enter word or string to encrypt, only entering alphabetic characters a-z.");
                if(scan.hasNextLine()){
                    System.out.println("has");
                }
                word = scan.nextLine();
                word = word.toLowerCase();
        
                //ensure user input is valid
                while(!strCheck(word, 0, 0)){
                    System.out.println("Sorry, please only enter alphabetical letters a-z.");
                    word = scan.nextLine();
                    word.toLowerCase();
                }
        
                System.out.println("Enter number of shifts you would like to do on " + word + ". Please only enter digits 0-9, and a positive number less than 27..");
                temp = scan.nextLine();

                //ensure user input is valid
                while(!intCheck(temp)){
                    System.out.println("Sorry, please only enter numerical digits 0-9, and a positive number less than 27..");
                    temp = scan.nextLine();
                }

                //reformat key to proper length
                key = Integer.parseInt(temp);
                key %= 26;
        
                //perform encryption
                System.out.println("Encrypting " + word + ", shifting " + key + " times..."); 
                word = caesarEncrypt(word, key);
                System.out.println("Encryption: " + word);
            }

            //caesar decryption
            else if(choice == 2){
                //check if user wants to brute force, or if they have a and b ready
                System.out.println("Please enter 1 if you already have a shift key, or 0 if you want to brute-force decrypt.");
                brute = scan.nextLine();
                //ensure user input is valid
                while(!binaryIntCheck(brute)){
                    System.out.println("Sorry, please enter 1 if you already have a shift key, or 0 if you want to brute-force decrypt.");
                    brute = scan.nextLine();
                }
                bru = Integer.parseInt(brute);

                //get ciphertext from user
                System.out.println("Enter word or string to decrypt, only entering alphabetic characters a-z.");
                word = scan.nextLine();
                word = word.toLowerCase();
        
                //ensure user input is valid
                while(!strCheck(word, 0, 0)){
                    System.out.println("Sorry, please only enter alphabetical letters a-z.");
                    word = scan.nextLine();
                    word.toLowerCase();
                }
        
                //key ready
                if(bru == 1){
                    System.out.println("Enter number of shifts you would like to do on " + word + ". Please only enter digits 0-9, and a positive number less than 27.");
                    temp = scan.nextLine();
    
                    //ensure user input is valid
                    while(!intCheck(temp)){
                        System.out.println("Sorry, please only enter numerical digits 0-9, and a positive number less than 27.");
                        temp = scan.nextLine();
                    }
    
                    //reformat key to proper length
                    key = Integer.parseInt(temp);
                    key %= 26;
            
                    //perform decryption
                    System.out.println("Decrypting " + word + ", shifting " + key + " times..."); 
                    word = caesarDecrypt(word, key);
                    System.out.println("Decryption: " + word);
                }
                //brute
                else{
                    temp = word;
                    //perform decryption
                    for(i = 1; i < 26; i++){
                        word = temp;
                        System.out.println();
                        System.out.println("Decrypting " + word + ", shifting " + i + " times..."); 
                        word = caesarDecrypt(word, i);
                        System.out.println("Decryption: " + word);
                        System.out.println();
                    }
                }
            }

            //vigenere encryption
            else if(choice == 3){
                System.out.println("Enter word or string to encrypt, only entering alphabetic characters a-z. If you're using multiple words, don't use spaces between them.");
                word = scan.nextLine();
                word = word.toLowerCase();
        
                //ensure user input is valid
                while(!strCheck(word, 1, 0)){
                    System.out.println("Sorry, please only enter alphabetical letters a-z. If you're using multiple words, don't use spaces between them.");
                    word = scan.nextLine();
                    word.toLowerCase();
                }

                System.out.println("Enter key to use for encryption, only entering alphabetic characters a-z. If you're using multiple words, don't use spaces between them.");
                kk = scan.nextLine();
                kk = kk.toLowerCase();
        
                //ensure user input is valid
                while(!strCheck(kk, 1, 0)){
                    System.out.println("Sorry, please only enter alphabetical letters a-z. If you're using multiple words, don't use spaces between them.");
                    kk = scan.nextLine();
                    kk.toLowerCase();
                }

                //perform encryption
                System.out.println("Encrypting " + word + ", using the key " + kk + "."); 
                word = vigenereEncrypt(word, kk);
                System.out.println("Encryption: " + word);
            }

            //vigenere decryption
            else if(choice == 4){
                System.out.println("Enter word or string to decrypt, only entering alphabetic characters a-z. If you're using multiple words, don't use spaces between them.");
                word = scan.nextLine();
                word = word.toLowerCase();
        
                //ensure valid input
                while(!strCheck(word, 1, 0)){
                    System.out.println("Sorry, please only enter alphabetical letters a-z. If you're using multiple words, don't use spaces between them.");
                    word = scan.nextLine();
                    word.toLowerCase();
                }

                System.out.println("Enter key to use for decryption, only entering alphabetic characters a-z. If you're using multiple words, don't use spaces between them.");
                kk = scan.nextLine();
                kk = kk.toLowerCase();
        
                //ensure valid input
                while(!strCheck(kk, 1, 0)){
                    System.out.println("Sorry, please only enter alphabetical letters a-z. If you're using multiple words, don't use spaces between them.");
                    kk = scan.nextLine();
                    kk.toLowerCase();
                }

                //perform decryption
                System.out.println("Decrypting " + word + ", using the key " + kk + "."); 
                word = vigenereDecrypt(word, kk);
                System.out.println("Decryption: " + word);
            }

            //affine encrypt
            else if(choice == 5){
                System.out.println("Enter word or string to encrypt, only entering alphabetic characters a-z.");
                word = scan.nextLine();
                word = word.toLowerCase();
        
                //ensure user input is valid
                while(!strCheck(word, 0, 0)){
                    System.out.println("Sorry, please only enter alphabetical letters a-z.");
                    word = scan.nextLine();
                    word.toLowerCase();
                }
        
                System.out.println("Enter a, please only enter digits 0-9...");
                a = scan.nextLine();

                //ensure user input is valid
                while(!intCheck(a)){
                    System.out.println("Sorry, please only enter numerical digits 0-9...");
                    a = scan.nextLine();
                }

                System.out.println("Enter b, please only enter digits 0-9...");
                b = scan.nextLine();

                //ensure user input is valid
                while(!intCheck(b)){
                    System.out.println("Sorry, please only enter numerical digits 0-9...");
                    temp = scan.nextLine();
                }

                //reformat key to proper length
                key = Integer.parseInt(b);
                key %= 26;

                aa = Integer.parseInt(a);
        
                //perform encryption
                System.out.println("Encrypting " + word + ", a is " + aa + " b is " + key); 
                word = affineEncrypt(word, aa, key);
                System.out.println("Encryption: " + word);
            }

            //affine decrypt
            else if(choice == 6){
                //check if user wants to brute force, or if they have a and b ready
                System.out.println("Please enter 1 if you already have a value for a and b, or 0 if you want to brute-force decrypt.");
                brute = scan.nextLine();
                //ensure user input is valid
                while(!binaryIntCheck(brute)){
                    System.out.println("Sorry, please enter 1 if you already have a value for a and b, or 0 if you want to brute-force decrypt.");
                    brute = scan.nextLine();
                }
                bru = Integer.parseInt(brute);

                System.out.println("Enter word or string to decrypt, only entering alphabetic characters a-z.");
                word = scan.nextLine();
                word = word.toLowerCase();
        
                //ensure user input is valid
                while(!strCheck(word, 0, 0)){
                    System.out.println("Sorry, please only enter alphabetical letters a-z.");
                    word = scan.nextLine();
                    word.toLowerCase();
                }
        
                //key ready
                if(bru == 1){
                    System.out.println("Enter a, please only enter digits 0-9...");
                    a = scan.nextLine();
    
                    //ensure user input is valid
                    while(!intCheck(a)){
                        System.out.println("Sorry, please only enter numerical digits 0-9...");
                        a = scan.nextLine();
                    }
    
                    System.out.println("Enter b, please only enter digits 0-9...");
                    b = scan.nextLine();
    
                    //ensure user input is valid
                    while(!intCheck(b)){
                        System.out.println("Sorry, please only enter numerical digits 0-9...");
                        temp = scan.nextLine();
                    }
    
                    //reformat key to proper length
                    key = Integer.parseInt(b);
                    key %= 26;
    
                    aa = Integer.parseInt(a);
            
                    //perform decryption
                    System.out.println("Decrypting " + word + ", a is " + aa + " b is " + key); 
                    word = affineDecrypt(word, aa, key);
                    System.out.println("Decryption: " + word);
                }
                //brute
                else{
                //perform decryption
                temp = word;
                //pass through each valid a value to decrypt method
                for(i = 0; i < validA.length; i++){
                    for(j = 1; j < 26; j++){
                        System.out.println("Decrypting " + temp + ", a is " + validA[i] + " b is " + j); 
                        word = affineDecrypt(temp, validA[i], j);
                        System.out.println("Decryption: " + word);
                        System.out.println();
                    }
                    //if user is done, they can terminate loop
                    System.out.println("Would you like to advance to the next valid A value? Enter 1 to advance or 0 if you are finished decrypting");
                    b = scan.nextLine();
                    //ensure user input is valid
                    while(!binaryIntCheck(b)){
                        System.out.println("Sorry, please enter either 1 if you would like to continue or 0 if you are finished.");
                        b = scan.nextLine();
                    }
                    key = Integer.parseInt(b);
                    if(key == 0){
                        j = 27;
                        i = validA.length + 1;
                    }
                }
            }
        }
        else if(choice == 7){
        //get ciphertext from user, convert to uppercase, store it's length
        System.out.println("Please enter the message you want to decrypt.");
        cipher = scan.nextLine();
        cipher = cipher.toUpperCase();
        len = cipher.length();

        //outermost for loop
        for(i = 0; i < 26; i++){
            for(j = 0; j < 26; j++){
                for(k = 0; k < 26; k++){
                    //innermost for loop
                    for(l = 0; l < 26; l++){
                        //this value will continually get increased for each unique matrix, or 26^4 times
                        matrix[1][1]++;
                        matrix[1][1] %= 26;
                        //reset plain
                        plain = "";
                        //convert ciphertext to plaintext
                        for(m = 0; m< len; m+=2){
                            //System.out.println(i);
                            plain = plain.concat(hillDecrypt(cipher.substring(m, m + 2), matrix));
                        }

                        //reset count
                        count = 0;
                        //go through entirety of top words array
                        for(m = 0; m < words.length; m++){
                            //check if each word is substring of plaintext
                            if(plain.contains(words[m])){
                                count++;
                                //if count reaches above target value, print out cooresponding info and terminate
                                if(count > COUNT){
                                    //print plaintext
                                    System.out.println(plain);
                                    //print key
                                    for(n = 0; n < 2; n++){
                                        for(o = 0; o < 2; o++){
                                            System.out.print(matrix[n][o] + " ");
                                        }
                                        System.out.println();
                                    }
                                    System.out.println("This should be right. If not, increase the count variable in the code, and try again.");
                                    m = words.length;
                                }
                            }
                        }
                    }
                    //this value will get increased every time the innermost value rolls over, or 26^3 times
                    matrix[0][1]++;
                    matrix[0][1] %= 26;
                }
                //this value will get increased 26^2 times
                matrix[1][0]++;
                matrix[1][0] %= 26;
            }
            //the outermost value will increase 26 times
            matrix[0][0]++;
            matrix[0][0] %= 26;
        }
        System.out.println("Message not found. Increase the count variable in the code, and try again.");
        }
    }

        //user is done, close scanner 
        System.out.println("Have a good day!");
        scan.close();
    }
}
