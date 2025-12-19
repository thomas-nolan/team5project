package io.github.team10.escapefromuni.Testing;

import io.github.team10.escapefromuni.ScoreManager;

public class ScoreManagerTest {

    public ScoreManagerTest(){}

    //Make boolean when added
    public boolean runAllTests() {
        ScoreManagerTest testing = new ScoreManagerTest();

        System.out.println("\nScore Manager Test!");
        System.out.println("--------------------");

        boolean test1 = testing.IncreaseScoreTest();

        System.out.println("\n\nTESTING FINISHED");


        System.out.println("--------------------");
        if(test1){return true;}
        else{return false;}
    }

    //Seeing the if the scoring works when increasing.
    public boolean IncreaseScoreTest(){
        System.out.println("\nTest 1: Score Increase");
        System.out.println("------------------");

        try {
            ScoreManager SM_TEST = new ScoreManager();
            int after_Score;
            boolean test1 = false, test2 = false, test3 = false;

            int before_Score = SM_TEST.getScore();
            System.out.println("Original Score: " + before_Score);

            SM_TEST.increaseScore(30);
            after_Score = SM_TEST.getScore();
            System.out.println(before_Score + " add 30 should be :" + (before_Score + 30) + "and it is : " + after_Score);
            if(after_Score == (before_Score + 30)){test1 = true;}

            before_Score = after_Score;

            SM_TEST.increaseScore(100);
            after_Score = SM_TEST.getScore();
            System.out.println(before_Score + " add 1000 should be :" + (before_Score + 100) + "and it is : " + after_Score);
            if(after_Score == (before_Score + 100)){test2 = true;}

            before_Score = after_Score;

            SM_TEST.increaseScore(100);
            SM_TEST.increaseScore(25);
            after_Score = SM_TEST.getScore();
            System.out.println(before_Score + " add 100 and 25 should be :" + (before_Score + 100 + 25) + "and it is : " + after_Score);
            if(after_Score == (before_Score + 125)){test3 = true;}

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


    public void ResetScoreTest(){}
    public void finalScoreCalcTest(){}

}
