import java.util.Random;
import java.util.Scanner;
import java.io.IOException;
import java.time.Year;
import java.util.Random;

public class JerrysAdventure {
private static Scanner scanner;
    public static void main(String[] args) {
        // ----- Correct the code below
        type(
        "        __                     _         ___       __                 __ \n"
        + "       / /__   ____________  _( )_____  /   | ____/ /   _____  ____  /  /___ __________ \n"
        + "  __  / / _ \\ / ___/ ___/ / / /// ___/ / /| |/ __  / | / / _ \\/ __ \\/ __/ / / / ___/ _ \\\n"
        + " / /_/ / __/ / /  / /  / /_/ / (__  ) / ___ / /_/ /| |/ /  __/ / / / /_/ /_/ / /  /  __/ \n"
        + " \\____/\\___//_/  /_/   \\__, / /____/ /_/  |_\\__,_/ |___/\\___/_/ /_/\\__/\\__,_/_/   \\___/ \n"
        + "                      /____/ \n\nby: Minseok Choi\n",5);
        // ----- Correct the code above
        scanner = new Scanner(System.in);
        // ----- Write your code below
        System.out.println("What is your name?: ");
        String player_name = scanner.nextLine();
        // System.out.println("Hi "+ player_name+"! What is birth year?: ");
        // int birth_year = scanner.nextInt();
        // System.out.println("You are "+ calculateAge(birth_year)+ "years old.");
        int typeSpeed = 20; //I can adjust the speed of typing effect by changing this value
        int [][] dungeonMap = {   //I changed stateId into a map instead so the stage doesn't go incremental
                                //player can choose which stage to go, so it's more adventurous
            {777,0,3,11,333},
            {11,33,0,1,0},
            {11,0,77,0,0},
            {0,1,0,1,33},
            {77,11,0,111,7}
        };  // in this map, the player will be able to encounter three phases. map that has 1 means encountering a monster. 3 means upgrading status, 7 means finding a treasure. more number means stronger monster, better rewards.

        System.out.println("\nType anything to move on...");
        scanner.nextLine();
        clearScreen();

        //The game starts from here
        
        type("Welcome to the dungeon "+ player_name+". You are an apprentice of top tier adventurer, Jerry. Your task is to find the Dungeon boss and clear the dungeon to become a master adventurer like Jerry. The dungeon is made of a 5x5 grid and you will be dropped at a random point of a deep dark dungeon. As you move around, you will encounter a monster, or find a weapon to upgrade your status, or find a treasure. Your goal is to clear the dungeon but also to gain as much treasure to become rich. Becareful to not get into the wrong direction as the monsters will respawn and you might encounter it again. Now your adventure starts! \n\nType anything to go next...", typeSpeed);


        Random rnd = new Random();          //Creating a random grid to drop a player inside a dungeon
        int playerRow = rnd.nextInt(5); 
        int playerCol = rnd.nextInt(5);

        int player_attack = 2, gold = 0, player_hp = 15;   //setting up a player status in the beginning (This might change depending on the balance)
        int max_hp = 10; //This will change when the player gets a new treasure!

        int treasure = 10; //level one treasure will be worth of 10 gold.

        while(true){ // get into the game loop, so maybe when player restarts, come back here
            //first checking which grid is the player at. Does the grid have a monster? a weapon? or a treasure?
            int dungeonState = dungeonMap[playerRow][playerCol];
            System.out.flush();;    //Every time the map changes, it will comeback to this point.
            scanner.nextLine();     //making sure that every text is printed and seen by player before moving on
            clearScreen();          //clearing screen to have clear interface 

            type("Your current hp: "+player_hp+" attack damage: "+player_attack+" gold: "+gold, typeSpeed);
            System.out.println("You are in a dungeon state of: ["+ (playerRow+1)+" , "+(playerCol+1)+"]\n");
            if(dungeonState!=0){    //so Checking here if dungeon has anything
                int level = checkLevelOfDungeon(dungeonState,0);
                System.out.println("The level of dungeon is: "+ level+"\n");

                if(dungeonState%7==0){  //if the dungeonMap contains 7 then it is a room with a treasure
                    type("Lucky you! You are in a treasure room! You will gain "+ level*treasure + "gold!",typeSpeed);
                    gold += level*treasure;
                    dungeonMap[playerRow][playerCol] = 0;   //after taking the treasure, the dungeonMap value changes so the treasure will be gone second time player comes to this room.
                }else if(dungeonState%3==0){ //if the dungeonMap contains 3 then it is a room with a weapon to upgrade
                    //I implemented a randomness whether to upgrade hp or attack of the player
                    switch (rnd.nextInt(2)) {
                        case 0:
                            type("Congratulations! You found a new armor and a potion! Your hp will be healed up and will be increased to "+max_hp*(level+1),typeSpeed);
                            max_hp += 10*level;    //player will heal up all hp and the will have more hp.
                            player_hp = max_hp; //calculated with max_hp because player_hp might be reduced due to a fight
                            break;
                        case 1: 
                           type("Congratulations! You found a new Weapon! Your attack damage will now increase to "+ (player_attack+3*level),typeSpeed);
                            player_attack += 3*level;
                            break;
                    }
                    dungeonMap[playerRow][playerCol] = 0; //after taking weapon, player will not be able to get it again when come back next time
                }else{  //when the dungeonMap has 1, means encountering a monster
                    if(level==1){   //if player meets a normal monster
                        type("You encountered a goblin!",typeSpeed);
                        player_hp = encounterMonster(level, player_attack, player_hp);
                    }else if(level==2){ //if player meets lvl 2 monster
                        type("You encountered a goblin warrior!!",typeSpeed);
                        player_hp = encounterMonster(level, player_attack, player_hp);
                    }else{ //if the player encounters a boss
                        type("You encountered a Goblin Boss!!!",typeSpeed);
                        player_hp = encounterMonster(level, player_attack, player_hp);
                        if(player_hp>0){
                            clearScreen();
                            type("\n CONGRATULATION! YOU COMPLETED THE QUEST!\nNOW YOU ARE A MASTER ADVENTURER!!",300);
                            type("YOU GAINED "+ gold+"GOLD THROUGH OUT YOUR ADVENTURE! THIS IS ALL YOURS NOW!!",300);
                            break;
                        }
                    }

                    if(player_hp<=0){
                        type("You lost the game!GAME OVER!!",typeSpeed);
                        break;
                    }else{
                        type("You beat the monster!",typeSpeed);
                        System.out.flush();
                        scanner.nextLine();
                    }

                }
                //after having one of the three events, player choose to move room again
                type("\nNow you can move to another room!\n1.Up 2.Down 3.Right 4.Left 5.Quit",typeSpeed);
                int choice = scanner.nextInt();
                if(choice == 5){    //if player choose to quit
                    System.out.println("GAME OVER! BYE~!");
                    break;
                }else if(choice<1||choice>5){
                    System.out.println("Invalid Input! Please Try it again!");
                    System.out.flush();
                    scanner.nextLine();
                }else if(canMove(playerRow, playerCol, choice)){    //if the map has space to go to the next move from the grid
                    switch (choice) {
                        case 1:
                            playerRow--;    //UP
                            break;
                        case 2:
                            playerRow++;    //Down
                            break;
                        case 3:
                            playerCol++;    //Right
                            break;
                        case 4:
                            playerCol--;    //Left
                            break;
                        default:
                            System.out.println("Invalid input!!Try it again");
                            break;
                    }
                }else{
                    System.out.println("This is the Edge of the Grid! Please go to other directions!");
                    
                }

            }else{  //if the room is empty, then nothing happens. Player can choose to move to the next room.
                type("There is nothing in this room. Where do you want to move? \n 1.Up, 2.Down, 3.Right, 4.Left 5.Quit  \n Type a number from 1~5: ", 20);
                //I changed the game from typing the action to just choosing from number options, as it will make the game much faster and remove tediousness for player.
                int choice = scanner.nextInt();
                if(choice == 5){    //if player choose to quit
                    System.out.println("GAME OVER! BYE~!");
                    break;
                }else if(choice<1||choice>5){

                    System.out.println("Invalid input! Please try it again!");  
                    System.out.flush();
                    scanner.nextLine();


                }else if(canMove(playerRow, playerCol, choice)){    //if the map has space to go to the next move from the grid
                    switch (choice) {
                        case 1:
                            playerRow--;    //UP
                            break;
                        case 2:
                            playerRow++;    //Down
                            break;
                        case 3:
                            playerCol++;    //Right
                            break;
                        case 4:
                            playerCol--;    //Left
                            break;
                        default:
                            System.out.println("Invalid input! Try it again!");
                            System.out.flush();
                            scanner.nextLine();
                            break;
                    }
                }else{
                    System.out.println("This is the edge of the Grid! Please move to other directions!");
                    System.out.flush();
                    scanner.nextLine();
                    clearScreen();
                }
            }
        }
    // ----- Write your code above

        
    }
    public static int calculateAge(int birthYear) {
        // ----- Write your code below
        int age = java.time.Year.now().getValue() - birthYear;
        return age;
        // ----- Write your code above
    }

     public static void clearScreen() {     //to clear screen for each stage, just for better interface
        try {
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                // Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Linux, Mac
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void type(String s, int msPerChar) { //to give a typiing effect into the text, so the game becomes more narrative
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            System.out.print(c);
            System.out.flush();

            // natural pauses
            int d = msPerChar;
            if (".!?".indexOf(c) >= 0) d *= 12;      // end of sentence
            else if (",;:".indexOf(c) >= 0) d *= 6;  // short pause for those letters
            else if (Character.isWhitespace(c)) d = Math.max(1, msPerChar / 3);

            sleep(d);
        }
        System.out.println(); //Line break after finishing the effect
    }

    private static void sleep(int ms) { //to decide how many times to pause in each text
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }

    public static int checkLevelOfDungeon(int dungeonValue,int returnLevel){ 
        //checking the level of events in the map. ex) 777 will be level 3 treasure, 11 will be level 2 monsters
        int level = returnLevel+1;
        if(dungeonValue/10 != 0){    //using the recursive until lvl is not divisible by 10 
            level = checkLevelOfDungeon(dungeonValue/10,level);
        }
        return level;
    }

    public static int encounterMonster(int level,int attack, int player_hp){    //a method that simulates a player vs monster
        int final_hp = player_hp;
        int monster_hp = 10*level, monster_attack = level; //The value can change to balance the game
        int player_attack_type = 0;
        Random rnd = new Random(); //giving randomness to a monster's choice of attack
        String [] attackType = {"Slash","Block","Magic"};
        while(final_hp>0 && monster_hp>0){ //the fight will continue until either player's or monster's hp runs out.
            System.out.println("====================================================");
            System.out.println("Your hp: "+ final_hp+" attack damage: "+attack);
            System.out.println("Monster hp: "+monster_hp+" attack damage: "+monster_attack);
            System.out.println("\nYou and Goblin will choose from 1.Slash 2.Block 3.Magic");
            System.out.println("Slash beats Magic, Magic beats block, Block beats Slash");
            System.out.println("====================================================");

            do{
                type("What will you do?\n 1.Slash 2.Block 3.Magic 4.quit", 20);  //ask for user action
                player_attack_type = scanner.nextInt();
                if(player_attack_type<1||player_attack_type>4){
                    System.out.println("invalid input. Please try it again");
                }
            }while(player_attack_type<1||player_attack_type>4);     //repeat until I get valid choice
            
            if(player_attack_type==4){
                return 0;   //when player chose to quit, then return 0 to make player_hp 0 which will end the game
            }
            int monster_attack_type = rnd.nextInt(3);   //random generation of monster attack type
            type("The Goblin chose to "+attackType[monster_attack_type]+"!",20);

            if((player_attack_type-monster_attack_type+2)%3==0) { 
                //just doing math to reduce 9cases possible to 3 cases of win, lose, draw
                //depending on the player and monster choice, either no one takes damage, or one of the two take damage.
                type("You chose to "+ player_attack_type+"! It's a tie so nothing happens!", 20);
            }else if((player_attack_type-monster_attack_type+2)%3==1){
                type("You chose to "+ player_attack_type+"! The Goblin takes "+attack+"damage", 20);
                monster_hp -= attack;
            }else{
                type("You chose to "+ player_attack_type+"! You take "+monster_attack+"damage!", 20);
                final_hp -=monster_attack;
            }
            sleep(1000); //just wait for 2second 
            clearScreen();
        }
        return final_hp;
    }

    public static boolean canMove(int row, int col, int direction) {
        int newRow = row;
        int newCol = col;
        //System.out.println("Inside canMove method");

        switch (direction) {
            case 1: newRow--; break;
            case 2: newRow++; break;
            case 4: newCol--; break;
            case 3: newCol++; break;
            default : return false; // invalid input
        }
    
        // Checking boundaries
        return (newRow >= 0 && newRow < 5) && (newCol >= 0 && newCol < 5);
    }



}

