package xms.adventofcode.day3;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*********
 * Day 3 *
 ********* 

https://adventofcode.com/2024/day/3

 
--- Day 3: Mull It Over ---
"Our computers are having issues, so I have no idea if we have any Chief Historians in stock! You're welcome to check the warehouse, though," says the mildly flustered shopkeeper at the North Pole Toboggan Rental Shop. The Historians head out to take a look.

The shopkeeper turns to you. "Any chance you can see why our computers are having issues again?"

The computer appears to be trying to run a program, but its memory (your puzzle input) is corrupted. All of the instructions have been jumbled up!

It seems like the goal of the program is just to multiply some numbers. It does that with instructions like mul(X,Y), where X and Y are each 1-3 digit numbers. For instance, mul(44,46) multiplies 44 by 46 to get a result of 2024. Similarly, mul(123,4) would multiply 123 by 4.

However, because the program's memory has been corrupted, there are also many invalid characters that should be ignored, even if they look like part of a mul instruction. Sequences like mul(4*, mul(6,9!, ?(12,34), or mul ( 2 , 4 ) do nothing.

For example, consider the following section of corrupted memory:

xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))
Only the four highlighted sections are real mul instructions. Adding up the result of each instruction produces 161 (2*4 + 5*5 + 11*8 + 8*5).

Scan the corrupted memory for uncorrupted mul instructions. What do you get if you add up all of the results of the multiplications?

Your puzzle answer was 166905464.

--- Part Two ---
As you scan through the corrupted memory, you notice that some of the conditional statements are also still intact. If you handle some of the uncorrupted conditional statements in the program, you might be able to get an even more accurate result.

There are two new instructions you'll need to handle:

The do() instruction enables future mul instructions.
The don't() instruction disables future mul instructions.
Only the most recent do() or don't() instruction applies. At the beginning of the program, mul instructions are enabled.

For example:

xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))
This corrupted memory is similar to the example from before, but this time the mul(5,5) and mul(11,8) instructions are disabled because there is a don't() instruction before them. The other mul instructions function normally, including the one at the end that gets re-enabled by a do() instruction.

This time, the sum of the results is 48 (2*4 + 8*5).

Handle the new instructions; what do you get if you add up all of the results of just the enabled multiplications?

Your puzzle answer was 72948684.

Both parts of this puzzle are complete! They provide two gold stars: **

At this point, you should return to your Advent calendar and try another puzzle.

If you still want to see it, you can get your puzzle input.


 */
public class Main {

	public static void main(String[] args) {
		System.out.println("Current path "+Paths.get(".").toAbsolutePath());
		Pattern patternPart1 = Pattern.compile("\\bmul\\(\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3})\\s*\\)");
		long sumPart1 = 0;
		try (BufferedReader br = Files.newBufferedReader(Paths.get("input-day3.txt"))) {			
		    for (String line; (line = br.readLine()) != null; ) {
		    	Matcher m = patternPart1.matcher(line);
		    	while (m.find()) {
		    		sumPart1 += Long.parseLong(m.group(1)) * Long.parseLong(m.group(2));
		    	}
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Pattern patternPart2 = Pattern.compile(
	            "do\\(\\)"                           // do()
	          + "|don't\\(\\)"                       // don't()
	          + "|mul\\(\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3})\\s*\\)" // mul(a,b) -> groups 1,2
	        );
		
		long sumPart2 = 0;
		try (BufferedReader br = Files.newBufferedReader(Paths.get("input-day3.txt"))) {
			boolean enabled = true;
		    for (String line; (line = br.readLine()) != null; ) {
		    	Matcher m = patternPart2.matcher(line);
                while (m.find()) {
                    String token = m.group(0);
                    if ("do()".equals(token)) {
                        enabled = true;
                    } else if ("don't()".equals(token)) {
                        enabled = false;
                    } else {
                        if (enabled) {
                        	sumPart2 += Long.parseLong(m.group(1)) * Long.parseLong(m.group(2));
                        }
                    }
                }
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("*** Part 1 ***");
		System.out.println("Total sum of multiplications is " + sumPart1);
		
		System.out.println("*** Part 2 ***");
		System.out.println("Total sum of multiplications is " + sumPart2);
	}

}
