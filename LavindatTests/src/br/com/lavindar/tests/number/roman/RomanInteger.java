package br.com.lavindar.tests.number.roman;

import java.util.regex.Pattern;

public class RomanInteger extends Number implements Comparable<Number> {
    private static final long serialVersionUID = 1L;
    
    public static final RomanInteger M = new RomanInteger(1000, "M");
    public static final RomanInteger D = new RomanInteger(500, "D");
    public static final RomanInteger C = new RomanInteger(100, "C");
    public static final RomanInteger L = new RomanInteger(50, "L");
    public static final RomanInteger X = new RomanInteger(10, "X");
    public static final RomanInteger V = new RomanInteger(5, "V");
    public static final RomanInteger I = new RomanInteger(1, "I");
    public static final RomanInteger _ZERO = new RomanInteger(0, "");
    
    public static final RomanInteger MAX_VALUE = new RomanInteger(3999, "MMMCMXCIX");
    public static final RomanInteger MIN_VALUE = I;
    
    private final int value;
    private final String number;
    
    private RomanInteger(int value, String number) {
        this.value = value;
        this.number = number;
    }
    
    public RomanInteger(int value) {
        if (value <= 0) {
            this.value = _ZERO.value;
            number = _ZERO.number;
        } else {
            this.value = value;
            number = generateNumber(value);
        }
    }
    
    public static RomanInteger valueOf(int value) {
        if (value == 1000) {
            return M;
        }
        
        if (value == 500) {
            return D;
        }
        
        if (value == 100) {
            return C;
        }
        
        if (value == 50) {
            return L;
        }
        
        if (value == 10) {
            return X;
        }
        
        if (value == 5) {
            return V;
        }
        
        if (value == 1) {
            return I;
        }
        
        if (value <= 0) {
            return _ZERO;
        }
        
        return new RomanInteger(value);
    }
    
    private static Pattern validator;
    
    public static RomanInteger valueOf(String number) {
        if (number == null) {
            return null;
        }
        
        if (number.length() == 0) {
            return _ZERO;
        }
        
        if (validator == null) {
            String regex = "";
            
            regex = "[Mn]{0,3}";
            regex += "([Cc][Mm]|[Dd][Cc]{0,3}|[Cc][Dd]|[Cc]{0,3})";
            regex += "([Xx][Cc]|[Ll][Xx]{0,3}|[Xx][Ll]|[Xx]{0,3})";
            regex += "([Iij][Xx]|[Vv][Ii]{0,3}|[Vv][Ii]{1,2}j|[Vv]j|[Iij][Vv]|[Ii]{0,3}|[Ii]{1,2}j|j)";
            
            validator = Pattern.compile("^" + regex + "$");
        }
        
        if (!validator.matcher(number).matches()) {
            throw new NumberFormatException();
        }
        
        if (M.number.equalsIgnoreCase(number)) {
            return M;
        }
        
        if (D.number.equalsIgnoreCase(number)) {
            return D;
        }
        
        if (C.number.equalsIgnoreCase(number)) {
            return C;
        }
        
        if (L.number.equalsIgnoreCase(number)) {
            return L;
        }
        
        if (X.number.equalsIgnoreCase(number)) {
            return X;
        }
        
        if (I.number.equalsIgnoreCase(number) || "j".equals(number)) {
            return I;
        }
        
        int value = 0;
        
        for (int i = 0; i < number.length(); i++) {
            switch (number.charAt(i)) {
                case 'M':
                case 'm':
                    value += 1000;
                    break;
                case 'D':
                case 'd':
                    value += 500;
                    break;
                case 'C':
                case 'c':
                    if (i + 1 == number.length()) {
                        value += 100;
                    } else {
                        switch (number.charAt(i + 1)) {
                            case 'M':
                            case 'm':
                                value += 900;
                                i++;
                                break;
                            case 'D':
                            case 'd':
                                value += 400;
                                i++;
                                break;
                            default:
                                value += 100;
                        }
                    }
                    break;
                case 'L':
                case 'l':
                    value += 50;
                    break;
                case 'X':
                case 'x':
                    if (i + 1 == number.length()) {
                        value += 10;
                    } else {
                        switch (number.charAt(i + 1)) {
                            case 'C':
                            case 'c':
                                value += 90;
                                i++;
                                break;
                            case 'L':
                            case 'l':
                                value += 40;
                                i++;
                                break;
                            default:
                                value += 10;
                        }
                    }
                    break;
                case 'V':
                case 'v':
                    value += 5;
                    break;
                case 'I':
                case 'i':
                    if (i + 1 == number.length()) {
                        value++;
                    } else {
                        switch (number.charAt(i + 1)) {
                            case 'X':
                            case 'x':
                                value += 9;
                                i++;
                                break;
                            case 'V':
                            case 'v':
                                value += 4;
                                i++;
                                break;
                            default:
                                value++;
                        }
                    }
                    break;
                case 'j':
                    value++;
                    break;
            
            }
        }
        
        return valueOf(value);
    }
    
    @Override
    public String toString() {
        return number;
    }
    
    public String toLowerString() {
        return number.toLowerCase();
    }
    
    private String generateNumber(int value) {
        int tempValue = value;
        
        StringBuilder number = new StringBuilder("");
        
        while (tempValue > 0) {
            if (tempValue >= M.value) {
                number.append(M);
                tempValue -= M.value;
                continue;
            }
            if (tempValue >= M.value - C.value) {
                number.append(C).append(M);
                tempValue -= M.value - C.value;
                continue;
            }
            if (tempValue >= D.value) {
                number.append(D);
                tempValue -= D.value;
                continue;
            }
            if (tempValue >= D.value - C.value) {
                number.append(C).append(D);
                tempValue -= D.value - C.value;
                continue;
            }
            if (tempValue >= C.value) {
                number.append(C);
                tempValue -= C.value;
                continue;
            }
            if (tempValue >= C.value - X.value) {
                number.append(X).append(C);
                tempValue -= C.value - X.value;
                continue;
            }
            if (tempValue >= L.value) {
                number.append(L);
                tempValue -= L.value;
                continue;
            }
            if (tempValue >= L.value - X.value) {
                number.append(X).append(L);
                tempValue -= L.value - X.value;
                continue;
            }
            if (tempValue >= X.value) {
                number.append(X);
                tempValue -= X.value;
                continue;
            }
            if (tempValue >= X.value - I.value) {
                number.append(I).append(X);
                tempValue -= X.value - I.value;
                continue;
            }
            if (tempValue >= V.value) {
                number.append(V);
                tempValue -= V.value;
                continue;
            }
            if (tempValue >= V.value - I.value) {
                number.append(I).append(V);
                tempValue -= V.value - I.value;
                continue;
            }
            if (tempValue >= I.value) {
                number.append(I);
                tempValue -= I.value;
                continue;
            }
        }
        
        return number.toString();
    }
    
    public RomanInteger add(Number number) {
        if (number == null) {
            return null;
        }
        
        return valueOf(value + number.intValue());
    }
    
    public RomanInteger subtract(Number number) {
        if (number == null) {
            return null;
        }
        
        return valueOf(value - number.intValue());
    }
    
    public RomanInteger multiply(Number number) {
        if (number == null) {
            return null;
        }
        
        return valueOf(value * number.intValue());
    }
    
    public RomanInteger divide(Number number) {
        if (number == null) {
            return null;
        }
        
        return valueOf(value / number.intValue());
    }
    
    @Override
    public int hashCode() {
        return value;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        RomanInteger other = (RomanInteger) obj;
        if (value != other.value) {
            return false;
        }
        return true;
    }
    
    @Override
    public int compareTo(Number n) {
        if (value == n.intValue()) {
            return 0;
        }
        
        return value > n.intValue() ? 1 : -1;
    }
    
    @Override
    public int intValue() {
        return value;
    }
    
    @Override
    public long longValue() {
        return value;
    }
    
    @Override
    public float floatValue() {
        return value;
    }
    
    @Override
    public double doubleValue() {
        return value;
    }
}