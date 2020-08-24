//Mark Dubin, 8/23/20, Cipher Collection
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

    //encryption method for Caesar's Cipher
    public static String caesarEncrypt(String word, int key){
        //alphabet char array used for reference, char array temp used to perform shift
        char[] alph = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'}, temp = word.toCharArray();
        int i, count = 0;
        for(i = 0; i < word.length(); i++){
            //these chars are allowed, but don't get shifted, so skip interior during this iteration
            if(temp[i] != ' ' && temp[i] != '.' && temp[i] != '!' && temp[i] != '?' && temp[i] != '\'' && temp[i] != '"' && temp[i] != ',' && temp[i] != ';'){
                //find numeric value of letter
                count = 0;
                while(temp[i] != alph[count]){
                    count++;
                }
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
                count = 0;
                while(temp[i] != alph[count]){
                    count++;
                }
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
            count = 0;
            while(wor[i] != alph[count]){
                count++;
            }
            track = 0;
            while(k[i] != alph[track]){
                track++;
            }

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
            count = 0;
            while(wor[i] != alph[count]){
                count++;
            }
            track = 0;
            while(k[i] != alph[track]){
                track++;
            }

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
                count = 0;
                while(temp[i] != alph[count]){
                    count++;
                }
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

    public static String affineDecrypt(String word, int a, int b){
        //alphabet char array used for reference, char array temp used to perform shift
        char[] alph = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'}, temp = word.toCharArray();
        int i, count = 0, result;
        for(i = 0; i < word.length(); i++){
            //these chars are allowed, but don't get shifted, so skip interior during this iteration
            if(temp[i] != ' ' && temp[i] != '.' && temp[i] != '!' && temp[i] != '?' && temp[i] != '\'' && temp[i] != '"' && temp[i] != ',' && temp[i] != ';'){
                //find numeric value of letter
                count = 0;
                while(temp[i] != alph[count]){
                    count++;
                }
                //perform shift based on a and b, reassign
                a = 1 / a;
                result = ((a * count) - b) % 26;
                temp[i] = alph[result];
            }
        }

        //reformat char array into string, return
        String ret = new String(temp);
        word = ret;
        return word;
    }
    
    //main driver function
    public static void main(String[]args){
        int key, choice = 0, aa;
        Scanner scan = new Scanner(System.in);
        String word, temp, k, a, b;

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
                System.out.println("Enter word or string to decrypt, only entering alphabetic characters a-z.");
                word = scan.nextLine();
                word = word.toLowerCase();
        
                //ensure user input is valid
                while(!strCheck(word, 0, 0)){
                    System.out.println("Sorry, please only enter alphabetical letters a-z.");
                    word = scan.nextLine();
                    word.toLowerCase();
                }
        
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
                System.out.println("Enter word or string to decrypt, only entering alphabetic characters a-z.");
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
        
                //perform decryption
                System.out.println("Decrypting " + word + ", a is " + aa + " b is " + key); 
                word = affineDecrypt(word, aa, key);
                System.out.println("Decryption: " + word);
            }
        }

        //user is done, close scanner 
        System.out.println("Have a good day!");
        scan.close();
    }
}
