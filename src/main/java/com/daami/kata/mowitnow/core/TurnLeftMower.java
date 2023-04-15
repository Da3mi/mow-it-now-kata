package com.daami.kata.mowitnow.core;

import com.daami.kata.mowitnow.model.Mower;
import com.daami.kata.mowitnow.model.Orientation;
import com.daami.kata.mowitnow.model.Position;
import com.daami.kata.mowitnow.port.ControlMower;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TurnLeftMower extends ControlMower {

	public TurnLeftMower(Mower mower) {
		super(mower);
	}

	@Override
	public Mower execute() {
		log.info("The mower is turned to the left compared to the orientation: {}", this.mower.getOrientation());

		switch (this.mower.getOrientation()) {
		case North:
			return this.buildTurnedLeftMower(Orientation.West);
		case South:
			return this.buildTurnedLeftMower(Orientation.East);
		case East:
			return this.buildTurnedLeftMower(Orientation.North);
		case West:
			return this.buildTurnedLeftMower(Orientation.South);
		default:
			break;
		}

		return this.mower;
	}

	private Mower buildTurnedLeftMower(Orientation orientation) {
		return Mower.builder().position(super.buildActualPosition()).orientation(orientation)
				.build();
	}

}
