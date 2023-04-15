package com.daami.kata.mowitnow.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

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
import org.apache.commons.lang3.tuple.Pair;

@Slf4j
@AllArgsConstructor
public class MowerProgramProcessor implements ProcessMowers {

    @Override
    public List<Mower> processDeployedMowers(String programFilePath) {

        if (StringUtils.isBlank(programFilePath)) {
            log.error("file path is missing");
            return Lists.newArrayList();
        }

        try {
            List<String> allFileLines = Files.readAllLines(Paths.get(programFilePath));
            if (CollectionUtils.isEmpty(allFileLines)) {
                log.error("The file is empty");
                return Lists.newArrayList();
            }

            return this.createMowerAndInstructionsPair(allFileLines).stream()
                    .map(mowerStringPair -> this.moveMower(mowerStringPair.getLeft(), mowerStringPair.getRight(),
                            this.createGarden(allFileLines.get(0))))
                    .collect(Collectors.toList());

        } catch (IOException x) {
            throw new BusinessLogicException("Failed to read file: IOException", x);
        }

    }
    private Garden createGarden(String firstLine) {
        String[] gardenSizeLimit = firstLine.split(" ");
        return Garden.builder()
                .gardenLimitPosition(this.buildPosition(Integer.valueOf(gardenSizeLimit[0]),
                        Integer.valueOf(gardenSizeLimit[1])))
                .build();
    }
    List<Pair<Mower, String>> createMowerAndInstructionsPair(List<String> allFileLines) {
        List<List<String>> subLists = ListUtils.partition(allFileLines.subList(1, allFileLines.size()), 2);

        return subLists.stream().map(mowerInstruction -> Pair.of(this.createMower(mowerInstruction.get(0)),
                mowerInstruction.get(1))).collect(Collectors.toList());
    }
    private Mower moveMower(Mower mower, String instructions, Garden garden) {
        if (mower != null && this.isUpperCaseAlpha(instructions)) {
            for (int i = 0; i < instructions.length(); i++) {
                String instruction = instructions.substring(i, i + 1);
                if (Instruction.FORWARD.getInstructionCode().equals(instruction)) {
                    mower = this.executeInstruction(new AdvanceMower(mower, garden));
                }

                if (Instruction.RIGHT.getInstructionCode().equals(instruction)) {
                    mower = this.executeInstruction(new TurnRightMower(mower));
                }

                if (Instruction.LEFT.getInstructionCode().equals(instruction)) {
                    mower = this.executeInstruction(new TurnLeftMower(mower));
                }
            }
            log.info("The Last position of the mower is ( {} {} {} )", mower.getPosition().getX(),
                    mower.getPosition().getY(), mower.getOrientation().getOrientationCode());
        }
        return mower;
    }
    private Mower executeInstruction(ControlMower instruction) {
        return instruction.execute();
    }
    private boolean isUpperCaseAlpha(String line) {
        return line.matches("[A-Z]+");
    }
    private Mower createMower(String line) {
        String[] mowerInitilPosition = line.split(" ");

        log.info("The initial position of the mower is ( {} {} {} )", mowerInitilPosition[0],
                mowerInitilPosition[1], mowerInitilPosition[2]);

        return Mower.builder()
                .position(this.buildPosition(Integer.valueOf(mowerInitilPosition[0]),
                        Integer.valueOf(mowerInitilPosition[1]))).orientation(Orientation.getOrientation(mowerInitilPosition[2])).build();
    }
    private Position buildPosition(int x, int y) {
        return Position.builder().x(x).y(y).build();
    }

}
