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
package org.sosy_lab.common.concurrency;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import com.google.common.base.Joiner;
import com.google.common.testing.AbstractPackageSanityTests;

public class PackageSanityTest extends AbstractPackageSanityTests {

  {
    setDefault(String[].class, new String[]{"test"});
    setDefault(Joiner.MapJoiner.class, Joiner.on(",").withKeyValueSeparator("="));
    setDefault(ClassLoader.class, new URLClassLoader(new URL[0]));
    try {
      setDefault(Constructor.class, PackageSanityTest.class.getConstructor());
      setDefault(Method.class, PackageSanityTest.class.getDeclaredMethod("defaultMethod"));
    } catch (NoSuchMethodException | SecurityException e) {
      throw new AssertionError(e);
    }
  }

  @SuppressWarnings("unused")
  private void defaultMethod() { }
}