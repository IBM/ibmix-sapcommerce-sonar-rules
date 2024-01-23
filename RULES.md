SAP Commerce custom rule summary
=======
### Security Hotspots
* Review change of SAP Commerce session context
```
public void doSomething()
{
    sessionService.executeInLocalView(new SessionExecutionBody() // Noncompliant
    {
        @Override
        public Object execute()
        {
            //Unsecure code
        }
    }, userService.getAdminUser());
}
```
* Review SAP Commerce search restrictions disable
```
public void doSomething()
{    
    searchRestrictionService.disableSearchRestrictions(); // Noncompliant
    productDAO.find(restrictedProduct);
    final FlexibleSearchQuery fQuery = new FlexibleSearchQuery("SELECT {passwd} FROM {User}");
    fQuery.setDisableSearchRestrictions(true); // Noncompliant
    flexibleSearchService.searchUnique(fQuery);
}
```
* Review use of dynamic variables in your flexible search query string
```
public void doSomethingWrong(final String uid)
{
    final FlexibleSearchQuery fQuery = new FlexibleSearchQuery("SELECT * FROM {User} WHERE {p.uid} = " + uid);  // Noncompliant
    flexibleSearchService.searchUnique(fQuery);
}
```
### Vulnerabilities
* Do not use SAP Commerce datasource directly
```
public void doSomething()
{
    Connection con = Registry.getCurrentTenant().getDataSource().getConnection(true); // Noncompliant
    con.prepareCall("SELECT * FROM users WHERE P_UID = 'admin'");
}
```
### Bugs
* SAP Commerce do not use constructor on entity models
```
public void doSomething()
{
    UserModel user = new UserModel(); // Noncompliant
}
```
### Code Smells
* Do not use entity models in controllers
```
@GetMapping
public String profilePage(final Model model) throws CMSItemNotFoundException
{
    CustomerModel customer = customerDAO.getCustomerByUID(facade.getCurrentUserID()); // Noncompliant
    model.addAttribute("user", customer);
    return getViewForPage(model);
}
```
* Do not update database in populators
```
@Override
public void populate(final UserModel source, final CustomerData target)
{
    ...
    modelService.save(source); // Noncompliant
    modelService.saveAll(Arrays.asList(source)); // Noncompliant
    modelService.remove(source); // Noncompliant
    modelService.removeAll(Arrays.asList(source)); // Noncompliant
}
```