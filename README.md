
Easy Gui
=======

a simple library mod meant to make gui slightly easier to make

documentation is still in progress please bare with me

Links
=
[Getting Started](https://github.com/OliverHesse/EasyGui/wiki)<br>
[CurseForge](https://www.curseforge.com/minecraft/mc-mods/easygui)

Planned Features
=
- Drop down menu
- build Ui from either json or markup(not sure which yet)
- make a wiki with a common task page (examples of commonly made things)
- JEI compatibility(use easyGui menus for it)

more planned features int the future. but that's all i can think of off the top of my head

Installation
=
this uses github packages so a little more work is needed sadly.<br>
i will swap to maven central at some point

```
dependencies {
    implementation "net.lucent.easygui:easy_gui_1.21.1:1.0.0"
}
```
replace 1.0.0 with whichever version you wish to use

to use github packages you must own a github account
```
repositories {
  maven {
    name = "GitHubPackages"
    url = uri("https://maven.pkg.github.com/oliverhesse/easygui")
    credentials {
      username = project.findProperty("gpr.user")
      password = project.findProperty("gpr.key") 
    } 
  }
  mavenCentral()
}
```
to set up your env variables make a gradle.properties files in your .gradle folder and add
```
gpr.user=USERNAME
gpr.key=TOKEN
```
just make sure to setup your environment variables properly and it should be sorted


feel free to either contact me directly or open an issue if there are any problems
