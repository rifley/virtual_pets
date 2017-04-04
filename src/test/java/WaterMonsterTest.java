import org.junit.*;
import org.sql2o.*;
import static org.junit.Assert.*;
import java.sql.Timestamp;
import java.util.Date;
import java.text.DateFormat;

public class WaterMonsterTest {
  @Test
  public void monster_instantiatesWithHalfFullPlayLevel() {
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    assertEquals(testWaterMonster.getPlayLevel(), (WaterMonster.MAX_PLAY_LEVEL / 2));
  }

  @Test
  public void monster_instantiatesWithHalfFullSleepLevel(){
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    assertEquals(testWaterMonster.getSleepLevel(), (WaterMonster.MAX_SLEEP_LEVEL / 2));
  }

  @Test
  public void monster_instantiatesWithHalfFullFoodLevel(){
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    assertEquals(testWaterMonster.getFoodLevel(), (WaterMonster.MAX_FOOD_LEVEL / 2));
  }

  @Test
  public void waterMonster_instantiatesWithHalfFullWaterLevel(){
    WaterMonster testWaterMonster = new WaterMonster("Drippy", 1);
    assertEquals(testWaterMonster.getWaterLevel(), (WaterMonster.MAX_WATER_LEVEL / 2));
  }

  @Test
  public void isAlive_confirmsWaterMonsterIsAliveIfAllLevelsAboveMinimum_true(){
   WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
   assertEquals(testWaterMonster.isAlive(), true);
   }

 @Test
 public void depleteLevels_reducesAllLevels(){
  WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
  testWaterMonster.depleteLevels();
  assertEquals(testWaterMonster.getFoodLevel(), (WaterMonster.MAX_FOOD_LEVEL / 2) - 1);
  assertEquals(testWaterMonster.getSleepLevel(), (WaterMonster.MAX_SLEEP_LEVEL / 2) - 1);
  assertEquals(testWaterMonster.getPlayLevel(), (WaterMonster.MAX_PLAY_LEVEL / 2) - 1);
  assertEquals(testWaterMonster.getWaterLevel(), (WaterMonster.MAX_WATER_LEVEL /2) - 1);
  }

  @Test
  public void play_increasesWaterMonsterPlayLevel(){
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    testWaterMonster.play();
    assertTrue(testWaterMonster.getPlayLevel() > (WaterMonster.MAX_PLAY_LEVEL / 2));
  }

  @Test
  public void sleep_increasesWaterMonsterSleepLevel(){
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    testWaterMonster.sleep();
    assertTrue(testWaterMonster.getSleepLevel() > (WaterMonster.MAX_SLEEP_LEVEL / 2));
  }

  @Test
  public void feed_increasesWaterMonsterFoodLevel(){
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    testWaterMonster.feed();
    assertTrue(testWaterMonster.getFoodLevel() > (WaterMonster.MAX_FOOD_LEVEL / 2));
  }

  @Test
  public void water_increasesWaterMonsterWaterLevel(){
    WaterMonster testWaterMonster = new WaterMonster("Drippy", 1);
    testWaterMonster.water();
    assertTrue(testWaterMonster.getWaterLevel() > (WaterMonster.MAX_WATER_LEVEL / 2));
  }

  @Test
  public void monster_foodLevelCannotGoBeyondMaxValue(){
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    for(int i = WaterMonster.MIN_ALL_LEVELS; i <= (WaterMonster.MAX_FOOD_LEVEL + 2); i++){
      try {
        testWaterMonster.feed();
      } catch (UnsupportedOperationException exception) { }
    }
    assertTrue(testWaterMonster.getFoodLevel() <= WaterMonster.MAX_FOOD_LEVEL);
  }

  @Test
  public void monster_playLevelCannotGoBeyondMaxValue(){
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    for(int i = WaterMonster.MIN_ALL_LEVELS; i <= (WaterMonster.MAX_PLAY_LEVEL); i++){
      try {
        testWaterMonster.play();
      } catch (UnsupportedOperationException exception){ }
    }
    assertTrue(testWaterMonster.getPlayLevel() <= WaterMonster.MAX_PLAY_LEVEL);
  }

  @Test
  public void monster_sleepLevelCannotGoBeyondMaxValue(){
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    for(int i = WaterMonster.MIN_ALL_LEVELS; i <= (WaterMonster.MAX_SLEEP_LEVEL); i++){
      try {
        testWaterMonster.sleep();
      } catch (UnsupportedOperationException exception){ }
    }
    assertTrue(testWaterMonster.getSleepLevel() <= WaterMonster.MAX_SLEEP_LEVEL);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void water_throwsExceptionIfWaterLevelIsAtMaxValue(){
    WaterMonster testWaterMonster = new WaterMonster("Drippy", 1);
    for(int i = WaterMonster.MIN_ALL_LEVELS; i <= (WaterMonster.MAX_WATER_LEVEL); i++){
      testWaterMonster.water();
    }
  }


  @Test(expected = UnsupportedOperationException.class)
  public void feed_throwsExceptionIfFoodLevelIsAtMaxValue(){
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    for(int i = WaterMonster.MIN_ALL_LEVELS; i <= (WaterMonster.MAX_FOOD_LEVEL); i++){
      testWaterMonster.feed();
    }
  }

  @Test(expected = UnsupportedOperationException.class)
  public void play_throwsExceptionIfPlayLevelIsAtMaxValue(){
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    for(int i = WaterMonster.MIN_ALL_LEVELS; i <= (WaterMonster.MAX_PLAY_LEVEL); i++){
      testWaterMonster.play();
    }
  }

  @Test(expected = UnsupportedOperationException.class)
  public void sleep_throwsExceptionIfSleepLevelIsAtMaxValue(){
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    for(int i = WaterMonster.MIN_ALL_LEVELS; i <= (WaterMonster.MAX_SLEEP_LEVEL); i++){
      testWaterMonster.sleep();
    }
  }

  @Test
  public void save_recordsTimeOfCreationInDatabase() {
    WaterMonster testWaterMonster = new WaterMonster("Eugene", 1);
    testWaterMonster.save();
    Timestamp savedWaterMonsterBirthday = WaterMonster.find(testWaterMonster.getId()).getBirthday();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(rightNow.getDay(), savedWaterMonsterBirthday.getDay());
  }

  @Test
  public void sleep_recordsTimeLastSleptInDatabase() {
    WaterMonster testWaterMonster = new WaterMonster("Mr. F", 1);
    testWaterMonster.save();
    testWaterMonster.sleep();
    Timestamp savedWaterMonsterLastSlept = WaterMonster.find(testWaterMonster.getId()).getLastSlept();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(savedWaterMonsterLastSlept));
  }

  @Test
  public void feed_recordsTimeLastAteInDatabase() {
    WaterMonster testWaterMonster = new WaterMonster("Gretchen", 1);
    testWaterMonster.save();
    testWaterMonster.feed();
    Timestamp savedWaterMonsterLastAte = WaterMonster.find(testWaterMonster.getId()).getLastAte();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(savedWaterMonsterLastAte));
  }

  @Test
  public void play_recordsTimeLastPlayedInDatabase() {
    WaterMonster testWaterMonster = new WaterMonster("Brian", 1);
    testWaterMonster.save();
    testWaterMonster.play();
    Timestamp savedWaterMonsterLastPlayed = WaterMonster.find(testWaterMonster.getId()).getLastPlayed();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(savedWaterMonsterLastPlayed));
  }

  @Test
  public void timer_executesDepleteLevelsMethod() {
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    int firstPlayLevel = testWaterMonster.getPlayLevel();
    testWaterMonster.startTimer();
    try {
      Thread.sleep(6000);
    } catch (InterruptedException exception){}
    int secondPlayLevel = testWaterMonster.getPlayLevel();
    assertTrue(firstPlayLevel > secondPlayLevel);
  }

  @Test
  public void timer_haltsAfterWaterMonsterDies() {
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    testWaterMonster.startTimer();
    try {
      Thread.sleep(6000);
    } catch (InterruptedException exception){}
    assertFalse(testWaterMonster.isAlive());
    assertTrue(testWaterMonster.getFoodLevel() >= 0);
  }

}
