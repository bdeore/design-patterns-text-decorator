## Text Manipulations using Decorator Pattern

Following are the commands & the instructions to run the project using ANT.


Note: build.xml is present in [textdecorators/src](./textdecorators/src/) folder.

### Instruction to clean:

```commandline
ant -buildfile textdecorators/src/build.xml clean
```

Description: It cleans up all the .class files that were generated when you
compiled your code.

### Instructions to compile:

```commandline
ant -buildfile textdecorators/src/build.xml all
```
The above command compiles your code and generates .class files inside the BUILD folder.

### Instructions to run:

```commandline
ant -buildfile textdecorators/src/build.xml run -Dinput="input.txt" -Dmisspelled="misspelled.txt" -Dkeywords="keywords.txt" -Doutput="output.txt"  -Ddebug="debug_log.txt"
```
Note: Arguments accept the absolute path of the files.


### Description:
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

Date:  July 30, 2020


