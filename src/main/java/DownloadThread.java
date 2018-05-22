import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * Created by Mrchim on 2018/4/19.
 */
//文件下载线程
class DownloadThread extends Thread {
    String courseName;
    int start, end, threadId;
    File file = null;
    URL  url  = null;
    private static final String BaseUrl   = "http://yipeiwu.com/getvideo.html?url=";

    public DownloadThread( int threadId, int block, File file, URL url,String courseName ) {
	this.threadId = threadId;
	start = block * threadId;
	end = block * ( threadId + 1 ) - 1;
	this.file = file;
	this.url = url;
	this.courseName=courseName;
    }

    public void run() {
	try {
	    System.out.println( "线程" + threadId + "开启：开始下载"+ courseName);
	    //获取连接并设置相关属性。
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod( "GET" );
	    conn.setReadTimeout( 5000 );
	    //此步骤是关键。
	    conn.setRequestProperty( "Range", "bytes=" + start + "-" + end );
	    if ( conn.getResponseCode() == 206 ) {
		RandomAccessFile raf = new RandomAccessFile( file, "rw" );
		//移动指针至该线程负责写入数据的位置。
		raf.seek( start );
		//读取数据并写入
		InputStream inStream = conn.getInputStream();
		byte[] b = new byte[1024];
		int len = 0;
		while ( ( len = inStream.read( b ) ) != -1 ) {
		    raf.write( b, 0, len );
		}
		System.out.println( "线程" + threadId + "："+courseName+"下载完毕" );
	    }
	} catch ( Exception e ) {
	    e.printStackTrace();
	}
    }

    /*
 * 下载文件
 */
    public static  void download( String filePath, String destination, String fileName, int threadNum ) {
	try {
	    //20秒下载一个视频
	    Thread.sleep(20000);
	    //通过下载路径获取连接
	    URL url = new URL( filePath );
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    //设置连接的相关属性
	    conn.setRequestMethod( "GET" );
	    conn.setReadTimeout( 5000 );
	    //判断连接是否正确。
	    if ( conn.getResponseCode() == 200 ) {
		// 获取文件大小。
		int fileSize = conn.getContentLength();
		//得到文件名
		//根据文件大小及文件名，创建一个同样大小，同样文件名的文件
		File file = new File( destination + File.separator + fileName );
		RandomAccessFile raf = new RandomAccessFile( file, "rw" );
		raf.setLength( fileSize );
		raf.close();
		// 将文件分成threadNum = 5份。
		int block = fileSize % threadNum == 0 ? fileSize / threadNum : fileSize / threadNum + 1;
		for ( int threadId = 0; threadId < threadNum; threadId++ ) {
		    //传入线程编号，并开始下载。
		    new DownloadThread( threadId, block, file, url, fileName ).start();
		}

	    }
	} catch ( Exception e ) {
	    e.printStackTrace();
	}
    }
    /**
     *
     * @param COURSEURL   课程地址
     * @param DestinationPath  下载文件到磁盘路径
     * @param threadNum   开启线程数
     * @throws IOException
     */
    public static void downOpen163(String []COURSEURL,String DestinationPath,int threadNum) throws IOException {
	int k=0;
	int c =0;
	for(;c <COURSEURL.length;c++) {
	    try {
		Document document = Jsoup.connect( BaseUrl+COURSEURL[c] ).header( "User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.119 Safari/537.36" ).get();

		String subjectName = document.body().getElementsByClass( "col-md-12" ).get( 1 ).getElementsByTag( "h2" ).text();
		System.out.println( "开始下载科目：-------------------"+subjectName );
		File sbujectFile; //存放文件夹
		if(COURSEURL.length>1){
		     sbujectFile = new File( DestinationPath +k+subjectName );
		     k++;
		}else{
		    sbujectFile = new File( DestinationPath + subjectName );
		}
		if ( !sbujectFile.isDirectory() ) {
		    sbujectFile.mkdirs();
		}
		if(c!=0){
		    Thread.sleep(10000);
		}
		Elements list2 = document.body().getElementsByTag( "tbody" ).get( 0 ).getElementsByTag( "tr" );
		for ( int i = 2; i < list2.size(); i++ ) {
		    String courseName = list2.get( i ).getElementsByTag( "th" ).html();
		    String courseDownUrl = list2.get( i ).getElementsByTag( "a" ).attr( "href" ).trim();
		    System.out.print( courseName + "   " );
		    System.out.println( courseDownUrl );
		    DownloadThread.download( courseDownUrl, sbujectFile.toString(), courseName.replaceAll( ":", "" )+".mp4", threadNum );
		}
	    }catch(SocketTimeoutException e){
		e.printStackTrace();
		System.out.println( c +"出错——————————————————");
		if(c!=0){
		    --c;
		}
	    }catch(InterruptedException e){
		e.printStackTrace();
	    }
	}
    }


}

