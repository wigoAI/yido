/*
 * Copyright (C) 2020 Wigo Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.moara.yido.tokenizer.dev;

import com.seomse.api.ApiRequests;

/**
 * mecab은 프로세스 재실행 해야만 하네요.
 * 프로세스가 켜있는 도중 사전교체는 안됩니다.
 * @author macle
 */
public class MecabDicChangeTest {
    public static void main(String[] args) {

        String text = "시내버스가 우아한형제들에 가요";


        System.out.println("\n" + ApiRequests.sendToReceiveMessage("localhost",33333, null,"MecabResult",text));

        System.out.println("\n" + ApiRequests.sendToReceiveMessage("localhost",33333, null,"MecabUpdateResult",text));





    }

}
