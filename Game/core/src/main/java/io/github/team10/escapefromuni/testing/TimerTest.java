package io.github.team10.escapefromuni.Testing;

import io.github.team10.escapefromuni.Timer;

public class TimerTest {

    public Timer timer_TEST;
    public TimerTest() { this.timer_TEST = new Timer();}

    public boolean runAllTests(){
        TimerTest testing = new TimerTest();

        System.out.println("Timer Test!");
        System.out.println("--------------------");

        boolean test1 = testing.TimeDescreaseTest();
        boolean test2 = testing.FreezeTimeTest();
        boolean test3 = testing.TimeFinishedTest();


        System.out.println("\n\nTESTING FINISHED");
        System.out.println("--------------------");

        if(test1){System.out.println("Time Decrease Test: PASS");}
        else{System.out.println("Time Decrease Test: FAIL");}
        if(test2){System.out.println("Time Freeze Test: PASS");}
        else{System.out.println("Time Freeze Test: FAIL");}
        if(test3){System.out.println("Time finish Test: PASS");}
        else{System.out.println("Time finish Test: FAIL");}

        if(test1 && test2 && test3){return true;}
        else {return false; }

    }


    //Need to check if the timer actually decreases or not
    public boolean TimeDescreaseTest(){
        System.out.println("\nTest 1: Timer decreases");
        System.out.println("------------------");

        try {
            timer_TEST = new Timer();
            int before_Time = timer_TEST.getTimeLeftSeconds();
            int after_Time;
            boolean test1 = false, test2=false, test3=false;

            //1 seconds
            timer_TEST.update(1.0f);
            after_Time = timer_TEST.getTimeLeftSeconds();
            System.out.println("one second later then " + before_Time + " is " + (before_Time - 1.0f) + " :" + after_Time);
            if(after_Time == (before_Time - 1.0f)){test1 = true;}
            before_Time = after_Time;

            //10 seconds
            timer_TEST.update(10.0f);
            after_Time = timer_TEST.getTimeLeftSeconds();
            System.out.println("ten seconds later then " + before_Time + " is " + (before_Time - 10.0f) + " :" + after_Time);
            if(after_Time == (before_Time - 10.0f)){test2 = true;}
            before_Time = after_Time;

            //100 seconds
            timer_TEST.update(100.0f);
            after_Time = timer_TEST.getTimeLeftSeconds();
            System.out.println("one hundred seconds later then " + before_Time + " is " + (before_Time - 100.0f) + " :" + after_Time);
            if(after_Time == (before_Time - 100.0f)){test3 = true;}

            if(test1 && test2 && test3){System.out.println("\nDECREASING TIME = PASS"); return true; }
            else{System.out.println("\nDECREASING TIME = FAIL");return false;}

        } catch (Exception error) {
            System.out.println("error is " + error.getMessage());
            return false;
        }

    }

    //Testing the freeeze timer function
    public boolean FreezeTimeTest(){
        System.out.println("\nTest 2: Time Freezes");
        System.out.println("------------------");

        try {
            timer_TEST = new Timer();

            timer_TEST.update(20.0f);
            int before_Time = timer_TEST.getTimeLeftSeconds();
            timer_TEST.setFrozen();
            int after_Time = timer_TEST.getTimeLeftSeconds();
            System.out.println("TESTING TIME FREEZING: the time when frozen was " + before_Time + " and it should be the same and it is : " + after_Time);

            if(before_Time == after_Time){System.out.println("\nFREEZING TIME = PASS"); return true; }
            else{System.out.println("\nDECREASING TIME = FAIL");return false;}

        } catch (Exception error) {
            System.out.println("error is " + error.getMessage());
            return false;
        }

    }

    //Checking if it is correct if it checks it reaches state zero
    public boolean TimeFinishedTest(){
        System.out.println("\nTest 3: Time Freezes");
        System.out.println("------------------");
        try {
            timer_TEST = new Timer();
            timer_TEST.update(300.0f);
            int Current_Time = timer_TEST.getTimeLeftSeconds();

            System.out.println("Currently the time should be 0: and it is " + Current_Time);

            if(timer_TEST.isFinished()){System.out.println("\n TIME HAS REACHED ZERO = PASS"); return true; }
            else{System.out.println("\n TIME HAS REACHED ZERO = FAIL");return false;}

        } catch (Exception error) {
            System.out.println("error is " + error.getMessage());
            return false;
        }

    }



}
