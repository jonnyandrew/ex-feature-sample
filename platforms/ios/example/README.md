# WYSIWYG iOS example application

This application provides an example usage of the WysiwygComposer 
package found at `platforms/ios/lib/WysiwygComposer`.
It also contains UI tests validating components provided by the package.

# Setup

All that is required is to run `make ios` in the repository main folder
in order to build uniffi bindings for Swift, then the app will build
directly from XCode.

# Gather global code coverage for WysiwygComposer.

The main scheme `Wysiwyg` provides an associated test suite that run both
WysiwygComposer's Unit tests, as well as the example app UI tests. Merged 
test results and code coverage can be used directly within XCode or 
retrieved inside DerivedData's `Logs/Test` folder for usage with an 
external tool.

You can run this test suite from terminal with this kind of command:

```bash
xcodebuild \
  -project Wysiwyg.xcodeproj \
  -scheme Wysiwyg \
  -sdk iphonesimulator \
  -destination 'platform=iOS Simulator,name=iPhone 13,OS=15.5' \
  test
```