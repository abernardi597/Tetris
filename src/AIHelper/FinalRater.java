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
 
  public double[] coefficients = {
/*new ConsecHorzHoles(),*/                0,  
/*new HeightAvg(),*/                      10,
/*new HeightMax(),*/                      1,
/*new HeightMinMax(),*/                   1,
/*new HeightVar(),*/                      0,
/*new HeightStdDev(),*/                   5,
/*new SimpleHoles(),*/                    40,
/*new ThreeVariance(),*/                  10,
/*new Trough(),*/                         1,
/*new WeightedHoles(),*/                  4,
/*new RowsWithHolesInMostHoledColumn()*/  4,
/*new AverageSquaredTroughHeight()*/      15,
/*new BlocksAboveHoles()*/                2
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
