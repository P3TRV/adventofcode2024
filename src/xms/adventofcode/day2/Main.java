package xms.adventofcode.day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


/*********
 * Day 2 *
 ********* 

https://adventofcode.com/2024/day/2

--- Day 2: Red-Nosed Reports ---
Fortunately, the first location The Historians want to search isn't a long walk from the Chief Historian's office.

While the Red-Nosed Reindeer nuclear fusion/fission plant appears to contain no sign of the Chief Historian, the engineers there run up to you as soon as they see you. Apparently, they still talk about the time Rudolph was saved through molecular synthesis from a single electron.

They're quick to add that - since you're already here - they'd really appreciate your help analyzing some unusual data from the Red-Nosed reactor. You turn to check if The Historians are waiting for you, but they seem to have already divided into groups that are currently searching every corner of the facility. You offer to help with the unusual data.

The unusual data (your puzzle input) consists of many reports, one report per line. Each report is a list of numbers called levels that are separated by spaces. For example:

7 6 4 2 1
1 2 7 8 9
9 7 6 2 1
1 3 2 4 5
8 6 4 4 1
1 3 6 7 9
This example data contains six reports each containing five levels.

The engineers are trying to figure out which reports are safe. The Red-Nosed reactor safety systems can only tolerate levels that are either gradually increasing or gradually decreasing. So, a report only counts as safe if both of the following are true:

The levels are either all increasing or all decreasing.
Any two adjacent levels differ by at least one and at most three.
In the example above, the reports can be found safe or unsafe by checking those rules:

7 6 4 2 1: Safe because the levels are all decreasing by 1 or 2.
1 2 7 8 9: Unsafe because 2 7 is an increase of 5.
9 7 6 2 1: Unsafe because 6 2 is a decrease of 4.
1 3 2 4 5: Unsafe because 1 3 is increasing but 3 2 is decreasing.
8 6 4 4 1: Unsafe because 4 4 is neither an increase or a decrease.
1 3 6 7 9: Safe because the levels are all increasing by 1, 2, or 3.
So, in this example, 2 reports are safe.

Analyze the unusual data from the engineers. How many reports are safe?

Your puzzle answer was 326.

The first half of this puzzle is complete! It provides one gold star: *

--- Part Two ---
The engineers are surprised by the low number of safe reports until they realize they forgot to tell you about the Problem Dampener.

The Problem Dampener is a reactor-mounted module that lets the reactor safety systems tolerate a single bad level in what would otherwise be a safe report. It's like the bad level never happened!

Now, the same rules apply as before, except if removing a single level from an unsafe report would make it safe, the report instead counts as safe.

More of the above example's reports are now safe:

7 6 4 2 1: Safe without removing any level.
1 2 7 8 9: Unsafe regardless of which level is removed.
9 7 6 2 1: Unsafe regardless of which level is removed.
1 3 2 4 5: Safe by removing the second level, 3.
8 6 4 4 1: Safe by removing the third level, 4.
1 3 6 7 9: Safe without removing any level.
Thanks to the Problem Dampener, 4 reports are actually safe!

Update your analysis by handling situations where the Problem Dampener can remove a single level from unsafe reports. How many reports are now safe?

Answer: 381
 

Although it hasn't changed, you can still get your puzzle input.


 */
public class Main {

	public static void main(String[] args) {
		System.out.println("Current path "+Paths.get(".").toAbsolutePath());
		try (var lines = Files.lines(Paths.get("input-day2.txt"))) {			
			 long countValidPart1 = 0;
			 long countValidPart2 = 0;
			 for(String line : lines.toList()) {
				 long[] report = convertLine(line);
				 if (checkPart1(report)) {
					 countValidPart1++;
				 } 
				 if (checkPart2(report)) {
					 countValidPart2++;
				 }
			 }
			 System.out.println("Part 1: Safe reports count " + countValidPart1);
			 System.out.println("Part 2: Safe reports count " + countValidPart2);
			 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Check input row for part 1
	 * 
	 * @param pLine
	 * @return
	 */
	static long[] convertLine(String pLine) {
		long[] out = null;
		if (pLine != null) {
			String[] ss = pLine.split("\\h+");
			if (ss.length > 1) {
				out = new long[ss.length];
				try {
					for (int i=0; i<ss.length; i++) {
						out[i]= Long.parseLong(ss[i]);
					}
				} catch (Exception e) {
					//invalid
					System.out.println("!!! Invalid line: "+ pLine);
				}
			}				
		}
		return out;
	}
	
	/**
	 * Check input row for part 1
	 * 
	 * @param pLine
	 * @return
	 */
	static boolean checkPart1(long[] report) {
		boolean ret = false;
		if (report != null) {
			if (report.length > 1) {
				try {
					long v1 = report[0];
					long v2 = report[1];
					boolean isIncreasing = v1 < v2;
					boolean valid = true;
					for (int i=1; i<report.length; i++) {						
						v2 = report[i];
						valid = checkPart2Values(v1, v2, isIncreasing);
						if (!valid) {
							break;
						}
						v1 = v2;
					}
					ret = valid;
				} catch (Exception e) {
					//invalid
				}
			}				
		}
		return ret;
	}
	
	/**
	 * Check input row for part 2
	 * 
	 * @param pLine
	 * @return
	 */
	static boolean checkPart2(long[] report) {
		boolean ret = false;
		if (report != null) {
			if (report.length > 1) {
				int cntInc = 0;
				int cntDec = 0;
				for (int i=0; i<report.length-1; i++) {
					if (report[i] < report[i+1]) {
						cntInc++;
					}
					if (report[i] > report[i+1]) {
						cntDec++;
					}
				}
				if (cntInc > 1 && cntDec > 1) {
					return false;
				}
				long v1 = report[0];
				long v2 = report[1];
				boolean isIncreasing = cntInc > cntDec;
				boolean validRet = true;
				boolean removedLevel = false;
				for (int i=1; i<report.length; i++) {						
					v2 = report[i];
					boolean valid12 = checkPart2Values(v1, v2, isIncreasing);
					if (!valid12 && !removedLevel) {
						if (i+1<report.length) {
							long v3 = report[i+1];
							boolean valid13 = checkPart2Values(v1, v3, isIncreasing);
							if (valid13) {
								valid12 = true;
								removedLevel = true;
								i++; //skip v2
								continue;
							}
							boolean validPrev = (i == 1) 
							        || checkPart2Values(report[i - 2], v2, isIncreasing);
							if (validPrev) {
								valid12 = true;
								removedLevel = true;
								v1 = v2;
								continue;
							}
						} else {
							valid12 = true;
						}
					}
					validRet = validRet && valid12;
					if (!validRet && removedLevel) {
						break;
					}
					v1 = v2;
				}
				ret = validRet;
			}				
		}
		return ret;
	}
	
	static boolean checkPart2Values(long v1, long v2, boolean isIncreasing) {
		boolean valid = true;
		long diff = 0;
		if (isIncreasing) {
			valid = valid && v2 > v1;
			diff = v2 - v1;
		} else {
			valid = valid && v1 > v2;
			diff = v1 - v2;
		}
		return valid && diff >= 1 && diff <= 3;
	}
	
}
