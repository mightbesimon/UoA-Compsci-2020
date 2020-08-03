# UoA CompSci 2020 #

*<u>Semester 1</u>*

- [COMPSCI 130](#compsci-130)
- [COMPSCI 210](#compsci-210)
- [COMPSCI 215](#compsci-215)
- COVID-19

*<u>Semester 2</u>*

- [COMPSCI 230](#compsci-230)
- [to be confirmed]

## COMPSCI 130 ##

basic Python, I didn't like this class  
but it was easy

check out the [assignment submissions](https://github.com/mightbesimon/Uoa-CompSci-2020/blob/master/130%20assignment/Ass.py),  
really well commented, they're just beautiful

### Prerequisite ###

[Python 3](https://www.python.org/downloads/) - the language used in this course
```bash
$ python3 --version
Python 3.7.6
```

### Setup ###

> terminal + sublime, personally  
> any more is just too much

my bash alias setup:
```bash
$ echo "alias python=python3" >> .bash_profile
```

my sublime setup:  
save as `~/Library/Application\ Support/Sublime\ Text\ 3/Packages/User/Python\ 3.sublime-build`
```json
{
	"cmd":			["/usr/local/bin/python3", "-u", "$file"],
	"file_regex":	"^\\s*File \"(...*?)\", line ([0-9]*)",
	"selector":		"source.python"
}
```

## COMPSCI 210 ##

- LC3 assembly language
- advanced C programming (some were covered in Engineering)

### Prerequisites ###

[LC3 simulator](https://github.com/mightbesimon/Uoa-CompSci-2020/blob/master/LC3%20assembly/LC3sim.jar) `lc3sim.jar`  
[LC3 Operating System](https://github.com/mightbesimon/Uoa-CompSci-2020/blob/master/LC3%20assembly/source/lc3os.asm) `lc3os.asm`  
[Java](https://www.oracle.com/java/technologies/javase-jdk8-downloads.html) to run the simulator
> JRE or JDK, either are fine

GCC for compiling C

### Assembling ###

to build an object file  
in the LC3 simulator command line:
```
as program.asm
```
this should make an object file of the same name `program.obj`

to load OS and program into the simulator:
```
reset
load lc3os.obj
load program.obj
load data_to_use.obj
```
normally this is enough and can then run the program  
by clicking "Continue" at the top

```
set R2 x3000
```
sets register 2 to value x3000, this is needed in some programs  
that starts with `.ORIGIN x3000` without OS

### Compiling C ###

##### Sublime #####
Cmd+B to build and run without args in sublime
> can't run arguments  
> can't take console inputs (which you shouldn't in C anyways)

##### Console ( my custom function :D ) #####
append to `.bash_profile`
```bash
function compile() {
	if [[ $1 == *.c ]]; then
		gcc $1 -o ${1%.*}
	fi
}
```

to compile, run
```bash
$ compile program.c
$ ./program arg1 arg2
```

### SSH Server Access ###

to login
```bash
$ ssh username@login.cs.auckland.ac.nz
```
then enter the password

## COMPSCI 215 ##

these are just some scripts I wrote to aid breaking encrytions and steganographs
- Feistel cipher
- XOR cipher
- LSB stegano

### Use ###

Import functions as needed  
example:
```
from xor_hex import *
ciphertext = xor_hex('89AB', 'DE01')
```

## COMPSCI 230 ##

[course description]

### Prerequisites ###

[Java JDK](https://www.oracle.com/java/technologies/javase-jdk13-downloads.html) - the language used in this course

> JRE is only the runtime environment  
> to code java you need JDK

```bash
$ java --version
java 13.0.2 2020-01-14
Java(TM) SE Runtime Environment (build 13.0.2+8)
Java HotSpot(TM) 64-Bit Server VM (build 13.0.2+8, mixed mode, sharing)
```

### Setup ###

> once again  
> terminal + sublime  
> I dislike IDEs such as Eclipse. Hate the suggestions, I don't need to be told what to do

##### Bash Aliases and Functions #####
[My own compiling functions :D](https://gist.github.com/mightbesimon/dcf81e22fab277e478ac4ab093c62d77)  
append to `.bash_profile`

##### Command Line #####
```bash
$ javac classname.c
$ javac classname.c -d build/	# puts class files inside a build folder
															# clean and organised
$ java classname
$ java -cp build/ classname		# run classname in the specified classpath
															# if built into a folder from the previous step
$ jar cvfe classname.jar classname -C build/ .
															# build a .jar archive
$ java -jar classname.jar
```

##### Sublime #####
compile and run:  
save as `~/Library/Application\ Support/Sublime\ Text\ 3/Packages/User/JavaBuild.sublime-build`

```json
{
	"cmd":			["javac -d build/ $file && java -cp build/ $file_base_name"],
	"shell":		true,
	"file_regex":	"^(...*?):([0-9]*):?([0-9]*)",
	"selector":		"source.java"
}
```

compile to .jar:  
save as `~/Library/Application\ Support/Sublime\ Text\ 3/Packages/User/Jar.sublime-build`
```json
{
	"cmd":			["javac $file -d build/ && jar cvfe $file_base_name.jar $file_base_name -C build/ . && java -jar $file_base_name.jar"],
	"shell":		true,
	"file_regex":	"^(...*?):([0-9]*):?([0-9]*)",
	"selector":		"source.java"
}
```

### [descriptive title] ###

[some sort of description goes here]

## Authors ##

- **Simon** - *buy my merch* - [mightbesimon](https://github.com/mightbesimon/)

Shoutout to Damir, Mano and everyone in Uoa Compsci 2020

## License ##

use_to_hire_me_only

## Acknowledgments ##

- **these are just my sample codes, if you misuse them its not my problem**
- Simon misses Engineering very much but CS is fun and easy for him
- shout out to 6 weeks lockdown
- thanks Damir, Mano and all instructors
