package huhu.com.qrback.Config;

/**
 * Created by Huhu on 5/6/16.
 */
public class Config {
    public static final String CHARSET = "utf-8";
    public static final String LOCALHOST = "121.250.222.66";
    public static final String URL_LOGIN = "http://" + LOCALHOST + ":8080/servlet/RootLogin";
    public static final String URL_CURRENT = "http://" + LOCALHOST + ":8080/servlet/GetMeetings";
    public static final String URL_HISTORY = "http://" + LOCALHOST + ":8080/servlet/GetHistoryMeetings";
    public static final String URL_DETAIL = "http://" + LOCALHOST + ":8080/servlet/MeetingInfo";
    public static final String URL_ADDMEMBER = "http://" + LOCALHOST + ":8080/servlet/AddPerson";
    public static final String URL_GETSIGN = "http://" + LOCALHOST + ":8080/servlet/GetPoints";
    public static final String URL_ADDMEET = "http://" + LOCALHOST + ":8080/servlet/AddMeeting";
    public static final String URL_CUT = "http://" + LOCALHOST + ":8080/servlet/MeetingOver";


}
