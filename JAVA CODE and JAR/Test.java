package team1;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.sql.*;
import java.net.URL;

public class Test {
	/** 배소연*/
	//jdbc driver 이용
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	//database는 team1
	static final String DB_URL = "jdbc:mysql://localhost:3306/team1?useSSL=false&serverTimezone=UTC";
	//user는 team1
	static final String USER = "team1";
	// password는 team1
	static final String PASS = "team1";
	//커넥션 설정을 위한 초기화
	static  Connection conn = null;
	//스테이트먼트 실행을 위한 초기화
	static   Statement stmt = null;
	static   Statement stmt2 = null;

	public static void main(String[] args) {
		try {
			//Register JDBC driver
			Class.forName(JDBC_DRIVER);
			//Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			//Execute a query
			stmt = conn.createStatement();
			stmt2 = conn.createStatement();
		}catch(Exception e) {
			e.printStackTrace();
		}
		/** 박소연*/
		//frame 생성
		REDSystem my = new REDSystem();
	}
	public static class REDSystem extends JFrame implements ActionListener, ItemListener {
		//페이지별 카드
		JPanel card, cardhome, card_m1, card_m2, card_m3, card_u1, card_u2, card_u3;
		//페이지 별 버튼을 담는 패널
		JPanel textpanel_u, textpanel_u2, radiobuttons, radiobuttons2, infopanel;
		//홈페이지 배경을 그릴 main 패널과 나머지 페이지의 배경을 그릴 page 패널, user와 employee페이지의 테이블패널
		JPanel main, page, tablepanel_u, tablepanel_emp;
		//order, profit, salary up, salary down을 위한 패널
		JPanel tablepanel_order, tablepanel_profit, bonuspanel, crisispanel;
		//manager, user 모드 진입 버튼, 홈 버튼, 뒤로가기 버튼
		JButton managerbutton, userbutton, homebutton, backbutton;
		//각 페이지의 홈 버튼과 뒤로가기 버튼
		JButton homebutton_m1, homebutton_m2, homebutton_m3;
		JButton homebutton_u1, homebutton_u2, homebutton_u3;
		JButton backbutton_m1, backbutton_m2, backbutton_m3;
		JButton backbutton_u1, backbutton_u2, backbutton_u3;
		//user모드에서 테이블 정렬 후 테이블 update 버튼과 next페이지로 넘어가는 버튼
		JButton nextbutton_u1, nextbutton_u2, updatebutton;
		//order table의 order를 처리하는 버튼과 user모드에서 delivery, nodelivery 선택 버튼
		JButton takeorderbutton, deliverbutton, nodeliverbutton;
		//manager모드에서 employee관리, orderprocessing 버튼, employee관리에서의 버튼
		JButton employeebutton, orderprbutton, getinfobutton, enterinfobutton;
		JButton givebutton_up, givebutton_down, firebutton, closebutton;
		//다중 선택 버튼
		Checkbox baby, pet, meat, seafood, dairy, fungus, vegetable, grain, fruit;
		Checkbox family, date, friends, oriental, western, dessert;
		Checkbox Seocho, Jongno, Seongdong, Songpa, Gangnam, Yongsan;
		//하나만 선택해야 할 때 라디오 버튼
		JRadioButton r5000, r10000, r15000, r20000, r25000, r30000;
		JRadioButton update_radio, delete_radio, insert_radio, park, nopark, nomatter;
		JRadioButton r0, r2, r4, r6, r8, score_radio, price_radio, region_radio;
		JRadioButton remove_radio, park_radio, accompany_radio, type_radio, recommend_radio;
		//text입력 창
		JTextField name, salary, plus_salary, minus_salary, delete_salary;
		//choice 상자
		Choice c_position, c_res_name;
		//user2 페이지에서 쓰일 label
		JLabel select, try_again, success, restaurant_name;
		//user3 페이지에 출력되는 label
		JLabel food_type, food_name, food_price, restaurant_num;
		//m2 페이지에 안내 메세지 label
		JLabel inform_emp_up_1, inform_emp_down_1,delivery_cost_label,delivery_cost;
		JLabel inform_emp_down_errtable;
		JLabel inform_emp_up_done, inform_emp_down_done;
		JLabel inform_emp_error, inform_emp_down_format;
		JLabel inform_emp_insert, inform_emp_select;
		//user3 페이지에 재료가 없을 때 출력되는 label, manager3 페이지에 쓰일 label
		JLabel x, noingredient, complete, thisis, order, profit, empty;
		//페이지와 버튼에 사용되는 이미지들
		ImageIcon start, strawberry, pineapple, watermelon, kiwi, delivery, deliveryx;
		ImageIcon manager1, manager2, manager3, user1, user2, user3, update, takeorder;
		ImageIcon employee, cashier, orange, grape, home, back, next, close;
		//gui 전체를 카드레이아웃으로 선언
		CardLayout layout=new CardLayout();
		//창 크기를 정하기 위해 스크린 사이즈를 불러오는 과정
		Toolkit tk = getToolkit();
		Dimension d = tk.getScreenSize();
		//m2에서 테이블에서 받아온 정보와 새롭게 입력받은 정보
		String emp_ID, oldname, oldposition, oldresname, newname, newposition, newresname;
		int oldsalary,newsalary;
		//창 크기
		int W = 9*d.width/10;
		int H = 9*W/16;
		//delivery, nodelivery를 구분하는 flag
		int flag = 0;
		//쿼리 실행 결과를 받아올 테이블모델
		DefaultTableModel model, model2;
		//테이블을 받아올 jtable
		JTable table, table2;
		//jtable을 반영할 스크롤팬
		JScrollPane jsp, jsp2;

		/** 배소연*/
		//이미지를 크기에 맞게 바꾸는 method
		ImageIcon changeImageSize(ImageIcon a, int m, int n) {
			Image b = a.getImage();
			b = b.getScaledInstance(W/m, H/n, Image.SCALE_SMOOTH);
			a = new ImageIcon(b);
			return a;
		}

		/*프레임창 생성자*/
		/** 박소연*/
		public REDSystem(){
			setTitle("Restaurant Management System");//타이틀을 설정한다.
			setSize(W,H);//크기를 설정한다.
			//card 패널에 cardlayout을 설정
			card=new JPanel();
			card.setLayout(layout);

			/** 배소연*/
			//이미지 불러오기
			home = new ImageIcon(this.getClass().getClassLoader().getResource("homebutton.png"));
			back = new ImageIcon(this.getClass().getClassLoader().getResource("backbutton.png"));
			start = new ImageIcon(this.getClass().getClassLoader().getResource("start.png"));
			next = new ImageIcon(this.getClass().getClassLoader().getResource("next.png"));
			manager1 = new ImageIcon(this.getClass().getClassLoader().getResource("managerbutton.png"));
			manager2 = new ImageIcon(this.getClass().getClassLoader().getResource("managerbutton2.png"));
			manager3 = new ImageIcon(this.getClass().getClassLoader().getResource("managerbutton3.png"));
			user1 = new ImageIcon(this.getClass().getClassLoader().getResource("userbutton.png"));
			user2 = new ImageIcon(this.getClass().getClassLoader().getResource("userbutton2.png"));
			user3 = new ImageIcon(this.getClass().getClassLoader().getResource("userbutton3.png"));
			strawberry = new ImageIcon(this.getClass().getClassLoader().getResource("strawberry.png"));
			pineapple = new ImageIcon(this.getClass().getClassLoader().getResource("pineapple.png"));
			watermelon = new ImageIcon(this.getClass().getClassLoader().getResource("watermelon.png"));
			kiwi = new ImageIcon(this.getClass().getClassLoader().getResource("kiwi.png"));
			delivery=new ImageIcon(this.getClass().getClassLoader().getResource("delivery.png"));
			deliveryx=new ImageIcon(this.getClass().getClassLoader().getResource("deliveryx.png"));
			update = new ImageIcon(this.getClass().getClassLoader().getResource("updatebutton.png"));
			takeorder = new ImageIcon(this.getClass().getClassLoader().getResource("takeorder.png"));
			employee = new ImageIcon(this.getClass().getClassLoader().getResource("employee.png"));
			cashier = new ImageIcon(this.getClass().getClassLoader().getResource("cashier.png"));
			orange = new ImageIcon(this.getClass().getClassLoader().getResource("orange.png"));
			grape = new ImageIcon(this.getClass().getClassLoader().getResource("grape.png"));
			close = new ImageIcon(this.getClass().getClassLoader().getResource("closebutton.png"));

			//화면 비율에 맞게 이미지 크기 조정
			home = changeImageSize(home, 20, 12);
			back = changeImageSize(back, 20, 12);
			next = changeImageSize(next, 12, 12);
			manager1 = changeImageSize(manager1, 5, 5);
			manager2 = changeImageSize(manager2, 5, 5);
			manager3 = changeImageSize(manager3, 5, 5);
			user1 = changeImageSize(user1, 5, 5);
			user2 = changeImageSize(user2, 5, 5);
			user3 = changeImageSize(user3, 5, 5);
			delivery = changeImageSize(delivery, 4, 2);
			deliveryx = changeImageSize(deliveryx, 4, 2);
			update = changeImageSize(update, 22, 13);
			takeorder = changeImageSize(takeorder, 8, 14);
			employee = changeImageSize(employee, 4, 2);
			cashier = changeImageSize(cashier, 4, 2);
			close = changeImageSize(close, 4, 3);

			/** 박소연*/
			//home 페이지 패널 생성
			cardhome=new JPanel();
			cardhome.setLayout(null);

			/** 배소연*/
			//home 페이지에 배경 그리기
			main = new JPanel(){
				public void paint(Graphics g) {
					g.drawImage(start.getImage(), 0, 0, W, H, null);
				}
			};
			main.setBounds(0, 0, getWidth(), getHeight());

			//homebutton 생성
			homebutton = new JButton(home);
			homebutton.setBounds(W/200, H/100, W/20, H/12);
			homebutton.setBorderPainted(false);

			//backbutton 생성
			backbutton = new JButton(back);
			backbutton.setBounds(12*W/200, H/100, W/20, H/12);
			backbutton.setBorderPainted(false);

			//managerbutton 생성
			managerbutton = new JButton(manager1);
			managerbutton.setBorderPainted(false);  //경계선 없앰
			managerbutton.setRolloverIcon(manager2);  //마우스 올리면 변하게
			managerbutton.setPressedIcon(manager3);  //클릭할 때 변하게
			managerbutton.setBounds(W/2, 5*H/9, W/5, H/5);  //x, y, width, height
			managerbutton.addActionListener(this);

			//userbutton 생성
			userbutton = new JButton(user1);
			userbutton.setBorderPainted(false);
			userbutton.setRolloverIcon(user2);
			userbutton.setPressedIcon(user3);
			userbutton.setBounds(3*W/4, 5*H/9, W/5, H/5);
			userbutton.addActionListener(this);

			//cardhome에 버튼과 패널을 붙인다
			cardhome.add(homebutton);
			cardhome.add(backbutton);
			cardhome.add(managerbutton);
			cardhome.add(userbutton);
			cardhome.add(main);

			//home카드를 card에 붙인다
			card.add("home",cardhome);

			/*user 선택 시 - u1 page*/
			/** 박소연*/
			//card layout에 붙일 새로운 패널 u1 생성
			card_u1 = new JPanel();
			card_u1.setLayout(null);

			/** 배소연*/
			//u1 card의 배경 및 위치 설정
			page = new JPanel() {
				public void paint(Graphics g) {
					g.drawImage(pineapple.getImage(), 0, 0, W, H, null);
				}
			};
			page.setBounds(0, 0, getWidth(), getHeight());

			//homebutton 생성
			homebutton_u1 = new JButton(home);
			homebutton_u1.setBounds(W/200, H/100, W/20, H/12);
			homebutton_u1.setBorderPainted(false);
			homebutton_u1.addActionListener(this);

			//backbutton 생성
			backbutton_u1 = new JButton(back);
			backbutton_u1.setBounds(12*W/200, H/100, W/20, H/12);
			backbutton_u1.setBorderPainted(false);
			backbutton_u1.addActionListener(this);

			//deliverbutton 생성
			deliverbutton = new JButton(delivery);
			deliverbutton.setBounds(W/18, H/4, W/4, H/2);
			deliverbutton.setBorderPainted(false);
			deliverbutton.setContentAreaFilled(false);
			deliverbutton.addActionListener(this);

			//nodeliverbutton 생성
			nodeliverbutton = new JButton(deliveryx);
			nodeliverbutton.setBounds(W/3, H/4, W/4, H/2);
			nodeliverbutton.setBorderPainted(false);
			nodeliverbutton.setContentAreaFilled(false);
			nodeliverbutton.addActionListener(this);

			//card_u1에 버튼과 패널을 붙인다
			card_u1.add(homebutton_u1);
			card_u1.add(backbutton_u1);
			card_u1.add(deliverbutton);
			card_u1.add(nodeliverbutton);
			card_u1.add(page);

			//u1카드를 card에 붙인다
			card.add("u1",card_u1);

			/*u2 page */
			/** 박소연*/
			//card layout에 붙일 새로운 패널 u2 생성
			card_u2 = new JPanel();
			card_u2.setLayout(null);

			/** 배소연*/
			//u2 card의 배경 및 위치 설정
			page = new JPanel() {
				public void paint(Graphics g) {
					g.drawImage(strawberry.getImage(), 0, 0, W, H, null);
				}
			};
			page.setBounds(0, 0, getWidth(), getHeight());

			/** 박소연*/
			//order에 전달 될 JLabel 초기화
			restaurant_name=new JLabel("");
			food_type=new JLabel("");
			food_name=new JLabel("");
			food_price=new JLabel("");
			restaurant_num=new JLabel("");

			//radio button들을 담을 패널
			JPanel scorepanel=new JPanel(new GridLayout(0,5));
			JPanel pricepanel=new JPanel(new GridLayout(0,3));
			JPanel regionpanel=new JPanel(new GridLayout(0,3));
			JPanel removepanel = new JPanel(new GridLayout(0,4));
			JPanel parkingpanel = new JPanel(new GridLayout(0,3));
			JPanel accompanypanel = new JPanel(new GridLayout(0,2));
			JPanel typepanel = new JPanel(new GridLayout(0,4));
			JPanel recommendpanel = new JPanel(new GridLayout(0,4));

			//검색 옵션 radio button
			score_radio=new JRadioButton("Score Option");
			price_radio=new JRadioButton("Price Option");
			region_radio=new JRadioButton("Region Option");
			remove_radio = new JRadioButton("I don't want this ingredient!");
			park_radio = new JRadioButton("Parking Option");
			accompany_radio = new JRadioButton("Accompany Option");
			type_radio = new JRadioButton("Food Type");
			recommend_radio = new JRadioButton("Recommend For");

			//radio button들의 배경을 투명으로 설정하고 글씨체 설정
			score_radio.setOpaque(false);
			price_radio.setOpaque(false);
			region_radio.setOpaque(false);
			remove_radio.setOpaque(false);
			park_radio.setOpaque(false);
			accompany_radio.setOpaque(false);
			type_radio.setOpaque(false);
			recommend_radio.setOpaque(false);
			score_radio.setFont(new Font("Britannic Bold",Font.PLAIN,H/35));
			region_radio.setFont(new Font("Britannic Bold",Font.PLAIN,H/35));
			price_radio.setFont(new Font("Britannic Bold",Font.PLAIN,H/35));
			remove_radio.setFont(new Font("Britannic Bold",Font.PLAIN,H/35));
			park_radio.setFont(new Font("Britannic Bold",Font.PLAIN,H/35));
			accompany_radio.setFont(new Font("Britannic Bold",Font.PLAIN,H/35));
			type_radio.setFont(new Font("Britannic Bold",Font.PLAIN,H/35));
			recommend_radio.setFont(new Font("Britannic Bold",Font.PLAIN,H/35));

			//옵션 radio button을 그룹으로 묶는다.
			ButtonGroup detail=new ButtonGroup();
			detail.add(price_radio);detail.add(region_radio);
			detail.add(remove_radio);detail.add(park_radio);
			detail.add(accompany_radio);detail.add(type_radio);
			detail.add(recommend_radio);detail.add(score_radio);

			//옵션을 담을 패널
			JPanel score_r=new JPanel();
			JPanel region_r=new JPanel();
			JPanel price_r=new JPanel();
			JPanel remove_r=new JPanel();
			JPanel parking_r=new JPanel();
			JPanel accompany_r=new JPanel();
			JPanel type_r=new JPanel();
			JPanel recommend_r=new JPanel();

			//옵션을 담을 패널의 배경색을 흰색으로 설정
			price_r.setBackground(Color.white);region_r.setBackground(Color.white);
			remove_r.setBackground(Color.white);parking_r.setBackground(Color.white);
			accompany_r.setBackground(Color.white);type_r.setBackground(Color.white);
			recommend_r.setBackground(Color.white);score_r.setBackground(Color.white);

			//score옵션의 radiobutton을 초기화
			r0=new JRadioButton("ALL");
			r2=new JRadioButton("over 2");
			r4=new JRadioButton("over 4");
			r6=new JRadioButton("over 6");
			r8=new JRadioButton("over 8");

			//패널에 score 옵션의 radiobutton들을 붙인다.
			scorepanel.add(r0);scorepanel.add(r2);
			scorepanel.add(r4);scorepanel.add(r6);scorepanel.add(r8);

			//score 옵션의 radiobutton들을 그룹으로 묶는다.
			ButtonGroup scoregroup=new ButtonGroup();
			scoregroup.add(r0);scoregroup.add(r2);
			scoregroup.add(r4);scoregroup.add(r6);scoregroup.add(r8);

			//region 옵션의 checkbox을 초기화
			Seocho= new Checkbox("Seocho");
			Jongno= new Checkbox("Jongno");
			Seongdong= new Checkbox("Seongdong");
			Songpa= new Checkbox("Songpa");
			Gangnam= new Checkbox("Gangnam");
			Yongsan= new Checkbox("Yongsan");

			//region 옵션의 radiobutton들을 패널에 붙인다.
			regionpanel.add(Seocho);regionpanel.add(Jongno);
			regionpanel.add(Seongdong);regionpanel.add(Songpa);
			regionpanel.add(Gangnam);regionpanel.add(Yongsan);

			//price 옵션의 radiobutton들을 초기화
			r5000= new JRadioButton("~5000 won");
			r10000= new JRadioButton("~10000 won");
			r15000= new JRadioButton("~15000 won");
			r20000= new JRadioButton("~20000 won");
			r25000= new JRadioButton("~25000 won");
			r30000= new JRadioButton("~30000 won");

			//price 옵션의 radiobutton들을 패널에 붙인다.
			pricepanel.add(r5000);pricepanel.add(r10000);
			pricepanel.add(r15000);pricepanel.add(r20000);
			pricepanel.add(r25000);pricepanel.add(r30000);

			//price 옵션의 radiobutton들을 그룹으로 묶는다.
			ButtonGroup pricegroup=new ButtonGroup();
			pricegroup.add(r5000); pricegroup.add(r10000); pricegroup.add(r15000);
			pricegroup.add(r20000); pricegroup.add(r25000); pricegroup.add(r30000);

			//parking 옵션의 radiobutton들을 초기화
			park = new JRadioButton("Parking");
			nopark = new JRadioButton("NO Parking");
			nomatter = new JRadioButton("NO matter");

			//parking 옵션의 radiobutton들을 패널에 붙인다.
			parkingpanel.add(park);parkingpanel.add(nopark);parkingpanel.add(nomatter);

			//버튼에 actionlistener을 붙인다.
			park.addActionListener(this); nopark.addActionListener(this);
			nomatter.addActionListener(this);

			//park 옵션의 radiobutton을 그룹으로 묶는다.
			ButtonGroup park_gr=new ButtonGroup();
			park_gr.add(park); park_gr.add(nopark); park_gr.add(nomatter);

			//service 옵션의 checkbox를 초기화
			baby = new Checkbox("with Baby");
			pet = new Checkbox("with pet");

			//패널에 service 옵션의 checkbox를 붙인다.
			accompanypanel.add(baby); accompanypanel.add(pet);

			//ingredient 옵션의 checkbox를 초기화
			meat = new Checkbox("meat");
			seafood = new Checkbox("seafood");
			dairy = new Checkbox("dairy");
			fungus = new Checkbox("fungus");
			vegetable = new Checkbox("vegetable");
			grain = new Checkbox("grain");
			fruit = new Checkbox("fruit");

			//ingredient 옵션의 radiobutton들을 패널에 붙인다.
			removepanel.add(meat); removepanel.add(seafood); removepanel.add(dairy);
			removepanel.add(fungus); removepanel.add(vegetable); removepanel.add(grain);
			removepanel.add(fruit);

			//recommend 옵션의 checkbox를 초기화
			family = new Checkbox("family");
			date = new Checkbox("date");
			friends = new Checkbox("friends");
			recommendpanel.add(family);recommendpanel.add(friends);recommendpanel.add(date);

			//foodtype 옵션의 checkbox를 초기화
			oriental = new Checkbox("oriental");
			western = new Checkbox("western");
			dessert = new Checkbox("dessert");

			//패널에 food type 옵션의 checkbox를 붙인다.
			typepanel.add(oriental);typepanel.add(western);typepanel.add(dessert);

			//패널에 recommend 옵션의 checkbox와 옵션들을 붙인다.
			score_r.add(score_radio);
			price_r.add(price_radio);
			region_r.add(region_radio);
			remove_r.add(remove_radio);
			parking_r.add(park_radio);
			accompany_r.add(accompany_radio);
			type_r.add(type_radio);
			recommend_r.add(recommend_radio);

			//radio button 묶음을 붙일 패널 - y축으로 붙이는 box layout으로 설정
			radiobuttons = new JPanel();
			radiobuttons2=new JPanel();
			radiobuttons.setLayout(new BoxLayout(radiobuttons,BoxLayout.Y_AXIS));
			radiobuttons2.setLayout(new BoxLayout(radiobuttons2,BoxLayout.Y_AXIS));

			//radiobutton과 checkbox의 글씨 설정
			Seocho.setFont(new Font("Britannic Bold",Font.PLAIN,H/40));
			Jongno.setFont(new Font("Britannic Bold",Font.PLAIN,H/40));
			Seongdong.setFont(new Font("Britannic Bold",Font.PLAIN,H/40));
			Songpa.setFont(new Font("Britannic Bold",Font.PLAIN,H/40));
			Gangnam.setFont(new Font("Britannic Bold",Font.PLAIN,H/40));
			Yongsan.setFont(new Font("Britannic Bold",Font.PLAIN,H/40));
			r5000.setFont(new Font("Courier",Font.PLAIN,H/40));
			r10000.setFont(new Font("Courier",Font.PLAIN,H/40));
			r15000.setFont(new Font("Courier",Font.PLAIN,H/40));
			r20000.setFont(new Font("Courier",Font.PLAIN,H/40));
			r25000.setFont(new Font("Courier",Font.PLAIN,H/40));
			r30000.setFont(new Font("Courier",Font.PLAIN,H/40));
			r0.setFont(new Font("Courier",Font.PLAIN,H/40));
			r2.setFont(new Font("Courier",Font.PLAIN,H/40));
			r4.setFont(new Font("Courier",Font.PLAIN,H/40));
			r6.setFont(new Font("Courier",Font.PLAIN,H/40));
			r8.setFont(new Font("Courier",Font.PLAIN,H/40));
			meat.setFont(new Font("Courier",Font.PLAIN,H/40));
			seafood.setFont(new Font("Courier",Font.PLAIN,H/40));
			dairy.setFont(new Font("Courier",Font.PLAIN,H/40));
			fungus.setFont(new Font("Courier",Font.PLAIN,H/40));
			vegetable.setFont(new Font("Courier",Font.PLAIN,H/40));
			grain.setFont(new Font("Courier",Font.PLAIN,H/40));
			fruit.setFont(new Font("Courier",Font.PLAIN,H/40));
			park.setFont(new Font("Courier",Font.PLAIN,H/40));
			nopark.setFont(new Font("Courier",Font.PLAIN,H/40));
			nomatter.setFont(new Font("Courier",Font.PLAIN,H/40));
			pet.setFont(new Font("Courier",Font.PLAIN,H/40));
			baby.setFont(new Font("Courier",Font.PLAIN,H/40));
			family.setFont(new Font("Courier",Font.PLAIN,H/40));
			date.setFont(new Font("Courier",Font.PLAIN,H/40));
			friends.setFont(new Font("Courier",Font.PLAIN,H/40));
			oriental.setFont(new Font("Courier",Font.PLAIN,H/40));
			western.setFont(new Font("Courier",Font.PLAIN,H/40));
			dessert.setFont(new Font("Courier",Font.PLAIN,H/40));

			//패널에 라디오버튼과 체크박스가 들어있는 패널들을 붙인다.
			textpanel_u = new JPanel();
			textpanel_u2 = new JPanel();
			radiobuttons.add(score_r);
			radiobuttons.add(scorepanel);
			radiobuttons.add(region_r);
			radiobuttons.add(regionpanel);
			radiobuttons.add(price_r);
			radiobuttons.add(pricepanel);
			radiobuttons.add(remove_r);
			radiobuttons.add(removepanel);
			radiobuttons.add(type_r);
			radiobuttons.add(typepanel);
			radiobuttons.add(recommend_r);
			radiobuttons.add(recommendpanel);
			radiobuttons2.add(accompany_r);
			radiobuttons2.add(accompanypanel);
			radiobuttons2.add(parking_r);
			radiobuttons2.add(parkingpanel);

			//라디오 버튼과 체크박스가 붙여져 있는 패널을 새로운 패널에 붙이고 배경 및 위치 설정
			textpanel_u.setLayout(new BoxLayout(textpanel_u,BoxLayout.Y_AXIS));
			textpanel_u.add(radiobuttons);
			textpanel_u2.add(radiobuttons2);
			radiobuttons.setBackground(Color.white);
			radiobuttons2.setBackground(Color.white);
			textpanel_u.setOpaque(false);
			textpanel_u2.setOpaque(false);
			textpanel_u.setBounds(W/50, H/10, 3*W/7, 3*H/4);
			textpanel_u2.setBounds(19*W/40, 8*H/12, W/3, H/5);

			//not delivery일 때 선택하는 버튼들을 알리는 label
			JLabel onlydelivery=new JLabel("Only for No Delivery");
			onlydelivery.setFont(new Font("Britannic Bold",Font.PLAIN,H/28));
			onlydelivery.setOpaque(false);
			onlydelivery.setBounds(22*W/40,15*H/24,W/3,H/20);
			onlydelivery.setBackground(Color.white);

			/** 배소연*/
			//table 위의 안내 message label
			//선택 전
			select = new JLabel("Select!", SwingConstants.CENTER);
			select.setFont(new Font("Britannic Bold",Font.PLAIN,H/15));
			select.setBounds(53*W/100, H/70, W/5, H/15);

			//에러가 났을 때
			try_again = new JLabel("Try Again!", SwingConstants.CENTER);
			try_again.setFont(new Font("Britannic Bold",Font.PLAIN,H/20));
			try_again.setBounds(53*W/100, H/70, W/5, H/15);
			try_again.setVisible(false);

			//선택이 성공적으로 완료되었을 때
			success = new JLabel("Success!", SwingConstants.CENTER);
			success.setFont(new Font("Britannic Bold",Font.PLAIN,H/18));
			success.setBounds(53*W/100, H/70, W/5, H/15);
			success.setVisible(false);

			//homebutton 생성
			homebutton_u2 = new JButton(home);
			homebutton_u2.setBounds(W/200, H/100, W/20, H/12);
			homebutton_u2.setBorderPainted(false);
			homebutton_u2.addActionListener(this);

			//backbutton 생성
			backbutton_u2 = new JButton(back);
			backbutton_u2.setBounds(12*W/200, H/100, W/20, H/12);
			backbutton_u2.setBorderPainted(false);
			backbutton_u2.addActionListener(this);

			//nextbutton 생성
			nextbutton_u2 = new JButton(next);
			nextbutton_u2.setBounds(8*W/9, H/100, W/12, H/12);
			nextbutton_u2.setBorderPainted(false);
			nextbutton_u2.setContentAreaFilled(false);
			nextbutton_u2.addActionListener(this);

			//updatebutton 생성
			updatebutton = new JButton(update);
			updatebutton.setBounds(15*W/18, H/100, W/22, H/13);
			updatebutton.setBorderPainted(false);
			updatebutton.setContentAreaFilled(false);
			updatebutton.addActionListener(this);

			//table 패널 생성
			tablepanel_u = new JPanel();

			//card_u2에 버튼과 패널을 붙인다
			card_u2.add(onlydelivery);
			card_u2.add(textpanel_u);
			card_u2.add(textpanel_u2);
			card_u2.add(homebutton_u2);
			card_u2.add(backbutton_u2);
			card_u2.add(nextbutton_u2);
			card_u2.add(updatebutton);
			card_u2.add(select);
			card_u2.add(try_again);
			card_u2.add(success);
			card_u2.add(tablepanel_u);
			card_u2.add(page);

			//card_u2 카드를 card에 붙인다
			card.add("u2", card_u2);

			/*u3 화면*/
			/** 박소연*/
			//card layout에 붙일 새로운 패널 u3 생성
			card_u3 = new JPanel();
			card_u3.setLayout(null);

			/** 배소연*/
			//u3의 배경 및 위치 설정
			page = new JPanel() {
				public void paint(Graphics g) {
					g.drawImage(kiwi.getImage(), 0, 0, W, H, null);
				}
			};
			page.setBounds(0, 0, getWidth(), getHeight());

			/** 박소연*/
			//order 확인 label과 패널
			JPanel orderpanel=new JPanel();
			JLabel column0=new JLabel("RESTAURANT NAME: ");
			column0.setFont(new Font("Britannic Bold",Font.BOLD,H/35));
			JLabel column1=new JLabel("FOOD TYPE: ");
			column1.setFont(new Font("Britannic Bold",Font.BOLD,H/35));
			JLabel column2=new JLabel("FOOD NAME: ");
			column2.setFont(new Font("Britannic Bold",Font.BOLD,H/35));
			JLabel column3=new JLabel("FOOD PRICE: ");
			column3.setFont(new Font("Britannic Bold",Font.BOLD,H/35));
			JLabel column4=new JLabel("RESTAURANT NUMBER: ");
			column4.setFont(new Font("Britannic Bold",Font.BOLD,H/35));
			delivery_cost_label=new JLabel("");
			delivery_cost=new JLabel("");
			delivery_cost_label.setFont(new Font("Britannic Bold",Font.BOLD,H/35));

			//label을 패널에 붙인다.
			orderpanel.add(column0);orderpanel.add(restaurant_name);
			orderpanel.add(column1);orderpanel.add(food_type);
			orderpanel.add(column2);orderpanel.add(food_name);
			orderpanel.add(column3);orderpanel.add(food_price);
			orderpanel.add(column4);orderpanel.add(restaurant_num);
			orderpanel.add(delivery_cost_label); orderpanel.add(delivery_cost);
			orderpanel.setLayout(new BoxLayout(orderpanel,BoxLayout.Y_AXIS));
			orderpanel.setBounds(W/13,13*H/40,W/3,H/2);
			orderpanel.setOpaque(false);

			/** 배소연*/
			//재료가 없을 때 뜨는 X label 생성
			x = new JLabel("X");
			x.setFont(new Font("Courier",Font.BOLD,2*H/3));
			x.setForeground(Color.RED);
			x.setBounds(W/13,H/4,W/3,2*H/3);
			x.setVisible(false);

			//sorry, no ingredient label 생성
			noingredient = new JLabel("Sorry, No Ingredient");
			noingredient.setFont(new Font("Britannic Bold",Font.BOLD,H/10));
			noingredient.setBounds(2*W/13,H/20,W,H/10);
			noingredient.setVisible(false);

			//complete order label 생성
			complete = new JLabel("Complete Order!");
			complete.setFont(new Font("Britannic Bold",Font.BOLD,H/10));
			complete.setBounds(2*W/13,H/20,W,H/10);

			//homebutton 생성
			homebutton_u3 = new JButton(home);
			homebutton_u3.setBounds(W/200, H/100, W/20, H/12);
			homebutton_u3.setBorderPainted(false);
			homebutton_u3.addActionListener(this);

			//backbutton 생성
			backbutton_u3 = new JButton(back);
			backbutton_u3.setBounds(12*W/200, H/100, W/20, H/12);
			backbutton_u3.setBorderPainted(false);
			backbutton_u3.addActionListener(this);

			//card_u3에 버튼과 패널을 붙인다
			card_u3.add(homebutton_u3);
			card_u3.add(backbutton_u3);
			card_u3.add(orderpanel);
			card_u3.add(noingredient);
			card_u3.add(complete);
			card_u3.add(x);
			card_u3.add(page);

			//card_u3 카드를 card에 붙인다
			card.add("u3", card_u3);

			/*manager 선택 시 - m1 page*/
			/** 배소연*/
			//card layout에 붙일 새로운 패널 m1 생성
			card_m1 = new JPanel();
			card_m1.setLayout(null);

			//m1 card의 배경 및 위치 설정
			page = new JPanel() {
				public void paint(Graphics g) {
					g.drawImage(watermelon.getImage(), 0, 0, W, H, null);
				}
			};
			page.setBounds(0, 0, getWidth(), getHeight());

			//homebutton 생성
			homebutton_m1 = new JButton(home);
			homebutton_m1.setBounds(W/200, H/100, W/20, H/12);
			homebutton_m1.setBorderPainted(false);
			homebutton_m1.addActionListener(this);

			//backbutton 생성
			backbutton_m1 = new JButton(back);
			backbutton_m1.setBounds(12*W/200, H/100, W/20, H/12);
			backbutton_m1.setBorderPainted(false);
			backbutton_m1.addActionListener(this);

			//employee management 버튼 생성
			employeebutton = new JButton(employee);
			employeebutton.setBounds(W/18, H/4, W/4, H/2);
			employeebutton.setBorderPainted(false);
			employeebutton.setContentAreaFilled(false);
			employeebutton.addActionListener(this);

			//order processing 버튼 생성
			orderprbutton = new JButton(cashier);
			orderprbutton.setBounds(W/3, H/4, W/4, H/2);
			orderprbutton.setBorderPainted(false);
			orderprbutton.setContentAreaFilled(false);
			orderprbutton.addActionListener(this);

			//card_m1에 버튼과 패널을 붙인다
			card_m1.add(homebutton_m1);
			card_m1.add(backbutton_m1);
			card_m1.add(employeebutton);
			card_m1.add(orderprbutton);
			card_m1.add(page);

			//card_m1 카드를 card에 붙인다
			card.add("m1", card_m1);

			/*m2 page*/
			/** 배소연 */
			//card layout에 붙일 새로운 패널 m2 생성
			card_m2 = new JPanel();
			card_m2.setLayout(null);

			//m2 card의 배경 및 위치 설정
			page = new JPanel() {
				public void paint(Graphics g) {
					g.drawImage(orange.getImage(), 0, 0, W, H, null);
				}
			};
			page.setBounds(0, 0, getWidth(), getHeight());

			//homebutton 생성
			homebutton_m2 = new JButton(home);
			homebutton_m2.setBounds(W/200, H/100, W/20, H/12);
			homebutton_m2.setBorderPainted(false);
			homebutton_m2.addActionListener(this);

			//backbutton 생성
			backbutton_m2 = new JButton(back);
			backbutton_m2.setBounds(12*W/200, H/100, W/20, H/12);
			backbutton_m2.setBorderPainted(false);
			backbutton_m2.addActionListener(this);

			//employee 테이블을 그릴 패널 생성
			tablepanel_emp = new JPanel();

			/** 박소연*/
			//각 패널의 역할을 알리는 label과 그 label의 글씨 설정
			JLabel bonuslabel=new JLabel("Change Salary!");
			bonuslabel.setFont(new Font("Britannic Bold",Font.PLAIN,H/30));
			JLabel crisislabel_up=new JLabel("Fire employee!");
			crisislabel_up.setFont(new Font("Britannic Bold",Font.PLAIN,H/25));
			JLabel crisislabel_down=new JLabel("salary over ");
			crisislabel_down.setFont(new Font("Britannic Bold",Font.PLAIN,H/25));
			JLabel inputlabel_up=new JLabel("Input :");
			inputlabel_up.setFont(new Font("Britannic Bold",Font.PLAIN,H/35));
			JLabel inputlabel_down=new JLabel("Input :");
			inputlabel_down.setFont(new Font("Britannic Bold",Font.PLAIN,H/35));
			JLabel inclabel_p=new JLabel("increase payment");
			inclabel_p.setFont(new Font("Britannic Bold",Font.PLAIN,H/40));
			JLabel declabel_p=new JLabel("decrease payment");
			declabel_p.setFont(new Font("Britannic Bold",Font.PLAIN,H/40));

			//안내메세지 label 설정
			inform_emp_up_1=new JLabel("Check & Change");
			inform_emp_up_1.setFont(new Font("Britannic Bold",Font.PLAIN,H/18));
			inform_emp_up_1.setBounds(W/40, 21*H/100, W/3, H/18);
			inform_emp_up_1.setVisible(true);

			inform_emp_down_1=new JLabel("Employee List!");
			inform_emp_down_1.setFont(new Font("Britannic Bold",Font.PLAIN,H/15));
			inform_emp_down_1.setBounds(W/50, 7*H/25, W/4, H/15);
			inform_emp_down_1.setVisible(true);

			inform_emp_up_done=new JLabel("Done!");
			inform_emp_up_done.setFont(new Font("Britannic Bold",Font.PLAIN,H/10));
			inform_emp_up_done.setBounds(W/15, H/6, W/4, H/10);
			inform_emp_up_done.setVisible(false);

			inform_emp_down_done=new JLabel("Identify Table!");
			inform_emp_down_done.setFont(new Font("Britannic Bold",Font.PLAIN,H/15));
			inform_emp_down_done.setBounds(W/40, 7*H/25, W/4, H/15);
			inform_emp_down_done.setVisible(false);

			inform_emp_error=new JLabel("Error!");
			inform_emp_error.setFont(new Font("Britannic Bold",Font.PLAIN,H/10));
			inform_emp_error.setBounds(W/15, H/6, W/4, H/10);
			inform_emp_error.setVisible(false);

			inform_emp_down_format=new JLabel("Input Properly!");
			inform_emp_down_format.setFont(new Font("Britannic Bold",Font.PLAIN,H/16));
			inform_emp_down_format.setBounds(W/45, 7*H/25, W/4, H/16);
			inform_emp_down_format.setVisible(false);

			inform_emp_down_errtable=new JLabel("Select Table!");
			inform_emp_down_errtable.setFont(new Font("Britannic Bold",Font.PLAIN,H/15));
			inform_emp_down_errtable.setBounds(W/35, 7*H/25, W/4, H/15);
			inform_emp_down_errtable.setVisible(false);

			/** 배소연*/
			inform_emp_select = new JLabel("Select Button!");
			inform_emp_select.setFont(new Font("Britannic Bold",Font.PLAIN,H/15));
			inform_emp_select.setBounds(W/45, 7*H/25, W/4, H/15);
			inform_emp_select.setVisible(false);

			inform_emp_insert = new JLabel("Insert!");
			inform_emp_insert.setFont(new Font("Britannic Bold",Font.PLAIN,H/10));
			inform_emp_insert.setBounds(W/17, 7*H/25, W/4, H/10);
			inform_emp_insert.setVisible(false);


			//table로부터 받아오거나 table을 반영시킬 정보를 담을 패널
			infopanel=new JPanel();
			infopanel.setLayout(new BoxLayout(infopanel,BoxLayout.Y_AXIS));
			bonuspanel=new JPanel();crisispanel=new JPanel();
			bonuspanel.setLayout(new BoxLayout(bonuspanel,BoxLayout.Y_AXIS));
			crisispanel.setLayout(new BoxLayout(crisispanel,BoxLayout.Y_AXIS));
			JPanel info_radiopanel=new JPanel(new GridLayout(0,4));
			JPanel info_tablepanel=new JPanel(new GridLayout(0,5));
			JPanel pluspanel=new JPanel(new GridLayout(0,3));
			JPanel minuspanel=new JPanel(new GridLayout(0,3));
			JPanel dismisspanel=new JPanel(new GridLayout(0,3));

			//update, delete, insert 선택할 버튼과 그 글씨체를 설정하고 그룹으로 묶는다.
			ButtonGroup infogroup=new ButtonGroup();
			update_radio=new JRadioButton("update"); update_radio.addActionListener(this);
			delete_radio=new JRadioButton("delete"); delete_radio.addActionListener(this);
			insert_radio=new JRadioButton("insert"); insert_radio.addActionListener(this);
			update_radio.setFont(new Font("Britannic Bold",Font.PLAIN,H/30));
			delete_radio.setFont(new Font("Britannic Bold",Font.PLAIN,H/30));
			insert_radio.setFont(new Font("Britannic Bold",Font.PLAIN,H/30));
			infogroup.add(update_radio); infogroup.add(delete_radio); infogroup.add(insert_radio);

			//테이블로부터 정보를 받아올 버튼
			getinfobutton=new JButton("GET INFO");
			getinfobutton.setFont(new Font("Britannic Bold",Font.PLAIN,H/30));
			getinfobutton.addActionListener(this);

			//테이블에 정보를 반영시킬 버튼
			enterinfobutton=new JButton("ENTER");
			enterinfobutton.setFont(new Font("Britannic Bold",Font.PLAIN,H/30));
			enterinfobutton.addActionListener(this);
			givebutton_up=new JButton("% UP");
			givebutton_up.setFont(new Font("Britannic Bold",Font.PLAIN,H/33));
			givebutton_up.addActionListener(this);
			givebutton_down=new JButton("% DOWN");
			givebutton_down.setFont(new Font("Britannic Bold",Font.PLAIN,H/33));
			givebutton_down.addActionListener(this);
			firebutton=new JButton("BYE!");
			firebutton.setFont(new Font("Britannic Bold",Font.PLAIN,H/30));
			firebutton.addActionListener(this);

			//information을 바꿀 옵션 radio button을 패널에 붙인다.
			info_radiopanel.add(update_radio);info_radiopanel.add(delete_radio);
			info_radiopanel.add(insert_radio);info_radiopanel.add(getinfobutton);

			//개인의 정보를 보여줄 textfield와 choice 생성
			name=new JTextField(W/9); name.addActionListener(this);
			name.setFont(new Font("Courier",Font.PLAIN,H/33));
			salary=new JTextField(W/9); salary.addActionListener(this);
			salary.setFont(new Font("Courier",Font.PLAIN,H/33));
			plus_salary=new JTextField(W/4); plus_salary.addActionListener(this);
			plus_salary.setFont(new Font("Courier",Font.PLAIN,H/30));
			minus_salary=new JTextField(W/4); minus_salary.addActionListener(this);
			minus_salary.setFont(new Font("Courier",Font.PLAIN,H/30));
			delete_salary=new JTextField(W/8); delete_salary.addActionListener(this);
			delete_salary.setFont(new Font("Courier",Font.PLAIN,H/30));
			c_position=new Choice(); c_position.addItemListener(this);
			c_res_name=new Choice(); c_res_name.addItemListener(this);
			c_position.add("chef");c_position.add("cook");
			c_position.add("manager");c_position.add("server");
			c_res_name.add("Nolboo");c_res_name.add("SchoolFood");
			c_res_name.add("Abiko");c_res_name.add("Yummy Sushi");
			c_res_name.add("Outback");c_res_name.add("VIPS");
			c_res_name.add("Ewhasung");c_res_name.add("BillyAngel");
			c_res_name.add("Sulbing");c_res_name.add("KFC");
			c_res_name.add("Shake Shack");
			c_position.setFont(new Font("Courier",Font.PLAIN,H/35));
			c_res_name.setFont(new Font("Courier",Font.PLAIN,H/35));

			//패널에 textfield, JButton, 그리고 choice를 붙인다.
			info_tablepanel.add(name);
			info_tablepanel.add(c_res_name);
			info_tablepanel.add(c_position);
			info_tablepanel.add(salary);
			info_tablepanel.add(enterinfobutton);
			pluspanel.add(inputlabel_up);
			pluspanel.add(plus_salary);pluspanel.add(givebutton_up);
			minuspanel.add(inputlabel_down);
			minuspanel.add(minus_salary);minuspanel.add(givebutton_down);
			bonuspanel.add(bonuslabel);
			bonuspanel.add(pluspanel);
			bonuspanel.add(minuspanel);
			dismisspanel.add(crisislabel_down);
			dismisspanel.add(delete_salary);dismisspanel.add(firebutton);
			crisispanel.add(crisislabel_up);crisispanel.add(dismisspanel);
			infopanel.add(info_radiopanel);infopanel.add(info_tablepanel);

			//panel의 위치 설정
			infopanel.setBounds(4*W/15, 13*H/20, 2*W/3, H/11);
			bonuspanel.setBounds(34*W/50, 19*H/25, W/4, H/7);
			crisispanel.setBounds(4*W/15, 19*H/25, 2*W/5, H/10);

			//카드에 안내메세지 label들을 붙이고 현재 보여줄 메세지만 true로 설정
			card_m2.add(inform_emp_up_1);
			card_m2.add(inform_emp_down_1);
			card_m2.add(inform_emp_error);
			card_m2.add(inform_emp_down_format);
			card_m2.add(inform_emp_down_errtable);
			card_m2.add(inform_emp_up_done);
			card_m2.add(inform_emp_down_done);
			card_m2.add(inform_emp_select);
			card_m2.add(inform_emp_insert);

			//card에 앞서 만든 패널들을 붙인다.
			card_m2.add(crisispanel);
			card_m2.add(infopanel);
			card_m2.add(bonuspanel);
			card_m2.add(homebutton_m2);
			card_m2.add(backbutton_m2);
			card_m2.add(tablepanel_emp);
			card_m2.add(page);
			card.add("m2", card_m2);

			/*m3 page*/
			/** 박소연*/
			//card layout에 붙일 새로운 패널 m3 생성
			card_m3 = new JPanel();
			card_m3.setLayout(null);

			/** 배소연*/
			//m3 card의 배경 및 위치 설정
			page = new JPanel() {
				public void paint(Graphics g) {
					g.drawImage(grape.getImage(), 0, 0, W, H, null);
				}
			};
			page.setBounds(0, 0, getWidth(), getHeight());

			//homebutton 생성
			homebutton_m3 = new JButton(home);
			homebutton_m3.setBounds(W/200, H/100, W/20, H/12);
			homebutton_m3.setBorderPainted(false);
			homebutton_m3.addActionListener(this);

			//backbutton 생성
			backbutton_m3 = new JButton(back);
			backbutton_m3.setBounds(12*W/200, H/100, W/20, H/12);
			backbutton_m3.setBorderPainted(false);
			backbutton_m3.addActionListener(this);

			//order table의 order를 차례대로 처리하는 takeorderbutton 생성
			takeorderbutton = new JButton(takeorder);
			takeorderbutton.setBounds(23*W/50, 9*H/20, W/8, H/14);
			takeorderbutton.setBorderPainted(false);
			takeorderbutton.setContentAreaFilled(false);
			takeorderbutton.addActionListener(this);

			//영업종료시 daily wage를 지급하고 close하는 closebutton 생성
			closebutton = new JButton(close);
			closebutton.setBounds(2*W/3, 23*H/40, W/4, H/3);
			closebutton.setBorderPainted(false);
			closebutton.setContentAreaFilled(false);
			closebutton.addActionListener(this);

			//order table을 위한 패널
			tablepanel_order = new JPanel();

			//restaurant 별 profit을 보여주는 table을 위한 패널
			tablepanel_profit = new JPanel();

			//thisis label 생성
			thisis = new JLabel("This is");
			thisis.setFont(new Font("Britannic Bold",Font.PLAIN,H/10));
			thisis.setBounds(39*W/100, 29*H/50, W/5, H/10);

			//order list label 생성
			order = new JLabel("Order List!");
			order.setFont(new Font("Britannic Bold",Font.PLAIN,H/10));
			order.setBounds(17*W/50, 69*H/100, W/3, H/10);

			//today's profit label 생성
			profit = new JLabel("Today's profit!");
			profit.setFont(new Font("Britannic Bold",Font.PLAIN,H/11));
			profit.setBounds(16*W/50, 69*H/100, 2*W/5, H/11);
			profit.setVisible(false);

			//order table의 order가 아직 남았는데 closebutton을 누른 경우 뜨는 label
			empty = new JLabel("Empty the list!");
			empty.setFont(new Font("Britannic Bold",Font.PLAIN,H/10));
			empty.setBounds(29*W/100, 64*H/100, 2*W/5, H/10);
			empty.setVisible(false);

			//card_m3에 버튼과 패널을 붙인다
			card_m3.add(homebutton_m3);
			card_m3.add(backbutton_m3);
			card_m3.add(takeorderbutton);
			card_m3.add(closebutton);
			card_m3.add(tablepanel_order);
			card_m3.add(tablepanel_profit);
			card_m3.add(thisis);
			card_m3.add(order);
			card_m3.add(profit);
			card_m3.add(empty);
			card_m3.add(page);

			//card_m3 카드를 card에 붙인다
			card.add("m3", card_m3);

			//전체 card를 add
			add(card);
			//x를 누르면 프로그램과 함께 종료하도록 설정
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setVisible(true); //visible하게 설정한다.
		}

		/*버튼 효과 처리 method*/
		public void actionPerformed(ActionEvent e) {
			/** 배소연 */
			//home 페이지로 가는 경우
			if(e.getSource() == homebutton_u1 || e.getSource() == homebutton_u2 || e.getSource() == homebutton_u3 || e.getSource() == homebutton_m1 || e.getSource() == homebutton_m2 || e.getSource() == homebutton_m3 || e.getSource() == backbutton_u1 || e.getSource() == backbutton_m1) {
				layout.show(card, "home");
			}
			//m1 페이지로 가는 경우
			else if(e.getSource()==managerbutton || e.getSource() == backbutton_m2 || e.getSource() == backbutton_m3){
				layout.show(card, "m1");
			}
			//u1 페이지로 가는 경우
			else if(e.getSource() == userbutton || e.getSource() == backbutton_u2){
				layout.show(card, "u1");
			}
			//u2 페이지로 가는 경우
			else if(e.getSource() == backbutton_u3) {
				layout.show(card, "u2");
			}
			/** 박소연*/
			else if(e.getSource() == employeebutton) {
				//employee 버튼을 누르면 안내메세지가 보이도록 설정
				inform_emp_up_1.setVisible(true);
				inform_emp_down_1.setVisible(true);
				inform_emp_up_done.setVisible(false);
				inform_emp_down_done.setVisible(false);
				inform_emp_down_errtable.setVisible(false);
				inform_emp_error.setVisible(false);
				inform_emp_down_format.setVisible(false);
				inform_emp_select.setVisible(false);
				inform_emp_insert.setVisible(false);

				//table의 column name을 초기화
				String[] colNames = {"ID", "name", "restaurant name", "position","salary(daily)", };
				//table을 update하도록 비운다.
				model = new DefaultTableModel(colNames, 0) {
					public Class<?> getColumnClass(int column) {
						if (column == 4) {
							return Integer.class;
						}
						return super.getColumnClass(column);
					}
				};
				try {
					//DBCOURSE_Employee로부터 정보를 가져온다.
					ResultSet rs = stmt.executeQuery("select * from DBCOURSE_Employee");
					while(rs.next()) {
						Object data[] = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)};
						model.addRow(data);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				//employee table을 보여준다
				showTable_emp();
				//m2 card를 보여준다
				layout.show(card, "m2");
			}
			/** 배소연*/
			//manager모드의 order processing 버튼을 누른 경우
			else if(e.getSource() == orderprbutton) {
				//order table에 들어갈 attribute array
				String[] colNames = {"number", "restaurant_name", "food_name", "price"};
				//DefaultTableModel 생성 - int 형의 column의 정렬을 위해 override
				model = new DefaultTableModel(colNames, 0) {
					public Class<?> getColumnClass(int column) {
						if (column == 4) {
							return Integer.class;
						}
						return super.getColumnClass(column);
					}
				};
				//profit을 보여주는 table에 들어갈 attribute array
				String[] colNames2 = {"restaurant_name", "profit"};
				//DefaultTableModel 생성 - int 형의 column의 정렬을 위해 override
				model2 = new DefaultTableModel(colNames2, 0) {
					public Class<?> getColumnClass(int column) {
						if (column == 1) {
							return Integer.class;
						}
						return super.getColumnClass(column);
					}
				};
				try {
					//order table의 모든 tuple 받아오기
					ResultSet rs = stmt.executeQuery("select * from DBCOURSE_Order");
					//한 tuple씩 받아온다
					while(rs.next()) {
						Object data[] = {rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4)};
						model.addRow(data);
					}
					//restaurant table에서 모든 restaurant_name과 profit 받아오기
					rs = stmt.executeQuery("select name as restaurant_name, profit from DBCOURSE_Restaurant");
					//한 줄씩 읽는다
					while(rs.next()) {
						Object data[] = {rs.getString(1), rs.getInt(2)};
						model2.addRow(data);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				//order table을 보여준다
				showTable_order();
				//profit을 보여주는 table을 보여준다
				showTable_profit();
				//m3 card를 보여준다
				layout.show(card, "m3");
			}
			/** 배소연*/
			//u1페이지에서 delivery를 선택한 경우
			else if(e.getSource() == deliverbutton) {
				flag = 0; //delivery인 경우 flag를 0으로 설정
				//delivery_O view를 보여주는 table에 들어갈 attribute array
				String[] colNames = {"restaurant", "city", "type", "food", "price"};
				//DefaultTableModel 생성 - int 형의 column의 정렬을 위해 override
				model = new DefaultTableModel(colNames, 0) {
					public Class<?> getColumnClass(int column) {
						if (column == 4) {
							return Integer.class;
						}
						return super.getColumnClass(column);
					}
				};
				try {
					//delivery_O view의 모든 tuple을 받아오기
					ResultSet rs = stmt.executeQuery("select * from DBCOURSE_Delivery_O");
					//한 tuple씩 받아온다
					while(rs.next()) {
						Object data[] = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)};
						model.addRow(data);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				//user모드에서의 테이블을 보여준다
				showTable_user();
				//u2 페이지를 보여준다
				layout.show(card, "u2");
			}
			/** 배소연*/
			//u1페이지에서 No delivery를 선택한 경우
			else if(e.getSource() == nodeliverbutton) {
				flag = 1; //No delivery인 경우 flag를 1로 설정
				//delivery_X view를 보여주는 table에 들어갈 attribute array
				String[] colNames = {"restaurant", "city", "type", "food", "price"};
				//DefaultTableModel 생성 - int 형의 column의 정렬을 위해 override
				model = new DefaultTableModel(colNames, 0) {
					public Class<?> getColumnClass(int column) {
						if (column == 4) {
							return Integer.class;
						}
						return super.getColumnClass(column);
					}
				};
				try {
					//delivery_X view의 모든 tuple을 받아오기
					ResultSet rs = stmt.executeQuery("select * from DBCOURSE_Delivery_X");
					//한 tuple씩 받아온다
					while(rs.next()) {
						Object data[] = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)};
						model.addRow(data);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				//user모드에서의 테이블을 보여준다
				showTable_user();
				//u2 페이지를 보여준다
				layout.show(card, "u2");
			}
			/** 배소연*/
			//u2페이지에서 updatebutton을 누른 경우
			else if(e.getSource() == updatebutton) {
				String S = ""; //sql문이 들어갈 String S
				//table에 들어갈 attribute array
				String[] colNames = {"restaurant", "city", "type", "food", "price"};

				model.setRowCount(0); //테이블 갱신을 위해 초기화

				/** 배소연, 김해리, 신유진 */
				//score 기준을 선택했을 때
				if(score_radio.isSelected()){
					//delivery인 경우
					if(flag == 0) S = "select restaurant_name, city, type, food_name, price " +
							"from (select restaurant_name, city, type, food_name, price, avg(score) as avg " +
							"from DBCOURSE_Delivery_O natural join DBCOURSE_Evaluation " +
							"group by food_name) as A where avg";
					//No delivery인 경우
					else S = "select restaurant_name, city, type, food_name, price " +
							"from (select restaurant_name, city, type, food_name, price, avg(score) as avg " +
							"from DBCOURSE_Delivery_X natural join DBCOURSE_Evaluation " +
							"group by food_name) as A where avg";
					//제대로 선택한 경우 success label 보여준다
					select.setVisible(false);
					try_again.setVisible(false);
					success.setVisible(true);
					/** 박소연*/
					//all을 선택한 경우
					if(r0.isSelected()){
						S+=">=0";
					}
					//over2를 선택한 경우
					else if(r2.isSelected()){
						S+=">=2";
					}
					//over4를 선택한 경우
					else if(r4.isSelected()){
						S+=">=4";
					}
					//over6을 선택한 경우
					else if(r6.isSelected()){
						S+=">=6";
					}
					//over8을 선택한 경우
					else if(r8.isSelected()){
						S+=">=8";
					}
					/** 배소연*/
					//아무것도 선택하지 않고 updatebutton을 누른 경우
					else{
						S+="<0";
						//try again label을 보여준다
						select.setVisible(false);
						success.setVisible(false);
						try_again.setVisible(true);
					}
				}

				/** 박소연*/
				//region 기준을 선택했을 때
				else if(region_radio.isSelected()) {
					/** sql - 신유진*/
					//delivery인 경우
					if(flag == 0) S = "select restaurant_name, city, type, food_name, price " +
							"from DBCOURSE_Delivery_O natural join DBCOURSE_Address";
					//No delivery인 경우
					else S = "select restaurant_name, city, type, food_name, price " +
							"from DBCOURSE_Delivery_X natural join DBCOURSE_Address";
					//다중 선택 체크박스 배열
					Checkbox[] check = {Seocho, Jongno, Seongdong, Songpa, Gangnam, Yongsan};
					int count = 0;
					//체크한 도시를 추가하는 for문
					for(int i=0; i<check.length; i++) {
						if(check[i].getState() == true) {
							count++;
							if(count == 1) S += " where city = '" + check[i].getLabel() + "'";
							else S += " or city = '" + check[i].getLabel() + "'";
						}
					}
					/** 배소연*/
					//아무 도시도 체크하지 않은 경우
					if(count == 0) {
						S += " where city = ''";
						select.setVisible(false);
						success.setVisible(false);
						try_again.setVisible(true);
					}
					//제대로 체크한 경우 success label을 보여준다
					else {
						select.setVisible(false);
						try_again.setVisible(false);
						success.setVisible(true);
					}
				}
				/** 박소연*/
				//price 기준을 선택했을 때
				else if(price_radio.isSelected()) {
					//price 조건을 제대로 선택한 경우 success label을 보여준다
					select.setVisible(false);
					try_again.setVisible(false);
					success.setVisible(true);
					//delivery인 경우
					if(flag == 0) S = "select * from DBCOURSE_Delivery_O";
					//No delivery인 경우
					else S = "select * from DBCOURSE_Delivery_X";
					//선택한 조건에 따라 where문 추가
					if(r5000.isSelected()) S += " where price < 5000";
					else if(r10000.isSelected()) S += " where price < 10000";
					else if(r15000.isSelected()) S += " where price < 15000";
					else if(r20000.isSelected()) S += " where price < 20000";
					else if(r25000.isSelected()) S += " where price < 25000";
					else if(r30000.isSelected()) S += " where price < 30000";
					//price 조건을 선택하지 않은 경우 try again label을 보여준다
					else {
						select.setVisible(false);
						success.setVisible(false);
						try_again.setVisible(true);
					}
				}
				//remove ingredient 기준을 선택했을 때
				else if(remove_radio.isSelected()) {
					/** 배소연*/
					//delivery인 경우
					if(flag == 0) S = "select * from DBCOURSE_Delivery_O";
					//No delivery인 경우
					else S = "select * from DBCOURSE_Delivery_X";
					//다중 선택 체크박스 배열
					Checkbox[] check = {meat, seafood, dairy, fungus, vegetable, grain, fruit};
					int count = 0; //선택된 체크박스의 개수와 선택된 ingredient 개수 세는 count 변수
					String S2 = "select name from DBCOURSE_Ingredient ";
					for(int i=0; i<check.length; i++) {
						if(check[i].getState() == true) {
							count++;
							if(count == 1) S2 += "where type = '" + check[i].getLabel() + "'";
							else S2 += " or type = '" + check[i].getLabel() + "'";
						}
					}
					//아무것도 체크하지 않았을 때
					if(count == 0) S2 += "where type = ''";
					S2 += "group by name";
					count = 0;
					try {
						String temp = "";
						ResultSet rs = stmt.executeQuery(S2);
						while(rs.next()) { //선택된 모든 ingredient를 하나씩 빼는 while문
							count++;
							if(count == 1) {//처음 체크된 경우에만 where문 추가
								/** sql - 김해리, 신유진*/
								if(flag == 0) S += " where food_name not in " + 
										"(select DBCOURSE_Delivery_O.food_name from DBCOURSE_Delivery_O, DBCOURSE_Ingredient " + 
										"where DBCOURSE_Delivery_O.food_name = DBCOURSE_Ingredient.food_name " + 
										"and (DBCOURSE_Ingredient.name = '" + rs.getString(1) + "'";
								else S += S += " where food_name not in " + 
										"(select DBCOURSE_Delivery_X.food_name from DBCOURSE_Delivery_X, DBCOURSE_Ingredient " + 
										"where DBCOURSE_Delivery_X.food_name = DBCOURSE_Ingredient.food_name " + 
										"and (DBCOURSE_Ingredient.name = '" + rs.getString(1) + "'";
							}
							else S += " or DBCOURSE_Ingredient.name = '" + rs.getString(1) + "'";
						}
						if(flag == 0) S += ") group by DBCOURSE_Delivery_O.food_name)";
						else S += ") group by DBCOURSE_Delivery_X.food_name)";
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					/** 배소연*/
					//제대로 선택한 경우 success label을 보여준다
					select.setVisible(false);
					try_again.setVisible(false);
					success.setVisible(true);
				}
				//type 기준을 선택한 경우
				else if(type_radio.isSelected()) {
					/** sql - 김해리*/
					//delivery인 경우
					if(flag == 0) S = "select * from DBCOURSE_Delivery_O";
					//No delivery인 경우
					else S = "select * from DBCOURSE_Delivery_X";
					/** 박소연*/
					//다중 선택 체크박스 배열
					Checkbox[] check = {oriental,western,dessert};
					int count = 0;
					for(int i=0; i<check.length; i++) { //체크한 type을 추가하는 for문
						if(check[i].getState() == true) {
							count++;
							if(count == 1) S += " where type = '" + check[i].getLabel() + "'";
							else S += " or type = '" + check[i].getLabel() + "'";
						}
					}
					/** 배소연*/
					//아무 것도 체크하지 않았을 때 빈 table을 보여주며 try again label을 보여준다
					if(count == 0) {
						S += " where type = ''";
						select.setVisible(false);
						success.setVisible(false);
						try_again.setVisible(true);
					}
					//제대로 체크했을 때 success label을 보여준다
					else {
						select.setVisible(false);
						try_again.setVisible(false);
						success.setVisible(true);
					}
				}
				//recommend 기준을 선택한 경우
				else if(recommend_radio.isSelected()) {
					/** sql - 김해리*/
					//delivery인 경우
					if(flag == 0) S = "select restaurant_name, city, type, food_name, price " +
							"from DBCOURSE_Delivery_O natural join DBCOURSE_Evaluation";
					//No delivery인 경우
					else S = "select restaurant_name, city, type, food_name, price " +
							"from DBCOURSE_Delivery_X natural join DBCOURSE_Evaluation";
					/** 박소연*/
					//다중 선택 체크박스 배열
					Checkbox[] check = {family,friends,date};
					int count = 0;
					for(int i=0; i<check.length; i++) {//체크한 recommend를 추가하는 for문
						if(check[i].getState() == true) {
							if(count ==0){
								S += " where recommendation = '" + check[i].getLabel() + "'";
							}
							else S += " or recommendation = '" + check[i].getLabel() + "'";
							count++;
						}
					}
					/** 배소연*/
					//아무 것도 체크하지 않았을 때 빈 table을 보여주며 try again label을 보여준다
					if(count == 0) {
						S += " where type = ''";
						select.setVisible(false);
						success.setVisible(false);
						try_again.setVisible(true);
					}
					//제대로 체크했을 때 success label을 보여준다
					else {
						select.setVisible(false);
						try_again.setVisible(false);
						success.setVisible(true);
					}
					//food_name으로 group으로 묶는다
					S += " group by food_name";
				}
				//accompany 기준을 선택한 경우
				else if(accompany_radio.isSelected()) {
					/** sql - 김해리, 신유진, 박소연*/
					//delivery인 경우 - only for not delivery의 조건이므로 빈 table과 try again 보여준다
					if(flag == 0) {
						S = "select A.restaurant_name, A.city, A.type, A.food_name, A.price " + 
								"from (select * from DBCOURSE_Delivery_O natural join DBCOURSE_Service)as A, " + 
								"(select * from DBCOURSE_Delivery_O natural join DBCOURSE_Service) as B " + 
								"where A.restaurant_name = B.restaurant_name";
						select.setVisible(false);
						success.setVisible(false);
						try_again.setVisible(true);
					}
					//No delivery인 경우
					else {
						S = "select A.restaurant_name, A.city, A.type, A.food_name, A.price " + 
								"from (select * from DBCOURSE_Delivery_X natural join DBCOURSE_Service)as A, " + 
								"(select * from DBCOURSE_Delivery_X natural join DBCOURSE_Service) as B " + 
								"where A.restaurant_name = B.restaurant_name";
						int count=0;
						//선택한 조건에 따라 where문 추가
						if(baby.getState()==true){
							S += " and A.permission = 'baby'";
							count++;
						}
						if(pet.getState()==true){
							if(count==0){
								S += " and B.permission='pet'";
								count++;
							}
							else S+=" and B.permission = 'pet'";
						}
						if(count==0) S+=" and A.permission = 'none'";

						//food_name으로 grouping한다
						S += " group by A.food_name";

						/** 배소연*/
						//제대로 선택한 경우 success label을 보여준다
						select.setVisible(false);
						try_again.setVisible(false);
						success.setVisible(true);
					}
				}
				//parking 기준을 선택한 경우
				else if(park_radio.isSelected()) {
					/** sql - 박소연*/
					//delivery인 경우 - only for not delivery의 조건이므로 빈 table과 try again 보여준다
					if(flag == 0) {
						S = "select * from DBCOURSE_Delivery_O natural join DBCOURSE_Address";
						select.setVisible(false);
						success.setVisible(false);
						try_again.setVisible(true);
					}
					//No delivery인 경우
					else {
						//선택한 조건에 따라 where문을 String S에 추가한다
						S = "select * from DBCOURSE_Delivery_X natural join DBCOURSE_Address";
						select.setVisible(false);
						try_again.setVisible(false);
						success.setVisible(true);
						if(park.isSelected()) {
							S += " where parking = 'o'";
						}
						else if(nopark.isSelected()) {
							S += " where parking = 'x'";
						}
						else if(nomatter.isSelected()) {
							S += " where parking = 'o' or parking = 'x'";
						}
						/** 배소연*/
						//아무것도 선택하지 않았을 때는 빈 table을 보여주며 try again label을 보여준다
						else{
							S += " where parking = 'o' and parking = 'x'";
							select.setVisible(false);
							success.setVisible(false);
							try_again.setVisible(true);
						}
					}
				}
				/** 배소연*/
				//아무 기준도 선택하지 않았을 때는 원래의 view를 보여주며 try again label을 보여준다
				else {
					if(flag == 0) S = "select * from DBCOURSE_Delivery_O";
					else S = "select * from DBCOURSE_Delivery_X";
					select.setVisible(false);
					success.setVisible(false);
					try_again.setVisible(true);
				}
				/** 배소연*/
				//각 기준에 따라 만들어진 String S로 sql문 실행
				try {
					//table의 모든 tuple을 받아온다
					ResultSet rs = stmt.executeQuery(S);
					//한 tuple씩 받아온다
					while(rs.next()) {
						Object data[] = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)};
						model.addRow(data);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				//user모드에서의 테이블을 보여준다
				showTable_user();
				//u2 페이지를 보여준다
			}
			/** 박소연*/
			else if(e.getSource() == nextbutton_u2) {
				x.setVisible(false);
				noingredient.setVisible(false);
				complete.setVisible(true);

				//테이블이 선택 되었을 때
				if (table.getSelectedRow() > -1){
					//선택된 줄을 받아오고
					int selectRow = table.getSelectedRow();
					String res_name="";
					String res_city="";
					String foodtype="";
					String res_phone="";
					String foodname="";
					String foodprice="";

					//테이블의 정보를 textfield와 choice에 반영되게 한다.
					res_name=table.getValueAt(selectRow, 0).toString();
					restaurant_name.setText(res_name);
					foodtype=table.getValueAt(selectRow, 2).toString();
					food_type.setText(foodtype);
					foodname=table.getValueAt(selectRow, 3).toString();
					food_name.setText(foodname);
					foodprice=table.getValueAt(selectRow, 4).toString();
					food_price.setText(String.valueOf(foodprice)+" won");
					String deliverycost="";

					//delivery인 경우
					if(flag==0){
						try{
							//레스토랑의 delivery cost를 받아온다
							String S = "select delivery_cost from DBCOURSE_Delivery where restaurant_name='"+res_name+"'";
							ResultSet rs = stmt.executeQuery(S);
							while(rs.next()){
								deliverycost=String.valueOf(rs.getInt(1));
								delivery_cost.setText(deliverycost+" won");
							}
							delivery_cost_label.setText("Delivery Cost: ");
						} catch(SQLException e1){
							e1.printStackTrace();
						}
					}
					//No delivery인 경우 delivery cost를 보이지 않도록 설정
					else{
						delivery_cost.setText("");
						delivery_cost_label.setText("");
					}

					/** transaction - 배소연, sql - 배소연, 박소연*/
					try {
						//setAutoCommit()함수에 false 인자를 주어 transaction을 실행한다
						conn.setAutoCommit(false);
						//음식의 이름이 foodname일때 재료를 구해서 numIngredient가 0인 재료의 개수를 센다
						String S = "select count(ingredient_name) " + 
								"from (select DBCOURSE_Ingredient.food_name, DBCOURSE_Inventory.ingredient_name, DBCOURSE_Inventory.numIngredient " + 
								"from DBCOURSE_Inventory, DBCOURSE_Ingredient, DBCOURSE_Food " + 
								"where (DBCOURSE_Ingredient.food_name = DBCOURSE_Food.name) " + 
								"and (DBCOURSE_Inventory.ingredient_name = DBCOURSE_Ingredient.name) " + 
								"and(DBCOURSE_Inventory.restaurant_name = DBCOURSE_Food.restaurant_name)) as A " + 
								"where numIngredient = 0 and food_name = '" + foodname + "'";
						//ingredient가 남아 있는지 확인하기 위한 count(ingredient) tuple 받아오기
						ResultSet rs = stmt.executeQuery(S);
						int count = 0;
						//count 변수를 numIngredient가 0인 재료의 개수로 설정
						if(rs.next()) count = rs.getInt(1);
						if(count == 0) { //재료가 부족하지 않으면
							try {
								/** 박소연*/
								//DBCOURSE_Order에 insert를 할 때 preparedstatement 이용
								PreparedStatement pStmt=conn.prepareStatement("insert into DBCOURSE_Order values(?,?,?,?)");
								rs = stmt.executeQuery("select max(order_number) from DBCOURSE_Order");

								/** 배소연*/
								//주문번호는 max(order_number)+1로 자동 셋팅
								if(rs.next()) count = rs.getInt(1);
								count++; 

								/** 박소연*/
								//delivery인 경우
								if(flag==0){
									pStmt.setInt(1, count);
									pStmt.setString(2, res_name);
									pStmt.setString(3, foodname);
									//음식가격에 배달료를 더하여 음식가격을 설정
									pStmt.setInt(4, (Integer.valueOf(foodprice)+Integer.valueOf(deliverycost)));
									pStmt.executeUpdate();
								}
								//No delivery인 경우
								else{
									pStmt.setInt(1, count);
									pStmt.setString(2, res_name);
									pStmt.setString(3, foodname);
									pStmt.setInt(4, (Integer.valueOf(foodprice)));
									pStmt.executeUpdate();
								}
								/** 배소연*/
								//방금 들어간 주문의 재료를 가져온다
								rs = stmt.executeQuery("select ingredient " +
										"from DBCOURSE_Order_Ingredient " +
										"where order_number >= all(select order_number from DBCOURSE_Order_Ingredient)");
								String ingr = "";
								while(rs.next()) { //방금 들어간 주문의 레스토랑에서 재료를 하나씩 빼서 요리한다.
									ingr = rs.getString(1);
									stmt2.executeUpdate("update DBCOURSE_Inventory " +
											"set numIngredient = numIngredient - 1 " +
											"where DBCOURSE_Inventory.restaurant_name = '" + res_name +
											"' and DBCOURSE_Inventory.ingredient_name = '" + ingr + "' ");
								}
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}
						else { //재료가 부족하면 X와 noingredient label을 보여준다
							complete.setVisible(false);
							x.setVisible(true);
							noingredient.setVisible(true);
						}
						//수행작업이 잘 이뤄졌을 때 변동사항을 적용한다
						conn.commit();
					} catch (SQLException e1) {
						e1.printStackTrace(); //예외 발생 원인 출력
						if(conn != null) {
							try {
								//SQLException 발생 시 rollback으로 변동사항을 없는 것으로 설정
								conn.rollback();
							} catch (SQLException e2) {
								e2.printStackTrace();
							}
						}
					} finally {
						try { //다시 setAutoCommit을 true로 설정
							conn.setAutoCommit(true);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
					/** 박소연*/
					try {
						//레스토랑의 전화번호를 가져온다.
						ResultSet rs = stmt.executeQuery("select phone_number from DBCOURSE_restaurant where name="+"'"+res_name+"'");
						while(rs.next()) {
							res_phone=rs.getString(1);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					//마지막의 order에 보여줄 정보를 설정
					restaurant_num.setText(res_phone);
					restaurant_name.setFont(new Font("Britannic Bold",Font.PLAIN,H/20));
					restaurant_name.setForeground(Color.white);
					food_type.setFont(new Font("Britannic Bold",Font.PLAIN,H/20));
					food_type.setForeground(Color.white);
					food_price.setFont(new Font("Britannic Bold",Font.PLAIN,H/20));
					food_price.setForeground(Color.white);
					restaurant_num.setFont(new Font("Britannic Bold",Font.PLAIN,H/20));
					restaurant_num.setForeground(Color.white);
					food_name.setFont(new Font("Britannic Bold",Font.PLAIN,H/20));
					food_name.setForeground(Color.white);
					delivery_cost.setFont(new Font("Britannic Bold",Font.PLAIN,H/20));
					delivery_cost.setForeground(Color.white);
					layout.show(card, "u3");
				}
				/** 배소연*/
				else{
					//제대로 선택하지 않은 경우 try again label을 보여준다
					select.setVisible(false);
					success.setVisible(false);
					try_again.setVisible(true);
				}
			}
			/** 박소연*/
			else if(e.getSource()==getinfobutton){
				if(delete_radio.isSelected() || update_radio.isSelected()) {
					//table을 선택했을 때
					if (table.getSelectedRow() > -1){
						//테이블로부터 정보를 받아와서 보여준다.
						int selectRow = table.getSelectedRow();//선택된 줄
						emp_ID=new String(table.getValueAt(selectRow, 0).toString());
						name.setText(table.getValueAt(selectRow, 1).toString());
						name.setFont(new Font("Courier",Font.PLAIN,H/33));
						c_res_name.select(table.getValueAt(selectRow, 2).toString());
						c_position.select(table.getValueAt(selectRow,3).toString());
						salary.setText(table.getValueAt(selectRow, 4).toString());
						salary.setFont(new Font("Courier",Font.PLAIN,H/33));
						oldname=name.getText();
						oldposition=c_position.getSelectedItem();
						oldresname=c_res_name.getSelectedItem();
						oldsalary=Integer.parseInt(salary.getText());
					}
					else{
						//table을 선택하지 않고 get information button을 눌렀을때 에러메세지를 보여준다
						inform_emp_up_1.setVisible(false);
						inform_emp_down_1.setVisible(false);
						inform_emp_up_done.setVisible(false);
						inform_emp_down_done.setVisible(false);
						inform_emp_down_errtable.setVisible(true);
						inform_emp_error.setVisible(true);
						inform_emp_down_format.setVisible(false);
						inform_emp_select.setVisible(false);
						inform_emp_insert.setVisible(false);
					}
				}
				/** 배소연*/
				//insert를 선택한 상태인데 getinfo를 누르면 insert label을 보여준다
				else if(insert_radio.isSelected()){
					inform_emp_up_1.setVisible(false);
					inform_emp_down_1.setVisible(false);
					inform_emp_up_done.setVisible(false);
					inform_emp_down_done.setVisible(false);
					inform_emp_down_errtable.setVisible(false);
					inform_emp_error.setVisible(true);
					inform_emp_down_format.setVisible(false);
					inform_emp_select.setVisible(false);
					inform_emp_insert.setVisible(true);
				}
				//라디오 버튼을 아무것도 선택하지 않고 getinfo를 누르면 select button label을 보여준다
				else {
					inform_emp_up_1.setVisible(false);
					inform_emp_down_1.setVisible(false);
					inform_emp_up_done.setVisible(false);
					inform_emp_down_done.setVisible(false);
					inform_emp_down_errtable.setVisible(false);
					inform_emp_error.setVisible(true);
					inform_emp_down_format.setVisible(false);
					inform_emp_select.setVisible(true);
					inform_emp_insert.setVisible(false);
				}
			}
			/** 박소연*/
			else if(e.getSource()==enterinfobutton){
				//새로운 정보를 textfield와 choice로부터 받아온다.
				newname=name.getText();
				newposition=c_position.getSelectedItem();
				newresname=c_res_name.getSelectedItem();
				String newsalary_s=salary.getText();

				//textfield가 비어있을 때 에러메시지 출력
				if(newname.isEmpty()||newsalary_s.isEmpty()){
					inform_emp_up_1.setVisible(false);
					inform_emp_down_1.setVisible(false);
					inform_emp_up_done.setVisible(false);
					inform_emp_down_done.setVisible(false);
					inform_emp_down_errtable.setVisible(false);
					inform_emp_error.setVisible(true);
					inform_emp_down_format.setVisible(true);
					inform_emp_select.setVisible(false);
					inform_emp_insert.setVisible(false);
				}
				//textfield가 비어있지 않을 때
				else{
					try{
						//salary에 자연수가 아닌 String이 입력되면 exception 처리
						newsalary=Integer.valueOf(newsalary_s);

						/** 배소연*/
						model.setRowCount(0);  //테이블 갱신을 위해 초기화

						/** 박소연*/
						//insert radio button이 눌렸을 때
						if(insert_radio.isSelected()){
							try {
								//이전 아이디에 +1 함으로써 새로운 아이디를 자동으로 부여한다
								int newid=0;
								ResultSet rs1 = stmt.executeQuery("select max(id) from DBCOURSE_Employee");
								if(rs1.next()) newid = Integer.valueOf(rs1.getString(1));

								//완료메시지를 출력한다
								inform_emp_up_1.setVisible(false);
								inform_emp_down_1.setVisible(false);
								inform_emp_up_done.setVisible(true);
								inform_emp_down_done.setVisible(true);
								inform_emp_down_errtable.setVisible(false);
								inform_emp_error.setVisible(false);
								inform_emp_down_format.setVisible(false);
								inform_emp_select.setVisible(false);
								inform_emp_insert.setVisible(false);

								//새로운 정보를 table에 insert한다
								//DBCOURSE_Employee에 prepared statement를 만든다
								PreparedStatement pStmt=conn.prepareStatement("insert into DBCOURSE_Employee values(?,?,?,?,?)");
								pStmt.setString(1, String.valueOf(newid+1));
								pStmt.setString(2, newname);
								pStmt.setString(3, newresname);
								pStmt.setString(4, newposition);
								pStmt.setInt(5, newsalary);
								pStmt.executeUpdate();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}
						//delete radiobutton을 선택하면
						else if(delete_radio.isSelected()){
							inform_emp_up_1.setVisible(false);
							inform_emp_down_1.setVisible(false);
							inform_emp_up_done.setVisible(true);
							inform_emp_down_done.setVisible(true);
							inform_emp_down_errtable.setVisible(false);
							inform_emp_error.setVisible(false);
							inform_emp_down_format.setVisible(false);
							inform_emp_select.setVisible(false);
							inform_emp_insert.setVisible(false);

							//employee id로 찾은 employee 정보를 DBCOURSE_Employee에서 제거한다
							try{
								stmt.executeUpdate("delete from DBCOURSE_Employee where id='"+emp_ID+"'");
							} catch(SQLException se) {
								se.printStackTrace();
							}
						}
						//update radio button이 선택되었을 때
						else if(update_radio.isSelected()){
							//안내 메시지를 출력한다.
							inform_emp_up_1.setVisible(false);
							inform_emp_down_1.setVisible(false);
							inform_emp_up_done.setVisible(true);
							inform_emp_down_done.setVisible(true);
							inform_emp_down_errtable.setVisible(false);
							inform_emp_error.setVisible(false);
							inform_emp_down_format.setVisible(false);
							inform_emp_select.setVisible(false);
							inform_emp_insert.setVisible(false);

							/** 배소연 - 트랜잭션, 박소연 - insert*/
							//delete와 insert가 같이 일어나야 하기 때문에 transaction
							try{
								//setAutoCommit()함수에 false 인자를 주어 transaction을 실행한다
								conn.setAutoCommit(false);
								//dbcourse_seriesName 테이블에서 사용자가 입력한 시리즈 삭제
								stmt.executeUpdate("delete from DBCOURSE_Employee where id='"+emp_ID+"'");
								PreparedStatement pStmt=conn.prepareStatement("insert into DBCOURSE_Employee values(?,?,?,?,?)");
								pStmt.setString(1, emp_ID);
								pStmt.setString(2, newname);
								pStmt.setString(3, newresname);
								pStmt.setString(4, newposition);
								pStmt.setInt(5, newsalary);
								pStmt.executeUpdate();

								//수행작업이 잘 이뤄졌을 때 변동사항을 적용한다
								conn.commit();
							} catch(SQLException se) {
								se.printStackTrace();
								if(conn != null) {
									try {
										//SQLException 발생 시 rollback으로 변동사항을 없는 것으로 설정
										conn.rollback();
									} catch (SQLException e1) {
										e1.printStackTrace();
									}
								}
							} finally {
								try { //다시 setAutoCommit을 true로 설정
									conn.setAutoCommit(true);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
							}
						}
						/**박소연*/
						else{ //radio button 아무것도 누르지 않고 엔터를 누르면 에러메시지 출력
							inform_emp_up_1.setVisible(false);
							inform_emp_down_1.setVisible(false);
							inform_emp_up_done.setVisible(false);
							inform_emp_down_done.setVisible(false);
							inform_emp_down_errtable.setVisible(false);
							inform_emp_error.setVisible(true);
							inform_emp_down_format.setVisible(false);
							inform_emp_select.setVisible(true);
							inform_emp_insert.setVisible(false);
						}
						/** 배소연*/
						//수정 이후 결과를 받아오기
						ResultSet rs;
						try {
							rs = stmt.executeQuery("SELECT * FROM DBCOURSE_Employee");
							while(rs.next()) {
								Object data[] = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)};
								model.addRow(data);
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						//바뀐 테이블을 보여준다.
						showTable_emp();
					} catch(NumberFormatException num){ //Integer를 입력하지 않으면 에러메시지를 보여준다.
						inform_emp_up_1.setVisible(false);
						inform_emp_down_1.setVisible(false);
						inform_emp_up_done.setVisible(false);
						inform_emp_down_done.setVisible(false);
						inform_emp_down_errtable.setVisible(false);
						inform_emp_error.setVisible(true);
						inform_emp_down_format.setVisible(true);
						inform_emp_select.setVisible(false);
						inform_emp_insert.setVisible(false);
					}
				}
			}
			/** 박소연*/
			//salary를 up하는 경우
			else if(e.getSource()==givebutton_up){
				try{
					String str=plus_salary.getText();
					Double value=Double.valueOf(str);

					/** 배소연*/
					model.setRowCount(0); //테이블 갱신을 위해 초기화

					/** 박소연*/
					if(value<0){ //음수이면 에러메시지 출력
						inform_emp_up_1.setVisible(false);
						inform_emp_down_1.setVisible(false);
						inform_emp_up_done.setVisible(false);
						inform_emp_down_done.setVisible(false);
						inform_emp_down_errtable.setVisible(false);
						inform_emp_error.setVisible(true);
						inform_emp_down_format.setVisible(true);
						inform_emp_select.setVisible(false);
						inform_emp_insert.setVisible(false);
					}
					else{
						//salary 결과를 update한다.
						String S="update DBCOURSE_Employee set salary=salary*"+String.valueOf(1+Double.valueOf(str)/(double)100);
						try {
							stmt.executeUpdate(S);
							//수정 이후 결과를 받아오기
							ResultSet rs = stmt.executeQuery("SELECT * FROM DBCOURSE_Employee");
							while(rs.next()) {
								Object data[] = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)};
								model.addRow(data);
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						//완료메세지를 출력한다.
						inform_emp_up_1.setVisible(false);
						inform_emp_down_1.setVisible(false);
						inform_emp_up_done.setVisible(true);
						inform_emp_down_done.setVisible(true);
						inform_emp_down_errtable.setVisible(false);
						inform_emp_error.setVisible(false);
						inform_emp_down_format.setVisible(false);
						inform_emp_select.setVisible(false);
						inform_emp_insert.setVisible(false);
						//table을 보여준다
						showTable_emp();
					}
				} catch(NumberFormatException num){ //input이 Integer형식이 아니면 에러메세지 출력
					inform_emp_up_1.setVisible(false);
					inform_emp_down_1.setVisible(false);
					inform_emp_up_done.setVisible(false);
					inform_emp_down_done.setVisible(false);
					inform_emp_down_errtable.setVisible(false);
					inform_emp_error.setVisible(true);
					inform_emp_down_format.setVisible(true);
					inform_emp_select.setVisible(false);
					inform_emp_insert.setVisible(false);
				}
			}
			/** 박소연*/
			//salary를 down하는 경우
			else if(e.getSource()==givebutton_down){
				//textfield로부터 String을 받아온다.
				try{
					String str=minus_salary.getText();
					Double value=Double.valueOf(str);

					/** 배소연*/
					model.setRowCount(0);  //테이블 갱신을 위해 초기화

					/** 박소연*/
					//textfield의 수가 음수이거나 100보다 크면
					if(value>100||value<0){
						//에러메세지 출력
						inform_emp_up_1.setVisible(false);
						inform_emp_down_1.setVisible(false);
						inform_emp_up_done.setVisible(false);
						inform_emp_down_done.setVisible(false);
						inform_emp_down_errtable.setVisible(false);
						inform_emp_error.setVisible(true);
						inform_emp_down_format.setVisible(true);
						inform_emp_select.setVisible(false);
						inform_emp_insert.setVisible(false);
					}
					else{ //0과 100 사이의 수라면 salary update
						String S="update DBCOURSE_Employee set salary=salary*"+String.valueOf(1-Double.valueOf(str)/(double)100);
						try {
							stmt.executeUpdate(S);
							//수정 이후의 결과를 받아온다
							ResultSet rs = stmt.executeQuery("SELECT * FROM DBCOURSE_Employee");
							while(rs.next()) {
								Object data[] = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)};
								model.addRow(data);
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						//완료메세지 출력
						inform_emp_up_1.setVisible(false);
						inform_emp_down_1.setVisible(false);
						inform_emp_up_done.setVisible(true);
						inform_emp_down_done.setVisible(true);
						inform_emp_down_errtable.setVisible(false);
						inform_emp_error.setVisible(false);
						inform_emp_down_format.setVisible(false);
						inform_emp_select.setVisible(false);
						inform_emp_insert.setVisible(false);
						//새로운 테이블을 보여준다.
						showTable_emp();
					}
				}catch(NumberFormatException num){ //input이 Integer형식이 아니면 exception 처리
					inform_emp_up_1.setVisible(false);
					inform_emp_down_1.setVisible(false);
					inform_emp_up_done.setVisible(false);
					inform_emp_down_done.setVisible(false);
					inform_emp_down_errtable.setVisible(false);
					inform_emp_error.setVisible(true);
					inform_emp_down_format.setVisible(true);
					inform_emp_select.setVisible(false);
					inform_emp_insert.setVisible(false);
				}
			}
			/** 박소연*/
			//salary를 기준으로 직원을 해고하는 경우
			else if(e.getSource()==firebutton){
				//textfield로부터 string을 받아온다
				try{
					String str=delete_salary.getText();
					Double value=Double.valueOf(str);

					/** 배소연*/
					model.setRowCount(0);  //테이블 갱신을 위해 초기화

					/** 박소연*/
					String S="delete from DBCOURSE_Employee where salary>"+String.valueOf(value);

					//textfield에 입력된 수보다 큰 salary를 가진 employee는 delete한다.
					try {
						stmt.executeUpdate(S);
						//수정 이후의 결과를 받아온다.
						ResultSet rs = stmt.executeQuery("SELECT * FROM DBCOURSE_Employee");
						while(rs.next()) {
							Object data[] = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)};
							model.addRow(data);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					//완료 메세지를 출력한다.
					inform_emp_up_1.setVisible(false);
					inform_emp_down_1.setVisible(false);
					inform_emp_up_done.setVisible(true);
					inform_emp_down_done.setVisible(true);
					inform_emp_down_errtable.setVisible(false);
					inform_emp_error.setVisible(false);
					inform_emp_down_format.setVisible(false);
					inform_emp_select.setVisible(false);
					inform_emp_insert.setVisible(false);
					//새로운 테이블을 보여준다.
					showTable_emp();
				} catch(NumberFormatException num){ //input형식이 Integer가 아니면 exception 처리한다
					inform_emp_up_1.setVisible(false);
					inform_emp_down_1.setVisible(false);
					inform_emp_up_done.setVisible(false);
					inform_emp_down_done.setVisible(false);
					inform_emp_down_errtable.setVisible(false);
					inform_emp_error.setVisible(true);
					inform_emp_down_format.setVisible(true);
					inform_emp_select.setVisible(false);
					inform_emp_insert.setVisible(false);
				}
			}
			/** 배소연*/
			//선택한 order를 처리하여 profit을 올리는 버튼
			else if (e.getSource() == takeorderbutton) {
				//order table과 profit을 보여주는 테이블을 초기화한다
				model.setRowCount(0);
				model2.setRowCount(0);
				//this is order list label을 보여준다
				empty.setVisible(false);
				profit.setVisible(false);
				thisis.setVisible(true);
				order.setVisible(true);
				String res = ""; String ingr = ""; int price = 0;
				//order 처리와 profit update는 항상 같이 이뤄져야 하기 때문에 transaction
				try {
					//setAutoCommit()함수에 false 인자를 주어 transaction을 실행한다
					conn.setAutoCommit(false);
					//order number가 가장 작은 tuple을 고른다
					ResultSet rs = stmt.executeQuery("select restaurant_name, price from DBCOURSE_Order " + 
							"where order_number <= all(select order_number from DBCOURSE_Order)");
					if(rs.next()) {
						res = rs.getString(1);
						price = rs.getInt(2);
					}
					//가져온 restaurant name과 price로 profit을 update한다
					stmt.executeUpdate("update DBCOURSE_Restaurant " +
							"set profit = profit + " + price +
							" where name = '" + res + "'");
					//처리가 끝난 order를 order table에서 delete한다
					stmt2.executeUpdate("delete from DBCOURSE_Order " +
							"where order_number <= all(select order_number from (select order_number from DBCOURSE_Order) as A)");

					//남은 order table의 tuple을 가져온다
					rs = stmt.executeQuery("select * from DBCOURSE_Order");
					while(rs.next()) {
						Object data[] = {rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4)};
						model.addRow(data);
					}
					//update된 restaurant 별 현재의 profit을 가져온다
					rs = stmt.executeQuery("select name as restaurant_name, profit from DBCOURSE_Restaurant");
					while(rs.next()) {
						Object data[] = {rs.getString(1), rs.getInt(2)};
						model2.addRow(data);
					}
					//수행작업이 잘 이뤄졌을 때 변동사항을 적용한다
					conn.commit();
				} catch (SQLException e1) {
					e1.printStackTrace();
					if(conn != null) {
						try {
							//SQLException 발생 시 rollback으로 변동사항을 없는 것으로 설정
							conn.rollback();
						} catch (SQLException e2) {
							e2.printStackTrace();
						}
					}
				} finally {
					try { //다시 setAutoCommit을 true로 설정
						conn.setAutoCommit(true);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				//order table과 restaurant 별 profit table을 보여준다
				showTable_order();
				showTable_profit();
			}
			/** 배소연*/
			//영업 종료를 하고 직원들에게 daily salary를 지급하고 최종 profit을 계산하는 버튼
			else if(e.getSource() == closebutton) {
				try {
					int count = 0;
					//order table이 다 처리 되어 비어 있는 상태인지 확인
					ResultSet rs = stmt.executeQuery("select count(order_number) from DBCOURSE_Order");
					if(rs.next()) count = rs.getInt(1);
					//order table에 아직 주문이 남아있으면 list를 비우라는 label을 보여준다
					if(count != 0) {
						thisis.setVisible(false);
						order.setVisible(false);
						profit.setVisible(false);
						empty.setVisible(true);
					}
					//order table이 비어있으면
					else {
						model2.setRowCount(0);
						thisis.setVisible(true);
						order.setVisible(false);
						empty.setVisible(false);
						profit.setVisible(true);

						//레스토랑 별 sum(salary)를 구한다
						rs = stmt.executeQuery("select restaurant_name, sum(salary) " +
								"from DBCOURSE_Employee " +
								"group by restaurant_name");
						String res = ""; int sum = 0;
						while(rs.next()) { //가져온 restaurant name과 sum(salary)로 profit을 update한다
							res = rs.getString(1); sum = rs.getInt(2);
							stmt2.executeUpdate("update DBCOURSE_Restaurant " +
									"set profit = profit - " + sum +
									" where name = '" + res + "'");
						}
						//update된 table을 보여준다
						rs = stmt.executeQuery("select name as restaurant_name, profit from DBCOURSE_Restaurant");
						while(rs.next()) {
							Object data[] = {rs.getString(1), rs.getInt(2)};
							model2.addRow(data);
						}
						showTable_profit();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		/** 배소연*/
		//user모드의 table을 보여주는 메소드
		void showTable_user() {
			//defaultTableModel을 기반으로 JTable 생성
			table = new JTable(model);
			table.getTableHeader().setBackground(Color.WHITE);
			table.getTableHeader().setFont(new Font("Britannic Bold",Font.PLAIN,H/35));
			table.setSelectionBackground(Color.decode("#FCE4EC"));
			table.setFont(new Font("Courier",Font.PLAIN,H/35));
			table.getColumn("restaurant").setPreferredWidth(W/15);
			table.getColumn("city").setPreferredWidth(W/15);
			table.getColumn("type").setPreferredWidth(W/40);
			table.getColumn("food").setPreferredWidth(W/10);
			table.getColumn("price").setPreferredWidth(W/50);
			//price column 오른쪽 정렬
			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
			dtcr.setHorizontalAlignment(SwingConstants.RIGHT);
			table.getColumn("price").setCellRenderer(dtcr);
			table.setRowHeight(H/30);
			//table 헤더를 눌렀을 경우 정렬
			table.setAutoCreateRowSorter(true);
			//JTable을 담은 JScrollPane 생성
			jsp = new JScrollPane(table);
			jsp.setPreferredSize(new Dimension(W/2,H/2));
			//tablepanel의 모든 컴포넌트 삭제
			tablepanel_u.removeAll();
			//tablepanel의 위치 설정
			tablepanel_u.setBounds(7*W/15, H/10, W/2, H/2);
			//tablepanel에 JTable을 담은 JScrollPane 추가
			tablepanel_u.add(jsp);
			//tablepanel의 UI reset
			tablepanel_u.updateUI();
			//tablepanel의 배경을 투명하게 설정
			tablepanel_u.setOpaque(false);
		}
		//manager모드의 employee management 페이지에서의 table을 보여주는 메소드
		void showTable_emp() {
			//defaultTableModel을 기반으로 JTable 생성
			table = new JTable(model);
			table.getTableHeader().setBackground(Color.WHITE);
			table.getTableHeader().setFont(new Font("Britannic Bold",Font.PLAIN,H/35));
			table.setSelectionBackground(Color.decode("#FCE4EC"));
			table.setFont(new Font("Courier",Font.PLAIN,H/35));
			table.getColumn("ID").setPreferredWidth(W/60);
			table.getColumn("name").setPreferredWidth(W/10);
			table.getColumn("restaurant name").setPreferredWidth(W/15);
			table.getColumn("position").setPreferredWidth(W/40);
			table.getColumn("salary(daily)").setPreferredWidth(W/20);
			//salary column 오른쪽 정렬
			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
			dtcr.setHorizontalAlignment(SwingConstants.RIGHT);
			table.getColumn("salary(daily)").setCellRenderer(dtcr);
			table.setRowHeight(H/30);
			//table 헤더를 눌렀을 경우 정렬
			table.setAutoCreateRowSorter(true);
			//JTable을 담은 JScrollPane 생성
			jsp = new JScrollPane(table);
			jsp.setPreferredSize(new Dimension(2*W/3,3*H/5));
			//tablepanel의 모든 컴포넌트 삭제
			tablepanel_emp.removeAll();
			//tablepanel의 위치 설정
			tablepanel_emp.setBounds(4*W/15, H/25, 2*W/3, 3*H/5);
			//tablepanel에 JTable을 담은 JScrollPane 추가
			tablepanel_emp.add(jsp);
			//tablepanel의 UI reset
			tablepanel_emp.updateUI();
			//tablepanel의 배경을 투명하게 설정
			tablepanel_emp.setOpaque(false);
		}
		//manager모드의 order processing 페이지에서의 order table을 보여주는 메소드
		void showTable_order() {
			//defaultTableModel을 기반으로 JTable 생성
			table = new JTable(model);
			table.getTableHeader().setBackground(Color.WHITE);
			table.getTableHeader().setFont(new Font("Britannic Bold",Font.PLAIN,H/35));
			table.setSelectionBackground(Color.decode("#FCE4EC"));
			table.setFont(new Font("Courier",Font.PLAIN,H/35));
			table.getColumn("number").setPreferredWidth(W/50);
			table.getColumn("restaurant_name").setPreferredWidth(W/15);
			table.getColumn("food_name").setPreferredWidth(W/10);
			table.getColumn("price").setPreferredWidth(W/50);
			//price column 오른쪽 정렬
			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
			dtcr.setHorizontalAlignment(SwingConstants.RIGHT);
			table.getColumn("price").setCellRenderer(dtcr);
			table.setRowHeight(H/30);
			//table 헤더를 눌렀을 경우 정렬
			table.setAutoCreateRowSorter(true);
			//JTable을 담은 JScrollPane 생성
			jsp = new JScrollPane(table);
			jsp.setPreferredSize(new Dimension(11*W/20,H/3));
			//tablepanel의 모든 컴포넌트 삭제
			tablepanel_order.removeAll();
			//tablepanel의 위치 설정
			tablepanel_order.setBounds(W/30, H/10, 11*W/20, H/3);
			//tablepanel에 JTable을 담은 JScrollPane 추가
			tablepanel_order.add(jsp);
			//tablepanel의 UI reset
			tablepanel_order.updateUI();
			//tablepanel의 배경을 투명하게 설정
			tablepanel_order.setOpaque(false);
		}
		//manager모드의 order processing 페이지에서의 profit table을 보여주는 메소드
		void showTable_profit() {
			//defaultTableModel을 기반으로 JTable 생성
			table2 = new JTable(model2);
			table2.getTableHeader().setBackground(Color.WHITE);
			table2.getTableHeader().setFont(new Font("Britannic Bold",Font.PLAIN,H/35));
			table2.setSelectionBackground(Color.decode("#FCE4EC"));
			table2.setFont(new Font("Courier",Font.PLAIN,H/35));
			table2.getColumn("restaurant_name").setPreferredWidth(W/15);
			table2.getColumn("profit").setPreferredWidth(W/50);
			//profit column 오른쪽 정렬
			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
			dtcr.setHorizontalAlignment(SwingConstants.RIGHT);
			table2.getColumn("profit").setCellRenderer(dtcr);
			table2.setRowHeight(H/30);
			//table 헤더를 눌렀을 경우 정렬
			table2.setAutoCreateRowSorter(true);
			//JTable을 담은 JScrollPane 생성
			jsp2 = new JScrollPane(table2);
			jsp2.setPreferredSize(new Dimension(W/3, 2*H/5));
			//tablepanel의 모든 컴포넌트 삭제
			tablepanel_profit.removeAll();
			//tablepanel의 위치 설정
			tablepanel_profit.setBounds(31*W/50, H/10, W/3, 2*H/5);
			//tablepanel에 JTable을 담은 JScrollPane 추가
			tablepanel_profit.add(jsp2);
			//tablepanel의 UI reset
			tablepanel_profit.updateUI();
			//tablepanel의 배경을 투명하게 설정
			tablepanel_profit.setOpaque(false);
		}

		@Override
		public void itemStateChanged(ItemEvent arg0) {}
	}
}