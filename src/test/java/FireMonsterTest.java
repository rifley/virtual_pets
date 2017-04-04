import org.junit.*;
import org.sql2o.*;
import static org.junit.Assert.*;
import java.sql.Timestamp;
import java.util.Date;
import java.text.DateFormat;

public class FireMonsterTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void fireMonster_instantiatesWithHalfFullPlayLevel() {
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    assertEquals(testFireMonster.getPlayLevel(), (FireMonster.MAX_PLAY_LEVEL / 2));
  }

  @Test
  public void fireMonster_instantiatesWithHalfFullSleepLevel(){
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    assertEquals(testFireMonster.getSleepLevel(), (FireMonster.MAX_SLEEP_LEVEL / 2));
  }

  @Test
  public void fireMonster_instantiatesWithHalfFullFoodLevel(){
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    assertEquals(testFireMonster.getFoodLevel(), (FireMonster.MAX_FOOD_LEVEL / 2));
  }

  @Test
  public void fireMonster_instantiatesWithHalfFullFireLevel() {
    FireMonster testFireMonster = new FireMonster("Smokey", 1);
    assertEquals(testFireMonster.getFireLevel(), (FireMonster.MAX_FIRE_LEVEL/2));
  }

  @Test
  public void isAlive_confirmsFireMonsterIsAliveIfAllLevelsAboveMinimum_true(){
   FireMonster testFireMonster = new FireMonster("Bubbles", 1);
   assertEquals(testFireMonster.isAlive(), true);
   }

 @Test
 public void depleteLevels_reducesAllLevels(){
  FireMonster testFireMonster = new FireMonster("Bubbles", 1);
  testFireMonster.depleteLevels();
  assertEquals(testFireMonster.getFoodLevel(), (FireMonster.MAX_FOOD_LEVEL / 2) - 1);
  assertEquals(testFireMonster.getSleepLevel(), (FireMonster.MAX_SLEEP_LEVEL / 2) - 1);
  assertEquals(testFireMonster.getPlayLevel(), (FireMonster.MAX_PLAY_LEVEL / 2) - 1);
  assertEquals(testFireMonster.getFireLevel(), (FireMonster.MAX_FIRE_LEVEL / 2) -1);
  }

  @Test
  public void play_increasesFireMonsterPlayLevel(){
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    testFireMonster.play();
    assertTrue(testFireMonster.getPlayLevel() > (FireMonster.MAX_PLAY_LEVEL / 2));
  }

  @Test
  public void sleep_increasesFireMonsterSleepLevel(){
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    testFireMonster.sleep();
    assertTrue(testFireMonster.getSleepLevel() > (FireMonster.MAX_SLEEP_LEVEL / 2));
  }

  @Test
  public void feed_increasesFireMonsterFoodLevel(){
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    testFireMonster.feed();
    assertTrue(testFireMonster.getFoodLevel() > (FireMonster.MAX_FOOD_LEVEL / 2));
  }

  @Test
  public void kindling_increasesFireMonsterFireLevel() {
    FireMonster testFireMonster = new FireMonster("SmokeMeister", 1);
    testFireMonster.kindling();
    assertTrue(testFireMonster.getFireLevel() > (FireMonster.MAX_FIRE_LEVEL / 2));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void kindling_throwsExceptionIfFireLevelIsAtMaxValue() {
    FireMonster testFireMonster = new FireMonster("Smokles", 1);
    for(int i = FireMonster.MIN_ALL_LEVELS; i <= (FireMonster.MAX_FIRE_LEVEL); i++) {
      testFireMonster.kindling();
    }
  }

  @Test
  public void fireMonster_foodLevelCannotGoBeyondMaxValue(){
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    for(int i = FireMonster.MIN_ALL_LEVELS; i <= (FireMonster.MAX_FOOD_LEVEL + 2); i++){
      try {
        testFireMonster.feed();
      } catch (UnsupportedOperationException exception) { }
    }
    assertTrue(testFireMonster.getFoodLevel() <= FireMonster.MAX_FOOD_LEVEL);
  }

  @Test
  public void fireMonster_playLevelCannotGoBeyondMaxValue(){
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    for(int i = FireMonster.MIN_ALL_LEVELS; i <= (FireMonster.MAX_PLAY_LEVEL); i++){
      try {
        testFireMonster.play();
      } catch (UnsupportedOperationException exception){ }
    }
    assertTrue(testFireMonster.getPlayLevel() <= FireMonster.MAX_PLAY_LEVEL);
  }

  @Test
  public void fireMonster_sleepLevelCannotGoBeyondMaxValue(){
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    for(int i = FireMonster.MIN_ALL_LEVELS; i <= (FireMonster.MAX_SLEEP_LEVEL); i++){
      try {
        testFireMonster.sleep();
      } catch (UnsupportedOperationException exception){ }
    }
    assertTrue(testFireMonster.getSleepLevel() <= FireMonster.MAX_SLEEP_LEVEL);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void feed_throwsExceptionIfFoodLevelIsAtMaxValue(){
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    for(int i = FireMonster.MIN_ALL_LEVELS; i <= (FireMonster.MAX_FOOD_LEVEL); i++){
      testFireMonster.feed();
    }
  }

  @Test(expected = UnsupportedOperationException.class)
  public void play_throwsExceptionIfPlayLevelIsAtMaxValue(){
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    for(int i = FireMonster.MIN_ALL_LEVELS; i <= (FireMonster.MAX_PLAY_LEVEL); i++){
      testFireMonster.play();
    }
  }

  @Test(expected = UnsupportedOperationException.class)
  public void sleep_throwsExceptionIfSleepLevelIsAtMaxValue(){
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    for(int i = FireMonster.MIN_ALL_LEVELS; i <= (FireMonster.MAX_SLEEP_LEVEL); i++){
      testFireMonster.sleep();
    }
  }

  @Test
  public void save_recordsTimeOfCreationInDatabase() {
    FireMonster testFireMonster = new FireMonster("Eugene", 1);
    testFireMonster.save();
    Timestamp savedFireMonsterBirthday = FireMonster.find(testFireMonster.getId()).getBirthday();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(rightNow.getDay(), savedFireMonsterBirthday.getDay());
  }

  @Test
  public void sleep_recordsTimeLastSleptInDatabase() {
    FireMonster testFireMonster = new FireMonster("Mr. F", 1);
    testFireMonster.save();
    testFireMonster.sleep();
    Timestamp savedFireMonsterLastSlept = FireMonster.find(testFireMonster.getId()).getLastSlept();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(savedFireMonsterLastSlept));
  }

  @Test
  public void feed_recordsTimeLastAteInDatabase() {
    FireMonster testFireMonster = new FireMonster("Gretchen", 1);
    testFireMonster.save();
    testFireMonster.feed();
    Timestamp savedFireMonsterLastAte = FireMonster.find(testFireMonster.getId()).getLastAte();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(savedFireMonsterLastAte));
  }

  @Test
  public void play_recordsTimeLastPlayedInDatabase() {
    FireMonster testFireMonster = new FireMonster("Brian", 1);
    testFireMonster.save();
    testFireMonster.play();
    Timestamp savedFireMonsterLastPlayed = FireMonster.find(testFireMonster.getId()).getLastPlayed();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(savedFireMonsterLastPlayed));
  }

  @Test
  public void timer_executesDepleteLevelsMethod() {
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    int firstPlayLevel = testFireMonster.getPlayLevel();
    testFireMonster.startTimer();
    try {
      Thread.sleep(6000);
    } catch (InterruptedException exception){}
    int secondPlayLevel = testFireMonster.getPlayLevel();
    assertTrue(firstPlayLevel > secondPlayLevel);
  }

  @Test
  public void timer_haltsAfterFireMonsterDies() {
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    testFireMonster.startTimer();
    try {
      Thread.sleep(6000);
    } catch (InterruptedException exception){}
    assertFalse(testFireMonster.isAlive());
    assertTrue(testFireMonster.getFoodLevel() >= 0);
  }

}