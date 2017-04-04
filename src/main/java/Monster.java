public class Monster {

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

}
