
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

- Scroll Box
    - fixed/dynamic size
    - both axis scrolling(smth like ctrl+scroll for x)
- text box
    - both multiline and single line
- drop down
- make sticky use a general solution
- let developer create UI from a json file(not 100% sure abt this yet since you cant create interactive elements like this)
- make a wiki with a common task page (examples of commonly made things)
- JEI compatibility(use easyGui menus for it)

more planned features int the future. but that's all i can think of off the top of my head

Installation
=
this uses github packages so a little more work is needed sadly.<br>
i will swap to maven central at some point

```
dependencies {
    implementation "net.lucent.easygui:easy_gui:0.0.1"
}
```
replace 0.0.1 with whichever version you wish to use

to use github packages you must own a github account
```
repositories {
  maven {
    name = "GitHubPackages"
    url = uri("https://maven.pkg.github.com/oliverhesse/easygui")
    credentials {
      username = project.findProperty("gpr.user") ?: System.getenv("GITHUBUSERNAME")
      password = project.findProperty("gpr.key") ?: System.getenv("TOKEN")
    } 
  }
  mavenCentral()
}
```

just make sure to setup your environment variables properly and it should be sorted


feel free to either contact me directly or open an issue if there are any problems
