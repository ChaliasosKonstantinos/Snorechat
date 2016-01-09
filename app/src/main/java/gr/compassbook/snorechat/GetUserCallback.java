package gr.compassbook.snorechat;

import java.util.List;

/**
 * Created by Konstantinos
 */
public interface GetUserCallback {

    void done(User returnedUser);
    List<String> done2(List<String> usernames);


}
