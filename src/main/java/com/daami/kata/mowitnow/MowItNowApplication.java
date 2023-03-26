package com.daami.kata.mowitnow;

import java.util.List;

import com.daami.kata.mowitnow.core.MowerProgramProcessor;
import com.daami.kata.mowitnow.model.Mower;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MowItNowApplication {

	public static void main(String[] args) {

		MowerProgramProcessor mowerProgram = new MowerProgramProcessor();

		List<Mower> mowers = mowerProgram.processDeployedMowers(args[0]);

		for (int i = 0; i < mowers.size(); i++) {
			log.info("The Mower {} last position is : ({} {} {})", i, mowers.get(i).getPosition().getX(), mowers.get(i).getPosition().getY(),
					mowers.get(i).getOrientation().getOrientationCode());
		}
	}

}
