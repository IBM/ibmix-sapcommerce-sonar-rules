import de.hybris.platform.core.Registry;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.core.model.user.UserModel;
import java.sql.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyService
{
    @Autowired
    private FlexibleSearchService flexibleSearchService;

    public void doSomething()
    {
        Connection con = Registry.getCurrentTenant().getDataSource().getConnection(true); // Noncompliant
        con.prepareCall("SELECT * FROM users WHERE P_UID = 'admin'");
    }

    public void doSomethingElse()
    {
        final FlexibleSearchQuery fQuery = new FlexibleSearchQuery("SELECT * FROM {User} WHERE {p.uid} = ?uid");
        fQuery.addQueryParameter("uid", "admin");
        flexibleSearchService.searchUnique(fQuery); // Compliant
    }
}