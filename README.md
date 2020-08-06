# CSX42: Assignment 5
**Name:** Bhagwan Sanjay Deore
-----------------------------------------------------------------------

Following are the commands, and the instructions to run ANT on your project.


Note: build.xml is present in [textdecorators/src](./textdecorators/src/) folder.

## Instruction to clean:

```commandline
ant -buildfile textdecorators/src/build.xml clean
```

Description: It cleans up all the .class files that were generated when you
compiled your code.

## Instructions to compile:

```commandline
ant -buildfile textdecorators/src/build.xml all
```
The above command compiles your code and generates .class files inside the BUILD folder.

## Instructions to run:

```commandline
ant -buildfile textdecorators/src/build.xml run -Dinput="input.txt" -Dmisspelled="misspelled.txt" -Dkeywords="keywords.txt" -Doutput="output.txt"  -Ddebug="debug_log.txt"
```
Note: Arguments accept the absolute path of the files.


## Description:
**Data Structures Used:**

InputDetails: two 2D ArrayList instances. one for storing original words in input file for reference and another one for storing processed words by decorators. advantage: using this data structure we can avoid constantly using indexOf which comparatively costly. we can simply look up position of the word in reference array and let decorator work on same word retrieved from reference ArrayList.
 
MostFrequentWordDecorator: HashMap with words as keys and frequency of occurrence as value. this ensures that we don't traverse sentences more than once. 

SentenceDecorator, KeywordDecorator: HashSet populated with words from input files. with HashSet we can do constant time lookup, so, with these decorators we simply traverse the ArrayList and lookup the word in HashSet if found, apply the prefix and suffix and store it in resultBuffer.  


```Time Complexities:
n is the number of words in input file.
m is the number of words either in keywords or misspelled words files (depending on the decorator)

populating ArrayLists in inputDetails: O(n)
MostFrequentWordDecorator: O(3n) ~ O(n)
SentenceDecorator: to populate HashSet - O(m). to apply decorator - O(n) 
KeywordDecorator: to populate HashSet - O(m). to apply decorator - O(n) 

```
## Academic Honesty statement:

"I have done this assignment completely on my own. I have not copied
it, nor have I given my solution to anyone else. I understand that if
I am involved in plagiarism or cheating an official form will be
submitted to the Academic Honesty Committee of the Watson School to
determine the action that needs to be taken. "

Date:  July 30, 2020


