import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;

public class Community {
  private String name;
  private String description;
  private int id;



  public Community(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object otherCommunity){
    if (!(otherCommunity instanceof Community)) {
      return false;
    } else {
    Community newCommunity = (Community) otherCommunity;
    return this.getName().equals(newCommunity.getName()) &&
           this.getDescription().equals(newCommunity.getDescription());
   }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO communities (name, description) VALUES (:name, :description)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("description", this.description)
        .executeUpdate()
        .getKey();
    }
  }

  public void addPerson() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO communities_persons (community_id, person_id) values (:community_id, :person_id)";
      con.createQuery(sql)
        .addParameter("community_id", this.getId())
        .addParameter("person_id", person.getId())
        .executeUpdate();
    }
  }

  public static List<Community> all() {
    String sql = "SELECT * FROM communities";
    try(Connection con = DB.sql2o.open()) {
    return con.createQuery(sql).executeAndFetch(Community.class);
    }
  }

  public List<Person> getPersons() {
    try(Connection con = DB.sql2o.open()){
      String joinQuery = "SELECT person_id FROM communities_persons WHERE community_id = :community_id";
      List<Integer> personIds = con.createQuery(joinQuery)
        .addParameter("community_id", this.getId())
        .executeAndFetch(Integer.class);

      List<Person> persons = new ArrayList<Person>();

      for (Integer personId : personIds) {
        String personQuery = "SELECT * FROM persons WHERE id = :personId";
        Person person = con.createQuery(personQuery)
          .addParameter("personId", personId)
          .executeAndFetchFirst(Person.class);
        persons.add(person);
      }
      return persons;
    }
  }

  public List<Community> getCommunities() {
    try(Connection con = DB.sql2o.open()){
      String joinQuery = "SELECT community_id FROM communities_persons WHERE person_id = :person_id";
      List<Integer> communityIds = con.createQuery(joinQuery)
        .addParameter("person_id", this.getId())
        .executeAndFetch(Integer.class);

      List<Community> communities = new ArrayList<Community>();

      for (Integer communityId : communityIds) {
        String communityQuery = "SELECT * FROM communities WHERE id = :communityId";
        Community community = con.createQuery(communityQuery)
          .addParameter("communityId", communityId)
          .executeAndFetchFirst(Community.class);
        communities.add(community);
      }
      return communities;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM communities WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
      String joinDeleteQuery = "DELETE FROM communities_persons where community_id = :communityId";
      con.createQuery(joinDeleteQuery)
        .addParameter("communityId", this.getId())
        .executeUpdate();
    }
  }

  public void removePerson(Person person){
    try(Connection con = DB.sql2o.open()){
      String joinRemovalQuery = "DELETE FROM communities_persons WHERE community_id = :communityId AND person_id = :personId;";
      con.createQuery(joinRemovalQuery)
        .addParameter("communityId", this.getId())
        .addParameter("personId", person.getId())
        .executeUpdate();
    }
  }

}
