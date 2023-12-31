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
package org.apache.beam.sdk.extensions.euphoria.core.client.accumulators;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.beam.sdk.extensions.euphoria.core.annotation.audience.Audience;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Placeholder implementation of {@link AccumulatorProvider} that may be used in executors as a
 * default.
 *
 * @deprecated Use Java SDK directly, Euphoria is scheduled for removal in a future release.
 */
@Audience(Audience.Type.EXECUTOR)
@Deprecated
public class VoidAccumulatorProvider implements AccumulatorProvider {

  private static final Logger LOG = LoggerFactory.getLogger(VoidAccumulatorProvider.class);

  private VoidAccumulatorProvider() {}

  public static Factory getFactory() {
    return Factory.get();
  }

  @Override
  public Counter getCounter(String name) {
    return VoidCounter.INSTANCE;
  }

  @Override
  public Counter getCounter(String namespace, String name) {
    return VoidCounter.INSTANCE;
  }

  @Override
  public Histogram getHistogram(String name) {
    return VoidHistogram.INSTANCE;
  }

  @Override
  public Histogram getHistogram(String namespace, String name) {
    return VoidHistogram.INSTANCE;
  }

  @Override
  public Timer getTimer(String name) {
    return VoidTimer.INSTANCE;
  }

  // ------------------------

  /** AccumulatorProvider Factory. */
  public static class Factory implements AccumulatorProvider.Factory {

    private static final Factory INSTANCE = new Factory();

    private static final AccumulatorProvider PROVIDER = new VoidAccumulatorProvider();

    private static final AtomicBoolean isLogged = new AtomicBoolean();

    private Factory() {}

    public static Factory get() {
      return INSTANCE;
    }

    @Override
    public AccumulatorProvider create() {
      if (isLogged.compareAndSet(false, true)) {
        LOG.warn("Using accumulators with VoidAccumulatorProvider will have no effect");
      }
      return PROVIDER;
    }
  }

  // ------------------------

  /** Empty implementation of Counter doesnt count anything. */
  private static class VoidCounter implements Counter {

    private static final VoidCounter INSTANCE = new VoidCounter();

    private VoidCounter() {}

    @Override
    public void increment(long value) {
      // NOOP
    }

    @Override
    public void increment() {
      // NOOP
    }
  }

  /** Empty implementation of Histogram doesnt count anything. */
  public static class VoidHistogram implements Histogram {

    private static final VoidHistogram INSTANCE = new VoidHistogram();

    private VoidHistogram() {}

    @Override
    public void add(long value) {
      // NOOP
    }

    @Override
    public void add(long value, long times) {
      // NOOP
    }
  }

  /** Empty implementation of Timer doesnt measure anything. */
  public static class VoidTimer implements Timer {

    private static final VoidTimer INSTANCE = new VoidTimer();

    private VoidTimer() {}

    @Override
    public void add(Duration duration) {
      // NOOP
    }
  }
}
