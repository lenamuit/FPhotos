/*
 *
 *  *
 *  *  * Copyright (C) 2014 Antonio Leiva Gordillo.
 *  *  *
 *  *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  *  * you may not use this file except in compliance with the License.
 *  *  * You may obtain a copy of the License at
 *  *  *
 *  *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *  *
 *  *  * Unless required by applicable law or agreed to in writing, software
 *  *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  * See the License for the specific language governing permissions and
 *  *  * limitations under the License.
 *  *
 *
 */

package vn.lenam.imagegallery;

import android.app.Application;
import dagger.Module;
import dagger.Provides;
import vn.lenam.imagegallery.data.DataModule;
import vn.lenam.imagegallery.ui.UiModule;

@Module(
        injects = {
                MPOFApp.class
        },
        includes = {
                DataModule.class,
                UiModule.class
        }
)
public class AppModule {

    private MPOFApp app;

    public AppModule(MPOFApp app) {
        this.app = app;
    }

    @Provides
    public MPOFApp provideApplication() {
        return app;
    }
}
