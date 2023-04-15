package com.daami.kata.mowitnow.port;

import com.daami.kata.mowitnow.model.Garden;
import com.daami.kata.mowitnow.model.Mower;
import com.daami.kata.mowitnow.model.Position;

public abstract class ControlMower {
	protected Mower mower;
	protected Garden garden;

	public ControlMower(Mower mower) {
		this.mower = mower;
		this.createAndInitializeMowerPosition();
	};
	public ControlMower(Mower mower, Garden garden) {
		this.mower = mower;
		this.garden = garden;
		this.createAndInitializeMowerPosition();
	};
	private void createAndInitializeMowerPosition() {
		if(this.mower.getPosition() == null) {
			this.mower = Mower.builder().position(new Position(0,0))
					.orientation(this.mower.getOrientation()).build();
		}
	}
	protected Position buildActualPosition() {
		return Position.builder().x(this.mower.getPosition().getX())
				.y(this.mower.getPosition().getY()).build();
	}
	public abstract Mower execute();

}
