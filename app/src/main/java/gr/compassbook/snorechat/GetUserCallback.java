package gr.compassbook.snorechat;

import java.util.List;
import gr.compassbook.snorechat.Jsondecode;

/**
 * Created by Konstantinos
 */
public interface GetUserCallback {

    void done(User returnedUser);
    void done2(List<String> usernames);


}
