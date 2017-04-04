import java.sql.Timestamp;
import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;


public class Monster {

  private Timestamp birthday;
  private Timestamp lastSlept;
  private Timestamp lastAte;
  private Timestamp lastPlayed;
  private String name;
  private int personId;
  private int id;
  private int foodLevel;
  private int sleepLevel;
  private int playLevel;

  public static final int MAX_FOOD_LEVEL = 3;
  public static final int MAX_SLEEP_LEVEL = 8;
  public static final int MAX_PLAY_LEVEL = 12;
  public static final int MIN_ALL_LEVELS = 0;

  public Monster(String name, int personId) {
    this.name = name;
    this.personId = personId;
    this.playLevel = MAX_PLAY_LEVEL / 2;
    sleepLevel = MAX_SLEEP_LEVEL / 2;
    foodLevel = MAX_FOOD_LEVEL / 2;
  }

  public int getPlayLevel() {
    return playLevel;
  }

  public int getSleepLevel() {
    return sleepLevel;
  }

  public int getFoodLevel() {
    return foodLevel;
  }

  public int getId() {
    return id;
  }

  public Timestamp getBirthday() {
    return birthday;
  }

  public boolean isAlive() {
    if (foodLevel <= MIN_ALL_LEVELS ||
    playLevel <= MIN_ALL_LEVELS ||
    sleepLevel <= MIN_ALL_LEVELS) {
      return false;
    }
    return true;
  }

  public void depleteLevels(){
    playLevel--;
    foodLevel--;
    sleepLevel--;
  }

  public void play(){
    if (playLevel >= MAX_PLAY_LEVEL){
      throw new UnsupportedOperationException("You cannot play with monster anymore!");
    }
    playLevel++;
  }

  public void sleep(){
     if (sleepLevel >= MAX_SLEEP_LEVEL){
       throw new UnsupportedOperationException("You cannot make your monster sleep anymore!");
     }
     sleepLevel++;
   }

  public void feed(){
    if(foodLevel >= MAX_FOOD_LEVEL) {
      throw new UnsupportedOperationException("You cannot feed your monster anymore you savage! They are bloated :( ");
    }
    foodLevel++;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO monsters (name, personId, birthday) values (:name, :personId, now())";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("personId", this.personId)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Monster> all() {
    String sql = "SELECT * FROM monsters;";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Monster.class);
    }
  }

  public static Monster find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM monsters where id = :id";
      Monster monster = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Monster.class);
      return monster;
    }
  }

}
