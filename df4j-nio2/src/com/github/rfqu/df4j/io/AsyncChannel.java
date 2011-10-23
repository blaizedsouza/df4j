/*
 * Copyright 2011 by Alexei Kaigorodov
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.github.rfqu.df4j.io;

import java.io.IOException;
import java.nio.channels.AsynchronousChannelGroup;
import java.util.concurrent.ExecutorService;

import com.github.rfqu.df4j.core.Task;

public class AsyncChannel {
    
    protected synchronized static AsynchronousChannelGroup getGroup() throws IOException {
        return currentASGKey.get();
    }

    private static final ThreadLocal <AsynchronousChannelGroup> currentASGKey = new ThreadLocal <AsynchronousChannelGroup> () {

        @Override
        protected AsynchronousChannelGroup initialValue() {
            ExecutorService executor=Task.getCurrentExecutor();
            try {
                return AsynchronousChannelGroup.withThreadPool(executor);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
        }
        
    };
}
