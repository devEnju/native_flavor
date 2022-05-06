# Native Flavor Example

This repository is showing, how to setup multiple product flavors with different build variants in order to extract and isolate already existing dependencies from an Android project into separate applications. These can then again individually be uploaded to the targeted app stores.

## Motivation

In order to comply with specific store regulations, to keep consistency and to offer a smooth user experience across all devices, it is important to maintain a well structured project. Different builds which would only include necessary dependencies can prevent any complication with store policies but could also cause some unwanted artifacts on a user's end.

Most users have a phone with preinstalled Google Play but could theoretically also use another store application, if the vendor preinstalls it or users install one by themselves. In case of Amazon and Huawei, devices can even come without Google Play and its services. This introduces several possibilities to address the services exclusively available to one store but only one logical project structure to build and distribute optimized applications with the before mentioned constraints.

In the example of distributing an app to Google Play with GMS and the Huawei AppGallery with HMS, there are the following possibilities:

| Device | Google Play | AppGallery |
| --- | :---: | :---: |
| Google Pixel 6 | O | X |
| Huawei P30 Pro | O | O |
| Huawei P40 Pro |   | O |

Since users of a Huawei P40 Pro are unable to officially obtain Google Mobile Services and the Play Store, Huawei Mobile Services should definitely be included in the application for the AppGallery. However, if a user would optionally download the AppGallery on a Google Pixel 6 and receive an update for an application with the same package name, visible services would change from GMS to HMS.

Therefore, it is always recommended to include both services inside an app for a store other than Google Play and to prioritize Google Services. By this prioritization, users with Google Play will never need the extra dependency of HMS, so that this distribution is recommended to also only include GMS.

The application ID or package name should ideally also always be consistent between stores, to allow for easy device upgradability by users via a phone clone and to prehibit them from installing two apps of one kind.

## App Level Gradle

Flavors with the same dimension are able to create additional yet separate source directories which will only be included in the right build variant. Setting up a Huawei flavor will add the build variants `huaweiRelease` and `huaweiDebug` additonally to Google's flavor in this example.

A full setup can be seen in the app level `build.gradle` file, while the following part is most essential:

```
flavorDimensions 'oms'

productFlavors {
    google {
        dimension 'oms'
    }
    huawei {
        dimension 'oms'
        apply plugin: 'com.huawei.agconnect'
    }
}
```

### Plugin Activation

Plugins might also need to be enabled but should then again only be activated in the right flavor. If they are applied under the scope of the flavor as shown in the code snippet from before, they will also only take effect in it.

### Dependency Implementation

Dependencies can easily be added by writing the flavor name in front of the implementation keyword:

```
googleImplementation 'com.google.android.gms:play-services-base:18.0.1'
huaweiImplementation 'com.google.android.gms:play-services-base:18.0.1'
huaweiImplementation 'com.huawei.hms:base:6.4.0.303'
```

For the sake of this simple example, the `play-services-base` dependency could have also been a single implementation, without a leading flavor keyword, but there might be cases where specific dependencies should never be in another flavor and this shows how to account for that.

## Project File Structure

Similarly to the classes and interfaces described later, directories need to follow a specific pattern to be recognized by the active build variant, which can usually be chosen almost on the very bottom of the left side of the Android Studio IDE.

Google and Huawei could be changed with different names in order to follow the same pattern for source folders of flavors:

```
src\google\com\marvinvogl\flavor\WrapperExample.kt
src\huawei\com\marvinvogl\flavor\WrapperExample.kt
```

### Plugin Files

For both the plugin from Huawei and Google Firebase, service json files are needed to compile. Usually, they are put under the app folder but can also be inserted inside of the corresponding flavor directory:

```
src\huawei\agconnect-services.json
```

### Wrapper Example

To use code in the main project's source, a developer needs to provide the exact same interfaces for every created class inside a flavor. If this criteria is not met, one build variant might throw a compiler warning or error.

In a regular project, classes similar to `WrapperExample.kt` could wrap only used services with checking for API availability instead of the static function returning the flavor name and what service is used. There will be some unavoidable code duplication cases for GMS, because it has to be written in both flavors but once this pattern is setup correctly and kept throughout the project, the code should be easily able to be copied between them.


## Documentation Links

- [Android Documentation](https://developer.android.com/studio/build/build-variants)
- [GMS Availability API](https://developers.google.com/android/reference/com/google/android/gms/common/GoogleApiAvailability)
- [HMS Availability API](https://developer.huawei.com/consumer/en/doc/development/hmscore-common-References/huaweiapiavailability-0000001050121134)