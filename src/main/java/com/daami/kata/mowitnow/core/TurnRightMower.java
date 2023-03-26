package com.daami.kata.mowitnow.core;

import com.daami.kata.mowitnow.model.Mower;
import com.daami.kata.mowitnow.model.Orientation;
import com.daami.kata.mowitnow.model.Position;
import com.daami.kata.mowitnow.port.ControlMower;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TurnRightMower extends ControlMower {

	public TurnRightMower(Mower mower) {
		super(mower);
	}

	@Override
	public Mower execute() {
		log.info("The mower is turned to the right compared to the orientation: {}", this.mower.getOrientation());

		switch (this.mower.getOrientation()) {
		case North:
			return this.buildTurnedRightMower(Orientation.East);
		case South:
			return this.buildTurnedRightMower(Orientation.West);
		case East:
			return this.buildTurnedRightMower(Orientation.South);
		case West:
			return this.buildTurnedRightMower(Orientation.North);
		default:
			break;
		}

		return this.mower;
	}

	private Mower buildTurnedRightMower(Orientation orientation) {
		return Mower.builder().position(super.buildActualPosition()).garden(mower.getGarden()).orientation(orientation)
				.build();
	}

}
