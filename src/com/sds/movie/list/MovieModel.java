package com.sds.movie.list;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.sds.main.AppMain;

public class MovieModel extends AbstractTableModel{

	String[] colName = {"Genre_ID","Movie_ID","��ȭ����","������","�󿵽ð�","�̹���"};
	ArrayList<String[]> listArr = new ArrayList<String[]>();
	

	public MovieModel() {
		// TODO Auto-generated constructor stub
		selectAll();
	}
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return colName.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return listArr.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		// TODO Auto-generated method stub
		String[] record=listArr.get(row);
		return record[col];
	}
	
	@Override
	public String getColumnName(int col) {
		// TODO Auto-generated method stub
		return colName[col];
	}
	
	//��ȭ��� ���������� ���ڵ� ��������
	public void selectAll(){
		Connection con= AppMain.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		
		
		String sql="select g.title as genre_title, m.title as movie_title, movie_id, openday,runtime,img from movie m, genre g";
		sql=sql+" where m.genre_id=g.genre_id";

		
		try {
			pstmt = con.prepareStatement(sql);
			rs=pstmt.executeQuery();
		
			
			listArr.removeAll(listArr);
			while(rs.next()){
				//���ڵ� �ѰǴ� 1���� �迭 1���� ����
				String[] record = new String[6];
				
				record[0]=rs.getString("genre_title");
				record[1]=rs.getString("movie_title");
				record[2]=Integer.toString(rs.getInt("movie_id"));
				record[3]=rs.getString("openday");
				record[4]=Integer.toString(rs.getInt("runtime"));
				record[5]=rs.getString("img");
				
				listArr.add(record);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(rs !=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		System.out.println(sql);
	}
	
	

}
