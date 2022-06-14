package com.hanback.sloppy;

public class PlayerModal {
    // variables for our player account,
    // description, score and time, id.
    //dbHandler.addNewCourse(playerName, highestlvl, lvlTime, lvlDescription);
    private String playerName;
    private String highestlvl;
    private String lvlTime;
    private String lvlDescription;
    private int id;

    // creating getter and setter methods
    public String getplayerName() {
        return playerName;
    }

    public void setplayerName(String playerName) {
        this.playerName = playerName;
    }

    public String gethighestlvl() {
        return highestlvl;
    }

    public void sethighestlvl(String highestlvl) {
        this.highestlvl = highestlvl;
    }

    public String getlvlTime() {
        return lvlTime;
    }

    public void setlvlTime(String lvlTime) {
        this.lvlTime = lvlTime;
    }

    public String getlvlDescription() {
        return lvlDescription;
    }

    public void setlvlDescription(String lvlDescription) {
        this.lvlDescription = lvlDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // constructor
    public PlayerModal(String playerName, String highestlvl, String lvlTime, String lvlDescription) {
        this.playerName = playerName;
        this.highestlvl = highestlvl;
        this.lvlTime = lvlTime;
        this.lvlDescription = lvlDescription;
    }
}
