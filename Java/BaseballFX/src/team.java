public class team {
    private pitcher ace;
    private batter[] lineup;
    private String name;
    private int score, hits, errors, battingSpot = 0;
    private boolean isHome;

    //team constructor, taking in one pitcher, batter array of 9 batters, team name, and home/away boolean
    public team(pitcher pit, batter[] bats, String name, boolean site){
        this.ace = pit;
        this.lineup = bats;
        this.name = name;
        this.isHome = site;
    }

    public pitcher getAce(){
        return ace;
    }

    public batter[] getLineup(){
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
