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
package org.apache.beam.sdk.extensions.euphoria.core.client.util;

import java.io.Serializable;
import java.util.stream.Stream;
import org.apache.beam.sdk.extensions.euphoria.core.client.functional.BinaryFunction;
import org.apache.beam.sdk.extensions.euphoria.core.client.functional.BinaryFunctor;
import org.apache.beam.sdk.extensions.euphoria.core.client.functional.CombinableReduceFunction;
import org.apache.beam.sdk.extensions.euphoria.core.client.functional.ReduceFunctor;
import org.apache.beam.sdk.extensions.euphoria.core.client.io.Collector;
import org.apache.beam.sdk.extensions.euphoria.core.translate.SingleValueContext;

/** Apply a folding function. */
/** @deprecated Use Java SDK directly, Euphoria is scheduled for removal in a future release. */
@Deprecated
public class Fold implements Serializable {

  /**
   * Return a {@link CombinableReduceFunction} that performs a fold operation and emits result after
   * fold of all input data.
   *
   * @param <T> element type
   * @param fold the associative fold function
   * @return the {@link CombinableReduceFunction}
   */
  public static <T> CombinableReduceFunction<T> of(BinaryFunction<T, T, T> fold) {
    return s ->
        s.reduce(fold::apply)
            .orElseThrow(() -> new IllegalStateException("Received empty stream on input!"));
  }

  /**
   * Return a {@link CombinableReduceFunction} that performs a fold operation and emits result after
   * fold of all input data.
   *
   * @param <T> element type
   * @param identity the zero element
   * @param fold the associative fold function
   * @return the {@link CombinableReduceFunction}
   */
  public static <T> CombinableReduceFunction<T> of(T identity, BinaryFunction<T, T, T> fold) {
    return s -> s.reduce(identity, fold::apply);
  }

  /**
   * Return a {@link ReduceFunctor} that performs a fold operation and emits result after fold of
   * all input data.
   *
   * @param <T> element type
   * @param identity the zero element
   * @param fold the associative fold function
   * @return the {@link CombinableReduceFunction}
   */
  public static <T> ReduceFunctor<T, T> of(T identity, BinaryFunctor<T, T, T> fold) {
    return (Stream<T> s, Collector<T> ctx) -> {
      final SingleValueContext<T> wrap = new SingleValueContext<>(ctx.asContext());
      final T ret =
          s.reduce(
              identity,
              (a, b) -> {
                fold.apply(a, b, wrap);
                return wrap.getAndResetValue();
              });
      ctx.collect(ret);
    };
  }
}
