//Mark Dubin
//Bash Shell
//3/31/21

#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <limits.h>
#include <dirent.h>
#include <errno.h>
#include <stdbool.h>
#include <signal.h>
#include <sys/stat.h>

#define LENGTH 100
#define HFILE "history.txt"

int getNumLines(FILE * fptr);
bool movetodir(char * curToken);
void history(FILE * fptr);
void coppy(FILE * fromPtr, FILE * toPtr);
char * replay(char * curToken, FILE * fptr);
int start(char * path, char * arg, FILE * fptr);
bool checkCommand(char * input, FILE * fptr, char * curDir);

int main(){
    char line[LENGTH];
    char input[LENGTH];
    char startDir[LENGTH];
    char * curDir;

    FILE * fptr;

    //get the starting directory, where the program begins
    if(getcwd(startDir, sizeof(startDir)) != NULL) {
       curDir = startDir;
       strcat(curDir, "/");
   } else{
       perror("getcwd() error");
       return 1;
   }

    //can be read and written into, will create file if one doesn't exist within starting directory
   fptr = fopen(HFILE, "ab+");

    //will continually get commands until byebye is run
    while(strcmp(input, "byebye") != 0){
        printf("#");
        fgets(input, sizeof(input), stdin);

        fseek(fptr, 0, SEEK_END);
        fputs(input, fptr);

        strtok(input, "\n");

        //byebye was run
        if(!checkCommand(input, fptr, curDir)){
            printf("\n");
            fclose(fptr);
            return 0;
        }
    }

    //byebye was run
    printf("\n");
    fclose(fptr);
    return 0;
}

//function to get number of lines in history file
int getNumLines(FILE * fptr){
    char c;
    int numLines = 0;
    fseek(fptr, 0, SEEK_SET);
    for (c = fgetc(fptr); c != EOF; c = fgetc(fptr)){
        if (c == '\n')
            numLines++;
    }
    return numLines;
}

//function to handle movetodir command
bool movetodir(char * curToken){
    //if curToken isn't null, check if it is a valid directory
    if(curToken){
        DIR * newDir = opendir(curToken);
        if(newDir){
            closedir(newDir);
            return true;
        } else{
            printf("ERROR: Invalid Directory. Please try again.\n");
            return false;
        }
        //curToken was null
    } else{
        printf("ERROR: proper use of this command is: movetodir <directory name>\n");
        return false;
    }
    return false;
}

//function to handle history command
void history(FILE * fptr){
    int i;
    int numLines;
    int count;
    char line[LENGTH];

    //gets number of lines within history file
    numLines = getNumLines(fptr);

    count = numLines - 1;
    printf("%d %d\n", numLines, count);

    //prints list of commands in order found in file (couldn't figure out how to do it in reverse)
    fseek(fptr, 0, SEEK_SET);
    for(i = numLines; i > 0; i--){
        printf("%d: %s", count, fgets(line, LENGTH, fptr));
        count--;
    }
    return;
}

//function carries out data transfer portion of coppy command
void coppy(FILE * fromPtr, FILE * toPtr){
    int numLines;
    char buffer[LENGTH];

    //gets number of lines within from file
    numLines = getNumLines(fromPtr);

    //moves all contents of from file into to file
    fseek(fromPtr, 0, SEEK_SET);
    for(int i = numLines; i > 0; i--){
        fprintf(toPtr, "%s", fgets(buffer, LENGTH, fromPtr));
    }
}

//function to handle replay command
char * replay(char * curToken, FILE * fptr){
    int number;
    int numLines;
    int count;
    int i;

    char line[LENGTH];

    //if curToken isn't null
    if(curToken){
        //curToken should be number
        number = atoi(curToken);
        numLines = getNumLines(fptr);
        count = numLines - 1;

        //go to line number passed to function
        fseek(fptr, 0, SEEK_SET);
        for(i = numLines; i > number + 1; i--){
            fgets(line, LENGTH, fptr);
            count--;
        }
        strtok(line, "\n");
        //will return command to execute
        return line;
    }
    else{
        printf("ERROR: proper use of this command is: replay number\n");
    }
    return NULL;
}

//function to handle the start command
int start(char * path, char * arg, FILE * fptr){
    pid_t pid = fork();

    //pid shouldn't be negative
    if(pid < 0){
        wait(NULL);
        printf("Fork Failed");
        return 1;
    //no argument was passed with program
    } else if(pid == 0 && !arg){
        if(execlp(path, NULL, NULL) == -1){
            exit(pid);
        }
    //argument was passed with program
    } else if(pid == 0){
        if(execlp(path, arg, NULL) == -1){
            exit(pid);
        }
    //will wait until child terminates
    } else{
        wait(NULL);
    }
    return pid;
}

//function to check which command was run
bool checkCommand(char * input, FILE * fptr, char * curDir){
    char * curToken;
    char * line;
    char * path;
    char * arg;
    char * fromFile;
    char * toFile;
    char buffer[LENGTH];
    char replayLine[LENGTH];

    pid_t curPID;
    pid_t killPID;

    //get command name
    curToken = strtok(input, " ");

    //movetodir
    if(strcmp(curToken, "movetodir") == 0){
        //get proposed directory path
        curToken = strtok(NULL, " ");
        //if valid, update curDir
        if(movetodir(curToken)){
            strcpy(curDir, curToken);
        }
        return true;
    } 
    //whereami, simply print curDir
    else if(strcmp(curToken, "whereami") == 0){
        printf("%s\n", curDir);
        return true;
    } 
    //history
    else if(strcmp(curToken, "history") == 0){
        //check if -c argument is present
        curToken = strtok(NULL, " ");
        //if it is, close ptr, reopen in w mode to clear file, close and reopen again in proper mode 
        if(curToken && strcmp(curToken, "-c") == 0){
            fclose(fptr);
            fptr = fopen(HFILE, "w");
            fclose(fptr);
            fptr = fopen(HFILE, "ab+");
            fseek(fptr, 0, SEEK_END);
            fputs("history -c\n", fptr);
        }
        //if not -c, go to history function
        else{
            history(fptr);
        }
        return true;
    }
    //replay
    else if(strcmp(curToken, "replay") == 0){
        //get next token, copy into line
        curToken = strtok(NULL, " ");
        strcpy(replayLine, replay(curToken, fptr));
        //if line isn't null, rerun checkCommand, return false if replay was byebye
        if(replayLine && !checkCommand(replayLine, fptr, curDir)){
            return false;
        }
        return true;
    }
    //start
    else if(strcmp(curToken, "start") == 0){
        curToken = strtok(NULL, "\n");
        path = strtok(curToken, " ");
        arg = strtok(NULL, " ");
        curPID = start(path, arg, fptr);
        return true;
    }
    //background, print PID of current process
    else if(strcmp(curToken, "background") == 0){
        printf("%d\n", curPID);
    }
    //byebye, return false
    else if(strcmp(curToken, "byebye") == 0){
        return false;
    }
    //killPID
    else if(strcmp(curToken, "dalek") == 0){
        //get next token, convert to int
        curToken = strtok(NULL, " ");
        killPID = atoi(curToken);
        //if kill returns -1, it fails to kill process, if not, it succeeds 
        if(kill(killPID, SIGKILL) == -1){
            printf("Failure\n");
        } else{
            printf("Success\n");
        }
    }
    //dwelt
    else if(strcmp(curToken, "dwelt") == 0){
        //get inputted file name, copy current path to it
        curToken = strtok(NULL, "\n");
        char string[LENGTH];
        strcpy(string, curDir); 
        strcat(string, curToken);   

        //try opening as directory first    
        DIR * testDir = opendir(string);
        if(testDir){
            closedir(testDir);
            printf("Abode is.\n");
            //if not directory, try accessing file
        } else{
            if( access(string, F_OK) == 0 ) {
                printf("Dwelt indeed.\n");
            } else{
                printf("Dwelt not.\n");
            }
        }
    }
    //maik
    else if(strcmp(curToken, "maik") == 0){
        //get inputted file name, copy current path to it
        curToken = strtok(NULL, "\n");
        char string[LENGTH];
        strcpy(string, curDir); 
        strcat(string, curToken);
        FILE * tPtr;      

        //if can be accessed, already exists
        if(access(string, F_OK) == 0) {
            printf("ERROR: File already exists!\n");
            //if not create new file, add 'Draft' to it
        } else{
            tPtr = fopen(string, "ab+");
            fprintf(tPtr, "Draft\n");
            fclose(tPtr);
            printf("File created.\n");
        }
    }
    //coppy
    else if(strcmp(curToken, "coppy") == 0){
        FILE * fromPtr, * toPtr; 
        char fromString[LENGTH];
        char toString[LENGTH];

        //get both from and to file names, copy paths to them
        curToken = strtok(NULL, "\n");
        fromFile = strtok(curToken, " ");
        toFile = strtok(NULL, " ");

        strcpy(fromString, curDir); 
        strcat(fromString, fromFile);
        strcpy(toString, curDir); 
        strcat(toString, toFile); 

        //make sure from file exists, to file doesn't
        if(access(fromString, F_OK) != 0) {
            printf("ERROR: Source file doesn't exists!\n");
        } else if(access(toString, F_OK) == 0){
            printf("ERROR: Destination file already exists!\n");
        }
        else{
            //open from file, create to file
            fromPtr = fopen(fromString, "ab+");
            toPtr = fopen(toString, "ab+");

            //data transfer is carried out in function
            coppy(fromPtr, toPtr);

            fclose(fromPtr);
            fclose(toPtr);
            printf("Copied contents of %s into newly created file %s.\n", fromFile, toFile);
        }
    }
    return true; 
}
