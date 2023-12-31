/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.beam.sdk.extensions.euphoria.core.translate.collector;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import org.apache.beam.sdk.extensions.euphoria.core.client.accumulators.AccumulatorProvider;
import org.apache.beam.sdk.extensions.euphoria.core.client.accumulators.Counter;
import org.apache.beam.sdk.extensions.euphoria.core.client.accumulators.Histogram;
import org.apache.beam.sdk.extensions.euphoria.core.client.accumulators.Timer;
import org.apache.beam.sdk.extensions.euphoria.core.client.io.Collector;
import org.apache.beam.sdk.extensions.euphoria.core.client.io.Context;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * {@code Collector} for combinable functors.
 *
 * @deprecated Use Java SDK directly, Euphoria is scheduled for removal in a future release.
 */
@SuppressWarnings({
  "nullness" // TODO(https://github.com/apache/beam/issues/20497)
})
@Deprecated
public class SingleValueCollector<T> implements Collector<T>, Serializable {

  private static final String UNSUPPORTED = "Accumulators are supported for named operators only.";

  private final AccumulatorProvider accumulators;
  private final @Nullable String operatorName;
  private T elem;

  public SingleValueCollector(AccumulatorProvider accumulators, @Nullable String operatorName) {
    this.accumulators = accumulators;
    this.operatorName = operatorName;
  }

  public T get() {
    return elem;
  }

  @Override
  public void collect(T elem) {
    this.elem = elem;
  }

  @Override
  public Context asContext() {
    // this is not needed, the underlying functor does not have access to this
    throw new UnsupportedOperationException("Not supported.");
  }

  @Override
  public Counter getCounter(String name) {
    return accumulators.getCounter(requireNonNull(operatorName, UNSUPPORTED), name);
  }

  @Override
  public Histogram getHistogram(String name) {
    return accumulators.getHistogram(requireNonNull(operatorName, UNSUPPORTED), name);
  }

  @Override
  public Timer getTimer(String name) {
    return accumulators.getTimer(name);
  }
}
