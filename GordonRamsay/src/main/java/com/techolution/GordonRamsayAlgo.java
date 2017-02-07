package com.techolution;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GordonRamsayAlgo {

  public static void main(String[] args) {
    File file = new File("src/main/resources/input.txt");

    try {
      Scanner scan = new Scanner(file);
      int totalTime = scan.nextInt();
      int totalDishes = scan.nextInt();

      int[] satisfaction = new int[totalDishes];
      int[] individualDishTime = new int[totalDishes];
      int i = 0;
      // fill the array from the input data
      while (scan.hasNextInt()) {
        satisfaction[i] = scan.nextInt();
        individualDishTime[i] = scan.nextInt();
        i++;
      }

      scan.close();

      int maxSatisfaction = maximumSatisfaction(satisfaction, individualDishTime, totalTime);
      System.out.println(maxSatisfaction);

    } catch (FileNotFoundException exception) {
      exception.printStackTrace();
    }
  }

  public static int maximumSatisfaction(int satisfaction[], int individualDishTime[], int totalTime) {
    int dishNumber = individualDishTime.length; // total number of dishes
    // creating a matrix. dishes will be in rows and time in colums.
    int[][] matrix = new int[dishNumber + 1][totalTime + 1];

    fillMatrix(totalTime, dishNumber, matrix); // filling the matrix initially with 0's

    for (int dish = 1; dish <= dishNumber; dish++) {
      // filling row by row
      for (int time = 1; time <= totalTime; time++) {
        // if the current dish time is less than or equal to running time
        if (individualDishTime[dish - 1] <= time) {
          // with the input dish time, checking if the satisfaction of current dish + satisfaction
          // of the
          // dish we could afford with the remaining dishes.
          // is greater than the satisfaction without the current dish
          matrix[dish][time] =
              Math.max(satisfaction[dish - 1]
                  + matrix[dish - 1][time - individualDishTime[dish - 1]], matrix[dish - 1][time]);
        } else {
          // If the current dish's time is more than the running time, move forward without the
          // current item
          matrix[dish][time] = matrix[dish - 1][time];
        }
      }
    }
    return matrix[dishNumber][totalTime];
  }

  private static void fillMatrix(int totalTime, int dishLength, int[][] matrix) {
    for (int col = 0; col <= totalTime; col++) {
      matrix[0][col] = 0;
    }
    for (int row = 0; row <= dishLength; row++) {
      matrix[row][0] = 0;
    }
  }
}
