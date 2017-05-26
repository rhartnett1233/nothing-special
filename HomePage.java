import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.text.DateFormat;


class HomePage1{

    static JButton btnPrevWeek, btnNextWeek, btnAddAssignment, btnAddEvent, btnEditAssignment, btnEditEvent, btnRefresh, btnAccount, btnAssignmentList, btnEventList; 
    static JButton btnCreateAssignment, btnCreateEvent;
    static JButton btnCancel;
    static JButton btnChangeAccount, btnChangeAssignment, btnChangeEvent;
    static JButton btnDeleteAssignment, btnDeleteEvent, btnDeleteProfile;
    static JButton btnLogOff;
    static JButton btnClose;
    static JTable tblWeekCalendar;
    static JDesktopPane desktop;
    static JFrame frmMain;
    static Container pane;
    static Container pane1,pane2,pane3,pane4;
    static JScrollPane stblCalendar; //The scrollpane
    static JLayeredPane pnlCalendar,pnlButtons,pnlTime;
    static int realYear, realMonth, realDay, currentYear, currentMonth;
    static JTable tblCalendar;
    static DefaultTableModel mtblCalendar; //Table model
    static JLabel lblLogo,lblName,lblStarttime,lblEndtime,lblLocation,lblClass,lblPriority,lblDueDate,lblEstimatedTime,lblPickAssignment,lblRepeatedDays,lblOther,lblPickEvent,lblUsername,lblEmail,lblNameofUser,lblCurrentBedtime,lblCurrentWakeuptime,lblChangeBedtime,lblChangeWakup;
    static JTable tblTime;
    static DefaultTableModel timeTable; //Table model
    static JLabel[] lblAssignments, lblEvents;
    int calendarwidth, width, height,calendarheight;
    static Profile dd;
    static final String IMG_PATH = "src/Logo/Capture.jpg";
    static LinkedList<JButton> btnAssignments;
    static LinkedList<JLabel> lblAssignmentList,lblEventList;
    static JFrame frmAddAssignment,frmEditAssignment,frmAddEvent,frmEditEvent,frmAccount,frmAssignmentList,frmEventList;
    private Client c;
    static JTextField JTextName,JTextClass, JTextLocation,JTextDays;
    static JComboBox JComboDueDate, JComboTimetoComplete, JComboPriority, JComboPickAssignment, JComboStarttime, JComboEndtime, JComboPickEvent, JComboStartDate, JComboEndDate, JComboDueHour;
    static String[] times,dates,Assignments2,Events2,timeneeded,priority;
    static boolean wait;
    static BufferedImage img = null;
    private Calendar curCal;
    private int panelOffset;
    static Color back = new Color(125,10,10);
    private JButton list1[];
    int size33;
    private LinkedList<CalendarEvent> calendarList;

    
    public HomePage1(){
    	btnAssignments = new LinkedList<JButton>();
    	curCal.getInstance();
      	panelOffset = 0;
      	calendarList = new LinkedList<CalendarEvent>();	
    }
    

    public void DisplayHomePage(String usernameInput){

    	try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}

      	catch (ClassNotFoundException e) {}
      	catch (InstantiationException e) {}
      	catch (IllegalAccessException e) {}
      	catch (UnsupportedLookAndFeelException e) {}
      	c = new Client(usernameInput);
      	dd = null;
    	setupFrame();
      	declareButtons();
      	declareLabels();
      	colorLabels();    
          
      	JTextName = new JTextField();
      	JTextClass = new JTextField();
      	JTextLocation = new JTextField();
      	JTextDays = new JTextField();
        
      	createComboBoxes();
        
      	JComboBox<String>JComboDueDate = new JComboBox<>(dates);
      	JComboBox<String>JComboTimetoComplete = new JComboBox<>(timeneeded);
      	JComboBox<String>JComboPriority = new JComboBox<>(priority);
      	JComboBox<String>JComboStarttime = new JComboBox<>(times);
      	JComboBox<String>JComboEndtime = new JComboBox<>(times);
      	JComboBox<String>JComboStartDate= new JComboBox<>(dates);
      	JComboBox<String>JComboEndDate = new JComboBox<>(dates);
      	JComboBox<String>JComboDueHour = new JComboBox<>(times);

        
        //GET SCREENSIZE FOR USER'S COMPUTER
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int)screenSize.getWidth()-((int)(screenSize.getHeight()/12)) ;
		height = (int)screenSize.getHeight();
		
		  //READING/SCALING IN LOGO
		try {
			img = ImageIO.read(this.getClass().getResource("Logo.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}

		Image scaled = img.getScaledInstance((int)(width/2.2766),(int)(height/7.38), Image.SCALE_FAST);
      	ImageIcon icon = new ImageIcon(scaled);
      	lblLogo = new JLabel(icon);
                
      	//TABLE CREATION
      	timeTable = new DefaultTableModel(){public boolean isCellEditable(int rowIndex, int mColIndex){return false;}};
      	tblTime = new JTable(timeTable);
      	mtblCalendar = new DefaultTableModel(){public boolean isCellEditable(int rowIndex, int mColIndex){return false;}};
      	tblCalendar = new JTable(mtblCalendar);
      	stblCalendar = new JScrollPane(tblCalendar);
        
      //PANEL CREATION
      	pnlCalendar = new JLayeredPane();
      	pnlButtons = new JLayeredPane();
      	pnlTime = new JLayeredPane();
            
  
      //COLOR CREATION FOR ELEMENTS
      	Color BabyBlue =  new Color(157,205,255);
     
        
      //ADD ELEMENTS TO PANELS
      	pane.add(pnlCalendar);
      	pnlCalendar.add(stblCalendar,new Integer(1));
      	pnlCalendar.setBackground(back);        
        
      //BUTTONS PANEL
      	pane.add(pnlButtons);
      	pnlButtons.add(btnPrevWeek);
      	pnlButtons.add(btnNextWeek);
      	pnlButtons.add(btnAccount);
      	pnlButtons.add(btnAddAssignment);
      	pnlButtons.add(btnEditAssignment);
      	pnlButtons.add(btnAddEvent);
      	pnlButtons.add(btnEditEvent);
      	pnlButtons.add(btnRefresh);
      	pnlButtons.add(lblLogo);
      	pnlButtons.add(btnAssignmentList);
      	pnlButtons.add(btnEventList);
      
      //TIME PANEL
      	pane.add(pnlTime);
      	pnlTime.add(tblTime);
        
      //SET BACKGROUDNS AND OTHER
      	frmMain.getContentPane().setBackground(back);
      	btnPrevWeek.setBackground(Color.GRAY); 
      	btnNextWeek.setBackground(Color.GRAY);
      	btnNextWeek.setContentAreaFilled(false);
      	btnNextWeek.setOpaque(true);
      	btnPrevWeek.setContentAreaFilled(false);
      	btnPrevWeek.setOpaque(true);
      	Border thickBorder = new LineBorder(Color.BLACK,2);
      	btnNextWeek.setBorder(thickBorder);
      	btnPrevWeek.setBorder(thickBorder);
      	pnlButtons.setBackground(back);
      	pnlTime.setBackground(back);
        
        
        //GET BOUND RELIANT COORDINATES
      	int x = (int) (width/6)*5-(int)width/14;
      	int y = (int) (width/6)*4-(int)width/68;
      	int z = (int) (width/6)*3-(int)width/68;
      	calendarheight = height-(int)(height/4.5);
      	calendarwidth = width - (int)(width/19.5);
      	int timewidth = (int)(width/19.5);
      	int timeheight = calendarheight-(int)(height/35);
        
      //SETTING LOCATIONS FOR ALL COMPONENTS
        
      //PANELS
      	pnlButtons.setBounds(0,0,width,(int)(height/5.35));
      	pnlCalendar.setBounds((int)(width/27.32),(int)(height/5.35), calendarwidth, calendarheight);
      	pnlTime.setBounds(0,(int)(height/5.35),(int)(width/27.32),calendarheight);
        
      //BUTTONS
      	btnRefresh.setBounds((int)(width/73.8),(int)(height/12.3),(int)(width/11.38),(int)(height/24.6));
      	btnAddAssignment.setBounds(y,(int)(height/12.3),(int)(width/11.38),(int)(height/24.6));
      	btnAddEvent.setBounds(y,(int)(height/36.9),(int)(width/11.38),(int)(height/24.6)); 
      	btnEditAssignment.setBounds(x,(int)(height/12.3),(int)(width/11.38),(int)(height/24.6)); 
      	btnEditEvent.setBounds(x,(int)(height/36.9),(int)(width/11.38),(int)(height/24.6));
      	btnPrevWeek.setBounds((int)(width/27),(int)(height/7.03),((int)(width/19.51)), (int)(height/24.6));
      	btnNextWeek.setBounds(width-((int)(width/17.0575)),(int)(height/7.03),(int)(width/19.51), (int)(height/24.6));
      	btnAccount.setBounds((int)(width/73.8),(int)(height/36.9), (int)(width/11.38), (int)(height/24.6));
      	btnAssignmentList.setBounds(y+((int)(width/4.533)),(int)(height/12.3),(int)(width/11.38),(int)(height/24.6));
      	btnEventList.setBounds(y+((int)(width/4.533)),(int)(height/36.9),(int)(width/11.38),(int)(height/24.6));
        
      //OTHER
      	stblCalendar.setBounds(0,0,calendarwidth,calendarheight);
      	tblTime.setBounds(0,(int)(height/29.52),(int)(width/27.32),timeheight);
      	lblLogo.setBounds((int)(width/6.83),(int)(height/73.8),600,120);
        
      //MAKE VISIBLE
      	frmMain.setMinimumSize(new Dimension(400,400));				//set minimize size
      	frmMain.setVisible(true);		
        
        
      //SET ROW/COLUMN COUNT
        
      	tblCalendar.setRowHeight(calendarheight);
      	tblCalendar.getTableHeader().setFont(new Font("Arial",Font.PLAIN,10));
      	mtblCalendar.setRowCount(1);
        
      	tblTime.setRowHeight(timeheight/17);
      	timeTable.setRowCount(17);

      	createHeaders();

        
      //CODING WEEKDAY FOR MONTH OF DECEMBER
        
      	int [] daysofDecember = new int[33];
      	String [] theday = new String[33];
        for(int a = 1;a<=32;a++){
        	if(a%7 == 1){
        		daysofDecember[a] = 4;
        		theday[a] = "Thursday";
        	}
        	if(a%7 == 2){
        		daysofDecember[a] = 5;
        		theday[a] = "Friday";
        	}
        	if(a%7 == 3){
        		daysofDecember[a] = 6;
        		theday[a] = "Saturday";
        	}
        	if(a%7 == 4){
        		daysofDecember[a] = 0;
        		theday[a] = "Sunday";
        	}
        	if(a%7 == 5){
        		daysofDecember[a] = 1;
        		theday[a] = "Monday";
        	}
        	if(a%7 == 6){
        		daysofDecember[a] = 2;
        		theday[a] = "Tuesday";
        	}
        	if(a%7 == 7){
        		daysofDecember[a] = 3;
        		theday[a] = "Wednesday";
        	}
        }
        	
        	
        	
        //GETTING REAL DATE AND CONVERT TO INT FORM
        	
        SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy");
        String date = sdf.format(new Date());
        char[] array= date.toCharArray();
        String dateref = "";
        String monthref = "";
        String yearref = "";
        int counter = 0;
         	 
        for(int c = 0; c<date.length();c++){
         	if(array[c] == '/'){
         		counter++;
         	}
         	else if(counter == 0){
         		monthref += array[c];
         	}
         	else if(counter == 1){
         		dateref += array[c];
         	}
         	else if(counter == 2){
         		yearref += array[c];
         	}
        }
        
        counter = 0;	 
       	int dayofthemonth = Integer.parseInt(dateref);
       	int whichmonth = Integer.parseInt(monthref);
       	int whichyear = Integer.parseInt(yearref);
       	 
       	 
       	 
       	 
       	
        
        
        //CREATION OF TIMES FOR TIME COLUMN
        
        timeTable.addColumn("T");
        for(int b = 7; b<24;b++){
        	if(b<12){
        		tblTime.setValueAt((Object)b+":00am",b-7, 0);
        	}
        	if(b == 12){
        		tblTime.setValueAt((Object)"12:00pm", b-7, 0);
        	}
        	if(b>12){
        		tblTime.setValueAt((Object)(b-12)+":00pm", b-7, 0);
        	}
          tblTime.setFont(new Font("Arial",Font.PLAIN,8));
        }
        
        
        
        
        //TABLE SPECIFICATIONS
        
        
        tblCalendar.getParent().setBackground(tblCalendar.getBackground()); //Set background
        //No resize/reorder
        tblCalendar.getTableHeader().setResizingAllowed(false);
        tblCalendar.getTableHeader().setReorderingAllowed(false);
        //single cell selection
        tblCalendar.setColumnSelectionAllowed(true);
        tblCalendar.setRowSelectionAllowed(true);
        tblCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblCalendar.setShowVerticalLines(true);
        
        tblTime.getParent().setBackground(back); //Set background
        //No resize/reorder
        tblTime.getTableHeader().setResizingAllowed(false);
        tblTime.getTableHeader().setReorderingAllowed(false);
        //single cell selection
        tblTime.setColumnSelectionAllowed(true);
        tblTime.setRowSelectionAllowed(true);
        tblTime.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblTime.setShowVerticalLines(true);
        tblTime.setShowHorizontalLines(true);
	
    
    //FIND COORDINATES FOR ASSIGNMENTS/EVENTS
   
        calendarList = c.display();
        LinkedList<CalendarEvent> calWeek = splitCalendar(calendarList);
        DisplayCalendarEvents(calWeek);
    
    
    
    //BUTTON ACITON METHODS
  //ACTION LISTENERS FOR BUTTONS
    	btnPrevWeek.addActionListener(new ActionListener(){
      		public void actionPerformed(ActionEvent e){
          		if(panelOffset != -1){
            		panelOffset--;
            		clearCalendar();  
            		LinkedList<CalendarEvent> hold = splitCalendar(calendarList);
            		DisplayCalendarEvents(hold);
            		removeHeaders();
            		createHeaders();
          		}
      		}
      	});
    	btnNextWeek.addActionListener(new ActionListener(){
      		public void actionPerformed(ActionEvent e){
          		if(panelOffset != 1){
            		panelOffset++;
            		clearCalendar();
            		LinkedList<CalendarEvent> hold = splitCalendar(calendarList);
            		DisplayCalendarEvents(hold);
            		removeHeaders();
            		createHeaders();
          		}
      		}
      	});
    	btnAccount.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    		  	frmAccount = new JFrame ("Account Information"); //Create frame
  		  		int frmwidth = width/((int)(width/400));
  		  		int frmheight = (int)(height/1.25);
  		  		frmAccount.setSize(frmwidth,frmheight);//Set screen to full
  		  		frmAccount.setLocation((width/3), height/8);
  		  		pane1 = frmAccount.getContentPane(); //Get content pane
  		  		pane1.setLayout(null); //Apply null layout
  		  		frmAccount.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  		  		frmAccount.setBackground(Color.black);
  		  		pane1.setBackground(Color.black);
  		  		int imgwidth = (int)(frmwidth/1.33);
  		  		int imgheight = (int)(frmheight/5);
  		  		Image scaled1 = img.getScaledInstance(imgwidth,imgheight, Image.SCALE_FAST);
  		  		ImageIcon icon = new ImageIcon(scaled1);
  		  		lblLogo = new JLabel(icon);
          		dd = c.getAccountInfo();
          		System.out.println(dd.toString());
          		JLabel lblusernamedd = new JLabel(dd.getUsername());
          		JLabel lblnamedd = new JLabel(dd.getName());
          		JLabel lblemaildd = new JLabel(dd.getEmail());
          		JLabel lblbeddd = new JLabel(dd.getBedtime());
          		JLabel lblwakedd = new JLabel(dd.getWaketime());
          		lblusernamedd.setFont(new Font("Arial", Font.PLAIN, 10));
          		lblnamedd.setFont(new Font("Arial", Font.PLAIN, 10));
          		lblemaildd.setFont(new Font("Arial", Font.PLAIN, 10));
          		lblbeddd.setFont(new Font("Arial", Font.PLAIN, 10));
          		lblwakedd.setFont(new Font("Arial", Font.PLAIN, 10));

          		pane1.add(lblusernamedd);
          		pane1.add(lblnamedd);
          		pane1.add(lblemaildd);
          		pane1.add(lblbeddd);
  		  		pane1.add(lblUsername);
  		  		pane1.add(lblEmail);
  		  		pane1.add(lblNameofUser);
  		  		pane1.add(lblCurrentBedtime);
  		  		pane1.add(lblCurrentWakeuptime);
  		  		pane1.add(lblLogo);
  		  		pane1.add(btnCancel);
  		  		pane1.add(btnChangeAccount);
  		  		pane1.add(btnLogOff);
  		  		pane1.add(lblChangeBedtime);
  		  		pane1.add(lblChangeWakup);
  		  		pane1.add(JComboStarttime);
  		  		pane1.add(JComboEndtime);
          		pane1.add(lblwakedd);
  		  		int col = (int)(frmwidth/7);
  		  		int boxw = (int)(width/13.6);
  		  		int gap = (int) (height/18.425);
  		  		int boxh = (int)(height/36.85);
     	    	lblUsername.setBounds(col,gap*4,boxw*4,boxh);
     	    	lblEmail.setBounds(col,gap*5,boxw*4,boxh);
     	    	lblNameofUser.setBounds(col,gap*6,boxw*4,boxh);
     	    	lblCurrentBedtime.setBounds(col,gap*7,boxw*4,boxh);
     	    	lblCurrentWakeuptime.setBounds(col,gap*8,boxw*4,boxh);
     	    	lblChangeBedtime.setBounds(col,gap*9,boxw*2,boxh);
     	    	lblChangeWakup.setBounds(col,gap*10,boxw*2,boxh);
            	lblusernamedd.setBounds(col+boxw*2,gap*4,boxw*4,boxh);
            	lblnamedd.setBounds(col+boxw*2,gap*6,boxw*4,boxh);
            	lblemaildd.setBounds(col+boxw*2,gap*5,boxw*4,boxh);
            	lblbeddd.setBounds(col+boxw*2,gap*7,boxw*4,boxh);
            	lblwakedd.setBounds(col+boxw*2,gap*8,boxw*4,boxh);


            	lblusernamedd.setForeground(Color.WHITE);
            	lblnamedd.setForeground(Color.WHITE);
            	lblemaildd.setForeground(Color.WHITE);
            	lblbeddd.setForeground(Color.WHITE);
           		lblwakedd.setForeground(Color.WHITE);

     	    	btnChangeAccount.setBounds(col,frmheight-(int)(gap*2.5),(int)(frmwidth/6), (int)(height/24.6));
     	    	btnCancel.setBounds((int)5*col,frmheight-(int)(gap*2.5),(int)(frmwidth/6), (int)(height/24.6));
     	    	btnLogOff.setBounds((int)3*col,frmheight-(int)(gap*2.5),(int)(frmwidth/6), (int)(height/24.6));

     	    	JComboStarttime.setBounds(col+boxw*2, gap*9, boxw, boxh);
     	    	JComboEndtime.setBounds(col+boxw*2, gap*10, boxw,boxh);

     	    	lblUsername.setFont(new Font("Arial", Font.PLAIN, 10));
     	    	lblEmail.setFont(new Font("Arial", Font.PLAIN, 10));
     	    	lblNameofUser.setFont(new Font("Arial", Font.PLAIN, 10));
     	    	lblCurrentBedtime.setFont(new Font("Arial", Font.PLAIN, 10));
     	    	lblCurrentWakeuptime.setFont(new Font("Arial", Font.PLAIN, 10));
          		lblChangeBedtime.setFont(new Font("Arial", Font.PLAIN, 10));
     	    	lblChangeWakup.setFont(new Font("Arial", Font.PLAIN, 10));
     		
     	    	lblLogo.setBounds(frmwidth/8,frmheight/30,imgwidth,imgheight);
  		  	
  		  		frmAccount.setVisible(true);
  		  		btnChangeAccount.addActionListener(new ActionListener() { 
  		  			public void actionPerformed(ActionEvent e) { 
  		  				String recievedbedtime = (String) JComboEndtime.getSelectedItem();
              			String recievedwaketime = (String) JComboStarttime.getSelectedItem();
              			String ww = c.formatBedTime(recievedbedtime);
              			String ll = c.formatBedTime(recievedwaketime);
              			System.out.println("waketime " +ll);
              			c.updateProfile("BEDTIME",ll);
              			c.updateProfile("WAKETIME",ww);
              			frmAccount.setVisible(false);
  		  				frmMain.setVisible(true);
  		  			}
  		  		});
  		  		btnCancel.addActionListener(new ActionListener() { 
  		  			public void actionPerformed(ActionEvent e) {
  		  				frmAccount.setVisible(false);
  		  				frmMain.setVisible(true);
   	    	  		}
   	    	  	});
   	    	  	btnLogOff.addActionListener(new ActionListener() { 
  		  			public void actionPerformed(ActionEvent e) {
  		  				frmAccount.setVisible(false);
  		  				frmMain.setVisible(true);
   	    	  		}
   	    	  	});
    		}
    	});
  

  //edit event
    	btnRefresh.addActionListener(new ActionListener(){
      		public void actionPerformed(ActionEvent e){
        
        		clearCalendar();
        		calendarList = c.schedule();
        		LinkedList<CalendarEvent> calWeek = splitCalendar(calendarList);
        		DisplayCalendarEvents(calWeek);
      
    		}
    	});

    	btnEditEvent.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    	  		frmEditEvent = new JFrame ("Edit Event"); //Create frame
   		  		int frmwidth = width/((int)(width/400));
   		  		int frmheight = (int)(height/2);
   		  		frmEditEvent.setSize(frmwidth,frmheight);//Set screen to full
   		  		frmEditEvent.setLocation((width/4)+(width/10), height/8);
        		createComboBoxes();
      			JComboBox<String>JComboPickEvent = new JComboBox<>(Events2);

   		  		pane1 = frmEditEvent.getContentPane(); //Get content pane
   		  		pane1.setLayout(null); //Apply null layout
   		  		frmEditEvent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   		  		pane1.add(lblPickEvent);
   		  		pane1.add(lblStarttime);
   		  		pane1.add(lblEndtime);
   		  		pane1.add(lblLocation);
   		  		pane1.add(lblRepeatedDays);
   		  		pane1.add(lblOther);
   		  		pane1.add(JTextLocation);
   		  		pane1.add(JTextDays);
   		  		pane1.add(btnChangeEvent);
   		  		pane1.add(btnCancel);
        		pane1.add(btnDeleteEvent);
   		  		pane1.add(JComboStarttime);
   		  		pane1.add(JComboEndtime);
   		  		pane1.add(JComboPickEvent);
   		  		pane1.add(JComboStartDate);
   		  		pane1.add(JComboEndDate);
   		  		int col = (int)(frmwidth/7);
   		  		int boxw = (int)(width/13.6);
   		  		int gap = (int) (height/18.425);
   		  		int boxh = (int)(height/36.85);
   		  		lblPickEvent.setBounds(col,gap,boxw,boxh);
   		  		lblStarttime.setBounds(col,gap*3,boxw,boxh);
   		  		lblEndtime.setBounds(col,gap*4,boxw,boxh);
   		  		lblLocation.setBounds(col, gap*5, boxw, boxh);
   		  		lblRepeatedDays.setBounds(col, gap*2, boxw, boxh);
   		  		lblOther.setBounds(col,(int)(gap*2.4),(int)(boxw*1.5),boxh);
        		btnChangeEvent.setFont(new Font("Arial", Font.PLAIN,10));
   		  		JComboPickEvent.setBounds(3*col,gap,boxw,boxh);
   		  		JTextDays.setBounds(3*col,gap*2,boxw,boxh);
   		  		JTextLocation.setBounds(3*col, gap*5, boxw, boxh);
   		  		btnChangeEvent.setBounds(col,frmheight-(int)(gap*2.5),(int)(frmwidth/6), (int)(height/24.6));
   	    		btnCancel.setBounds(5*col,frmheight-(int)(gap*2.5),(int)(frmwidth/6), (int)(height/24.6));         
        		btnDeleteEvent.setBounds((int)(3*col),frmheight-(int)(gap*2.5),(int)(frmwidth/6), (int)(height/24.6));

   	    		JComboStarttime.setBounds(3*col,gap*3,boxw,boxh);
   	    		JComboEndtime.setBounds(3*col,gap*4,boxw,boxh);
   	    		JComboStartDate.setBounds(5*col,gap*3,boxw,boxh);
   	    		JComboEndDate.setBounds(5*col,gap*4,boxw,boxh);


   	    		frmEditEvent.setVisible(true);
   		  		pane1.setBackground(Color.gray);
   		  		wait = false;
   		  		//while(wait == false){
   	    		btnChangeEvent.addActionListener(new ActionListener() { 
   	    			public void actionPerformed(ActionEvent e) { 
   	        			String pickedEvent = (String) JComboPickEvent.getSelectedItem();
            			String repeatedDays = JTextDays.getText();
            			String startHour = (String) JComboStarttime.getSelectedItem();
            			String endHour = (String) JComboEndtime.getSelectedItem();
            			String startDay = (String) JComboStartDate.getSelectedItem();
            			String endDay = (String) JComboEndDate.getSelectedItem();
            			String recLocation = JTextLocation.getText();
            			c.updateEvent(pickedEvent,"DAYS", repeatedDays);
            			c.updateEventDate(pickedEvent,"START_TIME", startDay, startHour);
            			c.updateEventDate(pickedEvent ,"END_TIME", endDay, endHour);
            			c.updateEvent(pickedEvent,"LOCATION",recLocation);
            			frmEditEvent.setVisible(false);
   	    				frmMain.setVisible(true);
   	    			}
   	    		});
   	    		btnCancel.addActionListener(new ActionListener() { 
   	    			public void actionPerformed(ActionEvent e) {
   	    				frmEditEvent.setVisible(false);
   	    				frmMain.setVisible(true);
   	    			}
   	    		});
   	    		btnDeleteEvent.addActionListener(new ActionListener(){
   	    			public void actionPerformed(ActionEvent e){
   	    				String pickedEvent = (String)JComboPickEvent.getSelectedItem();
   	    				c.deleteEvent(pickedEvent);
   	    				frmEditEvent.setVisible(false);
   	    				frmMain.setVisible(true);
     				}
     			});
    		}
    	});

    	btnAddAssignment.addActionListener(new ActionListener() { 
    	  	public void actionPerformed(ActionEvent e) { 
    		  	frmAddAssignment = new JFrame ("Add Assignment"); //Create frame
    		  	int frmwidth = width/((int)(width/400));
    		  	int frmheight = (int)(height/2);
    		  	frmAddAssignment.setSize(frmwidth,frmheight);//Set screen to full
    		  	frmAddAssignment.setLocation((width/4)+(width/10), height/8);
    		  	pane1 = frmAddAssignment.getContentPane(); //Get content pane
    		  	pane1.setLayout(null); //Apply null layout
    		  	frmAddAssignment.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		  	pane1.add(lblName);
    		  	pane1.add(lblClass);
    		  	pane1.add(lblPriority);
    		  	pane1.add(lblDueDate);
    		  	pane1.add(lblEstimatedTime);
    		  	pane1.add(JTextName);
    		  	pane1.add(JTextClass);
    		  	pane1.add(btnCreateAssignment);
    		  	pane1.add(btnCancel);
    		  	pane1.add(JComboDueDate);
    		  	pane1.add(JComboTimetoComplete);
    		  	pane1.add(JComboPriority);
    		  	pane1.add(JComboDueHour);
    		  	//pane1.add(lblPickAssignment);
    		  	int col = (int)(frmwidth/7);
    		  	int boxw = (int)(width/13.6);
    		  	int boxh = (int)(height/36.85);
       			int gap = (int) (height/18.425);
    		  	lblName.setBounds(col,gap,boxw,boxh);
     		  	lblClass.setBounds(col, gap*2, boxw, boxh);
    		  	lblPriority.setBounds(col,gap*3, boxw, boxh);
    		  	lblDueDate.setBounds(col,gap*4, boxw, boxh);
    		  	lblEstimatedTime.setBounds(col,gap*5,(int)( boxw*1.25), boxh);
    		  	JTextName.setBounds(3*col,gap,boxw,boxh);
    		  	JTextClass.setBounds(3*col,gap*2,boxw,boxh);
    		  	btnCreateAssignment.setBounds(col,frmheight-(int)(gap*2.5),(int)(width/11.38), (int)(height/24.6));
    	     	btnCancel.setBounds(4*col,frmheight-(int)(gap*2.5),(int)(width/11.38), (int)(height/24.6));
    	      	JComboDueDate.setBounds(5*col,gap*4,boxw,boxh);
    	      	JComboDueHour.setBounds(3*col,gap*4,boxw,boxh);
    	      	JComboPriority.setBounds(3*col,gap*3,boxw,boxh);
    	      	JComboTimetoComplete.setBounds(3*col,gap*5,boxw,boxh);
    	      
    	      	frmAddAssignment.setVisible(true);
    		  	pane1.setBackground(Color.gray);
    	      	btnCreateAssignment.addActionListener(new ActionListener() { 
    	    	  	public void actionPerformed(ActionEvent e) { 
    	    		  	String name1 = JTextName.getText();
    	    		  	String classname =  JTextClass.getText();
    	    		  	String recDueHour = (String) JComboDueHour.getSelectedItem();
                		String recDuedate = (String) JComboDueDate.getSelectedItem();
    	    	    	String priority1 = (String) JComboPriority.getSelectedItem();
    	    	    	String timeto =  (String) JComboTimetoComplete.getSelectedItem();
                		c.addAssignment(name1,classname,recDuedate,recDueHour,timeto, priority1,"");

    	    		  	frmAddAssignment.setVisible(false);
    	    		  	frmMain.setVisible(true);
    	    	  	}
    	   		});
    	      	btnCancel.addActionListener(new ActionListener() { 
    	    	  	public void actionPerformed(ActionEvent e) {
    	    		  	frmAddAssignment.setVisible(false);
    	    		  	frmMain.setVisible(true);
    	    	  	}
    	   		});
   			}
   		});
 
    	btnEditAssignment.addActionListener(new ActionListener(){ 
    	  	public void actionPerformed(ActionEvent e) { 
    	  		frmEditAssignment = new JFrame ("Edit Assignment"); //Create frame
    	   		int frmwidth = width/((int)(width/400));
    	    	int frmheight = (int)(height/2);
    	    	frmEditAssignment.setSize(frmwidth,frmheight);//Set screen to full
    	    	frmEditAssignment.setLocation((width/4)+(width/10), height/8);
    	    	pane1 = frmEditAssignment.getContentPane(); //Get content pane
    	    	pane1.setLayout(null); //Apply null layout
                createComboBoxes();
      			JComboBox<String>JComboPickAssignment = new JComboBox<>(Assignments2);

    	    	frmEditAssignment.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	    	pane1.add(lblClass);
    	    	pane1.add(lblPriority);
    	    	pane1.add(lblDueDate);
    	    	pane1.add(lblEstimatedTime);
    	    	pane1.add(JTextClass);
    	    	pane1.add(btnDeleteAssignment);
    	    	pane1.add(btnCancel);
    	    	pane1.add(btnChangeAssignment);
    	    	pane1.add(lblPickAssignment);
    	    	pane1.add(JComboDueDate);
    	    	pane1.add(JComboTimetoComplete);
    	    	pane1.add(JComboPriority);
    	    	pane1.add(JComboPickAssignment);
    	    	pane1.add(JComboDueHour);
    	    	int col = (int)(frmwidth/7);
    	    	int boxw = (int)(width/13.6);
    	    	System.out.println(boxw);
    	    	int boxh = (int)(height/36.85);
    	    	int gap = (int) (height/18.425);
    	    	lblPickAssignment.setBounds(col,gap,boxw,boxh);
    	    	lblClass.setBounds(col,gap*2, boxw, boxh);
    	    	lblPriority.setBounds(col,gap*3, boxw, boxh);
    	    	lblDueDate.setBounds(col,gap*4, boxw, boxh);
    	    	lblEstimatedTime.setBounds(col,gap*5 ,(int)( boxw*1.25), boxh);
    	    	JTextClass.setBounds(3*col,gap*2,boxw,boxh);   
                btnChangeAssignment.setFont(new Font("Arial",Font.PLAIN,10));
                btnChangeAssignment.setBounds(col,frmheight-(int)(gap*2.5),(int)(frmwidth/6), (int)(height/24.6));
                btnCancel.setBounds(5*col,frmheight-(int)(gap*2.5),(int)(frmwidth/6), (int)(height/24.6));         
                btnDeleteAssignment.setBounds((int)(3*col),frmheight-(int)(gap*2.5),(int)(frmwidth/6), (int)(height/24.6));
    	    	JComboDueDate.setBounds(5*col,gap*4,boxw,boxh);
    	    	JComboDueHour.setBounds(3*col,gap*4,boxw,boxh);
    	    	JComboPriority.setBounds(3*col,gap*3,boxw,boxh);
    	    	JComboTimetoComplete.setBounds(3*col,gap*5,boxw,boxh);
    	       	JComboPickAssignment.setBounds(3*col,gap*1,boxw,boxh);
    	   	    frmEditAssignment.setVisible(true);
    	    	pane1.setBackground(Color.gray);
    	    	wait = false;
    	    	//while(wait == false){
    	    	btnChangeAssignment.addActionListener(new ActionListener() { 
    	   			public void actionPerformed(ActionEvent e) { 
    	    	   		String pickedassign = (String)JComboPickAssignment.getSelectedItem();
    	    	    	String classname =  JTextClass.getText();
    	    	   		String duedate =  (String) JComboDueDate.getSelectedItem();
    	    	 		String dueTime = (String) JComboDueHour.getSelectedItem();
                      	String priority1 = (String) JComboPriority.getSelectedItem();
    	    	 		String timeto = (String) JComboTimetoComplete.getSelectedItem();
    	    	    	c.updateAssignment(pickedassign,"CLASSNAME", classname);
                      	c.updateAssignmentDate(pickedassign, duedate, dueTime);
                     	c.updateAssignment(pickedassign, "PRIORITY",priority1);
                      	c.updateAssignment(pickedassign,"HOURSTOCOMPLETION",timeto);
    	    	 		frmEditAssignment.setVisible(false);
    	    	    	frmMain.setVisible(true);
    	    	    }
    	    	});
    	    	btnCancel.addActionListener(new ActionListener() { 
    	    		public void actionPerformed(ActionEvent e) {
    	    	 		frmEditAssignment.setVisible(false);
    	    	    	frmMain.setVisible(true);
    	    	    }
    	    	});
    	 		btnDeleteAssignment.addActionListener(new ActionListener(){
    	    	 	public void actionPerformed(ActionEvent e){
    	    	  		String pickedassign = (String)JComboPickAssignment.getSelectedItem();
    	    	   		c.deleteAssignment(pickedassign);
    	    	    	frmEditAssignment.setVisible(false);
    	    	    	frmMain.setVisible(true);
    	    	    }
    	    	});
    	    }
    	});
    	
    	btnAddEvent.addActionListener(new ActionListener(){
	   		public void actionPerformed(ActionEvent e){
		  		frmAddEvent = new JFrame ("Add Event"); //Create frame
 		  		int frmwidth = width/((int)(width/400));
 		  		int frmheight = (int)(height/2);
 		  		frmAddEvent.setSize(frmwidth,frmheight);//Set screen to full
 		  		frmAddEvent.setLocation((width/4)+(width/10), height/8);
 		  		pane1 = frmAddEvent.getContentPane(); //Get content pane
 		  		pane1.setLayout(null); //Apply null layout
 		  		frmAddEvent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 		  		pane1.add(lblName);
 		  		pane1.add(lblStarttime);
 		  		pane1.add(lblEndtime);
 		  		pane1.add(lblLocation);
 		  		pane1.add(lblRepeatedDays);
 		  		pane1.add(lblOther);
 		  		pane1.add(JTextName);
 		  		//pane1.add(JTextClass);
 		  		pane1.add(JTextLocation);
 		  		pane1.add(JTextDays);
 		  		pane1.add(btnCreateEvent);
 		  		pane1.add(btnCancel);
 		  		pane1.add(JComboStarttime);
 		  		pane1.add(JComboEndtime);
 		  		pane1.add(JComboStartDate);
 		  		pane1.add(JComboEndDate);
 		  		int col = (int)(frmwidth/7);
 		  		int boxw = (int)(width/13.6);
 		  		int gap = (int) (height/18.425);
 		  		int boxh = (int)(height/36.85);
 		  		lblName.setBounds(col,gap,boxw,boxh);
 		  		lblStarttime.setBounds(col,gap*3,boxw,boxh);
 		  		lblEndtime.setBounds(col,gap*4,boxw,boxh);
 		  		lblLocation.setBounds(col, gap*5, boxw, boxh);
 		  		lblRepeatedDays.setBounds(col, gap*2, boxw, boxh);
 		  		lblOther.setBounds(col,(int)(gap*2.4),(int)(boxw*1.5),boxh);
 		  		JTextName.setBounds(3*col,gap,boxw,boxh);
 		  		JTextDays.setBounds(3*col,gap*2,boxw,boxh);
 		  		JTextLocation.setBounds(3*col, gap*5, boxw, boxh);
 		  		btnCreateEvent.setBounds(col,frmheight-(int)(gap*2.5),(int)(width/11.38), (int)(height/24.6));
 	      		btnCancel.setBounds(4*col,frmheight-(int)(gap*2.5),(int)(width/11.38), (int)(height/24.6));
 	      		JComboStarttime.setBounds(3*col,gap*3,boxw,boxh);
 	      		JComboEndtime.setBounds(3*col,gap*4,boxw,boxh);
 	      		JComboStartDate.setBounds(5*col,gap*3,boxw,boxh);
  	      		JComboEndDate.setBounds(5*col,gap*4,boxw,boxh);
  	      
 	        	frmAddEvent.setVisible(true);
 		      	pane1.setBackground(Color.gray);
 	        	btnCreateEvent.addActionListener(new ActionListener() { 
 	    	  		public void actionPerformed(ActionEvent e) { 
 	    		  		String recName = JTextName.getText();
            			String recDays = JTextDays.getText();
            			String location2 = JTextLocation.getText();
            			String startHour = (String)JComboStarttime.getSelectedItem();
            			String endHour = (String) JComboEndtime.getSelectedItem();
            			String recstartDate = (String)JComboStartDate.getSelectedItem();
            			String recendDate = (String)JComboEndDate.getSelectedItem();
            
            			c.addEvent(recName,recDays,recstartDate,startHour, recstartDate, endHour, location2);
 	    		  		frmAddEvent.setVisible(false);
 	    		  		frmMain.setVisible(true);
 	    	  		}
 	    	  	});
 	      		btnCancel.addActionListener(new ActionListener() { 
 	    	  		public void actionPerformed(ActionEvent e) {
 	    		  		frmAddEvent.setVisible(false);
 	    		  		frmMain.setVisible(true);
 	    	  		}
 	    	  	});
	   		}
	   	});

    	btnAssignmentList.addActionListener(new ActionListener(){
      		public void actionPerformed(ActionEvent e){
        		frmAssignmentList = new JFrame ("Assignments"); //Create frame
        		int frmwidth = (int)(width/1.2);
        		int frmheight = (int)(height/2);
        		frmAssignmentList.setSize(frmwidth,frmheight);//Set screen to full
        		frmAssignmentList.setLocation((int)((width/frmwidth)/2), height/8);
        		pane1 = frmAssignmentList.getContentPane(); //Get content pane
        		pane1.setLayout(null); //Apply null layout
        		pane1.add(btnClose);
        		frmAssignmentList.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        		btnClose.setBounds(frmwidth/2-(int)(width/22.76),frmheight-((int)(frmheight/6)),(int)(width/11.38), (int)(height/24.6));
        		String name3, classname3, timeto3,priority3;
        		Date dueDate3;

        		LinkedList<Assignment> assignList = null;
            	try{
            		assignList = c.getAssignmentList();
            		int size2 = assignList.size();
            		JLabel[] lblAssignments= new JLabel[size2];
            		for(int ii = 0; ii<size2;ii++){
                  		//lblAssignments[ii] = new JLabel(assignList.get(ii).toString());
                  		Assignment b = assignList.get(ii);
                  		lblAssignments[ii] = new JLabel(b.getAssignName()+" , Class:"+b.getClassName()+" , Due:"+ b.getDueDate()+ " , Hours Left:"+ b.getCompletionTime()+" , Priority:"+b.getPriority());
                  		pane1.add(lblAssignments[ii]);
                  		lblAssignments[ii].setBounds(frmwidth/12,(int)((ii+1)*frmheight/12),(int)(frmwidth), (int)(frmheight/12));
                  		lblAssignments[ii].setFont(new Font("Arial",Font.PLAIN,12));
                  		lblAssignments[ii].setForeground(Color.WHITE);
                	}
            	} catch (Exception e1){}
        
        		pane1.setBackground(Color.black);

        		frmAssignmentList.setVisible(true);

        		btnClose.addActionListener(new ActionListener(){
              		public void actionPerformed(ActionEvent e){
                		frmAssignmentList.setVisible(false);
                		frmMain.setVisible(true);
              		}
              	});
      		}
      	});


    	btnEventList.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			frmEventList = new JFrame ("Events"); //Create frame
            	int frmwidth = (int)(width/1.2);
            	int frmheight = (int)(height/2);
            	frmEventList.setSize(frmwidth,frmheight);//Set screen to full
            	frmEventList.setLocation((int)((width/frmwidth)/2), height/8);
            
            	pane1 = frmEventList.getContentPane(); //Get content pane
            	pane1.setLayout(null); //Apply null layout
            	pane1.add(btnClose);
            	frmEventList.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            	pane1.setBackground(Color.BLACK);
            	int intercept = frmwidth/10;
            	int gap2 = frmwidth/15;
            	String name3, days3, timeto3,location3;
            	Date start3,end3;
            	btnClose.setBounds(frmwidth/2-(int)(width/22.76),frmheight-((int)(frmheight/6)),(int)(width/11.38), (int)(height/24.6));
                       
            	LinkedList<Event> eventList = null;
            	try{
            		eventList = c.getEventList();
            		int size2 = eventList.size();
            		JLabel[] lblEvents = new JLabel[size2];
            		for(int ii = 0; ii<size2;ii++){
                  		//lblEvents[ii] = new JLabel(eventList.get(ii).toString());
                  		Event a = eventList.get(ii);
                  		lblEvents[ii] = new JLabel(a.getEventName()+" , Repeated Days:"+a.getDays()+" , Starttime:"+a.getStart()+ " , Endtime:"+a.getEnd()+" , Location:"+a.getLocation());
                  		pane1.add(lblEvents[ii]);
                  		lblEvents[ii].setBounds(frmwidth/12,(int)((ii+1)*frmheight/12),(int)(frmwidth), (int)(frmheight/12));
                  		lblEvents[ii].setForeground(Color.WHITE);
                  		lblEvents[ii].setFont(new Font("Arial",Font.PLAIN,12));

              		}
            	} catch (Exception e1){}
            

            	frmEventList.setVisible(true);
      
            	btnClose.addActionListener(new ActionListener(){
                  	public void actionPerformed(ActionEvent e){
            			frmEventList.setVisible(false);
            			frmMain.setVisible(true);
          			}
          		});
    		}
    	});
    
    
	}
  //LABEL DECLARATION
    public void declareLabels(){

        lblLogo = new JLabel("Label");
        lblName = new JLabel("Name");
        lblStarttime = new JLabel("Start Time/Date");
        lblEndtime = new JLabel("End Hour/Time");
        lblLocation = new JLabel("Location");
        lblClass = new JLabel("Class");
        lblPriority = new JLabel("Priority");
        lblDueDate = new JLabel("Due Time/Date");
        lblEstimatedTime = new JLabel("Estimated Time(Hours)");
        lblPickAssignment = new JLabel("Assignment");
        lblRepeatedDays = new JLabel("Repeated Days");
        lblOther = new JLabel("(One-Time Leave Blank)");
        lblPickEvent = new JLabel("Pick the Event");
        lblUsername = new JLabel("Username");
        lblEmail = new JLabel("Email");
        lblNameofUser = new JLabel("Name");
        lblCurrentBedtime = new JLabel("BedTime");
        lblCurrentWakeuptime = new JLabel("Wake Up Time:");
        lblChangeBedtime = new JLabel("Change Bedtime:");
        lblChangeWakup = new JLabel("Change Wake Up Time:");

        lblName.setFont(new Font("Arial", Font.PLAIN,9));
        lblStarttime.setFont(new Font("Arial", Font.PLAIN,9));
        lblEndtime.setFont(new Font("Arial", Font.PLAIN,9));
        lblLocation.setFont(new Font("Arial", Font.PLAIN,9));
        lblClass.setFont(new Font("Arial", Font.PLAIN,9));
        lblPriority.setFont(new Font("Arial", Font.PLAIN,9));
        lblDueDate.setFont(new Font("Arial", Font.PLAIN,9));
        lblEstimatedTime.setFont(new Font("Arial", Font.PLAIN,9));
        lblPickAssignment.setFont(new Font("Arial", Font.PLAIN,9));
        lblRepeatedDays.setFont(new Font("Arial", Font.PLAIN,9));
        lblOther.setFont(new Font("Arial", Font.PLAIN,9));
        lblPickEvent.setFont(new Font("Arial", Font.PLAIN,9));
        lblUsername.setFont(new Font("Arial", Font.PLAIN,9));
        lblEmail.setFont(new Font("Arial", Font.PLAIN,9));
        lblNameofUser.setFont((new Font("Arial", Font.PLAIN,9)));
        lblCurrentBedtime.setFont(new Font("Arial", Font.PLAIN,9));
        lblCurrentWakeuptime.setFont(new Font("Arial", Font.PLAIN,9));
        lblChangeBedtime.setFont(new Font("Arial", Font.PLAIN,9));
        lblChangeWakup.setFont(new Font("Arial", Font.PLAIN,9));
    	
    }
	    
    //COLOR LABELS
    public void colorLabels(){
        lblPickEvent.setForeground(Color.WHITE);
        lblClass.setForeground(Color.WHITE);
        lblPriority.setForeground(Color.WHITE);
        lblDueDate.setForeground(Color.WHITE);
        lblLogo.setForeground(Color.WHITE);
        lblName.setForeground(Color.WHITE);
        lblStarttime.setForeground(Color.WHITE);
        lblEndtime.setForeground(Color.WHITE);
        lblLocation.setForeground(Color.WHITE);
        lblEstimatedTime.setForeground(Color.WHITE);
        lblPickAssignment.setForeground(Color.WHITE);
        lblRepeatedDays.setForeground(Color.WHITE);
        lblOther.setForeground(Color.WHITE);
        lblUsername.setForeground(Color.WHITE);
        lblEmail.setForeground(Color.WHITE);
        lblNameofUser.setForeground(Color.WHITE);
        lblCurrentBedtime.setForeground(Color.WHITE);
        lblCurrentWakeuptime.setForeground(Color.WHITE);
        lblChangeBedtime.setForeground(Color.WHITE);
        lblChangeWakup.setForeground(Color.WHITE);
    }
    
	//BUTTON DECLARATION
    public void declareButtons(){
       	btnNextWeek = new JButton ("Next Week");
        btnNextWeek.setFont(new Font("Arial", Font.PLAIN, 10));
        
        btnPrevWeek = new JButton ("Prev Week");
        btnPrevWeek.setFont(new Font("Arial", Font.PLAIN, 10));
        
        btnAddAssignment = new JButton ("Add Assignment");
        btnAddAssignment.setFont(new Font("Arial", Font.PLAIN, 10));
        
        btnAddEvent = new JButton ("Add Event");
        btnAddEvent.setFont(new Font("Arial", Font.PLAIN, 10));
        
        btnEditAssignment = new JButton ("Edit Assignment");
        btnEditAssignment.setFont(new Font("Arial", Font.PLAIN, 10));
        
        btnEditEvent = new JButton ("Edit Event");
        btnEditEvent.setFont(new Font("Arial", Font.PLAIN, 10));
        
        btnRefresh = new JButton("Refresh Calendar");
        btnRefresh.setFont(new Font("Arial", Font.PLAIN, 10));
        
        btnAccount = new JButton("Account");
        btnAccount.setFont(new Font("Arial", Font.PLAIN, 10));
        
        btnCreateAssignment = new JButton("Create");
        btnCreateAssignment.setFont(new Font("Arial", Font.PLAIN, 10));
        
        btnCreateEvent = new JButton("Create"); 
        btnCreateEvent.setFont(new Font("Arial", Font.PLAIN, 10));
        
        btnCancel = new JButton("Cancel");
        btnCancel.setFont(new Font("Arial", Font.PLAIN, 10));

        btnLogOff = new JButton("Log Off");
        btnLogOff.setFont(new Font("Arial", Font.PLAIN, 10));
        
        btnChangeAccount = new JButton("Change");
        btnChangeAccount.setFont(new Font("Arial",Font.PLAIN,10));

        btnChangeAssignment = new JButton("Change");
        btnChangeAssignment.setFont(new Font("Arial",Font.PLAIN,10));

        btnChangeEvent = new JButton("Change");
        btnChangeEvent.setFont(new Font("Arial",Font.PLAIN,10));
        
        btnAssignmentList = new JButton("Assignments");
        btnAssignmentList.setFont(new Font("Arial", Font.PLAIN, 10));
        
        btnEventList = new JButton("Events");
        btnEventList.setFont(new Font("Arial", Font.PLAIN, 10));
        
        btnDeleteEvent = new JButton("Delete");
        btnDeleteEvent.setFont(new Font("Arial",Font.PLAIN,10));

        btnDeleteAssignment = new JButton("Delete");
        btnDeleteAssignment.setFont(new Font("Arial",Font.PLAIN,10));
        
        btnClose = new JButton("Close");
        btnClose.setFont(new Font("Arial",Font.PLAIN,10));
    }
    
	   //FRAME SETUP
    public void setupFrame(){
    	frmMain = new JFrame ("UPlan"); //Create frame
    	frmMain.setExtendedState(JFrame.MAXIMIZED_BOTH);//Set screen to full
        pane = frmMain.getContentPane(); //Get content pane
   	    pane.setLayout(null); //Apply null layout
 	    frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 	    frmAddAssignment = new JFrame("Add Assignment");
        frmEditAssignment = new JFrame("Edit Assignment");
        frmAddEvent = new JFrame("Add Event");
        frmEditEvent = new JFrame("Edit Event");
        frmAccount = new JFrame("Account");
    }

    //POSITION CALCULATION
    public int findHorizontialPosition(int day){
		int spot = ((calendarwidth/7)*day);
		return spot-day*((int)(width/683));
	}

    public int findVerticalStart(int hour1,int minute){
    	int start = (int)((calendarheight/17)*hour1);
    	return start;
    }

    public int findVerticalEnd(int hour2,int minute){
    	int end = ((calendarheight/17)*hour2);
    	return end;//+(calendarheight/1020)*1020;
    }
	
    //COMBO BOX CREATION
    public void createComboBoxes(){
        times = new String[34];
	  	int hh = 7;
	    for(int aa = 0;aa<34;aa++){
       		if(aa%2 == 0){
            	if(aa<10){
              		if(aa<6){
                		times[aa] = "0"+hh+":00am";
              		}
              		else{
                		times[aa] = hh+":00am";
              		}
            	}
            	else if(aa == 10){
              		times[aa] = hh+":00pm";
            	}
            	else{
              		if(hh-12<10){
                		times[aa] = "0"+(hh-12)+":00pm";
              		}
              		else{
                		times[aa] = ""+(hh-12)+":00pm";
              		}
            	}
          	}
          	else{
            	if(aa<10){
              		if(aa<6){
                		times[aa] = "0"+hh+":30am";
              		}
              		else{
                		times[aa] = hh+":30am";
              		}
            	}
            	else if(aa == 11){
              		times[aa] = hh+":30pm";
            	}
            	else{
              		if(hh-12<10){
                		times[aa] = "0"+(hh-12)+":30pm";
              		}
              		else{
                		times[aa] = ""+(hh-12)+":30pm";
              		}
            	}
            	hh++;
          	}       
	    }
	      
	    dates = new String[31];
	    for(int bb = 1; bb<32;bb++){
	    	dates[bb-1] = "12/"+bb+"/16";
	    }
        timeneeded = new String[20];
        for(int cc = 1;cc<21;cc++){
        	timeneeded[cc-1] = Integer.toString(cc);
        }
        priority = new String[3];
        for(int dd = 1;dd<4;dd++){
        	priority[dd-1] = Integer.toString(dd);
        }
        
        LinkedList<Assignment> temp1 = c.getAssignmentList();
        Assignments2 = new String[temp1.size()];
        for(int yy = 0; yy<temp1.size();yy++){
        	Assignments2[yy] = temp1.get(yy).getAssignName();
          	System.out.println(Assignments2[yy]);
        }
        
        //NEED EVENTS
        LinkedList<Event> temp2 = c.getEventList();
        Events2 = new String[temp2.size()];
        for(int yy = 0; yy<temp2.size();yy++){
          	Events2[yy] = temp2.get(yy).getEventName();
          	System.out.println(Events2[yy]);
        }
        
    }

  	public LinkedList<CalendarEvent> splitCalendar(LinkedList<CalendarEvent> calList){
      	Calendar curCal2 = Calendar.getInstance();
      	int curWeek = curCal2.get(Calendar.WEEK_OF_YEAR);
      	Calendar newCal = Calendar.getInstance();
      	newCal.set(Calendar.WEEK_OF_YEAR, curWeek + panelOffset);
      	int newWeek = newCal.get(Calendar.WEEK_OF_YEAR);

      	LinkedList<CalendarEvent> tempCal = new LinkedList<CalendarEvent>();
      	ListIterator iter = calList.listIterator();

      	while(iter.hasNext()){
        	CalendarEvent tempeve = calList.get(iter.nextIndex());
        	Calendar temp = dateToCalendar(tempeve.getStartTime());
        	int tempweek = temp.get(Calendar.WEEK_OF_YEAR);
        	if(tempweek == newWeek){
          		tempCal.add(tempeve);
          		System.out.println(tempeve.toString());
        	}
        	iter.next();
		}


      	return tempCal;
	}

    //DISPLAY ASSIGNMENTS ON CALENDAR	
	@SuppressWarnings("deprecation")
	public void DisplayCalendarEvents(LinkedList<CalendarEvent> calList){
		try {
      		size33 = calList.size();
      		list1 = new JButton[size33];
      		int date1,dayof,starthour,endhour,startmin,endmin, spot,place1,place2;
        	for(int ii = 0; ii<size33;ii++){
          		CalendarEvent a = calList.get(ii);
          		String name = a.getName();
          		Date starttime = a.getStartTime();
          		Date endtime = a.getEndTime();
          		Calendar startCalTime = dateToCalendar(starttime);
          		Calendar endCalTime = dateToCalendar(endtime);
          		String location = a.getLocation();
          		boolean display = a.getDisplay();
          		if(true){//eventually change to display
            		date1 = starttime.getDate();
            		dayof = starttime.getDay();
            		starthour = starttime.getHours();
            		endhour = endtime.getHours(); 
            		int starthour2 = starthour%12;
            		int endhour2 = endhour%12;
            		if(starthour2== 0){
              			starthour2 = 12;
            		}
            		if(endhour2 == 0){
              			endhour2 = 12;
            		}

            		startmin = starttime.getMinutes();
            		endmin = endtime.getMinutes();
            		list1[ii] = new JButton(name+"   "+starthour2+":"+startmin+"0-"+endhour2+":"+endmin+"0");
            		list1[ii].setFont(new Font("Arial",Font.PLAIN,10));
            		if(a.getLocation().equals("ASSIGNEMNT")){
              			list1[ii].setForeground(Color.BLACK);
             	 		list1[ii].setBackground(back);
            		}
            		else{
              			list1[ii].setForeground(Color.BLACK);
              			list1[ii].setBackground(Color.gray);
            		}
            		pnlCalendar.add(list1[ii],new Integer(2));
            		spot = findHorizontialPosition(dayof);
            		place1 = findVerticalStart(starthour-6, startmin);
            		place2 = findVerticalEnd(endhour-6,endmin);
            		//System.out.println(place1 + "   "+ place2 + "     " + calendarheight+ "   "+starthour+"  "+endhour);
            		list1[ii].setBounds((spot),place1,calendarwidth/7-(int)(width/136.6),place2-place1);
          		}
          	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  	}

  	public void clearCalendar(){
    	for(int ff = 0;ff<size33;ff++){
      		list1[ff].setVisible(false);
    	}
  	}
  
  	private Calendar dateToCalendar(Date date){
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
    	df.setCalendar(cal);
    	df.setTimeZone(TimeZone.getTimeZone("EST"));
    	cal = df.getCalendar();
    	return cal;
  	}    
  
  	public void createHeaders(){//FILLING OF HEADER ARRAY FOR CALENDAR
          //All headers
        Calendar calCal = Calendar.getInstance();
        int weekOfYear = calCal.get(Calendar.WEEK_OF_YEAR) + panelOffset;
        String[] days = c.displayWeekTimes(weekOfYear);
        
        for (int i = 0; i<7; i++){
        	mtblCalendar.addColumn(days[i]);  
        }
    }

    public void removeHeaders(){//FILLING OF HEADER ARRAY FOR CALENDAR
          //All headers
          mtblCalendar.setColumnCount(0);   
    }
}

