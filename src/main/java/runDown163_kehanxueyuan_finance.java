import java.io.IOException;

public class runDown163_kehanxueyuan_finance {

    public static void main( String[] args ) throws IOException {
	    String COURSEURL[] ={
			    "http://open.163.com/special/Khan/microeconomics.html"}; //课程地址
	 String destinationUrl= "D:/finance/";
	  DownloadThread.downOpen163(COURSEURL,destinationUrl,3);
    }



}