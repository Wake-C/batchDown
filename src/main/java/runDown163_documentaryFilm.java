import java.io.IOException;

public class runDown163_documentaryFilm {

    public static void main( String[] args ) throws IOException {
	//课程地址
	    String COURSEURL[] ={"http://open.163.com/special/sp/listeningtomusic.html"};
	 String destinationUrl= "D:/documentaryFilm/";
	  DownloadThread.downOpen163(COURSEURL,destinationUrl,3);
    }



}