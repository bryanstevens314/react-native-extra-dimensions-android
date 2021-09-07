import { NativeModules, Platform } from 'react-native';

export function get(dim) {
  if (Platform.OS !== 'android') {

    console.warn('react-native-extra-dimensions-android is only available on Android. Trying to access', dim);
    return 0;
  } else { // android

    if (!NativeModules.ExtraDimensions) {
      console.error('ExtraDimensions not defined. Try rebuilding your project. e.g. react-native run-android');
      return 0;
    }
    return new Promise(resolve => {
      NativeModules.ExtraDimensions[dim]((value) => { resolve(value); });
    });
  }
}

export function getRealWindowHeight()  { return get('getRealWindowHeight');  }

export function getRealWindowWidth()   { return get('getRealWindowWidth');   }

export function getStatusBarHeight()   { return get('getStatusBarHeight');   }

export function getSoftMenuBarHeight() { return get('getSoftMenuBarHeight'); }

export function getSmartBarHeight()    { return get('getSmartBarHeight');    }

export function isSoftMenuBarEnabled() { return get('isSoftMenuBarEnabled'); }

// stay compatible with pre-es6 exports
export default {
  getRealWindowHeight,
  getRealWindowWidth,
  getStatusBarHeight,
  getSoftMenuBarHeight,
  getSmartBarHeight,
  isSoftMenuBarEnabled
}
