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
package org.apache.beam.sdk.state;

import com.google.auto.value.AutoValue;

/** Static methods for working with {@link TimerSpec TimerSpecs}. */
public class TimerSpecs {

  public static TimerSpec timer(TimeDomain timeDomain) {
    return new AutoValue_TimerSpecs_SimpleTimerSpec(timeDomain);
  }

  public static TimerSpec timerMap(TimeDomain timeDomain) {
    return new AutoValue_TimerSpecs_SimpleTimerSpec(timeDomain);
  }

  /** A straightforward POJO {@link TimerSpec}. Package-level access for AutoValue. */
  @AutoValue
  abstract static class SimpleTimerSpec implements TimerSpec {
    @Override
    public abstract TimeDomain getTimeDomain();
  }
}
