package com.daami.kata.mowitnow.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class Mower {
	
	private final Position position;
	private final Orientation orientation;
	private final Garden garden;
	
}
