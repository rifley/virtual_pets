import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

public class PersonTest {


  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void person_instantiatesCorrectly_true() {
    Person testPerson = new Person("Henry", "henry@henry.com");
    assertEquals(true, testPerson instanceof Person);
  }

  @Test
  public void getEmail_personInstantiatesWithEmail_String() {
    Person testPerson = new Person("Henry", "henry@henry.com");
    assertEquals("henry@henry.com", testPerson.getEmail());
  }

  @Test
  public void equals_returnsTrueIfNameAndEmailAreSame_true() {
    Person firstPerson = new Person("Henry", "henry@henry.com");
    Person anotherPerson = new Person("Henry", "henry@henry.com");
    assertTrue(firstPerson.equals(anotherPerson));
  }

  @Test
  public void save_insertsObjectIntoDatabase_Person() {
    Person testPerson = new Person("Henry", "henry@henry.com");
    testPerson.save();
    assertTrue(Person.all().get(0).equals(testPerson));
  }

  @Test
  public void all_returnsAllInstancesOfPerson_true() {
   Person firstPerson = new Person("Henry", "henry@henry.com");
   firstPerson.save();
   Person secondPerson = new Person("Harriet", "harriet@harriet.com");
   secondPerson.save();
   assertEquals(true, Person.all().get(0).equals(firstPerson));
   assertEquals(true, Person.all().get(1).equals(secondPerson));
 }

 @Test
  public void getMonsters_retrievesAllMonstersFromDatabase_monstersList() {
    Person testPerson = new Person("Henry", "henry@henry.com");
    testPerson.save();
    FireMonster firstMonster = new FireMonster("Smokey", testPerson.getId());
    firstMonster.save();
    WaterMonster secondMonster = new WaterMonster("Drippy", testPerson.getId());
    secondMonster.save();
    Object[] monsters = new Object[] { firstMonster, secondMonster };
    assertTrue(testPerson.getMonsters().containsAll(Arrays.asList(monsters)));
  }

  @Test
  public void leaveCommunity_removesAssociationWithSpecifiedCommunity() {
    Community testCommunity = new Community("Fire Enthusiasts", "Flame on!");
    testCommunity.save();
    Person testPerson = new Person("Henry", "henry@henry.com");
    testPerson.save();
    testPerson.leaveCommunity(testCommunity);
    List savedCommunities = testPerson.getCommunities();
    assertEquals(0, savedCommunities.size());
  }

}
