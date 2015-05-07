package holamundo.itesm.mx.houseundercontrol_v1;

import com.parse.Parse;
import android.app.Application;

public class HouseUnderControl extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "iKGWAwlQ9Z2AWSyDMb24uxXi1jHV0J4QiAtA51Gq", "fZ0HR3NhJ9OFDXzAsZpmMo7habc07M8CYaZEZGwC");
    }
}