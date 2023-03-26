package com.daami.kata.mowitnow.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Orientation {
	North("N"),
	South("S"),
	East("E"),
	West("W");
	
	
	private final String code;
	
	private Orientation(final String code) {
		this.code = code;
	}
	
	public String getOrientationCode() {
		return code;
	}
	
	public static Orientation getOrientation(String code) {
		return Arrays.stream(values()).filter(v -> v.getOrientationCode().equals(code)).findFirst().orElse(null);
	}
	
	public static List<String> getCodeList() {
		return Arrays.stream(values()).map(Orientation::getOrientationCode).collect(Collectors.toList());
	}
}
