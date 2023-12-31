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
package org.apache.beam.sdk.extensions.euphoria.core.client.functional;

import java.io.Serializable;
import org.apache.beam.sdk.extensions.euphoria.core.annotation.audience.Audience;
import org.apache.beam.sdk.extensions.euphoria.core.client.io.Context;

/**
 * Function of single argument with access to Euphoria environment via context.
 *
 * @param <InputT> the type of the element processed
 * @param <OutputT> the type of the result applying element to the function
 * @deprecated Use Java SDK directly, Euphoria is scheduled for removal in a future release.
 */
@Audience(Audience.Type.CLIENT)
@FunctionalInterface
@Deprecated
public interface UnaryFunctionEnv<InputT, OutputT> extends Serializable {

  /**
   * Applies function to given element.
   *
   * @param what The element applied to the function
   * @param context Provides access to the environment.
   * @return the result of the function application
   */
  OutputT apply(InputT what, Context context);
}
