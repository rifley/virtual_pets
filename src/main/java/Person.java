import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;

public class Person {
  private String name;
  private String email;
  private int id;

  public Person(String name, String email) {
    this.name = name;
    this.email = email;

  }

  @Override
  public boolean equals(Object otherPerson){
    if (!(otherPerson instanceof Person)) {
      return false;
    } else {
      Person newPerson = (Person) otherPerson;
      return this.getName().equals(newPerson.getName()) &&
             this.getEmail().equals(newPerson.getEmail());
    }
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public int getId() {
    return id;
  }


  public void save() {
  try(Connection con = DB.sql2o.open()) {
    String sql = "INSERT INTO persons (name, email) VALUES (:name, :email)";
    this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
      .addParameter("email", this.email)
      .executeUpdate()
      .getKey();
    }
  }

  public static List<Person> all() {
    String sql = "SELECT * FROM persons";
    try(Connection con = DB.sql2o.open()) {
     return con.createQuery(sql)
     .throwOnMappingFailure(false)
     .executeAndFetch(Person.class);
    }
  }

  public List<Object> getMonsters() {
    List<Object> allMonsters = new ArrayList<Object>();

    try(Connection con = DB.sql2o.open()) {
      String sqlFire = "SELECT * FROM monsters WHERE personId=:id AND type='fire';";
      List<FireMonster> fireMonsters = con.createQuery(sqlFire)
        .addParameter("id", this.id)
        .throwOnMappingFailure(false)
        .executeAndFetch(FireMonster.class);
        allMonsters.addAll(fireMonsters);

      String sqlWater = "SELECT * FROM monsters WHERE personId=:id AND type='water';";
      List<WaterMonster> waterMonsters = con.createQuery(sqlWater)
        .addParameter("id", this.id)
        .throwOnMappingFailure(false)
        .executeAndFetch(WaterMonster.class);
        allMonsters.addAll(waterMonsters);
      }
      return allMonsters;
  }

  public void leaveCommunity(Community community) {
    try(Connection con = DB.sql2o.open()){
      String joinRemovalQuery = "DELETE FROM communities_persons WHERE community_id = :communityId AND person_id = :personId;";
      con.createQuery(joinRemovalQuery)
        .addParameter("communityId", community.getId())
        .addParameter("personId", this.getId())
        .executeUpdate();
    }
  }
}
