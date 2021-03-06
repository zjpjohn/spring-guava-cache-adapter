/**
 *  Copyright 2012 HYPOPORT AG
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
 */
package org.hypoport.springGuavaCacheAdapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

@ContextConfiguration(locations = "guava-cache-adapter-test.spring.xml")
public class SpringGuavaCacheAdapterTest extends AbstractTestNGSpringContextTests {

  @Autowired
  Bean bean;

  @BeforeMethod
  public void beforeMethod() {
    bean.resetCallCount();
  }

  @Test
  public void cached_method_gets_invoked_only_once_for_same_key() {

    bean.getSomethingCached("KEY");
    bean.getSomethingCached("KEY");
    bean.getSomethingCached("KEY");

    assertThat(bean.getCallCount()).isEqualTo(1);
  }

  @Test
  public void cached_method_gets_invoked_one_time_per_key() {

    bean.getSomethingCached("A");
    bean.getSomethingCached("A");
    bean.getSomethingCached("B");
    bean.getSomethingCached("B");

    assertThat(bean.getCallCount()).isEqualTo(2);
  }

  @Test
  public void not_cached_method_gets_invoked_every_time() {

    bean.getSomething("KEY");
    bean.getSomething("KEY");
    bean.getSomething("KEY");

    assertThat(bean.getCallCount()).isEqualTo(3);
  }
}
