package hznu.edu.recipesharing;


import android.content.Context;
import android.content.SharedPreferences;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class UserUtils {
    private String driver="com.mysql.jdbc.Driver";
    private String url="jdbc:mysql://10.0.2.2:3306/recipe?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    private String username="root";
    private String password="123456";
    private Connection conn = null;
    private PreparedStatement pstmt;
    private Statement stmt;
    private Context context;
    public UserUtils(){}

    public UserUtils(Context context){
        this.context = context;
    }

    public Connection createConnection(){
        try{
            Class.forName(driver);
            if(conn == null) {
                conn = DriverManager.getConnection(url, username, password);
                return conn;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return conn;
    }

    public int login(User user) {
        int i = 0;
        try {
            String sql = "select * from user where user_id=?";
            conn = createConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,user.getUser_id());
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                i = 1;
            }
            saveUser(user.getUser_id());
            if(rs == null)
                rs.close();
            if(pstmt == null)
                pstmt.close();
            if(conn == null)
                conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    public void saveUser(String user_id){
        SharedPreferences.Editor editor = context.getSharedPreferences("data", MODE_PRIVATE).edit();
        editor.putString("user_id", user_id);
        editor.commit();
    }

    public void logout(Context c){
        SharedPreferences sp = c.getSharedPreferences("data",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
        ActivityCollector.finishAll();
    }

    public int register(User user){
        int i = 0;
        try{
            String sql = "insert into user (user_id,password,security_question,security_answer) values (?,SHA(?),?,?)";
            conn = createConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,user.getUser_id());
            pstmt.setString(2,user.getPassword());
            pstmt.setString(3,user.getQuestion());
            pstmt.setString(4,user.getAnswer());
            i = pstmt.executeUpdate();
            if(pstmt == null)
                pstmt.close();
            if(conn == null)
                conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return i;
    }

    //执行不带参数的查询
    public ArrayList<Map<String, String>> queryForList(String sql){
        ArrayList<Map<String,String>> results=null;
        try {
            conn = createConnection();
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if(rs != null){
                results = new ArrayList<Map<String,String>>();
                while(rs.next()){
                    Map<String,String> result = new HashMap<String,String>();
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int columnCount = rsmd.getColumnCount();
                    for(int i = 1; i <= columnCount; i++){
                        String fieldName = rsmd.getColumnName(i).toLowerCase();
                        String fieldValue = rs.getString(i);
                        result.put(fieldName, fieldValue);
                    }
                    results.add(result);
                }
            }
            if(rs == null)
                rs.close();
            if(pstmt == null)
                pstmt.close();
            if(conn == null)
                conn.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    //查询某用户的所有信息
    public ArrayList<Map<String, String>> queryTheUserInfo(String sql, Context c){
        ArrayList<Map<String,String>> results=null;
        SharedPreferences sp = c.getSharedPreferences("data",MODE_PRIVATE);
        String id = sp.getString("user_id","");
        try {
            conn = createConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,id);
            ResultSet rs = pstmt.executeQuery();
            if(rs != null){
                results = new ArrayList<Map<String,String>>();
                while(rs.next()){
                    Map<String,String> result = new HashMap<String,String>();
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int columnCount = rsmd.getColumnCount();
                    for(int i = 1; i <= columnCount; i++){
                        String fieldName = rsmd.getColumnName(i).toLowerCase();
                        String fieldValue = rs.getString(i);
                        result.put(fieldName, fieldValue);
                    }
                    results.add(result);
                }
            }
            if(rs == null)
                rs.close();
            if(pstmt == null)
                pstmt.close();
            if(conn == null)
                conn.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public String queryRecipeContent(String recipeName){
        String sql = "select recipe_content from table_recipe where recipe_name=?";
        String result = "";
        try {
            conn = createConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,recipeName);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                result = rs.getString(1);
            }
            if(rs == null)
                rs.close();
            if(pstmt == null)
                pstmt.close();
            if(conn == null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int queryMyRecipe(Context c){
        int i = 0;
        SharedPreferences sp = c.getSharedPreferences("data",MODE_PRIVATE);
        String id = sp.getString("user_id","");
        try {
            String sql = "select * from table_recipe where user_id=?";
            conn = createConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,id);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                i = 1;
            }
            if(rs == null)
                rs.close();
            if(pstmt == null)
                pstmt.close();
            if(conn == null)
                conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    //上传菜谱
    public int uploadRecipe(Recipe r){
        int i = 0; //成功标志
        String sql = "insert into table_recipe (recipe_id,recipe_content," +
                "recipe_name,recipe_writer,recipe_tag,recipe_editdate" +
                ") values (SHA(?),?,?,?,?,?)";
        //ID
        SharedPreferences sp = context.getSharedPreferences("data",MODE_PRIVATE);
        String id = sp.getString("user_id","");
        //日期
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime()); //可直接存入数据库
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = df.format(date);
        String recipe_name = r.getRecipe_name();
        String recipe_content = r.getRecipe_content();
        String recipe_tag = r.getRecipe_tag();
        try{
            conn = createConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,id + datetime);
            pstmt.setString(2,recipe_content);
            pstmt.setString(3,recipe_name);
            pstmt.setString(4,id);
            pstmt.setString(5,recipe_tag);
            pstmt.setTimestamp(6, timestamp);
            i = pstmt.executeUpdate();
            if(pstmt == null)
                pstmt.close();
            if(conn == null)
                conn.close();
        }catch (Exception e){e.printStackTrace();}
        return i;
    }

    //修改个人资料
    public int ModifyInfo(User user,Context c){
        int i = 0;
        String sql = "update user set user_name=?,sex=?,residence=?,job=?,profile=? where user_id=?";
        SharedPreferences sp = c.getSharedPreferences("data",MODE_PRIVATE);
        String id = sp.getString("user_id","");
        try {
            conn = createConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,user.getUser_name());
            pstmt.setString(2,user.getUser_sex());
            pstmt.setString(3,user.getUser_residence());
            pstmt.setString(4,user.getUser_job());
            pstmt.setString(5,user.getUser_profile());
            pstmt.setString(6,id);
            i = pstmt.executeUpdate();
            if(pstmt == null)
                pstmt.close();
            if(conn == null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

}


