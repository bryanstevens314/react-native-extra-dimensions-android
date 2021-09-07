
### Notice
Pull requests always welcome!

## ExtraDimensions

This module allows you to access additional display metrics on Android devices. (RN 0.57.0+)

- Actual width and height of the screen (including elements such as soft menu bar)
- Soft menu height
- Status bar height
- Smart bar height (MeiZu)


### Why?

There is currently a bug in React Native where [`Dimensions.get('window').height` sometimes returns
the wrong value](https://github.com/facebook/react-native/issues/4934).

Also, some apps may want to set the background of status bar and soft menu bar to transparent, thus the top-level
view needs to fill up the real screen size.

### Installation

1. Install with npm
   ```
   npm install react-native-extra-dimensions-android --save
   ```
   
2. linking

    ```
    react-native link react-native-extra-dimensions-android
    ```

2b. You may have to register the module (in android/app/src/main/java/com/YOUR-PROJECT-NAME/MainApplication.java)
`react-native link` should automatically do the following for you. If it doesn't, you might have to add it yourself.

    ```
    import ca.jaysoo.extradimensions.ExtraDimensionsPackage;  // <--- import

    public class MainApplication extends Application implements ReactApplication {
      ......
      protected List<ReactPackage> getPackages() {
        return Arrays.<ReactPackage>asList(
          new MainReactPackage(),
          new ExtraDimensionsPackage()  // <--- add here
        );
      }
      ......
    }
    ```
3. As this is a package with Java, you'll need to rebuild the project.

e.g. `react-native run-android`

4. Whenever you want to use it within React Native code now you can:

`var ExtraDimensions = require('react-native-extra-dimensions-android');`

Or, if you are using ES6

`import ExtraDimensions from 'react-native-extra-dimensions-android';`

### Demo

![](./demo.png)

### API

There are methods for each constant, to fulfill autocomplete in your IDE

`ExtraDimensions.getRealWindowHeight()`

`ExtraDimensions.getRealWindowWidth()`

`ExtraDimensions.getStatusBarHeight()`

`ExtraDimensions.getSoftMenuBarHeight()`

`ExtraDimensions.getSmartBarHeight()`

`ExtraDimensions.isSoftMenuBarEnabled()`
