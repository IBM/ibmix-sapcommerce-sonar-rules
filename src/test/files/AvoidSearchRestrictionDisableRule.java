import de.hybris.platform.search.restriction.SearchRestrictionService;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyService
{
    @Autowired
    private SearchRestrictionService searchRestrictionService;
    @Autowired
    private FlexibleSearchService flexibleSearchService;

    public void doSomething()
    {
        searchRestrictionService.disableSearchRestrictions(); // Noncompliant
    }

    public void doSomething2()
    {
        final FlexibleSearchQuery fQuery = new FlexibleSearchQuery("SELECT {passwd} FROM {User}");
        fQuery.setDisableSearchRestrictions(true); // Noncompliant
        flexibleSearchService.searchUnique(fQuery);
    }

    public void doSomething3()
    {
        final FlexibleSearchQuery fQuery = new FlexibleSearchQuery("SELECT {name} FROM {User}");
        fQuery.setDisableSearchRestrictions(false);   // Compliant
        flexibleSearchService.searchUnique(fQuery);
    }
}