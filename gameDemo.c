//Mark Dubin
//RPG Game Demo
//9/17/2020

#include <stdio.h>
#include <time.h>
#include <stdlib.h>
#include <math.h>
#include <string.h>

#define ATTRIBUTES 6
#define NAME_LENGTH 10

typedef struct{
    int max_health;
    int cur_health;
    int attack;
    int def;
    int crit;
    char * name;
    int lvl;
    }character;

int init(void);
int char_select(void);
character char_create(int ch);
character en_create(int lvl);
void lvl_select(int lvl, character user);
int lvl_one(character user);
int action_menu(int lvl);
void display_health(character temp);

int debug = 1;


int main(int argc, const char * argv[]){
    srand(time(0));
    character user;
    int mode = init(), flag = 1, lvl = 0, choice, size;
    char line[100];

    
    if(mode == 1){
        int ch = char_select();
        user = char_create(ch);
        if(debug == 1)
            printf("mh: %d ch: %d a: %d d: %d c: %d name: %s\n", user.max_health, user.cur_health, user.attack, user.def, user.crit, user.name);
        while(flag == 1){
            lvl_select(++lvl, user);
            printf("Congrats on beating level %d!\n", lvl);
            lvl++;
            user.lvl = lvl;
            printf("\nWould you like to save your gane? Press 1 to save, or 2 to skip saving.\n");
            fgets(line, sizeof(line), stdin);
            sscanf(line, "%d", &choice);
            printf("%d\n", choice);
            while(choice < 1 || choice > 2){
                printf("Invalid choice, try again. Enter 1 to save your game, or 2 to skip saving. Enter your selection: ");
                fgets(line, sizeof(line), stdin);
                sscanf(line, "%d", &choice);
            }
            if(choice == 1){
                printf("Saving game...\n");
                FILE * ofp = fopen("saves.txt", "w");
                fprintf(ofp, "%d %d %d %d %d %d %s", user.max_health, user.cur_health, user.attack, user.def, user.crit, user.lvl, user.name);
                fclose(ofp);
                printf("Game saved!\n");
                choice = 0;
            }
            printf("Would you like to keep playing, or stop? Press 1 to continue to the next level, or 2 to take a break.\n");
            fgets(line, sizeof(line), stdin);
            sscanf(line, "%d", &choice);
            while(choice < 1 || choice > 2){
                printf("Invalid choice, try again. Enter 1 to save your game, or 0 to skip saving. Enter your selection: ");
                fgets(line, sizeof(line), stdin);
                sscanf(line, "%d", &choice);
            }
            if(choice == 2){
                printf("Thanks for playing! See you soon!\n");
                return 0;
            }
            if(debug == 1)
                return 0;
            flag = 0;
        }
    }
    else if(mode == 2){
        int attributes[ATTRIBUTES], i;
        char name[NAME_LENGTH];
        printf("Loading saved game...\n");
        FILE * ifp = fopen("saves.txt", "r");
        if (NULL != ifp){
            fseek(ifp, 0, SEEK_END);
            size = (int)ftell(ifp);
            if (0 == size) {
                printf("There is no saved data. Program is now terminating. Please restart the program, and select New Game at the prompt.\n");
                return -1;
            }
        }
        rewind(ifp);
        for(i = 0; i < ATTRIBUTES; i++){
            fscanf(ifp, "%d", &attributes[i]);
        }
        
        user.max_health = attributes[0];
        user.cur_health = attributes[1];
        user.attack = attributes[2];
        user.def = attributes[3];
        user.crit = attributes[4];
        user.lvl = attributes[5];
        
        fscanf(ifp, "%s", name);
        printf("%s\n", name);
        user.name = name;
        printf("%s\n", user.name);
        fclose(ifp);
        if(debug == 1)
            printf("mh: %d ch: %d a: %d d: %d c: %d name: %s\n", user.max_health, user.cur_health, user.attack, user.def, user.crit, user.name);
        printf("name: %s\n", user.name);
        printf("Saved game loaded! Returning to level %d.\n", user.lvl);
        lvl = user.lvl - 1;
        while(flag == 1){
            lvl_select(++lvl, user);
            if(flag == 1){
                printf("Congrats on beating level %d! Now saving game...\n", lvl);
                lvl++;
                user.lvl = lvl;
                printf("\nWould you like to save your gane? Press 1 to save, or 2 to skip saving.\n");
                fgets(line, sizeof(line), stdin);
                sscanf(line, "%d", &choice);
                printf("%d\n", choice);
                while(choice < 1 || choice > 2){
                    printf("Invalid choice, try again. Enter 1 to save your game, or 2 to skip saving. Enter your selection: ");
                    fgets(line, sizeof(line), stdin);
                    sscanf(line, "%d", &choice);
                }
                if(choice == 1){
                    printf("Saving game...\n");
                    FILE * ofp = fopen("saves.txt", "w");
                    fprintf(ofp, "%d %d %d %d %d %d %s", user.max_health, user.cur_health, user.attack, user.def, user.crit, user.lvl, user.name);
                    fclose(ofp);
                    printf("Game saved!\n");
                    choice = 0;
                }
                printf("Would you like to keep playing, or stop? Press 1 to continue to the next level, or 2 to take a break.\n");
                fgets(line, sizeof(line), stdin);
                sscanf(line, "%d", &choice);
                while(choice < 1 || choice > 2){
                    printf("Invalid choice, try again. Enter 1 to save your game, or 0 to skip saving. Enter your selection: ");
                    fgets(line, sizeof(line), stdin);
                    sscanf(line, "%d", &choice);
                }
                if(choice == 2){
                    printf("Thanks for playing! See you soon!\n");
                    return 0;
                }
                if(debug == 1)
                    return 0;
                flag = 0;
            }
        }
    }
    
    return 0;
}

int init(){
    int choice;
    char line[100];
    printf("Welcome to the game! Please select the number cooresponding to the following:\n1: New Game\n2: Load Game\n");
    printf("Enter your selection: ");
    fgets(line, sizeof(line), stdin);
    sscanf(line, "%d", &choice);
    while(choice > 2 || choice < 1){
        printf("Invalid choice, try again. Enter 1 to start a new game, or 2 to load a previously saved game. Enter your selection: ");
        fgets(line, sizeof(line), stdin);
        sscanf(line, "%d", &choice);
    }
    return choice;
}

int char_select(){
    int choice;
    char line[100];
    printf("\nFirst time huh...aight. Pick your character.\n1: Mark, an all-around\n2: Marc, an absolute unit\n3: Chad, SUPER STRONG\n");
    printf("Enter your selection: ");
    fgets(line, sizeof(line), stdin);
    sscanf(line, "%d", &choice);
    while(choice > 3 || choice < 1){
        printf("Invalid choice, try again. Enter an integer 1-3 to select your character: ");
        fgets(line, sizeof(line), stdin);
        sscanf(line, "%d", &choice);
    }
    return choice;
}

character char_create(int ch){
    character temp;
    if(ch == 1){
        temp.max_health = 50;
        temp.cur_health = 50;
        temp.attack = 10;
        temp.def = 6;
        temp.crit = 7;
        temp.name = "Mark";
    }
    else if(ch == 2){
        temp.max_health = 75;
        temp.cur_health = 75;
        temp.attack = 7;
        temp.def = 10;
        temp.crit = 6;
        temp.name = "Marc";
    }
    else if(ch == 3){
        temp.max_health = 30;
        temp.cur_health = 30;
        temp.attack = 12;
        temp.def = 4;
        temp.crit = 9;
        temp.name = "Chad";
    }
    return temp;
}

character en_create(int lvl){
    character temp;
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

void lvl_select(int lvl, character user){
    if(lvl == 1)
        lvl_one(user);
}

void display_health(character temp){
    printf("%s's health: %d/%d\n", temp.name, temp.cur_health, temp.max_health);
}

int lvl_one(character user){
    int flag = 0, choice, attack = 0, defense = 0;
    character midget = en_create(1);
    if(debug == 1)
        printf("mh: %d ch: %d a: %d d: %d c: %d name: %s\n", midget.max_health, midget.cur_health,  midget.attack, midget.def, midget.crit, midget.name);
    printf("\nWelcome %s! This is level one! Here, you must defeat this midget!\n", user.name);
    printf("After eyeballing you for a bit, the midget snarles and charges!\n");
    printf("\nYou are now in the action menu. Here, you currently have the choice to either attack or defend. As you progress through the game, more options will become available.\n");
    while(user.cur_health > 0 && midget.cur_health > 0){
        display_health(user);
        display_health(midget);
        choice = action_menu(1);
        if(choice == 1){
            attack = user.attack;
            if(rand() % 100 <= user.crit){
                printf("Critical!\n");
                attack *= 2;
            }
            defense = midget.def;
            midget.cur_health -= (int)(pow(attack, 2) / pow(defense, 2));
            printf("\nYou dealt %d damage to the midget.\n", (int)(pow(attack, 2) / pow(defense, 2)));
        }
        else if(choice == 2){
            defense = user.def + 2;
        }
        
        if(midget.cur_health > 0){
            printf("\nAs the midget prepares to strike, he screams: \"Alright! I'm gonna hit you as hard as I can!\"\n");
            attack = midget.attack;
            if(rand() % 100 <= midget.crit){
                printf("Critical!\n");
                attack *= 2;
            }
            if(choice != 2)
                defense = user.def;
            user.cur_health -= (int)(pow(attack, 2) / pow(defense, 2));
            printf("\nThe midget hit you for %d damage.\n", (int)(pow(attack, 2) / pow(defense, 2)));
            
            //if(debug == 1)
                //midget.cur_health = 0;
        }
        else
            printf("Damn you %s!\n", user.name);
    }
    if(midget.cur_health <= 0)
        flag = 1;
    
    if(flag == 1)
        printf("Congratulation, you've successfully defeated the midget!\n");
    else
        printf("F. You really somehow managed to die against a damn midget...\n");
    
    return flag;
}

int action_menu(int lvl){
    int choice;
    char line[100];
    if(lvl == 1){
        printf("\n1: Attack. Hit the enemy as hard as you can.\n");
        printf("2: Defend. Brace for the enemy's attack, increase your defense for this turn.\n");
        printf("What will you do? Input the number of the action you want to do: ");
        fgets(line, sizeof(line), stdin);
        sscanf(line, "%d", &choice);
        while(choice < 1 || choice > 2){
            printf("Invalid input, try again. Enter 1 to attack, or 2 to defend: ");
            fgets(line, sizeof(line), stdin);
            sscanf(line, "%d", &choice);
        }
    }
    return choice;
}
