import org.junit.*;
import static org.junit.Assert.*;

public class MonsterTest {
  @Test
  public void monster_instantiatesWithHalfFullPlayLevel() {
    Monster testMonster = new Monster("Bubbles", 1);
    assertEquals(testMonster.getPlayLevel(), (Monster.MAX_PLAY_LEVEL / 2));
  }

  @Test
  public void monster_instantiatesWithHalfFullSleepLevel(){
    Monster testMonster = new Monster("Bubbles", 1);
    assertEquals(testMonster.getSleepLevel(), (Monster.MAX_SLEEP_LEVEL / 2));
  }

  @Test
  public void monster_instantiatesWithHalfFullFoodLevel(){
    Monster testMonster = new Monster("Bubbles", 1);
    assertEquals(testMonster.getFoodLevel(), (Monster.MAX_FOOD_LEVEL / 2));
  }

  @Test
  public void isAlive_confirmsMonsterIsAliveIfAllLevelsAboveMinimum_true(){
   Monster testMonster = new Monster("Bubbles", 1);
   assertEquals(testMonster.isAlive(), true);
   }

 @Test
 public void depleteLevels_reducesAllLevels(){
  Monster testMonster = new Monster("Bubbles", 1);
  testMonster.depleteLevels();
  assertEquals(testMonster.getFoodLevel(), (Monster.MAX_FOOD_LEVEL / 2) - 1);
  assertEquals(testMonster.getSleepLevel(), (Monster.MAX_SLEEP_LEVEL / 2) - 1);
  assertEquals(testMonster.getPlayLevel(), (Monster.MAX_PLAY_LEVEL / 2) - 1);
  }

  @Test
  public void play_increasesMonsterPlayLevel(){
    Monster testMonster = new Monster("Bubbles", 1);
    testMonster.play();
    assertTrue(testMonster.getPlayLevel() > (Monster.MAX_PLAY_LEVEL / 2));
  }

  @Test
  public void sleep_increasesMonsterSleepLevel(){
    Monster testMonster = new Monster("Bubbles", 1);
    testMonster.sleep();
    assertTrue(testMonster.getSleepLevel() > (Monster.MAX_SLEEP_LEVEL / 2));
  }

  @Test
  public void feed_increasesMonsterFoodLevel(){
    Monster testMonster = new Monster("Bubbles", 1);
    testMonster.feed();
    assertTrue(testMonster.getFoodLevel() > (Monster.MAX_FOOD_LEVEL / 2));
  }

  @Test
  public void monster_foodLevelCannotGoBeyondMaxValue(){
    Monster testMonster = new Monster("Bubbles", 1);
    for(int i = Monster.MIN_ALL_LEVELS; i <= (Monster.MAX_FOOD_LEVEL + 2); i++){
      try {
        testMonster.feed();
      } catch (UnsupportedOperationException exception) { }
    }
    assertTrue(testMonster.getFoodLevel() <= Monster.MAX_FOOD_LEVEL);
  }

  @Test
  public void monster_playLevelCannotGoBeyondMaxValue(){
    Monster testMonster = new Monster("Bubbles", 1);
    for(int i = Monster.MIN_ALL_LEVELS; i <= (Monster.MAX_PLAY_LEVEL); i++){
      try {
        testMonster.play();
      } catch (UnsupportedOperationException exception){ }
    }
    assertTrue(testMonster.getPlayLevel() <= Monster.MAX_PLAY_LEVEL);
  }

  @Test
  public void monster_sleepLevelCannotGoBeyondMaxValue(){
    Monster testMonster = new Monster("Bubbles", 1);
    for(int i = Monster.MIN_ALL_LEVELS; i <= (Monster.MAX_SLEEP_LEVEL); i++){
      try {
        testMonster.sleep();
      } catch (UnsupportedOperationException exception){ }
    }
    assertTrue(testMonster.getSleepLevel() <= Monster.MAX_SLEEP_LEVEL);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void feed_throwsExceptionIfFoodLevelIsAtMaxValue(){
    Monster testMonster = new Monster("Bubbles", 1);
    for(int i = Monster.MIN_ALL_LEVELS; i <= (Monster.MAX_FOOD_LEVEL); i++){
      testMonster.feed();
    }
  }

  @Test(expected = UnsupportedOperationException.class)
  public void play_throwsExceptionIfPlayLevelIsAtMaxValue(){
    Monster testMonster = new Monster("Bubbles", 1);
    for(int i = Monster.MIN_ALL_LEVELS; i <= (Monster.MAX_PLAY_LEVEL); i++){
      testMonster.play();
    }
  }

  @Test(expected = UnsupportedOperationException.class)
  public void sleep_throwsExceptionIfSleepLevelIsAtMaxValue(){
    Monster testMonster = new Monster("Bubbles", 1);
    for(int i = Monster.MIN_ALL_LEVELS; i <= (Monster.MAX_SLEEP_LEVEL); i++){
      testMonster.sleep();
    }
  }

}
