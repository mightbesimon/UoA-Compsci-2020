# UoA CompSci 2020 #

<u>Semester 1</u>
- [COMPSCI 130](compsci130/README.md)
- [COMPSCI 210](compsci210/README.md)
- COVID-19

*<u>Semester 2</u>*
- [to be confirmed]

## COMPSCI 130 ##

very basic python (idk how people fail this course)
- class
- stack
- queue
- linked lists
- binary tree

### Prerequisite ###

[Python 3](https://www.python.org/downloads/) - the language used in this course
> I suggest Python 3.7.6 (3.8 still feels dodgy as heck)
> lots of packages are not yet supported in 3.8 (i.e. tensorflow)

### Setup ###

> don't like IDLE or vscode  
> I prefer terminal and sublime

my terminal setup:
```bash
$ echo "alias python=python3" >> .bash_profile
```

my sublime setup:
```bash
$ cd ~/Library/Application\ Support/Sublime\ Text\ 3/Packages/User
$ touch Python\ 3.sublime-build
$ open -t Python\ 3.sublime-build
```
paste this in and save
```json
{
	"cmd":			["/usr/local/bin/python3", "-u", "$file"],
	"file_regex":	"^[ ]File \"(...?)\", line ([0-9]*)",
	"selector":		"source.python"
}
```

### [descriptive title] ###

[some sort of description goes here]

## COMPSCI 210 ##

- LC3 assembly language
- basic C programming (already covered in Engineering)

### Prerequisites ###

[LC3 simulator](compsci210/LC3/lc3sim.jar) `lc3sim.jar`  
[LC3 Operating System](compsci210/LC3/source/lc3os.asm) `lc3os.asm`  
[Java](https://www.oracle.com/java/technologies/javase-jdk8-downloads.html) to run the simulator
> you can get either JRE or JDK, but strongly I suggest JDK 8

GCC for compiling C

### [descriptive title] ###

[some sort of description goes here]

## Authors ##

- **Simon** - *buy my merch* - [mightbesimon](https://github.com/mightbesimon/)


## License ##

do_whatever_the_heck (these are useless anyway)

## Acknowledgments ##

- **these are just my sample codes, if you misuse them its not my problem**
- Simon misses Engineering very much but CS is fun and easy for him
