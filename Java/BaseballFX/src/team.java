/*Mark Dubin
  6/12/21
  BaseballFX - Team Class*/

public class Team {
    private Pitcher ace;
    private Batter[] lineup;
    private String name;
    private int score, hits, errors, battingSpot = 0;
    private boolean isHome;

    //team constructor, taking in one Pitcher, Batter array of 9 Batters, team name, and home/away boolean
    public Team(Pitcher pit, Batter[] bats, String name, boolean site){
        this.ace = pit;
        this.lineup = bats;
        this.name = name;
        this.isHome = site;
    }

    //method used to construct Team
    public static Team buildTeam(String name, boolean site){
        int i;

        //Batter array of 9 used to generate entire lineup
        Batter[] bats = new Batter[9];
        Pitcher pit;

        //generate 9 Batters with i serving as defensive position/lineup order
        for(i = 2; i < 11; i++){
            bats[i-2] = Batter.genBat(i);
        }

        //generate Pitcher
        pit = Pitcher.genPit();

        //take Batters, Pitcher, Team name, and site boolean, and generate Team
        return new Team(pit, bats, name, site);
    }

    public Pitcher getAce(){
        return ace;
    }

    public Batter[] getLineup(){
        return lineup;
    }

    public String getTeamName(){
        return name;
    }

    public int getScore(){
        return score;
    }

    public void incScore(int num){
        score += num;
    }

    public boolean getIsHome(){
        return isHome;
    }

    public int getHits(){
        return hits;
    }

    public void incHits(){
        hits++;
    }

    public int getErrors(){
        return errors;
    }

    public void incErrors(){
        errors++;
    }

    public int getBattingSpot(){
        return battingSpot;
    }

    public void nextBatter(){
        battingSpot = (battingSpot + 1) % 9;
    }
}
