import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;

public class CommunityTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void community_instantiatesCorrectly_true() {
    Community testCommunity = new Community("Water Enthusiasts", "Lovers of all things water monsters!");
    assertEquals(true, testCommunity instanceof Community);
  }

  @Test
  public void getName_communityInstantiatesWithName_true() {
    Community testCommunity = new Community("Water Enthusiasts", "Lovers of all things water monsters!");
    assertEquals("Water Enthusiasts", testCommunity.getName());
  }

  @Test
  public void equals_returnsTrueIfNameAndDescriptionAreSame_true() {
    Community testCommunity = new Community("Fire Enthusiasts", "Flame on!");
    Community anotherCommunity = new Community("Fire Enthusiasts", "Flame on!");
    assertTrue(testCommunity.equals(anotherCommunity));
  }

  @Test
  public void all_returnsAllInstancesOfCommunity_true() {
    Community firstCommunity = new Community("Fire Enthusiasts", "Flame on!");
    firstCommunity.save();
    Community secondCommunity = new Community("Water Enthusiasts", "Lovers of all things water monsters!");
    secondCommunity.save();
    assertEquals(true, Community.all().get(0).equals(firstCommunity));
    assertEquals(true, Community.all().get(1).equals(secondCommunity));
  }

  @Test
  public void addPerson_addsPersonToCommunity() {
    Community testCommunity = new Community("Fire Enthusiasts", "Flame on!");
    testCommunity.save();
    Person testPerson = new Person("Henry", "henry@henry.com");
    testPerson.save();
    testCommunity.addPerson(testPerson);
    Person savedPerson = testCommunity.getPersons().get(0);
    assertTrue(testPerson.equals(savedPerson));
  }

  @Test
  public void getCommunities_returnsAllCommunities_List() {
    Community testCommunity = new Community("Fire Enthusiasts", "Flame on!");
    testCommunity.save();
    Person testPerson = new Person("Henry", "henry@henry.com");
    testPerson.save();
    testCommunity.addPerson(testPerson);
    List savedCommunities = testPerson.getCommunities();
    assertEquals(1, savedCommunities.size());
  }

  @Test
  public void delete_deletesCommunity_true() {
    Community testCommunity = new Community("Fire Enthusiasts", "Flame on!");
    testCommunity.save();
    testCommunity.delete();
    assertEquals(0, Community.all().size());
  }

  @Test
  public void delete_deletesAllPersonsAndCommunitiesAssociations() {
    Community testCommunity = new Community("Fire Enthusiasts", "Flame on!");
    testCommunity.save();
    Person testPerson = new Person("Henry", "henry@henry.com");
    testPerson.save();
    testCommunity.addPerson(testPerson);
    testCommunity.delete();
    assertEquals(0, testPerson.getCommunities().size());
  }

  @Test
  public void removePerson_removesAssociationWithSpecifiedCommunity() {
    Community testCommunity = new Community("Fire Enthusiasts", "Flame on!");
    testCommunity.save();
    Person testPerson = new Person("Henry", "henry@henry.com");
    testPerson.save();
    testCommunity.removePerson(testPerson);
    List savedPersons = testCommunity.getPersons();
    assertEquals(0, savedPersons.size());
  }
}
