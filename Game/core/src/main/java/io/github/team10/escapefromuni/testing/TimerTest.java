package io.github.team10.escapefromuni.Testing;

import io.github.team10.escapefromuni.Timer;

public class TimerTest {

    public Timer timer_TEST;
    public TimerTest() { this.timer_TEST = new Timer();}

    public boolean runAllTests(){
        TimerTest testing = new TimerTest();

        System.out.println("TimerTest!");
        System.out.println("----------------------------------------");

        boolean test1 = testing.TimeDecreaseTest();
        boolean test2 = testing.FreezeTimeTest();
        boolean test3 = testing.TimeFinishedTest();
        boolean test4 = testing.InitialStateTest();
        boolean test5 = testing.FractionalTimeTest();

        System.out.println("\n\nTIMER TESTING FINISHED");
        System.out.println("----------------------------------------");

        if(test1){System.out.println("Time Decrease Test: PASS");}
        else{System.out.println("Time Decrease Test: FAIL");}
        if(test2){System.out.println("Time Freeze Test: PASS");}
        else{System.out.println("Time Freeze Test: FAIL");}
        if(test3){System.out.println("Time Finish Test: PASS");}
        else{System.out.println("Time Finish Test: FAIL");}
        if(test4){System.out.println("Initial State Test: PASS");}
        else{System.out.println("Initial State Test: FAIL");}
        if(test5){System.out.println("Fractional Time Test: PASS");}
        else{System.out.println("Fractional Time Test: FAIL");}

        if(test1 && test2 && test3 && test4 && test5){return true;}
        else {return false; }

    }

    //Need to check if the timer actually decreases or not
    public boolean TimeDecreaseTest(){
        System.out.println("\nTest 1: Timer decreases");
        System.out.println("------------------");

        try {
            timer_TEST = new Timer();
            int before_Time = timer_TEST.getTimeLeftSeconds();
            int after_Time;
            boolean test1 = false, test2 = false, test3 = false;

            //1 seconds
            timer_TEST.update(1.0f);
            after_Time = timer_TEST.getTimeLeftSeconds();
            System.out.println("one second later then " + before_Time + " is " + (before_Time - 1) + " :" + after_Time);
            if(after_Time == (before_Time - 1)){test1 = true;}
            before_Time = after_Time;

            //10 seconds
            timer_TEST.update(10.0f);
            after_Time = timer_TEST.getTimeLeftSeconds();
            System.out.println("ten seconds later then " + before_Time + " is " + (before_Time - 10) + " :" + after_Time);
            if(after_Time == (before_Time - 10)){test2 = true;}
            before_Time = after_Time;

            //100 seconds
            timer_TEST.update(100.0f);
            after_Time = timer_TEST.getTimeLeftSeconds();
            System.out.println("one hundred seconds later then " + before_Time + " is " + (before_Time - 100) + " :" + after_Time);
            if(after_Time == (before_Time - 100)){test3 = true;}

            if(test1 && test2 && test3){
                System.out.println("\nDECREASING TIME = PASS"); 
                return true; 
            }
            else{
                System.out.println("\nDECREASING TIME = FAIL");
                return false;
            }

        } catch (Exception error) {
            System.out.println("error is " + error.getMessage());
            return false;
        }
    }

    //Testing the freeze timer function
    public boolean FreezeTimeTest(){
        System.out.println("\nTest 2: Time Freezes");
        System.out.println("------------------");

        try {
            timer_TEST = new Timer();

            timer_TEST.update(20.0f);
            int before_Time = timer_TEST.getTimeLeftSeconds();
            timer_TEST.setFrozen();
            
            // Update multiple times while frozen
            timer_TEST.update(5.0f);
            timer_TEST.update(5.0f);
            timer_TEST.update(5.0f);
            
            int after_Time = timer_TEST.getTimeLeftSeconds();
            System.out.println("TESTING TIME FREEZING: the time when frozen was " + before_Time + " and it should be the same and it is : " + after_Time);

            if(before_Time == after_Time){
                System.out.println("\nFREEZING TIME = PASS"); 
                return true; 
            }
            else{
                System.out.println("\nFREEZING TIME = FAIL");
                return false;
            }

        } catch (Exception error) {
            System.out.println("error is " + error.getMessage());
            return false;
        }
    }

    //Checking if it is correct if it checks it reaches state zero
    public boolean TimeFinishedTest(){
        System.out.println("\nTest 3: Time Finished");
        System.out.println("------------------");
        try {
            timer_TEST = new Timer();
            timer_TEST.update(300.0f);
            int Current_Time = timer_TEST.getTimeLeftSeconds();

            System.out.println("Currently the time should be 0: and it is " + Current_Time);

            timer_TEST.update(10.0f); // Should be maths -10 seconds now as its passed but should still be set as zero
            int PastZeroTime = timer_TEST.getTimeLeftSeconds();
            System.out.println("Added 10 seconds of time gone (-10s now) so it should still be zero and it is : " + PastZeroTime);

            if(timer_TEST.isFinished() && PastZeroTime == 0){
                System.out.println("\nTIME HAS REACHED ZERO = PASS"); 
                return true; 
            }
            else{
                System.out.println("\nTIME HAS REACHED ZERO = FAIL");
                return false;
            }

        } catch (Exception error) {
            System.out.println("error is " + error.getMessage());
            return false;
        }
    }

    //Test initial state of timer
    public boolean InitialStateTest(){
        System.out.println("\nTest 4: Initial State");
        System.out.println("------------------");

        try {
            timer_TEST = new Timer();
            
            int initialTime = timer_TEST.getTimeLeftSeconds();
            System.out.println("Initial time: " + initialTime);
            
            boolean isInitiallyNotFinished = !timer_TEST.isFinished();
            System.out.println("Initially finished: " + timer_TEST.isFinished());
            
            // Check reasonable initial values (usually 300 seconds = 5 minutes)
            if(initialTime > 0 && isInitiallyNotFinished){
                System.out.println("\nINITIAL STATE = PASS");
                return true;
            }
            else{
                System.out.println("\nINITIAL STATE = FAIL");
                return false;
            }

        } catch (Exception error) {
            System.out.println("error is " + error.getMessage());
            return false;
        }
    }

    //Test fractional time updates
    public boolean FractionalTimeTest(){
        System.out.println("\nTest 5: Fractional Time");
        System.out.println("------------------");

        try {
            timer_TEST = new Timer();
            
            int initialTime = timer_TEST.getTimeLeftSeconds();
            System.out.println("Initial time: " + initialTime);
            
            // Add fractional seconds that should accumulate to whole seconds
            timer_TEST.update(0.3f);
            timer_TEST.update(0.3f);
            timer_TEST.update(0.4f); // Total: 1.0 seconds
            
            int timeAfter1SecAccumulated = timer_TEST.getTimeLeftSeconds();
            System.out.println("After 0.3 + 0.3 + 0.4 seconds: " + timeAfter1SecAccumulated);
            
            // Add more fractional time
            timer_TEST.update(1.5f);
            timer_TEST.update(0.5f); // Total: 2.0 more seconds
            
            int timeAfterMoreFractional = timer_TEST.getTimeLeftSeconds();
            System.out.println("After 1.5 + 0.5 more seconds: " + timeAfterMoreFractional);
            
            // The timer should have decreased by at least 3 seconds total
            int totalDecrease = initialTime - timeAfterMoreFractional;
            System.out.println("Total decrease: " + totalDecrease + " seconds");
            
            if(totalDecrease >= 3){
                System.out.println("\nFRACTIONAL TIME = PASS");
                return true;
            }
            else{
                System.out.println("\nFRACTIONAL TIME = FAIL (expected at least 3 seconds decrease)");
                return false;
            }

        } catch (Exception error) {
            System.out.println("error is " + error.getMessage());
            return false;
        }
    }
}
