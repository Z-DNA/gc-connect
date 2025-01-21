package pl.zdna.gcconnect.users.infrastructure;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;
import pl.zdna.gcconnect.users.application.LoggedInUserService;

@Service
public class LoggedInUserServiceImpl implements LoggedInUserService {
    @Override
    public String getLoggedInUsername() {
        throw new NotImplementedException();
    }
}
