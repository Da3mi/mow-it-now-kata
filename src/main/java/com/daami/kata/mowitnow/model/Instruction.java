package com.daami.kata.mowitnow.model;

import java.util.Arrays;

public enum Instruction {
	FORWARD("A"),
	RIGHT("D"),
	LEFT("G");
	
	private final String code;
	
	private Instruction(final String code) {
		this.code = code;
	}
	
	public String getInstructionCode() {
		return code;
	}
	
	public static Instruction getInstruction(final String code) {
		return Arrays.stream(values()).filter(v -> v.getInstructionCode().equals(code)).findFirst().orElse(null);
	}
}
