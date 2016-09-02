package com.sds.movie.list;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelTest {
	public static void main(String[] args) {
		Connection con=null;
		PreparedStatement pstmt=null;//쿼리문을 담당!!
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:XE", 
					"java0819", "java0819");
			
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		File file = new File("C:/MovieDB.xlsx");
		
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			System.out.println(workbook);
			
			XSSFSheet movie=workbook.getSheet("movie");
			int total=movie.getPhysicalNumberOfRows();
			
			for(int i=1;i<total;i++){
				XSSFRow row=movie.getRow(i);
				
				//하나의 레코드가 완료되는 시점!!
				String sql="insert into movie(movie_id,title,genre_id,runtime,openday)";
				sql=sql+" values(seq_movie.nextval,?,?,?,?)";
				
				try {
					pstmt=con.prepareStatement(sql);
					
					for(int a=0;a<4;a++){
						XSSFCell cell=row.getCell(a);
						
						if(cell.getCellType() ==XSSFCell.CELL_TYPE_STRING){
							pstmt.setString(a+1, cell.getStringCellValue());
						}else if(cell.getCellType() ==XSSFCell.CELL_TYPE_NUMERIC){
							pstmt.setInt(a+1,(int)cell.getNumericCellValue());
						}
					}
					
					System.out.println(sql);
					pstmt.executeUpdate();//쿼리문 수행!!
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
