package team1;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.sql.*;
import java.net.URL;

public class Test {
	/** ��ҿ�*/
	//jdbc driver �̿�
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	//database�� team1
	static final String DB_URL = "jdbc:mysql://localhost:3306/team1?useSSL=false&serverTimezone=UTC";
	//user�� team1
	static final String USER = "team1";
	// password�� team1
	static final String PASS = "team1";
	//Ŀ�ؼ� ������ ���� �ʱ�ȭ
	static  Connection conn = null;
	//������Ʈ��Ʈ ������ ���� �ʱ�ȭ
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
		/** �ڼҿ�*/
		//frame ����
		REDSystem my = new REDSystem();
	}
	public static class REDSystem extends JFrame implements ActionListener, ItemListener {
		//�������� ī��
		JPanel card, cardhome, card_m1, card_m2, card_m3, card_u1, card_u2, card_u3;
		//������ �� ��ư�� ��� �г�
		JPanel textpanel_u, textpanel_u2, radiobuttons, radiobuttons2, infopanel;
		//Ȩ������ ����� �׸� main �гΰ� ������ �������� ����� �׸� page �г�, user�� employee�������� ���̺��г�
		JPanel main, page, tablepanel_u, tablepanel_emp;
		//order, profit, salary up, salary down�� ���� �г�
		JPanel tablepanel_order, tablepanel_profit, bonuspanel, crisispanel;
		//manager, user ��� ���� ��ư, Ȩ ��ư, �ڷΰ��� ��ư
		JButton managerbutton, userbutton, homebutton, backbutton;
		//�� �������� Ȩ ��ư�� �ڷΰ��� ��ư
		JButton homebutton_m1, homebutton_m2, homebutton_m3;
		JButton homebutton_u1, homebutton_u2, homebutton_u3;
		JButton backbutton_m1, backbutton_m2, backbutton_m3;
		JButton backbutton_u1, backbutton_u2, backbutton_u3;
		//user��忡�� ���̺� ���� �� ���̺� update ��ư�� next�������� �Ѿ�� ��ư
		JButton nextbutton_u1, nextbutton_u2, updatebutton;
		//order table�� order�� ó���ϴ� ��ư�� user��忡�� delivery, nodelivery ���� ��ư
		JButton takeorderbutton, deliverbutton, nodeliverbutton;
		//manager��忡�� employee����, orderprocessing ��ư, employee���������� ��ư
		JButton employeebutton, orderprbutton, getinfobutton, enterinfobutton;
		JButton givebutton_up, givebutton_down, firebutton, closebutton;
		//���� ���� ��ư
		Checkbox baby, pet, meat, seafood, dairy, fungus, vegetable, grain, fruit;
		Checkbox family, date, friends, oriental, western, dessert;
		Checkbox Seocho, Jongno, Seongdong, Songpa, Gangnam, Yongsan;
		//�ϳ��� �����ؾ� �� �� ���� ��ư
		JRadioButton r5000, r10000, r15000, r20000, r25000, r30000;
		JRadioButton update_radio, delete_radio, insert_radio, park, nopark, nomatter;
		JRadioButton r0, r2, r4, r6, r8, score_radio, price_radio, region_radio;
		JRadioButton remove_radio, park_radio, accompany_radio, type_radio, recommend_radio;
		//text�Է� â
		JTextField name, salary, plus_salary, minus_salary, delete_salary;
		//choice ����
		Choice c_position, c_res_name;
		//user2 ���������� ���� label
		JLabel select, try_again, success, restaurant_name;
		//user3 �������� ��µǴ� label
		JLabel food_type, food_name, food_price, restaurant_num;
		//m2 �������� �ȳ� �޼��� label
		JLabel inform_emp_up_1, inform_emp_down_1,delivery_cost_label,delivery_cost;
		JLabel inform_emp_down_errtable;
		JLabel inform_emp_up_done, inform_emp_down_done;
		JLabel inform_emp_error, inform_emp_down_format;
		JLabel inform_emp_insert, inform_emp_select;
		//user3 �������� ��ᰡ ���� �� ��µǴ� label, manager3 �������� ���� label
		JLabel x, noingredient, complete, thisis, order, profit, empty;
		//�������� ��ư�� ���Ǵ� �̹�����
		ImageIcon start, strawberry, pineapple, watermelon, kiwi, delivery, deliveryx;
		ImageIcon manager1, manager2, manager3, user1, user2, user3, update, takeorder;
		ImageIcon employee, cashier, orange, grape, home, back, next, close;
		//gui ��ü�� ī�巹�̾ƿ����� ����
		CardLayout layout=new CardLayout();
		//â ũ�⸦ ���ϱ� ���� ��ũ�� ����� �ҷ����� ����
		Toolkit tk = getToolkit();
		Dimension d = tk.getScreenSize();
		//m2���� ���̺��� �޾ƿ� ������ ���Ӱ� �Է¹��� ����
		String emp_ID, oldname, oldposition, oldresname, newname, newposition, newresname;
		int oldsalary,newsalary;
		//â ũ��
		int W = 9*d.width/10;
		int H = 9*W/16;
		//delivery, nodelivery�� �����ϴ� flag
		int flag = 0;
		//���� ���� ����� �޾ƿ� ���̺��
		DefaultTableModel model, model2;
		//���̺��� �޾ƿ� jtable
		JTable table, table2;
		//jtable�� �ݿ��� ��ũ����
		JScrollPane jsp, jsp2;

		/** ��ҿ�*/
		//�̹����� ũ�⿡ �°� �ٲٴ� method
		ImageIcon changeImageSize(ImageIcon a, int m, int n) {
			Image b = a.getImage();
			b = b.getScaledInstance(W/m, H/n, Image.SCALE_SMOOTH);
			a = new ImageIcon(b);
			return a;
		}

		/*������â ������*/
		/** �ڼҿ�*/
		public REDSystem(){
			setTitle("Restaurant Management System");//Ÿ��Ʋ�� �����Ѵ�.
			setSize(W,H);//ũ�⸦ �����Ѵ�.
			//card �гο� cardlayout�� ����
			card=new JPanel();
			card.setLayout(layout);

			/** ��ҿ�*/
			//�̹��� �ҷ�����
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

			//ȭ�� ������ �°� �̹��� ũ�� ����
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

			/** �ڼҿ�*/
			//home ������ �г� ����
			cardhome=new JPanel();
			cardhome.setLayout(null);

			/** ��ҿ�*/
			//home �������� ��� �׸���
			main = new JPanel(){
				public void paint(Graphics g) {
					g.drawImage(start.getImage(), 0, 0, W, H, null);
				}
			};
			main.setBounds(0, 0, getWidth(), getHeight());

			//homebutton ����
			homebutton = new JButton(home);
			homebutton.setBounds(W/200, H/100, W/20, H/12);
			homebutton.setBorderPainted(false);

			//backbutton ����
			backbutton = new JButton(back);
			backbutton.setBounds(12*W/200, H/100, W/20, H/12);
			backbutton.setBorderPainted(false);

			//managerbutton ����
			managerbutton = new JButton(manager1);
			managerbutton.setBorderPainted(false);  //��輱 ����
			managerbutton.setRolloverIcon(manager2);  //���콺 �ø��� ���ϰ�
			managerbutton.setPressedIcon(manager3);  //Ŭ���� �� ���ϰ�
			managerbutton.setBounds(W/2, 5*H/9, W/5, H/5);  //x, y, width, height
			managerbutton.addActionListener(this);

			//userbutton ����
			userbutton = new JButton(user1);
			userbutton.setBorderPainted(false);
			userbutton.setRolloverIcon(user2);
			userbutton.setPressedIcon(user3);
			userbutton.setBounds(3*W/4, 5*H/9, W/5, H/5);
			userbutton.addActionListener(this);

			//cardhome�� ��ư�� �г��� ���δ�
			cardhome.add(homebutton);
			cardhome.add(backbutton);
			cardhome.add(managerbutton);
			cardhome.add(userbutton);
			cardhome.add(main);

			//homeī�带 card�� ���δ�
			card.add("home",cardhome);

			/*user ���� �� - u1 page*/
			/** �ڼҿ�*/
			//card layout�� ���� ���ο� �г� u1 ����
			card_u1 = new JPanel();
			card_u1.setLayout(null);

			/** ��ҿ�*/
			//u1 card�� ��� �� ��ġ ����
			page = new JPanel() {
				public void paint(Graphics g) {
					g.drawImage(pineapple.getImage(), 0, 0, W, H, null);
				}
			};
			page.setBounds(0, 0, getWidth(), getHeight());

			//homebutton ����
			homebutton_u1 = new JButton(home);
			homebutton_u1.setBounds(W/200, H/100, W/20, H/12);
			homebutton_u1.setBorderPainted(false);
			homebutton_u1.addActionListener(this);

			//backbutton ����
			backbutton_u1 = new JButton(back);
			backbutton_u1.setBounds(12*W/200, H/100, W/20, H/12);
			backbutton_u1.setBorderPainted(false);
			backbutton_u1.addActionListener(this);

			//deliverbutton ����
			deliverbutton = new JButton(delivery);
			deliverbutton.setBounds(W/18, H/4, W/4, H/2);
			deliverbutton.setBorderPainted(false);
			deliverbutton.setContentAreaFilled(false);
			deliverbutton.addActionListener(this);

			//nodeliverbutton ����
			nodeliverbutton = new JButton(deliveryx);
			nodeliverbutton.setBounds(W/3, H/4, W/4, H/2);
			nodeliverbutton.setBorderPainted(false);
			nodeliverbutton.setContentAreaFilled(false);
			nodeliverbutton.addActionListener(this);

			//card_u1�� ��ư�� �г��� ���δ�
			card_u1.add(homebutton_u1);
			card_u1.add(backbutton_u1);
			card_u1.add(deliverbutton);
			card_u1.add(nodeliverbutton);
			card_u1.add(page);

			//u1ī�带 card�� ���δ�
			card.add("u1",card_u1);

			/*u2 page */
			/** �ڼҿ�*/
			//card layout�� ���� ���ο� �г� u2 ����
			card_u2 = new JPanel();
			card_u2.setLayout(null);

			/** ��ҿ�*/
			//u2 card�� ��� �� ��ġ ����
			page = new JPanel() {
				public void paint(Graphics g) {
					g.drawImage(strawberry.getImage(), 0, 0, W, H, null);
				}
			};
			page.setBounds(0, 0, getWidth(), getHeight());

			/** �ڼҿ�*/
			//order�� ���� �� JLabel �ʱ�ȭ
			restaurant_name=new JLabel("");
			food_type=new JLabel("");
			food_name=new JLabel("");
			food_price=new JLabel("");
			restaurant_num=new JLabel("");

			//radio button���� ���� �г�
			JPanel scorepanel=new JPanel(new GridLayout(0,5));
			JPanel pricepanel=new JPanel(new GridLayout(0,3));
			JPanel regionpanel=new JPanel(new GridLayout(0,3));
			JPanel removepanel = new JPanel(new GridLayout(0,4));
			JPanel parkingpanel = new JPanel(new GridLayout(0,3));
			JPanel accompanypanel = new JPanel(new GridLayout(0,2));
			JPanel typepanel = new JPanel(new GridLayout(0,4));
			JPanel recommendpanel = new JPanel(new GridLayout(0,4));

			//�˻� �ɼ� radio button
			score_radio=new JRadioButton("Score Option");
			price_radio=new JRadioButton("Price Option");
			region_radio=new JRadioButton("Region Option");
			remove_radio = new JRadioButton("I don't want this ingredient!");
			park_radio = new JRadioButton("Parking Option");
			accompany_radio = new JRadioButton("Accompany Option");
			type_radio = new JRadioButton("Food Type");
			recommend_radio = new JRadioButton("Recommend For");

			//radio button���� ����� �������� �����ϰ� �۾�ü ����
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

			//�ɼ� radio button�� �׷����� ���´�.
			ButtonGroup detail=new ButtonGroup();
			detail.add(price_radio);detail.add(region_radio);
			detail.add(remove_radio);detail.add(park_radio);
			detail.add(accompany_radio);detail.add(type_radio);
			detail.add(recommend_radio);detail.add(score_radio);

			//�ɼ��� ���� �г�
			JPanel score_r=new JPanel();
			JPanel region_r=new JPanel();
			JPanel price_r=new JPanel();
			JPanel remove_r=new JPanel();
			JPanel parking_r=new JPanel();
			JPanel accompany_r=new JPanel();
			JPanel type_r=new JPanel();
			JPanel recommend_r=new JPanel();

			//�ɼ��� ���� �г��� ������ ������� ����
			price_r.setBackground(Color.white);region_r.setBackground(Color.white);
			remove_r.setBackground(Color.white);parking_r.setBackground(Color.white);
			accompany_r.setBackground(Color.white);type_r.setBackground(Color.white);
			recommend_r.setBackground(Color.white);score_r.setBackground(Color.white);

			//score�ɼ��� radiobutton�� �ʱ�ȭ
			r0=new JRadioButton("ALL");
			r2=new JRadioButton("over 2");
			r4=new JRadioButton("over 4");
			r6=new JRadioButton("over 6");
			r8=new JRadioButton("over 8");

			//�гο� score �ɼ��� radiobutton���� ���δ�.
			scorepanel.add(r0);scorepanel.add(r2);
			scorepanel.add(r4);scorepanel.add(r6);scorepanel.add(r8);

			//score �ɼ��� radiobutton���� �׷����� ���´�.
			ButtonGroup scoregroup=new ButtonGroup();
			scoregroup.add(r0);scoregroup.add(r2);
			scoregroup.add(r4);scoregroup.add(r6);scoregroup.add(r8);

			//region �ɼ��� checkbox�� �ʱ�ȭ
			Seocho= new Checkbox("Seocho");
			Jongno= new Checkbox("Jongno");
			Seongdong= new Checkbox("Seongdong");
			Songpa= new Checkbox("Songpa");
			Gangnam= new Checkbox("Gangnam");
			Yongsan= new Checkbox("Yongsan");

			//region �ɼ��� radiobutton���� �гο� ���δ�.
			regionpanel.add(Seocho);regionpanel.add(Jongno);
			regionpanel.add(Seongdong);regionpanel.add(Songpa);
			regionpanel.add(Gangnam);regionpanel.add(Yongsan);

			//price �ɼ��� radiobutton���� �ʱ�ȭ
			r5000= new JRadioButton("~5000 won");
			r10000= new JRadioButton("~10000 won");
			r15000= new JRadioButton("~15000 won");
			r20000= new JRadioButton("~20000 won");
			r25000= new JRadioButton("~25000 won");
			r30000= new JRadioButton("~30000 won");

			//price �ɼ��� radiobutton���� �гο� ���δ�.
			pricepanel.add(r5000);pricepanel.add(r10000);
			pricepanel.add(r15000);pricepanel.add(r20000);
			pricepanel.add(r25000);pricepanel.add(r30000);

			//price �ɼ��� radiobutton���� �׷����� ���´�.
			ButtonGroup pricegroup=new ButtonGroup();
			pricegroup.add(r5000); pricegroup.add(r10000); pricegroup.add(r15000);
			pricegroup.add(r20000); pricegroup.add(r25000); pricegroup.add(r30000);

			//parking �ɼ��� radiobutton���� �ʱ�ȭ
			park = new JRadioButton("Parking");
			nopark = new JRadioButton("NO Parking");
			nomatter = new JRadioButton("NO matter");

			//parking �ɼ��� radiobutton���� �гο� ���δ�.
			parkingpanel.add(park);parkingpanel.add(nopark);parkingpanel.add(nomatter);

			//��ư�� actionlistener�� ���δ�.
			park.addActionListener(this); nopark.addActionListener(this);
			nomatter.addActionListener(this);

			//park �ɼ��� radiobutton�� �׷����� ���´�.
			ButtonGroup park_gr=new ButtonGroup();
			park_gr.add(park); park_gr.add(nopark); park_gr.add(nomatter);

			//service �ɼ��� checkbox�� �ʱ�ȭ
			baby = new Checkbox("with Baby");
			pet = new Checkbox("with pet");

			//�гο� service �ɼ��� checkbox�� ���δ�.
			accompanypanel.add(baby); accompanypanel.add(pet);

			//ingredient �ɼ��� checkbox�� �ʱ�ȭ
			meat = new Checkbox("meat");
			seafood = new Checkbox("seafood");
			dairy = new Checkbox("dairy");
			fungus = new Checkbox("fungus");
			vegetable = new Checkbox("vegetable");
			grain = new Checkbox("grain");
			fruit = new Checkbox("fruit");

			//ingredient �ɼ��� radiobutton���� �гο� ���δ�.
			removepanel.add(meat); removepanel.add(seafood); removepanel.add(dairy);
			removepanel.add(fungus); removepanel.add(vegetable); removepanel.add(grain);
			removepanel.add(fruit);

			//recommend �ɼ��� checkbox�� �ʱ�ȭ
			family = new Checkbox("family");
			date = new Checkbox("date");
			friends = new Checkbox("friends");
			recommendpanel.add(family);recommendpanel.add(friends);recommendpanel.add(date);

			//foodtype �ɼ��� checkbox�� �ʱ�ȭ
			oriental = new Checkbox("oriental");
			western = new Checkbox("western");
			dessert = new Checkbox("dessert");

			//�гο� food type �ɼ��� checkbox�� ���δ�.
			typepanel.add(oriental);typepanel.add(western);typepanel.add(dessert);

			//�гο� recommend �ɼ��� checkbox�� �ɼǵ��� ���δ�.
			score_r.add(score_radio);
			price_r.add(price_radio);
			region_r.add(region_radio);
			remove_r.add(remove_radio);
			parking_r.add(park_radio);
			accompany_r.add(accompany_radio);
			type_r.add(type_radio);
			recommend_r.add(recommend_radio);

			//radio button ������ ���� �г� - y������ ���̴� box layout���� ����
			radiobuttons = new JPanel();
			radiobuttons2=new JPanel();
			radiobuttons.setLayout(new BoxLayout(radiobuttons,BoxLayout.Y_AXIS));
			radiobuttons2.setLayout(new BoxLayout(radiobuttons2,BoxLayout.Y_AXIS));

			//radiobutton�� checkbox�� �۾� ����
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

			//�гο� ������ư�� üũ�ڽ��� ����ִ� �гε��� ���δ�.
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

			//���� ��ư�� üũ�ڽ��� �ٿ��� �ִ� �г��� ���ο� �гο� ���̰� ��� �� ��ġ ����
			textpanel_u.setLayout(new BoxLayout(textpanel_u,BoxLayout.Y_AXIS));
			textpanel_u.add(radiobuttons);
			textpanel_u2.add(radiobuttons2);
			radiobuttons.setBackground(Color.white);
			radiobuttons2.setBackground(Color.white);
			textpanel_u.setOpaque(false);
			textpanel_u2.setOpaque(false);
			textpanel_u.setBounds(W/50, H/10, 3*W/7, 3*H/4);
			textpanel_u2.setBounds(19*W/40, 8*H/12, W/3, H/5);

			//not delivery�� �� �����ϴ� ��ư���� �˸��� label
			JLabel onlydelivery=new JLabel("Only for No Delivery");
			onlydelivery.setFont(new Font("Britannic Bold",Font.PLAIN,H/28));
			onlydelivery.setOpaque(false);
			onlydelivery.setBounds(22*W/40,15*H/24,W/3,H/20);
			onlydelivery.setBackground(Color.white);

			/** ��ҿ�*/
			//table ���� �ȳ� message label
			//���� ��
			select = new JLabel("Select!", SwingConstants.CENTER);
			select.setFont(new Font("Britannic Bold",Font.PLAIN,H/15));
			select.setBounds(53*W/100, H/70, W/5, H/15);

			//������ ���� ��
			try_again = new JLabel("Try Again!", SwingConstants.CENTER);
			try_again.setFont(new Font("Britannic Bold",Font.PLAIN,H/20));
			try_again.setBounds(53*W/100, H/70, W/5, H/15);
			try_again.setVisible(false);

			//������ ���������� �Ϸ�Ǿ��� ��
			success = new JLabel("Success!", SwingConstants.CENTER);
			success.setFont(new Font("Britannic Bold",Font.PLAIN,H/18));
			success.setBounds(53*W/100, H/70, W/5, H/15);
			success.setVisible(false);

			//homebutton ����
			homebutton_u2 = new JButton(home);
			homebutton_u2.setBounds(W/200, H/100, W/20, H/12);
			homebutton_u2.setBorderPainted(false);
			homebutton_u2.addActionListener(this);

			//backbutton ����
			backbutton_u2 = new JButton(back);
			backbutton_u2.setBounds(12*W/200, H/100, W/20, H/12);
			backbutton_u2.setBorderPainted(false);
			backbutton_u2.addActionListener(this);

			//nextbutton ����
			nextbutton_u2 = new JButton(next);
			nextbutton_u2.setBounds(8*W/9, H/100, W/12, H/12);
			nextbutton_u2.setBorderPainted(false);
			nextbutton_u2.setContentAreaFilled(false);
			nextbutton_u2.addActionListener(this);

			//updatebutton ����
			updatebutton = new JButton(update);
			updatebutton.setBounds(15*W/18, H/100, W/22, H/13);
			updatebutton.setBorderPainted(false);
			updatebutton.setContentAreaFilled(false);
			updatebutton.addActionListener(this);

			//table �г� ����
			tablepanel_u = new JPanel();

			//card_u2�� ��ư�� �г��� ���δ�
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

			//card_u2 ī�带 card�� ���δ�
			card.add("u2", card_u2);

			/*u3 ȭ��*/
			/** �ڼҿ�*/
			//card layout�� ���� ���ο� �г� u3 ����
			card_u3 = new JPanel();
			card_u3.setLayout(null);

			/** ��ҿ�*/
			//u3�� ��� �� ��ġ ����
			page = new JPanel() {
				public void paint(Graphics g) {
					g.drawImage(kiwi.getImage(), 0, 0, W, H, null);
				}
			};
			page.setBounds(0, 0, getWidth(), getHeight());

			/** �ڼҿ�*/
			//order Ȯ�� label�� �г�
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

			//label�� �гο� ���δ�.
			orderpanel.add(column0);orderpanel.add(restaurant_name);
			orderpanel.add(column1);orderpanel.add(food_type);
			orderpanel.add(column2);orderpanel.add(food_name);
			orderpanel.add(column3);orderpanel.add(food_price);
			orderpanel.add(column4);orderpanel.add(restaurant_num);
			orderpanel.add(delivery_cost_label); orderpanel.add(delivery_cost);
			orderpanel.setLayout(new BoxLayout(orderpanel,BoxLayout.Y_AXIS));
			orderpanel.setBounds(W/13,13*H/40,W/3,H/2);
			orderpanel.setOpaque(false);

			/** ��ҿ�*/
			//��ᰡ ���� �� �ߴ� X label ����
			x = new JLabel("X");
			x.setFont(new Font("Courier",Font.BOLD,2*H/3));
			x.setForeground(Color.RED);
			x.setBounds(W/13,H/4,W/3,2*H/3);
			x.setVisible(false);

			//sorry, no ingredient label ����
			noingredient = new JLabel("Sorry, No Ingredient");
			noingredient.setFont(new Font("Britannic Bold",Font.BOLD,H/10));
			noingredient.setBounds(2*W/13,H/20,W,H/10);
			noingredient.setVisible(false);

			//complete order label ����
			complete = new JLabel("Complete Order!");
			complete.setFont(new Font("Britannic Bold",Font.BOLD,H/10));
			complete.setBounds(2*W/13,H/20,W,H/10);

			//homebutton ����
			homebutton_u3 = new JButton(home);
			homebutton_u3.setBounds(W/200, H/100, W/20, H/12);
			homebutton_u3.setBorderPainted(false);
			homebutton_u3.addActionListener(this);

			//backbutton ����
			backbutton_u3 = new JButton(back);
			backbutton_u3.setBounds(12*W/200, H/100, W/20, H/12);
			backbutton_u3.setBorderPainted(false);
			backbutton_u3.addActionListener(this);

			//card_u3�� ��ư�� �г��� ���δ�
			card_u3.add(homebutton_u3);
			card_u3.add(backbutton_u3);
			card_u3.add(orderpanel);
			card_u3.add(noingredient);
			card_u3.add(complete);
			card_u3.add(x);
			card_u3.add(page);

			//card_u3 ī�带 card�� ���δ�
			card.add("u3", card_u3);

			/*manager ���� �� - m1 page*/
			/** ��ҿ�*/
			//card layout�� ���� ���ο� �г� m1 ����
			card_m1 = new JPanel();
			card_m1.setLayout(null);

			//m1 card�� ��� �� ��ġ ����
			page = new JPanel() {
				public void paint(Graphics g) {
					g.drawImage(watermelon.getImage(), 0, 0, W, H, null);
				}
			};
			page.setBounds(0, 0, getWidth(), getHeight());

			//homebutton ����
			homebutton_m1 = new JButton(home);
			homebutton_m1.setBounds(W/200, H/100, W/20, H/12);
			homebutton_m1.setBorderPainted(false);
			homebutton_m1.addActionListener(this);

			//backbutton ����
			backbutton_m1 = new JButton(back);
			backbutton_m1.setBounds(12*W/200, H/100, W/20, H/12);
			backbutton_m1.setBorderPainted(false);
			backbutton_m1.addActionListener(this);

			//employee management ��ư ����
			employeebutton = new JButton(employee);
			employeebutton.setBounds(W/18, H/4, W/4, H/2);
			employeebutton.setBorderPainted(false);
			employeebutton.setContentAreaFilled(false);
			employeebutton.addActionListener(this);

			//order processing ��ư ����
			orderprbutton = new JButton(cashier);
			orderprbutton.setBounds(W/3, H/4, W/4, H/2);
			orderprbutton.setBorderPainted(false);
			orderprbutton.setContentAreaFilled(false);
			orderprbutton.addActionListener(this);

			//card_m1�� ��ư�� �г��� ���δ�
			card_m1.add(homebutton_m1);
			card_m1.add(backbutton_m1);
			card_m1.add(employeebutton);
			card_m1.add(orderprbutton);
			card_m1.add(page);

			//card_m1 ī�带 card�� ���δ�
			card.add("m1", card_m1);

			/*m2 page*/
			/** ��ҿ� */
			//card layout�� ���� ���ο� �г� m2 ����
			card_m2 = new JPanel();
			card_m2.setLayout(null);

			//m2 card�� ��� �� ��ġ ����
			page = new JPanel() {
				public void paint(Graphics g) {
					g.drawImage(orange.getImage(), 0, 0, W, H, null);
				}
			};
			page.setBounds(0, 0, getWidth(), getHeight());

			//homebutton ����
			homebutton_m2 = new JButton(home);
			homebutton_m2.setBounds(W/200, H/100, W/20, H/12);
			homebutton_m2.setBorderPainted(false);
			homebutton_m2.addActionListener(this);

			//backbutton ����
			backbutton_m2 = new JButton(back);
			backbutton_m2.setBounds(12*W/200, H/100, W/20, H/12);
			backbutton_m2.setBorderPainted(false);
			backbutton_m2.addActionListener(this);

			//employee ���̺��� �׸� �г� ����
			tablepanel_emp = new JPanel();

			/** �ڼҿ�*/
			//�� �г��� ������ �˸��� label�� �� label�� �۾� ����
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

			//�ȳ��޼��� label ����
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

			/** ��ҿ�*/
			inform_emp_select = new JLabel("Select Button!");
			inform_emp_select.setFont(new Font("Britannic Bold",Font.PLAIN,H/15));
			inform_emp_select.setBounds(W/45, 7*H/25, W/4, H/15);
			inform_emp_select.setVisible(false);

			inform_emp_insert = new JLabel("Insert!");
			inform_emp_insert.setFont(new Font("Britannic Bold",Font.PLAIN,H/10));
			inform_emp_insert.setBounds(W/17, 7*H/25, W/4, H/10);
			inform_emp_insert.setVisible(false);


			//table�κ��� �޾ƿ��ų� table�� �ݿ���ų ������ ���� �г�
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

			//update, delete, insert ������ ��ư�� �� �۾�ü�� �����ϰ� �׷����� ���´�.
			ButtonGroup infogroup=new ButtonGroup();
			update_radio=new JRadioButton("update"); update_radio.addActionListener(this);
			delete_radio=new JRadioButton("delete"); delete_radio.addActionListener(this);
			insert_radio=new JRadioButton("insert"); insert_radio.addActionListener(this);
			update_radio.setFont(new Font("Britannic Bold",Font.PLAIN,H/30));
			delete_radio.setFont(new Font("Britannic Bold",Font.PLAIN,H/30));
			insert_radio.setFont(new Font("Britannic Bold",Font.PLAIN,H/30));
			infogroup.add(update_radio); infogroup.add(delete_radio); infogroup.add(insert_radio);

			//���̺�κ��� ������ �޾ƿ� ��ư
			getinfobutton=new JButton("GET INFO");
			getinfobutton.setFont(new Font("Britannic Bold",Font.PLAIN,H/30));
			getinfobutton.addActionListener(this);

			//���̺� ������ �ݿ���ų ��ư
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

			//information�� �ٲ� �ɼ� radio button�� �гο� ���δ�.
			info_radiopanel.add(update_radio);info_radiopanel.add(delete_radio);
			info_radiopanel.add(insert_radio);info_radiopanel.add(getinfobutton);

			//������ ������ ������ textfield�� choice ����
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

			//�гο� textfield, JButton, �׸��� choice�� ���δ�.
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

			//panel�� ��ġ ����
			infopanel.setBounds(4*W/15, 13*H/20, 2*W/3, H/11);
			bonuspanel.setBounds(34*W/50, 19*H/25, W/4, H/7);
			crisispanel.setBounds(4*W/15, 19*H/25, 2*W/5, H/10);

			//ī�忡 �ȳ��޼��� label���� ���̰� ���� ������ �޼����� true�� ����
			card_m2.add(inform_emp_up_1);
			card_m2.add(inform_emp_down_1);
			card_m2.add(inform_emp_error);
			card_m2.add(inform_emp_down_format);
			card_m2.add(inform_emp_down_errtable);
			card_m2.add(inform_emp_up_done);
			card_m2.add(inform_emp_down_done);
			card_m2.add(inform_emp_select);
			card_m2.add(inform_emp_insert);

			//card�� �ռ� ���� �гε��� ���δ�.
			card_m2.add(crisispanel);
			card_m2.add(infopanel);
			card_m2.add(bonuspanel);
			card_m2.add(homebutton_m2);
			card_m2.add(backbutton_m2);
			card_m2.add(tablepanel_emp);
			card_m2.add(page);
			card.add("m2", card_m2);

			/*m3 page*/
			/** �ڼҿ�*/
			//card layout�� ���� ���ο� �г� m3 ����
			card_m3 = new JPanel();
			card_m3.setLayout(null);

			/** ��ҿ�*/
			//m3 card�� ��� �� ��ġ ����
			page = new JPanel() {
				public void paint(Graphics g) {
					g.drawImage(grape.getImage(), 0, 0, W, H, null);
				}
			};
			page.setBounds(0, 0, getWidth(), getHeight());

			//homebutton ����
			homebutton_m3 = new JButton(home);
			homebutton_m3.setBounds(W/200, H/100, W/20, H/12);
			homebutton_m3.setBorderPainted(false);
			homebutton_m3.addActionListener(this);

			//backbutton ����
			backbutton_m3 = new JButton(back);
			backbutton_m3.setBounds(12*W/200, H/100, W/20, H/12);
			backbutton_m3.setBorderPainted(false);
			backbutton_m3.addActionListener(this);

			//order table�� order�� ���ʴ�� ó���ϴ� takeorderbutton ����
			takeorderbutton = new JButton(takeorder);
			takeorderbutton.setBounds(23*W/50, 9*H/20, W/8, H/14);
			takeorderbutton.setBorderPainted(false);
			takeorderbutton.setContentAreaFilled(false);
			takeorderbutton.addActionListener(this);

			//��������� daily wage�� �����ϰ� close�ϴ� closebutton ����
			closebutton = new JButton(close);
			closebutton.setBounds(2*W/3, 23*H/40, W/4, H/3);
			closebutton.setBorderPainted(false);
			closebutton.setContentAreaFilled(false);
			closebutton.addActionListener(this);

			//order table�� ���� �г�
			tablepanel_order = new JPanel();

			//restaurant �� profit�� �����ִ� table�� ���� �г�
			tablepanel_profit = new JPanel();

			//thisis label ����
			thisis = new JLabel("This is");
			thisis.setFont(new Font("Britannic Bold",Font.PLAIN,H/10));
			thisis.setBounds(39*W/100, 29*H/50, W/5, H/10);

			//order list label ����
			order = new JLabel("Order List!");
			order.setFont(new Font("Britannic Bold",Font.PLAIN,H/10));
			order.setBounds(17*W/50, 69*H/100, W/3, H/10);

			//today's profit label ����
			profit = new JLabel("Today's profit!");
			profit.setFont(new Font("Britannic Bold",Font.PLAIN,H/11));
			profit.setBounds(16*W/50, 69*H/100, 2*W/5, H/11);
			profit.setVisible(false);

			//order table�� order�� ���� ���Ҵµ� closebutton�� ���� ��� �ߴ� label
			empty = new JLabel("Empty the list!");
			empty.setFont(new Font("Britannic Bold",Font.PLAIN,H/10));
			empty.setBounds(29*W/100, 64*H/100, 2*W/5, H/10);
			empty.setVisible(false);

			//card_m3�� ��ư�� �г��� ���δ�
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

			//card_m3 ī�带 card�� ���δ�
			card.add("m3", card_m3);

			//��ü card�� add
			add(card);
			//x�� ������ ���α׷��� �Բ� �����ϵ��� ����
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setVisible(true); //visible�ϰ� �����Ѵ�.
		}

		/*��ư ȿ�� ó�� method*/
		public void actionPerformed(ActionEvent e) {
			/** ��ҿ� */
			//home �������� ���� ���
			if(e.getSource() == homebutton_u1 || e.getSource() == homebutton_u2 || e.getSource() == homebutton_u3 || e.getSource() == homebutton_m1 || e.getSource() == homebutton_m2 || e.getSource() == homebutton_m3 || e.getSource() == backbutton_u1 || e.getSource() == backbutton_m1) {
				layout.show(card, "home");
			}
			//m1 �������� ���� ���
			else if(e.getSource()==managerbutton || e.getSource() == backbutton_m2 || e.getSource() == backbutton_m3){
				layout.show(card, "m1");
			}
			//u1 �������� ���� ���
			else if(e.getSource() == userbutton || e.getSource() == backbutton_u2){
				layout.show(card, "u1");
			}
			//u2 �������� ���� ���
			else if(e.getSource() == backbutton_u3) {
				layout.show(card, "u2");
			}
			/** �ڼҿ�*/
			else if(e.getSource() == employeebutton) {
				//employee ��ư�� ������ �ȳ��޼����� ���̵��� ����
				inform_emp_up_1.setVisible(true);
				inform_emp_down_1.setVisible(true);
				inform_emp_up_done.setVisible(false);
				inform_emp_down_done.setVisible(false);
				inform_emp_down_errtable.setVisible(false);
				inform_emp_error.setVisible(false);
				inform_emp_down_format.setVisible(false);
				inform_emp_select.setVisible(false);
				inform_emp_insert.setVisible(false);

				//table�� column name�� �ʱ�ȭ
				String[] colNames = {"ID", "name", "restaurant name", "position","salary(daily)", };
				//table�� update�ϵ��� ����.
				model = new DefaultTableModel(colNames, 0) {
					public Class<?> getColumnClass(int column) {
						if (column == 4) {
							return Integer.class;
						}
						return super.getColumnClass(column);
					}
				};
				try {
					//DBCOURSE_Employee�κ��� ������ �����´�.
					ResultSet rs = stmt.executeQuery("select * from DBCOURSE_Employee");
					while(rs.next()) {
						Object data[] = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)};
						model.addRow(data);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				//employee table�� �����ش�
				showTable_emp();
				//m2 card�� �����ش�
				layout.show(card, "m2");
			}
			/** ��ҿ�*/
			//manager����� order processing ��ư�� ���� ���
			else if(e.getSource() == orderprbutton) {
				//order table�� �� attribute array
				String[] colNames = {"number", "restaurant_name", "food_name", "price"};
				//DefaultTableModel ���� - int ���� column�� ������ ���� override
				model = new DefaultTableModel(colNames, 0) {
					public Class<?> getColumnClass(int column) {
						if (column == 4) {
							return Integer.class;
						}
						return super.getColumnClass(column);
					}
				};
				//profit�� �����ִ� table�� �� attribute array
				String[] colNames2 = {"restaurant_name", "profit"};
				//DefaultTableModel ���� - int ���� column�� ������ ���� override
				model2 = new DefaultTableModel(colNames2, 0) {
					public Class<?> getColumnClass(int column) {
						if (column == 1) {
							return Integer.class;
						}
						return super.getColumnClass(column);
					}
				};
				try {
					//order table�� ��� tuple �޾ƿ���
					ResultSet rs = stmt.executeQuery("select * from DBCOURSE_Order");
					//�� tuple�� �޾ƿ´�
					while(rs.next()) {
						Object data[] = {rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4)};
						model.addRow(data);
					}
					//restaurant table���� ��� restaurant_name�� profit �޾ƿ���
					rs = stmt.executeQuery("select name as restaurant_name, profit from DBCOURSE_Restaurant");
					//�� �پ� �д´�
					while(rs.next()) {
						Object data[] = {rs.getString(1), rs.getInt(2)};
						model2.addRow(data);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				//order table�� �����ش�
				showTable_order();
				//profit�� �����ִ� table�� �����ش�
				showTable_profit();
				//m3 card�� �����ش�
				layout.show(card, "m3");
			}
			/** ��ҿ�*/
			//u1���������� delivery�� ������ ���
			else if(e.getSource() == deliverbutton) {
				flag = 0; //delivery�� ��� flag�� 0���� ����
				//delivery_O view�� �����ִ� table�� �� attribute array
				String[] colNames = {"restaurant", "city", "type", "food", "price"};
				//DefaultTableModel ���� - int ���� column�� ������ ���� override
				model = new DefaultTableModel(colNames, 0) {
					public Class<?> getColumnClass(int column) {
						if (column == 4) {
							return Integer.class;
						}
						return super.getColumnClass(column);
					}
				};
				try {
					//delivery_O view�� ��� tuple�� �޾ƿ���
					ResultSet rs = stmt.executeQuery("select * from DBCOURSE_Delivery_O");
					//�� tuple�� �޾ƿ´�
					while(rs.next()) {
						Object data[] = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)};
						model.addRow(data);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				//user��忡���� ���̺��� �����ش�
				showTable_user();
				//u2 �������� �����ش�
				layout.show(card, "u2");
			}
			/** ��ҿ�*/
			//u1���������� No delivery�� ������ ���
			else if(e.getSource() == nodeliverbutton) {
				flag = 1; //No delivery�� ��� flag�� 1�� ����
				//delivery_X view�� �����ִ� table�� �� attribute array
				String[] colNames = {"restaurant", "city", "type", "food", "price"};
				//DefaultTableModel ���� - int ���� column�� ������ ���� override
				model = new DefaultTableModel(colNames, 0) {
					public Class<?> getColumnClass(int column) {
						if (column == 4) {
							return Integer.class;
						}
						return super.getColumnClass(column);
					}
				};
				try {
					//delivery_X view�� ��� tuple�� �޾ƿ���
					ResultSet rs = stmt.executeQuery("select * from DBCOURSE_Delivery_X");
					//�� tuple�� �޾ƿ´�
					while(rs.next()) {
						Object data[] = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)};
						model.addRow(data);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				//user��忡���� ���̺��� �����ش�
				showTable_user();
				//u2 �������� �����ش�
				layout.show(card, "u2");
			}
			/** ��ҿ�*/
			//u2���������� updatebutton�� ���� ���
			else if(e.getSource() == updatebutton) {
				String S = ""; //sql���� �� String S
				//table�� �� attribute array
				String[] colNames = {"restaurant", "city", "type", "food", "price"};

				model.setRowCount(0); //���̺� ������ ���� �ʱ�ȭ

				/** ��ҿ�, ���ظ�, ������ */
				//score ������ �������� ��
				if(score_radio.isSelected()){
					//delivery�� ���
					if(flag == 0) S = "select restaurant_name, city, type, food_name, price " +
							"from (select restaurant_name, city, type, food_name, price, avg(score) as avg " +
							"from DBCOURSE_Delivery_O natural join DBCOURSE_Evaluation " +
							"group by food_name) as A where avg";
					//No delivery�� ���
					else S = "select restaurant_name, city, type, food_name, price " +
							"from (select restaurant_name, city, type, food_name, price, avg(score) as avg " +
							"from DBCOURSE_Delivery_X natural join DBCOURSE_Evaluation " +
							"group by food_name) as A where avg";
					//����� ������ ��� success label �����ش�
					select.setVisible(false);
					try_again.setVisible(false);
					success.setVisible(true);
					/** �ڼҿ�*/
					//all�� ������ ���
					if(r0.isSelected()){
						S+=">=0";
					}
					//over2�� ������ ���
					else if(r2.isSelected()){
						S+=">=2";
					}
					//over4�� ������ ���
					else if(r4.isSelected()){
						S+=">=4";
					}
					//over6�� ������ ���
					else if(r6.isSelected()){
						S+=">=6";
					}
					//over8�� ������ ���
					else if(r8.isSelected()){
						S+=">=8";
					}
					/** ��ҿ�*/
					//�ƹ��͵� �������� �ʰ� updatebutton�� ���� ���
					else{
						S+="<0";
						//try again label�� �����ش�
						select.setVisible(false);
						success.setVisible(false);
						try_again.setVisible(true);
					}
				}

				/** �ڼҿ�*/
				//region ������ �������� ��
				else if(region_radio.isSelected()) {
					/** sql - ������*/
					//delivery�� ���
					if(flag == 0) S = "select restaurant_name, city, type, food_name, price " +
							"from DBCOURSE_Delivery_O natural join DBCOURSE_Address";
					//No delivery�� ���
					else S = "select restaurant_name, city, type, food_name, price " +
							"from DBCOURSE_Delivery_X natural join DBCOURSE_Address";
					//���� ���� üũ�ڽ� �迭
					Checkbox[] check = {Seocho, Jongno, Seongdong, Songpa, Gangnam, Yongsan};
					int count = 0;
					//üũ�� ���ø� �߰��ϴ� for��
					for(int i=0; i<check.length; i++) {
						if(check[i].getState() == true) {
							count++;
							if(count == 1) S += " where city = '" + check[i].getLabel() + "'";
							else S += " or city = '" + check[i].getLabel() + "'";
						}
					}
					/** ��ҿ�*/
					//�ƹ� ���õ� üũ���� ���� ���
					if(count == 0) {
						S += " where city = ''";
						select.setVisible(false);
						success.setVisible(false);
						try_again.setVisible(true);
					}
					//����� üũ�� ��� success label�� �����ش�
					else {
						select.setVisible(false);
						try_again.setVisible(false);
						success.setVisible(true);
					}
				}
				/** �ڼҿ�*/
				//price ������ �������� ��
				else if(price_radio.isSelected()) {
					//price ������ ����� ������ ��� success label�� �����ش�
					select.setVisible(false);
					try_again.setVisible(false);
					success.setVisible(true);
					//delivery�� ���
					if(flag == 0) S = "select * from DBCOURSE_Delivery_O";
					//No delivery�� ���
					else S = "select * from DBCOURSE_Delivery_X";
					//������ ���ǿ� ���� where�� �߰�
					if(r5000.isSelected()) S += " where price < 5000";
					else if(r10000.isSelected()) S += " where price < 10000";
					else if(r15000.isSelected()) S += " where price < 15000";
					else if(r20000.isSelected()) S += " where price < 20000";
					else if(r25000.isSelected()) S += " where price < 25000";
					else if(r30000.isSelected()) S += " where price < 30000";
					//price ������ �������� ���� ��� try again label�� �����ش�
					else {
						select.setVisible(false);
						success.setVisible(false);
						try_again.setVisible(true);
					}
				}
				//remove ingredient ������ �������� ��
				else if(remove_radio.isSelected()) {
					/** ��ҿ�*/
					//delivery�� ���
					if(flag == 0) S = "select * from DBCOURSE_Delivery_O";
					//No delivery�� ���
					else S = "select * from DBCOURSE_Delivery_X";
					//���� ���� üũ�ڽ� �迭
					Checkbox[] check = {meat, seafood, dairy, fungus, vegetable, grain, fruit};
					int count = 0; //���õ� üũ�ڽ��� ������ ���õ� ingredient ���� ���� count ����
					String S2 = "select name from DBCOURSE_Ingredient ";
					for(int i=0; i<check.length; i++) {
						if(check[i].getState() == true) {
							count++;
							if(count == 1) S2 += "where type = '" + check[i].getLabel() + "'";
							else S2 += " or type = '" + check[i].getLabel() + "'";
						}
					}
					//�ƹ��͵� üũ���� �ʾ��� ��
					if(count == 0) S2 += "where type = ''";
					S2 += "group by name";
					count = 0;
					try {
						String temp = "";
						ResultSet rs = stmt.executeQuery(S2);
						while(rs.next()) { //���õ� ��� ingredient�� �ϳ��� ���� while��
							count++;
							if(count == 1) {//ó�� üũ�� ��쿡�� where�� �߰�
								/** sql - ���ظ�, ������*/
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
					/** ��ҿ�*/
					//����� ������ ��� success label�� �����ش�
					select.setVisible(false);
					try_again.setVisible(false);
					success.setVisible(true);
				}
				//type ������ ������ ���
				else if(type_radio.isSelected()) {
					/** sql - ���ظ�*/
					//delivery�� ���
					if(flag == 0) S = "select * from DBCOURSE_Delivery_O";
					//No delivery�� ���
					else S = "select * from DBCOURSE_Delivery_X";
					/** �ڼҿ�*/
					//���� ���� üũ�ڽ� �迭
					Checkbox[] check = {oriental,western,dessert};
					int count = 0;
					for(int i=0; i<check.length; i++) { //üũ�� type�� �߰��ϴ� for��
						if(check[i].getState() == true) {
							count++;
							if(count == 1) S += " where type = '" + check[i].getLabel() + "'";
							else S += " or type = '" + check[i].getLabel() + "'";
						}
					}
					/** ��ҿ�*/
					//�ƹ� �͵� üũ���� �ʾ��� �� �� table�� �����ָ� try again label�� �����ش�
					if(count == 0) {
						S += " where type = ''";
						select.setVisible(false);
						success.setVisible(false);
						try_again.setVisible(true);
					}
					//����� üũ���� �� success label�� �����ش�
					else {
						select.setVisible(false);
						try_again.setVisible(false);
						success.setVisible(true);
					}
				}
				//recommend ������ ������ ���
				else if(recommend_radio.isSelected()) {
					/** sql - ���ظ�*/
					//delivery�� ���
					if(flag == 0) S = "select restaurant_name, city, type, food_name, price " +
							"from DBCOURSE_Delivery_O natural join DBCOURSE_Evaluation";
					//No delivery�� ���
					else S = "select restaurant_name, city, type, food_name, price " +
							"from DBCOURSE_Delivery_X natural join DBCOURSE_Evaluation";
					/** �ڼҿ�*/
					//���� ���� üũ�ڽ� �迭
					Checkbox[] check = {family,friends,date};
					int count = 0;
					for(int i=0; i<check.length; i++) {//üũ�� recommend�� �߰��ϴ� for��
						if(check[i].getState() == true) {
							if(count ==0){
								S += " where recommendation = '" + check[i].getLabel() + "'";
							}
							else S += " or recommendation = '" + check[i].getLabel() + "'";
							count++;
						}
					}
					/** ��ҿ�*/
					//�ƹ� �͵� üũ���� �ʾ��� �� �� table�� �����ָ� try again label�� �����ش�
					if(count == 0) {
						S += " where type = ''";
						select.setVisible(false);
						success.setVisible(false);
						try_again.setVisible(true);
					}
					//����� üũ���� �� success label�� �����ش�
					else {
						select.setVisible(false);
						try_again.setVisible(false);
						success.setVisible(true);
					}
					//food_name���� group���� ���´�
					S += " group by food_name";
				}
				//accompany ������ ������ ���
				else if(accompany_radio.isSelected()) {
					/** sql - ���ظ�, ������, �ڼҿ�*/
					//delivery�� ��� - only for not delivery�� �����̹Ƿ� �� table�� try again �����ش�
					if(flag == 0) {
						S = "select A.restaurant_name, A.city, A.type, A.food_name, A.price " + 
								"from (select * from DBCOURSE_Delivery_O natural join DBCOURSE_Service)as A, " + 
								"(select * from DBCOURSE_Delivery_O natural join DBCOURSE_Service) as B " + 
								"where A.restaurant_name = B.restaurant_name";
						select.setVisible(false);
						success.setVisible(false);
						try_again.setVisible(true);
					}
					//No delivery�� ���
					else {
						S = "select A.restaurant_name, A.city, A.type, A.food_name, A.price " + 
								"from (select * from DBCOURSE_Delivery_X natural join DBCOURSE_Service)as A, " + 
								"(select * from DBCOURSE_Delivery_X natural join DBCOURSE_Service) as B " + 
								"where A.restaurant_name = B.restaurant_name";
						int count=0;
						//������ ���ǿ� ���� where�� �߰�
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

						//food_name���� grouping�Ѵ�
						S += " group by A.food_name";

						/** ��ҿ�*/
						//����� ������ ��� success label�� �����ش�
						select.setVisible(false);
						try_again.setVisible(false);
						success.setVisible(true);
					}
				}
				//parking ������ ������ ���
				else if(park_radio.isSelected()) {
					/** sql - �ڼҿ�*/
					//delivery�� ��� - only for not delivery�� �����̹Ƿ� �� table�� try again �����ش�
					if(flag == 0) {
						S = "select * from DBCOURSE_Delivery_O natural join DBCOURSE_Address";
						select.setVisible(false);
						success.setVisible(false);
						try_again.setVisible(true);
					}
					//No delivery�� ���
					else {
						//������ ���ǿ� ���� where���� String S�� �߰��Ѵ�
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
						/** ��ҿ�*/
						//�ƹ��͵� �������� �ʾ��� ���� �� table�� �����ָ� try again label�� �����ش�
						else{
							S += " where parking = 'o' and parking = 'x'";
							select.setVisible(false);
							success.setVisible(false);
							try_again.setVisible(true);
						}
					}
				}
				/** ��ҿ�*/
				//�ƹ� ���ص� �������� �ʾ��� ���� ������ view�� �����ָ� try again label�� �����ش�
				else {
					if(flag == 0) S = "select * from DBCOURSE_Delivery_O";
					else S = "select * from DBCOURSE_Delivery_X";
					select.setVisible(false);
					success.setVisible(false);
					try_again.setVisible(true);
				}
				/** ��ҿ�*/
				//�� ���ؿ� ���� ������� String S�� sql�� ����
				try {
					//table�� ��� tuple�� �޾ƿ´�
					ResultSet rs = stmt.executeQuery(S);
					//�� tuple�� �޾ƿ´�
					while(rs.next()) {
						Object data[] = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)};
						model.addRow(data);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				//user��忡���� ���̺��� �����ش�
				showTable_user();
				//u2 �������� �����ش�
			}
			/** �ڼҿ�*/
			else if(e.getSource() == nextbutton_u2) {
				x.setVisible(false);
				noingredient.setVisible(false);
				complete.setVisible(true);

				//���̺��� ���� �Ǿ��� ��
				if (table.getSelectedRow() > -1){
					//���õ� ���� �޾ƿ���
					int selectRow = table.getSelectedRow();
					String res_name="";
					String res_city="";
					String foodtype="";
					String res_phone="";
					String foodname="";
					String foodprice="";

					//���̺��� ������ textfield�� choice�� �ݿ��ǰ� �Ѵ�.
					res_name=table.getValueAt(selectRow, 0).toString();
					restaurant_name.setText(res_name);
					foodtype=table.getValueAt(selectRow, 2).toString();
					food_type.setText(foodtype);
					foodname=table.getValueAt(selectRow, 3).toString();
					food_name.setText(foodname);
					foodprice=table.getValueAt(selectRow, 4).toString();
					food_price.setText(String.valueOf(foodprice)+" won");
					String deliverycost="";

					//delivery�� ���
					if(flag==0){
						try{
							//��������� delivery cost�� �޾ƿ´�
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
					//No delivery�� ��� delivery cost�� ������ �ʵ��� ����
					else{
						delivery_cost.setText("");
						delivery_cost_label.setText("");
					}

					/** transaction - ��ҿ�, sql - ��ҿ�, �ڼҿ�*/
					try {
						//setAutoCommit()�Լ��� false ���ڸ� �־� transaction�� �����Ѵ�
						conn.setAutoCommit(false);
						//������ �̸��� foodname�϶� ��Ḧ ���ؼ� numIngredient�� 0�� ����� ������ ����
						String S = "select count(ingredient_name) " + 
								"from (select DBCOURSE_Ingredient.food_name, DBCOURSE_Inventory.ingredient_name, DBCOURSE_Inventory.numIngredient " + 
								"from DBCOURSE_Inventory, DBCOURSE_Ingredient, DBCOURSE_Food " + 
								"where (DBCOURSE_Ingredient.food_name = DBCOURSE_Food.name) " + 
								"and (DBCOURSE_Inventory.ingredient_name = DBCOURSE_Ingredient.name) " + 
								"and(DBCOURSE_Inventory.restaurant_name = DBCOURSE_Food.restaurant_name)) as A " + 
								"where numIngredient = 0 and food_name = '" + foodname + "'";
						//ingredient�� ���� �ִ��� Ȯ���ϱ� ���� count(ingredient) tuple �޾ƿ���
						ResultSet rs = stmt.executeQuery(S);
						int count = 0;
						//count ������ numIngredient�� 0�� ����� ������ ����
						if(rs.next()) count = rs.getInt(1);
						if(count == 0) { //��ᰡ �������� ������
							try {
								/** �ڼҿ�*/
								//DBCOURSE_Order�� insert�� �� �� preparedstatement �̿�
								PreparedStatement pStmt=conn.prepareStatement("insert into DBCOURSE_Order values(?,?,?,?)");
								rs = stmt.executeQuery("select max(order_number) from DBCOURSE_Order");

								/** ��ҿ�*/
								//�ֹ���ȣ�� max(order_number)+1�� �ڵ� ����
								if(rs.next()) count = rs.getInt(1);
								count++; 

								/** �ڼҿ�*/
								//delivery�� ���
								if(flag==0){
									pStmt.setInt(1, count);
									pStmt.setString(2, res_name);
									pStmt.setString(3, foodname);
									//���İ��ݿ� ��޷Ḧ ���Ͽ� ���İ����� ����
									pStmt.setInt(4, (Integer.valueOf(foodprice)+Integer.valueOf(deliverycost)));
									pStmt.executeUpdate();
								}
								//No delivery�� ���
								else{
									pStmt.setInt(1, count);
									pStmt.setString(2, res_name);
									pStmt.setString(3, foodname);
									pStmt.setInt(4, (Integer.valueOf(foodprice)));
									pStmt.executeUpdate();
								}
								/** ��ҿ�*/
								//��� �� �ֹ��� ��Ḧ �����´�
								rs = stmt.executeQuery("select ingredient " +
										"from DBCOURSE_Order_Ingredient " +
										"where order_number >= all(select order_number from DBCOURSE_Order_Ingredient)");
								String ingr = "";
								while(rs.next()) { //��� �� �ֹ��� ����������� ��Ḧ �ϳ��� ���� �丮�Ѵ�.
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
						else { //��ᰡ �����ϸ� X�� noingredient label�� �����ش�
							complete.setVisible(false);
							x.setVisible(true);
							noingredient.setVisible(true);
						}
						//�����۾��� �� �̷����� �� ���������� �����Ѵ�
						conn.commit();
					} catch (SQLException e1) {
						e1.printStackTrace(); //���� �߻� ���� ���
						if(conn != null) {
							try {
								//SQLException �߻� �� rollback���� ���������� ���� ������ ����
								conn.rollback();
							} catch (SQLException e2) {
								e2.printStackTrace();
							}
						}
					} finally {
						try { //�ٽ� setAutoCommit�� true�� ����
							conn.setAutoCommit(true);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
					/** �ڼҿ�*/
					try {
						//��������� ��ȭ��ȣ�� �����´�.
						ResultSet rs = stmt.executeQuery("select phone_number from DBCOURSE_restaurant where name="+"'"+res_name+"'");
						while(rs.next()) {
							res_phone=rs.getString(1);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					//�������� order�� ������ ������ ����
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
				/** ��ҿ�*/
				else{
					//����� �������� ���� ��� try again label�� �����ش�
					select.setVisible(false);
					success.setVisible(false);
					try_again.setVisible(true);
				}
			}
			/** �ڼҿ�*/
			else if(e.getSource()==getinfobutton){
				if(delete_radio.isSelected() || update_radio.isSelected()) {
					//table�� �������� ��
					if (table.getSelectedRow() > -1){
						//���̺�κ��� ������ �޾ƿͼ� �����ش�.
						int selectRow = table.getSelectedRow();//���õ� ��
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
						//table�� �������� �ʰ� get information button�� �������� �����޼����� �����ش�
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
				/** ��ҿ�*/
				//insert�� ������ �����ε� getinfo�� ������ insert label�� �����ش�
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
				//���� ��ư�� �ƹ��͵� �������� �ʰ� getinfo�� ������ select button label�� �����ش�
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
			/** �ڼҿ�*/
			else if(e.getSource()==enterinfobutton){
				//���ο� ������ textfield�� choice�κ��� �޾ƿ´�.
				newname=name.getText();
				newposition=c_position.getSelectedItem();
				newresname=c_res_name.getSelectedItem();
				String newsalary_s=salary.getText();

				//textfield�� ������� �� �����޽��� ���
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
				//textfield�� ������� ���� ��
				else{
					try{
						//salary�� �ڿ����� �ƴ� String�� �ԷµǸ� exception ó��
						newsalary=Integer.valueOf(newsalary_s);

						/** ��ҿ�*/
						model.setRowCount(0);  //���̺� ������ ���� �ʱ�ȭ

						/** �ڼҿ�*/
						//insert radio button�� ������ ��
						if(insert_radio.isSelected()){
							try {
								//���� ���̵� +1 �����ν� ���ο� ���̵� �ڵ����� �ο��Ѵ�
								int newid=0;
								ResultSet rs1 = stmt.executeQuery("select max(id) from DBCOURSE_Employee");
								if(rs1.next()) newid = Integer.valueOf(rs1.getString(1));

								//�Ϸ�޽����� ����Ѵ�
								inform_emp_up_1.setVisible(false);
								inform_emp_down_1.setVisible(false);
								inform_emp_up_done.setVisible(true);
								inform_emp_down_done.setVisible(true);
								inform_emp_down_errtable.setVisible(false);
								inform_emp_error.setVisible(false);
								inform_emp_down_format.setVisible(false);
								inform_emp_select.setVisible(false);
								inform_emp_insert.setVisible(false);

								//���ο� ������ table�� insert�Ѵ�
								//DBCOURSE_Employee�� prepared statement�� �����
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
						//delete radiobutton�� �����ϸ�
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

							//employee id�� ã�� employee ������ DBCOURSE_Employee���� �����Ѵ�
							try{
								stmt.executeUpdate("delete from DBCOURSE_Employee where id='"+emp_ID+"'");
							} catch(SQLException se) {
								se.printStackTrace();
							}
						}
						//update radio button�� ���õǾ��� ��
						else if(update_radio.isSelected()){
							//�ȳ� �޽����� ����Ѵ�.
							inform_emp_up_1.setVisible(false);
							inform_emp_down_1.setVisible(false);
							inform_emp_up_done.setVisible(true);
							inform_emp_down_done.setVisible(true);
							inform_emp_down_errtable.setVisible(false);
							inform_emp_error.setVisible(false);
							inform_emp_down_format.setVisible(false);
							inform_emp_select.setVisible(false);
							inform_emp_insert.setVisible(false);

							/** ��ҿ� - Ʈ�����, �ڼҿ� - insert*/
							//delete�� insert�� ���� �Ͼ�� �ϱ� ������ transaction
							try{
								//setAutoCommit()�Լ��� false ���ڸ� �־� transaction�� �����Ѵ�
								conn.setAutoCommit(false);
								//dbcourse_seriesName ���̺��� ����ڰ� �Է��� �ø��� ����
								stmt.executeUpdate("delete from DBCOURSE_Employee where id='"+emp_ID+"'");
								PreparedStatement pStmt=conn.prepareStatement("insert into DBCOURSE_Employee values(?,?,?,?,?)");
								pStmt.setString(1, emp_ID);
								pStmt.setString(2, newname);
								pStmt.setString(3, newresname);
								pStmt.setString(4, newposition);
								pStmt.setInt(5, newsalary);
								pStmt.executeUpdate();

								//�����۾��� �� �̷����� �� ���������� �����Ѵ�
								conn.commit();
							} catch(SQLException se) {
								se.printStackTrace();
								if(conn != null) {
									try {
										//SQLException �߻� �� rollback���� ���������� ���� ������ ����
										conn.rollback();
									} catch (SQLException e1) {
										e1.printStackTrace();
									}
								}
							} finally {
								try { //�ٽ� setAutoCommit�� true�� ����
									conn.setAutoCommit(true);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
							}
						}
						/**�ڼҿ�*/
						else{ //radio button �ƹ��͵� ������ �ʰ� ���͸� ������ �����޽��� ���
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
						/** ��ҿ�*/
						//���� ���� ����� �޾ƿ���
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
						//�ٲ� ���̺��� �����ش�.
						showTable_emp();
					} catch(NumberFormatException num){ //Integer�� �Է����� ������ �����޽����� �����ش�.
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
			/** �ڼҿ�*/
			//salary�� up�ϴ� ���
			else if(e.getSource()==givebutton_up){
				try{
					String str=plus_salary.getText();
					Double value=Double.valueOf(str);

					/** ��ҿ�*/
					model.setRowCount(0); //���̺� ������ ���� �ʱ�ȭ

					/** �ڼҿ�*/
					if(value<0){ //�����̸� �����޽��� ���
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
						//salary ����� update�Ѵ�.
						String S="update DBCOURSE_Employee set salary=salary*"+String.valueOf(1+Double.valueOf(str)/(double)100);
						try {
							stmt.executeUpdate(S);
							//���� ���� ����� �޾ƿ���
							ResultSet rs = stmt.executeQuery("SELECT * FROM DBCOURSE_Employee");
							while(rs.next()) {
								Object data[] = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)};
								model.addRow(data);
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						//�Ϸ�޼����� ����Ѵ�.
						inform_emp_up_1.setVisible(false);
						inform_emp_down_1.setVisible(false);
						inform_emp_up_done.setVisible(true);
						inform_emp_down_done.setVisible(true);
						inform_emp_down_errtable.setVisible(false);
						inform_emp_error.setVisible(false);
						inform_emp_down_format.setVisible(false);
						inform_emp_select.setVisible(false);
						inform_emp_insert.setVisible(false);
						//table�� �����ش�
						showTable_emp();
					}
				} catch(NumberFormatException num){ //input�� Integer������ �ƴϸ� �����޼��� ���
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
			/** �ڼҿ�*/
			//salary�� down�ϴ� ���
			else if(e.getSource()==givebutton_down){
				//textfield�κ��� String�� �޾ƿ´�.
				try{
					String str=minus_salary.getText();
					Double value=Double.valueOf(str);

					/** ��ҿ�*/
					model.setRowCount(0);  //���̺� ������ ���� �ʱ�ȭ

					/** �ڼҿ�*/
					//textfield�� ���� �����̰ų� 100���� ũ��
					if(value>100||value<0){
						//�����޼��� ���
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
					else{ //0�� 100 ������ ����� salary update
						String S="update DBCOURSE_Employee set salary=salary*"+String.valueOf(1-Double.valueOf(str)/(double)100);
						try {
							stmt.executeUpdate(S);
							//���� ������ ����� �޾ƿ´�
							ResultSet rs = stmt.executeQuery("SELECT * FROM DBCOURSE_Employee");
							while(rs.next()) {
								Object data[] = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)};
								model.addRow(data);
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						//�Ϸ�޼��� ���
						inform_emp_up_1.setVisible(false);
						inform_emp_down_1.setVisible(false);
						inform_emp_up_done.setVisible(true);
						inform_emp_down_done.setVisible(true);
						inform_emp_down_errtable.setVisible(false);
						inform_emp_error.setVisible(false);
						inform_emp_down_format.setVisible(false);
						inform_emp_select.setVisible(false);
						inform_emp_insert.setVisible(false);
						//���ο� ���̺��� �����ش�.
						showTable_emp();
					}
				}catch(NumberFormatException num){ //input�� Integer������ �ƴϸ� exception ó��
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
			/** �ڼҿ�*/
			//salary�� �������� ������ �ذ��ϴ� ���
			else if(e.getSource()==firebutton){
				//textfield�κ��� string�� �޾ƿ´�
				try{
					String str=delete_salary.getText();
					Double value=Double.valueOf(str);

					/** ��ҿ�*/
					model.setRowCount(0);  //���̺� ������ ���� �ʱ�ȭ

					/** �ڼҿ�*/
					String S="delete from DBCOURSE_Employee where salary>"+String.valueOf(value);

					//textfield�� �Էµ� ������ ū salary�� ���� employee�� delete�Ѵ�.
					try {
						stmt.executeUpdate(S);
						//���� ������ ����� �޾ƿ´�.
						ResultSet rs = stmt.executeQuery("SELECT * FROM DBCOURSE_Employee");
						while(rs.next()) {
							Object data[] = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)};
							model.addRow(data);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					//�Ϸ� �޼����� ����Ѵ�.
					inform_emp_up_1.setVisible(false);
					inform_emp_down_1.setVisible(false);
					inform_emp_up_done.setVisible(true);
					inform_emp_down_done.setVisible(true);
					inform_emp_down_errtable.setVisible(false);
					inform_emp_error.setVisible(false);
					inform_emp_down_format.setVisible(false);
					inform_emp_select.setVisible(false);
					inform_emp_insert.setVisible(false);
					//���ο� ���̺��� �����ش�.
					showTable_emp();
				} catch(NumberFormatException num){ //input������ Integer�� �ƴϸ� exception ó���Ѵ�
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
			/** ��ҿ�*/
			//������ order�� ó���Ͽ� profit�� �ø��� ��ư
			else if (e.getSource() == takeorderbutton) {
				//order table�� profit�� �����ִ� ���̺��� �ʱ�ȭ�Ѵ�
				model.setRowCount(0);
				model2.setRowCount(0);
				//this is order list label�� �����ش�
				empty.setVisible(false);
				profit.setVisible(false);
				thisis.setVisible(true);
				order.setVisible(true);
				String res = ""; String ingr = ""; int price = 0;
				//order ó���� profit update�� �׻� ���� �̷����� �ϱ� ������ transaction
				try {
					//setAutoCommit()�Լ��� false ���ڸ� �־� transaction�� �����Ѵ�
					conn.setAutoCommit(false);
					//order number�� ���� ���� tuple�� ����
					ResultSet rs = stmt.executeQuery("select restaurant_name, price from DBCOURSE_Order " + 
							"where order_number <= all(select order_number from DBCOURSE_Order)");
					if(rs.next()) {
						res = rs.getString(1);
						price = rs.getInt(2);
					}
					//������ restaurant name�� price�� profit�� update�Ѵ�
					stmt.executeUpdate("update DBCOURSE_Restaurant " +
							"set profit = profit + " + price +
							" where name = '" + res + "'");
					//ó���� ���� order�� order table���� delete�Ѵ�
					stmt2.executeUpdate("delete from DBCOURSE_Order " +
							"where order_number <= all(select order_number from (select order_number from DBCOURSE_Order) as A)");

					//���� order table�� tuple�� �����´�
					rs = stmt.executeQuery("select * from DBCOURSE_Order");
					while(rs.next()) {
						Object data[] = {rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4)};
						model.addRow(data);
					}
					//update�� restaurant �� ������ profit�� �����´�
					rs = stmt.executeQuery("select name as restaurant_name, profit from DBCOURSE_Restaurant");
					while(rs.next()) {
						Object data[] = {rs.getString(1), rs.getInt(2)};
						model2.addRow(data);
					}
					//�����۾��� �� �̷����� �� ���������� �����Ѵ�
					conn.commit();
				} catch (SQLException e1) {
					e1.printStackTrace();
					if(conn != null) {
						try {
							//SQLException �߻� �� rollback���� ���������� ���� ������ ����
							conn.rollback();
						} catch (SQLException e2) {
							e2.printStackTrace();
						}
					}
				} finally {
					try { //�ٽ� setAutoCommit�� true�� ����
						conn.setAutoCommit(true);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				//order table�� restaurant �� profit table�� �����ش�
				showTable_order();
				showTable_profit();
			}
			/** ��ҿ�*/
			//���� ���Ḧ �ϰ� �����鿡�� daily salary�� �����ϰ� ���� profit�� ����ϴ� ��ư
			else if(e.getSource() == closebutton) {
				try {
					int count = 0;
					//order table�� �� ó�� �Ǿ� ��� �ִ� �������� Ȯ��
					ResultSet rs = stmt.executeQuery("select count(order_number) from DBCOURSE_Order");
					if(rs.next()) count = rs.getInt(1);
					//order table�� ���� �ֹ��� ���������� list�� ����� label�� �����ش�
					if(count != 0) {
						thisis.setVisible(false);
						order.setVisible(false);
						profit.setVisible(false);
						empty.setVisible(true);
					}
					//order table�� ���������
					else {
						model2.setRowCount(0);
						thisis.setVisible(true);
						order.setVisible(false);
						empty.setVisible(false);
						profit.setVisible(true);

						//������� �� sum(salary)�� ���Ѵ�
						rs = stmt.executeQuery("select restaurant_name, sum(salary) " +
								"from DBCOURSE_Employee " +
								"group by restaurant_name");
						String res = ""; int sum = 0;
						while(rs.next()) { //������ restaurant name�� sum(salary)�� profit�� update�Ѵ�
							res = rs.getString(1); sum = rs.getInt(2);
							stmt2.executeUpdate("update DBCOURSE_Restaurant " +
									"set profit = profit - " + sum +
									" where name = '" + res + "'");
						}
						//update�� table�� �����ش�
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
		/** ��ҿ�*/
		//user����� table�� �����ִ� �޼ҵ�
		void showTable_user() {
			//defaultTableModel�� ������� JTable ����
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
			//price column ������ ����
			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
			dtcr.setHorizontalAlignment(SwingConstants.RIGHT);
			table.getColumn("price").setCellRenderer(dtcr);
			table.setRowHeight(H/30);
			//table ����� ������ ��� ����
			table.setAutoCreateRowSorter(true);
			//JTable�� ���� JScrollPane ����
			jsp = new JScrollPane(table);
			jsp.setPreferredSize(new Dimension(W/2,H/2));
			//tablepanel�� ��� ������Ʈ ����
			tablepanel_u.removeAll();
			//tablepanel�� ��ġ ����
			tablepanel_u.setBounds(7*W/15, H/10, W/2, H/2);
			//tablepanel�� JTable�� ���� JScrollPane �߰�
			tablepanel_u.add(jsp);
			//tablepanel�� UI reset
			tablepanel_u.updateUI();
			//tablepanel�� ����� �����ϰ� ����
			tablepanel_u.setOpaque(false);
		}
		//manager����� employee management ������������ table�� �����ִ� �޼ҵ�
		void showTable_emp() {
			//defaultTableModel�� ������� JTable ����
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
			//salary column ������ ����
			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
			dtcr.setHorizontalAlignment(SwingConstants.RIGHT);
			table.getColumn("salary(daily)").setCellRenderer(dtcr);
			table.setRowHeight(H/30);
			//table ����� ������ ��� ����
			table.setAutoCreateRowSorter(true);
			//JTable�� ���� JScrollPane ����
			jsp = new JScrollPane(table);
			jsp.setPreferredSize(new Dimension(2*W/3,3*H/5));
			//tablepanel�� ��� ������Ʈ ����
			tablepanel_emp.removeAll();
			//tablepanel�� ��ġ ����
			tablepanel_emp.setBounds(4*W/15, H/25, 2*W/3, 3*H/5);
			//tablepanel�� JTable�� ���� JScrollPane �߰�
			tablepanel_emp.add(jsp);
			//tablepanel�� UI reset
			tablepanel_emp.updateUI();
			//tablepanel�� ����� �����ϰ� ����
			tablepanel_emp.setOpaque(false);
		}
		//manager����� order processing ������������ order table�� �����ִ� �޼ҵ�
		void showTable_order() {
			//defaultTableModel�� ������� JTable ����
			table = new JTable(model);
			table.getTableHeader().setBackground(Color.WHITE);
			table.getTableHeader().setFont(new Font("Britannic Bold",Font.PLAIN,H/35));
			table.setSelectionBackground(Color.decode("#FCE4EC"));
			table.setFont(new Font("Courier",Font.PLAIN,H/35));
			table.getColumn("number").setPreferredWidth(W/50);
			table.getColumn("restaurant_name").setPreferredWidth(W/15);
			table.getColumn("food_name").setPreferredWidth(W/10);
			table.getColumn("price").setPreferredWidth(W/50);
			//price column ������ ����
			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
			dtcr.setHorizontalAlignment(SwingConstants.RIGHT);
			table.getColumn("price").setCellRenderer(dtcr);
			table.setRowHeight(H/30);
			//table ����� ������ ��� ����
			table.setAutoCreateRowSorter(true);
			//JTable�� ���� JScrollPane ����
			jsp = new JScrollPane(table);
			jsp.setPreferredSize(new Dimension(11*W/20,H/3));
			//tablepanel�� ��� ������Ʈ ����
			tablepanel_order.removeAll();
			//tablepanel�� ��ġ ����
			tablepanel_order.setBounds(W/30, H/10, 11*W/20, H/3);
			//tablepanel�� JTable�� ���� JScrollPane �߰�
			tablepanel_order.add(jsp);
			//tablepanel�� UI reset
			tablepanel_order.updateUI();
			//tablepanel�� ����� �����ϰ� ����
			tablepanel_order.setOpaque(false);
		}
		//manager����� order processing ������������ profit table�� �����ִ� �޼ҵ�
		void showTable_profit() {
			//defaultTableModel�� ������� JTable ����
			table2 = new JTable(model2);
			table2.getTableHeader().setBackground(Color.WHITE);
			table2.getTableHeader().setFont(new Font("Britannic Bold",Font.PLAIN,H/35));
			table2.setSelectionBackground(Color.decode("#FCE4EC"));
			table2.setFont(new Font("Courier",Font.PLAIN,H/35));
			table2.getColumn("restaurant_name").setPreferredWidth(W/15);
			table2.getColumn("profit").setPreferredWidth(W/50);
			//profit column ������ ����
			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
			dtcr.setHorizontalAlignment(SwingConstants.RIGHT);
			table2.getColumn("profit").setCellRenderer(dtcr);
			table2.setRowHeight(H/30);
			//table ����� ������ ��� ����
			table2.setAutoCreateRowSorter(true);
			//JTable�� ���� JScrollPane ����
			jsp2 = new JScrollPane(table2);
			jsp2.setPreferredSize(new Dimension(W/3, 2*H/5));
			//tablepanel�� ��� ������Ʈ ����
			tablepanel_profit.removeAll();
			//tablepanel�� ��ġ ����
			tablepanel_profit.setBounds(31*W/50, H/10, W/3, 2*H/5);
			//tablepanel�� JTable�� ���� JScrollPane �߰�
			tablepanel_profit.add(jsp2);
			//tablepanel�� UI reset
			tablepanel_profit.updateUI();
			//tablepanel�� ����� �����ϰ� ����
			tablepanel_profit.setOpaque(false);
		}

		@Override
		public void itemStateChanged(ItemEvent arg0) {}
	}
}