package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ObjectStack;

/**
 * Demonstrates functionalities of ObjectStack.
 */
public class StackDemo {

    public static void main(String[] args) {

        ObjectStack stack = new ObjectStack();

        String[] expression = args[0].split(" +");

        for (String s : expression) {
            if (isNumber(s)) {
                stack.push(s);
            } else {
                switch (s) {
                    case "+" -> stack.push(sum((String) stack.pop(), (String) stack.pop()));
                    case "-" -> stack.push(sub((String) stack.pop(), (String) stack.pop()));
                    case "*" -> stack.push(mul((String) stack.pop(), (String) stack.pop()));
                    case "/" -> stack.push(div((String) stack.pop(), (String) stack.pop()));
                    case "%" -> stack.push(mod((String) stack.pop(), (String) stack.pop()));
                    default -> throw new IllegalArgumentException("Action " + s + " is not supported");
                }
            }
        }

        if (stack.size() != 1)
            throw new IllegalArgumentException("Wrong input");
        else
            System.out.println(stack.pop());
    }

    /**
     * Calculates remainder of integer division as dividend % divisor
     * of two given strings and returns value of calculation as string.
     *
     * @param divisor divisor in integer division.
     * @param dividend dividend in integer division.
     * @return remainder of integer division.
     * @throws IllegalArgumentException if given divisor is 0 or either one or both arguments can not be parsed into integer value.
     */
    private static String mod(String divisor, String dividend) {
        if (checkNumbers(divisor, dividend)) {
            if (Integer.parseInt(divisor) == 0)
                throw new IllegalArgumentException("Can not divide with 0");
            return Integer.toString(Integer.parseInt(dividend) % Integer.parseInt(divisor)) ;
        }
        throw new IllegalArgumentException("Parameters must be numbers! They were " + divisor + " and " + dividend + ".");
    }

    /**
     * Multiplies integer values of two given strings and returns result of multiplication as string.
     *
     * @param factor1 first factor in multiplication.
     * @param factor2 second factor in multiplication.
     * @return result of multiplying two given factors.
     * @throws IllegalArgumentException if either one or both arguments can not be parsed to integer value.
     */
    private static String mul(String factor1, String factor2) {
        if (checkNumbers(factor1, factor2))
            return Integer.toString(Integer.parseInt(factor1) * Integer.parseInt(factor2)) ;
        throw new IllegalArgumentException("Parameters must be numbers! They were " + factor1 + " and " + factor2 + ".");
    }

    /**
     * Subtracts integer values of two given strings as minuend - subtrahend
     * and returns result of subtraction as string.
     *
     * @param subtrahend subtrahend in subtraction.
     * @param minuend minuend in subtraction.
     * @return difference of two given arguments.
     * @throws IllegalArgumentException if either one or both arguments can not be parsed into integer value.
     */
    private static String sub(String subtrahend, String minuend) {
        if (checkNumbers(subtrahend, minuend))
            return Integer.toString(Integer.parseInt(minuend) - Integer.parseInt(subtrahend)) ;
        throw new IllegalArgumentException("Parameters must be numbers! They were " + (String) subtrahend + " and " + (String) minuend + ".");
    }

    /**
     * Sums integer values of two given strings and returns result od addition as string.
     *
     * @param summand1 first summand in addition.
     * @param summand2 second summand in addition.
     * @return sum of two given arguments
     * @throws IllegalArgumentException if either one or both arguments can not be parsed into integer value.
     */
    private static String sum(String summand1, String summand2) {
        if (checkNumbers(summand1, summand2))
            return Integer.toString(Integer.parseInt(summand1) + Integer.parseInt(summand2)) ;
        throw new IllegalArgumentException("Parameters must be numbers! They were " + (String) summand1 + " and " + (String) summand2 + ".");
    }

    /**
     * Divides integer values of two given strings as dividend / divisor
     * and returns integer value of division as string.
     *
     * @param divisor divisor in division.
     * @param dividend dividend in division.
     * @return integer value of division as string.
     * @throws IllegalArgumentException if given divisor is 0 or either one or both arguments can not be parsed into integer value.
     */
    private static String div(String divisor, String dividend) {
        if (Integer.parseInt(dividend) == 0)
            throw new IllegalArgumentException("Can not divide with 0");
        if (checkNumbers(divisor, dividend))
            return Integer.toString(Integer.parseInt(dividend) /Integer.parseInt(divisor)) ;
        throw new IllegalArgumentException("Parameters must be numbers! They were " + (String) divisor + " and " + (String) dividend + ".");
    }

    /**
     * Checks if given string can be parsed into integer value.
     *
     * @param s string to check if it is number.
     * @return true if string is number and false otherwise.
     */
    private static boolean isNumber(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if both of given arguments are numbers.
     *
     * @param a first argument.
     * @param b second argument.
     * @return
     */
    private static boolean checkNumbers(String a, String b) {
        if (isNumber((String) a) && isNumber((String) b))
            return true;
        return false;
    }
}