package io.github.team10.escapefromuni.Testing;

public class Testing_Area {

    //TESTING STENCIL FOR FORMATS
    //public boolean X(){
    //    System.out.println("\nTest Y: X Test");
    //    System.out.println("------------------");

    //    try{
    //      LOGIC in here
    //    }
    //    catch (Exception error) {
    //      System.out.println("error is " + error.getMessage());
    //      return false;
    //    }
    //


     public static void main(String[] args) {
        System.out.println("TESTING AREA");

        AchievementManager_Test AT = new AchievementManager_Test();
        boolean IsAchievementPass = AT.runAllTests();

        TimerTest TT = new TimerTest();
        boolean IsTimerPass = TT.runAllTests();

        //ONe for events when events is done

    }
}
