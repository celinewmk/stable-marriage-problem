# Solving the stable marriage problem using the Gale Shapley algorithm

## Instructions
1. Start terminal and `cd` into the directory 
2. Compile all the java files 
3. run `java GaleShapley`. 
4. Enter the name of the text file

## Description
The goal of this project is to solve the stable marriage problem using the Gale Shapley algorithm. 
In our case, the problem is to associate students with employers. In order to simplify this problem we will consider the case where n employers wish to hire n students; each employer hiring only one student. Each employer must therefore rank the students in order of preference (from 1 to n) and the students do the same. The solution we have matches each employer with a student so that everyone is as satisfied as possible.

The implementation is object-oriented programming therefore it will contain different classes such as:
- `GaleShapley`: the main application which executes the algorithm
- `Paire`: implements the interface `Comparable` and compares 2 pairs

Please note that the language used when running the application is french.

### Example
We have 2 lists of the same size (both numbered from 0 to n-1)

- a list of employers 
```
0. Thales
1. Canada Post
2. Cisco
```
- a list of students
```
0. Olivia
1. Jackson
2. Sophia
```

Each employer and students need to rank by order of preference, as shown below:
- Preference of employers
```
0. Thales: 0.Olivia, 1.Jackson, 2.Sophia
1. Canada Post: 2. Sophia, 1.Jackson, 0.Olivia
2. Cisco: 0.Olivia, 2.Sophia, 1.Jackson
```
We read for example that Canada Post has ranked the student Sophia 1st, then Jackson and then Olivia.
- Preference of students
```
0. Olivia: 0.Thales, 1.Canada Post, 2.Cisco
1. Jackson: 0.Thales, 1.Canada Post, 2.Cisco
2. Sophia: 2.Cisco, 0.Thales, 1.Canada Post
```
We read for example that Olivia has ranked employer Thales 1st, then Canada Post and then Cisco.

A solution to this stable marriage problem is:
```
Match 0: Thales - Olivia 
Match 1: Canada Post - Jackson 
Match 2: Cisco - Sophia
```

The preferences are given in the `test_N.txt` files.

## Gale Shapley algorithm
<p align='center'>
<img width="622" alt="Screenshot 2022-04-22 at 00 24 13" src="https://user-images.githubusercontent.com/71091659/164601986-2c2b473d-59cb-4a32-bbd2-b60c8e4922e9.png">
</p>
Link for more information: https://en.wikipedia.org/wiki/Gale%E2%80%93Shapley_algorithm

## Input file format
The first number in this file is the number of employers and students. Then there's the list of employers and students. Finally, the preferences are given in a matrix of pairs(employer ranking, student ranking). Each row corresponds to an employer and each column corresponds to a student. So for the example given above, this file would be:
```
3
Thales 
Canada Post 
Cisco 
Olivia 
Jackson 
Sophia
1,1 2,1 3,2
3,2 2,2 1,3
1,3 3,3 2,1
```
___________________________________________________________________
   
From CSI2510 (Data Structures and Algorithms) Course - Stable Marriage project
