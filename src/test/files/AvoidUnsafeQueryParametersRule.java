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

    @Autowired
    private String autowiredStuff;

    private static final String STATIC_UID = "admin";
    private static final String QUERY = "SELECT * FROM {User} WHERE {p.uid} = ";

    public void doSomethingWrong(final String uid)
    {
        final FlexibleSearchQuery fQuery = new FlexibleSearchQuery("SELECT {lastName} FROM {User} WHERE {p.uid} = " + uid);  // Noncompliant
        flexibleSearchService.searchUnique(fQuery);
    }

    public void doSomethingWrong2(final String uid)
    {
        final FlexibleSearchQuery fQuery = new FlexibleSearchQuery("SELECT {pk} FROM {User} WHERE {p.uid} = " + String.format(uid, "test"));  // Noncompliant
        flexibleSearchService.searchUnique(fQuery);
    }

    public void doSomethingWrong3(final String uid)
    {
        final FlexibleSearchQuery fQuery = new FlexibleSearchQuery("SELECT {passwd} FROM {User} WHERE {p.uid} = " + String.format(uid, "test") + " and 1=1");  // Noncompliant
        flexibleSearchService.searchUnique(fQuery);
    }

    public void doSomethingWrong4(final String uid)
    {
        final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(String.format(QUERY, uid));  // Noncompliant
        flexibleSearchService.searchUnique(fQuery);
    }

    public void doSomethingWrong5(final String uid)
    {
        flexibleSearchService.search("SELECT {encoding} FROM {User} WHERE {p.uid} = " + uid);  // Noncompliant
    }

    public void doSomething()
    {
        final FlexibleSearchQuery fQuery = new FlexibleSearchQuery("SELECT {name} FROM {User} WHERE {p.uid} = " + this.uid);  // Compliant
        flexibleSearchService.searchUnique(fQuery);
    }

    public void doSomething2(final String uid)
    {
        final FlexibleSearchQuery fQuery = new FlexibleSearchQuery("SELECT * FROM {User} WHERE {p.uid} = ?uid");  // Compliant
        fQuery.addQueryParameter("uid", uid);
        flexibleSearchService.searchUnique(fQuery);
    }

    public void doSomething3()
    {
        final FlexibleSearchQuery fQuery = new FlexibleSearchQuery("SELECT {firstName} FROM {User} WHERE {p.uid} = " + this.autowiredStuff);  // Compliant
        flexibleSearchService.searchUnique(fQuery);
    }

    public void doSomething4()
    {
        final FlexibleSearchQuery fQuery = new FlexibleSearchQuery("SELECT {lastName} FROM {User} WHERE {p.uid} = " + MyService.STATIC_UID);  // Compliant
        flexibleSearchService.searchUnique(fQuery);
    }

    public void doSomething5(String query)
    {
        final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(query); // Compliant
        flexibleSearchService.searchUnique(fQuery);
    }

    public void doSomething6(String query)
    {
        flexibleSearchService.search(query); // Compliant
    }
}