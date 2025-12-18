package io.github.team10.escapefromuni.testing;


import io.github.team10.escapefromuni.AchievementManager; //Import the class chosen


//This is a Test for the achievments code
//I am checking first if i can add and remove achievments
//I am also checking wether the ahcievements "reset" works
//and get total.

//Able to add more if more code is contributed
public class AchievementManager_Test {

    public AchievementManager AM_TEST = new AchievementManager();

    public AchievementManager_Test() {
        this.AM_TEST = new AchievementManager();
    }

    public static void main(String[] args) {

        AchievementManager_Test testing = new AchievementManager_Test();

        System.out.println("Achievements Test!");
        System.out.println("--------------------");

        boolean test1 = testing.addAchievementTest();
        boolean test2 = testing.removeAchievementTest();
        boolean test3 = testing.ResetTest();


        System.out.println("\n\nTESTING FINISHED");
        System.out.println("--------------------");
        if(test1){System.out.println("Add Achievement Test: PASS");}
        else{System.out.println("Add Achievement Test: FAIL");}
        if(test2){System.out.println("Remove Achievement Test: PASS");}
        else{System.out.println("Remove Achievement Test: FAIL");}
        if(test3){System.out.println("Reset Achievement Test: PASS");}
        else{System.out.println("Reset Achievement Test: FAIL");}

    }

    /* public boolean getCountTest(){
        System.out.println("\nTest 4: Counting Achievements");
        System.out.println("------------------");

        try {

            AM_TEST = new  AchievementManager();
            AM_TEST.addAchievement("Complete game");
            AM_TEST.addAchievement("Speedrun");
            AM_TEST.addAchievement("Find Long Boi");

            int Old_Amount = AM_TEST.getTotalAchievements();
            System.out.println("Before Resetting: " + Old_Amount + " achievements");



        } catch (Exception error) {
            System.out.println("error is " + error.getMessage());
            return false;
        }
    } */



    //This is a test to see if i can reset the whole achievment board
    //In this test I add two achievments
    //and then i reset the board
    //Check their state and check the total should be 0 (CURRENTLY ONE ONLY COS I DONT WANT TO CHANGE ANYTHING)
    public boolean ResetTest(){
        System.out.println("\nTest 3: Reset Achievements");
        System.out.println("------------------");

        try{
            AM_TEST = new  AchievementManager();
            AM_TEST.addAchievement("Complete game");
            AM_TEST.addAchievement("Speedrun");

            int Old_Amount = AM_TEST.getTotalAchievements();
            System.out.println("Before Resetting: " + Old_Amount + " achievements");

            AM_TEST.reset();
            int After_Amount = AM_TEST.getTotalAchievements();
            System.out.println("After Resetting: " + After_Amount + " achievements");

            //Check the state of them
            Boolean speedrunState = AM_TEST.getStateAchievement("Speedrun");
            Boolean CompGame = AM_TEST.getStateAchievement("Complete game");

            //Printing it
            if(speedrunState){throw new Exception("Incorrect State: Should be false not true");}
            else{System.out.println("\nSpeedrun status:" + speedrunState);}

            if(CompGame){throw new Exception("Incorrect State: Should be false not true");}
            else{System.out.println("Complete game status: " + CompGame);}


            //Change to 0 when the achievement manager is fixed!!!! aka the last achievment !!
            if(After_Amount == 1 && !speedrunState && !CompGame){
                System.out.println("\nRESETING ACHIEVMENTS = PASS");
                return true;
            }
            else{
                System.out.println("\nRESETING ACHIEVMENTS = FAIL");
                return false;
            }

        }
        catch(Exception error){
            System.out.println("error is " + error.getMessage());
            return false;
        }
    }




    //This is a test to see if i can a remove achievement to the achievement board and it counts.
    //In this test I add an achievment then remove it
    //and then i check if it is active before then i remove it
    //i then check its state it should now be not active and i check the amount goes down by one.
    public boolean removeAchievementTest(){
        System.out.println("\nTest 2: Remove Achievements");
        System.out.println("------------------");

        try {
            AM_TEST = new AchievementManager();
            AM_TEST.addAchievement("Find Long Boi");

            int Old_Amount = AM_TEST.getTotalAchievements();
            System.out.println("Before adding: " + Old_Amount + " achievements");


            if(AM_TEST.getStateAchievement("Find Long Boi")){System.out.println("Find Long Boi Achievement state : ACTIVE");}
            else{throw new Error("Incorrect logic");}

            AM_TEST.removeAchievement("Find Long Boi");

            int New_Amount = AM_TEST.getTotalAchievements();
            System.out.println("After adding: " + New_Amount + " achievements");

            if(!AM_TEST.getStateAchievement("Find Long Boi")){System.out.println("Find Long Boi Achievement state : DEACTIVATED");}
            else{throw new Exception("Incorrect State");}

            if (AM_TEST.getStateAchievement("Find Long Boi") == false && New_Amount == (Old_Amount - 1) ) {System.out.println("\n\nREMOVING ACHIEVEMENTS = PASS"); return true;}
            else { System.out.println("\n\nREMOVING ACHIEVEMENTS = FAIL"); return false;}


        } catch (Exception error) {
            System.out.println("Error is " + error.getMessage());
            return false;
        }
    }




    //This is a test to see if i can add achievements to the achievement board and it counts.
    //In this test I add two achievments
    //and then i check if they are both activated.
    //if they are then i check if the count went up by two
    public boolean addAchievementTest(){
        System.out.println("\nTest 1: Adding Achievements");
        System.out.println("------------------");

        try {
            // Reset to clean state
            AM_TEST = new AchievementManager();

            //Get old amount should be empty can change in future
            int Old_Amount = AM_TEST.getTotalAchievements();
            System.out.println("\nAmount before adding any : " + Old_Amount + " achievements");

            // Add achievements
            AM_TEST.addAchievement("Speedrun");
            AM_TEST.addAchievement("Complete game");
            System.out.println("Achievements added: Speedrun & Complete game");

            //Now testing if they have been added seeing if the amounts gone up
            int New_Amount = AM_TEST.getTotalAchievements();
            System.out.println("After adding: " + New_Amount + " achievements");


            //Check the state of them
            Boolean speedrunState = AM_TEST.getStateAchievement("Speedrun");
            Boolean gameState = AM_TEST.getStateAchievement("Complete game");

            //Printing it
            System.out.println("\nSpeedrun status: " + speedrunState);
            System.out.println("Complete game status: " + gameState);

            if (speedrunState == true && gameState == true && New_Amount == (Old_Amount + 2) ) {System.out.println("\n\nADDING ACHIEVEMENTS = PASS"); return true;}
            else { System.out.println("\n\nADDING ACHIEVEMENTS = FAIL"); return false;}

        } catch (Exception error) {
            System.out.println("error is " + error.getMessage());
            return false;
        }
    }



}

