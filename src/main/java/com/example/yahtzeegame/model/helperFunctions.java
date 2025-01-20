package com.example.yahtzeegame.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class helperFunctions {

    /**
     * *********************************************************************
     * Function Name: allSame
     * Purpose: Checks if all elements in the list are the same.
     * Parameters: List<Integer> v - the list to check.
     * Return Value: boolean - true if all elements are the same, false otherwise.
     * Algorithm:
     * 1. Iterate through the list starting from the second element.
     * 2. Compare each element to the first element.
     * 3. If any element is different, return false.
     * 4. If all elements are the same, return true.
     * Reference: None.
     *********************************************************************
     */
    public static boolean allSame(List<Integer> v) {
        for (int i = 1; i < v.size(); i++) {
            if (!v.get(i).equals(v.get(0))) {
                return false;
            }
        }
        return true;
    }

    /**
     * *********************************************************************
     * Function Name: allSequence
     * Purpose: Checks if the elements in the list form a sequence.
     * Parameters: List<Integer> v - the list to check.
     * Return Value: boolean - true if the list forms a sequence, false otherwise.
     * Algorithm:
     * 1. Sort the list in ascending order.
     * 2. Check if each consecutive element differs by 1.
     * 3. Return true if all elements form a sequence, otherwise false.
     * Reference: None.
     *********************************************************************
     */
    public static boolean allSequence(List<Integer> v) {
        List<Integer> sortedV = new ArrayList<>(v);
        Collections.sort(sortedV);
        for (int i = 1; i < sortedV.size(); i++) {
            if (sortedV.get(i) != sortedV.get(i - 1) + 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * *********************************************************************
     * Function Name: containsSequence
     * Purpose: Checks if the list contains a sequence of a given length.
     * Parameters: List<Integer> v - the list to check, int sequenceLength - the
     * length of the sequence.
     * Return Value: boolean - true if a sequence of the specified length is found,
     * false otherwise.
     * Algorithm:
     * 1. Iterate through the list and check sublists of the specified length.
     * 2. For each sublist, check if it forms a sequence using the allSequence
     * method.
     * 3. Return true if a sequence of the required length is found, otherwise
     * false.
     * Reference: None.
     *********************************************************************
     */
    public static boolean containsSequence(List<Integer> v, int sequenceLength) {
        if (v.size() < sequenceLength) {
            return false;
        }
        for (int i = 0; i <= v.size() - sequenceLength; i++) {
            List<Integer> subV = v.subList(i, i + sequenceLength);
            if (allSequence(subV)) {
                return true;
            }
        }
        return false;
    }

    /**
     * *********************************************************************
     * Function Name: fiveSequence
     * Purpose: Checks if the list contains a sequence of exactly 5 consecutive
     * numbers.
     * Parameters: List<Integer> v - the list to check.
     * Return Value: boolean - true if a sequence of 5 consecutive numbers is found,
     * false otherwise.
     * Algorithm:
     * 1. Call the containsSequence method with sequenceLength set to 5.
     * 2. Return the result.
     * Reference: None.
     *********************************************************************
     */
    public static boolean fiveSequence(List<Integer> v) {
        return containsSequence(v, 5);
    }

    /**
     * *********************************************************************
     * Function Name: fourSequence
     * Purpose: Checks if the list contains a sequence of exactly 4 consecutive
     * numbers.
     * Parameters: List<Integer> v - the list to check.
     * Return Value: boolean - true if a sequence of 4 consecutive numbers is found,
     * false otherwise.
     * Algorithm:
     * 1. Call the containsSequence method with sequenceLength set to 4.
     * 2. Return the result.
     * Reference: None.
     *********************************************************************
     */
    public static boolean fourSequence(List<Integer> v) {
        return containsSequence(v, 4);
    }

    /**
     * *********************************************************************
     * Function Name: atleastNSame
     * Purpose: Checks if the list contains at least 'n' occurrences of any number.
     * Parameters: List<Integer> v - the list to check, int n - the minimum number
     * of occurrences.
     * Return Value: boolean - true if any number appears 'n' or more times, false
     * otherwise.
     * Algorithm:
     * 1. Create a count map to store the frequency of each number in the list.
     * 2. Iterate through the map and check if any number has a frequency greater
     * than or equal to 'n'.
     * 3. Return true if such a number is found, otherwise false.
     * Reference: None.
     *********************************************************************
     */
    public static boolean atleastNSame(List<Integer> v, int n) {
        Map<Integer, Integer> counts = new HashMap<>();
        for (int i : v) {
            counts.put(i, counts.getOrDefault(i, 0) + 1);
        }
        for (int count : counts.values()) {
            if (count >= n) {
                return true;
            }
        }
        return false;
    }

    /**
     * *********************************************************************
     * Function Name: atleastFourSame
     * Purpose: Checks if the list contains at least 4 of the same number.
     * Parameters: List<Integer> v - the list to check.
     * Return Value: boolean - true if at least 4 occurrences of the same number are
     * found, false otherwise.
     * Algorithm:
     * 1. Call the atleastNSame method with 'n' set to 4.
     * 2. Return the result.
     * Reference: None.
     *********************************************************************
     */
    public static boolean atleastFourSame(List<Integer> v) {
        return atleastNSame(v, 4);
    }

    /**
     * *********************************************************************
     * Function Name: atleastThreeSame
     * Purpose: Checks if the list contains at least 3 of the same number.
     * Parameters: List<Integer> v - the list to check.
     * Return Value: boolean - true if at least 3 occurrences of the same number are
     * found, false otherwise.
     * Algorithm:
     * 1. Call the atleastNSame method with 'n' set to 3.
     * 2. Return the result.
     * Reference: None.
     *********************************************************************
     */
    public static boolean atleastThreeSame(List<Integer> v) {
        return atleastNSame(v, 3);
    }

    /**
     * *********************************************************************
     * Function Name: containsN
     * Purpose: Checks if the list contains the number 'n'.
     * Parameters: List<Integer> v - the list to check, int n - the number to check
     * for.
     * Return Value: boolean - true if 'n' is found in the list, false otherwise.
     * Algorithm:
     * 1. Use the contains method of the list to check if 'n' is present.
     * 2. Return the result.
     * Reference: None.
     *********************************************************************
     */
    public static boolean containsN(List<Integer> v, int n) {
        return v.contains(n);
    }

    /**
     * *********************************************************************
     * Function Name: getSequence
     * Purpose: Returns the first sequence of a given length found in the list.
     * Parameters: List<Integer> v - the list to check, int sequenceLength - the
     * length of the sequence.
     * Return Value: List<Integer> - the first sequence of the specified length
     * found in the list.
     * Algorithm:
     * 1. Sort the list in ascending order.
     * 2. Check each sublist of the specified length to see if it forms a sequence
     * using the allSequence method.
     * 3. Return the first sequence found, or an empty list if no sequence is found.
     * Reference: None.
     *********************************************************************
     */
    public static List<Integer> getSequence(List<Integer> v, int sequenceLength) {
        List<Integer> sortedV = new ArrayList<>(v);
        Collections.sort(sortedV);
        for (int i = 0; i <= sortedV.size() - sequenceLength; i++) {
            List<Integer> subV = sortedV.subList(i, i + sequenceLength);
            if (allSequence(subV)) {
                return subV;
            }
        }
        return new ArrayList<>();
    }

    /**
     * *********************************************************************
     * Function Name: sum
     * Purpose: Returns the sum of all elements in the list.
     * Parameters: List<Integer> v - the list to sum.
     * Return Value: int - the sum of the elements in the list.
     * Algorithm:
     * 1. Use the stream API to sum all elements in the list.
     * 2. Return the sum.
     * Reference: None.
     *********************************************************************
     */
    public static int sum(List<Integer> v) {
        return v.stream().mapToInt(Integer::intValue).sum();
    }

    /**
     * *********************************************************************
     * Function Name: countN
     * Purpose: Returns how many times 'n' appears in the list.
     * Parameters: List<Integer> v - the list to check, int n - the number to count.
     * Return Value: int - the count of occurrences of 'n' in the list.
     * Algorithm:
     * 1. Use the stream API to filter and count the occurrences of 'n'.
     * 2. Return the count.
     * Reference: None.
     *********************************************************************
     */

    public static int countN(List<Integer> v, int n) {
        return (int) v.stream().filter(i -> i == n).count();
    }

    /**
     * *********************************************************************
     * Function Name: getUnique
     * Purpose: Returns a list containing only the unique elements from the input
     * list.
     * Parameters: List<Integer> v - the list to process.
     * Return Value: List<Integer> - a list containing only the unique elements from
     * the input list.
     * Algorithm:
     * 1. Use the stream API to get distinct elements from the list.
     * 2. Return the resulting list of unique elements.
     * Reference: None.
     *********************************************************************
     */
    public static List<Integer> getUnique(List<Integer> v) {
        return v.stream().distinct().collect(Collectors.toList());
    }

    /**
     * *********************************************************************
     * Function Name: countUnique
     * Purpose: Returns the number of unique elements in the list.
     * Parameters: List<Integer> v - the list to process.
     * Return Value: int - the count of unique elements in the list.
     * Algorithm:
     * 1. Call the getUnique method to get the unique elements.
     * 2. Return the size of the resulting list.
     * Reference: None.
     *********************************************************************
     */
    public static int countUnique(List<Integer> v) {
        return getUnique(v).size();
    }

    /**
     * *********************************************************************
     * Function Name: maxCount
     * Purpose: Returns the highest count of any number in the list.
     * Parameters: List<Integer> v - the list to check.
     * Return Value: int - the highest frequency of any number in the list.
     * Algorithm:
     * 1. Create a count map to store the frequency of each number in the list.
     * 2. Return the maximum value from the map.
     * Reference: None.
     *********************************************************************
     */
    public static int maxCount(List<Integer> v) {
        Map<Integer, Integer> counts = new HashMap<>();
        for (int i : v) {
            counts.put(i, counts.getOrDefault(i, 0) + 1);
        }
        return counts.values().stream().max(Integer::compare).orElse(0);
    }

    /**
     * *********************************************************************
     * Function Name: numRepeats
     * Purpose: Returns the total number of repeated elements in the list.
     * Parameters: List<Integer> v - the list to check.
     * Return Value: int - the total number of repeated elements in the list.
     * Algorithm:
     * 1. Create a count map to store the frequency of each number in the list.
     * 2. Sum the counts of elements that occur more than once.
     * 3. Return the total sum of repeated elements.
     * Reference: None.
     *********************************************************************
     */
    public static int numRepeats(List<Integer> v) {
        Map<Integer, Integer> counts = new HashMap<>();
        for (int i : v) {
            counts.put(i, counts.getOrDefault(i, 0) + 1);
        }
        return counts.values().stream().mapToInt(count -> count > 1 ? count - 1 : 0).sum();
    }

    /**
     * *********************************************************************
     * Function Name: longestSequenceLength
     * Purpose: Returns the length of the longest sequence of consecutive numbers in
     * the list.
     * Parameters: List<Integer> v - the list to check.
     * Return Value: int - the length of the longest sequence of consecutive
     * numbers.
     * Algorithm:
     * 1. Sort the list and remove duplicates.
     * 2. Iterate through the sorted list and find the longest sequence of
     * consecutive numbers.
     * 3. Return the length of the longest sequence found.
     * Reference: None.
     *********************************************************************
     */
    public static int longestSequenceLength(List<Integer> v) {
        if (v.isEmpty()) {
            return 0;
        }

        int longest = 0;
        int current = 1;
        List<Integer> sortedV = getUnique(v);
        Collections.sort(sortedV);

        for (int i = 1; i < sortedV.size(); i++) {
            if (sortedV.get(i) == sortedV.get(i - 1) + 1) {
                current++;
            } else {
                longest = Math.max(longest, current);
                current = 1;
            }
        }

        return Math.max(longest, current);
    }

    /**
     * *********************************************************************
     * Function Name: fullHouse
     * Purpose: Checks if the list represents a full house (three of one number and
     * two of another).
     * Parameters: List<Integer> v - the list to check.
     * Return Value: boolean - true if the list represents a full house, false
     * otherwise.
     * Algorithm:
     * 1. Create a count map to store the frequency of each number in the list.
     * 2. Check if there is a number with a count of 3 and another with a count of
     * 2.
     * 3. Return true if both conditions are met, otherwise false.
     * Reference: None.
     *********************************************************************
     */
    public static boolean fullHouse(List<Integer> v) {
        Map<Integer, Integer> counts = new HashMap<>();
        for (int i : v) {
            counts.put(i, counts.getOrDefault(i, 0) + 1);
        }
        boolean hasTwo = false;
        boolean hasThree = false;
        for (int count : counts.values()) {
            if (count == 2) {
                hasTwo = true;
            } else if (count == 3) {
                hasThree = true;
            }
        }
        return hasTwo && hasThree;
    }

    /**
     * *********************************************************************
     * Function Name: split
     * Purpose: Splits a string into tokens based on a delimiter character.
     * Parameters:
     * String s - the string to be split.
     * char delimiter - the character used to split the string.
     * Return Value: List<String> - a list of strings, split by the delimiter.
     * Algorithm:
     * 1. Use the split method of String to split the string by the delimiter.
     * 2. Convert the resulting array into a List using Arrays.asList().
     * Reference: None.
     *********************************************************************
     */
    public static List<String> split(String s, char delimiter) {
        return Arrays.asList(s.split(String.valueOf(delimiter)));
    }

    /**
     * *********************************************************************
     * Function Name: join
     * Purpose: Joins a list of strings into a single string, separated by a
     * delimiter.
     * Parameters:
     * List<String> v - the list of strings to be joined.
     * char delimiter - the character used to separate the strings.
     * Return Value: String - the concatenated string, with delimiters between
     * elements.
     * Algorithm:
     * 1. Convert the delimiter to a String.
     * 2. Use String.join to concatenate the strings in the list with the delimiter.
     * Reference: None.
     *********************************************************************
     */
    public static String join(List<String> v, char delimiter) {
        return String.join(String.valueOf(delimiter), v);
    }

    /**
     * *********************************************************************
     * Function Name: trim
     * Purpose: Trims whitespace from both ends of a string.
     * Parameters:
     * String s - the string to be trimmed.
     * Return Value: String - the trimmed string.
     * Algorithm:
     * 1. Use the trim method of the String class to remove leading and trailing
     * whitespace.
     * Reference: None.
     *********************************************************************
     */
    public static String trim(String s) {
        return s.trim();
    }

    /**
     * *********************************************************************
     * Function Name: getDicePermutation
     * Purpose: Generates all possible permutations of dice rolls with a given
     * number of dice.
     * Parameters:
     * int numDice - the number of dice to generate permutations for.
     * Return Value: List<List<Integer>> - a list of all possible dice roll
     * combinations.
     * Algorithm:
     * 1. Initialize a list to store combinations.
     * 2. For each dice, add all possible dice values (1-6) to the current
     * combination.
     * 3. Repeat the process for the specified number of dice.
     * Reference: None.
     *********************************************************************
     */
    public static List<List<Integer>> getDicePermutation(int numDice) {
        List<List<Integer>> combinations = new ArrayList<>();
        List<Integer> combination = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            combination.add(i);
            combinations.add(new ArrayList<>(combination));
            combination.remove(combination.size() - 1);
        }

        for (int i = 1; i < numDice; i++) {
            List<List<Integer>> newCombinations = new ArrayList<>();
            for (List<Integer> c : combinations) {
                for (int j = 1; j <= 6; j++) {
                    List<Integer> newCombination = new ArrayList<>(c);
                    newCombination.add(j);
                    newCombinations.add(newCombination);
                }
            }
            combinations = newCombinations;
        }

        return combinations;
    }

    /**
     * *********************************************************************
     * Function Name: generateCombinations
     * Purpose: Recursively generates all combinations of dice rolls with a given
     * number of dice.
     * Parameters:
     * int n - the number of dice.
     * int start - the starting value for the dice roll (1 to 6).
     * List<Integer> current - the current combination being built.
     * List<List<Integer>> result - the list to store all combinations.
     * Return Value: void
     * Algorithm:
     * 1. If the number of dice is zero, add the current combination to the result.
     * 2. Recursively generate combinations by adding each value (1 to 6) to the
     * current combination.
     * Reference: None.
     *********************************************************************
     */
    public static void generateCombinations(int n, int start, List<Integer> current, List<List<Integer>> result) {
        if (n == 0) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i = start; i <= 6; i++) {
            current.add(i);
            generateCombinations(n - 1, i, current, result);
            current.remove(current.size() - 1);
        }
    }

    /**
     * *********************************************************************
     * Function Name: diceCombinations
     * Purpose: A wrapper function that generates all combinations of dice rolls.
     * Parameters:
     * int n - the number of dice.
     * Return Value: List<List<Integer>> - a list of all possible dice combinations.
     * Algorithm:
     * 1. Initialize an empty list for the result.
     * 2. Call the recursive generateCombinations function to fill the result.
     * Reference: None.
     *********************************************************************
     */
    public static List<List<Integer>> diceCombinations(int n) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> current = new ArrayList<>();
        generateCombinations(n, 1, current, result);
        return result;
    }

    /**
     * *********************************************************************
     * Function Name: randomBool
     * Purpose: Generates a random boolean value.
     * Parameters: None
     * Return Value: boolean - a randomly generated boolean value (true or false).
     * Algorithm:
     * 1. Use the Random class to generate a random boolean value.
     * Reference: None.
     *********************************************************************
     */
    public static boolean randomBool() {
        Random random = new Random();
        return random.nextBoolean();
    }

    /**
     * *********************************************************************
     * Function Name: reversed
     * Purpose: Returns the reversed version of a given list.
     * Parameters:
     * List<T> v - the list to be reversed.
     * Return Value: List<T> - the reversed list.
     * Algorithm:
     * 1. Create a copy of the list.
     * 2. Use Collections.reverse to reverse the list.
     * Reference: None.
     *********************************************************************
     */
    public static <T> List<T> reversed(List<T> v) {
        List<T> reversedV = new ArrayList<>(v);
        Collections.reverse(reversedV);
        return reversedV;
    }

    /**
     * *********************************************************************
     * Function Name: intersection
     * Purpose: Returns the intersection of two lists (common elements).
     * Parameters:
     * List<T> v1 - the first list.
     * List<T> v2 - the second list.
     * Return Value: List<T> - a list of common elements between v1 and v2.
     * Algorithm:
     * 1. Use a map to count occurrences of each element in the first list.
     * 2. For each element in the second list, check if it exists in the first list
     * and add it to the result.
     * Reference: None.
     *********************************************************************
     */
    public static <T> List<T> intersection(List<T> v1, List<T> v2) {
        Map<T, Integer> countMap = new HashMap<>();
        List<T> result = new ArrayList<>();

        for (T element : v1) {
            countMap.put(element, countMap.getOrDefault(element, 0) + 1);
        }

        for (T element : v2) {
            if (countMap.getOrDefault(element, 0) > 0) {
                result.add(element);
                countMap.put(element, countMap.get(element) - 1);
            }
        }

        return result;
    }

    /**
     * *********************************************************************
     * Function Name: difference
     * Purpose: Returns the difference between two lists (elements in the first list
     * but not in the second).
     * Parameters:
     * List<T> v1 - the first list.
     * List<T> v2 - the second list.
     * Return Value: List<T> - a list of elements that are in v1 but not in v2.
     * Algorithm:
     * 1. Sort both lists.
     * 2. Use two pointers to compare elements from both lists and add non-matching
     * elements to the result.
     * Reference: None.
     *********************************************************************
     */
    public static <T extends Comparable<T>> List<T> difference(List<T> v1, List<T> v2) {
        List<T> sortedV1 = new ArrayList<>(v1);
        Collections.sort(sortedV1);
        List<T> sortedV2 = new ArrayList<>(v2);
        Collections.sort(sortedV2);

        List<T> result = new ArrayList<>();
        int i = 0, j = 0;

        while (i < sortedV1.size() && j < sortedV2.size()) {
            if (sortedV1.get(i).compareTo(sortedV2.get(j)) < 0) {
                result.add(sortedV1.get(i));
                i++;
            } else if (sortedV1.get(i).equals(sortedV2.get(j))) {
                i++;
                j++;
            } else {
                j++;
            }
        }

        while (i < sortedV1.size()) {
            result.add(sortedV1.get(i));
            i++;
        }

        return result;
    }

    /**
     * *********************************************************************
     * Function Name: contains
     * Purpose: Checks if a list contains a specific element.
     * Parameters:
     * List<T> v - the list to check.
     * T element - the element to check for.
     * Return Value: boolean - true if the list contains the element, false
     * otherwise.
     * Algorithm:
     * 1. Use the contains method of the List interface to check if the element
     * exists in the list.
     * Reference: None.
     *********************************************************************
     */
    public static <T> boolean contains(List<T> v, T element) {
        return v.contains(element);
    }

    /**
     * *********************************************************************
     * Function Name: concatenate
     * Purpose: Concatenates two lists into one.
     * Parameters:
     * List<T> v1 - the first list.
     * List<T> v2 - the second list.
     * Return Value: List<T> - the concatenated list.
     * Algorithm:
     * 1. Create a new list and add all elements of the first list.
     * 2. Add all elements of the second list to the new list.
     * Reference: None.
     *********************************************************************
     */
    public static <T> List<T> concatenate(List<T> v1, List<T> v2) {
        List<T> result = new ArrayList<>(v1);
        result.addAll(v2);
        return result;
    }

    /**
     * *********************************************************************
     * Function Name: unorderedEqual
     * Purpose: Checks if two lists contain the same elements, regardless of order.
     * Parameters:
     * List<T> v1 - the first list.
     * List<T> v2 - the second list.
     * Return Value: boolean - true if the lists contain the same elements, false
     * otherwise.
     * Algorithm:
     * 1. If the lists have different sizes, return false.
     * 2. Count the occurrences of each element in both lists using a map.
     * 3. Ensure that both maps have the same counts for each element.
     * Reference: None.
     *********************************************************************
     */
    public static <T> boolean unorderedEqual(List<T> v1, List<T> v2) {
        if (v1.size() != v2.size()) {
            return false;
        }

        Map<T, Integer> counts = new HashMap<>();
        for (T element : v1) {
            counts.put(element, counts.getOrDefault(element, 0) + 1);
        }
        for (T element : v2) {
            counts.put(element, counts.getOrDefault(element, 0) - 1);
        }

        for (int count : counts.values()) {
            if (count != 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * *********************************************************************
     * Function Name: subset
     * Purpose: Checks if the second list is a subset of the first list.
     * Parameters:
     * List<T> v1 - the first list.
     * List<T> v2 - the second list.
     * Return Value: boolean - true if v2 is a subset of v1, false otherwise.
     * Algorithm:
     * 1. Count the occurrences of each element in both lists using a map.
     * 2. Ensure that the count of each element in v2 does not exceed the count in
     * v1.
     * Reference: None.
     *********************************************************************
     */
    public static <T> boolean subset(List<T> v1, List<T> v2) {
        Map<T, Integer> counts = new HashMap<>();
        for (T element : v1) {
            counts.put(element, counts.getOrDefault(element, 0) + 1);
        }
        for (T element : v2) {
            counts.put(element, counts.getOrDefault(element, 0) - 1);
        }

        for (int count : counts.values()) {
            if (count < 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * *********************************************************************
     * Function Name: getMaxFrequencyElement
     * Purpose: Returns the element that appears most frequently in the list.
     * Parameters:
     * List<T> v - the list to search.
     * Return Value: T - the element with the highest frequency.
     * Algorithm:
     * 1. Create a map to count the frequency of each element.
     * 2. Use a stream to find the element with the highest count.
     * Reference: None.
     *********************************************************************
     */
    public static <T> T getMaxFrequencyElement(List<T> v) {
        Map<T, Integer> counts = new HashMap<>();
        for (T element : v) {
            counts.put(element, counts.getOrDefault(element, 0) + 1);
        }
        return counts.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
    }
}