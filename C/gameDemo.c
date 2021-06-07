//Mark Dubin
//RPG Game Demo
//9/18/2020

//includes
#include <stdio.h>
#include <time.h>
#include <stdlib.h>
#include <math.h>
#include <string.h>
#include <ctype.h>

//constant definitions
#define ATTRIBUTES 6
#define NAME_LENGTH 10
#define MAX_LVL 1

//character struct
typedef struct{
    int max_health;
    int cur_health;
    int attack;
    int def;
    int crit;
    char * name;
    int lvl;
    }character;

//function definitions
int init(void);
int char_select(void);
character char_create(int ch, char * name);
character en_create(int lvl);
void lvl_select(character user);
int lvl_one(character user);
int action_menu(int lvl);
void display_health(character temp);
char * char_name();
character load_save(FILE * ifp);

//global debug var
int debug = 1;

//main driver function
int main(int argc, const char * argv[]){
    //seed rng
    srand(time(0));
    //declare user character, various other vars
    character user;
    int mode, flag = 1, lvl = 0, choice, size, ch;
    char line[100];
    char * name;

    //run initialization function, get output to know whether to start new game or load previous
    mode = init();
    //mode is 1, load
    if(mode == 1){
        //create new character, get their name
        ch = char_select();
        name = char_name();
        //assign user character to preferences
        user = char_create(ch, name);
        //if debug is 1, print stats
        if(debug == 1)
            printf("mh: %d ch: %d a: %d d: %d c: %d name: %s\n", user.max_health, user.cur_health, user.attack, user.def, user.crit, user.name);
        //send user to lvl one
        lvl_select(user);
    }
    //user wants to load save file
    else if(mode == 2){
        //where attributes will be stored
        int attributes[ATTRIBUTES], i;
        char name[NAME_LENGTH];
        printf("Loading saved game...\n");

        //open ifp
        FILE * ifp = fopen("saves.txt", "r");
        if (NULL != ifp){
            fseek(ifp, 0, SEEK_END);
            size = (int)ftell(ifp);
            //if file is empty, terminate
            if (0 == size){
                printf("There is no saved data. Program is now terminating. Please restart the program, and select New Game at the prompt.\n");
                return -1;
            }
        }
        //if ifp is null, terminate
        else{
          printf("There is no saved data. Program is now terminating. Please restart the program, and select New Game at the prompt.\n");
                return -1;  
        }

        //read in most of user data
        user = load_save(ifp);

        //read in username, close ifp
        fscanf(ifp, "%s", name);
        user.name = name;
        fclose(ifp);

        if(debug == 1)
            printf("mh: %d ch: %d a: %d d: %d c: %d name: %s\n", user.max_health, user.cur_health, user.attack, user.def, user.crit, user.name);
        printf("Saved game loaded! Returning to level %d.\n", user.lvl);
        //bring user back to their lvl
        lvl_select(user);
    }
    
    return 0;
}

//initialization function, returns whether user wants to start new game or load save
int init(){
    int choice = -1;
    //read-in buffer
    char line[100];
    printf("Welcome to the game! Please select the number cooresponding to the following:\n1: New Game\n2: Load Game\n");
    printf("Enter your selection: ");
    //read in from keyboard
    fgets(line, sizeof(line), stdin);
    sscanf(line, "%d", &choice);
    //ensures valid input
    while(choice > 2 || choice < 1){
        printf("Invalid choice, try again. Enter 1 to start a new game, or 2 to load a previously saved game. Enter your selection: ");
        fgets(line, sizeof(line), stdin);
        sscanf(line, "%d", &choice);
    }
    return choice;
}

//returns what kind of character user wants
int char_select(){
    int choice = -1;
    //buffer
    char line[100];
    printf("\nFirst time huh...alright. Pick your character.\n1: An all-around\n2: A defensive tank\n3: An offensive powerhouse\n");
    printf("Enter your selection: ");
    //read input from keyboard
    fgets(line, sizeof(line), stdin);
    sscanf(line, "%d", &choice);
    //ensures valid input
    while(choice > 3 || choice < 1){
        printf("Invalid choice, try again. Enter an integer 1-3 to select your character: ");
        fgets(line, sizeof(line), stdin);
        sscanf(line, "%d", &choice);
    }
    return choice;
}

//returns valid name of user's character
char * char_name(){
    int i = 0, len;
    //buffer
    char line[100];
    printf("And what will the name of your fighter be?\n");
    //get input from keyboard
    fgets(line, sizeof(line), stdin);
    len = strlen(line);
    //allocate appropriate amount of space for name
    char * choice = (char*)malloc((len-1)*sizeof(char));
    for(i = 0; i < len-1; i++){
        choice[i] = line[i];
    }
    /*for(i = 0; i < len-1; i++){
        printf("%c\n", choice[i]);
    }
    printf("%s %lu %d\n", choice, sizeof(choice), len-1);*/
    //ensures input is valid
    for(i = 0; i < len-1; i++){
        printf("%c", choice[i]);
        if(!isspace(choice[i]) && !isalpha(choice[i])){
        printf("Invalid choice, try again. Enter an string with only valid characters a-z and A-Z.");
        fgets(line, sizeof(line), stdin);
        sscanf(line, "%s", choice);
        i = 0;
        }
    }
    return choice;
}

//returns new character based on user's choice
character char_create(int ch, char * name){
    character temp;
    //all-around
    if(ch == 1){
        temp.max_health = 50;
        temp.cur_health = 50;
        temp.attack = 10;
        temp.def = 6;
        temp.crit = 7;
        temp.name = name;
        temp.lvl = 1;
    }
    //defensive
    else if(ch == 2){
        temp.max_health = 75;
        temp.cur_health = 75;
        temp.attack = 7;
        temp.def = 10;
        temp.crit = 6;
        temp.name = name;
        temp.lvl = 1;
    }
    //offensive
    else if(ch == 3){
        temp.max_health = 30;
        temp.cur_health = 30;
        temp.attack = 12;
        temp.def = 4;
        temp.crit = 9;
        temp.name = name;
        temp.lvl = 1;
    }
    return temp;
}

//returns enemy for user to fight based on lvl
character en_create(int lvl){
    character temp;
    //lvl one
    if(lvl == 1){
        temp.max_health = 20;
        temp.cur_health = 20;
        temp.attack = 10;
        temp.def = 4;
        temp.crit = 5;
        temp.name = "Midget";
    }
    
    return temp;
}

//sends user to proper lvl
void lvl_select(character user){
    char line[100];
    int flag = 1, choice, success;
    while(flag == 1){
            //send user to lvl
            if(user.lvl == 1)
                success = lvl_one(user);
            //if user loses, terminate
            if(success == 0){
                printf("You lost! Try again!");
                return;
            }
            //level beaten
            printf("Congrats on beating level %d!\n", user.lvl);
            //advance lvl, assign to user
            user.lvl++;
            //save option
            printf("\nWould you like to save your gane? Press 1 to save, or 2 to skip saving.\n");
            fgets(line, sizeof(line), stdin);
            sscanf(line, "%d", &choice);
            printf("%d\n", choice);
            //ensure valid input
            while(choice < 1 || choice > 2){
                printf("Invalid choice, try again. Enter 1 to save your game, or 2 to skip saving. Enter your selection: ");
                fgets(line, sizeof(line), stdin);
                sscanf(line, "%d", &choice);
            }
            //save 
            if(choice == 1){
                printf("Saving game...\n");
                //open file, read in user info in specific order, close pointer
                FILE * ofp = fopen("saves.txt", "w");
                fprintf(ofp, "%d %d %d %d %d %d %s", user.max_health, user.cur_health, user.attack, user.def, user.crit, user.lvl, user.name);
                fclose(ofp);
                printf("Game saved!\n");
                choice = 0;
            }
            //determines whether user wants to keep playing or not
            printf("Would you like to keep playing, or stop? Press 1 to continue to the next level, or 2 to take a break.\n");
            fgets(line, sizeof(line), stdin);
            sscanf(line, "%d", &choice);
            //ensures valid input
            while(choice < 1 || choice > 2){
                printf("Invalid choice, try again. Enter 1 to save your game, or 0 to skip saving. Enter your selection: ");
                fgets(line, sizeof(line), stdin);
                sscanf(line, "%d", &choice);
            }
            //user is done, terminate
            if(choice == 2){
                printf("Thanks for playing! See you soon!\n");
                return;
            }
            //if user has played every possible lvl, quit
            if(user.lvl > MAX_LVL){
                printf("Thanks for playing the demo! Hope you had fun!\n");
                return;
            }
            if(debug == 1)
                return;
            flag = 0;
        }
}

//displays health of given character
void display_health(character temp){
    printf("%s's health: %d/%d\n", temp.name, temp.cur_health, temp.max_health);
}

//plays level one, returns whether user won or lost
int lvl_one(character user){
    int flag = 0, choice, attack = 0, defense = 0;
    //create enemy
    character midget = en_create(1);
    //prints stats if debug is one
    if(debug == 1)
        printf("mh: %d ch: %d a: %d d: %d c: %d name: %s\n", midget.max_health, midget.cur_health,  midget.attack, midget.def, midget.crit, midget.name);

    //narration
    printf("\nWelcome %s! This is level one! Here, you must defeat this midget!\n", user.name);
    printf("After eyeballing you for a bit, the midget snarles and charges!\n");
    printf("\nYou are now in the action menu. Here, you currently have the choice to either attack or defend. As you progress through the game, more options will become available.\n");

    //runs until someone dies
    while(user.cur_health > 0 && midget.cur_health > 0){
        //displays health at the beginning of each sequence
        display_health(user);
        display_health(midget);

        //takes user to action menu
        choice = action_menu(1);
        //user wants to attack
        if(choice == 1){
            attack = user.attack;
            //crit determination
            if(rand() % 100 <= user.crit){
                printf("Critical!\n");
                attack *= 2;
            }
            defense = midget.def;
            //determines how much damange to do
            midget.cur_health -= (int)(pow(attack, 2) / pow(defense, 2));
            printf("\nYou dealt %d damage to the midget.\n", (int)(pow(attack, 2) / pow(defense, 2)));
        }
        //user wants to defend
        else if(choice == 2){
            defense = user.def + 2;
        }
        
        //midget attacks if not dead
        if(midget.cur_health > 0){
            printf("\nAs the midget prepares to strike, he screams: \"Alright! I'm gonna hit you as hard as I can!\"\n");
            attack = midget.attack;

            //crit chance
            if(rand() % 100 <= midget.crit){
                printf("Critical!\n");
                attack *= 2;
            }
            //if user didn't defend, use this for calculation
            if(choice != 2)
                defense = user.def;

                //determine how much damage user takes
            user.cur_health -= (int)(pow(attack, 2) / pow(defense, 2));
            printf("\nThe midget hit you for %d damage.\n", (int)(pow(attack, 2) / pow(defense, 2)));
            
            //if(debug == 1)
                //midget.cur_health = 0;
        }
        //if midget died, print defeat message
        else
            printf("Damn you %s!\n", user.name);
    }

    //shows user won
    if(midget.cur_health <= 0)
        flag = 1;
    
    if(flag == 1)
        printf("Congratulation, you've successfully defeated the midget!\n");
    else
        printf("You really somehow managed to die against a damn midget...\n");
    
    return flag;
}

//returns what action user wants to take
int action_menu(int lvl){
    int choice;
    //buffer
    char line[100];
    if(lvl == 1){
        printf("\n1: Attack. Hit the enemy as hard as you can.\n");
        printf("2: Defend. Brace for the enemy's attack, increase your defense for this turn.\n");
        printf("What will you do? Input the number of the action you want to do: ");
        //read input from keyboard
        fgets(line, sizeof(line), stdin);
        sscanf(line, "%d", &choice);
        //ensure valid input
        while(choice < 1 || choice > 2){
            printf("Invalid input, try again. Enter 1 to attack, or 2 to defend: ");
            fgets(line, sizeof(line), stdin);
            sscanf(line, "%d", &choice);
        }
    }
    return choice;
}

//returns character loaded from save file
character load_save(FILE * ifp){
    character user;
    int i, size, attributes[ATTRIBUTES];
    char name[NAME_LENGTH];

        //return to beginning of file if file has contents, scan in all data
        rewind(ifp);
        for(i = 0; i < ATTRIBUTES; i++){
            fscanf(ifp, "%d", &attributes[i]);
        }
        
        //assign attributes properly
        user.max_health = attributes[0];
        user.cur_health = attributes[1];
        user.attack = attributes[2];
        user.def = attributes[3];
        user.crit = attributes[4];
        user.lvl = attributes[5];
        
        return user;
}
