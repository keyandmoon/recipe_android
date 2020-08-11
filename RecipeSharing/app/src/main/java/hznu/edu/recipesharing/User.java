package hznu.edu.recipesharing;

public class User {
    private String user_id;
    private String password;
    private String question;
    private String answer;
    private String user_name;
    private String user_sex;
    private String user_residence;
    private String user_job;
    private String user_profile;
    User(){}

    public User(String user_id, String password){
        this.user_id = user_id;
        this.password = password;
    }

    public void setUser_id(String user_id){
        this.user_id = user_id;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getPassword() {
        return password;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String getUser_profile() {
        return user_profile;
    }

    public void setUser_profile(String user_profile) {
        this.user_profile = user_profile;
    }

    public void setUser_job(String user_job) {
        this.user_job = user_job;
    }

    public String getUser_job() {
        return user_job;
    }

    public String getUser_residence() {
        return user_residence;
    }

    public void setUser_residence(String user_residence) {
        this.user_residence = user_residence;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
