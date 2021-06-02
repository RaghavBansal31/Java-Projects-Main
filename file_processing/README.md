# File_Processing
External Sorting by improved Quicksort

You will implement an external sorting algorithm for binary data. The input data file will consist of many 4-byte records, with each record consisting of two 2-byte (short) integer values in the range 1 to 30,000. The first 2-byte field is the key value (used for sorting) and the second 2-byte field contains a data value. The input file is guaranteed to be a multiple of 4096 bytes. All I/O operations will be done on blocks of size 4096 bytes (i.e., 1024 logical records).

Warning: The data file is a binary file, not a text file.

Your job is to sort the file (in ascending order), using a modified version of Quicksort. The modification comes in the interaction between the Quicksort algorithm and the _le storing the data. The array being sorted will be the _le itself, rather than an array stored in memory. All accesses to the file will be mediated by a buffer pool. The buffer pool will store 4096-byte blocks (1024 records). The buffer pool will be organized using the Least Recently Used (LRU) replacement scheme.

Design Considerations:
The primary design concern for this project will be the interaction between the array as viewed by the Quicksort algorithm, and the physical representation of the array as implemented by the disk file mediated by the buffer pool. You should pay careful attention to the interface that you design for the buffer pool, since you will be using this again in Project 4. In essence, the disk file will be the array, and all accesses to the array from the Quicksort algorithm will be in the form of requests to the buffer pool for specific blocks of the file.

Invocation and I/O Files:
The program will be invoked from the command-line as:

java Quicksort <data-file-name> <numb-buffers> <stat-file-name>

The data file <data-file-name> is the file to be sorted. The sorting takes place in that file, so this program does modify the input data file. Be careful to keep a copy of the original when you do your testing. The parameter <numb-buffers> determines the number of buffers allocated for the buffer pool. This value will be in the range 1-20. The parameter <stat-file-name> is the name of a file that your program will generate to store runtime statistics; see below for more information. 

At the end of your program, the data file (on disk) should be in a sorted state. Do not forget to use buffers from your buffer pool as necessary at the end, or they will not update the file correctly. 

Be aware that performance does play an issue in the grading for this program. If your program takes significantly longer than it should, then it will be penalized. Note that we are not going to be checking exactly what blocks are swapped in and out of the buffer pool. Therefore, there is room for different approaches to tuning your Quicksort implementation as needed to meet the efficiency requirements.

In addition to sorting the data _le, you will generate and output some statistics about the execution of your program. Write these statistics to <stat-file-name>. Make sure your program
DOES NOT overwrite <stat-file-name> each time it is run; instead, have it append new statistics to the end of the _le. The information to write is as follows.

(a)	The name of the data _le being sorted.
(b)	The number of cache hits, or times your program found the data it needed in a buffer and did not have to go to the disk.
(c)	The number of disk reads, or times your program had to read a block of data from disk into a buffer.
(d)	The number of disk writes, or times your program had to write a block of data to disk from a buffer.
(e)	The time that your program took to execute the Quicksort. Put two calls to the standard Java timing method “System.currentTimeMillis()” in your program, one when you call the sort function, and one when you return from the sort function. This method returns a long value. The difference between the two values will be the total runtime in milliseconds. You should ONLY time the sort, and not the time to write the program output as described above.

