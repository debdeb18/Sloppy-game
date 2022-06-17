# Sloppy game for embedded hardware by Hanback

This repository contains our final code for this project, with purpose of storing for future references

There are one written document and a demo along with this project:
1. Report
2. Video demo

## Environtment setup
software and hardware:
```
IDE: Android Studio - jellybean (16.0)
h/w: Hanback board sm5-s4210
```
pre-install:
```
make sure to have
npm and git installed
```

## How to run
//coming soon apk file

## Description Detail

file structure and relation could be seen from `/app/src/main/AndroidManifest.xml` file 
or `ProjectStructure.file` for more user-friendly version

There are a total of 6 main activity (3 of them are per levels layout):
|Function|Java file|xml file|
|:---:|:---:|:---:|
|Home|SloppyActivity.java|activity_main.xml|
|Tutorial|How_to_play2.java|activity_how_to_play2.xml|
|Level Menu|LevelSelect2.java|activity_level_select2.xml|
|Level 1|LevelOne.java|activity_level_one.xml|
|Level 2|Level_two.java|activity_level_two.xml|
|Level 3|Level_three.java|activity_level_three.xml|

## TODO List

- [X] Home bar, in some activity provide back button that direct to Home
- [x] Home, consist of button to start game(lvl 1), tutorial, and level menu
- [x] Tutorial, consist of concise instruction on how to play
- [x] Level menu, shows available or under construction level, each button directing to the respective level
- [x] Level layout
  - [x] buttons (colored circle) that generates random sequence 
  - [x] score display, to keep track of user score each level
  - [x] lives, imageView of hearts that shows user's current live
  - [x] Game over screen, pop when "lives == 0" - shows try again or to Home
  - [x] Next Level, once user reach end of level

##### *possible future development*
- [ ] profile page
- [ ] highscore
