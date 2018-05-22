import java.io.*;

public class runDown163_kehanxueyuan_Math {

    public static void main( String[] args ) throws IOException {
	    String COURSEURL[] ={
			"http://open.163.com/special/Khan/exponents.html",
			"http://open.163.com/special/Khan/number.html",
			"http://open.163.com/special/Khan/algebra.html",
			"http://open.163.com/special/Khan/complexnumber.html",
			"http://open.163.com/special/Khan/logarithms.html",
			"http://open.163.com/special/Khan/precalculus.html",
			"http://open.163.com/special/Khan/euclideangeometry.html",
			"http://open.163.com/special/Khan/angles.html",
			"http://open.163.com/special/Khan/introductory.html",
			"http://open.163.com/special/Khan/trigonometry.html",
			"http://open.163.com/special/Khan/triangle.html",
			"http://open.163.com/special/Khan/polygon.html",
			"http://open.163.com/special/Khan/california.html",
			"http://open.163.com/special/Khan/probability.html",
			"http://open.163.com/special/Khan/khstatistics.html",
			"http://open.163.com/special/Khan/differentialcalculus.html",
			"http://open.163.com/special/Khan/differential.html",
			"http://open.163.com/special/Khan/linearalgebra.html",
			"http://open.163.com/special/Khan/ancientcryptography.html",
			"http://open.163.com/special/Khan/moderncryptography.html",
			"http://open.163.com/special/Khan/doodlinginmath.html",
			"http://open.163.com/special/Khan/brainteasers.html"}; //课程地址
	 String destinationUrl= "D:/kehan/";
	  DownloadThread.downOpen163(COURSEURL,destinationUrl,3);
    }



}