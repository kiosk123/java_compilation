package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Util {
	public static void close(Connection conn){
		if(conn!=null){try{conn.close();}catch (Exception e){}}
	}
	
	public static void close(Connection conn , Statement pstmt){
		if(pstmt!=null){try{pstmt.close();}catch (Exception e){}}
		if(conn!=null){try{conn.close();}catch (Exception e){}}
	}
	
	public static void close(Connection conn , PreparedStatement pstmt, ResultSet rs){
		if(rs!=null){try{rs.close();}catch (Exception e){}}
		if(pstmt!=null){try{pstmt.close();}catch (Exception e){}}
		if(conn!=null){try{conn.close();}catch (Exception e){}}
	}
	public static boolean stringIsEmpty(String value){
		if(value==null||value.equals("")){
			return true;
		}else{
			return false;
		}
	}
}
