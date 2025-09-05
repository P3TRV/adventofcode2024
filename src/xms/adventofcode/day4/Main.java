package xms.adventofcode.day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/*********
 * Day 4 *
 ********* 

https://adventofcode.com/2024/day/4

 
--- Day 4: Ceres Search ---
"Looks like the Chief's not here. Next!" One of The Historians pulls out a device and pushes the only button on it. After a brief flash, you recognize the interior of the Ceres monitoring station!

As the search for the Chief continues, a small Elf who lives on the station tugs on your shirt; she'd like to know if you could help her with her word search (your puzzle input). She only has to find one word: XMAS.

This word search allows words to be horizontal, vertical, diagonal, written backwards, or even overlapping other words. It's a little unusual, though, as you don't merely need to find one instance of XMAS - you need to find all of them. Here are a few ways XMAS might appear, where irrelevant characters have been replaced with .:


..X...
.SAMX.
.A..A.
XMAS.S
.X....
The actual word search will be full of letters instead. For example:

MMMSXXMASM
MSAMXMSMSA
AMXSXMAAMM
MSAMASMSMX
XMASAMXAMM
XXAMMXXAMA
SMSMSASXSS
SAXAMASAAA
MAMMMXMMMM
MXMXAXMASX
In this word search, XMAS occurs a total of 18 times; here's the same word search again, but where letters not involved in any XMAS have been replaced with .:

....XXMAS.
.SAMXMS...
...S..A...
..A.A.MS.X
XMASAMX.MM
X.....XA.A
S.S.S.S.SS
.A.A.A.A.A
..M.M.M.MM
.X.X.XMASX
Take a look at the little Elf's word search. How many times does XMAS appear?

Your puzzle answer was 2462.

The first half of this puzzle is complete! It provides one gold star: *

--- Part Two ---
The Elf looks quizzically at you. Did you misunderstand the assignment?

Looking for the instructions, you flip over the word search to find that this isn't actually an XMAS puzzle; it's an X-MAS puzzle in which you're supposed to find two MAS in the shape of an X. One way to achieve that is like this:

M.S
.A.
M.S
Irrelevant characters have again been replaced with . in the above diagram. Within the X, each MAS can be written forwards or backwards.

Here's the same example from before, but this time all of the X-MASes have been kept instead:

.M.S......
..A..MSMS.
.M.S.MAA..
..A.ASMSM.
.M.S.M....
..........
S.S.S.S.S.
.A.A.A.A..
M.M.M.M.M.
..........
In this example, an X-MAS appears 9 times.

Flip the word search from the instructions back over to the word search side and try again. How many times does an X-MAS appear?


 */
public class Main {

	public static void main(String[] args) {
		System.out.println("Current path "+Paths.get(".").toAbsolutePath());
		long sumPart1 = 0;
		try {
			List<char[]> array = Files.lines(Paths.get("input-day4.txt")).map(l -> l.toCharArray()).collect(Collectors.toList());
			sumPart1 += countHorizontalXMAS(array);
			sumPart1 += countVericalXMAS(array);
			sumPart1 += countDiagonalXMAS(array);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("*** Part 1 ***");
		System.out.println("Total sum of XMAS horizontal is " + sumPart1);
	}

	private static long countHorizontalXMAS(List<char[]> array) {
		int cnt = 0;
		for(int r=0; r<array.size(); r++) {			
			char[] arrrow = array.get(r);
			int length = arrrow.length;
			for(int c=0; c<length; c++) {				
				if ('X' ==arrrow[c]
						&& (c+1 < length && 'M' == arrrow[c+1]) 
						&& (c+2 < length && 'A' == arrrow[c+2])
						&& (c+3 < length && 'S' == arrrow[c+3]))
				{
					cnt++;
				}
				if ('X' == arrrow[length-1-c]
						&& (length-1-c-1 >= 0 && 'M' == arrrow[length-1-c-1]) 
						&& (length-1-c-2 >= 0 && 'A' == arrrow[length-1-c-2])
						&& (length-1-c-3 >= 0 && 'S' == arrrow[length-1-c-3]))
				{
					cnt++;
				}
			}
		}
		return cnt;
	}

	private static long countVericalXMAS(List<char[]> array) {
		int cnt = 0;
		int length = array.size();
		for(int c=0; c<array.get(0).length; c++) {
			for(int r=0; r<array.size(); r++) {			
				if ('X' == array.get(r)[c]
						&& (r+1 < length && 'M' == array.get(r+1)[c]) 
						&& (r+2 < length && 'A' == array.get(r+2)[c])
						&& (r+3 < length && 'S' ==array.get(r+3)[c]))
				{
					cnt++;
				}
				if ('X' == array.get(length-1-r)[c]
						&& (length-1-r-1 >= 0 && 'M' == array.get(length-1-r-1)[c]) 
						&& (length-1-r-2 >= 0 && 'A' == array.get(length-1-r-2)[c])
						&& (length-1-r-3 >= 0 && 'S' ==array.get(length-1-r-3)[c]))
				{
					cnt++;
				}
			}			
		}
		return cnt;
	}
	
	private static long countDiagonalXMAS(List<char[]> array) {
		int cnt = 0;
		int length = array.size();
		int lengthC = array.get(0).length;
		for(int c=0; c<lengthC; c++) {
			for(int r=0; r<array.size(); r++) {			
				if ('X' == array.get(r)[c]
						&& (r+1 < length && c+1 < lengthC && 'M' == array.get(r+1)[c+1]) 
						&& (r+2 < length && c+2 < lengthC && 'A' == array.get(r+2)[c+2])
						&& (r+3 < length && c+3 < lengthC && 'S' ==array.get(r+3)[c+3]))
				{
					cnt++;
				}
				if ('X' == array.get(length-1-r)[lengthC-1-c]
						&& (length-1-r-1 >= 0 && lengthC-1-c-1 >= 0 && 'M' == array.get(length-1-r-1)[lengthC-1-c-1]) 
						&& (length-1-r-2 >= 0 && lengthC-1-c-2 >= 0 && 'A' == array.get(length-1-r-2)[lengthC-1-c-2])
						&& (length-1-r-3 >= 0 && lengthC-1-c-3 >= 0 && 'S' ==array.get(length-1-r-3)[lengthC-1-c-3]))
				{
					cnt++;
				}
				if ('X' == array.get(length-1-r)[c]
						&& (length-1-r-1 >= 0 && c+1 < lengthC && 'M' == array.get(length-1-r-1)[c+1]) 
						&& (length-1-r-2 >= 0 && c+2 < lengthC && 'A' == array.get(length-1-r-2)[c+2]) 
						&& (length-1-r-3 >= 0 && c+3 < lengthC && 'S' ==array.get(length-1-r-3)[c+3])) 
				{
					cnt++;
				}
				if ('X' == array.get(r)[lengthC-1-c]
						&& (r+1 < length && lengthC-1-c-1 >= 0 && 'M' == array.get(r+1)[lengthC-1-c-1]) 
						&& (r+2 < length && lengthC-1-c-2 >= 0 && 'A' == array.get(r+2)[lengthC-1-c-2])
						&& (r+3 < length && lengthC-1-c-3 >= 0 && 'S' ==array.get(r+3)[lengthC-1-c-3]))
				{
					cnt++;
				}
			}			
		}
		return cnt;
	}
	
	
	
}
