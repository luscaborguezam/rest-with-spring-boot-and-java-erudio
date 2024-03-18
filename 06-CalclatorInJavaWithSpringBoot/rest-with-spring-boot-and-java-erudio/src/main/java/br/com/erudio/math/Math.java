package br.com.erudio.math;

import br.com.erudio.converters.NumberConverter;

public class Math {
	public static Double sum(Double n1, Double n2) {
		return n1+n2;
	}
	
	public static Double subtraction(Double n1, Double n2) {
		return n1-n2;
	}
	
	public static Double multiplication(Double n1, Double n2) {
		return n1*n2;
	}
	
	public static Double division(Double n1, Double n2) {
		return n1/n2;
	}
	
	public static Double mean(Double n1, Double n2) {
		return (n1+n2)/2;
	}
	
	
	public static Double potentiation(Double n1, Integer n2) {
		Double total = 1D;
		for(int i = 0; i<n2; i++) {
			total *= n1;
			System.out.println(total);
		}
		return total;
	}
	
	public static Double squereRoot(Double n1) {
		return  java.lang.Math.sqrt(n1);
	}
}
