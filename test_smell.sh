#!/bin/zsh
java -jar TestSmellDetector-0.1-jar-with-dependencies.jar \
  --file tsdetector-input.csv \
  --thresholds spadini \
  --granularity boolean \
  --output tsdetector-output.csv