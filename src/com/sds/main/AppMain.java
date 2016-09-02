//영화 예매 어플리케이션
package com.sds.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sds.movie.list.MovieList;
import com.sds.movie.regist.MovieRegist;
import com.sds.movie.reservation.Reservation;
import com.sds.movie.seat.SeatChoice;

public class AppMain extends JFrame implements ActionListener{
	String[] menuTitle={
			"상영중영화","영화등록","예매내역","좌석선택"};
	JButton[] menu;
	JPanel p_north;
	JPanel[] content;
	JPanel center;
	Color[] bg={
			Color.RED, Color.YELLOW, Color.GREEN
			,Color.PINK};
	
	//데이터베이스 접속 관련...
	String driver="oracle.jdbc.driver.OracleDriver";//드라이버
	
	String url="jdbc:oracle:thin:@localhost:1521:XE";
	String user="java0819";
	String password="java0819";
	private static Connection con;
	
	public AppMain() {
		connectDB();
		
		menu = new JButton[4];
		content=new JPanel[menu.length];
		
		p_north = new JPanel();
		p_north.setLayout(new GridLayout(1, menu.length));
		
		for(int i=0;i<menu.length;i++){
			menu[i] = new JButton(menuTitle[i]);
			p_north.add(menu[i]);
			menu[i].addActionListener(this); //버튼과 리스너연결
		}
		
		//클래스명은 틀리지만 모두~ JPanel의 자식이다..
		content[0] = new MovieList();
		content[1] = new MovieRegist();
		content[2] = new Reservation();
		content[3] = new SeatChoice();
		
		//컨텐츠 패널들이 대체될 센터영역!!
		center = new JPanel();
		
		add(p_north, BorderLayout.NORTH);
		
		//가운데 영역에 컨텐트 패널들을 미리 붙여놓자!!
		for(int i=0;i<menu.length;i++){
			center.add(content[i]);
			content[i].setPreferredSize(new Dimension(600, 500));
			//content[i].setBackground(bg[i]);
			content[i].setVisible(false);
		}
		
		add(center);
		
		//첫번째 컨텐츠 보이기!!
		showContent(0);
		
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				if(con!=null){
					try {
						con.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				System.exit(0);
			}
		});
		
		setSize(600,500);
		setVisible(true);
		
		
	}
	
	//데이터베이스 접속 메서드!!
	public void connectDB(){
		try {
			Class.forName(driver); //드라이버 로드!
			con=DriverManager.getConnection(url,user,password);
			if(con !=null){
				setTitle("접속됨");
			}else{
				setTitle("접속실패");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	//원하는 컨텐츠 보이기!!
	public void showContent(int current){
		for(int i=0;i<menu.length;i++){
			if(i==current){
				content[i].setVisible(true);
			}else{
				content[i].setVisible(false);
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		
		for(int i=0;i<menu.length;i++){
			if(obj==menu[i]){ //첫번째 버튼 누르면..
				showContent(i);
			}
		}
	}
	
	public static Connection getConnection(){
		return con;
	}
	
	public static void main(String[] args) {
		new AppMain();
	}

}






