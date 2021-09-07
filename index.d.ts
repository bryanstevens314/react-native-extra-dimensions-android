
declare interface ExtraDimensions {
  getRealWindowHeight: () => function;
  getRealWindowWidth: () => function;
  getStatusBarHeight: () => function;
  getSoftMenuBarHeight: () => function;
  getSmartBarHeight: () => function;
}

declare module "react-native-extra-dimensions-android" {
  const instance: ExtraDimensions;
  export = instance;
}
