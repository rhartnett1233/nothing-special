import java.sql.*;
import java.util.*;
import java.text.*;

public class DataBase{
    
    private Statement stmt = null;
    private PreparedStatement pstmt = null;
    private String sql = null;
    private ResultSet rs = null;
    private Connection c = null;
    private DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
    private int assignId = 0;
    private int calEventId = 0;
    

    public DataBase(){
         
    }
    
    
    public int createUser(String username, String name, String email, String password, String bedTime, String wakeTime) throws SQLException {
        int valid = 1;
        try {
            
            int a = createTable(username,"ASSIGNMENT");
            int b = createTable(username,"EVENT"); 
            int c = createTable(username,"SCHEDULE");

            if(a == 0 || b == 0 || c == 0)
                valid = 0;
   
        } catch (Exception e){
            System.err.println( "DatabaseCreateUser:" + e.getClass().getName() + ": " + e.getMessage() );
            valid = 0;
        }

        try{
            c = connect();
            sql = "INSERT INTO PROFILE VALUES(?,?,?,?,?,?)";
            
            pstmt = c.prepareStatement(sql);
            
            pstmt.setString(1,username);
            pstmt.setString(2,name);
            pstmt.setString(3,email);
            pstmt.setString(4,password);
            pstmt.setString(5,bedTime);
            pstmt.setString(6,wakeTime);
            pstmt.executeUpdate();
            pstmt.close();

        }catch (Exception e){
            System.err.println( "DatabaseCreateProfile:" + e.getClass().getName() + ": " + e.getMessage() );
            valid = 0;
        }

        c.close();
        return valid;
    }
    
    
    public int createTable(String username, String type) throws SQLException {
        int valid = 1;
        try{
            c = connect();
            stmt = c.createStatement();
            if(type.equals("EVENT")){
                sql = "CREATE TABLE " + username + "EVENT(" + 
                "EVENTNAME TEXT PRIMARY KEY NOT NULL," +
                "DAYS TEXT NOT NULL," +
                "START_TIME TEXT NOT NULL," + 
                "END_TIME TEXT NOT NULL," + 
                "LOCATION TEXT NOT NULL);";
            }
            if(type.equals("ASSIGNMENT")){
                sql = "CREATE TABLE " + username + "ASSIGNMENT(" +
                "ID INT PRIMARY KEY NOT NULL," +
                "ASSIGNMENTNAME TEXT NOT NULL," + 
                "CLASSNAME TEXT NOT NULL," + 
                "DUE TEXT NOT NULL," +
                "HOURSTOCOMPLETION TEXT NOT NULL," +
                "PRIORITY TEXT NOT NULL," +
                "APPPRIORITY TEXT NOT NULL);";
            }
            if(type.equals("SCHEDULE")){
                sql = "CREATE TABLE " + username + "SCHEDULE(" +
                "ID INT PRIMARY KEY NOT NULL," +
                "NAME TEXT NOT NULL," +
                "START_TIME TEXT NOT NULL," +
                "END_TIME TEXT NOT NULL," +
                "LOCATION TEXT NOT NULL," +
                "DISPLAY TEXT NOT NULL);";
            }
            stmt.executeUpdate(sql);
            stmt.close();
            
        } catch ( Exception e ) {
            System.err.println( "DatabaseCreateTable:" + e.getClass().getName() + ": " + e.getMessage() );    
            valid = 0;
        }

        System.out.println(type + " Tables Created Successfully");
        c.close();
        return valid;
    }
    
    
    public int valUser(String username, String password) throws SQLException{
        int valid = 1;
        try{
            c = connect();
            stmt = c.createStatement();
            rs = stmt.executeQuery( "SELECT * FROM PROFILE;" );
            while (rs.next()){
                String curUser = rs.getString("USERNAME");
                String curPass = rs.getString("PASSWORD");
                if(curUser.equals(username) && curPass.equals(password)){
                    rs.close();
                    stmt.close();
                    c.close();
                    return valid;
                }
            } 
        } catch(Exception e){
            System.err.println( "DatabaseValidateUser:" + e.getClass().getName() + ": " + e.getMessage() );
            valid = 0;
        }
        valid = 0;
        rs.close();
        stmt.close();
        c.close();
        return valid;
    }
    

    public int createAssignment(String name, String user, String className, String dueDate, String toCompletion, String priority, String appPriority, int id) throws SQLException {
        int valid = 1;
        try{
            System.out.println("Adding Assignment...");
            c = connect();
            sql = "INSERT INTO " + user + "ASSIGNMENT VALUES(?,?,?,?,?,?,?)";
            pstmt = c.prepareStatement(sql);
            
            pstmt.setInt(1,id);
            pstmt.setString(2,name);
            pstmt.setString(3,className);
            pstmt.setString(4,dueDate);
            pstmt.setString(5,toCompletion);
            pstmt.setString(6,priority);
            pstmt.setString(7,appPriority);
            pstmt.executeUpdate();
            pstmt.close();
            
            System.out.println("Assignment created");

        } catch ( Exception e ) {
            System.err.println( "DatabaseCreateAssignment:" + e.getClass().getName() + ": " + e.getMessage() );
            valid = 0;
        }

        c.close();
        return valid;
    }
    
    
    public int createEvent(String name, String username, String repeatDays, String startTime, String endTime, String loc) throws SQLException {
        int valid = 1;
        try{
            System.out.println("Adding Event...");
            c = connect();

            //String start = Integer.toString(startTime);
            //String end = Integer.toString(endTime);
            sql = "INSERT INTO " + username + "EVENT VALUES(?,?,?,?,?)";
            pstmt = c.prepareStatement(sql);
            
            pstmt.setString(1,name);
            pstmt.setString(2,repeatDays);
            pstmt.setString(3,startTime);
            pstmt.setString(4,endTime);
            pstmt.setString(5,loc);
            pstmt.executeUpdate();
            pstmt.close();
            
            System.out.println("Event created");
        } catch ( Exception e ) {
            System.err.println( "DatabaseCreateEvent:" + e.getClass().getName() + ": " + e.getMessage() );
            valid = 0;
        }

        c.close();
        return valid;

    }
    //*
    public int createCalendarEvent(String username, String name, String startTime, String endTime, String loc, String display, int id) throws SQLException {
        int valid = 1;
        try{
            System.out.println("Adding Calendar Event...");
            c = connect();

            //String start = Integer.toString(startTime);
            //String end = Integer.toString(endTime);
            sql = "INSERT INTO " + username + "SCHEDULE VALUES(?,?,?,?,?,?)";
            pstmt = c.prepareStatement(sql);
            
            pstmt.setInt(1,id);
            pstmt.setString(2,name);
            pstmt.setString(3,startTime);
            pstmt.setString(4,endTime);
            pstmt.setString(5,loc);
            pstmt.setString(6,display);
            pstmt.executeUpdate();
            pstmt.close();
            
            System.out.println("Calendar Event created");
        } catch ( Exception e ) {
            System.err.println( "DatabaseCreateCalendarEvent:" + e.getClass().getName() + ": " + e.getMessage() );
            valid = 0;
        }

        c.close();
        return valid;

    }//*/
   
    public int getAssignId(){
        assignId++;
        return assignId;
    }
    public int getCalEventId(){
        calEventId++;
        return calEventId;
    }
    
    
    public int removeProfile(String username) throws SQLException{
        int valid = 1;
        System.out.println("Deleting " + username + "'s AssignmentTable...");
        sql = "DROP TABLE " + username + "ASSIGNMENT";
        
        try{
            c = connect();
            stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            valid = 0;
        }
        
        System.out.println("Deleting " + username + "'s EventTable...");
        sql = "DROP TABLE " + username + "EVENT";
        
        try{
            if(c == null)
                c = connect();
            stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
 
        } catch (SQLException e) {
            System.out.println("DatabaseRemoveProfile1: " + e.getMessage());
            valid = 0;
        }

        System.out.println("Deleting " + username + "'s ScheduleTable...");
        sql = "DROP TABLE " + username + "SCHEDULE";
        
        try{
            if(c == null)
                c = connect();
            stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
 
        } catch (SQLException e) {
            System.out.println("DatabaseRemoveProfile1: " + e.getMessage());
            valid = 0;
        }
        
        System.out.println("Deleting Profile " + username + "...");
        sql = "DELETE FROM PROFILE WHERE USERNAME = ?";
        
        try{
            if(c == null)
                c = connect();
            pstmt = c.prepareStatement(sql); 
            // set the corresponding param
            pstmt.setString(1, username);
            // execute the delete statement
            pstmt.executeUpdate();
            pstmt.close();
 
        } catch (SQLException e) {
            System.out.println("DatabaseRemoveProfile2: " + e.getMessage());
            valid = 0;
        }

        c.close();
        return valid;
    }
    
    
    public int removeEvent(String name1, String username) throws SQLException{
        int valid = 1;
        try{
            c = connect();
            sql = "DELETE FROM " + username + "EVENT WHERE EVENTNAME = ?";
            pstmt = c.prepareStatement(sql); 
            // set the corresponding param
            pstmt.setString(1, name1);
            // execute the delete statement
            pstmt.executeUpdate();
            pstmt.close();
 
        } catch (SQLException e) {
            System.out.println("DatabaseRemoveEvent: " + e.getMessage());
            valid = 0;
        }

        c.close();
        return valid;
    }
    
    
    public int removeAssignment(String name1, String username) throws SQLException{
        int valid = 1;
        try{
            c = connect();
            stmt = c.createStatement();
            
            rs = stmt.executeQuery( "SELECT * FROM " + username + "ASSIGNMENT;" );

            while(rs.next()){
                int id = rs.getInt("ID");
                String name = rs.getString("ASSIGNMENTNAME");
                if(name1.equals(name)){
                    sql = "DELETE FROM " + username + "ASSIGNMENT WHERE ID = ?";
                    pstmt = c.prepareStatement(sql); 
                    // set the corresponding param
                    pstmt.setInt(1, id);
                    // execute the delete statement
                    pstmt.executeUpdate();
                    pstmt.close();
                }
            }

            rs.close();
            stmt.close();
 
        } catch (SQLException e) {
            System.out.println("DatabaseRemoveAssignment: " + e.getMessage());
            valid = 0;;
        }

        c.close();
        return valid;
    }
    

    //updates
    public int updateAssignment(String assignmentName, String type, String newData, String user) throws SQLException{
        int valid = 1;
        try{
            System.out.println("Updating " + assignmentName + " assignment to " + newData + " for " + user);
            
            c = connect();
            stmt = c.createStatement();
            
            rs = stmt.executeQuery( "SELECT * FROM " + user + "ASSIGNMENT;" );

            System.out.println(assignmentName);
            System.out.println("");

            while(rs.next()){
                int id = rs.getInt("ID");
                String name = rs.getString("ASSIGNMENTNAME");
                //System.out.println(name);
                if(assignmentName.equals(name)){
                    sql = "UPDATE " + user + "ASSIGNMENT " + "SET " + type+ " = ? where ID=?;";
                    pstmt = c.prepareStatement(sql); 
                    // set the corresponding param
                    pstmt.setString(1, newData);
                    pstmt.setInt(2, id);
                    // execute the delete statement
                    pstmt.executeUpdate();
                    pstmt.close();
                }
            }

            rs.close();
            stmt.close();
            
        } catch (SQLException e) {
            System.out.println("DatabaseUpdateAssignment: " + e.getMessage());
            valid = 0;
        }

        c.close();
        return valid;
    }
    
    
    public int updateEvent(String eventName, String type, String newData, String user) throws SQLException{
        int valid = 1;
        try{
            System.out.println("Updating " + eventName + " event to " + newData + " for " + user);
            c = connect();
            sql = "UPDATE " + user + "EVENT " + "SET " + type + " = ? where EVENTNAME=?;";
            pstmt = c.prepareStatement(sql); 
            pstmt.setString(1, newData);
            pstmt.setString(2, eventName);
            pstmt.executeUpdate();
            pstmt.close();
            
        } catch (SQLException e) {
            System.out.println("DatabaseUpdateEvent: " + e.getMessage());
            valid = 0;
        }

        c.close();
        return valid;
    }
    
    
    public int updateProfile(String type, String newData, String user) throws SQLException{
        int valid = 1;
        try{
            System.out.println("Updating " + user + " profile to " + newData + " for " + user);
            c = connect();
            sql = "UPDATE PROFILE " + "SET " + type + " = ? where USERNAME=?;";
            pstmt = c.prepareStatement(sql); 
            pstmt.setString(1, newData);
            pstmt.setString(2, user);
            pstmt.executeUpdate();
            pstmt.close();
            
        } catch (SQLException e) {
            System.out.println("DatabaseUpdateProfile: " + e.getMessage());
            valid = 0;
        }

        c.close();
        return valid;
    }
    
    //schedule section
    public int clearSchedule(String user) throws SQLException{
        int valid = 1;
        try{
            System.out.println("Clearing " + user + " schedule");
            c = connect();
            sql = "DELETE FROM " + user + "SCHEDULE;";
            stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();

        } catch (SQLException e) {
            System.out.println("DatabaseClearSchedule: " + e.getMessage());
            valid = 0;
        }

        c.close();
        return valid;
    }
    
    
    public int saveSchedule(String username, LinkedList<CalendarEvent> calList) {
        int valid = 1;
        Calendar tempCal = Calendar.getInstance();
        Calendar curCalen = Calendar.getInstance();
        int curDay = curCalen.get(Calendar.DAY_OF_YEAR);

        try{
            System.out.println("Saving " + username + " schedule");

            c = connect();
            stmt = c.createStatement();
            
            rs = stmt.executeQuery( "SELECT * FROM " + username + "SCHEDULE;" );

            while(rs.next()){
                int id = rs.getInt("ID");
                String end = rs.getString("END_TIME");
                java.util.Date endTime = df.parse(end);
                tempCal.setTime(endTime);
                int tempDay = tempCal.get(Calendar.DAY_OF_YEAR);
                if(tempDay >= curDay){
                    sql = "DELETE FROM " + username + "SCHEDULE WHERE ID = ?";
                    pstmt = c.prepareStatement(sql); 
                    // set the corresponding param
                    pstmt.setInt(1, id);
                    // execute the delete statement
                    pstmt.executeUpdate();
                    pstmt.close();
                }
            }

            rs.close();
            stmt.close();
            c.close();

            Calendar calEvent = Calendar.getInstance();
            int calEventDay = calEvent.get(Calendar.DAY_OF_YEAR);

            ListIterator calIter = calList.listIterator();
            while(calIter.hasNext()){
                CalendarEvent curCal = calList.get(calIter.nextIndex());
                if(calEventDay >= curDay)
                    valid = addCalEvent(username, curCal);
                calIter.next();
            }

        } catch (Exception e) {
            System.err.println( "DatabaseSaveSchedule:" + e.getClass().getName() + ": " + e.getMessage() );
            valid = 0;
        }
        return valid;
    }


    public int addCalEvent(String username, CalendarEvent curCal) throws SQLException{
        int valid = 1;
        try{
            c = connect();

            sql = "INSERT INTO " + username + "SCHEDULE VALUES(?,?,?,?,?,?)";
            pstmt = c.prepareStatement(sql);
            
            pstmt.setInt(1, curCal.getID());
            pstmt.setString(2,curCal.getName());
            String startDate = df.format(curCal.getStartTime());
            pstmt.setString(3,startDate);
            String endDate = df.format(curCal.getEndTime());
            pstmt.setString(4,endDate);
            pstmt.setString(5,curCal.getLocation());
            String dis = String.valueOf(curCal.getDisplay());
            pstmt.setString(6,dis);
            pstmt.executeUpdate();
            pstmt.close();
            
            System.out.println("CalendarEvent created");

        } catch (Exception e){
            System.err.println( "DatabaseCreateCalenadarEvent:" + e.getClass().getName() + ": " + e.getMessage() );
            valid = 0;
        }

        c.close();
        return valid;
    }


    public LinkedList<CalendarEvent> getSchedule(String username, String method) throws SQLException{
        LinkedList<CalendarEvent> calList= new LinkedList<CalendarEvent>();
        
        Calendar twoWeekCal = Calendar.getInstance();
        Calendar pastWeekCal = Calendar.getInstance();
        
        pastWeekCal.set(Calendar.DAY_OF_WEEK, 1);
        int week = pastWeekCal.get(Calendar.WEEK_OF_YEAR);
        pastWeekCal.set(Calendar.WEEK_OF_YEAR, week-1);
        int pastDayOfYear = pastWeekCal.get(Calendar.DAY_OF_YEAR);

        twoWeekCal.set(Calendar.DAY_OF_WEEK, 1);
        week = twoWeekCal.get(Calendar.WEEK_OF_YEAR);
        twoWeekCal.set(Calendar.WEEK_OF_YEAR, week+2);
        int futureDayOfYear = twoWeekCal.get(Calendar.DAY_OF_YEAR);

        Calendar calEventCal = Calendar.getInstance();
        Calendar curCal = Calendar.getInstance();

        int curDayOfYear = curCal.get(Calendar.DAY_OF_YEAR);

        try{
            c = connect();
            stmt = c.createStatement();
            
            rs = stmt.executeQuery( "SELECT * FROM " + username + "SCHEDULE;" );

            if(method.equals("display")){
                while(rs.next()){
                    int id = rs.getInt("ID");
                    String name = rs.getString("NAME");
                    String start = rs.getString("START_TIME");
                    String end = rs.getString("END_TIME");
                    String location = rs.getString("LOCATION");
                    String dis = rs.getString("DISPLAY");
                    java.util.Date startTime = df.parse(start);
                    java.util.Date endTime = df.parse(end);
                    boolean display = Boolean.parseBoolean(dis);

                    calEventCal.setTime(startTime);
                    int calEventDayOfYear = calEventCal.get(Calendar.DAY_OF_YEAR);

                    CalendarEvent curCalEve = new CalendarEvent(name, startTime, endTime, location, display, id);

                    if(calEventDayOfYear >= pastDayOfYear && calEventDayOfYear <= futureDayOfYear)
                        calList.add(curCalEve);
                }
            }

            else if(method.equals("schedule")){
                while(rs.next()){
                    int id = rs.getInt("ID");
                    String name = rs.getString("NAME");
                    String start = rs.getString("START_TIME");
                    String end = rs.getString("END_TIME");
                    String location = rs.getString("LOCATION");
                    String dis = rs.getString("DISPLAY");
                    java.util.Date startTime = df.parse(start);
                    java.util.Date endTime = df.parse(end);
                    boolean display = Boolean.parseBoolean(dis);
                    calEventCal.setTime(startTime);
                    
                    int calEventDayOfYear = calEventCal.get(Calendar.DAY_OF_YEAR);

                    CalendarEvent curCaleve = new CalendarEvent(name, startTime, endTime, location, display, id);

                    if(calEventDayOfYear >= pastDayOfYear && calEventDayOfYear <= curDayOfYear)
                        calList.add(curCaleve);
                }
            }
            rs.close();
            stmt.close();

        } catch (Exception e){
            System.err.println( "DatabaseGetSchedule:" + e.getClass().getName() + ": " + e.getMessage() );
            calList = null;
        }
        
        c.close();
        return calList;
    }
    
    
    //displays
    public int display(String user) throws SQLException{
        int valid = 1;
        Connection c = null;
        try{
            c = connect();
            stmt = c.createStatement();
            
            rs = stmt.executeQuery( "SELECT * FROM PROFILE;" );
            
            System.out.println("\nPrinting DataBase...\n");
            System.out.println("Profile List:\n------------------------");
            while(rs.next()){
                String username = rs.getString("username");
                String name = rs.getString("profilename");
                String email = rs.getString("email");
                String pass = rs.getString("password");
                String bed = rs.getString("bedtime");
                System.out.println(username);
                System.out.println(name);
                System.out.println(email);
                System.out.println(pass);
                System.out.println(bed);
                System.out.println();
            }
            rs.close();
            
            System.out.println("Event List:\n------------------------");
            
            rs = stmt.executeQuery( "SELECT * FROM " + user + "EVENT;" );
            while(rs.next()){
                String name = rs.getString("EVENTNAME");
                String days = rs.getString("days");
                String start = rs.getString("start_time");
                String end = rs.getString("end_time");
                String loc = rs.getString("location");
                System.out.println(name);
                System.out.println(days);
                System.out.println(start);
                System.out.println(end);
                System.out.println(loc);
                System.out.println();
            }
            rs.close();
            
            System.out.println("Assignment List:\n------------------------");
            
            rs = stmt.executeQuery( "SELECT * FROM " + user + "ASSIGNMENT;" );
            while(rs.next()){
                String name = rs.getString("ASSIGNMENTNAME");
                String className = rs.getString("classname");
                String dueDate = rs.getString("due");
                String hours = rs.getString("hourstocompletion");
                String pri = rs.getString("priority");
                String appPri = rs.getString("apppriority");
                int id = rs.getInt("ID");
                System.out.println(name);
                System.out.println(className);
                System.out.println(dueDate);
                System.out.println(hours);
                System.out.println(pri);
                System.out.println(appPri);
                System.out.println(id);
                System.out.println();
            }
            System.out.println("------------------------end");
            rs.close();
            stmt.close();
            
        }catch (Exception e){
            System.err.println( "DatabaseDisplay: " + e.getClass().getName() + ": " + e.getMessage() );
            valid = 0;
        }

        c.close();
        return valid;
    }


    public Profile getAccountInfo(String user) throws SQLException{

        Profile curUser = null;
        String username = "";
        String name = "";
        String email = "";
        String bedTime = "";
        String wakeTime = "";
        boolean found = false;

        try{
            c = connect();
            stmt= c.createStatement();

            rs = stmt.executeQuery( "SELECT * FROM PROFILE" );

            while(rs.next() && !found){
                if(rs.getString("USERNAME").equals(user)){
                    username = rs.getString("USERNAME");
                    name = rs.getString("PROFILENAME");
                    email = rs.getString("EMAIL");
                    bedTime = rs.getString("BEDTIME");
                    wakeTime = rs.getString("WAKETIME");
                    found = true;
                }
            }

            curUser = new Profile(username, name, email, bedTime, wakeTime);
            
            rs.close();
            stmt.close();
        } catch (Exception e){
            System.err.println( "DatabaseGetAccoutnInfo: " + e.getClass().getName() + ": " + e.getMessage() );
        }

        c.close();

        return curUser;
    }
    
    
    public LinkedList<Assignment> getAssignmentList(String user) throws SQLException{
        LinkedList<Assignment> assignList = new LinkedList<Assignment>();
        LinkedList<Assignment> tempList = new LinkedList<Assignment>();
        Calendar curCal = Calendar.getInstance();
        Calendar assignCal = Calendar.getInstance();
        try{
            c = connect();
            stmt = c.createStatement();
            
            rs = stmt.executeQuery( "SELECT * FROM " + user + "ASSIGNMENT;" );
            
            while(rs.next()){
                String name = rs.getString("ASSIGNMENTNAME");
                String className = rs.getString("classname");
                String dueDate = rs.getString("due");
                String hours = rs.getString("hourstocompletion");
                String pri = rs.getString("priority");
                String appPri = rs.getString("apppriority");
                int id = rs.getInt("ID");
                java.util.Date due = df.parse(dueDate);
                int curDay = curCal.get(Calendar.DAY_OF_YEAR);
                assignCal.setTime(due);
                int assignDay = assignCal.get(Calendar.DAY_OF_YEAR);
                Assignment assign = new Assignment(name, className, due, hours, pri, appPri, id);
                if(curDay <= assignDay)
                    assignList.add(assign);
                else
                    tempList.add(assign);
            }

            rs.close();
            stmt.close();

        } catch (Exception e){
            System.err.println( "DatabaseGetAssignmentList:" + e.getClass().getName() + ": " + e.getMessage() );
        }

        ListIterator iter = tempList.listIterator();

        while(iter.hasNext()){
            removeAssignment(tempList.get(iter.nextIndex()).getAssignName(), user);
            iter.next();
        }
        
        c.close();
        return assignList;
    }

    
    public LinkedList<Event> getEventList(String user) throws SQLException{
        LinkedList<Event> eventList= new LinkedList<Event>();
        LinkedList<Event> tempList = new LinkedList<Event>();
        df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
        Calendar curCal = Calendar.getInstance();
        Calendar eventCal = Calendar.getInstance();
        try{
            c = connect();
            stmt = c.createStatement();
            
            rs = stmt.executeQuery( "SELECT * FROM " + user + "EVENT;" );
            
            while(rs.next()){
                String eventName = rs.getString("EVENTNAME");
                String days = rs.getString("DAYS");
                String start = rs.getString("START_TIME");
                java.util.Date startTime = df.parse(start);
                int curDay = curCal.get(Calendar.DAY_OF_YEAR);
                eventCal.setTime(startTime);
                int eventDay = eventCal.get(Calendar.DAY_OF_YEAR);
                String end = rs.getString("END_TIME");
                java.util.Date endTime = df.parse(end);
                String loc = rs.getString("LOCATION");
                Event eve = new Event(eventName, days, startTime, endTime, loc);
                if(!days.equals(""))  
                    eventList.addLast(eve);   
                else if(curDay <= eventDay)
                    eventList.addLast(eve);   
                else
                    tempList.add(eve);
            }
            
            rs.close();
            stmt.close();

        } catch (Exception e){
            System.err.println( "DatabaseGetEventList:" + e.getClass().getName() + ": " + e.getMessage() );
        }

        ListIterator iter = tempList.listIterator();

        while(iter.hasNext()){
            removeEvent(tempList.get(iter.nextIndex()).getEventName(), user);
            iter.next();
        }
        
        c.close();
        return eventList;
    }

    public int[] getBedTime(String user)throws SQLException{
        String bedTime = "";
        int[] times = new int[2];
        Boolean found = false;
        try{
            c = connect();
            stmt= c.createStatement();

            rs = stmt.executeQuery( "SELECT USERNAME,BEDTIME FROM PROFILE" );

            while(rs.next() && !found){
                if(rs.getString("USERNAME").equals(user)){
                    bedTime = rs.getString("BEDTIME");
                    found = true;
                }
            }
            
            rs.close();
            stmt.close();

        } catch (Exception e){
            System.err.println( "DatabaseGetBedTime:" + e.getClass().getName() + ": " + e.getMessage() );
        }

        char[] bedToChar = bedTime.toCharArray();
        char[] temp = new char[2];
        int count = 0;
        int intArrayIndex = 0;

        while (count < 4){
            temp[0] = bedToChar[count++];
            temp[1] = bedToChar[count++];

            bedTime = new String(temp);
            int bed = Integer.parseInt(bedTime);

            times[intArrayIndex++] = bed;
        }
        
        c.close();
        return times;
    }


    public int[] getWakeTime(String user)throws SQLException{
        String wakeTime = "";
        int[] times = new int[2];
        Boolean found = false;
        try{
            c = connect();
            stmt= c.createStatement();

            rs = stmt.executeQuery( "SELECT USERNAME,WAKETIME FROM PROFILE" );

            while(rs.next() && !found){
                if(rs.getString("USERNAME").equals(user)){
                    wakeTime = rs.getString("WAKETIME");
                    found = true;
                }
            }
            
            rs.close();
            stmt.close();

        } catch (Exception e){
            System.err.println( "DatabaseGetWakeTime:" + e.getClass().getName() + ": " + e.getMessage() );
        }

        char[] wakeToChar = wakeTime.toCharArray();
        char[] temp = new char[2];
        int count = 0;
        int intArrayIndex = 0;

        while (count < 4){
            temp[0] = wakeToChar[count++];
            temp[1] = wakeToChar[count++];

            wakeTime = new String(temp);
            int wake = Integer.parseInt(wakeTime);

            times[intArrayIndex++] = wake;
        }
        
        c.close();
        return times;
    }
    
    
    private Connection connect() throws SQLException{
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:inventory.db");
            return c;
        } catch (Exception e){
            System.err.println("DatabaseConnection:" + e.getClass() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
        return c;
    }
}


