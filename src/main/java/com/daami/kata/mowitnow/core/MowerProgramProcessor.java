package com.daami.kata.mowitnow.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.daami.kata.mowitnow.core.exception.BusinessLogicException;
import com.daami.kata.mowitnow.model.Garden;
import com.daami.kata.mowitnow.model.Instruction;
import com.daami.kata.mowitnow.model.Mower;
import com.daami.kata.mowitnow.model.Orientation;
import com.daami.kata.mowitnow.model.Position;
import com.daami.kata.mowitnow.port.ControlMower;
import com.daami.kata.mowitnow.port.ProcessMowers;
import com.google.common.collect.Lists;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class MowerProgramProcessor implements ProcessMowers {

	@Override
	public List<Mower> processDeployedMowers(String programFilePath) {

		List<Mower> result = Lists.newArrayList();

		if (!StringUtils.isNotBlank(programFilePath)) {
			log.error("file path is missing");
			return result;
		}

		Path file = Paths.get(programFilePath);

		try {
			List<String> allFileLines = Files.readAllLines(file);
			if (CollectionUtils.isEmpty(allFileLines)) {
				log.error("The file is empty");
				return result;
			}

			// get the first line of the file --> The garden size limit
			// And then remove it
			String[] gardenSizeLimit = allFileLines.remove(0).split(" ");
			Garden garden = this.processGardenSizeLimitLine(gardenSizeLimit);

			if (garden == null) {
				log.error("The file is badly formatted");
				return result;
			}

			// get a sublist of lines for every mower in the file
			List<List<String>> subLists = ListUtils.partition(allFileLines, allFileLines.size() / 2);
			for (List<String> sublist : subLists) {
				Mower mower = null;

				for (String line : sublist) {
					mower = processInitialMowerPositionLine(mower, garden, line);
					mower = this.processInstructionsLine(mower, line);
				}

				if (mower != null) {
					result.add(mower);
				}
			}

		} catch (IOException x) {
			log.error("IOException: {}", x);
			throw new BusinessLogicException("Failed to read file: IOException", x);
		}

		return result;
	}

	private Mower processInstructionsLine(Mower mower, String line) {
		if (mower != null && this.isUpperCaseAlpha(line)) {
			for (int i = 0; i < line.length(); i++) {
				String instruction = line.substring(i, i + 1);
				if (Instruction.FORWARD.getInstructionCode().equals(instruction)) {
					mower = this.executeInstruction(new AdvanceMower(mower));
				}

				if (Instruction.RIGHT.getInstructionCode().equals(instruction)) {
					mower = this.executeInstruction(new TurnRightMower(mower));
				}

				if (Instruction.LEFT.getInstructionCode().equals(instruction)) {
					mower = this.executeInstruction(new TurnLeftMower(mower));
				}
			}
			log.info("The Last position of the mower is ( {} {} {} )", mower.getPosition().getX(), mower.getPosition().getY(),
					mower.getOrientation().getOrientationCode());
		}
		return mower;
	}

	private Mower executeInstruction(ControlMower instruction) {
		return instruction.execute();
	}

	private boolean isUpperCaseAlpha(String line) {
		return line.matches("[A-Z]+");
	}

	private Garden processGardenSizeLimitLine(String[] gardenSizeLimit) {
		return (NumberUtils.isCreatable(gardenSizeLimit[0]) && NumberUtils.isCreatable(gardenSizeLimit[0]))
				? Garden.builder()
						.gardenLimitPosition(this.buildPosition(Integer.valueOf(gardenSizeLimit[0]),
								Integer.valueOf(gardenSizeLimit[1])))
						.build()
				: null;
	}

	private Mower processInitialMowerPositionLine(Mower mower, Garden garden, String line) {
		String[] mowerInitilPosition = line.split(" ");
		if (mowerInitilPosition.length == 3 && NumberUtils.isCreatable(mowerInitilPosition[0])
				&& NumberUtils.isCreatable(mowerInitilPosition[1])
				&& Orientation.getCodeList().contains(mowerInitilPosition[2])) {

			log.info("The initial position of the mower is ( {} {} {} )", mowerInitilPosition[0],
					mowerInitilPosition[1], mowerInitilPosition[2]);

			return Mower.builder()
					.position(this.buildPosition(Integer.valueOf(mowerInitilPosition[0]),
							Integer.valueOf(mowerInitilPosition[1])))
					.garden(garden).orientation(Orientation.getOrientation(mowerInitilPosition[2])).build();
		}
		return mower;
	}

	private Position buildPosition(int x, int y) {
		return Position.builder().x(x).y(y).build();
	}

}
