/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package AIHelper;

import tetris.Board;

/**
 *
 * @author justinbehymer
 */
public class FinalRater extends BoardRater {
 public static BoardRater raters[] = //staticness prevents these raters from getting instantiated over and over and over and over again... this'll save garbage collection time.
 {
    new ConsecHorzHoles(),
    new HeightAvg(),
    new HeightMax(),
    new HeightMinMax(),
    new HeightVar(),
    new HeightStdDev(),
    new SimpleHoles(),
    new ThreeVariance(),
    new Through(),
    new WeightedHoles(),
    new RowsWithHolesInMostHoledColumn(),
    new AverageSquaredTroughHeight(),
    new BlocksAboveHoles()
 };
 //13.0, -9.0, 19.0, 1.0, 11.0, 1.0, 16.0, 14.0, 7.0, 0.0, -7.0, 17.0, 1.0 Best: 147420
  public double[] coefficients = {
/*new ConsecHorzHoles(),*/                13,
/*new HeightAvg(),*/                      -9,
/*new HeightMax(),*/                      19,
/*new HeightMinMax(),*/                   1,
/*new HeightVar(),*/                      11,
/*new HeightStdDev(),*/                   1,
/*new SimpleHoles(),*/                    16,
/*new ThreeVariance(),*/                  14,
/*new Trough(),*/                         7,
/*new WeightedHoles(),*/                  0,
/*new RowsWithHolesInMostHoledColumn()*/  -7,
/*new AverageSquaredTroughHeight()*/      17,
/*new BlocksAboveHoles()*/                1
  };
   
 public FinalRater() {
   // System.out.println("new final rater:");
   // String temp;`
   // for(int i=0; i<raters.length; i++) {
   //   System.out.println((temp=""+coefficients[i]).substring(0,temp.length()>=4?temp.length():3)+"\t\t"+raters[i]);
   // }
 }
 
 public FinalRater(double[] c) {
   if(c.length!=FinalRater.raters.length) {
     System.out.println("Make sure that the array passed into the FinalRater has the correct number of coefficients! Using DEFAULT COEFFICIENTS instead!");
     return;
   }
   this.coefficients = c;
 }
 
 double rate(Board board) {
   double score = 0, temp;
   for (int x=0; x<raters.length; x++) {
     score += (temp=this.coefficients[x])==0?0:temp*FinalRater.raters[x].rate(board);
     // System.out.print(this.coefficients[x]);
   }
   return score;
 }
 
 double rate(Board board, double[] coefficients) {
   double[] temp = this.coefficients;
   this.coefficients = coefficients;
   double ret = this.rate(board);
   this.coefficients = temp;
   return ret;
 }
}
