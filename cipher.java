//Mark Dubin, 8/27/20, Cipher Collection
import java.util.Scanner;

//class declaration
public class cipher{
    //menu function, gets user input for what encryption/decryption they want to perform
    public static int menu(){
        String choice;
        int cho;
        Scanner scan = new Scanner(System.in);
        System.out.println("Press 1 to encrypt using Caesar's Cipher, 2 to decrypt using Caesar's Cipher, 3 to encrypt using the Vigenère Cipher, 4 to decrypt using the Vigenère Cipher, 5 to encrpyt using the Affine Cipher, 6 to decrypt using the Affine Cipher, or 7 to exit.");
        choice = scan.nextLine();
        //ensures valid input
        while(!menuCheck(choice)){
            System.out.println("Sorry, please try again. Press 1 to encrypt using Caesar's Cipher, 2 to decrypt using Caesar's Cipher, 3 to encrypt using the Vigenère Cipher, 4 to decrypt using the Vigenère Cipher, 5 to encrpyt using the Affine Cipher, 6 to decrypt using the Affine Cipher, or 7 to exit.");
            choice = scan.nextLine();
        }
        //convert to int and return
        cho = Integer.parseInt(choice);
        scan.close();
        return cho;
    }

    //ensures user input for menu is acceptable
    public static boolean menuCheck(String choice){
        int i;
        for(i = 0; i < choice.length(); i++){
            if(choice.codePointAt(i) < 49 || choice.codePointAt(i) > 55){
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
        int i, j, count = 0, track = 0, temp;

        //matrix construction, using ints to represent letters
        int[][] matrix = new int[26][26];
        for(i = 0; i < 26; i++){
            for(j = 0; j < 26; j++){
                matrix[i][j] = (i + count) % 26;
                count++;
            }
        }

        for(i = 0; i < word.length(); i++){
            //find cooresponding letters numeric values
            count = wor[i] - 'a';
            track = k[i] - 'a';

            //find new letter, reassign
            temp = matrix[count][track];
            wor[i] = alph[temp];
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
        int i, j, count = 0, track = 0, temp;

        //matrix construction, using ints to represent letters
        int[][] matrix = new int[26][26];
        for(i = 0; i < 26; i++){
            for(j = 0; j < 26; j++){
                matrix[i][j] = (i + count) % 26;
                count++;
            }
        }

        for(i = 0; i < word.length(); i++){
            //find cooresponding letters numeric values
            count = wor[i] - 'a';
            track = k[i] - 'a';

            //find row where cipher letter appears
            temp = 0;
            while(matrix[track][temp] != count){
                temp++;
            }

            //reassign
            wor[i] = alph[temp];
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
    
    //main driver function
    public static void main(String[]args){
        int key, choice = 0, aa, bru, i, j;
        Scanner scan = new Scanner(System.in);
        String word, temp, k, a, b, brute;
        int[] validA = {1,3,5,7,9,11,15,17,19,21,23,25};

        //while loop used to continually execute until user is done with program
        System.out.print("Welcome! ");
        while(choice != 7){
            choice = menu();
            //caesar encrypt
            if(choice == 1){
                System.out.println("Enter word or string to encrypt, only entering alphabetic characters a-z.");
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

                System.out.println("Enter key to use for encryption, only entering alphabetic characters a-z. The key should be the same length as the word you want to encrypt");
                k = scan.nextLine();
                k = k.toLowerCase();
        
                //ensure user input is valid
                while(!strCheck(k, 2, word.length())){
                    System.out.println("Sorry, please only enter alphabetical letters a-z. Make sure your key has a length of " + word.length() + ".");
                    k = scan.nextLine();
                    k.toLowerCase();
                }

                //perform encryption
                System.out.println("Encrypting " + word + ", using the key " + k + "."); 
                word = vigenereEncrypt(word, k);
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

                System.out.println("Enter key to use for decryption, only entering alphabetic characters a-z. The key should be the same length as the word you want to encrypt");
                k = scan.nextLine();
                k = k.toLowerCase();
        
                //ensure valid input
                while(!strCheck(k, 2, word.length())){
                    System.out.println("Sorry, please only enter alphabetical letters a-z. Make sure your key has a length of " + word.length() + ".");
                    k = scan.nextLine();
                    k.toLowerCase();
                }

                //perform decryption
                System.out.println("Decrypting " + word + ", using the key " + k + "."); 
                word = vigenereDecrypt(word, k);
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
        
                System.out.println("Enter a, please only enter digits 0-9, and a positive number coprime to 26...");
                a = scan.nextLine();

                //ensure user input is valid
                while(!intCheck(a)){
                    System.out.println("Sorry, please only enter numerical digits 0-9, and a positive number coprime to 26...");
                    a = scan.nextLine();
                }

                System.out.println("Enter b, please only enter digits 0-9, and a positive number coprime to 26...");
                b = scan.nextLine();

                //ensure user input is valid
                while(!intCheck(b)){
                    System.out.println("Sorry, please only enter numerical digits 0-9, and a positive number coprime to 26...");
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
                    System.out.println("Enter a, please only enter digits 0-9, and a positive number coprime to 26...");
                    a = scan.nextLine();
    
                    //ensure user input is valid
                    while(!intCheck(a)){
                        System.out.println("Sorry, please only enter numerical digits 0-9, and a positive number coprime to 26...");
                        a = scan.nextLine();
                    }
    
                    System.out.println("Enter b, please only enter digits 0-9, and a positive number coprime to 26...");
                    b = scan.nextLine();
    
                    //ensure user input is valid
                    while(!intCheck(b)){
                        System.out.println("Sorry, please only enter numerical digits 0-9, and a positive number coprime to 26...");
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
        }

        //user is done, close scanner 
        System.out.println("Have a good day!");
        scan.close();
    }
}
