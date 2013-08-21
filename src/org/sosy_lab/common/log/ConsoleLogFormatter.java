/*
 *  CPAchecker is a tool for configurable software verification.
 *  This file is part of CPAchecker.
 *
 *  Copyright (C) 2007-2013  Dirk Beyer
 *  All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 *  CPAchecker web page:
 *    http://cpachecker.sosy-lab.org
 */
package org.sosy_lab.common.log;

import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.sosy_lab.common.configuration.Configuration;
import org.sosy_lab.common.configuration.InvalidConfigurationException;
import org.sosy_lab.common.configuration.Option;
import org.sosy_lab.common.configuration.Options;

/**
 * Class to handle formatting for console output.
 */
@Options(prefix="log")
public class ConsoleLogFormatter extends Formatter {

  @Option(description="use colors for log messages on console")
  private boolean useColors = true;

  public ConsoleLogFormatter(Configuration config) throws InvalidConfigurationException {
    config.inject(this);

    // Using colors is only good if stderr is connected to a terminal and not
    // redirected into a file.
    // AFAIK there is no way to determine this from Java, but at least there
    // is a way to determine whether stdout is connected to a terminal.
    // We assume that most users only redirect stderr if they also redirect
    // stdout, so this should be ok.
    if ((System.console() == null)
        || System.getProperty("os.name", "").startsWith("Windows")) {
      useColors = false;
    }
  }

  @Override
  public String format(LogRecord lr) {
    StringBuffer sb = new StringBuffer();

    if (useColors) {
      if (lr.getLevel().equals(Level.WARNING)) {
        sb.append("\033[1m"); // bold normal color
      } else if (lr.getLevel().equals(Level.SEVERE)) {
        sb.append("\033[31;1m"); // bold red color
      }
    }
    sb.append(lr.getMessage());
    sb.append(" (");
    sb.append(LogUtils.extractSimpleClassName(lr));
    sb.append(".");
    sb.append(lr.getSourceMethodName());
    sb.append(", ");
    sb.append(lr.getLevel().toString());
    sb.append(")");
    if (useColors) {
      sb.append("\033[m");
    }
    sb.append("\n\n");

    return sb.toString();
  }
}